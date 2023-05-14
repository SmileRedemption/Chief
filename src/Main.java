import Model.Salad;

import View.*;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Salad salad = new Salad();

        Menu menu = new Menu(scanner, salad);
        menu.run();
    }
}




