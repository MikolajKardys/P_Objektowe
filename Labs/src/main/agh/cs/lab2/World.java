package agh.cs.lab2;

public class World {
    public static void main (String [] args){
        try{
            GrassField field = new GrassField(10);
            Vector2d[] positions = {new Vector2d(2, 2), new Vector2d(3, 4)};
            System.out.print(field.toString());
            IEngine engine = new ProjectEngine(field, positions);
            engine.run();
            System.out.print(field.toString());
        }
        catch (IllegalArgumentException ex){
            throw ex;
        }
    }
}
