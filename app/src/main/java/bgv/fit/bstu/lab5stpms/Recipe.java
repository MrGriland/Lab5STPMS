package bgv.fit.bstu.lab5stpms;

import java.io.Serializable;

public class Recipe implements Comparable<Recipe>, Serializable {
    public String name;
    public String category;
    public String ingredients;
    public String recipe;
    public String time;


    @Override
    public int compareTo(Recipe o) {
        return name.compareTo(o.name);
    }
}
