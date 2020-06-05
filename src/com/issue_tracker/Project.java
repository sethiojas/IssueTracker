package com.issue_tracker;

import java.util.HashMap;
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
    private int projectId;
    final private String projectName;
    private int bugId = 0;
    protected ArrayList<String> maintainers = new ArrayList<>();

    public Project(String projectName){
        this.projectName = projectName;
        insertProject();
    }

    public void createNewBug(String title, String desc){
        new Bug(title, desc, projectId);
    }
    
    public String getProjectName(){
        return projectName;
    }

    public void closeBug(int bugId){
       Bug.closeBug(bugId);
    }

    public HashMap<String, String> getBug(int bugId){
        // return bugs.get(bugId);
        HashMap<String, String> bug = new HashMap<>();
        String getBug = "SELECT bug_title, bug_desc FROM bugs WHERE bug_id=? AND project_id=?";
        try{
            Connection conn = DriverManager.getConnection(dbPath);
            PreparedStatement pstm = conn.prepareStatement(getBug);
            pstm.setInt(1, bugId);
            pstm.setInt(2, projectId);
            ResultSet res = pstm.executeQuery();
            bug.put("title", res.getString("bug_title"));
            bug.put("desc", res.getString("bug_desc"));
            conn.close();
        }catch(SQLException e){
            e.printStackTrace();
        }finally{
            return bug;
        }
    }

    public int getProjectId(){
        return projectId;
    }
    
    public ArrayList<HashMap<String, String>> getAllBugs(){
        ArrayList<HashMap<String, String>> bugs = new ArrayList<>();
        String allBugsQuery = "SELECT bug_id, bug_title, bug_desc FROM bugs WHERE project_id=?";
        try{
            Connection conn = DriverManager.getConnection(dbPath);
            PreparedStatement pstm = conn.prepareStatement(allBugsQuery);
            pstm.setInt(1, projectId);
            ResultSet res = pstm.executeQuery();
            while(res.next()){
                HashMap<String, String> bug = new HashMap<>();
                bug.put("id", Integer.toString(res.getInt("bug_id")));
                bug.put("title", res.getString("bug_title"));
                bug.put("desc", res.getString("bug_desc"));

                bugs.add(bug);
            }
            conn.close();
        }catch(SQLException e){
            e.printStackTrace();
        }finally{
            return bugs;
        }
    }

    public ArrayList<String> getMaintainers(){
        return maintainers;
    }

    public boolean equals(Project proj){
        return projectName.equals(proj.getProjectId());
    }

    public void addMaintainers(ArrayList<String> uNameList){
        maintainers.addAll(uNameList);
        updateProject(this);
    }

    public void removeMaintainer(String uname){
        maintainers.remove(uname);
        updateProject(this);
    }

    @Override
    public String toString(){
        return "Project: " + projectName;
    }

    public void insertProject(){
        String insert = "INSERT INTO projects(project_name, project_object) VALUES(?, ?)";
        try{
            Connection conn = DriverManager.getConnection(dbPath);
            PreparedStatement pstm = conn.prepareStatement(insert);
            pstm.setString(1, projectName);
            byte[] arr = ConvertObject.<Project>getByteArrayObject(this);
            if (arr == null) throw new Exception("Byte array is null");
            pstm.setBytes(2, arr);
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
        String update = "UPDATE projects SET project_object=? WHERE project_id=? AND project_name=?";
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
        String getProject = "SELECT project_object FROM projects WHERE project_id=?";
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

    public static void removeProject(int projectID){
        Bug.removeAllBugsOfProject(projectID);
        String removeProject = "delete from projects where project_id=?";
        try{
            Connection conn = DriverManager.getConnection(dbPath);
            PreparedStatement pstm = conn.prepareStatement(removeProject);
            pstm.setInt(1, projectID);
            pstm.executeUpdate();
            conn.close();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
}