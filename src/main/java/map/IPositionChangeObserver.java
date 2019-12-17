package map;


import mapElements.animal.Animal;
import utils.Vector2d;

public interface IPositionChangeObserver {
    public void positionChanged(Animal animal, Vector2d oldPosition);
}

