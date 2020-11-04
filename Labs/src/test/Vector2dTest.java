import agh.cs.lab2.Vector2d;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

//opposite().
public class Vector2dTest {
    @Test
    public void equals_test() {
        Vector2d tester1 = new Vector2d(42, 42);
        Vector2d tester2 = new Vector2d(42, 42);
        Vector2d tester3 = new Vector2d(41, 43);
        assertTrue(tester1.equals(tester2));
        assertFalse(tester1.equals(tester3));
    }

    @Test
    public void toString_test() {
        Vector2d tester = new Vector2d(42, 42);
        String name = "(42,42)";
        assertEquals(name, tester.toString());
    }

    @Test
    public void precedes_test() {
        Vector2d tester1 = new Vector2d(41, 41);
        Vector2d tester2 = new Vector2d(42, 42);
        Vector2d tester3 = new Vector2d(40, 43);
        assertTrue(tester1.precedes(tester2));
        assertFalse(tester1.precedes(tester3));
    }

    @Test
    public void follows_test() {
        Vector2d tester1 = new Vector2d(42, 42);
        Vector2d tester2 = new Vector2d(41, 40);
        Vector2d tester3 = new Vector2d(41, 43);
        assertTrue(tester1.follows(tester2));
        assertFalse(tester1.follows(tester3));
    }

    @Test
    public void upperRight_test() {
        Vector2d tester1 = new Vector2d(42, 40);
        Vector2d tester2 = new Vector2d(39, 42);
        Vector2d result = new Vector2d(42, 42);
        assertTrue(tester1.upperRight(tester2).equals(result));
    }

    @Test
    public void lowerLeft_test() {
        Vector2d tester1 = new Vector2d(42, 45);
        Vector2d tester2 = new Vector2d(47, 42);
        Vector2d result = new Vector2d(42, 42);
        assertTrue(tester1.lowerLeft(tester2).equals(result));
    }

    @Test
    public void add_test() {
        Vector2d tester1 = new Vector2d(27, 33);
        Vector2d tester2 = new Vector2d(15, 9);
        Vector2d result = new Vector2d(42, 42);
        assertTrue(tester1.add(tester2).equals(result));
    }

    @Test
    public void subtract_test() {
        Vector2d tester1 = new Vector2d(78, 33);
        Vector2d tester2 = new Vector2d(36, -9);
        Vector2d result = new Vector2d(42, 42);
        assertTrue(tester1.subtract(tester2).equals(result));
    }

    @Test
    public void opposite_test() {
        Vector2d tester = new Vector2d(42, -42);
        Vector2d result = new Vector2d(-42, 42);
        assertTrue(tester.opposite().equals(result));
    }
}
