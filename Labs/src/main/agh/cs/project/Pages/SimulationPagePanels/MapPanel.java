package agh.cs.project.Pages.SimulationPagePanels;

import javax.swing.*;

public class MapPanel extends JPanel{

    


    /*





    private final JButton[][] fields;
    private final GrassField field;

    private final Color grassColor = new Color(31, 119, 49);
    private final Color steppeColor = new Color(182, 227, 83);
    private final Color jungeColor = new Color(51, 153, 102);
    private final Color borderColor = new Color(131, 125, 74);

    public MapPanel(SimulationPage stats, GrassField field, int mapWidth, int mapHeight, int fieldSize){
        this.field = field;

        this.setLayout(new GridLayout(field.width, field.height));
        this.setBackground(borderColor);
        this.setPreferredSize(new Dimension(mapWidth, mapHeight));
        this.setMaximumSize(new Dimension(mapWidth, mapHeight));


        for(int x = 0; x < field.width; x++) {
            for(int y = 0; y < field.height; y++){

                JButton newButton = new JButton();
                this.fields[x][y] = newButton;
                newButton.setName(x + " " + y);
                newButton.setHorizontalAlignment(SwingConstants.CENTER);
                newButton.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 1, borderColor));
                newButton.addActionListener(stats);
                setGroundColor(x, y);

                Vector2d curPosition = new Vector2d(x, y);
                if (field.objectAt(curPosition) instanceof Grass){
                    newButton.setBackground(grassColor);
                }

                this.add(newButton);
            }
        }
    }



    private void setGroundColor(int indX, int indY){
        if (field.positionInJungle(new Vector2d(indX, indY))){
            this.fields[indX][indY].setBackground(jungeColor);
        }
        else
            this.fields[indX][indY].setBackground(steppeColor);
    }

    private void updateField(int indX, int indY) {
        JButton text = this.fields[indX][indY];
        Vector2d position = new Vector2d(indX, indY);
        Color newColor = this.field.getColorAt(position);
        if (newColor != null) {
            text.setBackground(newColor);
        }
        else {
            setGroundColor(indX, indY);
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
            this.fields[indX][indY].setBackground(grassColor);
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
            setGroundColor(indX, indY);
        }
    }

    @Override
    public void changedEnergy(Animal animal){
        this.updateField(animal.getPosition().x, animal.getPosition().y);
    }

     */
}

