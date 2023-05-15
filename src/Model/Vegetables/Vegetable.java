package Model.Vegetables;

import java.io.Serializable;

public abstract class Vegetable implements Comparable<Vegetable>, Serializable {
    private final String name;
    private final int calories;

    public Vegetable(String name, int calories) {
        this.name = name;
        this.calories = calories;
    }

    @Override
    public String toString() {
        return getName() + " - " + getCalories() + " калорій";
    }

    public String getName() {
        return name;
    }

    public int getCalories() {
        return calories;
    }

    @Override
    public int compareTo(Vegetable other) {
        return Integer.compare(calories, other.calories);
    }
}

