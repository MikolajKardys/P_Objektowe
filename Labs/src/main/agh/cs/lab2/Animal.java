package agh.cs.lab2;

public class Animal {
    private MapDirection direction;
    private Vector2d position;
    private IWorldMap map;  // to może być finalne

    public Animal(IWorldMap map) {
        this(map, new Vector2d(2, 2));
    }

    public Animal(IWorldMap map, Vector2d initialPosition) {
        this.map = map;
        this.position = initialPosition;
        this.direction = MapDirection.NORTH;
    }

    public Vector2d getPosition() {
        return this.position;
    }

    public String toString() {
        return this.direction.toString();
    }

    private void move_forward() { //Przesuwamy się w odpowiednim kierunku
        Vector2d movement = this.direction.toUnitVector();
        if (this.map.canMoveTo(this.position.add(movement))) {
            this.position = this.position.add(movement);    // powtórzone obliczenie
        }
    }

    public void move(MoveDirection direction) {
        switch (direction) {
            case RIGHT:
                this.direction = this.direction.next();
                break;
            case LEFT:
                this.direction = this.direction.previous();
                break;
            case FORWARD:
                move_forward();
                break;
            case BACKWARD:
                this.move(MoveDirection.RIGHT);     //Obróć się o 180 stopni    // a nie lepiej użyć opposite z wektora?
                this.move(MoveDirection.RIGHT);
                move_forward();
                this.move(MoveDirection.RIGHT);     //Obróć się z powrotem
                this.move(MoveDirection.RIGHT);
                break;
        }

    }
}
