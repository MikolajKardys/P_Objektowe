package agh.cs.lab1;

public class World {
    public static void print_comm(Dir c){
        switch(c){
            case f:
                System.out.println("Zwierzak idzie do przodu");
                break;
            case b:
                System.out.println("Zwierzak idzie do tyłu");
                break;
            case r:
                System.out.println("Zwierzak skręca w prawo");
                break;
            case l:
                System.out.println("Zwierzak skręca w lewo");
                break;
        }
    }
    public static void run(Dir [] tab){
        for (Dir arg : tab){
            print_comm(arg);
        }
    }
    public static Dir [] replace(String [] args){
        Dir [] arg_tab = new Dir[args.length];
        for (int i = 0; i < args.length; i += 1){
            switch(args[i]){
                case "f":
                    arg_tab[i] = Dir.f;
                    break;
                case "b":
                    arg_tab[i] = Dir.b;
                    break;
                case "r":
                    arg_tab[i] = Dir.r;
                    break;
                case "l":
                    arg_tab[i] = Dir.l;
                    break;
            }
        }
        return arg_tab;
    }
    public static void main(String [] args) {
        System.out.println("Start Systemu");
        Dir [] arg_tab = replace(args);
        run(arg_tab);
        System.out.println("Stop Systemu");
    }
}

