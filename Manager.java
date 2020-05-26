import java.util.ArrayList;
import java.util.HashMap;

class Manager extends Maintainer{
    
    private HashMap<String, Maintainer> maintainer = new HashMap<>();
    private HashMap<String, ManageProject> projects = new HashMap<>();

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
}