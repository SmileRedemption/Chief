package Core;

import Exceptions.VegetableNotAvailableException;
import Model.Vegetable.Carrot;
import Model.Vegetable.Cucumber;
import Model.Vegetable.Tomato;
import Model.Vegetable.Vegetable;

public class VegetableFactory {
    public Vegetable Create(String name, int calories) throws VegetableNotAvailableException {
        if (name.equals("Carrot")){
            return new Carrot(name, calories);
        }
        if (name.equals("Cucumber")){
            return new Cucumber(name, calories);
        }
        if (name.equals("Tomato")){
            return new Tomato(name, calories);
        }

        throw new VegetableNotAvailableException("This is can't exist");
    }
}
