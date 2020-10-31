package agh.cs.lab2;

import agh.cs.lab3.Animal;

public class World {
    public static void main (String [] args){
        /*               -------laby 2 - pozostałości
        Vector2d position1 = new Vector2d(1,2);
        System.out.println(position1);
        Vector2d position2 = new Vector2d(-2,1);
        System.out.println(position2);
        System.out.println(position1.add(position2));
         */
        Animal frog = new Animal();
        //RIGHT, FORWARD, FORWARD, FORWARD
        frog.move(MoveDirection.RIGHT);
        frog.move(MoveDirection.FORWARD);
        frog.move(MoveDirection.FORWARD);
        frog.move(MoveDirection.FORWARD);
        System.out.println(frog.to_string());

    }

}
