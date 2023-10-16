---
layout: page
title: User Guide
---

TaskHub is a **desktop app for managing contacts, optimized for use via a Command Line Interface** (CLI) while still having the benefits of a Graphical User Interface (GUI). If you can type fast, TaskHub can get your project management tasks done faster than traditional GUI apps.

* Table of Contents
{:toc}

--------------------------------------------------------------------------------------------------------------------

## Quick start

1. Ensure you have Java `11` or above installed in your Computer.

1. Download the latest `taskhub.jar` from [here](https://github.com/se-edu/addressbook-level3/releases). (TODO: update link with github release. this link is still AB3 for now)

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

## Features

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

* Extraneous parameters for commands that do not take in parameters (such as `help`, `list`, `exit` and `clear`) will be ignored.<br>
  e.g. if the command specifies `help 123`, it will be interpreted as `help`.

* If you are using a PDF version of this document, be careful when copying and pasting commands that span multiple lines as space characters surrounding line-breaks may be omitted when copied over to the application.
</div>

### Viewing help : `help`

#### GUI Help: 
Upon clicking help from the GUI as shown below,

![circled_help_gui](images/circledHelpGUI.png)

a message is displayed, with the link to our user guide. 

![help message](images/helpMessage.png)

#### CLI Help
Alternatively, quickly enter a `help` command into TaskHub to display the pop-up shown below.
(TODO replace screenshot).


<img src="https://i.imgur.com/WNVqICQ.png" alt="help_popup" height="300"/>

Format: `help`


### Adding an employee: `addE`

Adds an employee to the employees list.

Format: `addE n/NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS [t/TAG]…​`

<div markdown="span" class="alert alert-primary">:bulb: **Tip:**
An employee can have any number of tags (including 0)
</div>

Examples:
* `addE n/John Doe p/98765432 e/johnd@example.com a/John street, block 123, #01-01`
* `addE n/Betsy Crowe t/Junior SWE e/betsycrowe@example.com a/Brick street p/+65 9123 4567 t/Employee of the month`

### Listing all employees : `listE`

Shows a list of all employees in TaskHub.

![list example](images/list.png)

Format: `listE`

### Editing an employee : `edit`

Edits an existing employee in the address book.

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

### Locating employees by name: `findE`

Finds employees whose names contain any of the given keywords.

Format: `findE KEYWORD [MORE_KEYWORDS]`

* The search is case-insensitive. e.g `hans` will match `Hans`
* The order of the keywords does not matter. e.g. `Hans Bo` will match `Bo Hans`
* Only the name is searched.
* Only full words will be matched e.g. `Han` will not match `Hans`
* employees matching at least one keyword will be returned (i.e. `OR` search).
  e.g. `Hans Bo` will return `Hans Gruber`, `Bo Yang`

Examples:
* `findE John` returns `john` and `John Doe`
* `findE alex david` returns `Alex Yeoh`, `David Li`
* `findE james` returns `James` <br>
  ![result for 'findE james'](images/findEjamesresult.png)

### Listing all projects: `listP`

Shows a list of all projects in TaskHub.

![list example](images/listp.png)

### Deleting an employee : `deleteE`

Deletes the specified employee from the employees list.

Format: `deleteE INDEX`

* Deletes the employee at the specified `INDEX`.
* The index refers to the index number shown in the displayed employees list.
* The index **must be a positive integer** 1, 2, 3, …​

Examples:
* `listE` followed by `deleteE 2` deletes the 2nd employee in the employees list.
* `findE Betsy` followed by `deleteE 1` deletes the 1st employee in the results of the `findE` command.

### Clearing all entries : `clear`

Clears all entries from TaskHub.

Format: `clear`

### Adding a new project : `addP`

Adds a new project in TaskHub

Format: `addP pr/PROJECT_NAME [e/EMPLOYEE_NAME] ...`

* Adds a new project with the employees assigned to the project.
* The employee must exist in the employees list. 

Examples: 
* `addP pr/Project1 e/Betsy` will add `Project1` to the projects list with `Betsy` assigned to the project.
* `addP pr/Project2` will add an empty `Project2` to the projects list.

### Assign employee(s) to a project: `assignE`

Assigns employee(s) to a project in TaskHub

Format: `assignE pr/PROJECT_NAME e/EMPLOYEE_NAME [e/MORE_EMPLOYEE_NAMES]...`
* The employee(s) will be assigned to the project
* The project name and employee names must exist in TaskHub.

Examples:
* `assignE pr/Project1 e/Anton e/Joe` will add `Anton` and `Joe` to `Project1`

### Delete a project: `deleteP`

Deletes the specified project from TaskHub.

Format: `deleteP INDEX`
* Deletes the project at the specified `INDEX`
* The index refers to the index number shown in the displayed employees list.
* The index __must be a positive integer__ 1, 2, 3,...

Examples:
* `listP` followed by `deleteP 2` deletes the 2nd project in TaskHub.

### Exiting the program : `exit`

Exits the program.

Format: `exit`

### Saving the data

TaskHub's data is saved in the hard disk automatically after any command that changes the data. There is no need to save manually.

### Editing the data file

AddressBook data are saved automatically as a JSON file `[JAR file location]/data/addressbook.json`. Advanced users are welcome to update data directly by editing that data file.

<div markdown="span" class="alert alert-warning">:exclamation: **Caution:**
If your changes to the data file makes its format invalid, AddressBook will discard all data and start with an empty data file at the next run. Hence, it is recommended to take a backup of the file before editing it.
</div>

### Archiving data files `[coming in v2.0]`

_Details coming soon ..._

--------------------------------------------------------------------------------------------------------------------

## FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with the file that contains the data of your previous TaskHub home folder.

--------------------------------------------------------------------------------------------------------------------

## Known issues

1. **When using multiple screens**, if you move the application to a secondary screen, and later switch to using only the primary screen, the GUI will open off-screen. The remedy is to delete the `preferences.json` file created by the application before running the application again.

--------------------------------------------------------------------------------------------------------------------

## Command summary

Action | Format, Examples
--------|------------------
**List all employees** | `listE`
**Clear** | `clear`
**Help** | `help`
**Add Employee** | `addE n/NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS [t/TAG]…​ r/REMARK` <br> e.g., `addE n/James Ho p/22224444 e/jamesho@example.com a/123, Clementi Rd, 1234665 t/friend t/colleague r/a good friend`
**Delete Employee** | `deleteE INDEX`<br> e.g., `deleteE 3`
**Find Employee** | `findE KEYWORD [MORE_KEYWORDS]`<br> e.g., `findE James Jake`
**List Projects** | `listP`
**Add Project** | `addP pr/PROJECT_NAME [e/EMPLOYEE_NAME]…​` <br> e.g, `addP pr/CS2103T e/Chandan e/Anton`
**Assign Employee to Project** | `assignE pr/PROJECT_NAME e/EMPLOYEE_NAME [e/MORE_EMPLOYEE_NAMES]…​` <br> e.g, `assignE pr/CS2103TP e/Candice e/John e/Boyd e/Pragg`
**Delete Project** | `deleteP INDEX`<br> e.g., `deleteP 3`

