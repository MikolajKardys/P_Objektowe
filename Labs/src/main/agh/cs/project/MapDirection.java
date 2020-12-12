package agh.cs.project;

public enum MapDirection{
    Dir_0,
    Dir_1,
    Dir_2,
    Dir_3,
    Dir_4,
    Dir_5,
    Dir_6,
    Dir_7;
    public final int numNumber = 8;
    private final String [] arrowTable = new String [] {"⭢", "⭨", "⭣", "⭩", "⭠", "⭦", "⭡", "⭧"};
    public String toString(){
        return arrowTable[this.ordinal()];
    }

    public Vector2d toUnitVector(){
        int [] tabX = {0, 1, 1, 1, 0, -1, -1, -1};
        int [] tabY = {1, 1, 0, -1, -1, -1, 0, 1};
        return new Vector2d(tabX[this.ordinal()], tabY[this.ordinal()]);
    }

    public MapDirection turn(MoveDirection turn){
        int turnNumber = turn.ordinal();
        int direction = (this.ordinal() + turnNumber) % this.numNumber;
        return MapDirection.valueOf("Dir_" + direction);
    }
}