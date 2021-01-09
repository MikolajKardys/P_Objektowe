package agh.cs.project.Sources; // nazwy pakietów raczej małymi literami

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Animal extends AbstractWorldMapElement {
    private final List<IChangeObserver> observers = new ArrayList<>();

    private MapDirection direction;
    private final GrassField map;
    private final int moveEnergy;

    private final Genome genome;
    private boolean isSelected;

    private int startEnergy;
    private int energy;
    private int lifeLength = 0;

    private final ArrayList<Animal> parents;
    private final ArrayList<Animal> children;

    public Animal(GrassField map, Vector2d initialPosition, int energy, int moveEnergy) {
        this(map, initialPosition, energy, moveEnergy, new Genome());
        startEnergy = energy;

        addObserver(map);
        alertObserversAdded();
    }

    public Animal(Animal aParent, Animal bParent){
        this(aParent.map, aParent.map.getSpawnPoint(aParent.position),
                aParent.energy / 4 + bParent.energy / 4,
                aParent.moveEnergy,
                new Genome(aParent.genome, bParent.genome));

        aParent.changeEnergy(-(aParent.energy / 4));
        bParent.changeEnergy(-(bParent.energy / 4));
        startEnergy = aParent.startEnergy;

        parents.add(aParent);
        parents.add(bParent);
        aParent.children.add(this);
        bParent.children.add(this);

        addObserver(map);

        if (aParent.observers.size() > 1){
            addObserver(aParent.observers.get(1));
        }
        else if (bParent.observers.size() > 1){
            addObserver(bParent.observers.get(1));
        }

        alertObserversAdded();
    }

    private Animal(GrassField map, Vector2d initialPosition, int energy, int moveEnergy, Genome genome) {
        super(initialPosition);
        this.map = map;
        direction = MapDirection.newMapDirection((int)(Math.random() * 8));
        this.energy = energy;
        this.moveEnergy = moveEnergy;
        this.genome = genome;

        children = new ArrayList<>();
        parents = new ArrayList<>();

        isSelected = false;
    }

/////////////////////////////////////////////////////////////////////////////////////

//Gettery

    public int getEnergy() {
        return energy;
    }

    public Genome getGenome(){
        return genome;
    }

    public void select(boolean doSelect){
        isSelected = doSelect;
    }
    public boolean isSelected(){
        return isSelected;
    }

    public boolean canBreed(){
        return energy > startEnergy / 2;
    }

    public int getLifeLength(){
        return lifeLength;
    }

    public int getChildNumber(){
        return children.size();
    }

    public int getParentNumber(){
        return parents.size();
    }

    public ArrayList<Animal> getParents(){
        return parents;
    }

///////////////////////////////////////////////////////////////////////////////////////

//Metody do obsługi stanu zwierzęcia w symulacji

    public ArrayList<EventType> move(){
        ArrayList<EventType> possibleEvents = new ArrayList<>();
        changeEnergy(-moveEnergy);

        int randomTurn = genome.getRandomGene();
        MoveDirection turn = MoveDirection.valueOf("TURN_" + randomTurn);

        Vector2d oldPosition = getPosition();
        direction = direction.turn(turn);
        Vector2d finalPosition = position.add(direction.toUnitVector());

        finalPosition = map.convertToMovable(finalPosition);
        if (map.isAnimalAt(finalPosition)){
            possibleEvents.add(EventType.Breading);
        }
        position = finalPosition;

        alertObserversMoved(oldPosition);

        if (map.isGrassAt(position)){      //try to eat
            possibleEvents.add(EventType.Eating);
        }

        lifeLength++;

        return possibleEvents;
    }

    public void changeEnergy(int change){
        energy += change;
        alertObserversEnergy(change);
    }

    public void kill(){
        for (Animal parent : parents){
            parent.children.remove(this);
        }
        for (Animal child : children){
            child.parents.remove(this);
        }
        alertObserversDied();
    }

//////////////////////////////////////////////////////////////////////////////////////

//Metody do obsługi observerów

    public void addObserver(IChangeObserver observer) {
        observers.add(observer);
    }

    public void removeObserver(IChangeObserver observer) {
        observers.remove(observer);
    }

    private void alertObserversMoved(Vector2d oldPosition) {
        for (IChangeObserver observer : observers) {
            observer.changedPosition(this, oldPosition);
        }
    }

    private void alertObserversAdded() {
        for (IChangeObserver observer : observers) {
            observer.addedElement(this);
        }
    }

    private void alertObserversDied() {
        for (IChangeObserver observer : observers) {
            observer.removedElement(this);
        }
    }

    private void alertObserversEnergy(int change) {
        for (IChangeObserver observer : observers) {
            observer.energyChanged(this, change);
        }
    }

////////////////////////////////////////////////////////////////////////////////////////////////////

//Merody do obsługi graficznej reprezentacji zwierzęcia

    public Color getHealthColor(){
        float healthScale = startEnergy;
        if (energy >= healthScale){
            return new Color(0, 255, 0);
        }
        if (energy <= 0){
            return new Color(255, 0, 0);
        }
        if (energy >= (healthScale / 2)){
            float healthPart = (energy - (healthScale / 2)) / (healthScale / 2);
            return new Color((int)(255 * (1 - healthPart)), 255, 0);
        }
        float healthPart = (float)(energy) / (healthScale / 2);
        return new Color(255, (int)(255 * healthPart), 0);
    }

    @Override
    public String toString(){
        return position.toString();
    }

    public void highLight(){
        map.highLight(position);
    }

    public void removeHighLight(){
        map.removeHighLight(position);
    }

///////////////////////////////////////////////////////////////////////////////////////////////////////////

    public void sortThisField(){
        ((AnimalSortedList) map.objectAt(position)).sort();
    }


}
