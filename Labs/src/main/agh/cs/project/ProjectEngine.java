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
    private final int fieldSize = 25;

    public ProjectEngine(int width, int height, int animalNumber, int startEnergy, int moveEnergy, int plantEnergy) throws InterruptedException {

        JFrame f = new JFrame("Map");
        f.setSize(fieldSize * width,fieldSize * height);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        GrassField field = new GrassField(f, height, width);
        this.energyToSurvive = moveEnergy;
        this.plantEnergy = plantEnergy;
        this.field = field;
        this.f = f;

        for (int i = 0; i < animalNumber; i++) {
            int newX = (int) (Math.random() * (double) height);
            int newY = (int) (Math.random() * (double) width);
            Vector2d newPosition = new Vector2d(newX, newY);
            Animals.add( new Animal(field, newPosition, MapDirection.Dir_0, startEnergy, moveEnergy) );
        }
        f.setVisible(true);
    }

    @Override
    public void run() throws InterruptedException {
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
                } else ind++;
            }
            f.setVisible(true);

            FieldEventMap eatEventMap = new FieldEventMap(this.plantEnergy);
            for (Animal animal : Animals) {
                ArrayList<EventType> events = animal.newMove();
                if (events.contains(EventType.Eating)) {
                    eatEventMap.addAnimal(animal);
                }
            }

            eatEventMap.resolveEating();

            f.setVisible(true);

        }
    }
}

