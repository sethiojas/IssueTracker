package com.issue_tracker.gui;

/**Users which can work on projects i.e. Contributors
*Implement this interface So that no specific controller
*object has to be initialized when going back from Project
*Screen beck to caller.
*This helps to utilize polymorphism at our advantage.
*
*NOTE: Method name is kept 'initialize' for continuity purpose
*not all Classes which have same initialize method implement
*this Interface 
*/

interface HasProjects {
    public void initialize(String name);
}