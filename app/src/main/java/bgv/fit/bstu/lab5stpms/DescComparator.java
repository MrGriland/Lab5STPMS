package bgv.fit.bstu.lab5stpms;

import java.util.Comparator;

public class DescComparator implements Comparator<Recipe> {

    @Override
    public int compare(Recipe o1, Recipe o2) {
        return o1.name.compareTo(o2.name)*(-1);
    }
}
