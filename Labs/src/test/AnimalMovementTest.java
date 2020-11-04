import agh.cs.lab2.*;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
public class AnimalMovementTest {

    @Test
    public void All_good_no_1() {             //poprawne polecenia, bez możliwosci wyjścia poza mapę
        RectangularMap map = new RectangularMap(4, 4);
        Animal lion = new Animal(map);
        String [] movement = {"f", "backward", "left", "forward", "left", "backward"};
        String expected_position = "(1,3), S";
        MoveDirection[] arguments = OptionParser.parse(movement);
        for (MoveDirection arg : arguments) {
            lion.move(arg);
        }
        String real_position = lion.getPosition().toString() + ", " + lion.to_String();
        assertEquals(expected_position, real_position);
    }
    @Test
    public void All_good_no_2() {               //poprawne polecenia, bez możliwosci wyjścia poza mapę
        RectangularMap map = new RectangularMap(4, 4);
        Animal lion = new Animal(map);
        String [] movement = {"right", "forward", "forward", "left", "backward", "backward", "left"};
        String expected_position = "(4,0), W";
        MoveDirection[] arguments = OptionParser.parse(movement);
        for (MoveDirection arg : arguments) {
            lion.move(arg);
        }
        String real_position = lion.getPosition().toString() + ", " + lion.to_String();
        assertEquals(expected_position, real_position);
    }
    @Test
    public void All_good_no_3() {               //poprawne polecenia, bez możliwosci wyjścia poza mapę
        RectangularMap map = new RectangularMap(4, 4);
        Animal lion = new Animal(map);
        String [] movement = {"l", "forward", "f", "right", "backward", "b"};
        String expected_position = "(0,0), N";
        MoveDirection[] arguments = OptionParser.parse(movement);
        for (MoveDirection arg : arguments) {
            lion.move(arg);
        }
        String real_position = lion.getPosition().toString() + ", " + lion.to_String();
        assertEquals(expected_position, real_position);
    }
    @Test
    public void All_good_no_4() {               //poprawne polecenia, bez możliwosci wyjścia poza mapę
        RectangularMap map = new RectangularMap(4, 4);
        Animal lion = new Animal(map);
        String [] movement = {"r", "forward", "left", "forward", "forward", "right", "b"};
        String expected_position = "(2,4), E";
        MoveDirection[] arguments = OptionParser.parse(movement);
        for (MoveDirection arg : arguments) {
            lion.move(arg);
        }
        String real_position = lion.getPosition().toString() + ", " + lion.to_String();
        assertEquals(expected_position, real_position);
    }
    @Test
    public void Border_test_no1() {               //poprawne polecenia, możliwość wyjścia poza mapę
        RectangularMap map = new RectangularMap(4, 4);
        Animal lion = new Animal(map);
        String [] movement = {"l", "forward", "f", "forward", "right", "b", "left", "left", "forward", "forward"};
        String expected_position = "(0,0), S";
        MoveDirection[] arguments = OptionParser.parse(movement);
        for (MoveDirection arg : arguments) {
            lion.move(arg);
        }
        String real_position = lion.getPosition().toString() + ", " + lion.to_String();
        assertEquals(expected_position, real_position);
    }
    @Test
    public void Border_test_no2() {               //poprawne polecenia, możliwość wyjścia poza mapę
        RectangularMap map = new RectangularMap(4, 4);
        Animal lion = new Animal(map);
        String [] movement = {"r", "forward", "f", "forward", "left", "f", "right", "right", "b", "backward"};
        String expected_position = "(4,4), S";
        MoveDirection[] arguments = OptionParser.parse(movement);
        for (MoveDirection arg : arguments) {
            lion.move(arg);
        }
        String real_position = lion.getPosition().toString() + ", " + lion.to_String();
        assertEquals(expected_position, real_position);
    }
    @Test
    public void Gibberish_command_test_no1() {               //niepoprawne polecenia
        RectangularMap map = new RectangularMap(4, 4);
        Animal lion = new Animal(map);
        String [] movement = {"r", "forwards", "l", "forasd", "rihj", "backward", "l", "lft", "b", "backward"};
        String expected_position = "(4,1), W";
        MoveDirection[] arguments = OptionParser.parse(movement);
        for (MoveDirection arg : arguments) {
            lion.move(arg);
        }
        String real_position = lion.getPosition().toString() + ", " + lion.to_String();
        assertEquals(expected_position, real_position);
    }
    @Test
    public void Gibberish_command_test_no2() {               //niepoprawne polecenia
        RectangularMap map = new RectangularMap(4, 4);
        Animal lion = new Animal(map);
        String [] movement = {"forwad", "backwar", "l", "right", "backward", "left", "forward", "fordard"};
        String expected_position = "(1,1), W";
        MoveDirection[] arguments = OptionParser.parse(movement);
        for (MoveDirection arg : arguments) {
            lion.move(arg);
        }
        String real_position = lion.getPosition().toString() + ", " + lion.to_String();
        assertEquals(expected_position, real_position);
    }
}
