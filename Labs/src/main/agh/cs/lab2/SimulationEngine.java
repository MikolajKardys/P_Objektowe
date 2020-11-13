package agh.cs.lab2;

import java.util.ArrayList;
import java.util.List;

public class SimulationEngine implements IEngine {
    private final MoveDirection [] movements;
    private final Vector2d [] positions;
    private final List<Animal> animals = new ArrayList<>();
    public SimulationEngine(MoveDirection [] movements, IWorldMap map, Vector2d [] positions){
        this.movements = movements;
        this.positions = positions;

        for (Vector2d position : positions){
            Animal snek = new Animal(map, position);
            map.place(snek);
            animals.add(snek);
        }
    }

    @Override
    public void run() {
        int index = 0;
        int length = positions.length;
        for (MoveDirection movement : movements){
            animals.get(index).move(movement);
            index = (index + 1) % length;
        }
    }
}
