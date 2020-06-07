package com.issue_tracker;

import java.util.HashMap;
import java.io.Serializable;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**Abstract class defining common features of contributors
*Contributor is extended by two classes Maintainer and Manager 
*/

public abstract class Contributor implements Serializable{
    private static final long serialVersionUID = 6529685098267757696L;
    protected String uName;
    protected HashMap<String, Integer> projects = new HashMap<>();
    protected static String dbPath = "jdbc:sqlite:../issueTracker.db";
    
    public String getUname(){
        /**@returns uname of contributor 
        */
        return uName;
    }

    public HashMap<String, Integer> getProjects(){
        /**@returns all project contributor has 
        */
        return projects;
    }

    public int getProjectID(String projectTitle){
        /**@returns project id associated with a project
        *           that a contributor has 
        */
        return projects.get(projectTitle);
    }

    // Add project to a contributor's project list
    public abstract void addProject(String title, int id);
    // remove project from project list of a contributor
    public abstract void removeProject(String title);

    public void saveContributor(){
        /**Save the contributor into database 
        */
        String saveContributor = "INSERT INTO contributors VALUES(?, ?, ?)";
        try{
            Connection conn = DriverManager.getConnection(dbPath);
            PreparedStatement pstm = conn.prepareStatement(saveContributor);
            pstm.setString(1, uName);
            pstm.setString(3, "true");
            byte[] arr = ConvertObject.<Contributor>getByteArrayObject(this);
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

    public void updateContributor(){
        /**Update the contributor's entry in database 
        */
        String update = "UPDATE contributors SET object=? WHERE uname=?";
        try{
            Connection conn = DriverManager.getConnection(dbPath);
            PreparedStatement pstm = conn.prepareStatement(update);
            pstm.setString(2, uName);
            byte[] arr = ConvertObject.<Contributor>getByteArrayObject(this);
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

    public static void removeContributor(String uname){
        /**Delete contributor from database 
        */
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

    public static Contributor getContributor(String uname){
        /**Retrieve a contributor from database 
        */
        String getContributor = "SELECT object FROM contributors WHERE uname=?";
        Contributor m = null;
        try{
            Connection conn = DriverManager.getConnection(dbPath);
            PreparedStatement pstm = conn.prepareStatement(getContributor);
            pstm.setString(1, uname);
            ResultSet res = pstm.executeQuery();
            byte[] arr = res.getBytes("object");
            m = ConvertObject.<Contributor>getJavaObject(arr);
            if (m == null) throw new Exception("Unable to Deserialize object");
            conn.close();
        }catch(SQLException e){
            e.printStackTrace();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            return m;
        }
    }

    public static void setOnBench(String uname, String value){
        /**Change the on-bench value associated with contributor
        */
        String onBench = "UPDATE contributors SET on_bench=? WHERE uname=?";
        try{
            Connection conn = DriverManager.getConnection(dbPath);
            PreparedStatement pstm = conn.prepareStatement(onBench);
            pstm.setString(1, value);
            pstm.setString(2, uname);
            pstm.executeUpdate();
            conn.close();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
}