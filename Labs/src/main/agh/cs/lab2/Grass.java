package agh.cs.lab2;

public class Grass extends  AbstractWorldMapElement{
    public Grass (Vector2d position){
        this.position = position;
    }
    public String toString (){
        return "*";
    }
}
