package demo.pubstatemachine.event;
public class AbstractEvent{
    private final String name;
    private final String code;

    public AbstractEvent(String name, String code) {
        this.name = name;
        this.code = code;
    }
    public String getCode() { return code;}
    public String getName() { return name;}
}
