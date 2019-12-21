package map;

import configuration.WorldConfig;
import mapElements.IMapElement;
import mapElements.animal.Animal;
import mapElements.animal.AnimalHashMap;
import mapElements.animal.Genome;
import mapElements.grass.Grass;
import utils.Rect;
import utils.Vector2d;
import visualization.map.MapViewPane;

import java.util.*;
import java.util.stream.Collectors;

import static utils.Vector2d.randomFromSet;

public class WorldMap implements IWorldMap {
    private final WorldConfig config = WorldConfig.getInstance();
    private Rect rect;
    private Rect jungleRect;
    private List<Animal> animals = new ArrayList<>();
    private Map<Vector2d, Grass> grassMap = new HashMap<>();
    private AnimalHashMap animalMap = new AnimalHashMap();
    private Set<Vector2d> freeSpace = new HashSet<>();

    //statistics and visualization
    private MapViewPane controller;
    private Set<Vector2d> changedTiles = new HashSet<>();
    private WorldStats worldStats;
    private Animal tracked = null;

    public WorldMap() {
        this.rect = WorldConfig.getInstance().mapBounds();
        this.jungleRect = WorldConfig.getInstance().jungleBounds();
        this.freeSpace.addAll(this.rect.toVectors());
        populate();
        this.worldStats = new WorldStats(animals);
    }

    public void setViewController(MapViewPane controller) {
        this.controller = controller;
    }

    /**
     * place given number of animals on map, each on different position.
     */
    private void populate() {
        for (int i = 0; i < config.params.animalsAtStart; i++) {
            Vector2d position = randomFromSet(freeSpace);
            if (position == null) throw new IllegalArgumentException("map declared too small for all the animals");
            Animal animal = new Animal(this, position);
            this.place(animal);
        }
    }

    public boolean place(Animal animal) throws IllegalArgumentException {
        if (!this.rect.lowerLeft.precedes(animal.getPosition()) || !this.rect.upperRight.follows(animal.getPosition())) {
            throw new IllegalArgumentException(animal.getPosition() + " is outside map! ");
        }
        animals.add(animal);
        animalMap.addAnimal(animal);
        updateTile(animal.getPosition());
        return true;
    }

    public void onUpdate(int step) {
        changedTiles = new HashSet<>();
        for (int i = 0; i < step; i++)
            simulate();

        notifyController(changedTiles);
    }

    public void simulate() {
//        // handle all deaths
        List<Animal> justDied = animals.stream().filter(Animal::isDead).collect(Collectors.toList());
        justDied.forEach(this::removeDeadAnimal);
//
//        // move all animals and eat grass
        animals.forEach(Animal::move);
        animalMap.keySet().forEach(this::eatGrass);
//
        // procreate in each region
        List<Animal> newborns = animalMap.keySet().stream()
                .map(this::procreate)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
        newborns.forEach(this::place);
//
        growGrass();
//        this.worldStats.updateNextDay(Collections.<Animal>emptyList(), Collections.<Animal>emptyList(), animals, grassMap.size());

        this.worldStats.updateNextDay(justDied, newborns, animals, grassMap.size());
    }

    private void growGrass() {
        Set<Vector2d> freeJungle = freeSpace.stream()
                .filter(v -> this.jungleRect.contains(v)).collect(Collectors.toSet());
        Set<Vector2d> freeDesert = new HashSet<>(freeSpace);
        freeDesert.removeAll(freeJungle);
        Vector2d onJungle = randomFromSet(freeJungle);
        Vector2d onDesert = randomFromSet(freeDesert);
        if (onDesert != null) {
            grassMap.put(onDesert, new Grass(onDesert));
            updateTile(onDesert);
        }
        if (onJungle != null) {
            grassMap.put(onJungle, new Grass(onJungle));
            updateTile(onJungle);
        }
    }

