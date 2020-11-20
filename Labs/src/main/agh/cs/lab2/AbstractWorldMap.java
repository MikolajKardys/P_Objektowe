package agh.cs.lab2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

abstract class AbstractWorldMap implements IWorldMap, IPositionChangeObserver {
    protected final List<Animal> animals = new ArrayList<>();
    protected Map<Vector2d,Animal> Animals = new HashMap<>();
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
        for (Animal animal : animals) {
            if (animal.getPosition().equals(position)) {
                return animal;
            }
        }
        return null;
    }

    @Override
    public void positionChanged(Vector2d oldPosition, Vector2d newPosition){
        Animal animal = Animals.get(oldPosition);
        removeFromHashMap(oldPosition);
        putToHashMap(newPosition, animal);
    }

    @Override
    public void printHashTab(){
        System.out.println(this.Animals);
    }

    protected void putToHashMap (Vector2d position, Animal animal){
        Animals.put(position, animal);
    }
    protected void removeFromHashMap (Vector2d position){
        Animals.remove(position);
    }
}
