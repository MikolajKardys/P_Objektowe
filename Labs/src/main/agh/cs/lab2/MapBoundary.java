package agh.cs.lab2;

import java.util.ArrayList;
import java.util.List;

public class MapBoundary implements IPositionChangeObserver {

    private static class borderElement extends AbstractWorldMapElement {
        public boolean isAnimal;

        public borderElement(Vector2d position, boolean isAnimal) {
            super(position);
            this.isAnimal = isAnimal;
        }

        public boolean greaterXThan(borderElement other) {
            if (this.getPosition().x > other.getPosition().x) {
                return true;
            }
            if (this.getPosition().x == other.getPosition().x) {
                if (this.getPosition().y == other.getPosition().y) {
                    return this.isAnimal;
                }
                return this.greaterYThan(other);
            }
            return false;
        }

        public boolean greaterYThan(borderElement other) {
            if (this.getPosition().y > other.getPosition().y) {
                return true;
            }
            if (this.getPosition().y == other.getPosition().y) {
                if (this.getPosition().x == other.getPosition().x) {
                    return this.isAnimal;
                }
                return this.greaterXThan(other);
            }
            return false;
        }
    }

    private final List<borderElement> sortedByX;
    private final List<borderElement> sortedByY;

    public MapBoundary() {
        this.sortedByX = new ArrayList<>();
        this.sortedByY = new ArrayList<>();
    }

    public void printSorted() {
        for (borderElement elem : this.sortedByX) {
            System.out.print(elem.getPosition().toString() + " ");
        }
        System.out.print("\n");
        for (borderElement elem : this.sortedByY) {
            System.out.print(elem.getPosition().toString() + " ");
        }
        System.out.print("\n");
    }

    private int[] getIndexXY(borderElement element) {
        int indX = 0;
        while (indX < sortedByX.size() && element.greaterXThan(sortedByX.get(indX))) {
            indX++;
        }
        int indY = 0;
        while (indY < sortedByY.size() && element.greaterYThan(sortedByY.get(indY))) {
            indY++;
        }
        return new int[]{indX, indY};
    }

    public void addElement(AbstractWorldMapElement element) {
        borderElement newElement = new borderElement(element.getPosition(), element instanceof Animal);
        int[] indexes = getIndexXY(newElement);
        this.sortedByX.add(indexes[0], newElement);
        this.sortedByY.add(indexes[1], newElement);
    }

    private void deleteElement(AbstractWorldMapElement element) {
        if (this.sortedByX.size() == 0) return;
        borderElement newElement = new borderElement(element.getPosition(), element instanceof Animal);
        int[] indexes = getIndexXY(newElement);
        this.sortedByX.remove(indexes[0]);
        this.sortedByY.remove(indexes[1]);
    }

    public Vector2d lowerCorn() {
        Vector2d lowestX = this.sortedByX.get(0).getPosition();
        Vector2d lowestY = this.sortedByY.get(0).getPosition();
        return lowestX.lowerLeft(lowestY);
    }

    public Vector2d upperCorn() {
        Vector2d highestX = this.sortedByX.get(sortedByX.size() - 1).getPosition();
        Vector2d highestY = this.sortedByY.get(sortedByY.size() - 1).getPosition();
        return highestX.upperRight(highestY);
    }

    @Override
    public void positionChanged(Vector2d oldPosition, Vector2d newPosition) {
        borderElement movedElement = new borderElement(oldPosition, true);
        this.deleteElement(movedElement);
        movedElement.position = newPosition;
        this.addElement(movedElement);
    }
}
