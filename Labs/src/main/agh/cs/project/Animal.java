package agh.cs.project;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Animal extends AbstractWorldMapElement {
    private final List<IChangeObserver> observers = new ArrayList<>();


    private MapDirection direction;

    private final GrassField map;
    public final Genome genome;
    private final float healthScale = 100;

    public int energy;
    private final int moveEnergy;

    public Animal(GrassField map, Vector2d newPosition, MapDirection direction, int energy, int moveEnergy) {
        this(map, newPosition, direction, energy, moveEnergy, new int []{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 2, 3, 4, 5, 6, 7});
    }

    public Animal(GrassField map, Vector2d initialPosition, MapDirection direction, int energy, int moveEnergy, int [] genome) {
        super(initialPosition);
        this.map = map;
        this.direction = direction;
        this.energy = energy;
        this.moveEnergy = moveEnergy;
        this.genome = new Genome(genome);

        this.addObserver(map);
        this.alertObserversAdded();
    }

    public EventType newMove(){
        int randomTurn = this.genome.getRandomGene();
        MoveDirection turn = MoveDirection.valueOf("TURN_" + randomTurn);

        Vector2d oldPosition = this.getPosition();
        this.direction = this.direction.turn(turn);
        Vector2d finalPosition = this.position.add(this.direction.toUnitVector());

        finalPosition = map.convertToMovable(finalPosition);
        this.position = finalPosition;

        this.alertObserversMoved(oldPosition);

        this.energy -= this.moveEnergy;

        if (this.map.isGrassAt(this.position)){
            return EventType.Eating;
        }
        return EventType.None;
    }

    public void kill(){
        System.out.println("Died with health: " + this.energy);
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
        if (this.energy >= 100){
            return new Color(0, 255, 0);
        }
        if (this.energy >= 50){
            float healthPart = (float)(this.energy - 50) / (healthScale / 2);
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
