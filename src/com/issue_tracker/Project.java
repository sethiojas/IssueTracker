package com.issue_tracker;

import java.util.HashMap;
import java.util.HashSet;
import java.util.ArrayList;
import java.util.Iterator;
import java.io.Serializable;

public class Project implements Serializable{
    private static final long serialVersionUID = 6529685098267757694L;
    private int bugId = 0;
    final private String projectName;
    private HashMap<Integer, Bug> bugs = new HashMap<>();
    protected HashSet<String> maintainers = new HashSet<>();

    public Project(String title){
        projectName = title;
    }

    public int createNewBug(String title, String desc){
        bugId += 1;
        bugs.put(bugId, new Bug(title, desc, bugId));
        return bugId;
    }
    
    public String getProjectName(){
        return projectName;
    }

    public boolean closeBug(int bugId){
        if(bugs.containsKey(bugId)){
            bugs.remove(bugId);
            return true;
        }
        return false;
    }

    public Bug getBug(int bugId){
        return bugs.get(bugId);
    }
    
    public ArrayList<Bug> getAllBugs(){
        return new ArrayList<Bug>(bugs.values());
    }

    public ArrayList<String> getMaintainers(){
        ArrayList<String> list = new ArrayList<>();
        Iterator<String> iterMaintainers = maintainers.iterator();
        while(iterMaintainers.hasNext()){
            list.add(iterMaintainers.next());
        }
        return list;
    }

    public boolean equals(Project proj){
        return projectName.equals(proj.getProjectName());
    }

    public boolean addMaintainer(String uName){
        return maintainers.add(uName);
    }

    public boolean addMaintainers(ArrayList<String> uNameList){
        return maintainers.addAll(uNameList);
    }

    public void removeMaintainer(String uname){
        maintainers.remove(uname);
    }

    @Override
    public String toString(){
        return "Project: " + projectName;
    }
}