package demo.pubstatemachine.state;

import demo.pubstatemachine.event.AbstractEvent;

import java.util.Scanner;

public class Editable extends AbstractState {
    public void enter(){
        System.out.println("Now in the Editable state");
    }

    public void update(AbstractEvent event){
        System.out.println(event.getCode());
    }
}
