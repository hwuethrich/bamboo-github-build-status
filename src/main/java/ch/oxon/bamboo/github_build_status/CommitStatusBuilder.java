package ch.oxon.bamboo.github_build_status;

import com.atlassian.bamboo.builder.BuildState;
import com.atlassian.bamboo.v2.build.BuildContext;
import com.atlassian.bamboo.v2.build.CurrentBuildResult;
import org.eclipse.egit.github.core.CommitStatus;

public class CommitStatusBuilder extends CommitStatus {

    private BuildContext context;

    public CommitStatusBuilder(BuildContext context) {
        this.context = context;
    }

    public CommitStatus buildResultStatus() {
        CommitStatus status = null;

        switch (getBuildState()) {
            case SUCCESS:
                status = buildSuccessStatus();
                break;
            case FAILED:
                status = buildFailedStatus();
                break;
            default:
                status = buildErrorStatus();
                break;
        }

        return status;
    }

    public CommitStatus buildPendingStatus() {
        CommitStatus status = initCommitStatus();

        // State
        status.setState(CommitStatus.STATE_PENDING);

        // Description
        int buildNumber = context.getBuildNumber();
        String description = String.format("Build #%d is pending", buildNumber);
        status.setDescription(description);

        return status;
    }

    protected CommitStatus buildSuccessStatus() {
        CommitStatus status = initCommitStatus();

        // State
        status.setState(CommitStatus.STATE_SUCCESS);

        // Description
        int buildNumber = context.getBuildNumber();
        String description = String.format("Build #%d is successful", buildNumber);
        status.setDescription(description);

        return status;
    }

    protected CommitStatus buildFailedStatus() {
        CommitStatus status = initCommitStatus();

        // State
        status.setState(CommitStatus.STATE_FAILURE);

        // Description
        int buildNumber = context.getBuildNumber();
        String description = String.format("Build #%d has failed", buildNumber);
        status.setDescription(description);

        return status;
    }

    protected CommitStatus buildErrorStatus() {
        CommitStatus status = initCommitStatus();

        // State
        status.setState(CommitStatus.STATE_ERROR);

        // Description
        int buildNumber = context.getBuildNumber();
        String description = String.format("Build #%d had an error", buildNumber);
        status.setDescription(description);

        return status;
    }


    protected CurrentBuildResult getBuildResult() {
        return context.getBuildResult();
    }

    protected BuildState getBuildState() {
        return getBuildResult().getBuildState();
    }

    protected CommitStatus initCommitStatus() {
        CommitStatus status = new CommitStatus();

        // Target URL
        status.setTargetUrl("test");

        return status;
    }
}
