package agh.cs.lab2;

public class RectangularMap extends AbstractWorldMap {
    final private Vector2d upperCorn;
    public RectangularMap (int width, int height){
        this.upperCorn = new Vector2d(width, height);
    }

    @Override
    protected Vector2d getUpperCorn(){
        return this.upperCorn;
    }
    @Override
    public boolean canMoveTo(Vector2d position) {
        if (position.follows(lowerCorn) && position.precedes(upperCorn)){
            return !(isOccupied(position));
        }
        return false;
    }

}
