package agh.cs.project;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GrassField implements IWorldMap, IChangeObserver {
    private final Map<Vector2d, Grass> GrassMap = new HashMap<>();
    private final FieldMap Animals = new FieldMap();
    private final Vector2d upperCorner;
    private final Vector2d lowerCorner = new Vector2d(0, 0);

    private final MapVisualizer viz = new MapVisualizer(this);
    public final IChangeObserver mapUpdater;

    public final int width;
    public final int height;

    public GrassField(JFrame f, int width, int height) {
        this.width = width;
        this.height = height;
        this.upperCorner = new Vector2d(width, height);
        this.mapUpdater = new MapVizRepresentation(f, this);
    }

//////////////////////////////////////////////////////////////////////////////////////////////////////

    public boolean growGrass(){

        int newX = (int) (Math.random() * (double) this.width);
        int newY = (int) (Math.random() * (double) this.height);
        Vector2d position = new Vector2d(newX, newY);

        if (!isOccupied(position)) {
            Grass newGrass = new Grass(position);
            this.GrassMap.put(position, newGrass);
            this.mapUpdater.addedElement(newGrass);
            return true;
        }
        return false;
    }

    public boolean isGrassAt(Vector2d position){
        Grass grass = this.GrassMap.get(position);
        return grass != null;
    }

////////////////////////////////////////////////////////////////////////////////////

    public Vector2d convertToMovable(Vector2d position) {
        return new Vector2d((position.x + this.width) % this.width, (position.y + this.height) % this.height);
    }

    public boolean place(Animal newAnimal) {
        this.Animals.addAnimal(newAnimal);
        return true;
    }

//////////////////////////////////////////////////////////////////////////////////////////////

    public Object objectAt(Vector2d position) {
        AnimalSortedList possibleAnimalList = Animals.get(position);
        if (possibleAnimalList != null) {
            return possibleAnimalList.getAllTop();
        }
        return GrassMap.get(position);
    }

    public boolean isOccupied(Vector2d position) {
        return objectAt(position) != null;
    }

////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public void changedPosition(Animal animal, Vector2d oldPosition) {
        this.Animals.removeAnimal(oldPosition, animal);

        this.Animals.addAnimal(animal);

        this.mapUpdater.changedPosition(animal, oldPosition);
    }

    @Override
    public void addedElement(AbstractWorldMapElement element){
        if (element instanceof Animal) {
            this.place((Animal) element);
        }
        else this.GrassMap.put(element.getPosition(), (Grass) element);

        this.mapUpdater.addedElement(element);
    }

    @Override
    public void removedElement(AbstractWorldMapElement element){
        if (element instanceof Animal) {
            this.Animals.removeAnimal(element.getPosition(), (Animal) element);
        }

        else this.GrassMap.remove(element.getPosition());

        this.mapUpdater.removedElement(element);
    }

    @Override
    public void changedEnergy(Animal animal){
        this.mapUpdater.changedEnergy(animal);
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

////////////////////////////////////////////////////////////////////////////////////////////

    public String getTopStringAt(Vector2d position){
        AnimalSortedList list = this.Animals.get(position);
        if (list != null) {
            ArrayList <Animal> topAt = this.Animals.get(position).getAllTop();
            if (topAt != null) {
                return topAt.get(0).toString();
            }
        }
        return null;
    }

    public Color getColorAt(Vector2d position){
        AnimalSortedList list = this.Animals.get(position);
        if (list != null) {
            ArrayList <Animal> topAt = this.Animals.get(position).getAllTop();
            if (topAt != null) {
                return topAt.get(0).getHealthColor();
            }
        }
        return null;
    }
}
