package agh.cs;

public enum MapDirection{
    NORTH,
    SOUTH,
    EAST,
    WEST;

    public String toString(){
        switch(this) {
            case NORTH: return "Północ";
            case EAST: return "Wschód";
            case SOUTH: return "Południe";
        }
        return "Zachód";
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
}