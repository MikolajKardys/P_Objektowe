package agh.cs.project;

import java.util.ArrayList;
import java.util.List;

public class MapBoundary implements IPositionChangeObserver {

    //Tej klasy są obiekty w listach
    private static class SortedListsElement extends AbstractWorldMapElement { //uważam, że możemy dziedziczyć, bo wykorzystujemy odziedziczone elementy
        public boolean isAnimal;

        public SortedListsElement(Vector2d position, boolean isAnimal) {
            super(position);
            this.isAnimal = isAnimal;
        }

        public boolean greaterXThan(SortedListsElement other) {
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

        public boolean greaterYThan(SortedListsElement other) {
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

    private final List<SortedListsElement> sortedByX;
    private final List<SortedListsElement> sortedByY;

    public MapBoundary() {
        this.sortedByX = new ArrayList<>();
        this.sortedByY = new ArrayList<>();
    }

    private int[] getIndexXY(SortedListsElement element) {   //Zwraca index w liście X i liście Y; używa binarySearch
        if (this.sortedByX.size() == 0){
            return new int[]{0, 0};
        }

        int indX = -1;
        int leftInd = 0;
        int rightInd = this.sortedByX.size() - 1;
        while (leftInd < rightInd) {
            int midInd = (rightInd + leftInd) / 2;
            SortedListsElement midElement = this.sortedByX.get(midInd);
            if (element.greaterXThan(midElement)){
                leftInd = midInd + 1;
            }
            else if (midElement.greaterXThan(element)){
                rightInd = midInd - 1;
            }
            else{
                indX = midInd;
                break;
            }
        }
        if (leftInd >= rightInd){
            indX =  (element.greaterXThan(sortedByX.get(leftInd))) ? (leftInd + 1) : leftInd;
        }

        int indY = -1;
        leftInd = 0;
        rightInd = this.sortedByY.size() - 1;
        while (leftInd < rightInd) {
            int midInd = (rightInd + leftInd) / 2;
            SortedListsElement midElement = this.sortedByY.get(midInd);
            if (element.greaterYThan(midElement)){
                leftInd = midInd + 1;
            }
            else if (midElement.greaterYThan(element)){
                rightInd = midInd - 1;
            }
            else{
                indY = midInd;
                break;
            }
        }
        if (leftInd >= rightInd){
            indY =  (element.greaterYThan(sortedByY.get(leftInd))) ? (leftInd + 1) : leftInd;
        }

        return new int[]{indX, indY};
    }

    public void addElement(AbstractWorldMapElement element) {
        if (element instanceof Animal){
            ((Animal) element).addObserver(this);
        }
        SortedListsElement newElement = new SortedListsElement(element.getPosition(), element instanceof Animal);
        int[] indexes = getIndexXY(newElement);
        this.sortedByX.add(indexes[0], newElement);
        this.sortedByY.add(indexes[1], newElement);
    }

    private void deleteElement(AbstractWorldMapElement element) {
        if (this.sortedByX.size() == 0) return;
        SortedListsElement newElement = new SortedListsElement(element.getPosition(), element instanceof Animal);
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
        SortedListsElement movedElement = new SortedListsElement(oldPosition, true);
        this.deleteElement(movedElement);
        movedElement.position = newPosition;
        this.addElement(movedElement);
    }
}
