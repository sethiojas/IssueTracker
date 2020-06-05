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

    public Project getProjectByTitle(String title){
        return projects.get(title);
    }

    public void createProject(String title){
        Project proj = new Project(title);
        projects.put(title, proj);
        proj.addMaintainer(uName);
    }

    public void updateProject(Project project) {
        projects.replace(project.getProjectName(), project);
    }

    public void addMaintainersToProject(String title, ArrayList<String> uNameList){
        Project proj = projects.get(title);
        proj.addMaintainers(uNameList);
        for (String item : uNameList){
            maintainer.get(item).addProject(proj);
        }
    }

    public Maintainer removeMaintainer(String uName){
        Maintainer removedMaintainer = maintainer.get(uName);
        removedMaintainer.removeAllProjects();
        removedMaintainer.setManager(""); 
        maintainer.remove(uName);
        return removedMaintainer;
    }

    public void removeMaintainerFromProject(String uName, String projTitle){
        Maintainer removedMaintainer = maintainer.get(uName);
        Project thisProj = removedMaintainer.getProjectByTitle(projTitle);
        thisProj.removeMaintainer(uName);
        removedMaintainer.removeProject(thisProj);
    }

    public void addMaintainer(Maintainer addMe){
        maintainer.put(addMe.getUName(), addMe);
    }

    public ArrayList<Maintainer> getAllMaintainers(){
        return new ArrayList<Maintainer>(maintainer.values());
    }

    public ArrayList<Project> getAllProjects(){
        return new ArrayList<Project>(projects.values());
    }

    public Maintainer getMaintainer(String uname) {
        return maintainer.get(uname);
    }

    @Override
    public String toString(){
        return "Manager: " + uName;
    }
}