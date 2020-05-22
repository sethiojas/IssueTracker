import java.util.HashMap;
// import java.util.HashSet;
import java.util.ArrayList;

class Project{
    private static int totalBugs = 0;
    final private String projectName;
    private HashMap<Integer, Bug> bugs = new HashMap<>();
    // private HashSet<Maintainer> maintainers = new HashSet<>();
    //implement return maintainers

    Project(String title){
        projectName = title;
    }

    int createNewBug(String title, String desc){
        //totalBugs is being used as a unique ID for a bug
        totalBugs += 1;
        bugs.put(totalBugs, new Bug(title, desc, totalBugs));
        return totalBugs;
    }
    
    String getProjectName(){
        return projectName;
    }

    boolean closeBug(int bugId){
        if(bugs.containsKey(bugId)){
            bugs.remove(bugId);
            totalBugs -=1;
            return true;
        }
        return false;
    }

    ArrayList<Bug> getAllBugs(){
        return new ArrayList<Bug>(bugs.values());
    }

    int getTotalBugs(){
        return totalBugs;
    }
}