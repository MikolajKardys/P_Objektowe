package agh.cs.lab2;

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
    private final String [] arrowTable = new String [] {"\uD83E\uDC51", "\uD83E\uDC55", "→", "\uD83E\uDC56", "\uD83E\uDC53", "\uD83E\uDC57", "←", "\uD83E\uDC54"};
    public String toString(){
        return arrowTable[this.ordinal()];
    }
    public MapDirection next(){
        int next = (this.ordinal() + 2) % this.numNumber;
        return MapDirection.valueOf("Dir_" + next);
    }
    public MapDirection previous(){
        int prev = (this.ordinal() + 6) % this.numNumber;
        return MapDirection.valueOf("Dir_" + prev);
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