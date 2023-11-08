---
layout: page
title: User Guide
---

TaskHub is a **desktop app for managing contacts, optimized for use via a Command Line Interface** (CLI) while still having the benefits of a Graphical User Interface (GUI). If you can type fast, TaskHub can get your project management tasks done faster than traditional GUI apps.

* Table of Contents
{:toc}

--------------------------------------------------------------------------------------------------------------------

# Quick start

1. Ensure you have Java `11` or above installed in your Computer.

1. Download the latest `taskhub.jar` from [here](https://github.com/AY2324S1-CS2103T-T08-3/tp/releases).

1. Copy the file to the folder you want to use as the _home folder_ for your TaskHub.

1. Open a command terminal, `cd` into the folder you put the jar file in, and use the `java -jar taskhub.jar` command to run the application.<br>
   A GUI similar to the below should appear in a few seconds. Note how the app contains some sample data.<br>
   ![Ui](images/Ui.png)

1. Type the command in the command box and press Enter to execute it. e.g. typing **`help`** and pressing Enter will open the help window.<br>
   Some example commands you can try:

   * `listE` : Lists all employees.

   * `addE n/John Doe p/98765432 e/johnd@example.com a/John street, block 123, #01-01` : Adds an employee named `John Doe` to the TaskHub.

   * `deleteE 3` : Deletes the 3rd employee shown in the current list.

   * `clear` : Deletes all data.

   * `exit` : Exits the app.

1. Refer to the [Features](#features) below for details of each command.

--------------------------------------------------------------------------------------------------------------------

# Features

<div markdown="block" class="alert alert-info">

**:information_source: Notes about the command format:**<br>

* Words in `UPPER_CASE` are the parameters to be supplied by the user.<br>
  e.g. in `addE n/NAME`, `NAME` is a parameter which can be used as `addE n/John Doe`.

* Items in square brackets are optional.<br>
  e.g `n/NAME [t/TAG]` can be used as `n/John Doe t/friend` or as `n/John Doe`.

* Items with `…`​ after them can be used multiple times including zero times.<br>
  e.g. `[t/TAG]…​` can be used as ` ` (i.e. 0 times), `t/friend`, `t/friend t/family` etc.

* Parameters can be in any order.<br>
  e.g. if the command specifies `n/NAME p/PHONE_NUMBER`, `p/PHONE_NUMBER n/NAME` is also acceptable.

* Extraneous parameters for commands that do not take in parameters (such as `help`, `list`, `listE`, `listP`, `exit` and `clear`) will be ignored.<br>
  e.g. if the command specifies `help 123`, it will be interpreted as `help`.

* Commands that contain `[MORE INDEXES]` are able to take in more than one index for the relevant parameter, separated by spaces.<br>
  e.g. `1`, `2 5 4`, `3 7`

* If you are using a PDF version of this document, be careful when copying and pasting commands that span multiple lines as space characters surrounding line-breaks may be omitted when copied over to the application.
</div>


<br>

## General Commands

### View help : `help`


#### GUI Help:
Upon clicking help on the top of the GUI as shown below,

![circled_help_gui](images/circledHelpGui.png)

a help window pops up with a list of all the possible commands:

![help window](images/HelpWindow.png)

and at the bottom of the window, is a link to our user guide.

![ug_link](images/HelpWindowUgLink.png)


#### CLI Help
Alternatively, enter a `help` command into TaskHub to quickly display the same help window without having to click on help.

Format: `help`


### List all employees and projects: `list`

Shows a list of all employees and projects in TaskHub.

Format: `list`


### Clear all entries : `clear`

Clears all entries from TaskHub.

Format: `clear`


### Exit the program : `exit`

Exits the program.

Format: `exit`


<br>

## Employee Commands


### Add an employee: `addE`

Adds an employee to the employees list.

Format: `addE n/NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS [t/TAG]…​`

<div markdown="span" class="alert alert-primary">:bulb: **Tip:**
An employee can have any number of tags (including 0)
</div>

Examples:
* `addE n/John Doe p/98765432 e/johnd@example.com a/John street, block 123, #01-01`
* `addE n/Betsy Crowe t/Junior e/betsycrowe@example.com a/Brick street p/91234567 t/Employee`


### Edit an employee : `editE`

Edits an existing employee in the TaskHub.

Format: `editE INDEX [n/NAME] [p/PHONE] [e/EMAIL] [a/ADDRESS] [t/TAG]…​`

* Edits the employee at the specified `INDEX`. The index refers to the index number shown in the displayed employee list. The index **must be a positive integer** 1, 2, 3, …​
* At least one of the optional fields must be provided.
* Existing values will be updated to the input values.
* When editing tags, the existing tags of the employee will be removed i.e adding of tags is not cumulative.
* You can remove all the employee’s tags by typing `t/` without
  specifying any tags after it.

Examples:
*  `editE 1 p/91234567 e/johndoe@example.com` Edits the phone number and email address of the 1st employee to be `91234567` and `johndoe@example.com` respectively.
*  `editE 2 n/Betsy Crower t/` Edits the name of the 2nd employee to be `Betsy Crower` and clears all existing tags.


### Delete an employee : `deleteE`

Deletes the specified employee from the employees list.

Format: `deleteE INDEX`

* Deletes the employee at the specified `INDEX`.
* The index refers to the index number shown in the displayed employees list.
* The index **must be a positive integer** 1, 2, 3, …​

Examples:
* `listE` followed by `deleteE 2` deletes the 2nd employee in the employees list.
* `findE Betsy` followed by `deleteE 1` deletes the 1st employee in the results of the `findE` command.


### List all employees : `listE`

Shows a list of all employees in TaskHub.

![list example](images/listE.png)

Format: `listE`


### Locate employees by name: `findE`

Finds employees whose names contain any of the given keywords.
Additionally, it shows only the projects that these employees are under.

Format: `findE KEYWORD [MORE_KEYWORDS]`

* The search is case-insensitive. e.g `hans` will match `Hans`
* The order of the keywords does not matter. e.g. `Hans Bo` will match `Bo Hans`
* Only the name is searched.
* Only full words will be matched e.g. `Han` will not match `Hans`
* Employees matching at least one keyword will be returned (i.e. `OR` search).
  e.g. `Hans Bo` will return `Hans Gruber`, `Bo Yang`

Examples:
* `findE John` returns `john` and `John Doe`
* `findE alex david` returns `Alex Yeoh`, `David Li`
* `findE david` returns `David Li` <br> 

  ![result for 'findE David'](images/findE.png)


<br>

## Project Commands


### Add a new project : `addP`

Adds a new project in TaskHub

Format: `addP n/PROJECT_NAME [em/EMPLOYEE_INDEXES] ...`

* Adds a new project with the employees assigned to the project.
* Each employee index __must be separated with a space.__
* The employee must exist in the employees list.
* There is no deadline by default.
* The priority is `normal` by default.
* The status is `not completed` by default.

Examples: 
* `addP n/Project1 em/1` will add `Project1` to the projects list with the employee index 1 assigned to the project.
* `addP n/Project2` will add an empty `Project2` to the projects list.


### Edit a project : `editP`

Edits an existing project in the TaskHub.

Format: `editP INDEX [n/NAME] [p/PRIORITY] [d/DEADLINE]`

* Edits the project at the specified `INDEX`. The index refers to the index number shown in the displayed project list. The index must be a positive integer 1, 2, 3, …​
* At least one of the optional fields must be provided.
* Existing values will be updated to the input values.
* If a `DEADLINE` date is provided, it must be in the `dd-MM-yyyy` format.
* You can remove the `DEADLINE` of the project by typing `d/` without specifying any deadline after it.
* You can also provide a `DEADLINE` date e.g. `d/01-01-2024`, in which case the provided deadline will overwrite the current deadline (if any).

Examples:
* `editP 1 n/Market Analysis p/low` Edits the name and priority of the 1st project to `Market Analysis` and `low` respectively.
* `editP 2 p/high d/` Edits the priority and deadline of the 2nd project to `high` and removes the deadline.


### Delete a project: `deleteP`

Deletes the specified project from TaskHub.

Format: `deleteP INDEX`
* Deletes the project at the specified `INDEX`.
* The index refers to the index number shown in the displayed employees list.
* The index __must be a positive integer__ 1, 2, 3,...

Examples:
* `listP` followed by `deleteP 2` deletes the 2nd project in TaskHub.


### Mark project(s) as completed: `markP`

Marks the specified project(s) as completed in TaskHub.

Format `markP INDEX [MORE_INDEXES]`

* Marks the project(s) at the specified `INDEX(ES)` as completed.
* The index(es) refers to the index number shown in the displayed projects list.
* The index(es) **must be a positive integers** 1, 2, 3,...

Examples:
* `listP` followed by `markP 1 3` marks the 1st and 3rd projects as completed in TaskHub.
* `findP Capstone` followed by `markP 1` marks the 1st project in the results of the `findP` command as completed.


### Mark project(s) as incomplete: `unmarkP`

Marks the specified project(s) as incomplete in TaskHub.

Format `unmarkP INDEX [MORE_INDEXES]`

* Marks the project(s) at the specified `INDEX(ES)` as incomplete.
* The index(es) refers to the index number shown in the displayed projects list.
* The index(es) **must be a positive integers** 1, 2, 3,...

Examples:
* `listP` followed by `unmarkP 1 3` marks the 1st and 3rd projects as incomplete in TaskHub.
* `findP Capstone` followed by `unmarkP 1` marks the 1st project in the results of the `findP` command as incomplete.


### Edit deadline of (a) project(s): `dlP`

Edits the deadline of the specified project(s) in the projects list.

Format: `dlP INDEX [MORE_INDEXES] d/[DEADLINE]`

* Edits the deadline of the project(s) at the specified `INDEX(ES)`.
* The index(es) refers to the index number shown in the displayed projects list.
* The index(es) **must be a positive integer** 1, 2, 3, …
* DATE refers to the date of the deadline.
* The `d/` prefix is required, but the `DEADLINE` is optional.
* If a date is provided, it must be in the `dd-MM-yyyy` format.
* Existing deadline will be updated to the new deadline.
* To remove the deadline of a project, leave the `DEADLINE` blank.

Examples:
*  `dlP 2 d/18-01-2022` sets the deadline of the 2nd project to be `18-01-2022`.
*  `dlP 1 2 d/` removes the deadline of the 1st and 2nd projects.
*  `findP Infinity` followed by `dlP 1 d/25-11-2024` sets the deadline of the 1st project in the results of the `findP` command to be `25-11-2024`.


### Prioritise projects: `priorityP`

Sets a priority for the specified project(s) in TaskHub.

Format: `priorityP INDEX [MORE_INDEXES] p/PRIORITY`
* Sets the priority of the project(s) at the specified `INDEX(ES)`.
* The index refers to the index number shown in the displayed projects list.
* The index __must be a positive integer__ 1, 2, 3,...
* The `PRIORITY` must be one of the following: `low`, `normal`, `high`.
* All projects are set to normal priority by default.

Examples:
* `listP` followed by `priorityP 1 2 p/high` sets the 1st and 2nd projects as high priority in TaskHub.


### List all projects: `listP`

Shows a list of all projects in TaskHub.

![list example](images/listP.png)

Format: `listP`


### Locate projects by name: `findP`

Finds projects whose names contain any of the given keywords.
Additionally, it shows only the employees that are under these projects.

Format: `findP KEYWORD [MORE_KEYWORDS]`

* The search is case-insensitive. e.g `website` will match `Website`
* The order of the keywords does not matter. e.g. `Create Website` will match `Website Create`
* Only the name is searched.
* Only full words will be matched e.g. `Website` will not match `Websites`
* Projects matching at least one keyword will be returned (i.e. `OR` search).
  e.g. `Website Model` will return `Create Website`, `Build Model`

Examples:
* `findP website model` returns `Create Website`, `Build Model`
* `findP market` returns `Market Expansion` <br>
  ![result for 'findP market'](images/findP.png)


<br>

## Task Commands


### Add a new task to a project: `addT`

Adds a new task to the specified project in TaskHub and assigns it to the (optionally) specified employee.

Format `addT pr/PROJECT_INDEX [em/EMPLOYEE_INDEX] n/TASK_NAME d/DEADLINE(dd-MM-yyyy HHmm) `

* Adds a new task to the project at the specified `PROJECT_INDEX`.
* The employee index field is optional - the task will be assigned to the employee at `EMPLOYEE_INDEX` if specified,
* otherwise it will have no assignee, and `assignT` can be used after that to assign it to an employee.
* The index(es) **must be positive integers** 1, 2, 3,...
* The deadline must be in the format `dd-MM-yyyy HHmm`. For example, `11-10-2023 2359` refers to 11th October 2023, 11.59pm.

Examples:
* `listP` followed by `addT pr/1 n/Website d/11-10-2023 2359` adds a task named `Website` with deadline `11-10-2023 2359` to the 1st project.
* `findP Capstone` followed by `addT pr/1 n/Website d/11-10-2023 2359` adds a task named `Website` with deadline `11-10-2023 2359` to the 1st project from the result of the `findP` command.


### Delete a task from a project: `deleteT`

Deletes the specified task(s) from the specified project in TaskHub

Format `deleteT pr/PROJECT_INDEX t/TASK_INDEX [MORE_TASK_INDEXES]`

* Deletes the tasks at the specified `TASK_INDEX`(es), from the project at the specified `PROJECT_INDEX`.
* The project index refers to the index number shown in the displayed projects list.
* The task index(es) refer to the index number(s) shown in the task list of the above specified project.
* The index(es) **must be positive integers** 1, 2, 3,...

Examples:
* `listP` followed by `deleteT pr/1 t/1 3` deletes the 1st and 3rd tasks of the 1st project from TaskHub.
* `findP Capstone` followed by `deleteT pr/2 t/2` deletes the 2nd task in the 2nd project from the results of the `findP` command.


### Mark task(s) as completed: `markT`

Marks the specified task(s) of a specified project as completed in TaskHub.

Format `markT pr/PROJECT_INDEX t/TASK_INDEX [MORE_TASK_INDEXES]`

* Marks the tasks(s) at the specified `TASK_INDEX`(es), of the project at the specified `PROJECT_INDEX` as completed.
* The project index refers to the index number shown in the displayed projects list.
* The task index(es) refer to the index number(s) shown in the task list of the above specified project.
* The index(es) **must be positive integers** 1, 2, 3,...

Examples:
* `listP` followed by `markT pr/1 t/1 3` marks the 1st and 3rd tasks of the 1st project as completed in TaskHub.
* `findP Capstone` followed by `markT pr/2 t/2` marks the 2nd task in the 2nd project from the results of the `findP` command as completed.


### Mark task(s) as incomplete: `unmarkT`

Marks the specified task(s) of a specified project as incomplete in TaskHub.

Format `unmarkT pr/PROJECT_INDEX t/TASK_INDEX [MORE_TASK_INDEXES]`

* Marks the tasks(s) at the specified `TASK_INDEX`(es), of the project at the specified `PROJECT_INDEX` as incomplete.
* The project index refers to the index number shown in the displayed projects list.
* The task index(es) refer to the index number(s) shown in the task list of the above specified project.
* The index(es) **must be positive integers** 1, 2, 3,...

Examples:
* `listP` followed by `unmarkT pr/1 t/1 3` marks the 1st and 3rd tasks of the 1st project as incomplete in TaskHub.
* `findP Capstone` followed by `unmarkT pr/2 t/2` marks the 2nd task in the 2nd project from the results of the `findP` command as incomplete.


### Sort tasks by deadline and completion status: `sortT`

Sorts the tasks in each project by their deadline and completion status.

Format: `sortT`

* The sorting of tasks is done within each project i.e tasks from different projects are not compared.
* Incomplete tasks will be placed before completed ones and tasks with an earlier deadline will be placed before those with a later one.
* Completion status takes precedence over deadline i.e. an incomplete task with a later deadline will be placed before a completed task with an earlier deadline.


<br>

## Assignment Commands


### Assign employee(s) to a project: `assignP`

Assigns employee(s) to a project in TaskHub

Format: `assignP pr/PROJECT_INDEX em/EMPLOYEE_INDEX [MORE_EMPLOYEE_INDEXES] ...`
* The employee(s) will be assigned to the project
* Each employee index __must be separated with a space.__
* The project and employee index refers to the index number shown in the displayed project and employee list.

Examples:
* `list` followed by `assignP pr/2 em/1 3` will assign the 1st and 3rd employees to the 2nd project.
* `findP Capstone` followed by `assignP pr/1 em/1` will assign the 1st employee to the 1st project from the results of the `findP` command.


### Un-assign employee(s) from a project: `unassignP`

Un-assigns employee(s) from a project in TaskHub

Format: `unassignP pr/PROJECT_INDEX em/EMPLOYEE_INDEX [MORE_EMPLOYEE_INDEXES] ...`

* The employee(s) will be unassigned from the project
* Each employee index __must be separated with a space.__
* The project and employee index refers to the index number shown in the displayed project and employee list.
* If the employee is assigned to a task within the project, the employee will be unassigned from the task as well.

Examples:
* `list` followed by `unassignP pr/2 em/1 3` will un-assign the 1st and 3rd employees from the 2nd project.
* `findP Capstone` followed by `unassignP pr/1 em/1` un-assigns the 1st employee from the 1st project from the results of the `findP` command.


### Assign an employee to a task: `assignT`

Assigns a specified employee in a specified project to a specified task in that project.

Format `assignT pr/PROJECT_INDEX t/TASK_INDEX em/EMPLOYEE_INDEX`

* The project index refers to the index number shown in the displayed projects list.
* The task index refers to the index number shown in the last list of the above specified project.
* The employee index refers to the index number of the employee **in the project list**.
* The index(es) **must be positive integers** 1, 2, 3,...

Examples:
* `listP` followed by `assignT pr/1 t/1 em/2` assigns the first task in the first project to the second employee in the project.
* `findP Capstone` followed by `assignT pr/2 t/2 em/2` assigns the second task, in the second project from the results of `findP`, to the second employee in the project.


### Un-assign an employee from a task: `unassignT`

Un-assigns the currently assigned employee from the specified task in the specified project.

Format `unassignT pr/PROJECT_INDEX t/TASK_INDEX`

* The project index refers to the index number shown in the displayed projects list.
* The task index refers to the index number shown in the last list of the above specified project.
* The index(es) **must be positive integers** 1, 2, 3,...

Examples:
* `listP` followed by `unassignT pr/1 t/1` un-assigns the first task in the first project from the currently assigned employee.
* `findP Capstone` followed by `unassignT pr/2 t/2` un-assigns the second task, in the second project from the results of `findP`, from the currently assigned employee.


<br>

## Data Storage

### Save the data

TaskHub's data is saved in the hard disk automatically after any command that changes the data. There is no need to save manually.

### Edit the data file

TaskHub data are saved automatically as a JSON file `[JAR file location]/data/taskhub.json`. Advanced users are welcome to update data directly by editing that data file.

<div markdown="span" class="alert alert-warning">:exclamation: **Caution:**
If your changes to the data file makes its format invalid, TaskHub will discard all data and start with an empty data file at the next run. Hence, it is recommended to take a backup of the file before editing it.
</div>

--------------------------------------------------------------------------------------------------------------------

## FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with the file that contains the data of your previous TaskHub home folder.

--------------------------------------------------------------------------------------------------------------------

## Known issues

1. **When using multiple screens**, if you move the application to a secondary screen, and later switch to using only the primary screen, the GUI will open off-screen. The remedy is to delete the `preferences.json` file created by the application before running the application again.

--------------------------------------------------------------------------------------------------------------------

## Command summary

| Action                                           | Format, Examples                                                                                                                                                                          |
|--------------------------------------------------|-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| **Help**                                         | `help`                                                                                                                                                                                    |
| **List All Employees And Projects**              | `list`                                                                                                                                                                                    |
| **Clear**                                        | `clear`                                                                                                                                                                                   |
| **Exit**                                         | `exit`                                                                                                                                                                                    |
| **Add Employee**                                 | `addE n/NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS [t/TAG]…​ r/REMARK` <br> e.g., `addE n/James Ho p/22224444 e/jamesho@example.com a/123, Clementi Rd, 1234665 t/friend t/colleague`          |
| **Edit Employee**                                | `editE INDEX [n/NAME] [p/PHONE_NUMBER] [e/EMAIL] [a/ADDRESS] [t/TAG]…​` <br> e.g., `editE 1 n/John Doe p/11114444 e/johndoe@example.com a/123, Clementi Rd, 1234665 t/friend t/colleague` |
| **Delete Employee**                              | `deleteE INDEX`<br> e.g., `deleteE 3`                                                                                                                                                     |
| **List All Employees**                           | `listE`                                                                                                                                                                                   |
| **Find Employee(s)**                             | `findE KEYWORD [MORE_KEYWORDS]`<br> e.g., `findE James Jake`                                                                                                                              |
| **Add Project**                                  | `addP n/PROJECT_NAME [em/EMPLOYEE_INDEX]…​` <br> e.g, `addP n/CS2103T em/2 3 4 5`                                                                                                         |
| **Edit Project**                                 | `editP INDEX [n/NAME] [p/PRIORITY] [d/DEADLINE]` <br> e.g., `editP 3 n/Create Website p/high`                                                                                             |
| **Delete Project**                               | `deleteP INDEX`<br> e.g., `deleteP 3`                                                                                                                                                     |
| **Mark Project(s) As Complete**                  | `markP INDEX [MORE_INDEXES]`<br> e.g., `markP 1 3`                                                                                                                                        |
| **Mark Project(s) As Incomplete**                | `unmarkP INDEX [MORE_INDEXES]`<br> e.g., `unmarkP 1 3`                                                                                                                                    |
| **Edit Project Deadline**                        | `dlP INDEX [MORE_INDEXES] [d/DEADLINE]` <br> e.g., `dlP 1 2 d/27-11-2023` <br>                                                                                                            |
| **Prioritise Project(s)**                        | `priorityP INDEX [MORE_INDEXES] p/PRIORITY` <br> e.g., `priorityP 1 2 p/high` <br>                                                                                                        |
| **List All Projects**                            | `listP`                                                                                                                                                                                   |
| **Find Project(s)**                              | `findP KEYWORD [MORE_KEYWORDS]`<br> e.g., `findP Wesite Create`                                                                                                                           |
| **Add Task**                                     | `addT pr/PROJECT_INDEX [em/EMPLOYEE_INDEX] n/TASK_NAME d/DEADLINE(dd-MM-yyyy HHmm)`<br> e.g., `addT pr/1 em/1 n/Read docs d/11-11-2023 2359`                                              |
| **Delete Task**                                  | `deleteT pr/PROJECT_INDEX t/TASK_INDEX [MORE_TASK_INDEXES]` <br> e.g., `deleteT pr/1 t/1 5 3`                                                                                             |
| **Mark Task(s) As Complete**                     | `markT pr/PROJECT_INDEX t/TASK_INDEX [MORE_TASK_INDEXES]`<br> e.g., `markT pr/1 t/1 3`                                                                                                    |
| **Mark Task(s) As Incomplete**                   | `unmarkT pr/PROJECT_INDEX t/TASK_INDEX [MORE_TASK_INDEXES]`<br> e.g., `unmarkT pr/1 t/1 3`                                                                                                |
| **Sort Tasks By Deadline And Completion Status** | `sortT`                                                                                                                                                                                   |
| **Assign Employee(s) To Project**                | `assignP pr/PROJECT_INDEX em/EMPLOYEE_INDEX [em/MORE_EMPLOYEE_INDEXES]…​` <br> e.g, `assignP pr/4 em/1 2 3`                                                                               |
| **Un-assign Employee(s) From Project**           | `unassignP pr/PROJECT_INDEX em/EMPLOYEE_INDEX [em/MORE_EMPLOYEE_INDEXES]` <br> e.g, `unassignP pr/2 em/1 3`                                                                               |
| **Assign Employee To Task**                      | `assignT` <br> e.g., `assignT pr/1 t/1 em/3`                                                                                                                                              |
| **Un-assign Employee From Task**                 | `unassignT` <br> e.g., `unassignT pr/1 t/1`                                                                                                                                               |
