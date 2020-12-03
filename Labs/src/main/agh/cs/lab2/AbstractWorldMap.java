package agh.cs.lab2;

import java.util.HashMap;
import java.util.Map;

abstract class AbstractWorldMap implements IWorldMap, IPositionChangeObserver {
    protected final Map<Vector2d, Animal> Animals = new HashMap<>();
    private final MapVisualizer viz = new MapVisualizer(this);

    abstract protected Vector2d getLowerCorner();   // :( // Może w tej trawie rozła kukurydza?
    abstract protected Vector2d getUpperCorner();

    public String toString() {
        return viz.draw(getLowerCorner(), getUpperCorner());
    }

    @Override
    public boolean isOccupied(Vector2d position) {
        return objectAt(position) != null;
    }

    @Override
    public Object objectAt(Vector2d position) {
        return Animals.get(position);
    }

    @Override
    public void positionChanged(Vector2d oldPosition, Vector2d newPosition) {
        Animal animal = Animals.get(oldPosition);
        Animals.remove(oldPosition);
        Animals.put(newPosition, animal);
    }

}

