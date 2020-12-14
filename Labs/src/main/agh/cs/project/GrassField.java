package agh.cs.project;

import agh.cs.project.Pages.SimulationPage;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GrassField implements IChangeObserver {
    public final Map<Vector2d, Grass> GrassMap = new HashMap<>();
    private final FieldMap Animals = new FieldMap();

    public final IChangeObserver mapUpdater;

    public final int width;
    public final int height;

    public final Jungle jungle;

    private final int maxFields;
    public int takenFields;

    public GrassField(int width, int height, float jungleRatio, ProjectEngine engine) {
        this.width = width;
        this.height = height;
        this.jungle = new Jungle(this, jungleRatio);
        this.takenFields = 0;
        this.maxFields = width * height - this.jungle.maxFields;
        this.mapUpdater = new SimulationPage(this, engine);
    }

//////////////////////////////////////////////////////////////////////////////////////////////////////
    private boolean canGrowAt(Vector2d position, boolean inJungle){
        if (inJungle){
            if (!this.positionInJungle(position)) return false;
            return isFree(position);
        }
        else {
            if (this.positionInJungle(position)) return false;
            return isFree(position);
        }
    }

    public void growGrass(){
        int newX;
        int newY;
        Vector2d position;

        if (jungle.takenFields < jungle.maxFields) {
            do {
                newX = (int) (Math.random() * (double) this.width);
                newY = (int) (Math.random() * (double) this.height);
                position = new Vector2d(newX, newY);
            }
            while (!canGrowAt(position, true));
            this.addedElement(new Grass(position));
        }

        if (this.takenFields < this.maxFields) {
            do {
                newX = (int) (Math.random() * (double) this.width);
                newY = (int) (Math.random() * (double) this.height);
                position = new Vector2d(newX, newY);
            }
            while (!canGrowAt(position, false));
            this.addedElement(new Grass(position));
        }

    }

    public boolean isGrassAt(Vector2d position){ //usuwa jeśli jesśli jest
        Grass grass = this.GrassMap.get(position);
        if (grass != null){
            this.GrassMap.remove(position);
            return true;
        }
        return false;
    }

    public boolean isAnimalAt(Vector2d position) {
        return this.objectAt(position) instanceof ArrayList;
    }
////////////////////////////////////////////////////////////////////////////////////

    public Vector2d convertToMovable(Vector2d position) {
        return new Vector2d((position.x + this.width) % this.width, (position.y + this.height) % this.height);
    }

    private void place(Animal newAnimal) {
        this.Animals.addAnimal(newAnimal);
    }

//////////////////////////////////////////////////////////////////////////////////////////////

    public Object objectAt(Vector2d position) {
        AnimalSortedList possibleAnimalList = Animals.get(position);
        if (possibleAnimalList != null) {
            return possibleAnimalList.getAllTop();
        }
        return GrassMap.get(position);
    }

    public boolean isFree(Vector2d position) {
        return objectAt(position) == null;
    }

////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public void changedPosition(Animal animal, Vector2d oldPosition) {
        this.Animals.removeAnimal(oldPosition, animal);

        if ( positionInJungle(oldPosition) && !positionInJungle(animal.getPosition()) ){
            if (isFree(oldPosition)) jungle.takenFields--;
            if (isFree(animal.getPosition())) this.takenFields++;
        }
        else if ( !positionInJungle(oldPosition) && positionInJungle(animal.getPosition()) ){
            if (isFree(oldPosition)) this.takenFields--;
            if (isFree(animal.getPosition())) jungle.takenFields++;
        }
        else {
            if ( positionInJungle(animal.getPosition()) ) {
                if (isFree(oldPosition)) jungle.takenFields--;
                if (isFree(animal.getPosition())) jungle.takenFields++;
            }
            if ( !positionInJungle(animal.getPosition()) ) {
                if (isFree(oldPosition)) this.takenFields--;
                if (isFree(animal.getPosition())) this.takenFields++;
            }
        }

        this.Animals.addAnimal(animal);

        this.mapUpdater.changedPosition(animal, oldPosition);
    }

    @Override
    public void addedElement(AbstractWorldMapElement element){
        if ( positionInJungle(element.getPosition()) ){
            if (isFree(element.getPosition())) jungle.takenFields++;
        }
        else {
            if (isFree(element.getPosition())) this.takenFields++;
        }

        if (element instanceof Animal) {
            this.place((Animal) element);
        }
        else {
            this.GrassMap.put(element.getPosition(), (Grass) element);
        }

        this.mapUpdater.addedElement(element);
    }

    @Override
    public void removedElement(AbstractWorldMapElement element){
        if (element instanceof Animal) {
            this.Animals.removeAnimal(element.getPosition(), (Animal) element);
            if ( positionInJungle(element.getPosition()) ){
                if (isFree(element.getPosition())) jungle.takenFields--;
            }
            else {
                if (isFree(element.getPosition())) this.takenFields--;
            }
        }
        else this.GrassMap.remove(element.getPosition());

        this.mapUpdater.removedElement(element);
    }

    @Override
    public void changedEnergy(Animal animal){
        this.mapUpdater.changedEnergy(animal);
    }

///////////////////////////////////////////////////////////////////////////////////

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

    public boolean positionInJungle(Vector2d position){
        return this.jungle.positionInJungle(position);
    }
}
