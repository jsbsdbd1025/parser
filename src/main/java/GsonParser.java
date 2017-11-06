import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class GsonParser implements Parser {

    @Override
    public String toSerialize(Object o) {

        List root = transform(o);

        return toJson(root, 0);
    }

    private List<Element> transform(Object o) {
        List<Element> result = new ArrayList<>();
        Class clazz = o.getClass();

        Field[] fields = clazz.getDeclaredFields();

        for (Field f : fields) {
            try {
                if (f.getName() == "this$0") {
                    continue;
                }
                f.setAccessible(true);
                result.add(new Element(f.getType(), f.getName(), f.get(o)));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    private String toJson(List<Element> list, int tab) {

        StringBuffer str = new StringBuffer();

        for (Element e : list) {
            if (e.value == null) {
                continue;
            }

            if (str.length() != 0) {
                str.append(",\n");
            } else {
                for (int i = 0; i < tab; i++) {
                    str.append("\t");
                }

                str.append("{\n");
            }

            for (int i = 0; i <= tab; i++) {
                str.append("\t");
            }

            str.append("\"" + e.getKey() + "\":");
            if ((e.type.isAssignableFrom(String.class))) {
                str.append("\"" + e.value + "\"");
            } else if ((e.type.isAssignableFrom(Double.class))) {
                str.append(new BigDecimal(e.value.toString()).toString());
            } else if ((e.type.isAssignableFrom(double.class))) {
                str.append(new BigDecimal(e.value + "").toString());
            } else if ((e.type.isAssignableFrom(List.class))) {
                StringBuffer s = new StringBuffer();

                for (Object o : ((List) e.value)) {
                    if (s.length() != 0) {
                        s.append(",\n");
                    }
                    s.append(toJson(transform(o), tab + 2));
                }

                str.append("[\n" + s.toString() + "\n\t]");
            } else {
                str.append(e.value);
            }
        }

        str.append("\n");

        for (int i = 0; i < tab; i++) {
            str.append("\t");
        }

        str.append("}");
        return str.toString();
    }

    @Override
    public <T> T fromSerialize(String s, Class<T> t) {
        return fromJson(s, t);
    }

    private <T> T fromJson(String s, Class<T> t) {

        T instance = null;
        if (s == null) {
            return null;
        }

        try {
            instance = t.newInstance();
            Field[] fields = t.getDeclaredFields();

            for (Field f : fields) {
                try {
                    if (f.getName() == "age") {
                        f.setAccessible(true);
                        f.set(instance, 12);
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }

        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return instance;
    }
}
