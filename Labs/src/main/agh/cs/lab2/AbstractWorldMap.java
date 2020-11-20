package agh.cs.lab2;

import java.util.ArrayList;
import java.util.List;

abstract class AbstractWorldMap implements IWorldMap {
    protected final List<Animal> animals = new ArrayList<>();
    protected MapVisualizer viz = new MapVisualizer(this);
    public String toString() {
        return viz.draw(getLowerCorn(), getUpperCorn());
    }

    abstract Vector2d getLowerCorn();
    abstract Vector2d getUpperCorn();

    @Override
    public boolean place(Animal animal) {
        if (!canMoveTo(animal.getPosition()))
            return false;
        animals.add(animal);
        return true;
    }

    @Override
    public boolean isOccupied(Vector2d position) {
        return objectAt(position) != null;
    }

    @Override
    public Object objectAt(Vector2d position) {
        for (Animal animal : animals) {
            if (animal.getPosition().equals(position)) {
                return animal;
            }
        }
        return null;
    }
}
