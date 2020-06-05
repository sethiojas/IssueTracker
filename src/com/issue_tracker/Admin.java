package com.issue_tracker;

import java.util.ArrayList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.DriverManager;
import java.io.Serializable;

public class Admin implements Serializable {
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
        new Manager(uName);
        Contributor.setOnBench(uName, "false");
    }

    void createNewMaintainer(String uName, String passwd){
        insertNewCredentials(uName, passwd, "maintainer");
        new Maintainer(uName);
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
        Manager manager = (Manager) Contributor.getContributor(uNameManager);
        Maintainer maintainer = (Maintainer) Contributor.getContributor(uNameMaintainer);
        maintainer.setManager(uNameManager);
        manager.addMaintainer(maintainer);

        Contributor.setOnBench(uNameMaintainer, "false");
    }

    void putMaintainerOnBench(String uNameManager, String uNameMaintainer){
        Manager manager = (Manager) Contributor.getContributor(uNameManager);
        Maintainer benchMaintainer = (Maintainer) Contributor.getContributor(uNameMaintainer);

        manager.removeMaintainer(uNameMaintainer);
        benchMaintainer.putOnBench();
    }

    void removeManager(String uName){
        removeCredential(uName);
        Manager rManager = (Manager) Contributor.getContributor(uName);
        for (String maintainerUname: rManager.getMaintainers()){
            putMaintainerOnBench(uName, maintainerUname);
        }

        for (int projID: rManager.getProjects().values()) {
            Project.removeProject(projID);
        }
        Contributor.removeContributor(uName);
    }
    
    void removeMaintainer(String uName, String ManagerUname){
        removeCredential(uName);
        putMaintainerOnBench(ManagerUname, uName);
        Contributor.removeContributor(uName);
    }

    ArrayList<String> getAllManagers(){
        ArrayList<String> listManagers = new ArrayList<>();

        try{
            String getManagers = "SELECT uname FROM credentials WHERE role=\"manager\"";
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
            String getOnBench = "SELECT uname FROM contributors WHERE on_bench=\"true\"";
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
            String deleteContributor = "DELETE FROM credentials WHERE uname=?";
            Connection conn = DriverManager.getConnection(dbPath);
            PreparedStatement pstm = conn.prepareStatement(deleteContributor);
            pstm.setString(1, uName);
            pstm.executeUpdate();
            conn.close();
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }

    void insertNewCredentials(String uName, String passwd, String role){
        try{
            String insertNew = "INSERT INTO credentials VALUES(?, ?, ?)";
            Connection conn = DriverManager.getConnection(dbPath);
            PreparedStatement pstm = conn.prepareStatement(insertNew);
            pstm.setString(1, uName);
            pstm.setString(2, passwd);
            pstm.setString(3, role);
            pstm.executeUpdate();
            conn.close();
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }
}