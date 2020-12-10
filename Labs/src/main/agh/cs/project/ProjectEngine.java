package agh.cs.project;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class ProjectEngine implements IEngine {
    private final List<Animal> Animals = new ArrayList<>() {
        @Override
        public Animal remove(int index) {
            Animals.get(index).kill();
            return super.remove(index);
        }
    };
    private final int moveEnergy;
    private final JFrame f;

    public ProjectEngine(JFrame f, int fieldSize, int width, int height, int animalNumber, int startEnergy, int moveEnergy) {

        GrassField field = new GrassField(0, f, width, height);
        this.moveEnergy = moveEnergy;
        this.f = f;

        for (int i = 0; i < animalNumber; i++) {
            int newX = (int) (Math.random() * (double) width);
            int newY = (int) (Math.random() * (double) height);
            Vector2d newPosition = new Vector2d(newX, newY);
            Animal newAnimal = new Animal(field, newPosition, MapDirection.Dir_0, (int) (startEnergy * Math.random()), moveEnergy);

            Animals.add(newAnimal);
            field.place(newAnimal);
        }

        f.setVisible(true);
    }

    @Override
    public void run(){
        while (Animals.size() > 1) {

            int ind = 0;
            while (ind < Animals.size()) {
                Animal curAnimal = Animals.get(ind);
                if (curAnimal.energy < moveEnergy) {
                    Animals.remove(ind);
                } else ind++;
            }
            f.setVisible(true);

            for (Animal animal : Animals) {
                animal.newMove();
            }
            f.setVisible(true);
        }
    }
}

