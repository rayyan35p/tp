---
layout: page
title: Anton's Project Portfolio Page
---

### Project: TaskHub

TaskHub is a desktop project management application used by project managers to manage projects and their team members in each project. The user interacts with it using a CLI, and it has a GUI created with JavaFX. It is written in Java.

Given below are my contributions to the project.

* `Project` model: 
  * Created the infrastructure for projects in `model` files.
  * Created `JsonAdaptedProject` for projects to be locally stored in TaskHub.
  * Updated `TaskHub` to store `Projects` using `UniqueProjectList` (inspired by `UniqueEmployeeList`)


* **New Feature**: Added the ability to add and delete projects.
  * What it does: Lets the user add and delete new projects to TaskHub.
  * Justification: The core feature of TaskHub is to manage projects for project managers. Users will need to add and delete projects first to plan how projects should proceed.

* **New Feature**: Added the ability to assign employees to projects.
  * What it does: Lets the user assign employees to be a part of a project.
  * Justification: Allows users to add employees to a project, so that they can better manage how many people should be in a project.

* **Icon Design**: Designed the icon for TaskHub. <br> ![TaskHubIcon](../../src/main/resources/images/task_hub_32.png)

* **Logo Design**: Created the Logo for TaskHub using Figma with Timothy's design from Visily. <br>![TaskHubIcon](../../src/main/resources/images/task_hub_logo.png)
* **Documentation**:
    * User Guide:
        * Added documentation for the feature `addP`,`assignP`,`deleteP`
    * Developer Guide:
        * Added Use Case Scenarios 
        * Updated the Storage section to fit our application.
        * Documented the design of Add Projects command and Storage Validation.

