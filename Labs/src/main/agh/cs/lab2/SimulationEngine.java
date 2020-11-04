package agh.cs.lab2;

public class SimulationEngine implements IEngine {
    MoveDirection [] movements;
    IWorldMap map;
    Vector2d [] positions;
    public SimulationEngine(MoveDirection [] movements, IWorldMap map, Vector2d [] positions){
        this.movements = movements;
        this.map = map;
        this.positions = positions;

        for (Vector2d position : positions){
            this.map.place(new Animal(map, position));
        }
    }

    @Override
    public void run() {
        int index = 0;
        int length = positions.length;
        for (MoveDirection movement : movements){
            Animal CurrentAnimal = (Animal) this.map.objectAt(positions[index]);
            CurrentAnimal.move(movement);
            positions[index] = CurrentAnimal.getPosition();
            index = (index + 1) % length;
        }
    }
}
