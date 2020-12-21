package agh.cs.project.Sources;

import agh.cs.project.Pages.SimulationPage;
import agh.cs.project.Pages.SimulationPagePanels.MapPanel;

import java.util.*;

public class GrassField implements IChangeObserver {
    private final Map<Vector2d, Grass> GrassMap = new HashMap<>();
    private final FieldMap Animals = new FieldMap();
    private final Jungle jungle;
    private final int maxFields;
    private int takenFields;
    private int jungleGrassNumber;

    public final int width;
    public final int height;

    private final SimulationPage mainPanel;

    public GrassField(int width, int height, float jungleRatio, ProjectEngine engine) {
        this.width = width;
        this.height = height;
        jungle = new Jungle(this, jungleRatio);

        takenFields = 0;
        jungleGrassNumber = 0;
        maxFields = width * height - jungle.maxFields;

        mainPanel = new SimulationPage(this, engine);
    }
//////////////////////////////////////////////////////////////////////////////////////////////////////

//Gettery

    public ArrayList<Grass> getGrasses(){
        return new ArrayList<>(GrassMap.values());
    }

    public ArrayList<AnimalSortedList> getAnimals(){
        return new ArrayList<>(Animals.values());
    }

    public Vector2d getJungleUpperCorner(){
        return jungle.upperCorner;
    }

    public Vector2d getJungleLowerCorner(){
        return jungle.lowerCorner;
    }

    public boolean positionInJungle(Vector2d position){
        return jungle.positionInJungle(position);
    }

    public int getJungleGrassNumber(){
        return jungleGrassNumber;
    }

    public int grassCount(){
        return GrassMap.size();
    }

//////////////////////////////////////////////////////////////////////////////////////////////////////

//Obsługuje fazę rośnięcia trawy na mapie

    public void growGrass(){
        int newX;
        int newY;
        Vector2d position;

        if (jungle.takenFields < jungle.maxFields) {
            do {
                newX = (int) (Math.random() * (double) width);
                newY = (int) (Math.random() * (double) height);
                position = new Vector2d(newX, newY);
            }
            while (cantGrowAt(position, true));
            addedElement(new Grass(position));
        }

        if (takenFields < maxFields) {
            do {
                newX = (int) (Math.random() * (double) width);
                newY = (int) (Math.random() * (double) height);
                position = new Vector2d(newX, newY);
            }
            while (cantGrowAt(position, false));
            addedElement(new Grass(position));
        }
    }

////////////////////////////////////////////////////////////////////////////////////

//operacje pomocnicze na współrzędnych

    public Vector2d convertToMovable(Vector2d position) {
        return new Vector2d((position.x + width) % width, (position.y + height) % height);
    }

    public Vector2d getSpawnPoint(Vector2d position) {
        ArrayList<Vector2d> positionList = new ArrayList<>();
        for (MapDirection dir : MapDirection.values()) {
            Vector2d freePoint = position.add(dir.toUnitVector());
            freePoint = convertToMovable(freePoint);
            if (isFree(freePoint)) positionList.add(freePoint);
        }
        if (positionList.size() > 0) {
            int randInd = (int) (Math.random() * positionList.size());
            return positionList.get(randInd);
        }
        int randomIndex = (int) (Math.random() * 8);
        return position.add(MapDirection.newMapDirection(randomIndex).toUnitVector());
    }

//////////////////////////////////////////////////////////////////////////////////////////////

//Funkcje testujące

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

    public boolean isGrassAt(Vector2d position){          //usuwa jeśli jest
        Grass grass = GrassMap.get(position);
        if (grass != null){
            GrassMap.remove(position);
            if ( positionInJungle(position) ){
                jungleGrassNumber--;
            }
            return true;
        }
        return false;
    }

    public boolean isAnimalAt(Vector2d position) {
        return objectAt(position) instanceof ArrayList;
    }

    private boolean cantGrowAt(Vector2d position, boolean inJungle){
        if (inJungle){
            if (!positionInJungle(position)) return true;
        }
        else {
            if (positionInJungle(position)) return true;
        }
        return !isFree(position);
    }

////////////////////////////////////////////////////////////////////////////////////////////////

//Metody z observera

    @Override
    public void changedPosition(Animal animal, Vector2d oldPosition) {
        Animals.removeAnimal(oldPosition, animal);

        if ( positionInJungle(oldPosition) && !positionInJungle(animal.getPosition()) ){  //przeszliśmy z dżungli na step
            if (isFree(oldPosition)) jungle.takenFields--;
            if (isFree(animal.getPosition())) takenFields++;
        }
        else if ( !positionInJungle(oldPosition) && positionInJungle(animal.getPosition()) ){ //przeszliśmy ze stepu do dżungli
            if (isFree(oldPosition)) takenFields--;
            if (isFree(animal.getPosition())) jungle.takenFields++;
        }
        else {
            if ( positionInJungle(animal.getPosition()) ) {
                if (isFree(oldPosition)) jungle.takenFields--;
                if (isFree(animal.getPosition())) jungle.takenFields++;
            }
            if ( !positionInJungle(animal.getPosition()) ) {
                if (isFree(oldPosition)) takenFields--;
                if (isFree(animal.getPosition())) takenFields++;
            }
        }

        Animals.addAnimal(animal);

    }

    @Override
    public void addedElement(AbstractWorldMapElement element){
        Vector2d position = element.getPosition();
        if ( positionInJungle(position) ){
            if ( isFree(position) ) jungle.takenFields++;
        }
        else {
            if ( isFree(position) ) takenFields++;
        }

        if (element instanceof Animal) {
            Animals.addAnimal((Animal) element);
            mainPanel.getStats().addedElement(element);
        }
        else {
            if ( positionInJungle(position) ){
                jungleGrassNumber++;
            }
            GrassMap.put(position, (Grass) element);
        }

    }

    @Override
    public void removedElement(AbstractWorldMapElement element){
        Vector2d position = element.getPosition();
        if (element instanceof Animal) {
            Animals.removeAnimal(position, (Animal) element);
            if ( positionInJungle(position) ){
                if ( isFree(position) ) jungle.takenFields--;
            }
            else {
                if ( isFree(position) ) takenFields--;
            }
            mainPanel.getStats().removedElement(element);
        }
        else {
            GrassMap.remove(position);
        }

    }

    @Override
    public void energyChanged(Animal animal, int change){
        mainPanel.getStats().energyChanged(animal, change);
    }

///////////////////////////////////////////////////////////////////////////////////

//metody do pośredniego kontatku między engine a wizualizacją

    public void repaint(){
        if (mainPanel.getMap() instanceof MapPanel) ((MapPanel) mainPanel.getMap()).repaint();
    }

    public void highLight(Vector2d position) {
        mainPanel.getMap().highLight(position.x, position.y);
    }

    public void removeHighLight(Vector2d position) {
        mainPanel.getMap().removeHighLight(position.x, position.y);
    }

    public void updateStats(){
        mainPanel.getStats().allUpdate(this);
    }

    public void terminated(){
        mainPanel.terminated();
    }

}
