import java.util.HashMap;
import java.util.ArrayList;

class Maintainer{
    
    protected String uName;
    private HashMap<String, Project> projects = new HashMap<>();
    private String manager;

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
}