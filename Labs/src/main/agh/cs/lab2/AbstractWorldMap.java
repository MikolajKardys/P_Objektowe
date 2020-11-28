package agh.cs.lab2;

import java.util.HashMap;
import java.util.Map;

abstract class AbstractWorldMap implements IWorldMap, IPositionChangeObserver {
    protected final MapBoundary mapCorners = new MapBoundary();
    protected final Map<Vector2d, Animal> Animals = new HashMap<>();
    private final MapVisualizer viz = new MapVisualizer(this);

    private Vector2d getLowerCorn(){
        return mapCorners.lowerCorn();
    }

    private Vector2d getUpperCorn(){
        return mapCorners.upperCorn();
    }  // górna kukurydza? // :)

    public String toString() {
        mapCorners.printSorted(); ////////////////////////////////////
        return viz.draw(getLowerCorn(), getUpperCorn());
    }



    @Override
    public boolean place(Animal animal) {
        if (!canMoveTo(animal.getPosition()))
            return false;
        this.Animals.put(animal.getPosition(), animal);

        this.mapCorners.addElement(animal);

        animal.addObserver(this);
        animal.addObserver(this.mapCorners);
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

