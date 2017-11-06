/**
 *
 */

public interface Parser {

    String toSerialize(Object o);

    <T> T fromSerialize(String s, Class<T> t);
}
