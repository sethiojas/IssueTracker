import java.util.HashMap;
import java.util.HashSet;
import java.util.ArrayList;
import java.util.Iterator;

class Project{
    static int totalBugs = 0;
    private static int bugId = 0;
    final private String projectName;
    private HashMap<Integer, Bug> bugs = new HashMap<>();
    protected HashSet<String> maintainers = new HashSet<>();

    Project(String title){
        projectName = title;
    }

    int createNewBug(String title, String desc){
        totalBugs += 1;
        bugId += 1;
        bugs.put(bugId, new Bug(title, desc, bugId));
        return bugId;
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

    ArrayList<String> getMaintainers(){
        ArrayList<String> list = new ArrayList<>();
        Iterator<String> iterMaintainers = maintainers.iterator();
        while(iterMaintainers.hasNext()){
            list.add(iterMaintainers.next());
        }
        return list;
    }
}