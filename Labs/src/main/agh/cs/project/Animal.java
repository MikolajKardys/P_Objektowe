package agh.cs.project;

import java.util.ArrayList;
import java.util.List;

public class Animal extends AbstractWorldMapElement {
    private MapDirection direction;
    private final List<IPositionChangeObserver> observers = new ArrayList<>();
    private final GrassField map;
    public final Genome genome;
    public Animal(GrassField map) {
        this(map, new Vector2d(2, 2), MapDirection.Dir_0, new int [] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 2, 3, 4, 5, 6, 7});
    }

    public Animal(GrassField map, Vector2d initialPosition) {
        this(map, initialPosition, MapDirection.Dir_0,  new int [] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 2, 3, 4, 5, 6, 7});
    }
    public Animal(GrassField map, Vector2d initialPosition, MapDirection direction, int [] genome) {
        super(initialPosition);
        this.map = map;
        this.genome = new Genome(genome);
        this.direction = direction;
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
        this.tryToHeal();

        this.alertObserversMoved(oldPosition, this.getPosition());

    }

    public void tryToHeal(){
        if (map.tryEatGrass(this)){
            System.out.println("I ate!");
        }
    }



    public void addObserver(IPositionChangeObserver observer) {
        observers.add(observer);
    }

    public void removeObserver(IPositionChangeObserver observer) {
        observers.remove(observer);
    }

    private void alertObserversMoved(Vector2d oldPosition, Vector2d newPosition) {
        for (IPositionChangeObserver observer : observers) {
            observer.positionChanged(oldPosition, newPosition);
        }
    }

    private void alertObserversDied(Vector2d position) {
        for (IPositionChangeObserver observer : observers) {
            observer.removedElement(position);
        }
    }
}
