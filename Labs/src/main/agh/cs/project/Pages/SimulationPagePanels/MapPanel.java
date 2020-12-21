package agh.cs.project.Pages.SimulationPagePanels;

import agh.cs.project.Pages.SimulationPage;
import agh.cs.project.Sources.*;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.util.List;

public class MapPanel extends JPanel implements IMapViz{

//Fragment okienka obsługujący faktyczną mapę

    private final GrassField field;
    private final int fieldSize;

    private final Color grassColor = new Color(31, 119, 49);
    private final Color steppeColor = new Color(182, 227, 83);
    private final Color jungeColor = new Color(51, 153, 102);

    private final JButton [][] fields;

    public MapPanel(SimulationPage mainPage, GrassField field, int mapWidth, int mapHeight, int fieldSize) {
        this.field = field;
        this.fieldSize = fieldSize;

        setBackground(steppeColor);
        setPreferredSize(new Dimension(mapWidth, mapHeight));
        setMinimumSize(new Dimension(mapWidth, mapHeight));
        setMaximumSize(new Dimension(mapWidth, mapHeight));
        setAlignmentX(CENTER_ALIGNMENT);

        fields = new JButton[field.height][field.width];
        setLayout(new GridLayout(field.height, field.width));
        for(int x = 0; x < field.height; x++) {
            for(int y = 0; y < field.width; y++){
                JButton newButton = new JButton();
                fields[x][y] = newButton;
                newButton.setOpaque(false);
                newButton.setName(x + " " + y);
                newButton.setContentAreaFilled(false);
                newButton.addActionListener(mainPage::actionPerformed);
                add(newButton);
            }
        }
    }

    public void highLight(int indX, int indY){
        fields[indX][indY].setBorder(new MatteBorder(4, 4, 4, 4, Color.blue));
    }

    public void removeHighLight(int indX, int indY){
        fields[indX][indY].setBorder(UIManager.getBorder("Button.border"));
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        //Paint Jungle
        Vector2d jungleLower = field.getJungleLowerCorner();
        Vector2d jungleUpper = field.getJungleUpperCorner();
        int jungleX = jungleUpper.x - jungleLower.x + 1;
        int jungleY = jungleUpper.y - jungleLower.y + 1;
        g.setColor(jungeColor);
        g.fillRect(jungleLower.x * fieldSize, jungleLower.y * fieldSize,
                jungleX * fieldSize, jungleY * fieldSize);

        //Paint Grass
        g.setColor(grassColor);
        List<Grass> grassList = field.getGrasses();
        for (Grass grass : grassList) {
            if (grass != null) {
                int x = grass.getPosition().x * fieldSize;
                int y = grass.getPosition().y * fieldSize;
                g.fillRect(x, y, fieldSize, fieldSize);
            }
        }

        //Paint Animals
        List<AnimalSortedList> animalLists = field.getAnimals();
        for (AnimalSortedList animals : animalLists) {
            Animal animal = animals.getAllTop().get(0);
            int x = animal.getPosition().x * fieldSize;
            int y = animal.getPosition().y * fieldSize;
            g.setColor(animal.getHealthColor());
            g.fillRect(x, y, fieldSize, fieldSize);
        }
    }
}





