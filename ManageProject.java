import java.util.ArrayList;

class ManageProject extends Project{
    ManageProject(String title){
        super(title);
    }
    boolean addMaintainer(String uName){
        return maintainers.add(uName);
    }

    boolean addMaintainers(ArrayList<String> uNameList){
        return maintainers.addAll(uNameList);
    }
}