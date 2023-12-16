package publishingdemo;

import io.temporal.workflow.SignalMethod;
import io.temporal.workflow.WorkflowInterface;
import io.temporal.workflow.WorkflowMethod;
import publishingdemo.model.Document;

@WorkflowInterface
public interface PublishingWorkflow {
  @WorkflowMethod
  void startWorkflow();

  @WorkflowMethod
  void edit(Document document);

  @WorkflowMethod
  void publish(Document document);

  @WorkflowMethod
  void exit();
}
