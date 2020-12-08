package agh.cs.project;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class ProjectEngine implements IEngine {
    private final List<Animal> Animals = new ArrayList<>(){
        @Override
        public boolean remove(Object o){
            ((Animal) o).kill();
            return super.remove(o);
        }
    };
    private final int moveEnergy;

    public ProjectEngine(JFrame f, int fieldSize, int width, int height, int animalNumber, int startEnergy, int moveEnergy) {

        GrassField field = new GrassField(0, f, width, height, fieldSize);
        this.moveEnergy = moveEnergy;

        for (int i = 0; i < animalNumber; i++){
            int newX = (int) (Math.random() * (double) width);
            int newY = (int) (Math.random() * (double) height);
            Vector2d newPosition = new Vector2d(newX, newY);
            Animal newAnimal = new Animal(field, newPosition, MapDirection.Dir_0, (int)(startEnergy * Math.random()), moveEnergy);

            Animals.add(newAnimal);
            field.place(newAnimal);
        }

        f.setVisible(true);
    }

    @Override
    public void run() throws InterruptedException {
        for (int i = 0; i < 30; i++){
            Thread.sleep(200);

            for (Animal animal : Animals){
                animal.newMove();
            }
        }
    }
}
