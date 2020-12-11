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
}
