package map;

import mapElements.IMapElement;
import mapElements.animal.Animal;
import utils.Vector2d;

/**
 * The interface responsible for interacting with the map of the world.
 * Assumes that Vector2d and MoveDirection classes are defined.
 *
 * @author apohllo
 *
 */
public interface IWorldMap extends IPositionChangeObserver {
    /**
     * Indicate if any object can move to the given vector2d.
     *
     * @param vector2d
     *            The vector2d checked for the movement possibility.
     * @return True if the object can move to that vector2d.
     */
    boolean canMoveTo(Vector2d vector2d);

    /**
     * Place a car on the map.
     *
     *            The car to place on the map.
     * @return True if the car was placed. The car cannot be placed if the map is already occupied.
     */
    boolean place(Animal animal);

    /**
     * Move the cars on the map according to the provided move directions. Every
     * n-th direction should be sent to the n-th car on the map.
     *
     * @param directions
     *            Array of move directions.
     */
//    void run(MoveDirection[] directions);

    /**
     * Return true if given vector2d on the map is occupied. Should not be
     * confused with canMove since there might be empty positions where the car
     * cannot move.
     *
     * @param vector2d
     *            Vector2d to check.
     * @return True if the vector2d is occupied.
     */
    boolean isOccupied(Vector2d vector2d);

    /**
     * Return an object at a given vector2d.
     *
     * @param vector2d
     *            The vector2d of the object.
     * @return Object or null if the vector2d is not occupied.
     */
    IMapElement objectAt(Vector2d vector2d);

    Vector2d[] getBoundaries();
}
