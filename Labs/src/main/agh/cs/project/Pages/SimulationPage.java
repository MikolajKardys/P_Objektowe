package agh.cs.project.Pages;

import agh.cs.project.Pages.SimulationPagePanels.*;
import agh.cs.project.Pages.SimulationPagePanels.Stats.StatsPanel;
import agh.cs.project.Sources.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class SimulationPage implements ActionListener{

//Klasa nadzorująca komunikację między symulacją a jej reprezentacją, statystykami etc.

    private final IMapViz map;
    private final StatsPanel stats;
    private final SelectPanel selectAnimal;
    private final JButton stopButton;

    private final GrassField field;

    private boolean isRunning;
    private final JFrame f;

    public SimulationPage(GrassField field, ProjectEngine engine){
        Color borderColor = new Color(131, 125, 74);

        List<AbstractSimulationPagePanel> enableList = new ArrayList<>();

        boolean tooBig = engine.tooBig;
        this.field = field;

        int minMapHeight = 650;
        int minMapWidth = 400;
        int statsWidth = 400;

        int fieldSize;
        int mapWidth;
        int mapHeight;
        if (!tooBig){
            fieldSize = Math.min(650 / field.height, 1100 / field.width);
            fieldSize = Math.min(100, fieldSize);
            fieldSize = Math.max(fieldSize, 10);
            mapHeight = fieldSize * field.height;
            mapWidth = (int) (mapHeight * ((float) field.width / (float) field.height));
        }
        else {
            fieldSize = 50;
            mapWidth = minMapWidth;
            mapHeight = minMapHeight;
        }

        int totalHeight = Math.max(mapHeight, minMapHeight) + 100;
        int totalWidth = Math.max(mapWidth, minMapWidth) + statsWidth;

        f = new JFrame("Simulation Map");
        f.setSize(totalWidth, totalHeight);
        f.setFocusableWindowState(true);
        f.setResizable(false);

        JPanel contentPane = new JPanel();
        f.setContentPane(contentPane);
        contentPane.setBackground(borderColor);
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.LINE_AXIS));

//Początek lewej strony

        JPanel left = new JPanel();
        left.setBackground(borderColor);
        left.setLayout(new BoxLayout(left, BoxLayout.Y_AXIS));
        left.setPreferredSize(new Dimension(Math.max(mapWidth, minMapWidth), totalHeight));
        left.setMaximumSize(new Dimension(Math.max(mapWidth, minMapWidth), totalHeight));
        contentPane.add(left);

        if (!tooBig) {
            map = new MapPanel(this, field, mapWidth, mapHeight, fieldSize);    //mapa
            left.add((MapPanel)map);
        }
        else {
            map = new PositionPanel(this, field, mapHeight);
            left.add((PositionPanel)map);
        }
        left.add(Box.createVerticalGlue());

        DelayPanel delayPanel = new DelayPanel(minMapWidth, mapHeight, totalHeight);     //panel opóźnienia
        delayPanel.setBackground(borderColor);
        left.add(delayPanel);

        StartButtonPanel buttonPanel = new StartButtonPanel();
        buttonPanel.setBackground(borderColor);
        stopButton = buttonPanel.getStopButton();

        delayPanel.setAlignmentY(Component.BOTTOM_ALIGNMENT);
        enableList.add(delayPanel);
        left.add(buttonPanel);

        isRunning = false;

//Początek prawej strony

        JPanel right = new JPanel();
        right.setPreferredSize(new Dimension(statsWidth, totalHeight));
        right.setLayout(new BoxLayout(right, BoxLayout.PAGE_AXIS));
        contentPane.add(right);

        stats = new StatsPanel(statsWidth);
        enableList.add(stats);
        right.add(stats);

        TrackingPanel trackAnimal = new TrackingPanel(statsWidth, buttonPanel.getStopButton());
        enableList.add(trackAnimal);

        selectAnimal = new SelectPanel(statsWidth, trackAnimal, stats);
        enableList.add(selectAnimal);


        right.add(selectAnimal);
        right.add(trackAnimal);

        f.setVisible(true);

        f.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                engine.cancel();
            }
        });

//Listenery guzików; zadeklarowane tu, żeby nie mieszały się z tymi odpowiedzialnymi za wybór zwierząt

        buttonPanel.getStatsButton().addActionListener(e -> stats.loadFile());

        stopButton.addActionListener(e -> {
            synchronized (engine) {
                if (isRunning) {
                    stopButton.setText("Resume Simulation");
                    delayPanel.getDelayText().setEnabled(true);
                    isRunning = false;
                    engine.pause();
                    for (AbstractSimulationPagePanel panel : enableList){
                        panel.enableElements(true);
                    }
                    buttonPanel.getStatsButton().setEnabled(true);
                } else {
                    if (stats.isHighlighted()){
                        JOptionPane.showMessageDialog(f, "Please remove highlight before continuing!!!", "Error!", JOptionPane.ERROR_MESSAGE);
                    }
                    else {
                        try {
                            int delay = Integer.parseInt(delayPanel.getDelayText().getText());
                            if (delay > 0) {
                                for (AbstractSimulationPagePanel panel : enableList){
                                    panel.enableElements(false);
                                }
                                buttonPanel.getStatsButton().setEnabled(false);
                                stopButton.setText("Stop Simulation");
                                engine.unpause(delay);
                                delayPanel.getDelayText().setEnabled(false);
                                isRunning = true;
                            } else {
                                JOptionPane.showMessageDialog(f, "Delay value must be a positive integer!!!", "Error!", JOptionPane.ERROR_MESSAGE);
                            }
                        } catch (NumberFormatException ignored) {
                            JOptionPane.showMessageDialog(f, "Invalid delay value!!!", "Error!", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                }
            }
        });

    }

//Metody

    public IMapViz getMap (){
        return map;
    }

    public StatsPanel getStats (){
        return stats;
    }

    public void terminated(){
        JOptionPane.showMessageDialog(f, "All animals have died. Simulation has terminated.", "End of simulation", JOptionPane.WARNING_MESSAGE);
        stopButton.doClick();
        stopButton.setEnabled(false);
    }

//

    @Override
    public void actionPerformed(ActionEvent e){
        if (!isRunning){
            JButton source = (JButton) e.getSource();
            Vector2d position = new Vector2d(source.getName());
            if (field.objectAt(position) instanceof AnimalSortedList){
                Animal selected = ((AnimalSortedList) field.objectAt(position)).getAllTop().get(0);
                selectAnimal.selectedAnimal(selected);
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





