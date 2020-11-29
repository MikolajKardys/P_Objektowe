package agh.cs.lab2;

public class World {
    public static void main (String [] args){
        try{
            MoveDirection[] directions = new OptionsParser().parse(args);
            IWorldMap field = new GrassField(10);
            Vector2d[] positions = {new Vector2d(2, 2), new Vector2d(3, 4), new Vector2d(2, 2)};
            System.out.print(field.toString());
            IEngine engine = new SimulationEngine(directions, field, positions);
            engine.run();
            System.out.print(field.toString());
        }
        catch (IllegalArgumentException ex){
            throw ex;
        }
    }
}
