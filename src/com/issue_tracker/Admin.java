package com.issue_tracker;

import java.util.List;
import java.util.LinkedList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.DriverManager;
import java.io.Serializable;
import java.lang.StringBuilder;

/**Has API for actions that can be
*performed via admin 
*/

public class Admin implements Serializable {
    // all static and transient variables are null after deserialization
    // https://www.baeldung.com/java-jdbc
    private String dbPath = "jdbc:sqlite:../issueTracker.db";

    // https://stackoverflow.com/questions/10378855/java-io-invalidclassexception-local-class-incompatible
    private static final long serialVersionUID = 6529685098267757690L;

    public void createNewManager(String uName, String passwd){
        /**Create a new manager account
        *@param uName   uname of new manager account
        *@param passwd  password of new manager account 
        */
        insertNewCredentials(uName, passwd, "manager");
        new Manager(uName);
        Contributor.setOnBench(uName, "false");
    }

    public void createNewMaintainer(String uName, String passwd){
        /**Create new Maintainer Account
        *@param uName   Uname of new Maintainer account
        *@param passwd  Password of new Maintainer account 
        */
        insertNewCredentials(uName, passwd, "maintainer");
        new Maintainer(uName);
    }
    
    public void assignManager(String uNameManager, String uNameMaintainer){
        /**Assign a manager to an on-bench maintainer
        *@param uNameManager    Uname of manager to which maintainer is
        *                       to be assigned
        *@param uNameMaintainer Uname of on-bench maintainer 
        */
        Manager manager = (Manager) Contributor.getContributor(uNameManager);
        Maintainer maintainer = (Maintainer) Contributor.getContributor(uNameMaintainer);
        maintainer.setManager(uNameManager);
        manager.addMaintainer(uNameMaintainer);

        Contributor.setOnBench(uNameMaintainer, "false");
    }

    public void putMaintainerOnBench(String uNameManager, String uNameMaintainer){
        /**Put assigned maintainer on bench
        *@param uNameManager    uname of Manager to which maintainer is assigned
        *@param uNameMaintainer uname ofMaintainer which is to be put on bench 
        */
        Manager manager = (Manager) Contributor.getContributor(uNameManager);
        Maintainer benchMaintainer = (Maintainer) Contributor.getContributor(uNameMaintainer);

        manager.removeMaintainer(uNameMaintainer);
        benchMaintainer.putOnBench();
    }

    public void removeManager(String uName){
        /**Removed Manager account
        *@param uname   Uname of manager whose account has to be removed 
        */
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
    
    public void removeMaintainer(String uName, String ManagerUname){
        /**Remove accout of maintainer
        *@param uName        uname of maintainer whose account has to be removed
        *@param ManagerUname uname of manager to which maintainer is assigned
        *                    NOTE: it can be null in case of on-bench maintainer 
        */
        removeCredential(uName);
        if (ManagerUname != null) putMaintainerOnBench(ManagerUname, uName);
        Contributor.removeContributor(uName);
    }

    public void removeMaintainer(String uname){
        /**Remove account of an on-bench maintainer, therefore provide
        *pass null to ManagerUname 
        *@param uname   Uname of on-bench maintainer whose account
        *               has to be removed
        */
        this.removeMaintainer(uname, null);
    }

    public List<String> getAllManagers(){
        /**Get all managers with registered accounts
        *@returns   List of uname of all managers 
        */
        List<String> listManagers = new LinkedList<>();

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

    public List<String> getAllOnBench(){
        /**Get all on-bench maintainers with registered accounts
        *@returns List of uname of all on-bench maintainers 
        */
        List<String> listOnBench = new LinkedList<>();

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

    public List<String> getAllAssigned(){
        /**Get unames of all maintainers who are assigned to
        *some manager
        *@returns List of uname of assigned maintainers 
        */
        List<String> listAssigned = new LinkedList<>();

        try{
            String getAssigned = new StringBuilder()
                                .append("SELECT con.uname ")
                                .append("FROM contributors con ")
                                .append("INNER JOIN credentials cred ON ")
                                .append("con.uname=cred.uname ")
                                .append("WHERE con.on_bench=\"false\" AND ")
                                .append("cred.role=\"maintainer\"")
                                .toString();
            Connection conn = DriverManager.getConnection(dbPath);
            Statement stm = conn.createStatement();
            ResultSet res = stm.executeQuery(getAssigned);
            while(res.next()){
                listAssigned.add(res.getString("uname"));
            }
            conn.close();
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        finally{
            return listAssigned;
        }
    }

    @Override
    public String toString(){
        return "Admin";
    }

    void removeCredential(String uName){
        /**Remove credentials of an account from database
        *@param uName   Uname of Contributor whose credentials
        *               have to be deleted 
        */
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
        /**Insert new credentials into database
        *@param uName      uName of new account
        *@param passwd     Password associated with new account
        *@param role       Role associated with new account 
        */
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