package agh.cs.project;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Animal extends AbstractWorldMapElement {
    private final List<IPositionChangeObserver> observers = new ArrayList<>();


    private MapDirection direction;

    private final GrassField map;
    private final Genome genome;
    private final float healthScale = 100;

    public int health;
    private final int moveEnergy;

    public Animal(GrassField map, Vector2d newPosition, MapDirection direction, int health, int moveEnergy) {
        this(map, newPosition, direction, health, moveEnergy, new int []{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 2, 3, 4, 5, 6, 7});
    }

    public Animal(GrassField map, Vector2d initialPosition, MapDirection direction, int health, int moveEnergy, int [] genome) {
        super(initialPosition);
        this.map = map;
        this.direction = direction;
        this.health = health;
        this.moveEnergy = moveEnergy;
        this.genome = new Genome(genome);
    }


    public String toString() {
        return this.direction.toString();
    }

    public void newMove(){
        int randomTurn = this.genome.getRandomGene();
        MoveDirection turn = MoveDirection.valueOf("TURN_" + randomTurn);

        Vector2d oldPosition = this.getPosition();
        this.direction = this.direction.turn(turn);
        Vector2d finalPosition = this.position.add(this.direction.toUnitVector());


        finalPosition = map.convertToMovable(finalPosition);
        this.position = finalPosition;

        this.alertObserversMoved(oldPosition);

    }
    public void kill(){
        this.alertObserversDied();
    }

    public void addObserver(IPositionChangeObserver observer) {
        observers.add(observer);
    }

    public void removeObserver(IPositionChangeObserver observer) {
        observers.remove(observer);
    }

    private void alertObserversMoved(Vector2d oldPosition) {
        for (IPositionChangeObserver observer : observers) {
            observer.positionChanged(this, oldPosition);
        }
    }

    private void alertObserversDied() {
        for (IPositionChangeObserver observer : observers) {
            observer.removedElement(this, this.position);
        }
    }

    public Color getHealthColor(){
        if (this.health >= 50){
            float healthPart = (float)(this.health - 50) / (healthScale / 2);
            return new Color((int)(255 * (1 - healthPart)), 255, 0);
        }
        float healthPart = (float)(this.health) / (healthScale / 2);
        return new Color(255, (int)(255 * healthPart), 0);
    }
}
