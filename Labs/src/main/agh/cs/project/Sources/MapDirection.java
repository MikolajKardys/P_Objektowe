package agh.cs.project.Sources;

public enum MapDirection{

//Tym przechowujący możliwe zwroty zwierzęcia i metody służące do łatwiejszego ich interpretowania

    Dir_0,
    Dir_1,
    Dir_2,
    Dir_3,
    Dir_4,
    Dir_5,
    Dir_6,
    Dir_7;

    public static MapDirection newMapDirection(int ordinal){
        return MapDirection.valueOf("Dir_" + ordinal);
    }

    public final int numNumber = 8;

    public Vector2d toUnitVector(){
        int [] tabX = {0, 1, 1, 1, 0, -1, -1, -1};
        int [] tabY = {1, 1, 0, -1, -1, -1, 0, 1};
        return new Vector2d(tabX[ordinal()], tabY[ordinal()]);
    }


    //Zmienia kierunek o podaną wartość MoveDirection
    public MapDirection turn(MoveDirection turn){
        int turnNumber = turn.ordinal();
        int direction = (ordinal() + turnNumber) % numNumber;
        return MapDirection.valueOf("Dir_" + direction);
    }
}