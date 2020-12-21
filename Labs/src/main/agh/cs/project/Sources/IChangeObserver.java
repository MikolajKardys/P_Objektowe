package agh.cs.project.Sources;

public interface IChangeObserver {

//Observer zmian obiektu;

    void changedPosition(Animal element, Vector2d oldPosition);

    void addedElement (AbstractWorldMapElement element);

    void removedElement(AbstractWorldMapElement element);

    void energyChanged(Animal animal, int change);

}

