package agh.cs.project.Pages;

import agh.cs.project.ProjectEngine;

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

    }

    private boolean areArgumentsCorrect(){
        for (Component comp : menuPanel.getComponents()){
            if (comp instanceof JTextField){
                String text = ((JTextField) comp).getText();
                if (text.isBlank()) {
                    return false;
                }
                for (char c : text.toCharArray()) {
                    if (!Character.isDigit(c)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == runButton){

            if (areArgumentsCorrect()){
                int width = Integer.parseInt(widthField.getText());
                int height = Integer.parseInt(heightField.getText());
                int animalNumber = Integer.parseInt(animalNumberField.getText());
                int startEnergy = Integer.parseInt(startEnergyField.getText());
                int moveEnergy = Integer.parseInt(moveEnergyField.getText());
                int plantEnergy = Integer.parseInt(plantEnergyField.getText());
                new ProjectEngine(height, width, animalNumber, startEnergy, moveEnergy, plantEnergy).start();
            }
            else{
                JOptionPane.showMessageDialog(frame, "Incorrect simulation parameters!!!");
            }

        }
    }

}
