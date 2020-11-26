package agh.cs.lab2;

import java.util.HashMap;
import java.util.Map;

abstract class AbstractWorldMap implements IWorldMap, IPositionChangeObserver {
    private final MapBoundary mapCorners = new MapBoundary();
    protected final Map<Vector2d, Animal> Animals = new HashMap<>();
    private final MapVisualizer viz = new MapVisualizer(this);

    public String toString() {
        return viz.draw(getLowerCorn(), getUpperCorn());
    }

    abstract protected Vector2d getLowerCorn();

    abstract protected Vector2d getUpperCorn();   // górna kukurydza? // :)

    @Override
    public boolean place(Animal animal) {
        if (!canMoveTo(animal.getPosition()))
            return false;
        Animals.put(animal.getPosition(), animal);

        mapCorners.addElement(animal);

        animal.addObserver(this);
        return true;
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

