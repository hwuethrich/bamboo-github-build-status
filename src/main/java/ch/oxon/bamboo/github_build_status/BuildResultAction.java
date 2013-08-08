package ch.oxon.bamboo.github_build_status;

import com.atlassian.bamboo.build.CustomPostBuildCompletedAction;
import com.atlassian.bamboo.v2.build.BuildContext;
import org.eclipse.egit.github.core.CommitStatus;

public class BuildResultAction extends BuildStatusAction implements CustomPostBuildCompletedAction {
    public CommitStatus getCommitStatus() {
        return getCommitStatusBuilder().buildResultStatus();
    }
}

