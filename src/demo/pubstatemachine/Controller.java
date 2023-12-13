package demo.pubstatemachine;

import demo.pubstatemachine.message.AbstractMessage;
import demo.pubstatemachine.message.MessageImpl;
import demo.pubstatemachine.message.MessageType;
import demo.pubstatemachine.queue.SimpleMessageQueue;
import demo.pubstatemachine.state.*;

public class Controller {

    private final SimpleMessageQueue queue;

    public Controller() throws InterruptedException {
        this.queue = new SimpleMessageQueue();
        // sleep for 1 second
        Thread pollThread = new Thread(() -> {
            AbstractState.inactive = new Inactive(queue);
            AbstractState.editable = new Editable(queue);
            AbstractState.graphicEdit = new GraphicEdit(queue);
            AbstractState.copyEdit = new CopyEdit(queue);
            AbstractState.awaitingEdits = new AwaitingEdits(queue);
            AbstractState.awaitingPublish = new AwaitingPublish(queue);
            AbstractState.publish = new Publish(queue);
            AbstractState.current = AbstractState.inactive;

            while (true) {
                try {
                    AbstractMessage msg = queue.getMessage();
                    System.out.println("Controller received message: " + msg.getEventType());
                    switch (msg.getEventType()) {
                        case EVENT_EDITABLE:
                            processEventEditable(msg);
                            break;
                        case EVENT_AWAIT_GRAPHIC_EDIT:
                            AbstractState.current = AbstractState.awaitingEdits;
                            AbstractState.current.enter();
                            break;
                        case EVENT_AWAIT_COPY_EDIT:
                            AbstractState.current = AbstractState.awaitingEdits;
                            AbstractState.current.enter();
                            break;
                        case COMMAND_GRAPHIC_EDIT:
                            processCommandGraphicEdit(msg);
                            break;
                        case COMMAND_COPY_EDIT:
                            AbstractState.current = AbstractState.copyEdit;
                            AbstractState.current.enter();
                            break;
                        case COMMAND_PUBLISH:
                            AbstractState.current = AbstractState.publish;
                            AbstractState.current.enter();
                            break;
                        case COMMAND_AWAIT_EDIT:
                            AbstractState.current = AbstractState.awaitingEdits;
                            AbstractState.current.enter();
                            break;
                        case COMMAND_AWAIT_PUBLISH:
                            AbstractState.current = AbstractState.awaitingPublish;
                            AbstractState.current.enter();
                            break;
                    }
                } catch (InterruptedException e) {
                    System.err.println("Polling interrupted: " + e.getMessage());
                    break;
                }
                try {
                    Thread.sleep(1000); // sleep for 1 second
                } catch (InterruptedException e) {
                    System.err.println("Sleep interrupted: " + e.getMessage());
                    break;
                }
            }
        });
        pollThread.start();
        System.out.println("Controller running");

    }

    private static void processEventEditable(AbstractMessage message) throws InterruptedException {
        AbstractState.current = AbstractState.editable;
        AbstractState.current.enter();
        StateMonitor sm = StateMonitor.getStateMonitor(message.getDocument());
        if(sm == null){
            sm = new StateMonitor(message.getDocument());
            StateMonitor.addStateMonitor(sm);
        }
        if(!sm.isEditable()){
            AbstractState.current.update(message);
        }
    }

    private static void processCommandGraphicEdit(AbstractMessage message) throws InterruptedException {
        AbstractState.current = AbstractState.graphicEdit;
        AbstractState.current.enter();
        StateMonitor sm = StateMonitor.getStateMonitor(message.getDocument());
        if(sm == null){
            throw new NullPointerException("StateMonitor not found in processCommandGraphicEdit");
        }
        if(!sm.isGraphicEdited()){
            AbstractState.current.update(message);
            sm.setGraphicEdited(true);
        }
    }

    private static void processCommandCopyEdit(AbstractMessage message) throws InterruptedException {
        AbstractState.current = AbstractState.graphicEdit;
        AbstractState.current.enter();
        StateMonitor sm = StateMonitor.getStateMonitor(message.getDocument());
        if(sm == null){
            throw new NullPointerException("StateMonitor not found in processCommandGraphicEdit");
        }
        if(!sm.isGraphicEdited()){
            AbstractState.current.update(message);
        }
    }

    private static void processEventAwaitEdits(AbstractMessage message) throws InterruptedException {
        AbstractState.current = AbstractState.awaitingEdits;
        AbstractState.current.enter();
        StateMonitor sm = StateMonitor.getStateMonitor(message.getDocument());
        if(sm == null){
            throw new NullPointerException("StateMonitor not found in processEventAwaitEdits");
        }
        if(!sm.isGraphicEdited()){
            AbstractState.current.update(message);
        }
    }


    public void sendMessage(AbstractMessage message) {
        System.out.println("Sending message: " + message.getEventType());
        queue.putMessage(message);
    }
}
