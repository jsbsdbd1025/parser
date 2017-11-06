import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class GsonParserTest {

    GsonParser gsonParser;
    Person<Number> person;

    @Before
    public void setUp() {
        gsonParser = new GsonParser();

        person = new Person("ben", 18, 1.78f, 82347983.984297);

        List<Number> nums = new ArrayList();
        for (int i = 0; i < 10; i++) {
            nums.add(new Number(i, String.valueOf(i)));
        }
        person.setNums(nums);
    }


    @Test
    public void toSerializeTest() {
        System.out.println(gsonParser.toSerialize(person));
    }

    @Test
    public void fromSerialzeTest() {
        String str = "  {\n" +
                "                \"age\":18\n" +
                "                }";

        Person person = gsonParser.fromSerialize(str, Person.class);

        System.out.println(gsonParser.toSerialize(person));
    }





}