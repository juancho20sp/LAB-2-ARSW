package edu.eci.arsw.primefinder;


import java.util.Scanner;

public class Main {

    public static void main(String[] args)  {
        Control control = Control.newControl();
        Scanner scanner = new Scanner(System.in);
        
        control.start();

        while(true){
            String myInput = scanner.nextLine();

            if(myInput.isEmpty()){
                System.out.println("waiting for entering a letter");
                control.rerun();
            }
        }
    }
}
