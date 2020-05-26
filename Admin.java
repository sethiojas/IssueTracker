import java.util.ArrayList;
import java.util.HashMap;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.*;

class Admin{
    
    private static HashMap<String, Manager> managers = new HashMap<>();
    private static HashMap<String, Maintainer> onBench = new HashMap<>();
    private final String adminUName;
    private Connection conn;
    private PreparedStatement pstm;
    private String insertNew = "insert into test values(?, ?, ?)";
    private String dbPath = "jdbc:sqlite:test.db";

    Admin(String _name){
        adminUName = _name;
    }

    void createNewManager(String uName){
        Manager newManager = new Manager(uName);
        managers.put(uName, newManager);
    }

    void createNewMaintainer(String uName){
        Maintainer newMaintainer = new Maintainer(uName);
        onBench.put(uName, newMaintainer);
    }

    void promoteToManager(String uName){
        Maintainer thisMaintainer = onBench.remove(uName);
        Manager promoted = new Manager(thisMaintainer.getUName());
        managers.put(uName, promoted);
    }

    void assignManager(String uNameManager, String uNameMaintainer){
        Manager manager = managers.get(uNameManager);
        Maintainer maintainer = onBench.remove(uNameMaintainer);
        manager.addMaintainer(maintainer);
    }

    void putMaintainerOnBench(String uNameManager, String uNameMaintainer){
        Manager manager = managers.get(uNameManager);
        Maintainer benchMaintainer = manager.removeMaintainer(uNameMaintainer);
        onBench.put(benchMaintainer.getUName(), benchMaintainer);
    }
    
    void removeManager(String uName){
        Manager rManager = managers.get(uName);
        for (Maintainer m : rManager.getAllMaintainers()){
            putMaintainerOnBench(uName, m.getUName());
        }
        managers.remove(uName);
    }
    
    void removeMaintainer(String uName){
        onBench.remove(uName);
    }

    ArrayList<Manager> getAllManagers(){
        return new ArrayList<Manager>(managers.values());
    }

    ArrayList<Maintainer> getAllOnBench(){
        return new ArrayList<Maintainer>(onBench.values());
    }

    @Override
    public String toString(){
        return "Admin: " + adminUName;
    }

    void insertNewCredentials(String uname, String passwd, boolean isAdmin){
        conn = DriverManager.getConnection(dbPath);
        pstm = conn.prepareStatement(insertNew);
        pstm.setString(1, uName);
        pstm.setString(2, passwd);
        pstm.setString(3, isAdmin);
        pstm.executeUpdate();
        conn.close();
    }
}