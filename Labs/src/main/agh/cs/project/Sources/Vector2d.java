package agh.cs.project.Sources;


import java.util.Objects;
import java.util.Scanner;

public class Vector2d {
    public final int x;
    public final int y;

    public Vector2d(String name){                    //Jedyny dodatek; kreuje wektor ze stringa postaci "x y"
        Scanner scanner = new Scanner(name);
        this.x = scanner.nextInt();
        this.y = scanner.nextInt();
    }

    public Vector2d(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public String toString() {
        return "(" + x + "," + y + ")";
    }

    public Vector2d add(Vector2d other) {
        return new Vector2d(x + other.x, y + other.y);
    }

    public boolean equals(Object other) {
        if (this == other)
            return true;
        if (!(other instanceof Vector2d))
            return false;
        Vector2d other_vec = (Vector2d) other;
        return x == other_vec.x && y == other_vec.y;
    }

    public Vector2d opposite() {
        return new Vector2d(x * (-1), y * (-1));
    }

    public boolean precedes(Vector2d other) {
        return x <= other.x && y <= other.y;
    }

    public boolean follows(Vector2d other) {
        return x >= other.x && y >= other.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}