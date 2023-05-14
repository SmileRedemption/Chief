package View;

import Core.VegetableFactory;
import Exceptions.InvalidInputException;
import Exceptions.VegetableNotAvailableException;
import Model.Salad;
import Model.Vegetable.Vegetable;

import java.io.*;
import java.util.*;

public class Menu {
    private final Scanner scanner;
    private final VegetableFactory vegetableFactory;
    private Salad salad;

    public Menu(Scanner scanner, Salad salad) {
        this.scanner = scanner;
        this.salad = salad;
        this.vegetableFactory = new VegetableFactory();
    }

    public void displayMenu() {
        System.out.println("МЕНЮ:");
        System.out.println("1. Додати овоч");
        System.out.println("2. Видалити овоч");
        System.out.println("3. Вивести список овочів у салаті");
        System.out.println("4. Розрахувати загальну калорійність салату");
        System.out.println("5. Сортувати овочі за калоріями");
        System.out.println("6. Зберегти салат у файл");
        System.out.println("7. Завантажити салат з файлу");
        System.out.println("0. Вийти");
    }

    public void run() {
        int choice;
        do {
            displayMenu();
            choice = getChoice();

            switch (choice) {
                case 1 -> addVegetable();
                case 2 -> removeVegetable();
                case 3 -> displayVegetables();
                case 4 -> calculateTotalCalories();
                case 5 -> sortVegetablesByCalories();
                case 6 -> saveSaladToFile();
                case 7 -> loadSaladFromFile();
                case 0 -> System.out.println("Дякую за використання меню!");
                default -> System.out.println("Невірний вибір. Спробуйте ще раз.");
            }
        } while (choice != 0);
    }

    private int getChoice() {
        int choice = -1;
        try {
            System.out.print("Ваш вибір: ");
            choice = scanner.nextInt();
            scanner.nextLine();
        } catch (InputMismatchException e) {
            scanner.nextLine();
        }
        return choice;
    }

    private void addVegetable() {
        try {
            System.out.print("Введіть назву овоча: ");
            String name = scanner.nextLine();
            System.out.print("Введіть калорійність овоча: ");
            int calories = scanner.nextInt();
            scanner.nextLine(); // Ігноруємо символ нового рядка після nextInt()

            if (calories <= 0) {
                throw new InvalidInputException("Некоректна калорійність овоча!");
            }

            Vegetable vegetable = vegetableFactory.Create(name, calories);
            salad.addVegetable(vegetable);
            System.out.println("Овоч " + name + " успішно додано до салату!");
        } catch (InvalidInputException | VegetableNotAvailableException e) {
            System.out.println(e.getMessage());
        }
    }

    private void removeVegetable() {
        System.out.print("Введіть назву овоча, який потрібно видалити: ");
        String name = scanner.nextLine();

        try {
            List<Vegetable> vegetables = new ArrayList<>(salad.getVegetables());
            Vegetable vegetableToRemove = null;
            for (Vegetable vegetable : vegetables) {
                if (vegetable.getName().equalsIgnoreCase(name)) {
                    vegetableToRemove = vegetable;
                    break;
                }
            }

            if (vegetableToRemove != null) {
                salad.removeVegetable(vegetableToRemove);
                System.out.println("Овоч " + name + " успішно видалено з салату!");
            } else {
                throw new VegetableNotAvailableException("Даний овоч не міститься у салаті!");
            }
        } catch (VegetableNotAvailableException e) {
            System.out.println(e.getMessage());
        }
    }

    private void displayVegetables() {
        salad.show();
    }

    private void calculateTotalCalories() {
        int totalCalories = salad.getTotalCalories();
        System.out.println("Загальна калорійність салату: " + totalCalories + " калорій");
    }

    private void sortVegetablesByCalories() {
        salad.sortVegetablesByCalories();
        System.out.println("Овочі у салаті відсортовано за калоріями.");
    }

    private void saveSaladToFile() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("salad.dat"))) {
            oos.writeObject(salad);
            System.out.println("Салат збережено до файлу.");
        } catch (IOException e) {
            System.out.println("Помилка при збереженні салату до файлу: " + e.getMessage());
        }
    }

    private void loadSaladFromFile() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("salad.dat"))) {
            salad = (Salad) ois.readObject();
            System.out.println("Салат завантажено з файлу.");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Помилка при завантаженні салату з файлу: " + e.getMessage());
        }
    }
}