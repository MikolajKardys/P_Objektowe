package agh.cs.project;

import java.util.HashMap;
import java.util.Map;

public class GrassField implements IWorldMap, IPositionChangeObserver{
    private final Map<Vector2d, Grass> GrassMap = new HashMap<>();
    private final Map<Vector2d, Animal> Animals = new HashMap<>();
    private final Vector2d upperCorner;
    private final Vector2d lowerCorner = new Vector2d(0, 0);

    private final MapVisualizer viz = new MapVisualizer(this);

    public GrassField(int GrassNumber) {
        int i = 0;
        this.upperCorner = new Vector2d(GrassNumber - 1, GrassNumber - 1);
        while (i < GrassNumber){
            if (growGrassOn(this.lowerCorner, this.upperCorner)){
                i++;
            }
        }
    }

    public boolean growGrassOn(Vector2d lowerCorner, Vector2d upperCorner){
        int width = upperCorner.x - lowerCorner.x;
        int height = upperCorner.y - lowerCorner.y;
        int newGrassX = (int) (Math.random() * (double) width);
        int newGrassY = (int) (Math.random() * (double) height);
        Vector2d position = new Vector2d(newGrassX, newGrassY);
        if (this.canGrowAt(position)) {
            this.GrassMap.put(position, new Grass(position));
            return true;
        }
        return false;
    }

    private boolean canGrowAt(Vector2d position) {
        return !isOccupied(position);
    }

    public boolean tryEatGrass(Animal hungryHippo){
        Vector2d position = hungryHippo.getPosition();
        if (GrassMap.get(position) != null){
            GrassMap.remove(position);
            System.out.println("Ate grass at " + position.toString());
            return true;
        }
        return false;
    }

    //////////////////////////////////////////////////////////////////////////

    public Vector2d convertToMovable(Vector2d position) {
        position = position.add(this.upperCorner.add(new Vector2d(1, 1)));
        return new Vector2d(position.x % (this.upperCorner.x + 1), position.y % (this.upperCorner.y + 1));
    }

    public boolean place(Animal animal) {
        this.Animals.put(animal.getPosition(), animal);
        animal.addObserver(this);
        return true;
    }

    public Object objectAt(Vector2d position) {
        Object possibleAnimal = Animals.get(position);
        if (possibleAnimal != null) {
            return possibleAnimal;
        }
        return GrassMap.get(position);
    }

    public boolean isOccupied(Vector2d position) {
        return objectAt(position) != null;
    }

////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public void positionChanged(Vector2d oldPosition, Vector2d newPosition) {
        Animal animal = Animals.get(oldPosition);
        Animals.put(newPosition, animal);
        Animals.remove(oldPosition);
    }

    @Override
    public void removedElement(Vector2d position){
        Animals.remove(position);
    }

///////////////////////////////////////////////////////////////////////////////////

    private boolean isEmpty(){
        if (!Animals.isEmpty()) return false;
        return GrassMap.isEmpty();
    }

    public String toString() {
        if (isEmpty()){
            return viz.draw(new Vector2d(0, 0), new Vector2d(0, 0));
        }
        return viz.draw(this.lowerCorner, this.upperCorner);
    }
}
