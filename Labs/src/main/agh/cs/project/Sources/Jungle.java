package agh.cs.project.Sources;

public class Jungle{

    public final Vector2d lowerCorner;
    public final Vector2d upperCorner;

    public final int maxFields;
    public int takenFields;

    private int getDimension(int mainDimension, float ratio){
        float dimention = mainDimension * ratio;
        if (dimention == Math.round(dimention)){
            if (dimention % 2 == mainDimension % 2){
                return (int)dimention;
            }
            return (int)dimention + 1;
        }
        if (Math.ceil(dimention) % 2 == dimention % 2){
            return (int) Math.ceil(dimention);
        }
        return (int) Math.floor(dimention);
    }

    public boolean positionInJungle(Vector2d position){
        return (position.follows(this.lowerCorner) && position.precedes(this.upperCorner));
    }

    public Jungle(GrassField field, float ratio) {
        int width = getDimension(field.width, ratio);
        int height = getDimension(field.height, ratio);

        this.takenFields = 0;

        this.lowerCorner = new Vector2d(field.width / 2 - width / 2, field.height / 2 - height / 2);
        this.upperCorner = new Vector2d(field.width / 2 + width / 2 - (field.width + 1) % 2, field.height / 2 + height / 2 - (field.height + 1) % 2);

        int jungleX = upperCorner.x - lowerCorner.x + 1;
        int jungleY = upperCorner.y - lowerCorner.y + 1;

        this.maxFields = jungleX * jungleY;
    }

}
