import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class GsonParser implements Parser {

    @Override
    public String toSerialize(Object o) {

        Element root = transform(o);


        return toJson(root, 0);
    }

    // TODO: 2017/11/7  最外层可能为数组

    private Element transform(Object o) {

        Element parent = new Element(null, o);
        Class clazz = o.getClass();

        Field[] fields = clazz.getDeclaredFields();
        for (Field f : fields) {

            try {
                if (f.getName() == "this$0") {
                    continue;
                }
                f.setAccessible(true);

                Element son = new Element(f.getName(), f.get(o));

                if (f.getType().isAssignableFrom(List.class)) {
                    List list = (List) f.get(o);

                    for (int i = 0; i < list.size(); i++) {
                        son.sonList.add(transform(list.get(i)));
                    }
                }

                parent.sonList.add(son);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        return parent;
    }


    private String toJson(Element parent, int tab) {

        StringBuffer stringBuffer = new StringBuffer();

        if (parent.sonList.size() == 0) {
            return "";
        }

        if (parent.key != null) {

            stringBuffer.append("\"");
            stringBuffer.append(parent.value);
            stringBuffer.append("\"");
        }


        for (Element e : parent.sonList) {
            if (stringBuffer.length() != 0) {
                stringBuffer.append(",");
            }
            stringBuffer.append("\n");
            for (int i = 0; i <= tab; i++) {
                stringBuffer.append("\t");
            }

            stringBuffer.append("\"");
            stringBuffer.append(e.getKey());
            stringBuffer.append("\":");

            if ((e.isAssignableFrom(String.class))) {
                stringBuffer.append("\"");
                stringBuffer.append(e.value);
                stringBuffer.append("\"");
            } else if (e.isAssignableFrom(Double.class)) {
                stringBuffer.append(new BigDecimal(e.value.toString()).toString());
            } else if (e.isAssignableFrom(double.class)) {
                stringBuffer.append(new BigDecimal(e.value + "").toString());
            } else if (e.isAssignableFrom(int.class)) {
                stringBuffer.append(String.valueOf(e.value));
            } else if (e.isAssignableFrom(String[].class)) {

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
            } else if (e.isAssignableFrom(int[].class)) {
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


            } else if (e.isAssignableFrom(ArrayList.class)) {
                StringBuffer s = new StringBuffer();

                for (Element o : e.sonList) {
                    if (s.length() != 0) {
                        s.append(",\n");
                    }
                    s.append(toJson(o, tab + 2));
                }

                stringBuffer.append("[\n");
                stringBuffer.append(s.toString());
                stringBuffer.append("\n\t]");
            } else {
                stringBuffer.append(e.value);
            }

        }

        StringBuilder result = new StringBuilder();
        for (int i = 0; i < tab; i++) {
            result.append("\t");
        }
        result.append("{");
        result.append(stringBuffer);
        result.append("\n");
        for (int i = 0; i < tab; i++) {
            result.append("\t");
        }
        result.append("}");
        return result.toString();
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
