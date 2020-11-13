package agh.cs.lab2;
import java.util.ArrayList;
import java.util.List;

abstract class AbstractWorldMap implements IWorldMap{
    protected final List<Animal> animals = new ArrayList<>();
    protected final Vector2d lowerCorn = new Vector2d(0 ,0);
    public String toString() {
        MapVisualizer viz = new MapVisualizer(this);
        return viz.draw(lowerCorn, getUpperCorn());
    }
    protected Vector2d getUpperCorn(){
        return null;
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
