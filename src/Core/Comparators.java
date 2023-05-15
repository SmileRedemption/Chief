package Core;

import Model.Vegetables.Vegetable;

import java.util.Comparator;

public class Comparators
{
    public static Comparator<Vegetable> NAME = Comparator.comparing(Vegetable::getName);
    public static Comparator<Vegetable> Calories = Comparator.comparingInt(Vegetable::getCalories);
}