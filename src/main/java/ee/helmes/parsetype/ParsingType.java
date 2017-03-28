package ee.helmes.parsetype;

public class ParsingType {
    private Type type;

    public ParsingType(Type type){
        this.type = type;
    }

    public void setType(Type type) {
        this.type = type;
    }
    public void parse(){
        type.parse();
    }
}
