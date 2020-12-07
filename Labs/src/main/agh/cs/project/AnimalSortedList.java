package agh.cs.project;

import java.util.*;

public class AnimalSortedList extends ArrayList<Animal>{

    private static class compareHealth implements Comparator<Animal>{
        public int compare (Animal a, Animal b){
            return b.health - a.health;
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

    public Animal getTop(){
        if (this.size() == 0){
            return null;
        }
        return this.get(0);
    }

    public Animal [] getTopTwo(){
        if (this.size() < 2){
            return null;
        }
        return new Animal [] {this.get(0), this.get(1)};
    }

    @Override
    public boolean add(Animal newAnimal){
        this.add(this.getIndex(newAnimal), newAnimal);
        return true;
    }

    @Override
    public boolean remove(Object animal){
        int index = this.getIndex((Animal) animal);
        if (index >= 0){
            this.remove(index);
            return true;
        }
        return false;
    }

}
