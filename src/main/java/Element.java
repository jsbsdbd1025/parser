import java.util.ArrayList;
import java.util.List;

public class Element {
    String key;
    Object value;
    List<Element> sonList = new ArrayList<>();

    public Element() {
    }

    public Element(String key, Object value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public boolean has(String key) {
        for (Element e : sonList) {
            if (e.key.equals(key)) {
                return true;
            }
        }
        return false;
    }

    public Element get(String key) {
        for (Element e : sonList) {
            if (e.key.equals(key)) {
                return e;
            }
        }
        return null;
    }

    public boolean isAssignableFrom(Class<?> cls) {
        return value.getClass().isAssignableFrom(cls);
    }
}
