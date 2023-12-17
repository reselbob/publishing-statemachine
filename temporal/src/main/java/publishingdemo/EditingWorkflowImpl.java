package publishingdemo;

import io.temporal.workflow.Async;
import io.temporal.workflow.Promise;
import io.temporal.workflow.Workflow;
import io.temporal.workflow.WorkflowQueue;
import org.slf4j.Logger;
import publishingdemo.model.Document;

public class EditingWorkflowImpl implements EditingWorkflow {
    private static final Logger logger = Workflow.getLogger(EditingWorkflowImpl.class);

    private final ActivityOptions options = AcitivityOptions.newBuilder().setStartToCloseTimeout(Duration.ofSecond(10));
    private final EditingActivities activities = Workflow.newActivitiesStub(EditingActivities.class, options)

    private Document copyEditedDocument;
    private Document graphicEditedDocument;

    /**
     * This is the main workflow method that will be invoked by the parent workflow. It creates two
     * promises that will be executed asynchronously in parallel. The functions called are copyEdit
     * and graphicEdit. The exit method is called to shut down the workflow.
     *
     * @param document, the document to edit
     */
    @Override
    public Document editDocument(Document document) {
        logger.info(
                "Starting Workflow for Edit child workflow for Publishing use case for document: "
                        + document.getUrl());
        Promise<Void> copyEdit = Async.procedure(activities::sendCopyEdit, document);
        Promise<Void> graphEdit = Async.procedure(activities::sendGraphEdit, document);
        copyEdit.get();
        graphicEdit.get();
        // Await for both edited documents to arrive
        Workflow.await(()->copyEditedDocument != null && graphicEditedDocument != null);
        return activities.mergeDocuments(copyEditedDocument, graphicEditedDocument);
    }

    @Override
    public void notifyCopyEditDone(Document updatedDocument) {
        copyEditedDocument = updatedDocument;
    }

    @Override
    void notifyGraphicEditDone(Document updatedDocument) {
        graphicEditedDocument = updatedDocument;
    }

}
