package Core;

import Model.Vegetable.Vegetable;

import java.util.Comparator;

public class Comparators
{
    public static Comparator<Vegetable> NAME = new Comparator<Vegetable>() {
        @Override
        public int compare(Vegetable o1, Vegetable o2) {
            return o1.getName().compareTo(o2.getName());
        }
    };
    public static Comparator<Vegetable> Calories = new Comparator<Vegetable>() {
        @Override
        public int compare(Vegetable o1, Vegetable o2) {
            return o1.getCalories() - o2.getCalories();
        }
    };
}