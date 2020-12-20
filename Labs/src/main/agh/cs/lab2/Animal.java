package agh.cs.lab2;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Animal extends AbstractWorldMapElement {
    private MapDirection direction;
    private final List<IPositionChangeObserver> observers = new ArrayList<>();
    private final IWorldMap map;
    public final int [] genome;
    public Animal(IWorldMap map) {
        this(map, new Vector2d(2, 2), new int [] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0});
    }

    public Animal(IWorldMap map, Vector2d initialPosition) {
        this(map, initialPosition, new int [] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0});
    }
    public Animal(IWorldMap map, Vector2d initialPosition, int [] genome) {
        super(initialPosition);
        this.map = map;
        this.genome = genome;
        this.direction = MapDirection.Dir_0;
    }

    public String toString() {
        return this.direction.toString();
    }

    private void moveForward(boolean backward) { //Przesuwamy się w odpowiednim kierunku   
        Vector2d finalPosition;
        if (backward) {
            finalPosition = this.position.add(this.direction.toUnitVector().opposite());
        } else {
            finalPosition = this.position.add(this.direction.toUnitVector());
        }
        if (this.map.canMoveTo(finalPosition)) {
            this.position = finalPosition;
        }
    }

    public void move(MoveDirection turn) {      //Stare poruszanie się
        Vector2d oldPosition = this.getPosition();
        switch (turn) {
            case TURN_0:
                moveForward(false);
                break;
            case TURN_4:
                moveForward(true);
                break;
            default:
                this.direction = this.direction.turn(turn);
                break;
        }
        this.alertObservers(oldPosition, this.getPosition());
    }

    public void newMove(){
        int randomTurn = this.genome[new Random().nextInt(32)];
        MoveDirection turn = MoveDirection.valueOf("TURN_" + randomTurn);

        Vector2d oldPosition = this.getPosition();
        this.direction = this.direction.turn(turn);
        Vector2d finalPosition = this.position.add(this.direction.toUnitVector());

        if (this.map.canMoveTo(finalPosition)) {
            this.position = finalPosition;
            this.alertObservers(oldPosition, this.getPosition());
        }
    }

    public void addObserver(IPositionChangeObserver observer) {
        observers.add(observer);
    }

    public void removeObserver(IPositionChangeObserver observer) {
        observers.remove(observer);
    }

    private void alertObservers(Vector2d oldPosition, Vector2d newPosition) {
        for (IPositionChangeObserver observer : observers) {
            observer.positionChanged(oldPosition, newPosition);
        }
    }


}
