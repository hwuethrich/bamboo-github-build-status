package ch.oxon.bamboo.github_build_status;

import com.atlassian.bamboo.build.CustomPreBuildAction;
import com.atlassian.bamboo.utils.error.ErrorCollection;
import com.atlassian.bamboo.ww2.actions.build.admin.create.BuildConfiguration;
import org.eclipse.egit.github.core.CommitStatus;

public class BuildPendingAction extends BuildStatusAction implements CustomPreBuildAction {

    public CommitStatus getCommitStatus() {
        return getCommitStatusBuilder().buildPendingStatus();
    }

    public ErrorCollection validate(BuildConfiguration buildConfiguration) {
        return null;
    }

}
