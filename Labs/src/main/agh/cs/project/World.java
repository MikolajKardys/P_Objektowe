package agh.cs.project;


public class World {
    public static void main (String [] args){
        GrassField field = new GrassField(10);
        // {0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 2, 2, 2, 2, 2, 2, 3, 3, 4, 4, 4, 4, 4, 4, 5, 5, 6, 6, 7, 7, 7, 7}
        int [] lionGen = new int [] {0};
        Animal lion = new Animal(field, new Vector2d(4, 4), MapDirection.Dir_6, lionGen);
        field.place(lion);
        System.out.print(field.toString());
        for (int i = 0; i < 10; i++){
            lion.newMove();
        }
        System.out.print(field.toString());
    }
}
