package bean;

import java.util.ArrayList;
import java.util.List;

public class Number {
    int i;
    String n;
    List<SomeThing> someThings;

    public Number(int i, String n) {
        this.i = i;
        this.n = n;
        someThings = new ArrayList<>();
        for (int k = 0; k < 3; k++) {
            someThings.add(new SomeThing(String.valueOf(k)));
        }
    }
}
