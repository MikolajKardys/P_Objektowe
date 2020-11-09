package agh.cs.lab2;

import java.util.ArrayList;
import java.util.List;

public class GrassField implements IWorldMap{
    private int GrassNumber;
    private final List<Animal> Animals = new ArrayList<>();
    private final List<Grass> GrassList = new ArrayList<>();
    public GrassField(int GrassNumber){
        this.GrassNumber = GrassNumber;
        for (int i = 0; i < this.GrassNumber; i++){
            Vector2d position = new Vector2d((int)(Math.random() * Math.sqrt(GrassNumber*10)),(int)(Math.random() * Math.sqrt(GrassNumber*10)));
            if (this.objectAt(position) == null){
                GrassList.add(new Grass(position));
            }
            else if (!(this.objectAt(position) instanceof Grass)){
                GrassList.add(new Grass(position));
            }
            else i-=1;
        }
    }
    public String toString() {
        MapVisualizer viz = new MapVisualizer(this);
        Vector2d UpperCorn = new Vector2d(0, 0);
        for (Animal animal : Animals){
            UpperCorn = animal.getPosition().upperRight(UpperCorn);
        }
        for (Grass bunch : GrassList){
            UpperCorn = bunch.getPosition().upperRight(UpperCorn);
        }
        return viz.draw(new Vector2d(0,0), UpperCorn);
    }

    @Override
    public boolean canMoveTo(Vector2d position) {
        if (position.follows(new Vector2d(0, 0))){
            if (isOccupied(position)){
                return !(this.objectAt(position) instanceof Animal);
            }
            return true;
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
        for (Grass bunch : GrassList){
            if(bunch.getPosition().equals(position)){
                return bunch;
            }
        }
        return null;
    }
}
