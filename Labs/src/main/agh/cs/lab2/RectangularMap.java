package agh.cs.lab2;

public class RectangularMap extends AbstractWorldMap {
    final private Vector2d upperCorn;

    public RectangularMap(int width, int height) {
        this.upperCorn = new Vector2d(width - 1, height - 1);   
    }

    @Override   // na pewno?
    protected Vector2d getUpperCorn() {
        return this.upperCorn;
    }

    protected Vector2d getLowerCorn() {
        return new Vector2d(0, 0);
    }

    @Override
    public boolean canMoveTo(Vector2d position) {
        if (position.follows(getLowerCorn()) && position.precedes(upperCorn)) {
            return !(isOccupied(position));
        }
        return false;
    }

}
