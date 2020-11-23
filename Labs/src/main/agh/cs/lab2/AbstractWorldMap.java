package agh.cs.lab2;

import java.util.HashMap;
import java.util.Map;

abstract class AbstractWorldMap implements IWorldMap, IPositionChangeObserver {
    protected Map<Vector2d, Animal> Animals = new HashMap<>();  // oba pola mogą być finalne
    protected MapVisualizer viz = new MapVisualizer(this);  // to może być prywatne

    public String toString() {
        return viz.draw(getLowerCorn(), getUpperCorn());
    }

    abstract Vector2d getLowerCorn();   // prawdopodobnie powinno być protected

    abstract Vector2d getUpperCorn();   // górna kukurydza?

    @Override
    public boolean place(Animal animal) {
        if (!canMoveTo(animal.getPosition()))
            return false;
        Animals.put(animal.getPosition(), animal);
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

