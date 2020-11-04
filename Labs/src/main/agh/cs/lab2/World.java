package agh.cs;
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
        /*
        frog.move(MoveDirection.RIGHT);
        frog.move(MoveDirection.FORWARD);
        frog.move(MoveDirection.FORWARD);
        frog.move(MoveDirection.FORWARD);
        */
        MoveDirection [] arguments = OptionParser.parse(args);
        for (MoveDirection arg : arguments) {
            frog.move(arg);
        }
        System.out.println(frog.to_string());
    }
}
