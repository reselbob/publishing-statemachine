package publishingdemo;

import io.temporal.workflow.QueryMethod;
import io.temporal.workflow.SignalMethod;
import io.temporal.workflow.WorkflowInterface;
import io.temporal.workflow.WorkflowMethod;
import publishingdemo.model.Document;

@WorkflowInterface
public interface PublishingWorkflow {
    @WorkflowMethod
    void startWorkflow();

    @SignalMethod
    void edit(Document document);

    @SignalMethod
    void publish(Document document);

    @SignalMethod
    void exit();
}