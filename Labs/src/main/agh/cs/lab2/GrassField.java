package agh.cs.lab2;

import java.util.HashMap;
import java.util.Map;

public class GrassField extends AbstractWorldMap {
    private final Map<Vector2d, Grass> GrassMap = new HashMap<>();

    public GrassField(int GrassNumber) {
        double grassRangeConst = Math.sqrt(GrassNumber * 10);
        int i = 0;
        while (i < GrassNumber){
            int newGrassX = (int) (Math.random() * grassRangeConst);
            int newGrassY = (int) (Math.random() * grassRangeConst);
            Vector2d position = new Vector2d(newGrassX, newGrassY);
            if (!isOccupied(position)) {
                GrassMap.put(position, new Grass(position));
                i++;
            }
        }
    }

    @Override
    protected Vector2d getUpperCorn() {
        Vector2d upperCorn;
        if (Animals.isEmpty()) {
            if (GrassMap.isEmpty()) return new Vector2d(0, 0);    //Żeby pojawiło się cokolwiek dla pustej mapy
            upperCorn = GrassMap.keySet().iterator().next();               //adres 1. elementu Grasslist
        } else {
            upperCorn = Animals.keySet().iterator().next();            //adres 1. elementu Animals
        }

        for (Animal animal : Animals.values()) {
            upperCorn = animal.getPosition().upperRight(upperCorn);
        }
        for (Grass bunch : GrassMap.values()) {
            upperCorn = bunch.getPosition().upperRight(upperCorn);
        }
        return upperCorn;
    }

    @Override
    protected Vector2d getLowerCorn() {
        Vector2d lowerCorn;
        if (Animals.isEmpty()) {
            if (GrassMap.isEmpty()) return new Vector2d(0, 0);      //Żeby pojawiło się cokolwiek dla pustej mapy
            lowerCorn = GrassMap.keySet().iterator().next();               //adres 1. elementu Grasslist
        } else {
            lowerCorn = Animals.keySet().iterator().next();            //adres 1. elementu Animals
        }

        for (Animal animal : Animals.values()) {
            lowerCorn = animal.getPosition().lowerLeft(lowerCorn);
        }
        for (Grass bunch : GrassMap.values()) {
            lowerCorn = bunch.getPosition().lowerLeft(lowerCorn);
        }
        return lowerCorn;
    }

    @Override
    public boolean canMoveTo(Vector2d position) {
        if (isOccupied(position)) {
            return !(this.objectAt(position) instanceof Animal);      //nie w jednym warunku dla ułatwienia czytelności
        }
        return true;
    }

    @Override
    public Object objectAt(Vector2d position) {
        Object possibleAnimal = super.objectAt(position);
        if (possibleAnimal != null) {
            return possibleAnimal;
        }
        return GrassMap.get(position);
    }
}
