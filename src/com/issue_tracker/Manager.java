package com.issue_tracker;

import java.util.ArrayList;
import java.util.HashMap;
import java.io.Serializable;

class Manager extends Maintainer implements Serializable, Saveable{
    
    private HashMap<String, Maintainer> maintainer = new HashMap<>();
    private HashMap<String, Project> projects = new HashMap<>();

    // https://stackoverflow.com/questions/10378855/java-io-invalidclassexception-local-class-incompatible
    private static final long serialVersionUID = 6529685098267757691L;

    Manager(String _uName){
        super(_uName);
    }

    void createProject(String title){
        projects.put(title, new Project(title));
    }

    void addMaintainersToProject(String title, ArrayList<String> uNameList){
        Project proj = projects.get(title);
        proj.addMaintainers(uNameList);
        proj.addMaintainer(uName);
        
        for (String item : uNameList){
            maintainer.get(item).addProject(proj);
        }
    }

    Project getProjectByTitle(String title){
        return projects.get(title);
    }

    Maintainer removeMaintainer(String uName){
        Maintainer removedMaintainer = maintainer.get(uName);
        removedMaintainer.removeAllProjects();
        removedMaintainer.setManager(""); 
        maintainer.remove(uName);
        return removedMaintainer;
    }

    void removeMaintainerFromProject(String uName, String projTitle){
        Maintainer removedMaintainer = maintainer.get(uName);
        Project thisProj = removedMaintainer.getProjectByTitle(projTitle);
        thisProj.removeMaintainer(uName);
        removedMaintainer.removeProject(thisProj);
    }

    void addMaintainer(Maintainer addMe){
        maintainer.put(addMe.getUName(), addMe);
    }

    ArrayList<Maintainer> getAllMaintainers(){
        return new ArrayList<Maintainer>(maintainer.values());
    }

    ArrayList<Project> getAllProjects(){
        return new ArrayList<Project>(projects.values());
    }

    @Override
    public String toString(){
        return "Manager: " + uName;
    }
}