package com.issue_tracker;

import java.io.Serializable;

public class Maintainer extends Contributor implements Serializable {
    private String manager;

    // https://stackoverflow.com/questions/10378855/java-io-invalidclassexception-local-class-incompatible
    private static final long serialVersionUID = 6529685098267757692L;

    public Maintainer(String _uName){
        /**Create an object of Maintainer */
        uName = _uName;
        saveContributor();
    }

    public void addProject(String title, int id){
        /**Add project to Maintainers project HashMap
        *@param title   project title
        *@param id      unique id of project 
        */
        projects.put(title, id);
        updateContributor();
    }

    public void removeProject(String title){
        /**Remove project from maintainers project HashMap
        *@param title   title of project to remove 
        */
        projects.remove(title);
        updateContributor();
    }

    public void setManager(String uNameManager){
        /**Set manager of maintainer
        *@param uNameManager    uname of manager 
        */
        manager = uNameManager;
        updateContributor();
    }

    public String getManager(){
        /**@returns uname of manager to which maintainer is assigned
        *           its value is an empty string if maintainer is on bench 
        */
        return manager;
    }

    public void putOnBench(){
        /**Puts maintainer on bench 
        */
        for (int id: projects.values()){
            Project p = Project.getProject(id);
            p.removeMaintainer(uName);
        }
        projects.clear();
        setManager("");
        updateContributor();
        setOnBench(uName, "true");
    }

    @Override
    public String toString(){
        return "Maintainer: " + uName;
    }
}