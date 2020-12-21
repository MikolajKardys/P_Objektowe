package agh.cs.project.Pages.SimulationPagePanels;

import javax.swing.*;

public abstract class AbstractSimulationPagePanel extends JPanel {

//klasa łącząca panele, które chcemy wyłączyć gdy trwa symulacja; nie jest interfejsem, bo część tych klas imlementuje inne

    abstract public void enableElements(boolean enable);
}
