package agh.cs.project.Pages;

import agh.cs.project.InputFile.Parameters;
import agh.cs.project.Sources.ProjectEngine;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuPage implements ActionListener {
    private final JFrame frame;

    private JButton runButton;
    private JPanel menuPanel;
    private JTextField widthField;
    private JTextField heightField;
    private JTextField animalNumberField;
    private JTextField startEnergyField;
    private JTextField moveEnergyField;
    private JTextField plantEnergyField;
    private JTextField jungeRatio;
    private JButton loadButton;

    private final Parameters parameters;

    public MenuPage(){
        this.frame = new JFrame();

        this.frame.setTitle("Main Menu");
        this.frame.setLocationRelativeTo(null);
        this.frame.setSize(420, 420);
        this.frame.setResizable(false);

        this.frame.setContentPane(this.menuPanel);


        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.setVisible(true);

        this.runButton.addActionListener(this);
        this.loadButton.addActionListener(this);

        this.parameters = new Parameters();
    }

    private boolean areArgumentsCorrect(){
        for (Component comp : menuPanel.getComponents()){
            try {
                if (comp instanceof JTextField) {
                    String text = ((JTextField) comp).getText();

                    if (comp.equals(this.jungeRatio)) {
                        Float.parseFloat(text);
                    } else {
                        Integer.parseInt(text);
                    }
                }
            }
            catch (NumberFormatException ignored) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == runButton){

            if (areArgumentsCorrect()){
                float jungeRatio = Float.parseFloat(this.jungeRatio.getText());
                int width = Integer.parseInt(widthField.getText());
                int height = Integer.parseInt(heightField.getText());
                int animalNumber = Integer.parseInt(animalNumberField.getText());
                if (jungeRatio < 0 || jungeRatio > 1){
                    JOptionPane.showMessageDialog(frame, "Jungle ratio must be between 0 and 1!!!", "Error!", JOptionPane.ERROR_MESSAGE);
                }
                else if (width <= 0 || height <= 0){
                    JOptionPane.showMessageDialog(frame, "Please select positive width and height!!!", "Error!", JOptionPane.ERROR_MESSAGE);
                }
                else if(animalNumber > width * height){
                    JOptionPane.showMessageDialog(frame, "Too many animals to fit on the map!!!", "Error!", JOptionPane.ERROR_MESSAGE);
                }
                else {
                    String [] options = {"Yes", "No"};
                    int x;
                    if (width > 100 || height > 60){
                        x = JOptionPane.showOptionDialog(frame,
                                "Selected size is over display bounds (width <= 100, height <= 60) and map will not be displayed. Do you wish to continue?",
                                "Over display bounds",
                                JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE,
                                null, options, options[0]);
                    }
                    else {
                        x = 0;
                    }
                    if (x == 0){
                        int startEnergy = Integer.parseInt(startEnergyField.getText());
                        int moveEnergy = Integer.parseInt(moveEnergyField.getText());
                        int plantEnergy = Integer.parseInt(plantEnergyField.getText());
                        new ProjectEngine(height, width, animalNumber, startEnergy, moveEnergy, plantEnergy, jungeRatio);
                    }
                }
            }
            else{
                JOptionPane.showMessageDialog(frame, "Incorrect simulation parameters!!!","Error!", JOptionPane.ERROR_MESSAGE);
            }

        }
        if (e.getSource() == this.loadButton){
            String [] params = parameters.getParameters();
            this.widthField.setText(params[0]);
            this.heightField.setText(params[1]);
            this.animalNumberField.setText(params[2]);
            this.startEnergyField.setText(params[3]);
            this.moveEnergyField.setText(params[4]);
            this.plantEnergyField.setText(params[5]);
            this.jungeRatio.setText(params[6]);
        }
    }

}
