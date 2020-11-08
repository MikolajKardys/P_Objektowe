package agh.cs.lab2;

public enum MapDirection{
    NORTH,
    SOUTH,
    EAST,
    WEST;

    public String toString(){
        switch(this) {
            case NORTH: return "^";
            case EAST: return ">";
            case SOUTH: return "v";
        }
        return "<";
    }
    public MapDirection next(){
        switch(this) {
            case NORTH: return EAST;
            case EAST: return SOUTH;
            case SOUTH: return WEST;
        }
        return NORTH;  //case WEST
    }
    public MapDirection previous(){
        switch(this) {
            case NORTH: return WEST;
            case EAST: return NORTH;
            case SOUTH: return EAST;
        }
        return SOUTH; //case WEST
    }
    public Vector2d toUnitVector(){
        switch(this) {
            case NORTH: return new Vector2d(0, 1);
            case EAST: return new Vector2d(1, 0);
            case SOUTH: return new Vector2d(0, -1);
        }
        return new Vector2d(-1, 0); //case WEST
    }
}