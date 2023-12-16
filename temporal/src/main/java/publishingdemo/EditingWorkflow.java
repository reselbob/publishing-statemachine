package publishingdemo;
import io.temporal.workflow.SignalMethod;
import io.temporal.workflow.WorkflowInterface;
import io.temporal.workflow.WorkflowMethod;
import publishingdemo.model.Document;

@WorkflowInterface
public interface EditingWorkflow {
    @WorkflowMethod
    void startWorkflow(Document document);

    @WorkflowMethod
    void copyEdit(Document document) throws InterruptedException;

    @WorkflowMethod
    void graphicEdit(Document document) throws InterruptedException;

    @WorkflowMethod
    void exit();
}
