package com.issue_tracker;

import java.util.HashMap;
import java.util.ArrayList;
import java.io.Serializable;

public class Maintainer implements Serializable, Saveable{
    
    protected String uName;
    private HashMap<String, Project> projects = new HashMap<>();
    private String manager;

    // https://stackoverflow.com/questions/10378855/java-io-invalidclassexception-local-class-incompatible
    private static final long serialVersionUID = 6529685098267757692L;

    public Maintainer(String _uName){
        uName = _uName;
    }

    @Override
    public String getUName(){
        return uName;
    }

    public ArrayList<Project> getProjects(){
        return new ArrayList<Project>(projects.values());
    }

    public Project getProjectByTitle(String title){
        return projects.get(title);
    }

    public void addProject(Project proj){
        projects.put(proj.getProjectName(), proj);
    }

    public boolean removeProject(Project proj){
        Project isRemoved = projects.remove(proj.getProjectName());
        if(proj.equals(isRemoved)) return true;
        return false;
    }

    public void updateProject(Project proj){
        projects.replace(proj.getProjectName(), proj);
    }

    public void removeAllProjects(){
        for (Project proj: projects.values()){
            proj.removeMaintainer(uName);
        }
        projects.clear();
    }

    public void setManager(String uNameManager){
        manager = uNameManager;
    }

    public String getManager(){
        return manager;
    }

    @Override
    public String toString(){
        return "Maintainer: " + uName;
    }
}