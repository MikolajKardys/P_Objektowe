package agh.cs.lab2;
public class Vector2d {
    public final int x;
    public final int y;
    public Vector2d(int x, int y){
        this.x = x;
        this.y = y;
    }
    public String toString(){
        return "(" + this.x + "," + this.y +  ")";
    }
    public boolean precedes(Vector2d other){
        if (this.x <= other.x && this.y <= other.y) return true;
        return false;
    }
    public boolean follows(Vector2d other){
        if (this.x >= other.x && this.y >= other.y) return true;
        return false;
    }
    public Vector2d upperRight(Vector2d other){
        int max_x;
        int max_y;
        if (this.x > other.x){
            max_x = this.x;
        }
        else{
            max_x = other.x;
        }
        if (this.y > other.y){
            max_y = this.y;
        }
        else{
            max_y = other.y;
        }
        return new Vector2d(max_x, max_y);
    }
    public Vector2d lowerLeft(Vector2d other){
        int min_x;
        int min_y;
        if (this.x < other.x){
            min_x = this.x;
        }
        else{
            min_x = other.x;
        }
        if (this.y < other.y){
            min_y = this.y;
        }
        else{
            min_y = other.y;
        }
        return new Vector2d(min_x, min_y);
    }
    public Vector2d add(Vector2d other){
        return new Vector2d(this.x + other.x, this.y + other.y);
    }
    public Vector2d subtract(Vector2d other){
        return new Vector2d(this.x - other.x, this.y - other.y);
    }
    public boolean equals(Object other){
        if (this == other)
            return true;
        if (!(other instanceof Vector2d))
            return false;
        Vector2d other_vec = (Vector2d) other;
        if (this.x == other_vec.x && this.y == other_vec.y){
            return true;
        }
        return false;
    }
    public Vector2d opposite(){
        return new Vector2d(this.x * (-1), this.y * (-1));
    }
}