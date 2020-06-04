package com.issue_tracker;

import java.util.HashMap;
import java.util.HashSet;
import java.util.ArrayList;
import java.util.Iterator;
import java.io.Serializable;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Project implements Serializable{
    private static final long serialVersionUID = 6529685098267757694L;
    private static final String dbPath = "jdbc:sqlite:../issueTracker.db";
    private static int projectId;
    final private String projectName;
    private int bugId = 0;
    private HashMap<Integer, Bug> bugs = new HashMap<>();
    protected HashSet<String> maintainers = new HashSet<>();

    public Project(String projectName){
        this.projectName = projectName;
        insertProject(this);
    }

    public int createNewBug(String title, String desc){
        bugId += 1;
        bugs.put(bugId, new Bug(title, desc, bugId));
        updateProject(this);
        return bugId;
    }
    
    public String getProjectName(){
        return projectName;
    }

    public boolean closeBug(int bugId){
        if(bugs.containsKey(bugId)){
            bugs.remove(bugId);
            updateProject(this);
            return true;
        }
        return false;
    }

    public Bug getBug(int bugId){
        return bugs.get(bugId);
    }

    public int getProjectId(){
        return projectId;
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

    public static void insertProject(Project project){
        String insert = "insert into projects values(?, ?, ?)";
        try{
            Connection conn = DriverManager.getConnection(dbPath);
            PreparedStatement pstm = conn.prepareStatement(insert);
            pstm.setInt(1, project.getProjectId());
            pstm.setString(2, project.getProjectName());
            byte[] arr = ConvertObject.<Project>getByteArrayObject(project);
            if (arr == null) throw new Exception("Byte array is null");
            pstm.setBytes(3, arr);
            pstm.executeUpdate();
            Statement stm = conn.createStatement();
            ResultSet res = stm.executeQuery("SELECT LAST_INSERT_ROWID() FROM projects");
            projectId = res.getInt(1);
            conn.close();
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    public static void updateProject(Project project){
        String update = "update projects set project_object=? where project_id=? and project_name=?";
        try{
            Connection conn = DriverManager.getConnection(dbPath);
            PreparedStatement pstm = conn.prepareStatement(update);
            byte[] arr = ConvertObject.<Project>getByteArrayObject(project);
            if (arr == null) throw new Exception("Byte array is null");
            pstm.setBytes(1, arr);
            pstm.setInt(2, project.getProjectId());
            pstm.setString(3, project.getProjectName());
            pstm.executeUpdate();
            conn.close();
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    public static Project getProject(int id){
        String getProject = "select project_object from projects where project_id=?";
        Project p = null;
        try{
            Connection conn = DriverManager.getConnection(dbPath);
            PreparedStatement pstm = conn.prepareStatement(getProject);
            pstm.setInt(1, id);
            ResultSet res = pstm.executeQuery();
            byte[] arr = res.getBytes("project_object");
            p = ConvertObject.<Project>getJavaObject(arr);
            if (p == null) throw new Exception("Error while deserializing object");
            conn.close();
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        catch(Exception e){
            e.printStackTrace();
        }
        finally{
            return p;
        }
    }
}