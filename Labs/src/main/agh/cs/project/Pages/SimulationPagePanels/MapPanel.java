package agh.cs.project.Pages.SimulationPagePanels;

import agh.cs.project.*;
import agh.cs.project.Pages.SimulationPage;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class MapPanel extends JPanel {
    private final GrassField field;
    private final int fieldSize;

    private final Color grassColor = new Color(31, 119, 49);
    private final Color steppeColor = new Color(182, 227, 83);
    private final Color jungeColor = new Color(51, 153, 102);

    private final JButton [][] fields;

    public MapPanel(SimulationPage mainPage, GrassField field, int mapWidth, int mapHeight, int fieldSize) {
        this.field = field;
        this.fieldSize = fieldSize;

        this.setBackground(steppeColor);
        this.setPreferredSize(new Dimension(mapWidth, mapHeight));
        this.setMinimumSize(new Dimension(mapWidth, mapHeight));
        this.setMaximumSize(new Dimension(mapWidth, mapHeight));
        this.setAlignmentX(CENTER_ALIGNMENT);

        this.fields = new JButton[field.width][field.height];
        this.setLayout(new GridLayout(field.width, field.height));
        for(int x = 0; x < field.width; x++) {
            for(int y = 0; y < field.height; y++){
                JButton newButton = new JButton();
                this.fields[x][y] = newButton;
                newButton.setOpaque(false);
                newButton.setName(x + " " + y);
                newButton.setContentAreaFilled(false);
                newButton.addActionListener(mainPage::actionPerformed);
                this.add(newButton);
            }
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        //Paint Jungle
        Vector2d jungleLower = field.getJungleLowerCorner();
        Vector2d jungleUpper = field.getJungleUpperCorner();
        int jungleX = jungleUpper.y - jungleLower.y + 1;
        int jungleY = jungleUpper.x - jungleLower.x + 1;
        g.setColor(jungeColor);
        g.fillRect(jungleLower.y * fieldSize, jungleLower.x * fieldSize,
                jungleX * fieldSize, jungleY * fieldSize);

        //Paint Grass
        g.setColor(grassColor);
        ArrayList<Grass> grassList = field.getGrasses();
        for (Grass grass : grassList) {
            if (grass != null) {
                int y = grass.getPosition().x * fieldSize;
                int x = grass.getPosition().y * fieldSize;
                g.fillRect(x, y, fieldSize, fieldSize);
            }
        }

        //Paint Animals
        ArrayList<AnimalSortedList> animalLists = field.getAnimals();
        for (AnimalSortedList animals : animalLists) {
            Animal animal = animals.getAllTop().get(0);
            int y = animal.getPosition().x * fieldSize;
            int x = animal.getPosition().y * fieldSize;
            g.setColor(animal.getHealthColor());
            g.fillRect(x, y, fieldSize, fieldSize);
        }

        /*
        //Paint Grid
        g.setColor(borderColor);
        for (int i = 1; i < field.height; i++) {
            g.drawLine(0, i * fieldSize, mapWidth * fieldSize, i * fieldSize);
            g.drawLine(i * fieldSize, 0, i * fieldSize, mapHeight * fieldSize);
        }*/

    }
}





