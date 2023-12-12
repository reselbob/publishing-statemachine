package demo.pubstatemachine;

import demo.pubstatemachine.event.AbstractEvent;
import demo.pubstatemachine.event.Command;
import demo.pubstatemachine.state.*;

public class Main {
    public static void main(String[] args) {
        AbstractState.editable = new Editable();
        AbstractState.graphicEdit = new GraphicEdit();
        AbstractState.copyEdit = new CopyEdit();
        AbstractState.awaitingEdits = new AwaitingEdits();
        AbstractState.awaitingPublish = new AwaitingPublish();
        AbstractState.publish = new Publish();
        AbstractState.current = AbstractState.editable;

        while(true){
            AbstractEvent command = new Command("Start process", "START");
            AbstractState.current.enter();
            AbstractState.current.update(command);
        }
    }
}