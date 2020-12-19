package agh.cs.project;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.Timer;

public class ProjectEngine implements ActionListener {

    private final List<Animal> Animals = new ArrayList<>() {
        @Override
        public Animal remove(int index) {
            Animals.get(index).kill();
            return super.remove(index);
        }
    };
    private final int energyToSurvive;
    private final int plantEnergy;
    private final GrassField field;

    private boolean paused = true;
    private int delay;

    private final Timer timer;

    public final boolean tooBig;

    public ProjectEngine(int width, int height, int animalNumber, int startEnergy, int moveEnergy, int plantEnergy, float jungleRatio){
        this.tooBig = ((width > 60) || (height > 100));

        GrassField field = new GrassField(width, height, jungleRatio, this);
        this.energyToSurvive = moveEnergy;
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
    }

    public void pause(){
        this.paused = true;
    }

    public void unpause(int delay){
        this.paused = false;
        this.delay = delay;
        this.notifyAll();
        this.start(delay);
    }

    public void start(int delay){
        timer.setDelay(delay);
        timer.start();
    }

    public void cancel(){
        timer.stop();
        System.out.println("Terminated");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (this.Animals.size() == 0){
            this.pause();
            this.cancel();
        }
        else if (this.paused){
            this.cancel();
        }
        else {
            int ind = 0;
            while (ind < Animals.size()) {
                Animal curAnimal = Animals.get(ind);
                if (curAnimal.getEnergy() < this.energyToSurvive) {
                    Animals.remove(ind);
                } else ind++;
            }

            FieldEventMap eatEventMap = new FieldEventMap(this.plantEnergy);
            FieldEventMap breedEventMap = new FieldEventMap();
            for (Animal animal : Animals) {
                ArrayList<EventType> events = animal.newMove();
                if (events.contains(EventType.Eating)) {
                    eatEventMap.addAnimal(animal);
                }
                if (events.contains(EventType.Breading)) {
                    breedEventMap.addAnimal(animal);
                }
            }

            eatEventMap.resolveEating();

            ArrayList<Animal> newAnimals = breedEventMap.resolveBreeding();
            this.Animals.addAll(newAnimals);

            field.growGrass();

            field.updateStats();

            if (!tooBig) field.repaint();
        }
    }
}