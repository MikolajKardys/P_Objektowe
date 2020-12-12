package agh.cs.project;
class App {

    public static void main(String [] args) throws InterruptedException {


        int width = 20;
        int height = 30;


        ProjectEngine engine = new ProjectEngine(width, height, 20, 100, 3, 50);
        engine.run();




    }
}

