package agh.cs.lab2;

import java.util.ArrayList;
import java.util.List;

public class ProjectEngine implements IEngine {
    private final Vector2d [] positions;
    private final List<Animal> animals = new ArrayList<>();
    public ProjectEngine(GrassField field, Vector2d [] positions){
        this.positions = positions;
        for (Vector2d position : positions){
            Animal snake = new Animal(field, position);
            if (field.place(snake)){
                animals.add(snake);
            }
        }
    }

    @Override
    public void run() {

    }
}
