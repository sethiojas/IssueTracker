import java.util.ArrayList;
import java.util.HashMap;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.DriverManager;
import java.io.Serializable;

class Admin implements Serializable, Saveable{
    // all static and transient variables are null after deserialization

    private static HashMap<String, Manager> managers = new HashMap<>();
    private static HashMap<String, Maintainer> onBench = new HashMap<>();
    private final String adminUName;

    // https://www.baeldung.com/java-jdbc
    private String insertNew = "insert into test values(?, ?, ?)";
    private String deleteContributor = "delete from test where uname=?";
    private String dbPath = "jdbc:sqlite:test.db";

    // https://stackoverflow.com/questions/10378855/java-io-invalidclassexception-local-class-incompatible
    private static final long serialVersionUID = 6529685098267757690L;

    Admin(String _name, String passwd){
        adminUName = _name;
        insertNewCredentials(_name, passwd, "true");
    }

    void createNewManager(String uName, String passwd){
        insertNewCredentials(uName, passwd, "false");
        Manager newManager = new Manager(uName);
        managers.put(uName, newManager);
    }

    void createNewMaintainer(String uName, String passwd){
        insertNewCredentials(uName, passwd, "false");
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
        maintainer.setManager(uNameManager);
        manager.addMaintainer(maintainer);
    }

    void putMaintainerOnBench(String uNameManager, String uNameMaintainer){
        Manager manager = managers.get(uNameManager);
        Maintainer benchMaintainer = manager.removeMaintainer(uNameMaintainer);
        onBench.put(benchMaintainer.getUName(), benchMaintainer);
    }
    
    void removeManager(String uName){
        removeCredential(uName);
        Manager rManager = managers.get(uName);
        for (Maintainer m : rManager.getAllMaintainers()){
            putMaintainerOnBench(uName, m.getUName());
        }
        managers.remove(uName);
    }
    
    void removeMaintainer(String uName){
        removeCredential(uName);
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

    void removeCredential(String uName){
        try{
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

    void insertNewCredentials(String uName, String passwd, String isAdmin){
        try{
            Connection conn = DriverManager.getConnection(dbPath);
            PreparedStatement pstm = conn.prepareStatement(insertNew);
            pstm.setString(1, uName);
            pstm.setString(2, passwd);
            pstm.setString(3, isAdmin);
            pstm.executeUpdate();
            conn.close();
        }
        catch (SQLException e){
            System.out.println("Error connecting to Database");
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void saveThisObject(){
        try{
            String saveObject = "insert into test_two values(?, ?)";
            Connection conn = DriverManager.getConnection(dbPath);
            PreparedStatement pstm = conn.prepareStatement(saveObject);
            pstm.setString(1, adminUName);
            
            ConvertObject<Admin> obj = new ConvertObject<>();
            byte[] arr = obj.getByteArrayObject(this);
            if(arr == null) throw new Exception("Cannot save object to database");
            
            pstm.setBytes(2, arr);
            pstm.executeUpdate();
            conn.close();
        }
        catch(SQLException e){
            System.out.println("Database Error");
            e.printStackTrace();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public Admin retrieveThisObject(){
        Admin a = null;
        try{
            String retrival = "select obj from test_two where uname=?";
            Connection conn = DriverManager.getConnection(dbPath);
            PreparedStatement pstm = conn.prepareStatement(retrival);
            pstm.setString(1, adminUName);
            ResultSet s = pstm.executeQuery();
            
            byte[] arr = s.getBytes("obj");
            ConvertObject<Admin> con = new ConvertObject<>();
            a = con.getJavaObject(arr);

        }
        catch(SQLException e){
            e.printStackTrace();
        }
        catch(Exception e){
            e.printStackTrace();
        }
        finally{
            return a;
        }
    }
}