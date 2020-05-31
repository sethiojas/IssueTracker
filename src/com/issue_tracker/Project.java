package com.issue_tracker;

import java.util.HashMap;
import java.util.HashSet;
import java.util.ArrayList;
import java.util.Iterator;
import java.io.Serializable;

public class Project implements Serializable{
    private static final long serialVersionUID = 6529685098267757694L;
    private static int bugId = 0;
    final private String projectName;
    private HashMap<Integer, Bug> bugs = new HashMap<>();
    protected HashSet<String> maintainers = new HashSet<>();

    Project(String title){
        projectName = title;
    }

    int createNewBug(String title, String desc){
        bugId += 1;
        bugs.put(bugId, new Bug(title, desc, bugId));
        return bugId;
    }
    
    String getProjectName(){
        return projectName;
    }

    boolean closeBug(int bugId){
        if(bugs.containsKey(bugId)){
            bugs.remove(bugId);
            return true;
        }
        return false;
    }

    ArrayList<Bug> getAllBugs(){
        return new ArrayList<Bug>(bugs.values());
    }

    ArrayList<String> getMaintainers(){
        ArrayList<String> list = new ArrayList<>();
        Iterator<String> iterMaintainers = maintainers.iterator();
        while(iterMaintainers.hasNext()){
            list.add(iterMaintainers.next());
        }
        return list;
    }

    boolean equals(Project proj){
        return projectName.equals(proj.getProjectName());
    }

    boolean addMaintainer(String uName){
        return maintainers.add(uName);
    }

    boolean addMaintainers(ArrayList<String> uNameList){
        return maintainers.addAll(uNameList);
    }

    void removeMaintainer(String uname){
        maintainers.remove(uname);
    }

    @Override
    public String toString(){
        return "Project: " + projectName;
    }
}