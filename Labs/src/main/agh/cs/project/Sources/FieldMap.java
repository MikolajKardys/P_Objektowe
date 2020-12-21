package agh.cs.project.Sources;

import java.util.HashMap;

public class FieldMap extends HashMap<Vector2d, AnimalSortedList> {

    //Implementacja mapy przechowująca posortowane malejąco po enegii zwierzęta na danym polu;

    public void addAnimal(Animal newAnimal){
        Vector2d key = newAnimal.getPosition();
        if ( get(key) != null ){
            get(key).add(newAnimal);
        }
        else {
            AnimalSortedList newList = new AnimalSortedList(newAnimal);
            put(key, newList);
        }
    }

    public void removeAnimal(Vector2d key, Animal animal){
        get(key).remove(animal);
        if (get(key).size() == 0){
            remove(key);
        }
    }

}
