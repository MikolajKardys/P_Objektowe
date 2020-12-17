package agh.cs.project;

import agh.cs.project.Pages.SimulationPage;
import agh.cs.project.Pages.SimulationPagePanels.MapPanel;
import agh.cs.project.Pages.SimulationPagePanels.StatsPanel;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GrassField implements IChangeObserver {
    private final Map<Vector2d, Grass> GrassMap = new HashMap<>();
    private final FieldMap Animals = new FieldMap();

    private final MapPanel mapUpdater;
    private final StatsPanel statsUpdater;
    private final Jungle jungle;

    public final int width;
    public final int height;

    public int jungleGrassNumber;
    public int animalNumber = 0;

    private final int maxFields;
    private int takenFields;

    public GrassField(int width, int height, float jungleRatio, ProjectEngine engine) {
        this.width = width;
        this.height = height;
        this.jungle = new Jungle(this, jungleRatio);

        this.takenFields = 0;
        this.jungleGrassNumber = 0;
        this.maxFields = width * height - this.jungle.maxFields;

        SimulationPage thisPage = new SimulationPage(this, engine);
        this.mapUpdater = thisPage.getMap();
        this.statsUpdater = thisPage.getStats();
    }

//////////////////////////////////////////////////////////////////////////////////////////////////////

    public int grassCount(){
        return this.GrassMap.size();
    }
    private boolean cantGrowAt(Vector2d position, boolean inJungle){
        if (inJungle){
            if (!this.positionInJungle(position)) return true;
        }
        else {
            if (this.positionInJungle(position)) return true;
        }
        return !isFree(position);
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
            while (cantGrowAt(position, true));
            this.addedElement(new Grass(position));
        }

        if (this.takenFields < this.maxFields) {
            do {
                newX = (int) (Math.random() * (double) this.width);
                newY = (int) (Math.random() * (double) this.height);
                position = new Vector2d(newX, newY);
            }
            while (cantGrowAt(position, false));
            this.addedElement(new Grass(position));
        }
    }

    public boolean isGrassAt(Vector2d position){ //usuwa jeśli jesśli jest
        Grass grass = this.GrassMap.get(position);
        if (grass != null){
            this.GrassMap.remove(position);
            if ( positionInJungle(position) ){
                jungleGrassNumber--;
            }
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

        if (this.mapUpdater != null) this.mapUpdater.changedPosition(animal, oldPosition);
    }

    @Override
    public void addedElement(AbstractWorldMapElement element){
        Vector2d position = element.getPosition();
        if ( positionInJungle(position) ){
            if ( isFree(position) ) jungle.takenFields++;
        }
        else {
            if ( isFree(position) ) this.takenFields++;
        }

        if (element instanceof Animal) {
            this.place((Animal) element);
            animalNumber++;
        }
        else {
            if ( positionInJungle(position) ){
                jungleGrassNumber++;
            }
            this.GrassMap.put(position, (Grass) element);
        }

        if (this.mapUpdater != null) this.mapUpdater.addedElement(element);
    }

    @Override
    public void removedElement(AbstractWorldMapElement element){
        Vector2d position = element.getPosition();
        if (element instanceof Animal) {
            this.Animals.removeAnimal(position, (Animal) element);
            if ( positionInJungle(position) ){
                if ( isFree(position) ) jungle.takenFields--;
            }
            else {
                if ( isFree(position) ) this.takenFields--;
            }
            animalNumber--;
        }
        else {
            this.GrassMap.remove( position );
        }

        if (this.mapUpdater != null) this.mapUpdater.removedElement(element);
    }

    @Override
    public void changedEnergy(Animal animal){
        if (this.mapUpdater != null) this.mapUpdater.changedEnergy(animal);
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

    public void updateStats(Object task){
        this.statsUpdater.allUpdate(this);
    }

    public void setVisible(boolean doSet){
        this.mapUpdater.setVisible(doSet);
    }
}
