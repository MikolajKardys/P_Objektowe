package agh.cs.lab2;

public class World {
    public static void main (String [] args){
        RectangularMap map = new RectangularMap(4, 2);
        System.out.print(map.toString());
        Animal doggo = new Animal(map);
        String doggo_where = doggo.getPosition().toString() + ", " + doggo.to_String();
        System.out.print(doggo_where);
    }
}
