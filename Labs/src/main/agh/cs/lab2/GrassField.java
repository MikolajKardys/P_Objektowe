package agh.cs.lab2;

import java.util.HashMap;
import java.util.Map;

public class GrassField extends AbstractWorldMap {
    private final Map<Vector2d, Grass> GrassList = new HashMap<>(); // myląca nazwa

    public GrassField(int GrassNumber) {
        double grassRangeConst = Math.sqrt(GrassNumber * 10);
        for (int i = 0; i < GrassNumber; i++) {
            Vector2d position = new Vector2d((int) (Math.random() * grassRangeConst), (int) (Math.random() * grassRangeConst)); // warto wynik tego obliczenia zapisać w zmiennej
            if (!isOccupied(position)) {
                GrassList.put(position, new Grass(position));
            } else i -= 1;  // czytelniej jest użyć while'a, niż modyfikować licznik for'a
        }
    }

    @Override
    protected Vector2d getUpperCorn() {
        Vector2d upperCorn;
        if (Animals.isEmpty()) {
            if (GrassList.isEmpty()) return new Vector2d(0, 0);    //Żeby pojawiło się cokolwiek dla pustej mapy
            upperCorn = GrassList.keySet().iterator().next();               //adres 1. elementu Grasslist
        } else {
            upperCorn = Animals.keySet().iterator().next();            //adres 1. elementu Animals
        }

        for (Animal animal : Animals.values()) {
            upperCorn = animal.getPosition().upperRight(upperCorn);
        }
        for (Grass bunch : GrassList.values()) {
            upperCorn = bunch.getPosition().upperRight(upperCorn);
        }
        return upperCorn;
    }

    @Override
    protected Vector2d getLowerCorn() {
        Vector2d lowerCorn;
        if (Animals.isEmpty()) {
            if (GrassList.isEmpty()) return new Vector2d(0, 0);      //Żeby pojawiło się cokolwiek dla pustej mapy
            lowerCorn = GrassList.keySet().iterator().next();               //adres 1. elementu Grasslist
        } else {
            lowerCorn = Animals.keySet().iterator().next();            //adres 1. elementu Animals
        }

        for (Animal animal : Animals.values()) {
            lowerCorn = animal.getPosition().lowerLeft(lowerCorn);
        }
        for (Grass bunch : GrassList.values()) {
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
        return GrassList.get(position);
    }
}
