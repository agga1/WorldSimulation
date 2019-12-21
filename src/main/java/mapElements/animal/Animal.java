package mapElements.animal;

import configuration.WorldConfig;
import map.IAnimalObserver;
import map.IWorldMap;
import mapElements.ILivingMapElement;
import visualization.Icon;
import utils.Orientation;
import utils.Vector2d;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class Animal implements ILivingMapElement {
    private static int minEnergy = WorldConfig.getInstance().params.startEnergy/2;  // minimum energy needed to procreate
    private Orientation orientation = Orientation.NORTH;
    private Vector2d position;
    private Genome genome;
    private int energy;
    private IWorldMap map;
    private Set<IAnimalObserver> observers = new HashSet<>();

    private AnimalStats stats;
    private boolean isTracked=false;

    public Animal(IWorldMap map, Vector2d initialPos) { // TODO make builder?
        this(map, initialPos, new Genome(), WorldConfig.getInstance().params.startEnergy);
    }
    public Animal(IWorldMap map, Vector2d initialPos, Genome genome, int energy) {
        this.map = map;
        this.position = initialPos;
        this.genome = genome;
        this.energy = energy;
        this.stats = new AnimalStats();
        addObserver(map);
    }
    public Animal(IWorldMap map, Vector2d initialPos, Genome genome, int energy, Animal father, Animal mother, int birthday) {
        this.map = map;
        this.position = initialPos;
        this.genome = genome;
        this.energy = energy;
        this.stats = new AnimalStats(birthday, List.of(father, mother));
        addObserver(map);
    }

    public void move(){
        this.energy -= WorldConfig.getInstance().params.moveEnergy; // movement takes 1 energy
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

    public Optional<Animal> procreate(Animal other, Vector2d position, int birthday){
        if(! canReproduce(other)) return Optional.empty();
        int newEnergy = this.energy/4 + other.energy/4;
        this.energy = this.energy*3/4;
        other.energy = other.energy*3/4;
        Genome childGenome = new Genome(this.genome, other.genome);
        stats.newKidInFamily();
        return Optional.of(new Animal(this.map, position, childGenome, newEnergy, this, other, birthday));
    }

    public boolean canReproduce(Animal other){
        return this.energy >= minEnergy && other.energy >= minEnergy;
    }

    public void eatGrass(double part){
        this.energy = (int) (this.energy+ (WorldConfig.getInstance().params.plantEnergy)*part);
    }

    public boolean isDead(){
        return this.energy < 1;
    }

    public Vector2d getPosition() {
        return this.position;
    }

    public void addObserver(IAnimalObserver observer){
        observers.add(observer);
    }

    void removeObserver(IAnimalObserver observer){
        observers.remove(observer);
    }

    public AnimalStats getStats(){
        return this.stats;
    }

    private void positionChanged(Vector2d oldPosition, Vector2d newPosition){ // notify observers
        for(IAnimalObserver observer : observers){
            observer.onPositionChanged(this, oldPosition);
        }
    }

    public boolean isTracked(){
        return isTracked;
    }
    public void track(){ isTracked = true; }
    public void untrack(){ isTracked = false; }

    public int getEnergy() {
        return energy;
    }

    public Genome getGenome() {
        return this.genome;
    }

    public String toString(){
        return "animal at: "+position
                +"\nenergy: "+energy
                +"\ngenome:\n "+genome;
    }

    @Override
    public Icon getIcon() {
        if(!isTracked) return Icon.ANIMAL;
        return Icon.ANIMALTRACKED;
    }
}
