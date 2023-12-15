package publishingdemo;


import io.temporal.client.WorkflowClient;
import io.temporal.client.WorkflowOptions;
import io.temporal.common.RetryOptions;
import io.temporal.serviceclient.WorkflowServiceStubs;
import io.temporal.worker.Worker;
import io.temporal.worker.WorkerFactory;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import publishingdemo.model.Document;

import javax.print.Doc;

public class App {
    static final String TASK_QUEUE = "BarryPeanutsTemporal";

    private static final Logger logger = LoggerFactory.getLogger(App.class);

    @SuppressWarnings("CatchAndPrintStackTrace")
    public static void main(String[] args) throws MalformedURLException {
        // gRPC stubs wrapper that talks to the local docker instance of temporal
        // service.
        WorkflowServiceStubs service = WorkflowServiceStubs.newLocalServiceStubs();
        // client that can be used to start and signal workflows
        WorkflowClient client = WorkflowClient.newInstance(service);

        //Start the worker and hold onto the WorkerFactory for later use, if necessary.
        WorkerFactory factory = startWorkerWithFactory(client);

        // Declare the WORKFLOW_ID
        String WORKFLOW_ID = TASK_QUEUE + "-" + "01";

        // now we can start running instances of our workflow - its state will be persisted
        WorkflowOptions options =
                WorkflowOptions.newBuilder()
                        .setTaskQueue(TASK_QUEUE)
                        .setWorkflowId(WORKFLOW_ID)
                        // set the return options
                        .setRetryOptions(
                                RetryOptions.newBuilder()
                                        .setInitialInterval(Duration.ofSeconds(1))
                                        .setMaximumInterval(Duration.ofSeconds(10))
                                        .build())
                        .build();

        PublishingWorkflow wf = client.newWorkflowStub(PublishingWorkflow.class, options);
        try {

            URL url = new URL("https://learn.temporal.io/getting_started/#set-up-your-development-environment");
            Document document = new Document(url);

            WorkflowClient.start(wf::startWorkflow);


            wf.exit();

        } catch (Exception e) {
            // Just rethrow for now
            throw e;
        }
        logger.info("Nothing left to do, so the Executor will exit. That's all folks!");
    }

    /**
     *
     * @param client, the workflow client
     * @return, the WorkerFactory that created the worker
     */
    private static WorkerFactory startWorkerWithFactory(WorkflowClient client) {
        // worker factory that can be used to create workers for specific task queues
        WorkerFactory factory = WorkerFactory.newInstance(client);

        // Worker that listens on a task queue and hosts both workflow and activity
        // implementations.
        Worker worker = factory.newWorker(TASK_QUEUE);

        // Workflows are stateful. So you need a type to create instances.
        worker.registerWorkflowImplementationTypes(ShoppingCartWorkflowImpl.class);

        // Start the worker created by this factory.
        factory.start();

        logger.info("Worker listening on task queue: {}.", TASK_QUEUE);

        return factory;
    }
}