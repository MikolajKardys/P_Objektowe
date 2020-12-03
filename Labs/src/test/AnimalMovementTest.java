import agh.cs.lab2.*;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
public class AnimalMovementTest {

    @Test
    public void All_good_no_1() {             //poprawne polecenia, bez możliwosci wyjścia poza mapę
        RectangularMap map = new RectangularMap(5, 5);
        Animal lion = new Animal(map);
        String [] movement = {"f", "backward", "left", "forward", "left", "backward"};
        String expected_position = "(1,3), v";
        MoveDirection[] arguments = OptionsParser.parse(movement);
        for (MoveDirection arg : arguments) {
            lion.move(arg);
        }
        String real_position = lion.getPosition().toString() + ", " + lion.toString();
        assertEquals(expected_position, real_position);
    }
    @Test
    public void All_good_no_2() {               //poprawne polecenia, bez możliwosci wyjścia poza mapę
        RectangularMap map = new RectangularMap(5, 5);
        Animal lion = new Animal(map);
        String [] movement = {"right", "forward", "forward", "left", "backward", "backward", "left"};
        String expected_position = "(4,0), <";
        MoveDirection[] arguments = OptionsParser.parse(movement);
        for (MoveDirection arg : arguments) {
            lion.move(arg);
        }
        String real_position = lion.getPosition().toString() + ", " + lion.toString();
        assertEquals(expected_position, real_position);
    }
    @Test
    public void All_good_no_3() {               //poprawne polecenia, bez możliwosci wyjścia poza mapę
        RectangularMap map = new RectangularMap(5, 5);
        Animal lion = new Animal(map);
        String [] movement = {"l", "forward", "f", "right", "backward", "b"};
        String expected_position = "(0,0), ^";
        MoveDirection[] arguments = OptionsParser.parse(movement);
        for (MoveDirection arg : arguments) {
            lion.move(arg);
        }
        String real_position = lion.getPosition().toString() + ", " + lion.toString();
        assertEquals(expected_position, real_position);
    }
    @Test
    public void All_good_no_4() {               //poprawne polecenia, bez możliwosci wyjścia poza mapę
        RectangularMap map = new RectangularMap(5, 5);
        Animal lion = new Animal(map);
        String [] movement = {"r", "forward", "left", "forward", "forward", "right", "b"};
        String expected_position = "(2,4), >";
        MoveDirection[] arguments = OptionsParser.parse(movement);
        for (MoveDirection arg : arguments) {
            lion.move(arg);
        }
        String real_position = lion.getPosition().toString() + ", " + lion.toString();
        assertEquals(expected_position, real_position);
    }
    @Test
    public void Border_test_no1() {               //poprawne polecenia, możliwość wyjścia poza mapę
        RectangularMap map = new RectangularMap(5, 5);
        Animal lion = new Animal(map);
        String [] movement = {"l", "forward", "f", "forward", "right", "b", "left", "left", "forward", "forward"};
        String expected_position = "(0,0), v";
        MoveDirection[] arguments = OptionsParser.parse(movement);
        for (MoveDirection arg : arguments) {
            lion.move(arg);
        }
        String real_position = lion.getPosition().toString() + ", " + lion.toString();
        assertEquals(expected_position, real_position);
    }
    @Test
    public void Border_test_no2() {               //poprawne polecenia, możliwość wyjścia poza mapę
        RectangularMap map = new RectangularMap(5, 5);
        Animal lion = new Animal(map);
        String [] movement = {"r", "forward", "f", "forward", "left", "f", "right", "right", "b", "backward"};
        String expected_position = "(4,4), v";
        MoveDirection[] arguments = OptionsParser.parse(movement);
        for (MoveDirection arg : arguments) {
            lion.move(arg);
        }
        String real_position = lion.getPosition().toString() + ", " + lion.toString();
        assertEquals(expected_position, real_position);
    }
    @Test
    public void Gibberish_command_test_no1() {               //niepoprawne polecenia
        try {
            RectangularMap map = new RectangularMap(5, 5);
            Animal lion = new Animal(map);
            String[] movement = {"r", "forwards", "l", "forasd", "rihj", "backward", "l", "lft", "b", "backward"};
            MoveDirection[] arguments = OptionsParser.parse(movement);
            for (MoveDirection arg : arguments) {
                lion.move(arg);
            }
        }
        catch (IllegalArgumentException ex){
            assertTrue(ex.getMessage().equals("forwards is not legal move specification; check 2. given argument"));
        }
    }
    @Test
    public void Gibberish_command_test_no2() {               //niepoprawne polecenia
        try {
            RectangularMap map = new RectangularMap(5, 5);
            Animal lion = new Animal(map);
            String[] movement = {"forwad", "backwar", "l", "right", "backward", "left", "forward", "fordard"};
            MoveDirection[] arguments = OptionsParser.parse(movement);
            for (MoveDirection arg : arguments) {
                lion.move(arg);
            }
        }
        catch (IllegalArgumentException ex){
            assertTrue(ex.getMessage().equals("forwad is not legal move specification; check 1. given argument"));
        }
    }
}
