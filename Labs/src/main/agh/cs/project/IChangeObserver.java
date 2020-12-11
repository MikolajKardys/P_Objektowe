package agh.cs.project;

public interface IChangeObserver {
    void changedPosition(Animal element, Vector2d oldPosition);

    void addedElement (AbstractWorldMapElement element);

    void removedElement(AbstractWorldMapElement element);

    void changedEnergy(Animal animal);
}

