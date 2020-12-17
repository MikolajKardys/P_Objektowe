package agh.cs.project;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class ProjectEngine extends Timer {

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

    public final boolean tooBig;

    public ProjectEngine(int width, int height, int animalNumber, int startEnergy, int moveEnergy, int plantEnergy, float jungleRatio){
        this.tooBig = ((width > 40) || (height > 70) || (animalNumber > 2000));

        GrassField field = new GrassField(width, height, jungleRatio, this);
        this.energyToSurvive = moveEnergy;
        this.plantEnergy = plantEnergy;
        this.field = field;

        for (int i = 0; i < animalNumber; i++) {
            int newX = (int) (Math.random() * (double) width);
            int newY = (int) (Math.random() * (double) height);
            Vector2d newPosition = new Vector2d(newX, newY);
            Animals.add( new Animal(field, newPosition, startEnergy, moveEnergy) );
        }
        field.updateStats(this);
    }

    public void pause(){
        this.paused = true;
        System.out.println("Paused");
    }

    public void unpause(int delay){
        synchronized (this){
            this.paused = false;
            this.delay = delay;
            this.notifyAll();
            this.start();
            System.out.println("Unpaused");
        }
    }

    public void start(){
        this.schedule(new ProjectEngineDay(this),0, delay);
    }

    @Override
    public void cancel(){
        System.out.println("Terminated");
        super.cancel();
    }

    private class ProjectEngineDay extends TimerTask {
        private final ProjectEngine engine;
        public ProjectEngineDay(ProjectEngine engine){
            this.engine = engine;
        }
        @Override
        public void run() {
            synchronized (this) {

                if (engine.Animals.size() == 0){
                    engine.pause();
                    this.cancel();
                }
                else if (engine.paused){
                    this.cancel();
                }
                else {
                    field.setVisible(false);

                    int ind = 0;
                    while (ind < Animals.size()) {
                        Animal curAnimal = Animals.get(ind);
                        if (curAnimal.energy < engine.energyToSurvive) {
                            Animals.remove(ind);
                        } else ind++;
                    }

                    FieldEventMap eatEventMap = new FieldEventMap(engine.plantEnergy);
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
                    engine.Animals.addAll(newAnimals);

                    field.growGrass();

                    field.updateStats(this);

                    field.setVisible(true);
                }
            }
        }
    }
}

