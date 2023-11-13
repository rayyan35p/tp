---
layout: page
title: Rayyan's Project Portfolio Page
---

### Project: TaskHub

TaskHub is a desktop project management application used by project managers to manage projects and their team members in each project. The user interacts with it using a CLI, and it has a GUI created with JavaFX. It is written in Java.

Given below are my contributions to the project.

* **New Feature**: Added the ability to prioritise projects [\#113](https://github.com/AY2324S1-CS2103T-T08-3/tp/pull/113)
    * What it does: Allows the user to set a priority for each project.
    * Justification: This feature allows the user to be able to more easily see which projects need to be worked on as higher priority projects will stand out.

* **New Feature**: Added the ability to assign and unassign tasks within projects to employees [\#156](https://github.com/AY2324S1-CS2103T-T08-3/tp/pull/156)
    * What it does: Allows the user to assign a task in a project to an employee within that project or unassign a task that has been assigned to an employee. 
    * Justification: This feature lets the user keep track of which employee should be doing each task.
    * Highlights: Due to the nature of how tasks are stored in projects, and employees are stored in tasks, this was particularly complex as there were many possible cases where a command that might change an employee's details does not affect the employee within the task. All possible scenarios had to be considered to ensure there were no bugs.

* **Code contributed**: [RepoSense link](https://nus-cs2103-ay2324s1.github.io/tp-dashboard/?search=rayyan35p&breakdown=false&sort=groupTitle%20dsc&sortWithin=title&since=2023-09-22&timeframe=commit&mergegroup=&groupSelect=groupByRepos)

* **Project management**:
    * Managed release `v1.2.1` on GitHub

* **Enhancements to existing features**:

* **Documentation**:
    * User Guide:
        * Added documentation for the features `priorityP`, `assignT` and `uassignT` [\#160](https://github.com/AY2324S1-CS2103T-T08-3/tp/pull/160)
        * Updated the UG to replace outdated information and screenshots with updated TaskHub information [\#181](https://github.com/AY2324S1-CS2103T-T08-3/tp/pull/181)
    * Developer Guide:
        * Added non-functional requirements.
        * Added implementation details of the `priorityP` feature.
        * Added instructions for manual testing for all the TaskHub commands. [\#264](https://github.com/AY2324S1-CS2103T-T08-3/tp/pull/264)

* **Team-based Tasks**:
    * PRs reviewed (with non-trivial review comments): [\#130](https://github.com/AY2324S1-CS2103T-T08-3/tp/pull/130), [\#138](https://github.com/AY2324S1-CS2103T-T08-3/tp/pull/138), [\#154](https://github.com/AY2324S1-CS2103T-T08-3/tp/pull/154)
    * Refactored list command into listE.
    * Morphed list and clear commands into TaskHub commands.
