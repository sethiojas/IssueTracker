import java.util.ArrayList;
import java.util.HashMap;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.DriverManager;

class Manager extends Maintainer implements Serializable, Saveable{
    
    private HashMap<String, Maintainer> maintainer = new HashMap<>();
    private HashMap<String, ManageProject> projects = new HashMap<>();

    // https://stackoverflow.com/questions/10378855/java-io-invalidclassexception-local-class-incompatible
    private static final long serialVersionUID = 6529685098267757691L;

    private String saveObject = "insert into test_two values(?, ?)";
    private String dbPath = "jdbc:sqlite:test.db";

    Manager(String _uName){
        super(_uName);
    }

    void createProject(String title){
        projects.put(title, new ManageProject(title));
    }

    void addMaintainersToProject(String title, ArrayList<String> uNameList){
        ManageProject proj = projects.get(title);
        proj.addMaintainers(uNameList);
        proj.addMaintainer(uName);
        
        for (String item : uNameList){
            maintainer.get(item).addProject(proj);
        }
    }

    ManageProject getProjectByTitle(String title){
        return projects.get(title);
    }

    Maintainer removeMaintainer(String uName){
        Maintainer removedMaintainer = maintainer.get(uName);
        removedMaintainer.removeAllProjects();
        removedMaintainer.setManager(""); 
        maintainer.remove(uName);
        return removedMaintainer;
    }

    void removeMaintainerFromProject(String uName, String projTitle){
        Maintainer removedMaintainer = maintainer.get(uName);
        removedMaintainer.removeProject(removedMaintainer.getProjectByTitle(projTitle));
    }

    void addMaintainer(Maintainer addMe){
        maintainer.put(addMe.getUName(), addMe);
    }

    ArrayList<Maintainer> getAllMaintainers(){
        return new ArrayList<Maintainer>(maintainer.values());
    }

    @Override
    public String toString(){
        return "Manager: " + uName;
    }

    @Override
    public void saveThisObject(){
        try{
            Connection conn = DriverManager.getConnection(dbPath);
            PreparedStatement pstm = conn.prepareStatement(saveObject);
            pstm.setString(1, uName);
            
            ConvertObject<Manager> obj = new ConvertObject<>();
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
    public Manager retrieveThisObject(String name){
        Manager a = null;
        try{
            String retrival = "select obj from test_two where uname=?";
            Connection conn = DriverManager.getConnection(dbPath);
            PreparedStatement pstm = conn.prepareStatement(retrival);
            pstm.setString(1, name);
            ResultSet s = pstm.executeQuery();
            
            byte[] arr = s.getBytes("obj");
            ConvertObject<Manager> con = new ConvertObject<>();
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