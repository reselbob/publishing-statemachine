package demo.pubstatemachine.event;

public class Command extends AbstractEvent {
    public Command(String name, String code) {
        super(name, code);
    }
}