    private void eatGrass(Vector2d position) {
        if (grassMap.get(position) == null) return;
        List<Animal> animalsAtPos = animalMap.get(position);
        animalsAtPos.sort(Comparator.comparing(Animal::getEnergy));

        List<Animal> allStrongest = new ArrayList<>();
        allStrongest.add(animalsAtPos.get(0));
        for (int i = 1; i < animalsAtPos.size(); i++)
            allStrongest.add(animalsAtPos.get(i));

        double partForEach = 1.0 / allStrongest.size();
        grassMap.remove(position);
        allStrongest.forEach(a -> a.eatGrass(partForEach));
    }

    public boolean isOccupied(Vector2d vector2d) {
        return (objectAt(vector2d) != null);
    }

    public void onPositionChanged(Animal animal, Vector2d from) {
        animalMap.removeAnimal(animal, from);
        updateTile(from);
        animalMap.addAnimal(animal);
        updateTile(animal.getPosition());
    }

    private void removeDeadAnimal(Animal animal) {
        animalMap.removeAnimal(animal, animal.getPosition());
        updateTile(animal.getPosition());
        animals.remove(animal);
    }

    private Optional<Animal> procreate(Vector2d position) {
        List<Animal> animalsAtPos = animalMap.get(position);
        if (animalsAtPos.size() < 2) return Optional.empty();
        animalsAtPos.sort(Comparator.comparing(Animal::getEnergy));
        Animal parent1 = animalsAtPos.get(0);
        Animal parent2 = animalsAtPos.get(1);
        Vector2d childPosition;
        List<Vector2d> positions = new Rect(
                new Vector2d(position.x - 1, position.y - 1),
                new Vector2d(position.x + 1, position.y + 1)).toVectors().stream()
                .map(v -> v.mapToBoundaries(this.getBoundaries())).collect(Collectors.toList());
        List<Vector2d> emptyPositions = positions.stream().filter(p -> objectAt(p) == null).collect(Collectors.toList());

        if (emptyPositions.size() > 0) { // at least 1 free position found
            int idx = new Random().nextInt(emptyPositions.size());
            childPosition = emptyPositions.get(idx);
        } else { // all adjacent positions occupied
            int idx = new Random().nextInt(9);
            childPosition = positions.get(idx);
        }
        return parent1.procreate(parent2, childPosition, worldStats.getDay());
    }

    private void updateTile(Vector2d position) {
        if (objectAt(position) == null)
            freeSpace.add(position);
        else
            freeSpace.remove(position);
        changedTiles.add(position);
    }

    @Override
    public boolean canMoveTo(Vector2d pos) {
        return true;
    }

    public IMapElement objectAt(Vector2d pos) { // returns any object
        List<Animal> animalsAtPos = animalMap.get(pos);
        if (animalsAtPos != null) {
            if (tracked != null && tracked.getPosition().equals(pos)) return tracked;
            return animalsAtPos.get(0);
        }
        return grassMap.get(pos);
    }

    public String toString() {
        return "map";
    }

    @Override // TODO get rect instead
    public Vector2d[] getBoundaries() {
        return new Vector2d[]{this.rect.lowerLeft, this.rect.upperRight};
    }

    @Override
    public List<Animal> getAnimalsWithGenome(Genome dominantGenome) {
        return animals.stream().filter(animal -> animal.getGenome().equals(dominantGenome)).collect(Collectors.toList());
    }

    public Rect getRect() {
        return this.rect;
    }

    public Rect getJungleRect() {
        return jungleRect;
    }

    private void notifyController(Set<Vector2d> changedTiles) {
        if (controller != null)
            controller.onUpdate(changedTiles);
    }

    public void onTrackingChanged(Animal animal, boolean isTracked) {
        if (this.tracked != null) {  // untrack previous animal
            this.tracked.untrack();
            notifyController(Set.of(this.tracked.getPosition()));
        }
        if (!isTracked)
            this.tracked = null;
        else {
            this.tracked = animal;
            this.tracked.track();
        }
    }

    public Animal getTracked() {
        return this.tracked;
    }

    public WorldStats getStats() {
        return this.worldStats;
    }
}
