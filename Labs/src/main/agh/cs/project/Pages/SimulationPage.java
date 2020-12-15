package agh.cs.project.Pages;

import agh.cs.project.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class SimulationPage implements IChangeObserver {
    private final JButton [][] fields;
    private final GrassField field;

    private final Color grassColor = new Color(31, 119, 49);
    private final Color steppeColor = new Color(182,227,83);
    private final Color jungeColor = new Color(51, 153, 102);
    private final Color borderColor = new Color(131, 125, 74);
    private JPanel statsPanel;

    public SimulationPage(GrassField field, ProjectEngine engine){
        int minMapHeight = 400;
        int minMapWidth = 400;
        int statsWidth = 300;    //trzeba ustawić w formularzu

        this.field = field;
        this.fields = new JButton [field.width][field.height];

        int fieldSize = Math.min(600 / field.width, 1200 / field.height);
        fieldSize = Math.min(120, fieldSize);
        fieldSize = Math.max(fieldSize, 20);
        System.out.println(field.height + " " +  field.width + " " + 50);

        int mapHeight = fieldSize * field.width;
        int mapWidth = (int)(mapHeight * ((float)field.height/ (float)field.width));

        int totalHeight = Math.max(mapHeight, minMapHeight) + 100;
        int totalWidth = Math.max(mapWidth, minMapWidth) + statsWidth;

        JFrame f = new JFrame("Simulation Map");
        f.setSize(totalWidth, totalHeight);
        f.setFocusableWindowState(true);
        f.setResizable(false);

        JPanel contentPane = new JPanel();
        f.setContentPane(contentPane);
        contentPane.setBackground(borderColor);
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.LINE_AXIS));

//Początek lewej strony

        JPanel left = new JPanel();
        left.setLayout(new BoxLayout(left, BoxLayout.Y_AXIS));
        left.setPreferredSize(new Dimension(mapWidth, totalHeight));
        left.setMaximumSize(new Dimension(mapWidth, totalHeight));
        contentPane.add(left);

        JPanel mapPanel = new JPanel();
        mapPanel.setLayout(new GridLayout(field.width, field.height));
        mapPanel.setBackground(borderColor);
        mapPanel.setPreferredSize(new Dimension(mapWidth, mapHeight));

        left.add(mapPanel);

        for(int x = 0; x < field.width; x++) {
            for(int y = 0; y < field.height; y++){

                JButton newButton = new JButton();
                newButton.setEnabled(false);
                this.fields[x][y] = newButton;
                newButton.setHorizontalAlignment(SwingConstants.CENTER);
                newButton.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, borderColor));
                setGroundColor(x, y);

                Vector2d curPosition = new Vector2d(x, y);
                if (field.objectAt(curPosition) instanceof Grass){
                    newButton.setBackground(grassColor);
                }

                mapPanel.add(newButton);
            }
        }
        JPanel delayPanel = new JPanel();
        delayPanel.setPreferredSize( new Dimension(mapWidth, totalHeight - mapHeight - 50) );
        delayPanel.setBackground(borderColor);

        JLabel delayLabel = new JLabel("Current delay (in milliseconds): ");
        delayLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        delayLabel.setBackground(Color.WHITE);
        delayLabel.setOpaque(true);
        delayLabel.setFont(new Font(delayLabel.getFont().getName(), Font.BOLD, 16));
        delayPanel.add(delayLabel);

        JTextField delayText = new JTextField("200");
        delayText.setAlignmentX(Component.CENTER_ALIGNMENT);
        delayText.setPreferredSize(new Dimension(60, 30));
        delayText.setHorizontalAlignment(0);
        delayText.setEnabled(true);
        delayPanel.add(delayText);

        left.add(delayPanel);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setPreferredSize(new Dimension(mapWidth,50));
        buttonPanel.setBackground(borderColor);

        JButton stopButton = new JButton("Resume Simulation");
        stopButton.setPreferredSize(new Dimension(200, 30));
        buttonPanel.add(stopButton);

        left.add(buttonPanel);

        stopButton.addActionListener(e -> {
            if (stopButton.getText().equals("Stop Simulation")){
                stopButton.setText("Resume Simulation");
                delayText.setEnabled(true);
                engine.pause();
            }
            else{
                try {
                    int delay = Integer.parseInt(delayText.getText());
                    if (delay >= 0){
                        stopButton.setText("Stop Simulation");
                        engine.unpause(delay);
                        delayText.setEnabled(false);
                    }
                    else{
                        JOptionPane.showMessageDialog(f, "Delay value must be equal or above 0");
                    }
                }
                catch (NumberFormatException ignored) {
                    JOptionPane.showMessageDialog(f, "Invalid delay value!!!");
                }
            }

        });

//Koniec lewej strony, początek prawej

        JPanel right = new JPanel();
        right.setPreferredSize(new Dimension(statsWidth,totalHeight));
        right.setBackground(Color.white);
        contentPane.add(right);

        right.add(statsPanel);

        f.setVisible(true);

        f.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                engine.interrupt();
            }
        });



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
        text.setText("");

        Vector2d position = new Vector2d(indX, indY);
        String topSprite = this.field.getTopStringAt(position);
        if (topSprite != null) {
            text.setText(topSprite);
            text.setBackground(this.field.getColorAt(position));
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
}
