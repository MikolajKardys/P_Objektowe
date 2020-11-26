package agh.cs.lab2;

import java.util.ArrayList;
import java.util.List;

public class MapBoundary implements IPositionChangeObserver{
    List<AbstractWorldMapElement> sortedByX;
    List<AbstractWorldMapElement> sortedByY;
    public MapBoundary(){
        this.sortedByX = new ArrayList<>();
        this.sortedByY = new ArrayList<>();
    }

    public void addElement(AbstractWorldMapElement newElement){
        if (sortedByX.size() == 0){
            this.sortedByX.add(newElement);
            this.sortedByY.add(newElement);
            return;
        }

    }

    @Override
    public void positionChanged(Vector2d oldPosition, Vector2d newPosition) {

    }
}
