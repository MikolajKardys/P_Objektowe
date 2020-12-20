package agh.cs.project;

import agh.cs.project.Pages.SimulationPage;
import agh.cs.project.Pages.SimulationPagePanels.MapPanel;

import java.util.*;

public class GrassField implements IChangeObserver {
    private final Map<Vector2d, Grass> GrassMap = new HashMap<>();
    private final FieldMap Animals = new FieldMap();
    private final SimulationPage mainPanel;
    private final Jungle jungle;
    private final int maxFields;
    private int takenFields;

    public final int width;
    public final int height;
    public int jungleGrassNumber;

    public GrassField(int width, int height, float jungleRatio, ProjectEngine engine) {
        this.width = width;
        this.height = height;
        this.jungle = new Jungle(this, jungleRatio);

        this.takenFields = 0;
        this.jungleGrassNumber = 0;
        this.maxFields = width * height - this.jungle.maxFields;

        this.mainPanel = new SimulationPage(this, engine);
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

    private void place(Animal newAnimal) {
        this.Animals.addAnimal(newAnimal);
    }

    public Vector2d convertToMovable(Vector2d position) {
        return new Vector2d((position.x + this.width) % this.width, (position.y + this.height) % this.height);
    }

    public Vector2d getSpawnPoint(Vector2d position){
        ArrayList<Vector2d> positionList = new ArrayList<>();
        for (MapDirection dir : MapDirection.values()){
            Vector2d freePoint = position.add(dir.toUnitVector());
            freePoint = this.convertToMovable(freePoint);
            if (this.isFree(freePoint)) positionList.add(freePoint);
        }
        if (positionList.size() > 0) {
            int randInd = (int) (Math.random() * positionList.size());
            return positionList.get(randInd);
        }
        int randomIndex = (int) (Math.random() * 8);
        return position.add(MapDirection.newMapDirection(randomIndex).toUnitVector());
    }

//////////////////////////////////////////////////////////////////////////////////////////////

    public Object objectAt(Vector2d position) {
        AnimalSortedList possibleAnimalList = Animals.get(position);
        if (possibleAnimalList != null) {
            return possibleAnimalList;
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
            this.mainPanel.getStats().addedElement(element);
        }
        else {
            if ( positionInJungle(position) ){
                jungleGrassNumber++;
            }
            this.GrassMap.put(position, (Grass) element);
        }

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
            this.mainPanel.getStats().removedElement(element);
        }
        else {
            this.GrassMap.remove( position );
        }

    }

    @Override
    public void energyChanged(Animal animal, int change){
        this.mainPanel.getStats().energyChanged(animal, change);
    }

///////////////////////////////////////////////////////////////////////////////////

    public boolean positionInJungle(Vector2d position){
        return this.jungle.positionInJungle(position);
    }

    public Vector2d getJungleUpperCorner(){
        return this.jungle.upperCorner;
    }

    public Vector2d getJungleLowerCorner(){
        return this.jungle.lowerCorner;
    }

    public ArrayList<Grass> getGrasses(){
        return new ArrayList<>(this.GrassMap.values());
    }

    public ArrayList<AnimalSortedList> getAnimals(){
        return new ArrayList<>(this.Animals.values());
    }

    public void updateStats(){
        this.mainPanel.getStats().allUpdate(this);
    }

    public void repaint(){
        if (this.mainPanel.getMap() instanceof MapPanel) ((MapPanel) this.mainPanel.getMap()).repaint();
    }

    public void highLight(Vector2d position) {
        this.mainPanel.getMap().highLight(position.x, position.y);
    }

    public void removeHighLight(Vector2d position) {
        this.mainPanel.getMap().removeHighLight(position.x, position.y);
    }

    public void terminated(){
        this.mainPanel.terminated();
    }
}
