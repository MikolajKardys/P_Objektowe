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
                this.GrassMap.put(position, new Grass(position));
                this.mapCorners.addElement(new Grass(position));
                i++;
            }
        }
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
