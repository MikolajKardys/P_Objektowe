import agh.cs.lab2.MapDirection;
import org.junit.Test;
import static org.junit.Assert.assertEquals;


public class MapDirectionTest {
    @Test
    public void next_check() {
        assertEquals(MapDirection.Dir_0.next(), MapDirection.Dir_2);
        assertEquals(MapDirection.Dir_2.next(), MapDirection.Dir_4);
        assertEquals(MapDirection.Dir_4.next(), MapDirection.Dir_6);
        assertEquals(MapDirection.Dir_6.next(), MapDirection.Dir_0);
    }
    @Test
    public void previous_check() {
        assertEquals(MapDirection.Dir_0.previous(), MapDirection.Dir_6);
        assertEquals(MapDirection.Dir_2.previous(), MapDirection.Dir_0);
        assertEquals(MapDirection.Dir_4.previous(), MapDirection.Dir_2);
        assertEquals(MapDirection.Dir_6.previous(), MapDirection.Dir_4);
    }

}
