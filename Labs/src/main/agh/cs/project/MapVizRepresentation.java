package agh.cs.project;

import javax.swing.*;
import java.awt.*;

public class MapVizRepresentation implements IPositionChangeObserver {
    private final JPanel [][] fields;
    private final GrassField field;
    private final JFrame f;
    private int fieldSize;

    public MapVizRepresentation(JFrame f, GrassField field, int fieldSize){
        this.f = f;
        this.field = field;
        this.fieldSize = fieldSize;

        f.setLayout(new GridLayout(field.width, field.height));
        this.fields = new JPanel [field.width][field.height];
        for(int x = 0; x < field.width; x++) {
            for(int y = 0; y < field.height; y++){

                JPanel newPanel = new JPanel();
                f.add(newPanel);
                newPanel.setBackground(Color.white);
                newPanel.setVisible(true);

                this.fields[x][y] = newPanel;
                newPanel.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 1, Color.gray));

                Vector2d curPosition = new Vector2d(x, y);
                if (field.objectAt(curPosition) instanceof Grass){
                    newPanel.setBackground(Color.green);
                }

            }
        }
    }

    private void updateField(int indX, int indY) {
        JPanel curField = this.fields[indX][indY];
        curField.removeAll();

        Vector2d position = new Vector2d(indX, indY);
        String topSprite = this.field.getTopStringAt(position);
        if (topSprite != null) {
            curField.setBackground(this.field.getColorAt(position));

            JTextPane text = new JTextPane();
            text.setText(topSprite);
            text.setOpaque(false);
            text.setEditable(false);

            curField.setLayout(new GridBagLayout());
            curField.add(text);

            f.setVisible(true);

        }
        else {
            curField.setBackground(Color.white);
        }
    }

    @Override
    public void positionChanged(Animal animal, Vector2d oldPosition){
        int indX = oldPosition.x;
        int indY = oldPosition.y;
        this.updateField(indX, indY);

        int newX = animal.getPosition().x;
        int newY = animal.getPosition().y;
        this.updateField(newX, newY);
    }

    @Override
    public void addedElement(AbstractWorldMapElement element){
        int indX = element.getPosition().x;
        int indY = element.getPosition().y;
        if (element instanceof Animal){
            this.updateField(indX, indY);
        }
        else {
            this.fields[indX][indY].setBackground(Color.green);
        }
    }

    @Override
    public void removedElement(AbstractWorldMapElement element, Vector2d oldPosition){
        int indX = oldPosition.x;
        int indY = oldPosition.y;
        if (element instanceof Animal){
            this.updateField(indX, indY);
        }
        else {
            this.fields[indX][indY].setBackground(Color.white);
        }
    }

}
