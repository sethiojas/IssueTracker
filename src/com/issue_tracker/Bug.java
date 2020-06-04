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
    private int bugId;
    private final int projectId;
    private final String dbPath = "jdbc:sqlite:../test.db";
    

    public Bug(String _title, String _desc, int _projectId){
        title = _title;
        description = _desc;
        projectId = _projectId;
        saveBug();
    }

    public String getTitle(){
        return title;
    }

    public String getDescription(){
        return description;
    }

    public int getId(){
        return bugId;
    }

    @Override
    public String toString(){
        return "(" + bugId + "," + title + ")";
    }

    public void saveBug(){
        String insert = "INSERT INTO bugs(bug_title, bug_desc, project_id) VALUES(?, ?, ?)";
        try{
            Connection conn = DriverManager.getConenction(dbPath);
            PreparedStatement pstm = conn.prepareStatement(insert);
            pstm.setString(1, title);
            pstm.setString(2, description);
            pstm.setInt(3, projectId);
            pstm.executeUpdate();
            Statement stm = conn.createStatement();
            ResultSet res = stm.executeQuery("SELECT LAST_INSERT_ROWID() FROM bugs");
            bugId = res.getInt(1);
            conn.close();
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }

    public static void closeBug(int bugId){
        String close = "delete from bugs where bug_id=?";
        try{
            Connection conn = DriverManager.getConnection();
            PreparedStatement pstm = conn.prepareStatement();
            pstm.setInt(1, bugId);
            pstm.executeUpdate();
            conn.close();
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }
}