package agh.cs.lab2;

public class RectangularMap extends AbstractWorldMap {
    final private Vector2d upperCorn;

    public RectangularMap(int width, int height) {
        this.upperCorn = new Vector2d(width - 1, height - 1);   
    }

    protected Vector2d getUpperCorner() {      // na pewno?    //Wydaje mi się, że tak; RectangularMap ma ustalony rozmiar
        return this.upperCorn;
    }

    protected Vector2d getLowerCorner() {
        return new Vector2d(0, 0);
    }

    @Override
    public boolean place(Animal animal) {
        if (!canMoveTo(animal.getPosition())) {
            throw new IllegalArgumentException("Can't add Animal to position " + animal.getPosition().toString() + "; field already taken");
        }
        this.Animals.put(animal.getPosition(), animal);
        animal.addObserver(this);
        return true;
    }

    @Override
    public boolean canMoveTo(Vector2d position) {
        if (position.follows(getLowerCorner()) && position.precedes(getUpperCorner())) {
            return !(isOccupied(position));
        }
        return false;
    }

}
