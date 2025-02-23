import java.util.Scanner;

import utility.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;
        while (!exit) {
            CustomString.clearTerminal();
            System.out.println("[=======[ Welcome To IQ Puzzler ]=======]");
            System.out.println("");
            System.out.println("[Program] Silahkan pilih menu: ");
            System.out.println("          A. run on CLI");
            System.out.println("          B. run on GUI");
            System.out.println("          C. exit program");
            System.out.println("");
            System.out.print(">> "); char selectedOption = scanner.nextLine().charAt(0);
            switch (selectedOption) {
                case 'A', 'a' -> {CLI.runCLI();}
                case 'B', 'b' -> {GUI.runGUI();}
                case 'C', 'c' -> {exit = true;}
                default       -> {;}
            }
        }
        CustomString.clearTerminal();
        scanner.close();
    }    
}
