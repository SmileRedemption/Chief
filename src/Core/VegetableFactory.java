package Core;

import Exceptions.VegetableNotAvailableException;
import Model.Vegetables.*;

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
        else{
            return new NoneVegetable(name, calories);
        }
    }
}
