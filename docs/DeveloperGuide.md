---
layout: page
title: Developer Guide
---
* Table of Contents
{:toc}

--------------------------------------------------------------------------------------------------------------------

## **Acknowledgements**

* {list here sources of all reused/adapted ideas, code, documentation, and third-party libraries -- include links to the original source as well}

--------------------------------------------------------------------------------------------------------------------

## **Setting up, getting started**

Refer to the guide [_Setting up and getting started_](SettingUp.md).

--------------------------------------------------------------------------------------------------------------------

## **Design**

<div markdown="span" class="alert alert-primary">

:bulb: **Tip:** The `.puml` files used to create diagrams in this document `docs/diagrams` folder. Refer to the [_PlantUML Tutorial_ at se-edu/guides](https://se-education.org/guides/tutorials/plantUml.html) to learn how to create and edit diagrams.
</div>

### Architecture

<img src="images/ArchitectureDiagram.png" width="280" />

The ***Architecture Diagram*** given above explains the high-level design of the App.

Given below is a quick overview of main components and how they interact with each other.

**Main components of the architecture**

**`Main`** (consisting of classes [`Main`](https://github.com/AY2324S1-CS2103T-T08-3/tp/blob/master/src/main/java/seedu/address/Main.java) and [`MainApp`](https://github.com/AY2324S1-CS2103T-T08-3/tp/blob/master/src/main/java/seedu/address/MainApp.java)) is in charge of the app launch and shut down.
* At app launch, it initializes the other components in the correct sequence, and connects them up with each other.
* At shut down, it shuts down the other components and invokes cleanup methods where necessary.

The bulk of the app's work is done by the following four components:

* [**`UI`**](#ui-component): The UI of the App.
* [**`Logic`**](#logic-component): The command executor.
* [**`Model`**](#model-component): Holds the data of the App in memory.
* [**`Storage`**](#storage-component): Reads data from, and writes data to, the hard disk.

[**`Commons`**](#common-classes) represents a collection of classes used by multiple other components.

**How the architecture components interact with each other**

The *Sequence Diagram* below shows how the components interact with each other for the scenario where the user issues the command `deleteE 1`.

<img src="images/ArchitectureSequenceDiagram.png" width="574" />

Each of the four main components (also shown in the diagram above),

* defines its *API* in an `interface` with the same name as the Component.
* implements its functionality using a concrete `{Component Name}Manager` class (which follows the corresponding API `interface` mentioned in the previous point).

For example, the `Logic` component defines its API in the `Logic.java` interface and implements its functionality using the `LogicManager.java` class which follows the `Logic` interface. Other components interact with a given component through its interface rather than the concrete class (reason: to prevent outside component's being coupled to the implementation of a component), as illustrated in the (partial) class diagram below.

<img src="images/ComponentManagers.png" width="300" />

The sections below give more details of each component.

### UI component

The **API** of this component is specified in [`Ui.java`](https://github.com/AY2324S1-CS2103T-T08-3/tp/blob/master/src/main/java/seedu/address/ui/Ui.java)

![Structure of the UI Component](images/UiClassDiagram.png)

The UI consists of a `MainWindow` that is made up of parts e.g.`CommandBox`, `ResultDisplay`, `EmployeeListPanel`, `StatusBarFooter` etc. All these, including the `MainWindow`, inherit from the abstract `UiPart` class which captures the commonalities between classes that represent parts of the visible GUI.

The `UI` component uses the JavaFx UI framework. The layout of these UI parts are defined in matching `.fxml` files that are in the `src/main/resources/view` folder. For example, the layout of the [`MainWindow`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/ui/MainWindow.java) is specified in [`MainWindow.fxml`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/resources/view/MainWindow.fxml)

The `UI` component,

* executes user commands using the `Logic` component.
* listens for changes to `Model` data so that the UI can be updated with the modified data.
* keeps a reference to the `Logic` component, because the `UI` relies on the `Logic` to execute commands.
* depends on some classes in the `Model` component, as it displays `Employee` object residing in the `Model`.

### Logic component

**API** : [`Logic.java`](https://github.com/AY2324S1-CS2103T-T08-3/tp/blob/master/src/main/java/seedu/address/logic/Logic.java)

Here's a (partial) class diagram of the `Logic` component:

<img src="images/LogicClassDiagram.png" width="550"/>

The sequence diagram below illustrates the interactions within the `Logic` component, taking `execute("delete 1")` API call as an example.

![Interactions Inside the Logic Component for the `delete 1` Command](images/DeleteSequenceDiagram.png)

<div markdown="span" class="alert alert-info">:information_source: **Note:** The lifeline for `DeleteCommandParser` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline reaches the end of diagram.
</div>

How the `Logic` component works:

1. When `Logic` is called upon to execute a command, it is passed to a `TaskHubParser` object which in turn creates a parser that matches the command (e.g., `DeleteCommandParser`) and uses it to parse the command.
2. This results in a `Command` object (more precisely, an object of one of its subclasses e.g., `DeleteCommand`) which is executed by the `LogicManager`.
3. The command can communicate with the `Model` when it is executed (e.g. to delete a employee).
4. The result of the command execution is encapsulated as a `CommandResult` object which is returned back from `Logic`.

Here are the other classes in `Logic` (omitted from the class diagram above) that are used for parsing a user command:

<img src="images/ParserClasses.png" width="600"/>

How the parsing works:
* When called upon to parse a user command, the `TaskHubParser` class creates an `XYZCommandParser` (`XYZ` is a placeholder for the specific command name e.g., `AddCommandParser`) which uses the other classes shown above to parse the user command and create a `XYZCommand` object (e.g., `AddCommand`) which the `TaskHubParser` returns back as a `Command` object.
* All `XYZCommandParser` classes (e.g., `AddCommandParser`, `DeleteCommandParser`, ...) inherit from the `Parser` interface so that they can be treated similarly where possible e.g, during testing.

### Model component
**API** : [`Model.java`](https://github.com/AY2324S1-CS2103T-T08-3/tp/blob/master/src/main/java/seedu/address/model/Model.java)

<img src="images/ModelClassDiagram.png" width="450" />


The `Model` component,

* stores the TaskHub data i.e., all `Employee` and `Project` objects (which are separately contained in a `UniqueEmployeeList` object and a `UniqueProjectList` object).
* stores the currently 'selected' `Employee` and `Project` objects (e.g., results of a search query) as 2 separate _filtered_ lists which are exposed to outsiders as unmodifiable `ObservableList<Employee>` and `ObservableList<Project>` that can be 'observed' e.g. the UI can be bound to this list so that the UI automatically updates when the data in the list change.
* stores a `UserPref` object that represents the user’s preferences. This is exposed to the outside as a `ReadOnlyUserPref` object.
* does not depend on any of the other three components (as the `Model` represents data entities of the domain, they should make sense on their own without depending on other components)

<div markdown="span" class="alert alert-info">:information_source: **Note:** An alternative (arguably, a more OOP) model is given below. It has a `Tag` list in the `TaskHub`, which `Employee` references. This allows `TaskHub` to only require one `Tag` object per unique tag, instead of each `Employee` needing their own `Tag` objects.<br>

<img src="images/BetterModelClassDiagram.png" width="450" />

</div>


### Employee component
**API** : [`Employee.java`](https://github.com/AY2324S1-CS2103T-T08-3/tp/blob/master/src/main/java/seedu/address/model/employee/Employee.java)

<img src="images/EmployeeClassDiagram.png" width="450" />

The `Employee` component stores an employee's data which comprises:

* a `Name`, `Phone`, `Email`, `Address`, `Project` and `Tag`(s).
* `Project`(s) and `Tag`(s) are optional.


### Project component
**API** : [`Project.java`](https://github.com/AY2324S1-CS2103T-T08-3/tp/blob/master/src/main/java/seedu/address/model/project/Project.java)

<img src="images/ProjectClassDiagram.png" width="450" />

The `Project` component stores a project's data which comprises:

* a `name`, `Deadline`, `UniqueEmployeeList`, `ProjectPriority` and `CompletionStatus`.
* The `Deadline` is optional.
* The `UniqueEmployeeList` contains only the `Employee`s under the said `Project`.
* Each `Employee` in the `UniqueEmployeeList` of the project must also exist in the `UniqueEmployeeList` of the `Model`. (All fields must be the same) 

***Note: For the Model, Employee and Project components, lower-level details (e.g. most class attributes and methods) have been omitted for visual clarity.


### Storage component

**API** : [`Storage.java`](https://github.com/AY2324S1-CS2103T-T08-3/tp/blob/master/src/main/java/seedu/address/storage/Storage.java)

<img src="images/StorageClassDiagram.png" width="550" />

The `Storage` component,
* can save both TaskHub data and user preference data in JSON format, and read them back into corresponding objects.
* inherits from both `TaskHubStorage` and `UserPrefStorage`, which means it can be treated as either one (if only the functionality of only one is needed).
* depends on some classes in the `Model` component (because the `Storage` component's job is to save/retrieve objects that belong to the `Model`)

### Common classes

Classes used by multiple components are in the `seedu.address.commons` package.

--------------------------------------------------------------------------------------------------------------------

## **Implementation**

This section describes some noteworthy details on how certain features are implemented.

### Upgraded help feature 
In the `initialize()` method of the `HelpWindow` class, commands are organized into different categories using HashMaps. Here's an explanation:

```java
// Initializes HashMaps to store commands for different sections
Map<String, String> generalCommands = new HashMap<>();
Map<String, String> employeeCommands = new HashMap<>();
Map<String, String> projectCommands = new HashMap<>();

// Adds general commands with their descriptions to the generalCommands HashMap
generalCommands.put("help", "- Get help pop-up to display.");
generalCommands.put("clear", "- Clears all entries from TaskHub.");
generalCommands.put("exit", "- Exits the program.");

// Adds employee-related commands with their descriptions to the employeeCommands HashMap
employeeCommands.put("addE n/NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS [t/TAG]…\u200B",
        "- Adds an employee to the employees list.");
// ... (other employee commands)

// Adds project-related commands with their descriptions to the projectCommands HashMap
projectCommands.put("listP", "- Shows a list of all projects in TaskHub.");
// ... (other project commands)
```

In this code snippet, each HashMap (`generalCommands`, `employeeCommands`, and `projectCommands`) is used to store commands related to a specific section of the application. The keys in the HashMap represent the commands themselves, while the corresponding values provide a description of what each command does.

```java
// Adds sections and their respective commands to the VBox layout
addToVBox("General Commands", generalCommands);
addToVBox("Employee Commands", employeeCommands);
addToVBox("Project Commands", projectCommands);
```

The `addToVBox()` method takes a section header (like "General Commands") and a corresponding HashMap of commands. It then formats and adds these commands to the `VBox` layout of the help window. Inline code formatting (using backticks) could be applied as follows:

```java
String command = "addE n/NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS [t/TAG]…\u200B";
String description = "- Adds an employee to the employees list.";
```

This code structure efficiently organizes commands into distinct sections, making it easier for users to locate and understand the functionalities provided by each command. It also promotes code readability and maintainability for developers working on the application.


### Add Project feature

When creating a new project from the `addP` command, each `Employee` that is to be added to the `Project` is updated to have an empty `Project`. This is to avoid cyclic dependency between an `Employee` and a `Project`.

Given below is an example usage scenario and the internal changes that happen at each step.

![AddPSequenceDiagram](images/AddPSequenceDiagram.png)

Step 1. The user launches the application. All employees and projects will be shown to the user.

Step 2. The user executes `addP n/Project1 em/1 2 3` to add a new `Project` called `Project1` with the 1<sup>st</sup>, 2<sup>nd</sup> and 3<sup>rd</sup> `Employee` on the list as a member. `LogicManager` will call `TaskHubParser#parse(input)` to extract the parameters and pass it to an `AddProjectCommandParser`.

Step 3. `TaskHubParser` will call `AddProjectCommandParser#parse(arguments)` to produce a `AddProjectCommand` to be executed by the `LogicManager`.

Step 4. `LogicManager` calls `AddProjectCommand#execute(model)` to produce a `CommandResult `to be logged. 

Step 5. During the execution of the `AddProjectCommand`, selected `Employee`s are extracted from the current `UniqueEmployeeList` of `TaskHub` and added to the `Project` in the `AddProjectCommand`. New copies of `Employee` objects are also created to replace the original `Employee` objects in the UniqueEmployeeList using a new `EditCommand`.  

Step 6. A `CommandResult` is produced based on whether the execution was a success or not and returned to the `LogicManager`. 

### Prioritise Projects Feature

A priority can be assigned to multiple projects using the `priorityP` command. 

This command modifies the `Priority` attribute of a `Project` by creating a new `Project` with the updated `Priority` and replacing the existing project with the updated one.

Given below is an example usage scenario and the internal changes that happen at each step.

![PriorityPSequenceDiagram](images/PriorityPSequenceDiagram.png)

Step 1. The user launches the application. All employees and projects will be shown to the user.

Step 2. The user executes `priorityP 1 2 3 p/high` to assign a high priority to the projects indexed at 1, 2 and 3. `LogicManager` will call `TaskHubParser#parse(input)` to extract the parameters and pass it to a `PriorityProjectCommandParser`.

Step 3. `TaskHubParser` will call `PriorityProjectCommandParser#parse(arguments)` to produce a `PriorityProjectCommand` to be executed by the `LogicManager`.

Step 4. `LogicManager` calls `PriorityProjectCommand#execute(model)` to produce a `CommandResult`.

Step 5. During the execution of the `PriorityProjectCommand`, new projects are created with the same details as the projects whose `Priority` is supposed to be changed, except that the `Priority` for all the projects are now high.

Step 6. The model is updated accordingly through `ModelManager` by replacing the old projects with the new projects with the updated `Priority`.

Step 7. A `CommandResult` is produced based on whether the execution was a success or not and returned to the `LogicManager`.

### Find Projects Feature

The displayed list of `Project`s can be filtered by name (specifically, the keywords that the project name contains).

The `findP` command accepts a sequence of keywords (at least 1) and filters the displayed `Project`s accordingly.

Once the `Project`s are filtered out, the displayed list of `Employee`s will also be filtered out, according to whether
they are under at least one of the said filtered `Project`s.

Given below is an example usage scenario and the internal changes that happen at each step.

![FindPSequenceDiagram](images/FindPSequenceDiagram.png)

Step 1. The user launches the application. All employees and projects will be shown to the user.

Step 2. The user executes the `findP website presentation` command to search for projects with name containing "website" and/or "presentation".
`LogicManager` will call `TaskHubParser#parse(input)` to extract the parameters and pass it to an `FindProjectCommandParser`.

Step 3. `TaskHubParser` will call `FindProjectCommandParser#parse(arguments)` to produce a `FindProjectCommand` to be executed by the `LogicManager`.

Step 4. `LogicManager` calls `FindProjectCommand#execute(model)` to produce a `CommandResult`.

Step 5. During the execution of the `FindProjectCommand`, the `ProjectNameContainsKeywordsPredicate` predicate that is received, is
used to update the model's `FilteredProjectList` through `ModelManager`. 

Step 6. Then, using the updated `FilteredProjectList` from step 5, a new `EmployeeUnderFilteredProjectsPredicate` predicate is created. Using this predicate, 
the model's `FilteredEmployeeList` is updated through `ModelManager`.

Step 7. A `CommandResult` is produced based on whether the execution was a success or not and returned to the `LogicManager`.


### Mark Projects feature

Execution of the `markP` command will result in each `Project` being marked as completed. This is done by setting each `Project`'s `isCompleted` attribute to `true`.

Given below is an example usage scenario and the internal changes that happen at each step.

![MarkPSequenceDiagram](images/MarkPSequenceDiagram.png)

Step 1. The user launches the application. All employees and projects will be shown to the user.

Step 2. The user executes `markP 1 3` to mark the 1st and 3rd `Project` on the shown list as completed. `LogicManager` will call `TaskHubParser#parse(input)` to extract the parameters and pass it to a `MarkProjectParser`.

Step 3. `TaskHubParser` will call `MarkProjectParser#parse(arguments)` to produce a `MarkProjectCommand` to be executed by the `LogicManager`.

Step 4. `LogicManager` calls `MarkProjectCommand#execute(model)` to produce a `CommandResult `to be logged.

Step 5. During the execution of the `MarkProjectCommand`, a new `Project` object is created for each `Project` to be marked as completed. The new `Project` objects are then used to replace the original `Project` objects in the `UniqueProjectList`.

Step 6. A `CommandResult` is produced based on whether the execution was a success or not and returned to the `LogicManager`.

A similar sequence of events will occur when executing the `unmarkP` command, except that the `isCompleted` attribute of each `Project` will be set to `false` instead of `true`.

### Storage Validation

When TaskHub is first loaded, the `taskhub.json` file is checked whether it can be converted into a valid TaskHub model.
This is to prevent undefined behaviour from users making modifications to the `taskhub.json` file.

Given below is an example usage scenario and the internal changes that happen at each step.

![StorageValidationSequenceDiagram](images/StorageValidationSequenceDiagram.png)

Step 1. The user launches the application.

Step 2. `Main` will look for the data file in the specified `filepath` in the `preferences.json` file and pass it to the `JsonTaskHubStorage`. If there is no file found,
        then a new `TaskHub` with sample data will be created.

Step 3. After retrieving the data file, `JsonTaskHubStorage` will call `JsonSerializableTaskHub` to check whether each `JsonAdaptedEmployee` fulfills the requirements of an `Employee`.
        If the requirements are fulfilled ([Employee Requirements here](#employee-component)), then each `JsonAdaptedEmployee` is converted into an `Employee` and added to a `UniqueEmployeeList`.

Step 4. `JsonSerializableTaskHub` will then check whether each `JsonAdaptedProject` fulfills the requirements of a `Project`.
        If the requirements are fulfilled ([Project Requirements here](#project-component)), then each `JsonAdaptedProject` is converted into a `Project` and added to a `UniqueProjectList`.

If any `JsonAdaptedEmployee` or `JsonAdaptedProject` fails to meet the requirements, an empty `Taskhub` is returned.

### \[Proposed\] Undo/redo feature

#### Proposed Implementation

The proposed undo/redo mechanism is facilitated by `VersionedTaskHub`. It extends `TaskHub` with an undo/redo history, stored internally as an `taskHubStateList` and `currentStatePointer`. Additionally, it implements the following operations:

* `VersionedTaskHub#commit()` — Saves the current TaskHub state in its history.
* `VersionedTaskHub#undo()` — Restores the previous TaskHub state from its history.
* `VersionedTaskHub#redo()` — Restores a previously undone TaskHub state from its history.

These operations are exposed in the `Model` interface as `Model#commitTaskHub()`, `Model#undoTaskHub()` and `Model#redoTaskHub()` respectively.

Given below is an example usage scenario and how the undo/redo mechanism behaves at each step.

Step 1. The user launches the application for the first time. The `VersionedTaskHub` will be initialized with the initial TaskHub state, and the `currentStatePointer` pointing to that single TaskHub state.

![UndoRedoState0](images/UndoRedoState0.png)

Step 2. The user executes `delete 5` command to delete the 5th employee in the TaskHub. The `delete` command calls `Model#commitTaskHub()`, causing the modified state of the TaskHub after the `delete 5` command executes to be saved in the `taskHubStateList`, and the `currentStatePointer` is shifted to the newly inserted TaskHub state.

![UndoRedoState1](images/UndoRedoState1.png)

Step 3. The user executes `add n/David …​` to add a new employee. The `add` command also calls `Model#commitTaskHub()`, causing another modified TaskHub state to be saved into the `taskHubStateList`.

![UndoRedoState2](images/UndoRedoState2.png)

<div markdown="span" class="alert alert-info">:information_source: **Note:** If a command fails its execution, it will not call `Model#commitTaskHub()`, so the TaskHub state will not be saved into the `taskHubStateList`.

</div>

Step 4. The user now decides that adding the employee was a mistake, and decides to undo that action by executing the `undo` command. The `undo` command will call `Model#undoTaskHub()`, which will shift the `currentStatePointer` once to the left, pointing it to the previous TaskHub state, and restores the TaskHub to that state.

![UndoRedoState3](images/UndoRedoState3.png)

<div markdown="span" class="alert alert-info">:information_source: **Note:** If the `currentStatePointer` is at index 0, pointing to the initial TaskHub state, then there are no previous TaskHub states to restore. The `undo` command uses `Model#canUndoTaskHub()` to check if this is the case. If so, it will return an error to the user rather
than attempting to perform the undo.

</div>

The following sequence diagram shows how the undo operation works:

![UndoSequenceDiagram](images/UndoSequenceDiagram.png)

<div markdown="span" class="alert alert-info">:information_source: **Note:** The lifeline for `UndoCommand` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline reaches the end of diagram.

</div>

The `redo` command does the opposite — it calls `Model#redoTaskHub()`, which shifts the `currentStatePointer` once to the right, pointing to the previously undone state, and restores the TaskHub to that state.

<div markdown="span" class="alert alert-info">:information_source: **Note:** If the `currentStatePointer` is at index `taskHubStateList.size() - 1`, pointing to the latest TaskHub state, then there are no undone TaskHub states to restore. The `redo` command uses `Model#canRedoTaskHub()` to check if this is the case. If so, it will return an error to the user rather than attempting to perform the redo.

</div>

Step 5. The user then decides to execute the command `list`. Commands that do not modify the TaskHub, such as `list`, will usually not call `Model#commitTaskHub()`, `Model#undoTaskHub()` or `Model#redoTaskHub()`. Thus, the `taskHubStateList` remains unchanged.

![UndoRedoState4](images/UndoRedoState4.png)

Step 6. The user executes `clear`, which calls `Model#commitTaskHub()`. Since the `currentStatePointer` is not pointing at the end of the `taskHubStateList`, all TaskHub states after the `currentStatePointer` will be purged. Reason: It no longer makes sense to redo the `add n/David …​` command. This is the behavior that most modern desktop applications follow.

![UndoRedoState5](images/UndoRedoState5.png)

The following activity diagram summarizes what happens when a user executes a new command:

<img src="images/CommitActivityDiagram.png" width="250" />

#### Design considerations:

**Aspect: How undo & redo executes:**

* **Alternative 1 (current choice):** Saves the entire TaskHub.
  * Pros: Easy to implement.
  * Cons: May have performance issues in terms of memory usage.

* **Alternative 2:** Individual command knows how to undo/redo by
  itself.
  * Pros: Will use less memory (e.g. for `delete`, just save the employee being deleted).
  * Cons: We must ensure that the implementation of each individual command are correct.

_{more aspects and alternatives to be added}_

### \[Proposed\] Data archiving

_{Explain here how the data archiving feature will be implemented}_

### \[Proposed\] Deadline validation between Tasks and Projects

#### Proposed Implementation

Currently, when a user enters a deadline for a task for a given project, any possible date can be entered including deadlines past the deadline of the project itself.

We recognise that there is always a probability that tasks still can be added beyond a project's deadline, so we will not prevent users from doing so, but this scenario is not common.

Beyond the possibility of users keying in a task deadline that is past the project deadline on purpose, there is also the issue that it may be accidental.

Due to the aforementioned reasons, we believe the project deadlines and task deadlines should be validated.

To be more specific, when tasks that have deadlines past the project deadline are added, there should be a warning indicating that a task with a deadline past the project deadline was added.

Given below is an example usage scenario and how the validation mechanism behaves at each step.

Step 1. The user executes `addT` to add a task to a project with a deadline past the project deadline.

Step 2. The task is added as per normal in the storage, but a warning is also displayed in the `ResultDisplay`

```
New task added to project 1, Description: Task In a Project; Deadline: 13 Nov 2023, 11:59PM
The task has a deadline past the project deadline! Check again if the details are correct and edit if needed!
```

Step 3. If the user intended to add a task with that deadline, then he/she would continue but if it was not intended, they would be alerted to the issue and be able to delete the task.

### \[Proposed\] Multiple employees assigned to each task

#### Proposed Implementation

In this version of TaskHub, only 1 employee can be assigned at a time to each task.

Tasks in a project can be worked on by multiple people at a time, so it would be more appropriate to expand the task to take on more than 1 employee.

The current implementation of tasks is already using a `List` that has been restricted to hold only one `Employee`.

`List` was chosen as the data type for storing assigned `Employees` in preparation for a future iteration where more employees could be held in each task.

Thus, expanding `Task` to take more than one employee would simply involve allowing the list to take more than 1 `Employee`.

However, this was not done in the current implementation due to the already complex nature of the `assignT` command which has to modify multiple instances of objects stored in the model.

### \[Proposed\] Allowing multiple spaces between indexes

#### Proposed Implementation

TaskHub is not currently able to handle multiple spaces between indexes.

In order to deal with multiple spaces, the following solution can be used (taken from [this StackOverflow discussion](https://stackoverflow.com/questions/2932392/java-how-to-replace-2-or-more-spaces-with-single-space-in-string-and-delete-lead))

```Java
String after = before.trim().replaceAll(" +", " ");
```

This will allow indexes with multiple spaces between indexes to be handled automatically instead of the user having to find where they may have put the additional space(s).

--------------------------------------------------------------------------------------------------------------------

## **Documentation, logging, testing, configuration, dev-ops**

* [Documentation guide](Documentation.md)
* [Testing guide](Testing.md)
* [Logging guide](Logging.md)
* [Configuration guide](Configuration.md)
* [DevOps guide](DevOps.md)

--------------------------------------------------------------------------------------------------------------------

## **Appendix: Requirements**

### Product scope

**Target user profile**:

* has a need to manage a significant number of employees in a project management context
* has a need to manage projects with multiple timelines
* has a need to quickly delegate and manage distribution of tasks and projects among employees
* prefer desktop apps over other types
* can type fast
* prefers typing to mouse interactions
* is reasonably comfortable using CLI apps

**Value proposition**: manage projects and task delegation faster than a typical mouse/GUI driven app


### User stories

Priorities: High (must have) - `* * *`, Medium (nice to have) - `* *`, Low (unlikely to have) - `*`

| Priority | As a …​                             | I want to …​                  | So that I can…​                                                          |
|----------|-------------------------------------|-------------------------------|--------------------------------------------------------------------------|
| `* * *`  | completely new user                 | see usage instructions        | refer to instructions when I forget how to use the App                   |
| `* * *`  | user                                | add a new employee            | easily manage and access employees in one place                          |
| `* * *`  | user                                | delete an employee            | remove entries that I no longer need                                     |
| `* * *`  | user                                | edit an employee              | change the details of the employee                                       |
| `* * *`  | user                                | list all employees            | see an overview of all the employees                                     |
| `* * *`  | user                                | add a new project             | easily manage and access projects in one place                           |
| `* * *`  | user                                | delete a project              | remove entries that I no longer need                                     |
| `* * *`  | user                                | edit a project                | change the details of the project                                        |
| `* * *`  | user                                | list all projects             | see an overview of all my projects                                       |
| `* * *`  | user                                | find an employee by name      | locate details of employees without having to go through the entire list |
| `* * *`  | user                                | assign employees to a project | know which employees are on which projects in one place                  |
| `* *`    | completely new user                 | have sample data              | practice some commands before trying the App                             |
| `* *`    | user                                | purge all data                | get rid of sample/experimental data and add in my actual data            |
| `*`      | user with many projects in TaskHub  | sort projects by date         | tell which project deadlines are coming soon                             |
| `*`      | user with many employees in TaskHub | sort employees by name        | locate an employee easily                                                |
| `*`      | new user                            | get autocomplete suggestions  | write commands without referring to usage instructions often             |

*{More to be added}*

### Use cases

(For all use cases below, the **System** is the `TaskHub` and the **Actor** is the `user`, unless specified otherwise)

**Use case 1: Delete a employee**

**MSS**

1.  User requests to list employees
2.  TaskHub shows a list of employees
3.  User requests to delete a specific employee in the list
4.  TaskHub deletes the employee

    Use case ends.

**Extensions**

* 2a. The list is empty.

  Use case ends.

* 3a. The given index is invalid.

    * 3a1. TaskHub shows an error message.

      Use case resumes at step 2.


**Use case 2: Delete a project**

**MSS**

1.  User requests to list projects
2.  TaskHub shows a list of projects
3.  User requests to delete a specific project in the list
4.  TaskHub deletes the employee

    Use case ends.

**Extensions**

* 2a. The list is empty.

  Use case ends.

* 3a. The given index is invalid.

    * 3a1. TaskHub shows an error message.

      Use case resumes at step 2.

**Use case 3: Add an employee**

**MSS**

1. User attempts to add employee
2. TaskHub shows a message that indicates a successful operation

    Use case ends.

**Extensions**
* 1a. The input does not follow the format.
  * 1a1. TaskHub shows an error message with the correct format.

Use case resumes at step 1.

**Use case 4: Add an employee to a project**

**MSS**

1. User attempts to add an employee to a project
2. TaskHub shows a message that indicates a successful operation

   Use case ends.

**Extensions**

* 1a. The Project does not exist.
    * 1a1. The user requests to create a new project with the Employees.
      
        Use case ends.
* 1b. The Employee does not exist.
    * 1b1. The user attempts to add the Employee.(Use Case 3)

  Use case resumes at step 1.


### Non-Functional Requirements

1.  Should work on any _mainstream OS_ as long as it has Java `11` or above installed.
2.  Should be able to hold up to 1000 employees without a noticeable(More than 2s) sluggishness in performance for typical usage.
3.  A user with above average typing speed for regular English text (i.e. not code, not system admin commands) should be able to accomplish most of the tasks faster using commands than using the mouse.
4.  Should continue to work in most file directories as long as TaskHub and its data file maintain the same structure.
5.  Should be easily usable even by a first time user.

*{More to be added}*

### Glossary

* **Mainstream OS**: Windows, Linux, Unix, macOS

--------------------------------------------------------------------------------------------------------------------

## **Appendix: Instructions for manual testing**

Given below are instructions to test the app manually.

<div markdown="span" class="alert alert-info">:information_source: **Note:** These instructions only provide a starting point for testers to work on;
testers are expected to do more *exploratory* testing.

</div>

### Launch and shutdown

1. Initial launch

   1. Download the jar file and copy into an empty folder

   2. Double-click the jar file Expected: Shows the GUI with a set of sample contacts. The window size may not be optimum.

2. Saving window preferences

   1. Resize the window to an optimum size. Move the window to a different location. Close the window.

   2. Re-launch the app by double-clicking the jar file.<br>
       Expected: The most recent window size and location is retained.

3. _{ more test cases …​ }_

### Deleting an employee

1. Deleting an employee while all employees are being shown

   1. Prerequisites: List all employees using the `list` command. Multiple employees in the list.

   2. Test case: `delete 1`<br>
      Expected: First contact is deleted from the list. Details of the deleted contact shown in the status message. Timestamp in the status bar is updated.

   3. Test case: `delete 0`<br>
      Expected: No employee is deleted. Error details shown in the status message. Status bar remains the same.

   4. Other incorrect delete commands to try: `delete`, `delete x`, `...` (where x is larger than the list size)<br>
      Expected: Similar to previous.

2. _{ more test cases …​ }_

### Saving data

1. Dealing with missing/corrupted data files

   1. _{explain how to simulate a missing/corrupted file, and the expected behavior}_

2. _{ more test cases …​ }_
