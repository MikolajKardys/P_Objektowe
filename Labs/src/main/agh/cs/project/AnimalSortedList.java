package agh.cs.project;

import java.util.*;

public class AnimalSortedList extends ArrayList<Animal>{
    private static class compareHealth implements Comparator<Animal>{
        public int compare (Animal a, Animal b){
            if (b.getEnergy() == a.getEnergy()){
                return b.hashCode() - a.hashCode();
            }
            return b.getEnergy() - a.getEnergy();

        }
    }

    public AnimalSortedList(Animal firstAnimal){
        super();
        this.add(firstAnimal);
    }

    public ArrayList<Animal> getAllTop(){
        if (this.size() == 0){
            return null;
        }
        AnimalSortedList allTop = new AnimalSortedList(this.get(0));
        int i = 1;
        while (i < this.size() && this.get(i).getEnergy() == allTop.get(0).getEnergy()){
            allTop.add(this.get(i));
            i++;
        }
        return allTop;
    }

    public ArrayList<Animal> getTopTwo(){
        if (this.size() < 2){
            return null;
        }
        ArrayList<Animal> topTwo = this.getAllTop();
        if (topTwo.size() == 1){
            topTwo.add(this.get(1));
        }
        return topTwo;
    }

    public void sort(){
        this.sort(new compareHealth());
    }

    @Override
    public boolean add(Animal newAnimal){
        int ind = 0;
        while (ind < this.size() && newAnimal.getEnergy() < this.get(ind).getEnergy()){
            ind++;
        }
        super.add(ind, newAnimal);
        return true;
    }

}
