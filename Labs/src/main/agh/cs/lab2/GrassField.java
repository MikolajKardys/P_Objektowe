package agh.cs.lab2;

import java.util.ArrayList;
import java.util.List;

public class GrassField implements IWorldMap{
    private final List<Animal> Animals = new ArrayList<>();
    private final List<Grass> GrassList = new ArrayList<>();
    private final Vector2d lowerCorn = new Vector2d(0 ,0);
    public GrassField(int GrassNumber){
        for (int i = 0; i < GrassNumber; i++){
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
        Vector2d upperCorn = new Vector2d(0, 0);
        for (Animal animal : Animals){
            upperCorn = animal.getPosition().upperRight(upperCorn);
        }
        for (Grass bunch : GrassList){
            upperCorn = bunch.getPosition().upperRight(upperCorn);
        }
        return viz.draw(lowerCorn, upperCorn);
    }

    @Override
    public boolean canMoveTo(Vector2d position) {
        if (position.follows(lowerCorn)){
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
