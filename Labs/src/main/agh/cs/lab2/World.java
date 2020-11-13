package agh.cs.lab2;

public class World {
    public static void main (String [] args){
        MoveDirection[] directions = new OptionsParser().parse(args);
        IWorldMap field = new RectangularMap(6, 6);
        Vector2d[] positions = { new Vector2d(2,2), new Vector2d(3,4) };
        System.out.print(field.toString());
        IEngine engine = new SimulationEngine(directions, field, positions);
        engine.run();
        System.out.print(field.toString());
    }
}
