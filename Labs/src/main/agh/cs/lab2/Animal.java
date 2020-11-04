package agh.cs.lab2;
public class Animal {
    private MapDirection direction = MapDirection.NORTH;
    private Vector2d position = new Vector2d(2, 2);

    public String to_String(){
        return "(" + this.position.x + "," + this.position.y + "), " + this.direction;
    }

    private void move_forward() { //Przesuwamy się w odpowiednim kierunku
        Vector2d movement = this.direction.toUnitVector();
        if ( this.position.add(movement).precedes(new Vector2d(4, 4)) &&
            this.position.add(movement).follows(new Vector2d(0, 0))) {
            this.position = this.position.add(movement);
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
                this.move(MoveDirection.RIGHT);     //Obróć się o 180 stopni
                this.move(MoveDirection.RIGHT);
                move_forward();
                this.move(MoveDirection.RIGHT);     //Obróć się z powrotem
                this.move(MoveDirection.RIGHT);
                break;
        }

    }
}
