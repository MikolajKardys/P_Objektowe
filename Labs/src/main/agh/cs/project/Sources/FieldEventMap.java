package agh.cs.project.Sources;

import java.util.ArrayList;

public class FieldEventMap extends FieldMap{

//Struktura rozstrzygająca 3. i 4. etap każdej epoki

    private final int plantEnergy;

    public FieldEventMap(){
        this(0);
    }
    public FieldEventMap(int plantEnergy) {
        this.plantEnergy = plantEnergy;
    }

    public void resolveEating(){
        for (AnimalSortedList field : values()){
            ArrayList<Animal> hungryAnimals = field.getAllTop();
            int energyForEach = plantEnergy / hungryAnimals.size();
            for (Animal hippo : hungryAnimals){
                hippo.changeEnergy(energyForEach);
            }
        }
        clear();
    }

    public ArrayList<Animal> resolveBreeding(){             //Zwraca listę nowo powtałych zwierząt
        ArrayList<Animal> newTab = new ArrayList<>();
        for (AnimalSortedList field : values()){
            ArrayList<Animal> breedingAnimals = field.getTopTwo();
            if (breedingAnimals != null) {
                Animal aParent = breedingAnimals.get(0);
                Animal bParent = breedingAnimals.get(1);    // a co z losowaniem rodziców w przypadku remisu?
                if (bParent.canBreed()) {
                    Animal newAnimal = new Animal(aParent, bParent);
                    newTab.add(newAnimal);
                    aParent.sortThisField();
                }
            }

        }
        return newTab;
    }
}
