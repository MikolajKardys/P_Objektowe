package agh.cs.lab2;

public class World {
    public static void main (String [] args){
        IWorldMap map = new RectangularMap(3, 3);                                    //Mapa
        MoveDirection[] directions = new OptionsParser().parse(new String [] {"f", "b", "r", "r", "f", "f", "f", "f", "f", "f", "r", "l", "f", "f"});//Argumenty
        Vector2d [] positions = {new Vector2d(0,0), new Vector2d(0,3) };                //Zwierzęta
        IEngine engine = new SimulationEngine(directions, map, positions);
        engine.run();
    }
}
