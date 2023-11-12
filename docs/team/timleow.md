---
layout: page
title: Timothy Leow's Project Portfolio Page
---

### Project: TaskHub

## Overview
TaskHub is a desktop project management application used by project managers to manage projects and their team members in each project. The user interacts with it using a CLI, and it has a GUI created with JavaFX. It is written in Java.
## Summary of Contributions

### Code Contributed:
My code contributions to TaskHub can be found [here](https://nus-cs2103-ay2324s1.github.io/tp-dashboard/?search=&sort=groupTitle&sortWithin=title&timeframe=commit&mergegroup=&groupSelect=groupByRepos&breakdown=true&checkedFileTypes=docs~functional-code~test-code&since=2023-09-22&tabOpen=true&tabType=authorship&tabAuthor=timleow&tabRepo=AY2324S1-CS2103T-T08-3%2Ftp%5Bmaster%5D&authorshipIsMergeGroup=false&authorshipFileTypes=docs~functional-code~test-code&authorshipIsBinaryFileTypeChecked=false&authorshipIsIgnoredFilesChecked=false%23%2F):

### Enhancements implemented:
<img src="../images/HelpWindow.png" width="280" />

- **Upgraded Help Window:**
  - Modified `Ui` for `HelpWindow.java`.
  - Displays a quick guide of all commands.
  - Provides a clickable hyperlink to the user guide at the bottom, instead of the original 'copy to clipboard' feature.
  - Added value to the `help` command.


<img src="../images/TasksUi.png" width="280" />

- **`Task` Model, `addT`, and `deleteT` Command:**
  - Created infrastructure for tasks in both the `model` and `Ui` files - the Ui can be seen in the 'Tasks' section of the `Project`s in the screenshot above.
  - Implemented basic functionality for adding and deleting tasks.
  - Efficiently manages multiple task deletions.
  - Enhanced `addT` to allow task assignment to an employee upon creation using the optional `em/` parameter.


### Contributions to the UG:
- **Introductory section**
  - Getting Started
    - Installing and launching TaskHub
    - Understanding the components of TaskHub
- **Features section**
  - Explanation for `addT` command.
  - Explanation for `deleteT` command.

### Contributions to the DG:
- **Architecture, UI Component, Logic component, Model Component, Employee, Project, Task components**
  - Addition of tasks to the Class Diagram for Ui component 
  - Minor updates and diagram changes to other components (e.g., removing attributes section, updating command names/links)
- **Implementation Section**
  - Upgraded Help Feature
  - Add Task feature (created sequence diagram for this)
### Contributions to team-based tasks:
- Set up the GitHub team org/repo
- Updated diagrams in DG related to Tasks
- Created tags to help with bug triaging post PE-D
- Generated table for PR review allocations here:

<img src="../images/PRReview.png" width="280" />

### Review/mentoring contributions:
- Within the team, we had decided that each of us were to review 2 of our teammates. I mainly reviewed Anton and Aslam's PRs.
- Helped Anton with centering the TaskHub logo in [this PR ](https://github.com/AY2324S1-CS2103T-T08-3/tp/pull/167/files/9539b1872903d50de0d94125b550a784ac42b0cf), by providing a code block which gave advice on handling positioning of components on Java FXML.
- Gave Aslam code quality suggestions in [this PR](https://github.com/AY2324S1-CS2103T-T08-3/tp/pull/109) so that we could sync up our implementation of deadlines in both `Tasks` and `Projects`.

### Contributions beyond the project team:
To be added soon.
#### Evidence of helping others e.g. responses you posted in our forum, bugs you reported in other team's products,
- Reported an above average bug count of 10 with value-added suggestions to my allocated PE-D team, can be seen from the [PE-D](https://github.com/timleow/ped/issues) repo.
  <img src="../images/timPEDbugs.png" width="280" />

#### Evidence of technical leadership e.g. sharing useful information in the forum
- Participated in the forum in this [issue](https://github.com/nus-cs2103-AY2324S1/forum/issues/145).
--- ---

### Contribution to developer guide (extracts)
To be added soon.
### Contribution to user guide (extracts)
To be added soon.

