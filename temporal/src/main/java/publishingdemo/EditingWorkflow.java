package publishingdemo;

import io.temporal.workflow.SignalMethod;
import io.temporal.workflow.WorkflowInterface;
import io.temporal.workflow.WorkflowMethod;
import publishingdemo.model.Document;

@WorkflowInterface
public interface EditingWorkflow {
  @WorkflowMethod
  Document editDocument(Document document);
  
  @SignalMethod
  void notifyCopyEditDone(Document document);

  @SignalMethod
  void notifyGraphicEditDone(Document document);
}
