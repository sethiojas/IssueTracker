import java.util.ArrayList;
import java.util.HashMap;

class Admin{
    
    private HashMap<String, Manager> managers = new HashMap<>();
    private HashMap<String, Maintainer> onBench = new HashMap<>();
    private final String adminUName;

    Admin(String _name){
        adminUName = _name;
    }

    void createNewManager(String uName){
        Manager newManager = new Manager(uName);
        managers.put(uName, newManager);
    }

    void createNewMaintainer(String uName){
        Maintainer newMaintainer = new Maintainer(uName);
        onBench.put(uName, newMaintainer);
    }

    void promoteToManager(String uName){
        Maintainer thisMaintainer = onBench.remove(uName);
        Manager promoted = new Manager(thisMaintainer.getUName());
        managers.put(uName, promoted);
    }

    void assignManager(String uNameManager, String uNameMaintainer){
        Manager manager = managers.get(uNameManager);
        Maintainer maintainer = onBench.remove(uNameMaintainer);
        manager.addMaintainer(maintainer);
    }

    void putMaintainerOnBench(String uNameManager, String uNameMaintainer){
        Manager manager = managers.get(uNameManager);
        Maintainer benchMaintainer = manager.removeMaintainer(uNameMaintainer);
        onBench.put(benchMaintainer.getUName(), benchMaintainer);
    }
    
    void removeManager(String uName){
        Manager rManager = managers.get(uName);
        for (Maintainer m : rManager.getAllMaintainers()){
            putMaintainerOnBench(uName, m.getUName());
        }
        managers.remove(uName);
    }
    
    void removeMaintainer(String uName){
        onBench.remove(uName);
    }

    ArrayList<Manager> getAllManagers(){
        return new ArrayList<Manager>(managers.values());
    }

    ArrayList<Maintainer> getAllOnBench(){
        return new ArrayList<Maintainer>(onBench.values());
    }

    @Override
    public String toString(){
        return "Admin: " + adminUName;
    }
}