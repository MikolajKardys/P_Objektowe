package agh.cs.lab2;

import java.util.ArrayList;
import java.util.List;

public class GrassField extends AbstractWorldMap{
    private final List<Grass> GrassList = new ArrayList<>();
    public GrassField(int GrassNumber){
        for (int i = 0; i < GrassNumber; i++){
            Vector2d position = new Vector2d((int)(Math.random() * Math.sqrt(GrassNumber*10)),(int)(Math.random() * Math.sqrt(GrassNumber*10)));
            if (!isOccupied(position)){
                GrassList.add(new Grass(position));
            }
            else if (!(this.objectAt(position) instanceof Grass)){
                GrassList.add(new Grass(position));
            }
            else i-=1;
        }
    }

    @Override
    protected Vector2d getUpperCorn(){
        Vector2d upperCorn = new Vector2d(0, 0);
        for (Animal animal : animals){
            upperCorn = animal.getPosition().upperRight(upperCorn);
        }
        for (Grass bunch : GrassList){
            upperCorn = bunch.getPosition().upperRight(upperCorn);
        }
        return upperCorn;
    }
    @Override
    public boolean canMoveTo(Vector2d position) {
        if (position.follows(lowerCorn)){
            if (isOccupied(position)){
                return !(this.objectAt(position) instanceof Animal);      //nie w jednym warunku dla ułatwienia czytelności
            }
            return true;
        }
        return false;
    }
    @Override
    public Object objectAt(Vector2d position) {
        Object possibleAnimal = super.objectAt(position);
        if (possibleAnimal != null){
            return possibleAnimal;
        }
        for (Grass bunch : GrassList){
            if(bunch.getPosition().equals(position)){
                return bunch;
            }
        }
        return null;
    }
}
