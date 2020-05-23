import java.util.HashMap;
import java.util.ArrayList;

class Maintainer{
    
    protected String uName;
    private HashMap<String, Project> projects = new HashMap<>();

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

    @Override
    public String toString(){
        return "Maintainer: " + uName;
    }
}