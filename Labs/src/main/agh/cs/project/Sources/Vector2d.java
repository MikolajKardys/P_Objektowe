package agh.cs.project.Sources;


import java.util.Objects;
import java.util.Scanner;

public class Vector2d {
    public final int x;
    public final int y;

    public Vector2d(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public String toString() {
        return "(" + this.x + "," + this.y + ")";
    }

    public static Vector2d fromString(String name){
        Scanner scanner = new Scanner(name);
        return new Vector2d(scanner.nextInt(), scanner.nextInt());
    }

    public Vector2d add(Vector2d other) {
        return new Vector2d(this.x + other.x, this.y + other.y);
    }

    public boolean equals(Object other) {
        if (this == other)
            return true;
        if (!(other instanceof Vector2d))
            return false;
        Vector2d other_vec = (Vector2d) other;
        return this.x == other_vec.x && this.y == other_vec.y;
    }

    public Vector2d opposite() {
        return new Vector2d(this.x * (-1), this.y * (-1));
    }

    public boolean precedes(Vector2d other) {
        return this.x <= other.x && this.y <= other.y;
    }

    public boolean follows(Vector2d other) {
        return this.x >= other.x && this.y >= other.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.x, this.y);
    }
}