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

    public void updateMaintainer(){
        String update = "UPDATE contributors SET object=? WHERE uname=?";
        try{
            Connection conn = DriverManager.getConnection(dbPath);
            PreparedStatement pstm = conn.prepareStatement(update);
            pstm.setString(2, uName);
            byte[] arr = ConvertObject.<Maintainer>getByteArrayObject(this);
            if (arr == null) throw new Exception("Unable to Serialize object");
            pstm.setBytes(1, arr);
            pstm.executeUpdate();
            conn.close();
        }catch(SQLException e){
            e.printStackTrace();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public static void removeMaintainer(String uname){
        String delete = "DELETE FROM contributors WHERE uname=?";
        try{
            Connection conn = DriverManager.getConnection(dbPath);
            PreparedStatement pstm = conn.prepareStatement(delete);
            pstm.setString(1, uname);
            pstm.executeUpdate();
            conn.close();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public String toString(){
        return "Maintainer: " + uName;
    }
}