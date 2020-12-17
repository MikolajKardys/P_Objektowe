package agh.cs.project;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Animal extends AbstractWorldMapElement {
    private final List<IChangeObserver> observers = new ArrayList<>();

    private MapDirection direction;
    private final GrassField map;
    private final Genome genome;
    public int startEnergy;

    public int energy;
    private final int moveEnergy;

    public Animal(GrassField map, Vector2d initialPosition, int energy, int moveEnergy) {
        this(map, initialPosition, energy, moveEnergy, new Genome());
        this.startEnergy = energy;
    }
    public Animal(Animal aParent, Animal bParent){
        this(aParent.map, aParent.position, aParent.energy / 4 + bParent.energy / 4, aParent.moveEnergy, new Genome(aParent.genome, bParent.genome));

        aParent.energy -= aParent.energy / 4;
        bParent.energy -= bParent.energy / 4;
        this.startEnergy = aParent.startEnergy;
    }

    public Animal(GrassField map, Vector2d initialPosition, int energy, int moveEnergy, Genome genome) {
        super(initialPosition);
        this.map = map;
        this.direction = MapDirection.newMapDirection((int)(Math.random() * 8));
        this.energy = energy;
        this.moveEnergy = moveEnergy;
        this.genome = genome;
        this.addObserver(map);
        this.alertObserversAdded();
    }

    public ArrayList<EventType> newMove(){
        ArrayList<EventType> possibleEvents = new ArrayList<>();

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

        this.energy -= this.moveEnergy;

        if (this.map.isGrassAt(this.position)){      //try to eat
            possibleEvents.add(EventType.Eating);
        }

        return possibleEvents;
    }

    public void kill(){
        this.alertObserversDied();
    }

//////////////////////////////////////////////////////////////////////////////////////

    public void addObserver(IChangeObserver observer) {
        observers.add(observer);
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

    public void alertObserversChangedEnergy() {
        for (IChangeObserver observer : observers) {
            observer.changedEnergy(this);
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

    public String toString() {
        return this.direction.toString();
    }

///////////////////////////////////////////////////////////////////////////////////////////////////////////

}
