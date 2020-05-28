import java.util.HashMap;
import java.util.ArrayList;
import java.io.Serializable;

class Maintainer implements Serializable, Saveable{
    
    protected String uName;
    private HashMap<String, Project> projects = new HashMap<>();
    private String manager;

    // https://stackoverflow.com/questions/10378855/java-io-invalidclassexception-local-class-incompatible
    private static final long serialVersionUID = 6529685098267757692L;

    Maintainer(String _uName){
        uName = _uName;
    }

    String getUName(){
        return uName;
    }

    ArrayList<Project> getProjects(){
        return new ArrayList(projects.values());
    }

    Project getProjectByTitle(String title){
        return projects.get(title);
    }

    void addProject(Project proj){
        projects.put(proj.getProjectName(), proj);
    }

    boolean removeProject(Project proj){
        Project isRemoved = projects.remove(proj.getProjectName());
        if(proj.equals(isRemoved)) return true;
        return false;
    }

    void removeAllProjects(){
        projects.clear();
    }

    void setManager(String uNameManager){
        manager = uNameManager;
    }

    String getManager(){
        return manager;
    }

    @Override
    public String toString(){
        return "Maintainer: " + uName;
    }

    @Override
    public void saveThisObject(){
        try{
            String saveObject = "insert into test_two values(?, ?)";
            Connection conn = DriverManager.getConnection(dbPath);
            PreparedStatement pstm = conn.prepareStatement(saveObject);
            pstm.setString(1, uName);
            
            ConvertObject<Maintainer> obj = new ConvertObject<>();
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
    public Maintainer retrieveThisObject(){
        Maintainer a = null;
        try{
            String retrival = "select obj from test_two where uname=?";
            Connection conn = DriverManager.getConnection(dbPath);
            PreparedStatement pstm = conn.prepareStatement(retrival);
            pstm.setString(1, uName);
            ResultSet s = pstm.executeQuery();
            
            byte[] arr = s.getBytes("obj");
            ConvertObject<Maintainer> con = new ConvertObject<>();
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