package publishingdemo;

import io.temporal.workflow.Async;
import io.temporal.workflow.Promise;
import io.temporal.workflow.Workflow;
import io.temporal.workflow.WorkflowQueue;
import org.slf4j.Logger;
import publishingdemo.model.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;

public class EditingWorkflowImpl implements EditingWorkflow{
    private static final Logger logger = Workflow.getLogger(EditingWorkflowImpl.class);
    private final WorkflowQueue<Runnable> queue = Workflow.newWorkflowQueue(1024);
    private boolean exit = false;

    /**
     * This is the main workflow method that will be invoked by the parent workflow. It
     * creates two promises that will be executed asynchronously in parallel. The functions
     * called are copyEdit and graphicEdit. The exit method is called to shut down the workflow.
     * @param document, the document to edit
     * @throws InterruptedException
     */
    @Override
    public void startWorkflow(Document document) throws InterruptedException{
        logger.info("Starting Workflow for Edit child workflow for Publishing use case for document: " + document.getUrl());
        List<Promise<Void>> promises = new ArrayList<>();
        promises.add(
                Async.procedure(() -> {
                    try {
                        this.copyEdit(document);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                })
        );

        promises.add(
                Async.procedure(() -> {
                    try {
                        this.graphicEdit(document);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                })
        );

        Promise.allOf(promises).get();

        Workflow.await(() -> exit);
    }

    @Override
    public void copyEdit(Document document) throws InterruptedException {
        logger.info("I am copy editing the document: " + document.getUrl() + " in the child workflow");
        Thread.sleep(1000);
    }

    @Override
    public void graphicEdit(Document document) throws InterruptedException {
        logger.info("I am graphic editing the document: " + document.getUrl() + " in the child workflow");
        Thread.sleep(1000);
    }

    @Override
    public void exit() {
        logger.info("Exiting the editing process");
        exit = true;
    }
}
