package mapElements.animal;

import configuration.Config;
import map.IPositionChangeObserver;
import map.IWorldMap;
import mapElements.IMapElement;
import utils.Orientation;
import utils.Vector2d;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

public class Animal implements IMapElement {
    private static int minEnergy = Config.getInstance().params.startEnergy/2;  // minimum energy needed to procreate
    private Orientation orientation = Orientation.NORTH;
    private Vector2d position;
    private Genome genome;
    private int energy;
    private IWorldMap map;
    private Set<IPositionChangeObserver> observers = new HashSet<>();

    private int nrOfChildren=0;

    public Animal(){
    }
    public Animal(IWorldMap map, Vector2d initialPos) { // TODO make builder?
        this(map, initialPos, new Genome(), Config.getInstance().params.startEnergy);
    }
    public Animal(IWorldMap map, Vector2d initialPos, Genome genome, int energy) {
        this.map = map;
        this.position = initialPos;
        this.genome = genome;
        this.energy = energy;
        addObserver(map);
    }
    public void move(){

        this.energy -= Config.getInstance().params.moveEnergy; // movement takes 1 energy
        int geneIndex = ThreadLocalRandom.current().nextInt(this.genome.getGenome().length);
        int turnBy = this.genome.getGeneAt(geneIndex);
        this.orientation = this.orientation.turnBy(turnBy);
        Vector2d oldPosition = this.position;
        Vector2d newPosition = this.position
                .add(this.orientation.toUnitVector())
                .mapToBoundaries(this.map.getBoundaries());
        if(map.canMoveTo(newPosition)){
            this.position = newPosition;
            this.positionChanged(oldPosition, this.position);
        }
    }

    public Optional<Animal> procreate(Animal other, Vector2d position){
        if(! canReproduce(other)) return Optional.empty();
        int newEnergy = this.energy/4 + other.energy/4;
        this.energy = this.energy*3/4;
        other.energy = other.energy*3/4;
        Genome childGenome = new Genome(this.genome, other.genome);
        nrOfChildren ++;
        return Optional.of(new Animal(this.map, position, childGenome, newEnergy));
    }

    public boolean canReproduce(Animal other){
        return this.energy >= minEnergy && other.energy >= minEnergy;
    }

    public void eatGrass(double part){
        this.energy = (int) (this.energy+ (Config.getInstance().params.plantEnergy)*part);
    }

    public boolean isDead(){
        return this.energy < 1;
    }

    public Orientation getOrientation() {
        return this.orientation;
    }

    public Vector2d getPosition() {
        return this.position;
    }

    public void addObserver(IPositionChangeObserver observer){
        observers.add(observer);
    }

    void removeObserver(IPositionChangeObserver observer){
        observers.remove(observer);
    }

    private void positionChanged(Vector2d oldPosition, Vector2d newPosition){ // notify observers
        for(IPositionChangeObserver observer : observers){
            observer.positionChanged(this, oldPosition);
        }
    }

    public int getEnergy() {
        return energy;
    }

    public String toString(){
        return "animal at: "+position+"\nenergy: "+energy;
    }

    public int getNrOfChildren() {
        return nrOfChildren;
    }
}
