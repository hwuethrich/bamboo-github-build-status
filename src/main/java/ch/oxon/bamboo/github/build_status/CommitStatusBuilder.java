package ch.oxon.bamboo.github.build_status;

import com.atlassian.bamboo.chains.ChainResultsSummary;
import com.atlassian.bamboo.chains.branches.MergeResultSummary;
import com.atlassian.bamboo.resultsummary.tests.TestResultsSummary;
import org.eclipse.egit.github.core.CommitStatus;

public class CommitStatusBuilder extends CommitStatus {

    private int buildNumber;
    private String buildUrl;

    public CommitStatusBuilder(int buildNumber, String buildUrl) {
        this.buildNumber = buildNumber;
        this.buildUrl = buildUrl;
    }

    public CommitStatus buildPendingStatus() {
        CommitStatus status = initCommitStatus();

        // State
        status.setState(CommitStatus.STATE_PENDING);

        // Description
        String description = String.format("Build #%d is pending", buildNumber);
        status.setDescription(description);

        return status;
    }

    protected CommitStatus buildResultStatus(ChainResultsSummary result) {
        MergeResultSummary mergeResult = result.getMergeResult();

        if (mergeResult != null && mergeResult.hasFailed()) {
            return buildErrorStatus(buildNumber, "could not be merged");
        } else {
            switch (result.getBuildState()) {
                case FAILED:  return buildFailedStatus(buildNumber, result.getTestResultsSummary());
                case SUCCESS: return buildSuccessStatus(buildNumber);
            }
        }

        return buildErrorStatus(buildNumber, "has unknown state");
    }

    protected CommitStatus buildSuccessStatus(int buildNumber) {
        CommitStatus status = initCommitStatus();

        // State
        status.setState(CommitStatus.STATE_SUCCESS);

        // Description
        String description = String.format("Build #%d is successful", buildNumber);
        status.setDescription(description);

        return status;
    }

    protected CommitStatus buildFailedStatus(int buildNumber, TestResultsSummary testResultsSummary) {
        CommitStatus status = initCommitStatus();

        // State
        status.setState(CommitStatus.STATE_FAILURE);

        // Errors
        int totalTests  = testResultsSummary.getTotalTestCaseCount();
        int failedTests = testResultsSummary.getFailedTestCaseCount();

        // Description
        String description = String.format("Build #%d failed with %d/%d tests", buildNumber, failedTests, totalTests);
        status.setDescription(description);

        return status;
    }

    protected CommitStatus buildErrorStatus(int buildNumber, String message) {
        CommitStatus status = initCommitStatus();

        // State
        status.setState(CommitStatus.STATE_ERROR);

        // Description
        String description = String.format("Build #%d %s", buildNumber, message);
        status.setDescription(description);

        return status;
    }

    protected CommitStatus initCommitStatus() {
        CommitStatus status = new CommitStatus();

        status.setTargetUrl(buildUrl);

        return status;
    }
}
