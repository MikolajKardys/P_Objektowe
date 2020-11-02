package agh.cs.lab2;

import java.util.Arrays;

public class OptionParser {
    public static MoveDirection[] parse(String[] args) {
        MoveDirection[] results = new MoveDirection[args.length];
        int elements = 0;
        for (String arg : args) {
            switch (arg) {
                case "f":
                case "forward":
                    results[elements] = MoveDirection.FORWARD;
                    elements += 1;  // elements++
                    break;
                case "b":
                case "backward":
                    results[elements] = MoveDirection.BACKWARD;
                    elements += 1;
                    break;
                case "l":
                case "left":
                    results[elements] = MoveDirection.LEFT;
                    elements += 1;
                    break;
                case "r":
                case "right":
                    results[elements] = MoveDirection.RIGHT;
                    elements += 1;
                    break;

            }
        }
        results = Arrays.copyOfRange(results, 0, elements);
        return results;
    }
}
