package agh.cs.project.Pages.SimulationPagePanels;


public interface IMapViz {

//Iterfejs wspólny dla mapy i "mapy zastępczej"

    void highLight(int indX, int indY);

    void removeHighLight(int indX, int indY);
}
