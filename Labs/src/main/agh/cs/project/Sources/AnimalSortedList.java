package agh.cs.project.Sources;

import java.util.*;

public class AnimalSortedList extends ArrayList<Animal>{

//Struktura przechowująca zwierzęta posortowane po energii

    private static class compareHealth implements Comparator<Animal>{   // nazwa brzmi jak metoda + nazwy klas raczej PascalCase
        public int compare (Animal a, Animal b){
            return b.getEnergy() - a.getEnergy();
        }
    }

    //Tworzy listę i od razu dodaje pierwszy element
    public AnimalSortedList(Animal firstAnimal){    // zakłóca czytelność - konstruktor ArrayList ma inny parametr
        add(firstAnimal);
    }

    //Zwraca listę wszystkich zwierząt o najwyższej energii
    public ArrayList<Animal> getAllTop(){
        if (size() == 0){
            return null;
        }
        AnimalSortedList allTop = new AnimalSortedList(get(0));
        int i = 1;
        while (i < size() && get(i).getEnergy() == allTop.get(0).getEnergy()){
            allTop.add(get(i));
            i++;
        }
        return allTop;
    }

    //Zwraca pierwsze dwa elementy z góry listy
    public ArrayList<Animal> getTopTwo(){
        if (size() < 2){
            return null;
        }
        ArrayList<Animal> topTwo = getAllTop();
        if (topTwo.size() == 1){
            topTwo.add(get(1));
        }
        return topTwo;
    }

    public void sort(){
        sort(new compareHealth());
    }

    //Wstawia do posortowanej listy
    @Override
    public boolean add(Animal newAnimal){
        int ind = 0;
        while (ind < size() && newAnimal.getEnergy() < get(ind).getEnergy()){
            ind++;
        }
        super.add(ind, newAnimal);
        return true;
    }

}
