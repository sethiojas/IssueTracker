import com.issue_tracker.Project;

public interface Contributor {
    void initialize(String name);
    void saveChangesToProject(Project project);
}