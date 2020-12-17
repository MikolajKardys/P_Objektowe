package agh.cs.project;

import java.util.ArrayList;

public class FieldEventMap extends FieldMap{
    private final int plantEnergy;

    public FieldEventMap(){
        this(0);
    }
    public FieldEventMap(int plantEnergy) {
        this.plantEnergy = plantEnergy;
    }

    public void resolveEating(){
        for (AnimalSortedList field : this.values()){
            ArrayList<Animal> hungryAnimals = field.getAllTop();
            int energyForEach = plantEnergy / hungryAnimals.size();
            for (Animal hippo : hungryAnimals){
                hippo.energy += energyForEach;
                hippo.alertObserversChangedEnergy();
            }
        }
        this.clear();
    }

    public ArrayList<Animal> resolveBreeding(){
        ArrayList<Animal> newTab = new ArrayList<>();
        for (AnimalSortedList field : this.values()){
            ArrayList<Animal> breedingAnimals = field.getTopTwo();
            if (breedingAnimals != null) {
                Animal aParent = breedingAnimals.get(0);
                Animal bParent = breedingAnimals.get(1);
                if (bParent.energy >= bParent.startEnergy / 2) {
                    Animal newAnimal = new Animal(aParent, bParent);
                    newTab.add(newAnimal);
                    System.out.println("Breed at: " + breedingAnimals.get(0).getPosition().toString());
                    System.out.println(newAnimal.startEnergy);
                }
            }

        }
        return newTab;
    }
}
