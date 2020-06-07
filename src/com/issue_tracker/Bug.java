package com.issue_tracker;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.DriverManager;

public class Bug implements Serializable{
    private static final long serialVersionUID = 6529685098267757695L;
    private final String title;
    private final String description;
    private final int projectId;
    private static final String dbPath = "jdbc:sqlite:../issueTracker.db";
    

    public Bug(String _title, String _desc, int _projectId){
        /**Constructor to create a new bug
        *@param _title      title of new bug
        *@param _desc       description od new bug
        *@param _projectID  ID of project to which the bug belongs
        */
        title = _title;
        description = _desc;
        projectId = _projectId;
        saveBug();
    }

    public String getTitle(){
        /**@returns Title of bug 
        */
        return title;
    }

    public String getDescription(){
        /**@returns description of bug
        */
        return description;
    }

    @Override
    public String toString(){
        return "(Bug: " + title + ")";
    }

    public void saveBug(){
        /**Save the bug into database 
        */
        String insert = "INSERT INTO bugs(bug_title, bug_desc, project_id) VALUES(?, ?, ?)";
        try{
            Connection conn = DriverManager.getConnection(dbPath);
            PreparedStatement pstm = conn.prepareStatement(insert);
            pstm.setString(1, title);
            pstm.setString(2, description);
            pstm.setInt(3, projectId);
            pstm.executeUpdate();
            conn.close();
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }

    public static void closeBug(int bugId){
        /**Delete the bug from database
        */
        String close = "delete from bugs where bug_id=?";
        try{
            Connection conn = DriverManager.getConnection(dbPath);
            PreparedStatement pstm = conn.prepareStatement(close);
            pstm.setInt(1, bugId);
            pstm.executeUpdate();
            conn.close();
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }

    public static void removeAllBugsOfProject(int projectId){
        /**Removes all bug associated with a project from
        *database
        *@param projectId   ID of project whose bugs have to be removed
        */
        String removeAll = "delete from bugs where project_id=?";
        try{
            Connection conn = DriverManager.getConnection(dbPath);
            PreparedStatement pstm = conn.prepareStatement(removeAll);
            pstm.setInt(1, projectId);
            pstm.executeUpdate();
            conn.close();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
}