package agh.cs.project;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class GrassField implements IWorldMap, IPositionChangeObserver{
    private final Map<Vector2d, Grass> GrassMap = new HashMap<>();
    private final Map<Vector2d, AnimalSortedList> Animals = new HashMap<>();
    private final Vector2d upperCorner;
    private final Vector2d lowerCorner = new Vector2d(0, 0);

    private final MapVisualizer viz = new MapVisualizer(this);
    public final IPositionChangeObserver mapUpdater;

    public final int width;
    public final int height;

    public GrassField(int GrassNumber, JFrame f, int width, int height, int fieldSize) {
        this.width = width;
        this.height = height;
        this.upperCorner = new Vector2d(width, height);


        this.mapUpdater = new MapVizRepresentation(f, this, fieldSize);
        int i = 0;
        while (i < GrassNumber){
            if (growGrassOn(this.lowerCorner, this.upperCorner)){
                i++;
            }
        }
    }

    public boolean growGrassOn(Vector2d lowerCorner, Vector2d upperCorner){
        int width = upperCorner.x - lowerCorner.x;
        int height = upperCorner.y - lowerCorner.y;
        int newX = (int) (Math.random() * (double) width);
        int newY = (int) (Math.random() * (double) height);
        Vector2d position = new Vector2d(newX, newY);

        if (this.canGrowAt(position)) {
            Grass newGrass = new Grass(position);
            this.GrassMap.put(position, newGrass);
            this.mapUpdater.addedElement(newGrass);
            return true;
        }
        return false;
    }

    private boolean canGrowAt(Vector2d position) {
        return !isOccupied(position);
    }

////////////////////////////////////////////////////////////////////////////////////

    public Vector2d convertToMovable(Vector2d position) {
        return new Vector2d((position.x + this.width) % this.width, (position.y + this.height) % this.height);
    }

    private void addAnimalAt(Animal animal, Vector2d position){
        if ( this.objectAt(position) instanceof Animal ){
            this.Animals.get(position).add(animal);
        }
        else {
            AnimalSortedList list = new AnimalSortedList(animal);
            this.Animals.put(position, list);
        }
    }

    public boolean place(Animal animal) {
        this.addAnimalAt(animal, animal.getPosition());
        animal.addObserver(this);
        this.mapUpdater.addedElement(animal);
        return true;
    }

//////////////////////////////////////////////////////////////////////////////////////////////

    public Object objectAt(Vector2d position) {
        AnimalSortedList possibleAnimalList = Animals.get(position);
        if (possibleAnimalList != null) {
            return possibleAnimalList.getTop();
        }
        return GrassMap.get(position);
    }

    public boolean isOccupied(Vector2d position) {
        return objectAt(position) != null;
    }

////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public void positionChanged(Animal animal, Vector2d oldPosition) {
        Animals.get(oldPosition).remove(animal);
        if (Animals.get(oldPosition).size() == 0) {
            Animals.remove(oldPosition);
        }
        this.addAnimalAt(animal, animal.getPosition());

        this.mapUpdater.positionChanged(animal, oldPosition);
    }

    @Override
    public void addedElement(AbstractWorldMapElement element){
        //Jeszcze nie potrzebna
    }

    @Override
    public void removedElement(AbstractWorldMapElement element, Vector2d oldPosition){
        if (element instanceof Animal) {
            Animals.get(oldPosition).remove(element);
            if (Animals.get(oldPosition).size() == 0){
                Animals.remove(element.getPosition());
            }
        }

        else this.GrassMap.remove(element.getPosition());

        this.mapUpdater.removedElement(element, element.getPosition());
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

    public String getTopStringAt(Vector2d position){
        AnimalSortedList list = this.Animals.get(position);
        if (list != null) {
            Animal topAt = this.Animals.get(position).getTop();
            if (topAt != null) {
                return topAt.toString();
            }
        }
        return null;
    }

    public Color getColorAt(Vector2d position){
        AnimalSortedList list = this.Animals.get(position);
        if (list != null) {
            Animal topAt = this.Animals.get(position).getTop();
            if (topAt != null) {
                return topAt.getHealthColor();
            }
        }
        return null;
    }
}
