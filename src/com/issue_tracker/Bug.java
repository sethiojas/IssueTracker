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
    private final int bugId;
    private final String dbPath = "jdbc:sqlite:../test.db";
    

    public Bug(String _title, String _desc){
        title = _title;
        description = _desc;
        // bugId = _id;
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

}