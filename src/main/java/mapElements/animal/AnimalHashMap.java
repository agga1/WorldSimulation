package mapElements.animal;

import utils.Vector2d;

import java.util.*;

/**
 * implementation of multimap designed for animals
 */
public class AnimalHashMap {
    private Map<Vector2d, List<Animal>> animalMap= new HashMap<>();
    public AnimalHashMap(){}

    public void addAnimal(Animal animal){
        if(! animalMap.containsKey(animal.getPosition())){
            List<Animal> animalsAtPos = new ArrayList<>();
            animalsAtPos.add(animal);
            animalMap.put(animal.getPosition(), animalsAtPos);
        }
        else{ animalMap.get(animal.getPosition()).add(animal); }
    }

    public void removeAnimal(Animal animal, Vector2d position){
        if(this.animalMap.get(position)==null) return;
        this.animalMap.get(position).remove(animal);

        if(this.animalMap.get(position).isEmpty()) // remove redundant key
            this.animalMap.remove(position);
    }
    public List<Animal> get(Vector2d position){
        return animalMap.get(position);
    }
    public Set<Vector2d> keySet() { return animalMap.keySet(); }
}
