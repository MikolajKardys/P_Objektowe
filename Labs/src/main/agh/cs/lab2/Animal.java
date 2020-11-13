package agh.cs.lab2;
public class Animal extends AbstractWorldMapElement {
    private MapDirection direction;
    private final IWorldMap map;
    public Animal(IWorldMap map){
        this(map, new Vector2d(2, 2));
    }
    public Animal(IWorldMap map, Vector2d initialPosition){
        this.map = map;
        this.position = initialPosition;
        this.direction = MapDirection.NORTH;
    }
    public String toString(){
        return this.direction.toString();
    }

    private void move_forward(boolean backward) { //Przesuwamy się w odpowiednim kierunku
        Vector2d FinalPosition;
        if (backward) {
            FinalPosition = this.position.add(this.direction.toUnitVector().opposite());
        }
        else {
            FinalPosition = this.position.add(this.direction.toUnitVector());
        }
        if ( this.map.canMoveTo(FinalPosition) ) {
            this.position = FinalPosition;
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
                move_forward(false);
                break;
            case BACKWARD:
                move_forward(true);
                break;
        }

    }
}
