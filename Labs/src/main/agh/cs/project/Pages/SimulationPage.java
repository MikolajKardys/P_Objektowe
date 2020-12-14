package agh.cs.project.Pages;

import agh.cs.project.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class SimulationPage implements IChangeObserver {
    private final JTextField [][] fields;
    private final GrassField field;

    private final Color grassColor = new Color(31, 119, 49);
    private final Color steppeColor = new Color(182,227,83);

    public SimulationPage(GrassField field, ProjectEngine engine){
        this.field = field;
        this.fields = new JTextField [field.width][field.height];

        int fieldSize = 50;   //bo czemu nie

        JFrame f = new JFrame("Simulation Map");
        f.setSize(fieldSize * field.height,fieldSize * field.width + fieldSize);

        f.setFocusableWindowState(false);
        f.setResizable(false);
        Color borderColor = new Color(131, 125, 74);
        f.getContentPane().setBackground(borderColor);

        JPanel contentPane = new JPanel();
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.PAGE_AXIS));
        f.setContentPane(contentPane);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(field.width, field.height));
        mainPanel.setBackground(borderColor);
        mainPanel.setPreferredSize(new Dimension(fieldSize * field.height,fieldSize * field.width));

        f.add(mainPanel);

        for(int x = 0; x < field.width; x++) {
            for(int y = 0; y < field.height; y++){

                JTextField newPanel = new JTextField();
                newPanel.setHorizontalAlignment(SwingConstants.CENTER);
                newPanel.setEditable(false);
                newPanel.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, borderColor));
                newPanel.setBackground(steppeColor);

                Vector2d curPosition = new Vector2d(x, y);
                if (field.objectAt(curPosition) instanceof Grass){
                    newPanel.setBackground(grassColor);
                }

                mainPanel.add(newPanel);
                this.fields[x][y] = newPanel;
            }
        }
        JPanel buttonPanel = new JPanel();
        buttonPanel.setPreferredSize(new Dimension(fieldSize * field.height,fieldSize));
        buttonPanel.setBackground(borderColor);

        JButton stopButton = new JButton("Stop Simulation");

        buttonPanel.add(stopButton);

        f.add(buttonPanel);

        f.setVisible(true);

        f.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                engine.interrupt();
            }
        });

        stopButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (stopButton.getText() == "Stop Simulation"){
                    stopButton.setText("Resume Simulation");
                    engine.pause();
                }
                else{
                    stopButton.setText("Stop Simulation");
                    engine.unpause();
                }

            }
        });

    }

    private void updateField(int indX, int indY) {
        JTextField text = this.fields[indX][indY];
        text.setText("");

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
            this.fields[indX][indY].setBackground(steppeColor);
        }
    }

    @Override
    public void changedEnergy(Animal animal){
        this.updateField(animal.getPosition().x, animal.getPosition().y);
    }
}
