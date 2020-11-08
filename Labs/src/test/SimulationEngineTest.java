import agh.cs.lab2.*;
import org.junit.Test;
import static org.junit.Assert.*;

public class SimulationEngineTest {
    @Test
    public void Animal_Place_Test_1(){
        IWorldMap map = new RectangularMap(10, 5);
        Animal lion = new Animal(map);
        map.place(lion);
        assertTrue(map.isOccupied(new Vector2d(2, 2)));
        assertFalse(map.isOccupied(new Vector2d(4, 5)));
    }
    @Test
    public void Animal_Place_Test_2(){
        IWorldMap map = new RectangularMap(3, 4);
        Animal lion = new Animal(map, new Vector2d(1, 3));
        map.place(lion);
        assertTrue(map.isOccupied(new Vector2d(1, 3)));
        assertFalse(map.isOccupied(new Vector2d(0, 0)));
    }
    @Test
    public void Animal_Place_Test_3(){
        IWorldMap map = new RectangularMap(8, 10);
        Animal lion = new Animal(map, new Vector2d(4, 5));
        map.place(lion);
        assertTrue(map.isOccupied(new Vector2d(4, 5)));
        assertFalse(map.isOccupied(new Vector2d(4, 2)));
    }

    @Test
    public void Animal_CanMoveTo_Test_1(){
        IWorldMap map = new RectangularMap(10, 5);
        Animal lion = new Animal(map);
        map.place(lion);

        assertFalse(map.canMoveTo(new Vector2d(12, 3)));
        assertTrue(map.canMoveTo(new Vector2d(3, 1)));
        assertFalse(map.canMoveTo(new Vector2d(2, 2)));
    }
    @Test
    public void Animal_CanMoveTo_Test_2(){
        IWorldMap map = new RectangularMap(8, 9);
        Animal lion = new Animal(map, new Vector2d(5, 5));
        map.place(lion);

        assertFalse(map.canMoveTo(new Vector2d(10, 4)));
        assertTrue(map.canMoveTo(new Vector2d(3, 1)));
        assertFalse(map.canMoveTo(new Vector2d(5, 5)));
    }
    @Test
    public void Animal_CanMoveTo_Test_3(){
        IWorldMap map = new RectangularMap(3, 3);
        Animal lion = new Animal(map,  new Vector2d(0, 0));
        map.place(lion);

        assertFalse(map.canMoveTo(new Vector2d(2, 4)));
        assertTrue(map.canMoveTo(new Vector2d(1, 1)));
        assertFalse(map.canMoveTo(new Vector2d(0, 0)));
    }

    @Test
    public void Complete_Test_1() {
        IWorldMap map = new RectangularMap(10, 5);                                    //Mapa
        MoveDirection[] directions = new OptionsParser().parse(new String [] {"f", "b", "r", "l", "f", "f", "r", "r", "f", "f", "f", "f", "f", "f", "f", "f"});//Argumenty
        Vector2d [] positions = {new Vector2d(2,2), new Vector2d(3,4) };                //Zwierzęta
        IEngine engine = new SimulationEngine(directions, map, positions);
        engine.run();
        String expected = " y\\x  0 1 2 3 4 5 6 7 8 910" + System.lineSeparator() +
                "  6: -----------------------" + System.lineSeparator() +
                "  5: | | | |^| | | | | | | |" + System.lineSeparator() +
                "  4: | | | | | | | | | | | |" + System.lineSeparator() +
                "  3: | | | | | | | | | | | |" + System.lineSeparator() +
                "  2: | | | | | | | | | | | |" + System.lineSeparator() +
                "  1: | | | | | | | | | | | |" + System.lineSeparator() +
                "  0: | | |v| | | | | | | | |" + System.lineSeparator() +
                " -1: -----------------------" + System.lineSeparator();
        assertEquals(expected, map.toString());
    }
    @Test
    public void Complete_Test_2() {
        IWorldMap map = new RectangularMap(5, 5);                                    //Mapa
        MoveDirection[] directions = new OptionsParser().parse(new String [] {"r", "l", "f", "f", "f", "f", "f", "f", "l", "l", "f", "f", "f", "f"});//Argumenty
        Vector2d [] positions = {new Vector2d(0,0), new Vector2d(5,5) };                //Zwierzęta
        IEngine engine = new SimulationEngine(directions, map, positions);
        engine.run();
        String expected = " y\\x  0 1 2 3 4 5" + System.lineSeparator() +
                "  6: -------------" + System.lineSeparator() +
                "  5: | | | | | | |" + System.lineSeparator() +
                "  4: | | | | | | |" + System.lineSeparator() +
                "  3: | | |v| | | |" + System.lineSeparator() +
                "  2: | | | |^| | |" + System.lineSeparator() +
                "  1: | | | | | | |" + System.lineSeparator() +
                "  0: | | | | | | |" + System.lineSeparator() +
                " -1: -------------" + System.lineSeparator();
        assertEquals(expected, map.toString());
    }
    @Test
    public void Complete_Test_3() {
        IWorldMap map = new RectangularMap(3, 3);                                    //Mapa
        MoveDirection[] directions = new OptionsParser().parse(new String [] {"f", "b", "r", "r", "f", "f", "f", "f", "f", "f", "r", "l", "f", "f"});//Argumenty
        Vector2d [] positions = {new Vector2d(0,0), new Vector2d(0,3) };                //Zwierzęta
        IEngine engine = new SimulationEngine(directions, map, positions);
        engine.run();
        String expected = " y\\x  0 1 2 3" + System.lineSeparator() +
                "  4: ---------" + System.lineSeparator() +
                "  3: | | | |^|" + System.lineSeparator() +
                "  2: | | | | |" + System.lineSeparator() +
                "  1: | | | | |" + System.lineSeparator() +
                "  0: | | | |v|" + System.lineSeparator() +
                " -1: ---------" + System.lineSeparator();
        assertEquals(expected, map.toString());
    }
}
