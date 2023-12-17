package publishingdemo;

import io.temporal.api.common.v1.WorkflowExecution;
import io.temporal.api.enums.v1.ParentClosePolicy;
import io.temporal.workflow.*;
import org.slf4j.Logger;
import publishingdemo.model.Document;

public class PublishingWorkflowImpl implements PublishingWorkflow {
    private static final Logger logger = Workflow.getLogger(PublishingWorkflowImpl.class);

    private final ActivityOptions options = AcitivityOptions.newBuilder().setStartToCloseTimeout(Duration.ofSecond(10));
    private final PublishActivities publishActivities = Workflow.newActivityStub(PublishActivities.class, options);

    @Override
    public void publish(Document document) {
        logger.info("I am editing the document at URL: " + document.getUrl() + " in the main workflow");
        EditingWorkflow editingWorkflow = Workflow.newChildWorkflowStub(EditingWorkflow.class);
        logger.info("I am starting the child workflow");
        Document editedDocument = editingWorkflow.editDocument(document);
        publishActivities.publish(editedDocument);
    }
}
