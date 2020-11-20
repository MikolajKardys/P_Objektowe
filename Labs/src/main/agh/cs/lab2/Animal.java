package agh.cs.lab2;

import java.util.ArrayList;
import java.util.List;

public class Animal extends AbstractWorldMapElement {
    private MapDirection direction;
    private List<IPositionChangeObserver> observers = new ArrayList<>();
    private final IWorldMap map;
    public Animal(IWorldMap map){
        this(map, new Vector2d(2, 2));
    }
    public Animal(IWorldMap map, Vector2d initialPosition){
        super(initialPosition);
        this.map = map;
        this.direction = MapDirection.NORTH;
    }
    public String toString(){
        return this.direction.toString();
    }

    private void move_forward(boolean backward) { //Przesuwamy się w odpowiednim kierunku
        Vector2d finalPosition;
        if (backward) {
            finalPosition = this.position.add(this.direction.toUnitVector().opposite());
        }
        else {
            finalPosition = this.position.add(this.direction.toUnitVector());
        }
        if ( this.map.canMoveTo(finalPosition) ) {
            this.position = finalPosition;
        }
    }

    public void move(MoveDirection direction) {
        Vector2d oldPosition = this.getPosition();
        switch (direction) {
            case RIGHT:
                this.direction = this.direction.next();
                break;
            case LEFT:
                this.direction = this.direction.previous();
                break;
            case FORWARD:
                move_forward(false);
                break;
            case BACKWARD:
                move_forward(true);
                break;
        }
        this.positionChanged(oldPosition, this.getPosition());
    }

    public void addObserver(IPositionChangeObserver observer){
        observers.add(observer);
    }

    public void removeObserver(IPositionChangeObserver observer){
        observers.remove(observer);
    }

    private void positionChanged(Vector2d oldPosition, Vector2d newPosition){
        for (IPositionChangeObserver observer : observers){
            observer.positionChanged(oldPosition, newPosition);
        }
    }
}
