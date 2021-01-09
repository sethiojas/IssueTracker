# Issue Tracker
![Issue Tracker Logo](https://github.com/sethiojas/IssueTracker/blob/master/Images/header.png "Icon")
It is exactly what the name suggests. An issue tracker application made in Java utilizing JavaFx, SQLite, Gluon SceneBuilder and JMetro.

![Login](https://github.com/sethiojas/readme_images/blob/master/Issue%20Tracker/login.gif "Login")

## Table of Contents
* [Levels of Access](#levels-of-access)
    * [Admin](#admin)
    * [Manager](#manager)
    * [Maintainer](#maintainer)
* [Security](#security)
* [Directory Structure](#directory-structure)

## Levels Of Access
Application provides 3 levels of access: Admin, Manager, Maintainer. Each access level has its own set of functions to perform.

### Admin
![Admin panel](https://github.com/sethiojas/readme_images/blob/master/Issue%20Tracker/admin-panel.gif "Admin")

Features of Admin Level Access
*   Ability to create or remove Managers and Maintainers.
*   Ability to assign Manager to a Maintainer who is on bench.
*   Put a maintainer on bench which is assigned to some Manager
*   Admin **can not** create projects and add bugs/issues to projects

There isn't an option to create more Admins. A single Admin account with credentials:
`username` : `admin`
`password` : `admin`
is provided in the database, the password for which **can be changed via Password tab**.

### Manager
![Manager Panel](https://github.com/sethiojas/readme_images/blob/master/Issue%20Tracker/manager-panel.gif "Manager")

Features of Manager Level Access
*   View the list of maintainers assigned to you.
*   Create or remove Projects
*   Create or close Bugs
*   View Projects assigned to a Maintainer and an option to remove
    that maintainer from project.

Managers have control over only those Maintainers which are assigned to them.
Managers cannot create or remove Maintainers like Admin.

### Maintainer
![Maintainer Panel](https://github.com/sethiojas/readme_images/blob/master/Issue%20Tracker/maintainer-panel.gif "Maintainer")

Lowest in the Hierarchy, Maintainers can create/raise new Issues and close the already existing
ones.

## Security
*   Passwords are Hashed with **SHA-256** before being saved into the database.
*   No members of this Hierarchy can access other's account. Admin can remove an existing
    account but **can not log into** any of the account other than theirs.

## Directory Structure
*   app_bin - Contains class file for GUI app
*   app_src - Source code (Java files) of GUI app
*   bin - Class files of issue_tracker API
*   src - Source code for issue_tracker API
*   fxml - FXML files of scenes
*   Images - images used
*   style - stylesheet used (CSS)
