# Implementing a Temporal Workflow for a Publishing Use Case using the Java SDK

The purpose of the project is to demonstrate how to implement a workflow under Temporal for a document publishing use case. The project uses the [Temporal Java SDK](https://docs.temporal.io/docs/java/introduction).

The workflow has three activities:

- CopyEdit
- Graphic Edit
- Publish

The Copy Edit and Graphic Edit activities execute concurrently. Upon completion of both activites, the Publish activity executes as illustated in the figure below.

![statemachine-03](https://github.com/reselbob/publishing-statemachine/assets/1110569/488b624d-c8fb-46bc-9e6b-85c0e9ebea2f)


# Running the code:

The [Java Virtual Machine](https://openjdk.org/) and [Maven](https://maven.apache.org/install.html) need to be installed
on the host computer.

## (1) Confirm that Java and Maven are installed on the host machine

Confirm that Java is installed:

```bash
java --version
```

You'll get output similar to the following:

```bash
openjdk 18.0.2-ea 2022-07-19
OpenJDK Runtime Environment (build 18.0.2-ea+9-Ubuntu-222.04)
OpenJDK 64-Bit Server VM (build 18.0.2-ea+9-Ubuntu-222.04, mixed mode, sharing)
```

Confirm that Maven is installed:

```bash
mvn --version
```

```bash
Maven home: /usr/share/maven
Java version: 18.0.2, vendor: Oracle Corporation, runtime: /usr/lib/jvm/jdk-18.0.2
Default locale: en_US, platform encoding: UTF-8
OS name: "linux", version: "5.19.0-46-generic", arch: "amd64", family: "unix"
```

## (2) Download and install the Temporal CLI (which includes the server)

If you do not have the Temporal server installed, click the link below to go to the Temporal documentation that has the
instructions for installing the Temporal CLI.

[https://docs.temporal.io/cli/#installation](https://docs.temporal.io/cli/#installation)

The Temporal development server ships with the CLI.

---

## (3) Start the Temporal Server

Here is the command for starting the Temporal Server on a local Ubuntu machine. Execute the command in a terminal
window.

```bash
temporal server start-dev --dynamic-config-value frontend.enableUpdateWorkflowExecution=true
```

---

## (4) Do some maven housecleaning

Run the following command in a new terminal window to create a fresh Maven environment:

```bash
mvn clean package install
```

## (5) Start the application

In that same terminal window run:

```bash
mvn exec:java -Dexec.mainClass="publishingdemo.App"
```

You'll see output similar to the following:

```text                                                                                                                                                                                                                                                           reselbob@bobs-mac-mini temporal % mvn exec:java -Dexec.mainClass="publishingdemo.App"
[INFO] Scanning for projects...
[INFO] 
[INFO] -------------------------< barryspeanuts:app >--------------------------
[INFO] Building app 1.0-SNAPSHOT
[INFO]   from pom.xml
[INFO] --------------------------------[ jar ]---------------------------------
[INFO] 
[INFO] --- exec:3.1.0:java (default-cli) @ app ---
Enter the TASK QUEUE name: 

You did not enter a value for TASK QUEUE to we'll use the default value: PublishingDemo
[publishingdemo.App.main()] INFO io.temporal.serviceclient.WorkflowServiceStubsImpl - Created WorkflowServiceStubs for channel: ManagedChannelOrphanWrapper{delegate=ManagedChannelImpl{logId=1, target=127.0.0.1:7233}}
[publishingdemo.App.main()] INFO io.temporal.internal.worker.Poller - start: Poller{name=Workflow Poller taskQueue="PublishingDemo", namespace="default", identity=24633@bobs-mac-mini.lan}
[publishingdemo.App.main()] INFO io.temporal.internal.worker.Poller - start: Poller{name=Activity Poller taskQueue="PublishingDemo", namespace="default", identity=24633@bobs-mac-mini.lan}
[publishingdemo.App.main()] INFO publishingdemo.App - The worker has started and is listening on task queue: PublishingDemo.
Enter 'exit' to exit or any other key to add a new Document URL: 

Enter Document URL: 
https://docs.temporal.io/dev-guide/java
Enter 'exit' to exit or any other key to add a new Document URL: 
[workflow-method-17d1ec34-b962-4b8d-800e-11d05f3c282e-151fb4fe-a367-445b-a1fa-cdaaa79e000d] INFO publishingdemo.PublicationWorkflowImpl - Starting Workflow for Publishing
[Activity Executor taskQueue="PublishingDemo", namespace="default": 2] INFO publishingdemo.PublishingActivitiesImpl - I am Amazing AI. I have the smarts to Graphic Edit the document id: 17d1ec34-b962-4b8d-800e-11d05f3c282e at URL https://docs.temporal.io/dev-guide/java. STARTING GRAPHIC EDIT NOW!
[Activity Executor taskQueue="PublishingDemo", namespace="default": 1] INFO publishingdemo.PublishingActivitiesImpl - I am Amazing AI. I have the smarts to Copy Edit the document id: 17d1ec34-b962-4b8d-800e-11d05f3c282e at URL https://docs.temporal.io/dev-guide/java. STARTING COPY EDIT NOW!
[workflow-method-17d1ec34-b962-4b8d-800e-11d05f3c282e-151fb4fe-a367-445b-a1fa-cdaaa79e000d] INFO publishingdemo.PublicationWorkflowImpl - Copy edit complete
[workflow-method-17d1ec34-b962-4b8d-800e-11d05f3c282e-151fb4fe-a367-445b-a1fa-cdaaa79e000d] INFO publishingdemo.PublicationWorkflowImpl - Graphic edit complete
[Activity Executor taskQueue="PublishingDemo", namespace="default": 2] INFO publishingdemo.PublishingActivitiesImpl - I am Amazing AI. I have the smarts to Publish the document id: 17d1ec34-b962-4b8d-800e-11d05f3c282e at URL https://docs.temporal.io/dev-guide/java. STARTING PUBLISH NOW!
[workflow-method-17d1ec34-b962-4b8d-800e-11d05f3c282e-151fb4fe-a367-445b-a1fa-cdaaa79e000d] INFO publishingdemo.PublicationWorkflowImpl - Publishing complete

Enter Document URL: 
https://www.lacourt.org/
Enter 'exit' to exit or any other key to add a new Document URL: 
[workflow-method-c1d8c49d-1e0e-4736-bf69-270252e9111a-434adbcc-9d9d-4217-9ba4-ca902e57fe7e] INFO publishingdemo.PublicationWorkflowImpl - Starting Workflow for Publishing
[Activity Executor taskQueue="PublishingDemo", namespace="default": 3] INFO publishingdemo.PublishingActivitiesImpl - I am Amazing AI. I have the smarts to Copy Edit the document id: c1d8c49d-1e0e-4736-bf69-270252e9111a at URL https://www.lacourt.org/. STARTING COPY EDIT NOW!
[Activity Executor taskQueue="PublishingDemo", namespace="default": 4] INFO publishingdemo.PublishingActivitiesImpl - I am Amazing AI. I have the smarts to Graphic Edit the document id: c1d8c49d-1e0e-4736-bf69-270252e9111a at URL https://www.lacourt.org/. STARTING GRAPHIC EDIT NOW!
[workflow-method-c1d8c49d-1e0e-4736-bf69-270252e9111a-434adbcc-9d9d-4217-9ba4-ca902e57fe7e] INFO publishingdemo.PublicationWorkflowImpl - Copy edit complete
[workflow-method-c1d8c49d-1e0e-4736-bf69-270252e9111a-434adbcc-9d9d-4217-9ba4-ca902e57fe7e] INFO publishingdemo.PublicationWorkflowImpl - Graphic edit complete
[Activity Executor taskQueue="PublishingDemo", namespace="default": 4] INFO publishingdemo.PublishingActivitiesImpl - I am Amazing AI. I have the smarts to Publish the document id: c1d8c49d-1e0e-4736-bf69-270252e9111a at URL https://www.lacourt.org/. STARTING PUBLISH NOW!
[workflow-method-c1d8c49d-1e0e-4736-bf69-270252e9111a-434adbcc-9d9d-4217-9ba4-ca902e57fe7e] INFO publishingdemo.PublicationWorkflowImpl - Publishing complete

Enter Document URL: 
https://www.linkedin.com/notifications/
Enter 'exit' to exit or any other key to add a new Document URL: 
[workflow-method-8e22386a-3036-440a-87cd-0738cd735fed-94cfc1d6-103b-49a5-adca-d02d87188251] INFO publishingdemo.PublicationWorkflowImpl - Starting Workflow for Publishing
[Activity Executor taskQueue="PublishingDemo", namespace="default": 5] INFO publishingdemo.PublishingActivitiesImpl - I am Amazing AI. I have the smarts to Copy Edit the document id: 8e22386a-3036-440a-87cd-0738cd735fed at URL https://www.linkedin.com/notifications/. STARTING COPY EDIT NOW!
[Activity Executor taskQueue="PublishingDemo", namespace="default": 6] INFO publishingdemo.PublishingActivitiesImpl - I am Amazing AI. I have the smarts to Graphic Edit the document id: 8e22386a-3036-440a-87cd-0738cd735fed at URL https://www.linkedin.com/notifications/. STARTING GRAPHIC EDIT NOW!
[workflow-method-8e22386a-3036-440a-87cd-0738cd735fed-94cfc1d6-103b-49a5-adca-d02d87188251] INFO publishingdemo.PublicationWorkflowImpl - Copy edit complete
[workflow-method-8e22386a-3036-440a-87cd-0738cd735fed-94cfc1d6-103b-49a5-adca-d02d87188251] INFO publishingdemo.PublicationWorkflowImpl - Graphic edit complete
[Activity Executor taskQueue="PublishingDemo", namespace="default": 5] INFO publishingdemo.PublishingActivitiesImpl - I am Amazing AI. I have the smarts to Publish the document id: 8e22386a-3036-440a-87cd-0738cd735fed at URL https://www.linkedin.com/notifications/. STARTING PUBLISH NOW!
[workflow-method-8e22386a-3036-440a-87cd-0738cd735fed-94cfc1d6-103b-49a5-adca-d02d87188251] INFO publishingdemo.PublicationWorkflowImpl - Publishing complete

Enter Document URL: 
https://www.pbs.org/
Enter 'exit' to exit or any other key to add a new Document URL: 
[workflow-method-bcaab00c-9b1a-4d5f-8ac7-7c10065f45c4-22886980-71f1-42e8-9ca0-f5a9f64aaaa5] INFO publishingdemo.PublicationWorkflowImpl - Starting Workflow for Publishing
[Activity Executor taskQueue="PublishingDemo", namespace="default": 7] INFO publishingdemo.PublishingActivitiesImpl - I am Amazing AI. I have the smarts to Graphic Edit the document id: bcaab00c-9b1a-4d5f-8ac7-7c10065f45c4 at URL https://www.pbs.org/. STARTING GRAPHIC EDIT NOW!
[Activity Executor taskQueue="PublishingDemo", namespace="default": 8] INFO publishingdemo.PublishingActivitiesImpl - I am Amazing AI. I have the smarts to Copy Edit the document id: bcaab00c-9b1a-4d5f-8ac7-7c10065f45c4 at URL https://www.pbs.org/. STARTING COPY EDIT NOW!
[workflow-method-bcaab00c-9b1a-4d5f-8ac7-7c10065f45c4-22886980-71f1-42e8-9ca0-f5a9f64aaaa5] INFO publishingdemo.PublicationWorkflowImpl - Copy edit complete
[workflow-method-bcaab00c-9b1a-4d5f-8ac7-7c10065f45c4-22886980-71f1-42e8-9ca0-f5a9f64aaaa5] INFO publishingdemo.PublicationWorkflowImpl - Graphic edit complete
[Activity Executor taskQueue="PublishingDemo", namespace="default": 8] INFO publishingdemo.PublishingActivitiesImpl - I am Amazing AI. I have the smarts to Publish the document id: bcaab00c-9b1a-4d5f-8ac7-7c10065f45c4 at URL https://www.pbs.org/. STARTING PUBLISH NOW!
[workflow-method-bcaab00c-9b1a-4d5f-8ac7-7c10065f45c4-22886980-71f1-42e8-9ca0-f5a9f64aaaa5] INFO publishingdemo.PublicationWorkflowImpl - Publishing complete
exit
[publishingdemo.App.main()] INFO io.temporal.worker.WorkerFactory - shutdown: WorkerFactory{identity=24633@bobs-mac-mini.lan}
[publishingdemo.App.main()] INFO io.temporal.internal.worker.Poller - shutdown: Poller{name=Workflow Poller taskQueue="PublishingDemo", namespace="default", identity=24633@bobs-mac-mini.lan}
[Workflow Poller taskQueue="PublishingDemo", namespace="default": 1] INFO io.temporal.internal.worker.Poller - poll loop is terminated: WorkflowPollTask
[Workflow Poller taskQueue="PublishingDemo", namespace="default": 2] INFO io.temporal.internal.worker.Poller - poll loop is terminated: WorkflowPollTask
[Workflow Poller taskQueue="PublishingDemo", namespace="default": 4] INFO io.temporal.internal.worker.Poller - poll loop is terminated: WorkflowPollTask
[Workflow Poller taskQueue="PublishingDemo", namespace="default": 3] INFO io.temporal.internal.worker.Poller - poll loop is terminated: WorkflowPollTask
[Workflow Poller taskQueue="PublishingDemo", namespace="default": 5] INFO io.temporal.internal.worker.Poller - poll loop is terminated: WorkflowPollTask
[publishingdemo.App.main()] INFO io.temporal.internal.worker.Poller - shutdown: Poller{name=Activity Poller taskQueue="PublishingDemo", namespace="default", identity=24633@bobs-mac-mini.lan}
[Activity Poller taskQueue="PublishingDemo", namespace="default": 5] INFO io.temporal.internal.worker.Poller - poll loop is terminated: ActivityPollTask
[Activity Poller taskQueue="PublishingDemo", namespace="default": 2] INFO io.temporal.internal.worker.Poller - poll loop is terminated: ActivityPollTask
[Activity Poller taskQueue="PublishingDemo", namespace="default": 1] INFO io.temporal.internal.worker.Poller - poll loop is terminated: ActivityPollTask
[Activity Poller taskQueue="PublishingDemo", namespace="default": 3] INFO io.temporal.internal.worker.Poller - poll loop is terminated: ActivityPollTask
[Activity Poller taskQueue="PublishingDemo", namespace="default": 4] INFO io.temporal.internal.worker.Poller - poll loop is terminated: ActivityPollTask
[publishingdemo.App.main()] INFO publishingdemo.App - The worker has been shutdown. That's all folks!


```
