package agh.cs.project.Visualisation;

import agh.cs.project.*;

import javax.swing.*;
import java.awt.*;

public class DrawBoard{
    private final JPanel [][] fields;

    public DrawBoard(JFrame f, GrassField field, int fieldSize){
        f.setLayout(new GridLayout(fieldSize,fieldSize));
        this.fields = new JPanel [fieldSize][fieldSize];
        for(int x=0;x<fieldSize;x++)
        {
            for(int y = 0; y < fieldSize; y++){

                JPanel newPanel = new JPanel();
                f.add(newPanel);
                JTextArea text = new JTextArea();
                text.setOpaque(false);
                text.setEditable(false);
                newPanel.add(text);

                this.fields[x][y] = newPanel;
                newPanel.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 1, Color.gray));

                Vector2d curPosition = new Vector2d(x, y);
                if (field.objectAt(curPosition) instanceof Grass){
                    newPanel.setBackground(Color.green);
                    
                }

            }
        }
    }
    public void addAnimal(Animal animal){
        int indX = animal.getPosition().x;
        int indY = animal.getPosition().y;
        JTextArea thisText = (JTextArea) this.fields[indX][indY].getComponents()[0];
        thisText.append(animal.toString());
    }

    public void removeAnimal(Vector2d position){
        int indX = position.x;
        int indY = position.y;
    }

    public void moveAnimal(Animal animal, Vector2d oldPosition){
        int indX = animal.getPosition().x;
        int indY = animal.getPosition().y;
        JTextArea thisText = (JTextArea) this.fields[indX][indY].getComponents()[0];
        thisText.remove(0);


    }
}
