package agh.cs.lab1;

public class World {
    public static void print_comm(String c){
        switch(c){
            case "f":
                System.out.println("Zwierzak idzie do przodu");
                break;
            case "b":
                System.out.println("Zwierzak idzie do tyłu");
                break;
            case "r":
                System.out.println("Zwierzak skręca w prawo");
                break;
            case "l":
                System.out.println("Zwierzak skręca w lewo");
                break;
        }
    }
    public static void run(String [] tab){
        for (String arg : tab){
            print_comm(arg);
        }
    }
    public static void main(String [] args) {
        System.out.println("Start Systemu");
        run(args);
        System.out.println("Stop Systemu");
    }
}

