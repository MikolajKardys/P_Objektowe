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
    private final int energyToSurvive;
    private final int plantEnergy;
    private final JFrame f;
    private final GrassField field;

    public ProjectEngine(JFrame f, int width, int height, int animalNumber, int startEnergy, int moveEnergy, int plantEnergy) throws InterruptedException {

        GrassField field = new GrassField(f, width, height);
        this.energyToSurvive = moveEnergy;
        this.plantEnergy = plantEnergy;
        this.field = field;
        this.f = f;

        for (int i = 0; i < animalNumber; i++) {
            int newX = (int) (Math.random() * (double) width);
            int newY = (int) (Math.random() * (double) height);
            Vector2d newPosition = new Vector2d(newX, newY);
            Animals.add( new Animal(field, newPosition, MapDirection.Dir_0, startEnergy, moveEnergy) );
        }
        f.setVisible(true);
    }

    @Override
    public void run() throws InterruptedException {
        FieldEventMap eatEventMap = new FieldEventMap(this.plantEnergy);
        int died = 0;
        while (Animals.size() > 0) {
            Thread.sleep(200);
            field.growGrass();
            field.growGrass();
            Thread.sleep(200);
            int ind = 0;
            while (ind < Animals.size()) {
                Animal curAnimal = Animals.get(ind);
                if (curAnimal.energy < this.energyToSurvive) {
                    Animals.remove(ind);
                    died++;
                    System.out.println("Number that died: " + died);
                } else ind++;
            }
            f.setVisible(true);

            for (Animal animal : Animals) {
                if (animal.newMove() == EventType.Eating) {
                    eatEventMap.addAnimal(animal);
                }
            }

            eatEventMap.resolveEating();

            f.setVisible(true);

        }
    }
}

