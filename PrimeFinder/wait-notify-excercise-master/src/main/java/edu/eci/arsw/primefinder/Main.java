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




        /*while (true){
            String myInput = scanner.next();

            System.out.println("MY INPUT:" + myInput);
            System.out.println("LENGTH:" + myInput.length());


            if (myInput.length() == 1){
                // STOP
                System.out.println("   ");
                System.out.println("   ");
                System.out.println("STOP");
                System.out.println("   ");
                System.out.println("   ");

                control.stopThreads();
                scanner.close();
                return;


            } else {
                scanner.close();
                return;
            }
        }*/





    }
	
}
