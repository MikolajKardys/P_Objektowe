package agh.cs.lab1;

public class World {
    public static void run(String dir[]){
        int i = 0;
        for (; i < dir.length - 1; i += 1){
            switch(dir[i]){
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
        switch(dir[i]){
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
    public static void main(String[] args) {
        System.out.print("Start\n");
        String dir [] = {"f", "f", "r", "l"};
        run(dir);
        System.out.print("Stop");
    }
}
