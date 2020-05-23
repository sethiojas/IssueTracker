import java.util.ArrayList;
import java.util.HashMap;

class Manager extends Maintainer{
    
    private HashMap<String, Maintainer> maintainer = new HashMap<>();
    private HashMap<String, ManageProject> projects = new HashMap<>();
    


    Manager(String _uName){
        super(_uName);
        maintainer.put("darren", new Maintainer("darren"));
        maintainer.put("loony", new Maintainer("loony"));
        maintainer.put("bard", new Maintainer("bard"));
    }

    void test(){
        System.out.println(projects);
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

    Maintainer getM(){
        return maintainer.get("darren");
    }

    ManageProject getProjectByTitle(String title){
        return projects.get(title);
    }
}