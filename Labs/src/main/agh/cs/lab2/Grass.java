package agh.cs.lab2;

public class Grass extends  AbstractWorldMapElement{
    protected Grass(Vector2d position) {
        super(position);
    }
    public String toString (){
        return "*";
    }
}
