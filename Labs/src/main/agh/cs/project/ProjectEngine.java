package agh.cs.project;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class ProjectEngine implements IEngine {
    private final List<Animal> Animals = new ArrayList<>();
    private final GrassField field;

    public ProjectEngine(JFrame f, int fieldSize, int width, int height, int animalNumber, int startEnergy, int moveEnergy) {

        this.field = new GrassField(0, f, width, height, fieldSize);

        for (int i = 0; i < animalNumber; i++){
            int newX = (int) (Math.random() * (double) width);
            int newY = (int) (Math.random() * (double) height);
            Vector2d newPosition = new Vector2d(newX, newY);
            Animal newAnimal = new Animal(this.field, newPosition, MapDirection.Dir_0, (int)(startEnergy * Math.random()), moveEnergy);

            Animals.add(newAnimal);
            this.field.place(newAnimal);
        }

        f.setVisible(true);
    }

    @Override
    public void run() throws InterruptedException {
        for (int i = 0; i < 30; i++){
            Thread.sleep(300);
            if (i % 3 == 2){
                int randInd = (int)(Math.random() * Animals.size());
                Animals.get(randInd).kill();
                Animals.remove(randInd);
            }
            for (Animal animal : Animals){
                animal.newMove();
            }
        }
    }
}
