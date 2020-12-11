package agh.cs.project;

import java.util.*;

public class AnimalSortedList extends ArrayList<Animal>{

    private static class compareHealth implements Comparator<Animal>{
        public int compare (Animal a, Animal b){
            return b.energy - a.energy;
        }
    }

    public AnimalSortedList(Animal firstAnimal){
        this.add(firstAnimal);
    }

    private int getIndex(Animal animal){
        int index = Collections.binarySearch(this, animal, new compareHealth());
        if (index >= 0){
            return index;
        }
        else return (index * (-1)) - 1;
    }

    public ArrayList<Animal> getAllTop(){
        if (this.size() == 0){
            return null;
        }
        ArrayList<Animal> firsts = new ArrayList<>();
        firsts.add(this.get(0));
        int ind = 1;
        while (ind < this.size() && this.get(ind).energy == this.get(0).energy){
            firsts.add(this.get(ind));
            ind++;
        }
        return firsts;
    }
    @Override
    public boolean add(Animal newAnimal){
        this.add(this.getIndex(newAnimal), newAnimal);
        return true;
    }
}
