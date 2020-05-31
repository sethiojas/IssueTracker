package com.issue_tracker;

import java.util.ArrayList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.DriverManager;
import java.io.Serializable;

class Admin implements Serializable, Saveable{
    // all static and transient variables are null after deserialization

    private final String adminUName;

    // https://www.baeldung.com/java-jdbc
    private String dbPath = "jdbc:sqlite:test.db";

    // https://stackoverflow.com/questions/10378855/java-io-invalidclassexception-local-class-incompatible
    private static final long serialVersionUID = 6529685098267757690L;

    Admin(String _name, String passwd){
        adminUName = _name;
        insertNewCredentials(_name, passwd, "admin");
        SaveOrRetrieve<Admin> saver = new SaveOrRetrieve<>();
        saver.saveThisObject(this, "false");
    }

    void createNewManager(String uName, String passwd){
        insertNewCredentials(uName, passwd, "manager");
        Manager newManager = new Manager(uName);
        SaveOrRetrieve<Manager> saver = new SaveOrRetrieve<>();
        saver.saveThisObject(newManager, "false");
    }

    void createNewMaintainer(String uName, String passwd){
        insertNewCredentials(uName, passwd, "maintainer");
        Maintainer newMaintainer = new Maintainer(uName);
        SaveOrRetrieve<Maintainer> saver = new SaveOrRetrieve<>();
        saver.saveThisObject(newMaintainer, "true");
    }

    void promoteToManager(String uName){
        SaveOrRetrieve<Maintainer> srMaintainer = new SaveOrRetrieve<>();
        SaveOrRetrieve<Manager> srManager = new SaveOrRetrieve<>();
        Maintainer thisMaintainer = srMaintainer.retrieveThisObject(uName);
        updateRole(uName, "manager");
        Manager promoted = new Manager(thisMaintainer.getUName());
        srManager.updateThisObject(promoted, "false");
    }
    
    void assignManager(String uNameManager, String uNameMaintainer){
        SaveOrRetrieve<Manager> srManager = new SaveOrRetrieve<>();
        SaveOrRetrieve<Maintainer> srMaintainer = new SaveOrRetrieve<>();

        Manager manager = srManager.retrieveThisObject(uNameManager);
        Maintainer maintainer = srMaintainer.retrieveThisObject(uNameMaintainer);
        maintainer.setManager(uNameManager);
        manager.addMaintainer(maintainer);

        srManager.updateThisObject(manager);
        srMaintainer.updateThisObject(maintainer, "false");
    }

    void putMaintainerOnBench(String uNameManager, String uNameMaintainer){
        SaveOrRetrieve<Manager> srManager = new SaveOrRetrieve<>();
        SaveOrRetrieve<Maintainer> srMaintainer = new SaveOrRetrieve<>();

        Manager manager = srManager.retrieveThisObject(uNameManager);
        Maintainer benchMaintainer = manager.removeMaintainer(uNameMaintainer);

        srMaintainer.updateThisObject(benchMaintainer, "true");
    }

    void removeManager(String uName){
        removeCredential(uName);
        SaveOrRetrieve<Manager> srManager = new SaveOrRetrieve<>();
        Manager rManager = srManager.retrieveThisObject(uName);
        for (Maintainer m : rManager.getAllMaintainers()){
            putMaintainerOnBench(uName, m.getUName());
        }
        srManager.deleteThisObject(uName);
    }
    
    void removeMaintainer(String uName){
        removeCredential(uName);
        SaveOrRetrieve<Maintainer> srMaintainer = new SaveOrRetrieve<>();
        srMaintainer.deleteThisObject(uName);
    }

    ArrayList<String> getAllManagers(){
        ArrayList<String> listManagers = new ArrayList<>();

        try{
            String getManagers = "select uname from test where role=\"manager\"";
            Connection conn = DriverManager.getConnection(dbPath);
            Statement stm = conn.createStatement();
            ResultSet res = stm.executeQuery(getManagers);
            while(res.next()){
                listManagers.add(res.getString("uname"));
            }
            conn.close();
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        finally{
            return listManagers;
        }
    }

    ArrayList<String> getAllOnBench(){
        ArrayList<String> listOnBench = new ArrayList<>();

        try{
            String getOnBench = "select uname from test_two where on_bench=\"true\"";
            Connection conn = DriverManager.getConnection(dbPath);
            Statement stm = conn.createStatement();
            ResultSet res = stm.executeQuery(getOnBench);
            while(res.next()){
                listOnBench.add(res.getString("uname"));
            }
            conn.close();
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        finally{
            return listOnBench;
        }
    }

    @Override
    public String getUName(){
        return adminUName;
    }

    @Override
    public String toString(){
        return "Admin: " + adminUName;
    }

    void removeCredential(String uName){
        try{
            String deleteContributor = "delete from test where uname=?";
            Connection conn = DriverManager.getConnection(dbPath);
            PreparedStatement pstm = conn.prepareStatement(deleteContributor);
            pstm.setString(1, uName);
            pstm.executeUpdate();
            conn.close();
        }
        catch(SQLException e){
            System.out.println("Error connecting to Database");
            System.out.println(e.getMessage());
        }
    }

    void insertNewCredentials(String uName, String passwd, String role){
        try{
            String insertNew = "insert into test values(?, ?, ?)";
            Connection conn = DriverManager.getConnection(dbPath);
            PreparedStatement pstm = conn.prepareStatement(insertNew);
            pstm.setString(1, uName);
            pstm.setString(2, passwd);
            pstm.setString(3, role);
            pstm.executeUpdate();
            conn.close();
        }
        catch (SQLException e){
            System.out.println("Error connecting to Database");
            System.out.println(e.getMessage());
        }
    }

    void updateRole(String uName, String role){
        try{
            String updateRoleStr = "update test set role=? where uname=?";
            Connection conn = DriverManager.getConnection(dbPath);
            PreparedStatement pstm = conn.prepareStatement(updateRoleStr);
            pstm.setString(1, role);
            pstm.setString(2, uName);
            pstm.executeUpdate();
            conn.close();
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }
}