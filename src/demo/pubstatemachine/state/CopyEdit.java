package demo.pubstatemachine.state;

import demo.pubstatemachine.event.AbstractEvent;

public class CopyEdit extends AbstractState{
    public void enter(){
        System.out.println("Now in the CopyEdit state");
    }

    public void update(AbstractEvent event){
        System.out.println(event.getCode());
    }
}
