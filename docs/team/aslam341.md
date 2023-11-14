---
layout: page
title: Aslam's Project Portfolio Page
---

### Project: TaskHub

TaskHub is a desktop project management application used by project managers to manage projects and their team members in each project. The user interacts with it using a CLI, and it has a GUI created with JavaFX. It is written in Java.
Given below are my contributions to the project.

* **New Feature**: Added the ability to set deadlines for projects. (Pull request [\#109](https://github.com/AY2324S1-CS2103T-T08-3/tp/pull/109))
  * What it does: Lets the user set deadlines for projects and able to visually tell if the deadline has passed or not.
  * Justification: Many projects tend to have deadlines, and it is important to be aware of them to stay on track.

* **New Feature**: Added the mark/un-mark projects commands. (Pull request [\#137](https://github.com/AY2324S1-CS2103T-T08-3/tp/pull/137))
  * What it does: Lets the user set the completion status of a project.
  * Justification: Allows users to know which projects are not yet completed, which makes it easier to for them to manage their projects.

* **New Feature**: Added the ability to un-assign members from a project. (Pull request [\#151](https://github.com/AY2324S1-CS2103T-T08-3/tp/pull/151))
  * What it does: Lets the user un-assign members from a project.
  * Justification: There was an existing assign feature, but no un-assign feature. This new feature will allow users to remove members from projects they had assigned previously,
    if they are no longer needed in the project.
  * Highlights: The implementation was challenging as it requires verification of the member's existence in the project before un-assigning them.

* **New Feature**: Added the ability to edit some details of project. (Pull request [\#171](https://github.com/AY2324S1-CS2103T-T08-3/tp/pull/171))
  * What it does: Lets the user edit the name, priority, and deadline of a project.
  * Justification: Allows users to make changes to their projects if they need to, without having to delete and recreate the project with the new details.

* **Code contributed**: [RepoSense link](https://nus-cs2103-ay2324s1.github.io/tp-dashboard/?search=aslam341&breakdown=true)

  * **Project management**: 
  * Setting milestones and deadlines for the team, ensuring that the team is on track to meet the deadlines, and ensuring that the team is on track to meet the milestones.
  * Contributed to issue-tracking and management.
  * Managed the latest [milestone 1.4 release](https://github.com/AY2324S1-CS2103T-T08-3/tp/releases).

* **Enhancements to existing features**: 
  * Enhanced the existing deadline and priority commands to set the respective attributes for multiple projects at once, from only being able to set for one project at a time.
    This is due to the introduction of the edit project command, which allows user to set these attributes for one project at a time, making the existing aforementioned commands redundant. (Pull request [\#174](https://github.com/AY2324S1-CS2103T-T08-3/tp/pull/174))
  * The enhancement listed above led to the creation of a new method to parse multiple indexes from the user input, which is now not only used in deadline and priority commands for projects, but also
    many other commands where parsing of multiple indexes can be used, thus indirectly enhancing those commands implemented by other group members. Some pull requests from others that showcases the use of my new method are
    [\#140](https://github.com/AY2324S1-CS2103T-T08-3/tp/pull/140) and [\#143](https://github.com/AY2324S1-CS2103T-T08-3/tp/pull/143).
  * Improved on the existing sample data to have much more information for new users to try out commands on and understand the application better. (Pull request [\#179](https://github.com/AY2324S1-CS2103T-T08-3/tp/pull/179))

* **Contributions to team-based tasks**:
  * Renamed all references to the original AddressBook project to TaskHub. (Pull requests [\#87](https://github.com/AY2324S1-CS2103T-T08-3/tp/pull/87), [\#97](https://github.com/AY2324S1-CS2103T-T08-3/tp/pull/97))
  * Renamed all references to Person model to Employee model. (Pull requests [\#83](https://github.com/AY2324S1-CS2103T-T08-3/tp/pull/83), [\#97](https://github.com/AY2324S1-CS2103T-T08-3/tp/pull/97)) 
  * Enable java assertions in the project. (Pull request [\#119](https://github.com/AY2324S1-CS2103T-T08-3/tp/pull/119))

* **Review/mentoring contributions**:
  * PRs reviewed (with non-trivial review comments): 
    (Pull requests [\#111](https://github.com/AY2324S1-CS2103T-T08-3/tp/pull/111#discussion_r1371983270),
    [\#138](https://github.com/AY2324S1-CS2103T-T08-3/tp/pull/138#discussion_r1375265054),
    [\#156](https://github.com/AY2324S1-CS2103T-T08-3/tp/pull/156), and more...) 

* **Documentation**:
  * User Guide:
    * Added documentation for the new features I implemented which are mentioned above.
    * Ensuring correctness of the features in the user guide, such as command format and example usages, for the entire user guide.
    * Added command parameter tables for each command section, inspired from another current team [W08-1](https://ay2324s1-cs2103t-w08-1.github.io/tp/UserGuide.html#command-parameters-1).

  * Developer Guide:
    * Added most of the user stories.
    * Documented the design of the mark project command.
    * Documented the design of commands related to setting project deadlines.
    * Added all the use cases.
