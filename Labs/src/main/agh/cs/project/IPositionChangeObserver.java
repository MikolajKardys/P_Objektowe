package agh.cs.project;

public interface IPositionChangeObserver {
    void positionChanged(Animal element, Vector2d newPosition);

    void addedElement (AbstractWorldMapElement element);

    void removedElement(AbstractWorldMapElement element, Vector2d oldPosition);
}

