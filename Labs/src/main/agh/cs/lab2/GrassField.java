package agh.cs.lab2;

import java.util.ArrayList;
import java.util.List;

public class GrassField extends AbstractWorldMap {
    private final List<Grass> GrassList = new ArrayList<>();

    public GrassField(int GrassNumber) {
        double givenSqrt = Math.sqrt(GrassNumber * 10);
        for (int i = 0; i < GrassNumber; i++) {
            Vector2d position = new Vector2d((int) (Math.random() * givenSqrt), (int) (Math.random() * givenSqrt));
            if (!isOccupied(position)) {
                GrassList.add(new Grass(position));
            } else i -= 1;
        }
    }

    @Override
    protected Vector2d getUpperCorn() {
        if (Animals.isEmpty()) return new Vector2d(0, 0);

        Vector2d upperCorn = Animals.keySet().iterator().next();
        for (Animal animal : Animals.values()) {
            upperCorn = animal.getPosition().upperRight(upperCorn);
        }
        for (Grass bunch : GrassList) {
            upperCorn = bunch.getPosition().upperRight(upperCorn);
        }
        return upperCorn;
    }

    @Override
    protected Vector2d getLowerCorn(){
        if (Animals.isEmpty()) return new Vector2d(0, 0);

        Vector2d lowerCorn = Animals.keySet().iterator().next();
        for (Animal animal : Animals.values()) {
            lowerCorn = animal.getPosition().lowerLeft(lowerCorn);
        }
        for (Grass bunch : GrassList) {
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
        for (Grass bunch : GrassList) {
            if (bunch.getPosition().equals(position)) {
                return bunch;
            }
        }
        return null;
    }
}
