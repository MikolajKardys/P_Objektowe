package agh.cs.lab2;

public class World {
    public static void main (String [] args){
        try{
            GrassField field = new GrassField(10);

            int [] lionGen = new int [] {0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 2, 2, 2, 2, 2, 2, 3, 3, 4, 4, 4, 4, 4, 4, 5, 5, 6, 6, 7, 7, 7, 7};
            Animal lion = new Animal(field, new Vector2d(4, 4), lionGen);
            field.place(lion);
            System.out.print(field.toString());
            for (int i = 0; i < 10; i++){
                lion.newMove();
            }
            System.out.print(field.toString());



            /*
            Vector2d[] positions = {new Vector2d(2, 2), new Vector2d(3, 4)};
            System.out.print(field.toString());
            IEngine engine = new ProjectEngine(field, positions);
            engine.run();
            System.out.print(field.toString());*/
        }
        catch (IllegalArgumentException ex){
            throw ex;
        }
    }
}
