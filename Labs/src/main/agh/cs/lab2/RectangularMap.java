package agh.cs.lab2;
import java.util.ArrayList;
import java.util.List;

public class RectangularMap implements IWorldMap {
    final private Vector2d upperCorn;
    final private Vector2d lowerCorn = new Vector2d(0 ,0);
    final private List<Animal> animals = new ArrayList<>();

    public RectangularMap (int width, int height){
        this.upperCorn = new Vector2d(width, height);
    }

    public String toString() {
        MapVisualizer viz = new MapVisualizer(this);
        return viz.draw(lowerCorn, upperCorn);
    }

    @Override
    public boolean canMoveTo(Vector2d position) {
        if (position.precedes(upperCorn) && position.follows(lowerCorn)){
            return (!isOccupied(position));
        }
        return false;
    }

    @Override
    public boolean place(Animal animal) {
        if(!canMoveTo(animal.getPosition()))
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
        for (Animal animal : animals){
            if(animal.getPosition().equals(position)){
                return animal;
            }
        }
        return null;
    }
}
