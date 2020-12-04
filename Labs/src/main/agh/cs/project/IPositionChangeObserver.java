package agh.cs.project;

public interface IPositionChangeObserver {
    void positionChanged(Vector2d oldPosition, Vector2d newPosition);

    void removedElement(Vector2d position);
}

