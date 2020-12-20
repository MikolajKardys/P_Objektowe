package agh.cs.project.Sources;

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
        this.startEnergy = energy;

        this.addObserver(map);
        this.alertObserversAdded();
    }

    public Animal(Animal aParent, Animal bParent){
        this(aParent.map, aParent.map.getSpawnPoint(aParent.position),
                aParent.energy / 4 + bParent.energy / 4,
                aParent.moveEnergy,
                new Genome(aParent.genome, bParent.genome));

        aParent.changeEnergy(-(aParent.energy / 4));
        bParent.changeEnergy(-(bParent.energy / 4));
        this.startEnergy = aParent.startEnergy;

        this.parents.add(aParent);
        this.parents.add(bParent);
        aParent.children.add(this);
        bParent.children.add(this);

        this.addObserver(map);

        if (aParent.observers.size() > 1){
            this.addObserver(aParent.observers.get(1));
        }
        else if (bParent.observers.size() > 1){
            this.addObserver(bParent.observers.get(1));
        }

        this.alertObserversAdded();
    }

    private Animal(GrassField map, Vector2d initialPosition, int energy, int moveEnergy, Genome genome) {
        super(initialPosition);
        this.map = map;
        this.direction = MapDirection.newMapDirection((int)(Math.random() * 8));
        this.energy = energy;
        this.moveEnergy = moveEnergy;
        this.genome = genome;

        this.children = new ArrayList<>();
        this.parents = new ArrayList<>();

        this.isSelected = false;
    }

    public int getEnergy() {
        return energy;
    }

    public Genome getGenome(){
        return genome;
    }

    public void select(boolean doSelect){
        this.isSelected = doSelect;
    }
    public boolean isSelected(){
        return isSelected;
    }

    public boolean canBreed(){
        return this.energy > this.startEnergy / 2;
    }

    public int getLifeLength(){
        return this.lifeLength;
    }

    public int getChildNumber(){
        return this.children.size();
    }

    public int getParentNumber(){
        return this.parents.size();
    }

    public ArrayList<Animal> getParents(){
        return this.parents;
    }

    public ArrayList<EventType> newMove(){
        ArrayList<EventType> possibleEvents = new ArrayList<>();
        this.changeEnergy(-moveEnergy);

        int randomTurn = this.genome.getRandomGene();
        MoveDirection turn = MoveDirection.valueOf("TURN_" + randomTurn);

        Vector2d oldPosition = this.getPosition();
        this.direction = this.direction.turn(turn);
        Vector2d finalPosition = this.position.add(this.direction.toUnitVector());

        finalPosition = map.convertToMovable(finalPosition);
        if (this.map.isAnimalAt(finalPosition)){
            possibleEvents.add(EventType.Breading);
        }
        this.position = finalPosition;

        this.alertObserversMoved(oldPosition);

        if (this.map.isGrassAt(this.position)){      //try to eat
            possibleEvents.add(EventType.Eating);
        }

        lifeLength++;

        return possibleEvents;
    }

    public void kill(){
        for (Animal parent : parents){
            parent.children.remove(this);
        }
        for (Animal child : children){
            child.parents.remove(this);
        }
        this.alertObserversDied();
    }

//////////////////////////////////////////////////////////////////////////////////////

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

    public Color getHealthColor(){
        float healthScale = startEnergy;
        if (this.energy >= healthScale){
            return new Color(0, 255, 0);
        }
        if (this.energy >= (healthScale / 2)){
            float healthPart = (this.energy - (healthScale / 2)) / (healthScale / 2);
            return new Color((int)(255 * (1 - healthPart)), 255, 0);
        }
        float healthPart = (float)(this.energy) / (healthScale / 2);
        return new Color(255, (int)(255 * healthPart), 0);
    }

    @Override
    public String toString(){
        return this.position.toString();
    }

///////////////////////////////////////////////////////////////////////////////////////////////////////////

    public void sortThisField(){
        ((AnimalSortedList) this.map.objectAt(this.position)).sort();
    }

    public void changeEnergy(int change){
        this.energy += change;
        this.alertObserversEnergy(change);
    }

    public void highLight(){
        this.map.highLight(this.position);
    }

    public void removeHighLight(){
        this.map.removeHighLight(this.position);
    }

}
