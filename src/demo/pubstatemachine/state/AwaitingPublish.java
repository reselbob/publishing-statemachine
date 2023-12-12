package demo.pubstatemachine.state;

import demo.pubstatemachine.event.AbstractEvent;

public class AwaitingPublish extends AbstractState{
    public void enter(){
        System.out.println("Now in the AwaitingPublish state");
    }

    public void update(AbstractEvent event){
        System.out.println(event.getCode());
    }
}
