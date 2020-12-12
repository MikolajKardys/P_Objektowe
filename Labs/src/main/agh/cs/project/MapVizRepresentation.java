package agh.cs.project;

import javax.swing.*;
import java.awt.*;

public class MapVizRepresentation implements IChangeObserver {
    private final JTextField [][] fields;
    private final GrassField field;

    private final Color grassColor = new Color(31, 119, 49);
    private final Color steppeColor = new Color(182,227,83);
    private final Color borderColor = new Color(131,125,74);

    public MapVizRepresentation(JFrame f, GrassField field){
        this.field = field;

        f.setLayout(new GridLayout(field.width, field.height));
        this.fields = new JTextField [field.width][field.height];
        for(int x = 0; x < field.width; x++) {
            for(int y = 0; y < field.height; y++){

                JTextField newPanel = new JTextField();
                f.add(newPanel);
                newPanel.setBackground(steppeColor);
                newPanel.setHorizontalAlignment(SwingConstants.CENTER);
                newPanel.setEditable(false);

                this.fields[x][y] = newPanel;
                newPanel.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 1, borderColor));
                f.setBackground(steppeColor);

                Vector2d curPosition = new Vector2d(x, y);
                if (field.objectAt(curPosition) instanceof Grass){
                    newPanel.setBackground(grassColor);
                }

            }
        }
    }

    private void updateField(int indX, int indY) {
        JTextField text = this.fields[indX][indY];
        text.setText(null);

        Vector2d position = new Vector2d(indX, indY);
        String topSprite = this.field.getTopStringAt(position);
        if (topSprite != null) {
            text.setText(topSprite);
            text.setBackground(this.field.getColorAt(position));
        }
        else {
            text.setBackground(steppeColor);
        }
    }

    @Override
    public void changedPosition(Animal animal, Vector2d oldPosition){
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
    public void removedElement(AbstractWorldMapElement element){
        int indX = element.getPosition().x;
        int indY = element.getPosition().y;
        if (element instanceof Animal){
            this.updateField(indX, indY);
        }
        else {
            this.fields[indX][indY].setBackground(steppeColor);
        }
    }

    @Override
    public void changedEnergy(Animal animal){
        this.updateField(animal.getPosition().x, animal.getPosition().y);
    }
}
