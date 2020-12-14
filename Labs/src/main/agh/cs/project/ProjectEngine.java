package agh.cs.project;

import java.util.ArrayList;
import java.util.List;

public class ProjectEngine extends Thread {
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

    private boolean paused = false;

    public ProjectEngine(int width, int height, int animalNumber, int startEnergy, int moveEnergy, int plantEnergy, float jungleRatio){
        GrassField field = new GrassField(width, height, jungleRatio, this);
        this.energyToSurvive = moveEnergy;
        this.plantEnergy = plantEnergy;
        this.field = field;

        for (int i = 0; i < animalNumber; i++) {
            int newX = (int) (Math.random() * (double) width);
            int newY = (int) (Math.random() * (double) height);
            Vector2d newPosition = new Vector2d(newX, newY);
            Animals.add( new Animal(field, newPosition, MapDirection.Dir_0, startEnergy, moveEnergy) );
        }
    }


    public void pause(){
        this.paused = true;
    }

    public void unpause(){
        synchronized (this){
            this.paused = false;
            this.notifyAll();
        }
    }

    @Override
    public void run() {
        try {
            while (Animals.size() > 0) {
                sleep(200);
                System.out.println(field.jungle.takenFields + "   " + field.takenFields + "   " + field.GrassMap.size());
                if (paused){
                    synchronized (this){
                        System.out.println("Paused");
                        this.wait();
                    }
                    System.out.println("Unpaused");
                }
                field.growGrass();

                int ind = 0;
                while (ind < Animals.size()) {
                    Animal curAnimal = Animals.get(ind);
                    if (curAnimal.energy < this.energyToSurvive) {
                        Animals.remove(ind);
                    } else ind++;
                }

                FieldEventMap eatEventMap = new FieldEventMap(this.plantEnergy);
                for (Animal animal : Animals) {
                    ArrayList<EventType> events = animal.newMove();
                    if (events.contains(EventType.Eating)) {
                        eatEventMap.addAnimal(animal);
                    }
                }

                eatEventMap.resolveEating();
            }
        }
        catch (InterruptedException ignored) {
            System.out.println("Terminated");
        }
    }
}

