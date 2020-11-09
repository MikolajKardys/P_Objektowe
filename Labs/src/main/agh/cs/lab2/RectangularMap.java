package agh.cs.lab2;
import java.util.ArrayList;
import java.util.List;

public class RectangularMap implements IWorldMap {
    final private int width;
    final private int height;
    private List<Animal> Animals = new ArrayList<>();
    public RectangularMap (int width, int height){
        this.width = width;
        this.height = height;
    }

    public String toString() {
        MapVisualizer viz = new MapVisualizer(this);
        return viz.draw(new Vector2d(0,0), new Vector2d(this.width, this.height));
    }

    @Override
    public boolean canMoveTo(Vector2d position) {
        if (position.precedes(new Vector2d(this.width, this.height)) && position.follows(new Vector2d(0, 0))){
            return (!isOccupied(position));
        }
        return false;
    }

    @Override
    public boolean place(Animal animal) {
        if(!canMoveTo(animal.getPosition()))
            return false;
        Animals.add(animal);
        return true;
    }

    @Override
    public boolean isOccupied(Vector2d position) {
        return objectAt(position) != null;
    }

    @Override
    public Object objectAt(Vector2d position) {
        for (Animal animal : Animals){
            if(animal.getPosition().equals(position)){
                return animal;
            }
        }
        return null;
    }
}
