package edu.eci.arsw.primefinder;


import java.util.Scanner;

public class Main {

    public static void main(String[] args)  {
        Control control = Control.newControl();
        Scanner scanner = new Scanner(System.in);
        
        control.start();
        boolean cnt = true;

        while(cnt){
            String myInput = scanner.nextLine();

            if(myInput.isEmpty()) {
//                System.out.println("waiting for entering a letter");
                cnt = control.rerun();
            }
        }

        System.out.println("FINAL. ");
    }
}
