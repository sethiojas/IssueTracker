package com.issue_tracker;

import java.util.HashMap;
import java.io.Serializable;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Maintainer extends Contributor implements Serializable {
    private String manager;

    // https://stackoverflow.com/questions/10378855/java-io-invalidclassexception-local-class-incompatible
    private static final long serialVersionUID = 6529685098267757692L;

    public Maintainer(String _uName){
        uName = _uName;
        saveMaintainer();
    }

    public void addProject(String title, int id){
        projects.put(title, id);
        updateMaintainer();
    }

    public void removeProject(String title){
        projects.remove(title);
        updateMaintainer();
    }

    public void setManager(String uNameManager){
        manager = uNameManager;
        updateMaintainer();
    }

    public String getManager(){
        return manager;
    }

    public void saveMaintainer(){
        String saveMaintainer = "INSERT INTO contributors VALUES(?, ?, ?)";
        try{
            Connection conn = DriverManager.getConnection(dbPath);
            PreparedStatement pstm = conn.prepareStatement(saveMaintainer);
            pstm.setString(1, uName);
            pstm.setString(3, "true");
            byte[] arr = ConvertObject.<Maintainer>getByteArrayObject(this);
            if (arr == null) throw new Exception("Unable to Serialize object");
            pstm.setBytes(2, arr);
            pstm.executeUpdate();
            conn.close();
        }catch(SQLException e){
            e.printStackTrace();
        }catch(Exception e){
            e.printStackTrace();
        }
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