package com.issue_tracker;

import java.io.Serializable;

public class Maintainer extends Contributor implements Serializable {
    private String manager;

    // https://stackoverflow.com/questions/10378855/java-io-invalidclassexception-local-class-incompatible
    private static final long serialVersionUID = 6529685098267757692L;

    public Maintainer(String _uName){
        uName = _uName;
        saveContributor();
    }

    public void addProject(String title, int id){
        projects.put(title, id);
        updateContributor();
    }

    public void removeProject(String title){
        projects.remove(title);
        updateContributor();
    }

    public void setManager(String uNameManager){
        manager = uNameManager;
        updateContributor();
    }

    public String getManager(){
        return manager;
    }

    @Override
    public String toString(){
        return "Maintainer: " + uName;
    }
}