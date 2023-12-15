package publishingdemo;
import io.temporal.workflow.SignalMethod;
import io.temporal.workflow.WorkflowInterface;
import io.temporal.workflow.WorkflowMethod;
import publishingdemo.model.Document;

public interface EditingWorkflow {
    @WorkflowMethod
    void startWorkflow();

    @SignalMethod
    void copyEdit(Document document);

    @SignalMethod
    void graphicEdit(Document document);

    @SignalMethod
    void exit();
}
