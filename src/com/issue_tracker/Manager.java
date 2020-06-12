package com.issue_tracker;

import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.io.Serializable;

public class Manager extends Contributor implements Serializable {
    
    private List<String> maintainers = new ArrayList<>();

    // https://stackoverflow.com/questions/10378855/java-io-invalidclassexception-local-class-incompatible
    private static final long serialVersionUID = 6529685098267757691L;

    public Manager(String _uName){
        /**Create a manager object 
        */
        uName = _uName;
        saveContributor();
    }

    public void createProject(String title){
        /**Create a project
        *@param title   title of project to create 
        */
        Project proj = new Project(title);
        proj.addMaintainer(this.uName);
        addProject(proj.getProjectName(), proj.getProjectId());
    }

    public void addProject(String title, int id){
        /**Add project to Managers project HashMap
        *@param title   title of project
        *@param id      id of project 
        */
        projects.put(title, id);
        updateContributor();
    }

    public void removeProject(String title){
        /**Remove every maintainer assigned to project
        *and then remove the project from you project HashMap 
        *@param title   title of project to remove
        */
        int projectID = projects.get(title);
        Project.removeProject(projectID);

        for (String maintainerUname: maintainers){
            Maintainer m = (Maintainer) Contributor.getContributor(maintainerUname);
            m.removeProject(title);
        }

        projects.remove(title);
        updateContributor();
    }
    
    public void addMaintainersToProject(String title, List<String> uNameList){
        /**Assign given project to the given list of maintainers 
        *@param title       title of project to which to assign maintainers
        *@param uNameList   list of maintainer uname which have to be assigned to a project
        */
        int projectID = projects.get(title);
        Project proj = Project.getProject(projectID);

        proj.addMaintainers(uNameList);

        for (String _maintainer : uNameList) {
            Contributor c = Contributor.getContributor(_maintainer);
            c.addProject(proj.getProjectName(), proj.getProjectId());
        }        
    }

    public void removeMaintainersFromProject(String projTitle, List<String> uNameList) {
        /**Remove maintainers from given project 
        *@param title       title of project from which to remove maintainers from
        *@param uNameList   list of maintainer uname which have to be removed from a project
        */
        int projectID = projects.get(projTitle);
        Project p = Project.getProject(projectID);
        for (String _maintainer: uNameList){
            p.removeMaintainer(_maintainer);
            Contributor c = Contributor.getContributor(_maintainer);
            c.removeProject(projTitle);
        }
    }

    public void addMaintainer(String uname){
        /**Add a maintainer to Manager's list of maintainer
        *@param uname   uname of maintainer which is to be added 
        */
        maintainers.add(uname);
        updateContributor();
    }

    public void removeMaintainer(String uname){
        /**Remove a maintainer from Manager's list of maintainer
        *@param uname   uname of maintainer which is to be removed 
        */
        maintainers.remove(uname);
        updateContributor();
    }

    public List<String> getMaintainers(){
        /**@returns list of all maintainers in manager's maintainer list 
        */
        return maintainers;
    }

    public HashMap<String, Integer> getProjects(){
        /**@returns hashmap of all projects in manager's project hashmap 
        */
        return projects;
    }

    @Override
    public String toString(){
        return "Manager: " + uName;
    }
}