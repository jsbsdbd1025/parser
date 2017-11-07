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

    // TODO: 2017/11/7  最外层可能为数组

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

        StringBuffer stringBuffer = new StringBuffer();

        for (Element e : list) {
            if (e.value == null) {
                continue;
            }

            if (stringBuffer.length() != 0) {
                stringBuffer.append(",\n");
            } else {
                for (int i = 0; i < tab; i++) {
                    stringBuffer.append("\t");
                }

                stringBuffer.append("{\n");
            }

            for (int i = 0; i <= tab; i++) {
                stringBuffer.append("\t");
            }

            stringBuffer.append("\"");
            stringBuffer.append(e.getKey());
            stringBuffer.append("\":");
            if ((e.type.isAssignableFrom(String.class))) {
                stringBuffer.append("\"");
                stringBuffer.append(e.value);
                stringBuffer.append("\"");
            } else if (e.type.isAssignableFrom(Double.class)) {
                stringBuffer.append(new BigDecimal(e.value.toString()).toString());
            } else if (e.type.isAssignableFrom(double.class)) {
                stringBuffer.append(new BigDecimal(e.value + "").toString());
            } else if (e.type.isAssignableFrom(int[].class)) {
                StringBuilder s = new StringBuilder();
                for (int v : (int[]) e.value) {
                    if (s.length() != 0) {
                        s.append(",\n");
                    }
                    for (int i = 0; i < tab + 2; i++) {
                        s.append("\t");
                    }
                    s.append(v);
                }
                stringBuffer.append("[\n");
                stringBuffer.append(s.toString());
                stringBuffer.append("\n\t]");

            } else if (e.type.isAssignableFrom(String[].class)) {

                StringBuilder s = new StringBuilder();
                for (String v : (String[]) e.value) {
                    if (s.length() != 0) {
                        s.append(",\n");
                    }
                    for (int i = 0; i < tab + 2; i++) {
                        s.append("\t");
                    }
                    s.append("\"");
                    s.append(v);
                    s.append("\"");
                }
                stringBuffer.append("[\n");
                stringBuffer.append(s.toString());
                stringBuffer.append("\n\t]");
            } else if ((e.type.isAssignableFrom(List.class))) {
                StringBuffer s = new StringBuffer();

                for (Object o : ((List) e.value)) {
                    if (s.length() != 0) {
                        s.append(",\n");
                    }
                    s.append(toJson(transform(o), tab + 2));
                }

                stringBuffer.append("[\n");
                stringBuffer.append(s.toString());
                stringBuffer.append("\n\t]");
            } else {
                stringBuffer.append(e.value);
            }
        }

        stringBuffer.append("\n");

        for (int i = 0; i < tab; i++) {
            stringBuffer.append("\t");
        }

        stringBuffer.append("}");
        return stringBuffer.toString();
    }

    @Override
    public <T> T fromSerialize(String s, Class<T> t) {
        return fromJson(s, t);
    }


    private List<Element> transform(String s) {
        List<Element> result = new ArrayList<>();

//            Class clazz = o.getClass();
//
//            Field[] fields = clazz.getDeclaredFields();
//
//            for (Field f : fields) {
//                try {
//                    if (f.getName() == "this$0") {
//                        continue;
//                    }
//                    f.setAccessible(true);
//                    result.add(new Element(f.getType(), f.getName(), f.get(o)));
//                } catch (IllegalAccessException e) {
//                    e.printStackTrace();
//                }
//            }
        return result;
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
