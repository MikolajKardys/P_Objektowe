package agh.cs.project.Sources;

public class Jungle{

    //Klasa przechowująca wszystko co potrzebujemy wiedzieć o stanie dżungli

    public final Vector2d lowerCorner;
    public final Vector2d upperCorner;

    public final int maxFields;
    public int takenFields;

    public Jungle(GrassField field, float ratio) {
        int width = getDimension(field.width, ratio);
        int height = getDimension(field.height, ratio);

        takenFields = 0;

        lowerCorner = new Vector2d(field.width / 2 - width / 2, field.height / 2 - height / 2);
        upperCorner = new Vector2d(field.width / 2 + width / 2 - (field.width + 1) % 2, field.height / 2 + height / 2 - (field.height + 1) % 2);

        int jungleX = upperCorner.x - lowerCorner.x + 1;
        int jungleY = upperCorner.y - lowerCorner.y + 1;

        maxFields = jungleX * jungleY;
    }

    //Dla podanej wpółrzędnej podaje ile powinna wynosić aby zachować odpowiedni stosunek dżungli do reszty mapy
    private int getDimension(int mainDimension, float ratio){
        float dimension = mainDimension * ratio;
        if (dimension == Math.round(dimension)){
            if (dimension % 2 == mainDimension % 2){
                return (int)dimension;
            }
            return (int)dimension + 1;
        }
        if (Math.ceil(dimension) % 2 == dimension % 2){
            return (int) Math.ceil(dimension);
        }
        return (int) Math.floor(dimension);
    }


    //Czy dana pozycja na mapie jest częścią dżungli
    public boolean positionInJungle(Vector2d position){
        return (position.follows(lowerCorner) && position.precedes(upperCorner));
    }
}
