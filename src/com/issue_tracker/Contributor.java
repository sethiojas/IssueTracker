package com.issue_tracker;

import java.util.HashMap;
import java.io.Serializable;

abstract class Contributor implements Serializable{
    private static final long serialVersionUID = 6529685098267757696L;
    protected String uName;
    protected HashMap<String, Integer> projects = new HashMap<>();
    protected static String dbPath = "jdbc:sqlite:../issueTracker.db";
    
    public String getUname(){
        return uName;
    }

    public HashMap<String, Integer> getProjects(){
        return projects;
    }

    public int getProjectID(String projectTitle){
        return projects.get(projectTitle);
    }

    public abstract void addProject(String title, int id);
    
    public abstract void removeProject(String title);
}