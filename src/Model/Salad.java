package Model;

import Core.Comparators;
import Exceptions.VegetableNotAvailableException;
import Model.Vegetable.Vegetable;

import java.io.Serializable;
import java.util.*;

public class Salad implements Serializable {
    private Deque<Vegetable> vegetables;

    public Salad() {
        vegetables = new ArrayDeque<>();
    }

    public void addVegetable(Vegetable vegetable) {
        vegetables.add(vegetable);
    }

    public int getTotalCalories() {
        int totalCalories = 0;
        for (Vegetable vegetable : vegetables) {
            totalCalories += vegetable.getCalories();
        }
        return totalCalories;
    }

    public void sortVegetablesByCalories() {
        List<Vegetable> sortedVegetables = new ArrayList<>(vegetables);
        sortedVegetables.sort(Comparators.Calories);
        vegetables = new ArrayDeque<>(sortedVegetables);
    }

    public void sortVegetablesByName() {
        List<Vegetable> sortedVegetables = new ArrayList<>(vegetables);
        sortedVegetables.sort(Comparators.NAME);
        vegetables = new ArrayDeque<>(sortedVegetables);
    }

    public List<Vegetable> getVegetablesByCalorieRange(int minCalories, int maxCalories) {
        List<Vegetable> selectedVegetables = new ArrayList<>();
        for (Vegetable vegetable : vegetables) {
            if (vegetable.getCalories() >= minCalories && vegetable.getCalories() <= maxCalories) {
                selectedVegetables.add(vegetable);
            }
        }
        return selectedVegetables;
    }

    public void removeVegetable(Vegetable vegetable) throws VegetableNotAvailableException {
        if (!vegetables.contains(vegetable)) {
            throw new VegetableNotAvailableException("Даний овоч не міститься у салаті!");
        }
        vegetables.remove(vegetable);
    }

    public void show(){
        for (Vegetable vegetable : vegetables) {
            System.out.println(vegetable.getName() + " - " + vegetable.getCalories() + " калорій");
        }
    }

    public Deque<Vegetable> getVegetables() {
        return vegetables;
    }
}
