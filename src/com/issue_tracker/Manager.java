package com.issue_tracker;

import java.util.ArrayList;
import java.util.HashMap;
import java.io.Serializable;

public class Manager extends Contributor implements Serializable {
    
    private ArrayList<String> maintainers = new ArrayList<>();

    // https://stackoverflow.com/questions/10378855/java-io-invalidclassexception-local-class-incompatible
    private static final long serialVersionUID = 6529685098267757691L;

    public Manager(String _uName){
        uName = _uName;
        saveContributor();
    }

    public void createProject(String title){
        Project proj = new Project(title);
        addProject(proj.getProjectName(), proj.getProjectId());
    }

    public void addProject(String title, int id){
        projects.put(title, id);
        updateContributor();
    }

    public void removeProject(String title){
        int projectID = projects.get(title);
        Project.removeProject(projectID);

        for (String maintainerUname: maintainers){
            Maintainer m = (Maintainer) Contributor.getContributor(maintainerUname);
            m.removeProject(title);
        }

        projects.remove(title);
        updateContributor();
    }
    
    public void addMaintainersToProject(String title, ArrayList<String> uNameList){
        int projectID = projects.get(title);
        Project proj = Project.getProject(projectID);

        proj.addMaintainers(uNameList);

        for (String _maintainer : uNameList) {
            Contributor c = Contributor.getContributor(_maintainer);
            c.addProject(proj.getProjectName(), proj.getProjectId());
        }        
    }

    public void removeMaintainersFromProject(String projTitle, ArrayList<String> uNameList) {
        int projectID = projects.get(projTitle);
        Project p = Project.getProject(projectID);
        for (String _maintainer: uNameList){
            p.removeMaintainer(_maintainer);
            Contributor c = Contributor.getContributor(_maintainer);
            c.removeProject(projTitle);
        }
    }

    public void addMaintainer(String uname){
        maintainers.add(uname);
        updateContributor();
    }

    public void removeMaintainer(String uname){
        maintainers.remove(uname);
        updateContributor();
    }

    public ArrayList<String> getMaintainers(){
        return maintainers;
    }

    public HashMap<String, Integer> getProjects(){
        return projects;
    }

    @Override
    public String toString(){
        return "Manager: " + uName;
    }
}