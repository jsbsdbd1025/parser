/**
 * 设计成通过具体解析器对数据进行序列／反序列化
 */
public class ParserBuilder {

    private Parser mParser;

    public ParserBuilder() {
        this(new GsonParser());
    }

    public ParserBuilder(Parser parser) {
        if (parser == null) {
            mParser = new GsonParser();
        }
    }

    public Parser create() {
        return mParser;
    }

}
