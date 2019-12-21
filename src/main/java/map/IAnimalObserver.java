package map;


import mapElements.animal.Animal;
import utils.Vector2d;

public interface IAnimalObserver {
     void onPositionChanged(Animal animal, Vector2d oldPosition);
//     void onTrackingChanged(Animal animal, boolean status);
}

