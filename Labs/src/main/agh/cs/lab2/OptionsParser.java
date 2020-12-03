package agh.cs.lab2;

public class OptionsParser {
    public static MoveDirection[] parse(String[] args) {
        MoveDirection[] results = new MoveDirection[args.length];
        int elements = 0;
        for (String arg : args) {
            switch (arg) {
                case "f":
                case "forward":
                    results[elements] = MoveDirection.TURN_0;
                    elements += 1;
                    break;
                case "b":
                case "backward":
                    results[elements] = MoveDirection.TURN_4;
                    elements += 1;
                    break;
                case "l":
                case "left":
                    results[elements] = MoveDirection.TURN_6;
                    elements += 1;
                    break;
                case "r":
                case "right":
                    results[elements] = MoveDirection.TURN_2;
                    elements += 1;
                    break;
                default: throw new IllegalArgumentException(arg + " is not legal move specification; check " + (elements + 1) + ". given argument");
            }
        }
        return results;
    }
}
