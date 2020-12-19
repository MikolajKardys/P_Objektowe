package agh.cs.project.Pages;

import agh.cs.project.*;
import agh.cs.project.Pages.SimulationPagePanels.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class SimulationPage {
    private final MapPanel map;
    private final StatsPanel stats;
    private final AnimalPanel curAnimal;

    private final GrassField field;

    private boolean isRunning;
    private final JFrame f;

    public SimulationPage(GrassField field, ProjectEngine engine){
        Color borderColor = new Color(131, 125, 74);


        boolean tooBig = engine.tooBig;
        this.field = field;

        int minMapHeight = 650;
        int minMapWidth = 400;
        int statsWidth = 400;    //trzeba ustawić w formularzu

        int fieldSize;
        int mapWidth;
        int mapHeight;
        if (!tooBig){
            fieldSize = Math.min(600 / field.width, 1100 / field.height);
            fieldSize = Math.min(100, fieldSize);
            fieldSize = Math.max(fieldSize, 10);
            mapHeight = fieldSize * field.width;
            mapWidth = (int) (mapHeight * ((float) field.height / (float) field.width));
        }
        else {
            fieldSize = 50;
            mapWidth = minMapWidth;
            mapHeight = minMapHeight;
        }

        int totalHeight = Math.max(mapHeight, minMapHeight) + 100;
        int totalWidth = Math.max(mapWidth, minMapWidth) + statsWidth;

        this.f = new JFrame("Simulation Map");
        f.setSize(totalWidth, totalHeight);
        f.setFocusableWindowState(true);
        f.setResizable(false);

        JPanel contentPane = new JPanel();
        f.setContentPane(contentPane);
        contentPane.setBackground(borderColor);
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.LINE_AXIS));

//Początek prawej strony

        JPanel left = new JPanel();
        left.setBackground(borderColor);
        left.setLayout(new BoxLayout(left, BoxLayout.Y_AXIS));
        left.setPreferredSize(new Dimension(Math.max(mapWidth, minMapWidth), totalHeight));
        left.setMaximumSize(new Dimension(Math.max(mapWidth, minMapWidth), totalHeight));
        contentPane.add(left);

        if (!tooBig) {
            this.map = new MapPanel(this, field, mapWidth, mapHeight, fieldSize);    //mapa
            left.add(this.map);
        }
        else {
            this.map = null;
        }
        left.add(Box.createVerticalGlue());

        DelayPanel delayPanel = new DelayPanel(minMapWidth, mapHeight, totalHeight);     //panel opóźnienia
        delayPanel.setBackground(borderColor);
        left.add(delayPanel);

        ButtonPanel buttonPanel = new ButtonPanel();
        buttonPanel.setBackground(borderColor);
        delayPanel.setAlignmentY(Component.BOTTOM_ALIGNMENT);
        left.add(buttonPanel);

        this.isRunning = false;

        buttonPanel.stopButton.addActionListener(e -> {
            synchronized (engine) {
                if (buttonPanel.stopButton.getText().equals("Stop Simulation")) {
                    buttonPanel.stopButton.setText("Resume Simulation");
                    delayPanel.delayText.setEnabled(true);
                    this.isRunning = false;
                    engine.pause();
                } else {
                    try {
                        int delay = Integer.parseInt(delayPanel.delayText.getText());
                        if (delay > 0) {
                            buttonPanel.stopButton.setText("Stop Simulation");
                            engine.unpause(delay);
                            delayPanel.delayText.setEnabled(false);
                            this.isRunning = true;
                        } else {
                            JOptionPane.showMessageDialog(f, "Delay value must be a positive integer!!!", "Error!", JOptionPane.ERROR_MESSAGE);
                        }
                    } catch (NumberFormatException ignored) {
                        JOptionPane.showMessageDialog(f, "Invalid delay value!!!", "Error!", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        //Początek lewej strony
        JPanel right = new JPanel();
        right.setPreferredSize(new Dimension(statsWidth, totalHeight));
        right.setLayout(new BoxLayout(right, BoxLayout.PAGE_AXIS));
        contentPane.add(right);

        this.stats = new StatsPanel(statsWidth);
        right.add(stats);

        this.curAnimal = new AnimalPanel(statsWidth, buttonPanel.stopButton);
        right.add(curAnimal);

        f.setVisible(true);

        f.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                engine.cancel();
            }
        });

    }

//Metody

    public MapPanel getMap (){
        return this.map;
    }

    public StatsPanel getStats (){
        return this.stats;
    }

    public void actionPerformed(ActionEvent e){
        if (!isRunning){
            JButton source = (JButton) e.getSource();
            Vector2d position = Vector2d.fromString(source.getName());
            if (field.objectAt(position) instanceof AnimalSortedList){
                Animal selected = ((AnimalSortedList) field.objectAt(position)).getAllTop().get(0);
                this.curAnimal.selectedAnimal(selected);
            }
            else {
                JOptionPane.showMessageDialog(f, "There aren't any animals on this field!!!", "Error!", JOptionPane.ERROR_MESSAGE);
            }
        }
        else {
            JOptionPane.showMessageDialog(f, "Can't select animal while the simulation is running!!!", "Error!", JOptionPane.ERROR_MESSAGE);
        }
    }
}





