package agh.cs.project.Sources;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.Timer;

public class ProjectEngine implements ActionListener {

//Silnik obsługujący symulacje dla danej mapy

    private final List<Animal> Animals = new ArrayList<>() {
        @Override
        public Animal remove(int index) {
            Animals.get(index).kill();
            return super.remove(index);
        }
    };
    private final int plantEnergy;
    private final GrassField field;
    private boolean terminated;

    private boolean paused = true;

    private final Timer timer;

    public final boolean tooBig;

    public ProjectEngine(int width, int height, int animalNumber, int startEnergy, int moveEnergy, int plantEnergy, float jungleRatio){
        tooBig = ((width > 100) || (height > 60));
        GrassField field = new GrassField(width, height, jungleRatio, this);
        this.plantEnergy = plantEnergy;
        this.field = field;

        for (int i = 0; i < animalNumber; i++) {
            Vector2d newPosition;
            do {
                int newX = (int) (Math.random() * (double) width);
                int newY = (int) (Math.random() * (double) height);
                newPosition = new Vector2d(newX, newY);
            }
            while (!field.isFree(newPosition));
            Animals.add( new Animal(field, newPosition, startEnergy, moveEnergy) );
        }
        field.updateStats();

        field.repaint();

        timer = new Timer(200, this);

        terminated = false;
    }

    public void pause(){
        paused = true;
    }

    public void unpause(int delay){
        paused = false;
        notifyAll();
        start(delay);
    }

    public void start(int delay){
        timer.setDelay(delay);
        timer.start();
    }

    public void cancel(){
        timer.stop();
    }

    @Override
    public void actionPerformed(ActionEvent e) {         //Reprezentuje jedno pełne przejście epoki
        if (terminated){                         //Sprawdzamy czy symulacja już się skończyła lub czy została zapauzowana
            return;
        }
        if (Animals.size() == 0){
            cancel();
            terminated = true;
            field.terminated();
        }
        else if (paused){
            cancel();
        }
        else {
            int ind = 0;
            while (ind < Animals.size()) {            //Usunięcie martwych zwierząt
                Animal curAnimal = Animals.get(ind);
                if (curAnimal.getEnergy() <= 0) {
                    Animals.remove(ind);
                } else ind++;
            }

            FieldEventMap eatEventMap = new FieldEventMap(plantEnergy);
            FieldEventMap breedEventMap = new FieldEventMap();

            for (Animal animal : Animals) {
                ArrayList<EventType> events = animal.move();
                if (events.contains(EventType.Eating)) {
                    eatEventMap.addAnimal(animal);
                }
                if (events.contains(EventType.Breading)) {
                    breedEventMap.addAnimal(animal);
                }
            }

            eatEventMap.resolveEating();

            ArrayList<Animal> newAnimals = breedEventMap.resolveBreeding();
            Animals.addAll(newAnimals);

            field.growGrass();

            field.updateStats();

            if (!tooBig) field.repaint();
        }
    }
}