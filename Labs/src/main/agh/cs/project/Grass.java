package agh.cs.project;

public class Grass extends AbstractWorldMapElement {
    protected Grass(Vector2d position) {
        super(position);
    }
    public String toString (){
        return "*";
    }
}
