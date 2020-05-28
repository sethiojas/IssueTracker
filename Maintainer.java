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

    @Override
    public String getUName(){
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
}