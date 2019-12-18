package map;

import configuration.WorldConfig;
import mapElements.grass.Grass;
import mapElements.IMapElement;
import mapElements.animal.Animal;
import mapElements.animal.AnimalHashMap;
import visualization.map.MapView;
import utils.Rect;
import utils.Vector2d;

import java.util.*;
import java.util.stream.Collectors;

public class WorldMap implements IWorldMap {
    private final WorldConfig config = WorldConfig.getInstance();
    private Rect rect;
    private Rect jungleRect;
    private List<Animal> animals = new ArrayList<>();
    private Map<Vector2d, Grass> grassMap = new HashMap<>();
    private AnimalHashMap animalMap = new AnimalHashMap();
    private Set<Vector2d> freeSpace = new HashSet<>();
    private MapView controller;
    private int maxEnergy;

    //statistics
    private int allChildrenWithLivingParents=0;
    private int nrOfDeaths=0;
    private int lifeLengthForDeadSum=0;


    private Set<Vector2d> changedTiles = new HashSet<>();

    private int day = 0;

    public WorldMap(){
        this.rect = WorldConfig.getInstance().mapBounds();
        this.jungleRect = WorldConfig.getInstance().jungleBounds();
        this.freeSpace.addAll(this.rect.toVectors());
        this.maxEnergy = WorldConfig.getInstance().params.startEnergy;
        populate();
    }
    public void setViewController(MapView controller){
        this.controller = controller;
    }

    /**
     * place given number of animals on map, each on different position.
     */
    private void populate(){
        for(int i=0;i<config.params.animalsAtStart; i++){
            Vector2d position = randomFromSet(freeSpace);
            if(position == null) throw new IllegalArgumentException("map declared too small for all the animals");
            Animal animal = new Animal(this, position);
            this.place(animal);
        }
    }

    public boolean place(Animal animal) throws IllegalArgumentException {
        if (!this.rect.lowerLeft.precedes(animal.getPosition()) || !this.rect.upperRight.follows(animal.getPosition())){
            throw new IllegalArgumentException(animal.getPosition() + " is outside map! ");
        }
        animals.add(animal);
        animalMap.addAnimal(animal);
        updateTile(animal.getPosition());
        return true;
    }

    public void onUpdate(int step) {
        changedTiles = new HashSet<>();
        for(int i=0;i<step;i++){
            simulate();
        }
        changedTiles.forEach(pos -> notifyTileChanged(pos, objectAt(pos)));
    }
    public void simulate(){
        day++;

        // handle all deaths
        List<Animal> justDied = animals.stream().filter(Animal::isDead).collect(Collectors.toList());
        //statistics
        justDied.forEach(a-> allChildrenWithLivingParents -=a.getNrOfChildren());
        lifeLengthForDeadSum += day*justDied.size();
        nrOfDeaths += justDied.size();
        justDied.forEach(this::removeDeadAnimal);

        // move all animals and eat grass
        animals.forEach(Animal::move);
        animalMap.keySet().forEach(this::eatGrass);

        // procreate in each region
        List<Animal> newborns = animalMap.keySet().stream()
                .map(this::procreate)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
        newborns.forEach(this::place);
        // statistics
        allChildrenWithLivingParents += newborns.size()*2; // for each child 2 parents have +1 child

        growGrass();
    }
    private void growGrass(){
        Set<Vector2d> freeJungle = Set.copyOf(freeSpace).stream()
                .filter(v-> this.jungleRect.contains(v)).collect(Collectors.toSet());
        Set<Vector2d> freeDesert = new HashSet<>(freeSpace);
        freeDesert.removeAll(freeJungle);
        Vector2d onJungle = randomFromSet(freeJungle);
        Vector2d onDesert = randomFromSet(freeDesert);
        if(onDesert != null){
            grassMap.put(onDesert, new Grass(onDesert));
            updateTile(onDesert);
        }
        if(onJungle != null){
            grassMap.put(onJungle, new Grass(onJungle));
            updateTile(onJungle);
        }
    }
    private static Vector2d randomFromSet(Set<Vector2d> set){
        if(set.isEmpty()) return null;
        int idx = new Random().nextInt(set.size());
        int i=0;
        for(Vector2d vector:set){
            if(i==idx) return vector;
            i++;
        }
        return null;
    }
    private void eatGrass(Vector2d position){
        if(grassMap.get(position)==null) return;
        List<Animal> animalsAtPos = animalMap.get(position);
        animalsAtPos.sort(Comparator.comparing(Animal::getEnergy));
        List<Animal> strongest = new ArrayList<>();
        strongest.add(animalsAtPos.get(0));
        for(int i=1;i<animalsAtPos.size();i++){
            strongest.add(animalsAtPos.get(i));
        }
        double part = 1.0/strongest.size();
        grassMap.remove(position);
        strongest.forEach(a->a.eatGrass(part));
        maxEnergy = strongest.get(0).getEnergy();
    }

