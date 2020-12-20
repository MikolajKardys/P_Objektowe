package agh.cs.project.Sources;

import java.util.HashMap;

public class FieldMap extends HashMap<Vector2d, AnimalSortedList> {

    public void addAnimal(Animal newAnimal){
        Vector2d key = newAnimal.getPosition();
        if ( this.get(key) != null ){
            this.get(key).add(newAnimal);
        }
        else {
            AnimalSortedList newList = new AnimalSortedList(newAnimal);
            this.put(key, newList);
        }
    }

    public void removeAnimal(Vector2d key, Animal animal){
        this.get(key).remove(animal);
        if (this.get(key).size() == 0){
            this.remove(key);
        }
    }

}
