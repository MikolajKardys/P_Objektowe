import agh.cs.lab2.*;
import org.junit.Test;
import static org.junit.Assert.*;


public class GrassFieldTest {
    @Test
    public void Grass_Generation_Test_1(){                       //testuje, czy na wygenerowanej mapie znajduje się odpowiednia liczba kęp trawy
        int expectedGrassCount = 10;
        GrassField field = new GrassField(expectedGrassCount);
        String output = field.toString();
        int grassCount = 0;
        int outputLength = output.length();
        for (int i = 0; i < outputLength; i++){
            if(output.charAt(i) == '*') grassCount++;
        }
        assertEquals(expectedGrassCount, grassCount);
    }

    @Test
    public void Grass_Generation_Test_2(){
        int expectedGrassCount = 14;
        GrassField field = new GrassField(expectedGrassCount);
        String output = field.toString();
        int grassCount = 0;
        int outputLength = output.length();
        for (int i = 0; i < outputLength; i++){
            if(output.charAt(i) == '*') grassCount++;
        }
        assertEquals(expectedGrassCount, grassCount);
    }

    @Test
    public void Grass_Generation_Test_3(){
        int expectedGrassCount = 5;
        GrassField field = new GrassField(expectedGrassCount);
        String output = field.toString();
        int grassCount = 0;
        int outputLength = output.length();
        for (int i = 0; i < outputLength; i++){
            if(output.charAt(i) == '*') grassCount++;
        }
        assertEquals(expectedGrassCount, grassCount);
    }

    @Test
    public void Animal_Place_Test_1(){                          //Analogiczne to tych które już były
        int expectedGrassCount = 5;
        GrassField field = new GrassField(expectedGrassCount);
        Animal lion = new Animal(field);
        field.place(lion);
        assertFalse(field.canMoveTo(new Vector2d(2, 2)));
        assertTrue(field.canMoveTo(new Vector2d(1, 5)));
    }

    @Test
    public void Animal_Place_Test_2(){
        int expectedGrassCount = 10;
        GrassField field = new GrassField(expectedGrassCount);
        Animal lion = new Animal(field, new Vector2d(6, 4));
        field.place(lion);
        assertFalse(field.canMoveTo(new Vector2d(6, 4)));
        assertTrue(field.canMoveTo(new Vector2d(4, 2)));
    }

    @Test
    public void Animal_CanMoveTo_Test_1(){                           //Analogiczne to tych które już były
        int expectedGrassCount = 3;
        GrassField field = new GrassField(expectedGrassCount);
        Animal lion = new Animal(field, new Vector2d(5, 5));
        field.place(lion);

        assertTrue(field.canMoveTo(new Vector2d(10, 4)));
        assertTrue(field.canMoveTo(new Vector2d(3, 1)));
        assertFalse(field.canMoveTo(new Vector2d(5, 5)));
    }
    @Test
    public void Animal_CanMoveTo_Test_2(){
        int expectedGrassCount = 8;
        GrassField field = new GrassField(expectedGrassCount);
        Animal lion = new Animal(field, new Vector2d(4, 2));
        field.place(lion);

        assertTrue(field.canMoveTo(new Vector2d(-1, 4)));
        assertTrue(field.canMoveTo(new Vector2d(3, 1)));
        assertFalse(field.canMoveTo(new Vector2d(4, 2)));
    }
}
