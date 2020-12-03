package agh.cs.lab2;

import java.util.HashMap;
import java.util.Map;

abstract class AbstractWorldMap implements IWorldMap, IPositionChangeObserver {
    protected final MapBoundary mapCorners = new MapBoundary(); // czy to jest część wspólna obu map?
    protected final Map<Vector2d, Animal> Animals = new HashMap<>();
    private final MapVisualizer viz = new MapVisualizer(this);

    private Vector2d getLowerCorn(){
        return mapCorners.lowerCorn();
    }

    private Vector2d getUpperCorn(){
        return mapCorners.upperCorn();
    }  // górna kukurydza? // :) // nadal mnie ta kukurydza nurtuje; bo chyba nie chodzi o oszczędność 2 literek?

    public String toString() {
        return viz.draw(getLowerCorn(), getUpperCorn());
    }



    @Override
    public boolean place(Animal animal) {
        if (!canMoveTo(animal.getPosition())) {
            throw new IllegalArgumentException("Can't add Animal to position " + animal.getPosition().toString() + "; field already taken");
        }
        this.Animals.put(animal.getPosition(), animal);

        this.mapCorners.addElement(animal);

        animal.addObserver(this);
        animal.addObserver(this.mapCorners);    // czemu MapBoundary nie wychodzi samo z inicjatywą?
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

