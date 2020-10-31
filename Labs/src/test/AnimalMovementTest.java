import agh.cs.lab2.*;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
public class AnimalMovementTest {
    @Test
    public void All_good_no_1() {             //poprawne polecenia, bez możliwosci wyjścia poza mapę
        Animal lion = new Animal();
        String [] movement = {"f", "backward", "left", "forward", "left", "backward"};
        String expected_position = "(1,3), Południe";
        MoveDirection[] arguments = OptionParser.parse(movement);
        for (MoveDirection arg : arguments) {
            lion.move(arg);
        }
        assertEquals(expected_position, lion.to_string());
    }
    @Test
    public void All_good_no_2() {               //poprawne polecenia, bez możliwosci wyjścia poza mapę
        Animal lion = new Animal();
        String [] movement = {"right", "forward", "forward", "left", "backward", "backward", "left"};
        String expected_position = "(4,0), Zachód";
        MoveDirection[] arguments = OptionParser.parse(movement);
        for (MoveDirection arg : arguments) {
            lion.move(arg);
        }
        assertEquals(expected_position, lion.to_string());
    }
    @Test
    public void All_good_no_3() {               //poprawne polecenia, bez możliwosci wyjścia poza mapę
        Animal lion = new Animal();
        String [] movement = {"l", "forward", "f", "right", "backward", "b"};
        String expected_position = "(0,0), Północ";
        MoveDirection[] arguments = OptionParser.parse(movement);
        for (MoveDirection arg : arguments) {
            lion.move(arg);
        }
        assertEquals(expected_position, lion.to_string());
    }
    @Test
    public void All_good_no_4() {               //poprawne polecenia, bez możliwosci wyjścia poza mapę
        Animal lion = new Animal();
        String [] movement = {"r", "forward", "left", "forward", "forward", "right", "b"};
        String expected_position = "(2,4), Wschód";
        MoveDirection[] arguments = OptionParser.parse(movement);
        for (MoveDirection arg : arguments) {
            lion.move(arg);
        }
        assertEquals(expected_position, lion.to_string());
    }
    @Test
    public void Border_test_no1() {               //poprawne polecenia, możliwość wyjścia poza mapę
        Animal lion = new Animal();
        String [] movement = {"l", "forward", "f", "forward", "right", "b", "left", "left", "forward", "forward"};
        String expected_position = "(0,0), Południe";
        MoveDirection[] arguments = OptionParser.parse(movement);
        for (MoveDirection arg : arguments) {
            lion.move(arg);
        }
        assertEquals(expected_position, lion.to_string());
    }
    @Test
    public void Border_test_no2() {               //poprawne polecenia, możliwość wyjścia poza mapę
        Animal lion = new Animal();
        String [] movement = {"r", "forward", "f", "forward", "left", "f", "right", "right", "b", "backward"};
        String expected_position = "(4,4), Południe";
        MoveDirection[] arguments = OptionParser.parse(movement);
        for (MoveDirection arg : arguments) {
            lion.move(arg);
        }
        assertEquals(expected_position, lion.to_string());
    }
    @Test
    public void Gibberish_command_test_no1() {               //niepoprawne polecenia
        Animal lion = new Animal();
        String [] movement = {"r", "forwards", "l", "forasd", "rihj", "backward", "l", "lft", "b", "backward"};
        String expected_position = "(4,1), Zachód";
        MoveDirection[] arguments = OptionParser.parse(movement);
        for (MoveDirection arg : arguments) {
            lion.move(arg);
        }
        assertEquals(expected_position, lion.to_string());
    }
    @Test
    public void Gibberish_command_test_no2() {               //niepoprawne polecenia
        Animal lion = new Animal();
        String [] movement = {"forwad", "backwar", "l", "right", "backward", "left", "forward", "fordard"};
        String expected_position = "(1,1), Zachód";
        MoveDirection[] arguments = OptionParser.parse(movement);
        for (MoveDirection arg : arguments) {
            lion.move(arg);
        }
        assertEquals(expected_position, lion.to_string());
    }
}
