package lt.banelis.aurelijus.simpleplugin;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import org.jetbrains.annotations.NotNull;

public class ProjectPath extends AnAction {
    public ProjectPath() {
        super("auginte");
    }

    public void actionPerformed(@NotNull AnActionEvent event) {
        Project project = event.getProject();
        if (project != null) {
            String path = project.getBaseDir().getPath();
            Messages.showMessageDialog(path, "Project", Messages.getInformationIcon());
        } else {
            Messages.showMessageDialog("No project", "Project", Messages.getWarningIcon());
        }
    }
}
