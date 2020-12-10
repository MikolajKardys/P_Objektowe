package agh.cs.project;

import javax.swing.*;
import java.awt.*;

public class MapVizRepresentation implements IPositionChangeObserver {
    private final JPanel [][] fields;
    private final GrassField field;

    private Color grassColor = new Color(0, 102, 0);

    public MapVizRepresentation(JFrame f, GrassField field){
        this.field = field;

        f.setLayout(new GridLayout(field.width, field.height));
        this.fields = new JPanel [field.width][field.height];
        for(int x = 0; x < field.width; x++) {
            for(int y = 0; y < field.height; y++){

                JPanel newPanel = new JPanel();
                f.add(newPanel);
                newPanel.setBackground(Color.white);
                newPanel.setVisible(true);
                newPanel.setLayout(new GridBagLayout());

                this.fields[x][y] = newPanel;
                newPanel.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 1, Color.gray));

                Vector2d curPosition = new Vector2d(x, y);
                if (field.objectAt(curPosition) instanceof Grass){
                    newPanel.setBackground(grassColor);
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

            JTextArea text = new JTextArea();
            text.setText(topSprite);
            text.setOpaque(false);
            text.setEditable(false);

            curField.add(text);

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
            this.fields[indX][indY].setBackground(this.grassColor);
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
