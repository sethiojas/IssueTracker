package com.issue_tracker;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;
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
    protected List<String> maintainers = new ArrayList<>();

    public Project(String projectName){
        /**Create a Project object 
        */
        this.projectName = projectName;
        insertProject();
    }

    public void createNewBug(String title, String desc){
        /**Create new bug
        *@param title   title of bug
        *@param desc    description of bug 
        */
        new Bug(title, desc, projectId);
    }
    
    public String getProjectName(){
        /**@returns Name/title of project 
        */
        return projectName;
    }

    public void closeBug(int bugId){
        /**Close a bug
        *@param bugId id of the bug which is to be close 
        */
       Bug.closeBug(bugId);
    }

    public HashMap<String, String> getBug(int bugId){
        /**@returns bug having the provided bug id associated with given project 
        */
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
        /**@returns project id of project 
        */
        return projectId;
    }
    
    public ArrayList<HashMap<String, String>> getAllBugs(){
        /**@returns all bugs associated with a project 
        */
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

    public List<String> getMaintainers(){
        /**@returns List of Contributors which have been assigned to this 
        *project
        */
        return maintainers;
    }

    public boolean equals(Project proj){
        /**Compares if two project objects are equal or not
        *@param proj    project object to compare with
        *@returns       boolean based on equality of projects
        */
        return projectId.equals(proj.getProjectId());
    }

    public void addMaintainers(List<String> uNameList){
        /**Add all Contributors in given uname list to list of maintainers
        *of project
        *@param uNameList list of contributors uname*/
        maintainers.addAll(uNameList);
        updateProject(this);
    }

    public void addMaintainer(String uname){
        /**Added the given contributor uname to list of maintainers
        *of project
        @param uname    uname of contributor
        */
        maintainers.add(uname);
        updateProject(this);
    }

    public void removeMaintainer(String uname){
        /**Remove a contributor from list of maintainers
        *@param uname   uname of contributor to remove
        */
        maintainers.remove(uname);
        updateProject(this);
    }

    @Override
    public String toString(){
        return "Project: " + projectName + " #" + projectId;
    }

    public void insertProject(){
        /**Save project object into database 
        */
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
            ResultSet res = stm.executeQuery("SELECT LAST_INSERT_ROWID()");
            projectId = res.getInt(1);
            conn.close();
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        catch(Exception e){
            e.printStackTrace();
        }
        finally{
            updateProject(this);
        }
    }

    public static void updateProject(Project project){
        /**Update project object stored in database 
        */
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
        /**Retrieve project object from database
        *@returns Project object 
        */
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
        /**Delete project object from database 
        */
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