    public boolean isOccupied(Vector2d vector2d){
        return (objectAt(vector2d) != null);
    }

    public void positionChanged(Animal animal, Vector2d from){
        animalMap.removeAnimal(animal, from);
        updateTile(from);
        animalMap.addAnimal(animal);
        updateTile(animal.getPosition());
    }

    private void removeDeadAnimal(Animal animal){
        animalMap.removeAnimal(animal, animal.getPosition());
        updateTile(animal.getPosition());
        animals.remove(animal);
    }
    private void updateTile(Vector2d position){
        if(objectAt(position)==null)
            freeSpace.add(position);
        else
            freeSpace.remove(position);
        changedTiles.add(position);
    }
    private Optional<Animal> procreate(Vector2d position){
        List<Animal> animalsAtPos = animalMap.get(position);
        if(animalsAtPos.size() < 2 ) return Optional.empty();
        animalsAtPos.sort(Comparator.comparing(Animal::getEnergy));
        Animal parent1 = animalsAtPos.get(0);
        Animal parent2 = animalsAtPos.get(1);
        Vector2d childPosition;
        List<Vector2d> positions = new Rect(
                            new Vector2d(position.x-1, position.y-1),
                            new Vector2d(position.x+1, position.y+1)).toVectors().stream()
                            .map(v->v.mapToBoundaries(this.getBoundaries()) ).collect(Collectors.toList());
        List<Vector2d> emptyPositions = positions.stream().filter(p->objectAt(p)==null).collect(Collectors.toList());

        if(emptyPositions.size() > 0){ // at least 1 free position found
            int idx = new Random().nextInt(emptyPositions.size());
            childPosition = emptyPositions.get(idx);
        } else{ // all adjacent positions occupied
            int idx = new Random().nextInt(9);
            childPosition = positions.get(idx);
        }
       return parent1.procreate(parent2, childPosition);
    }

    @Override
    public boolean canMoveTo(Vector2d pos){
        return true; // no restrictions
    }

    public IMapElement objectAt(Vector2d pos){ // returns any object
        List<Animal> animalsAtPos = animalMap.get(pos);
        if(animalsAtPos != null) return animalsAtPos.get(0);
        return grassMap.get(pos);
    }

    public String toString(){
        return "map";
    }

    public int getMaxEnergy() {
        return maxEnergy;
    }

    @Override
    public Vector2d[] getBoundaries() {
        return new Vector2d[]{this.rect.lowerLeft, this.rect.upperRight};
    }
    public Rect getRect(){ return this.rect; }

    public Rect getJungleRect() {
        return jungleRect;
    }

    private void notifyTileChanged(Vector2d position, IMapElement object){
        controller.onTileUpdate(position, object);
    }

    //statistics
    public int getNrOfAnimals(){
        return animals.size();
    }

    public int getNrOfPlants(){
        return grassMap.keySet().size();
    }
    public double getAverageEnergy(){
        return (double)animals.stream().map(Animal::getEnergy).reduce(0, Integer::sum) /animals.size();
    }

    public double avgLifeExpectancy() {
        return (double)lifeLengthForDeadSum/nrOfDeaths;
    }

    /**
     * @return average nr of children for living animals
     */
    public int avgNrOfChildren() {
        return allChildrenWithLivingParents/animals.size();
    }
}
