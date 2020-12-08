package agh.cs.project;

import java.util.ArrayList;

public class FieldEatList extends ArrayList<Animal> {

    public void resolveEating(int plantEnergy){  //rozdziela krzaczek między wszystkich zainteresowanych
        int energyForEach = plantEnergy / this.size();
        for (Animal hungry : this){
            hungry.energy += energyForEach;
        }
    }

    @Override
    public boolean add(Animal newAnimal){
        if (this.size() == 0){
            this.add(0, newAnimal);
            return true;
        }
        if (newAnimal.energy > this.get(0).energy){
            this.removeRange(0, this.size() - 1);
            this.add(0, newAnimal);
            return true;
        }
        if (newAnimal.energy == this.get(0).energy){
            this.add(0, newAnimal);
            return true;
        }
        return false;
    }
}
