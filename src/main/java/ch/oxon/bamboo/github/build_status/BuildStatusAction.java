package ch.oxon.bamboo.github.build_status;

import com.atlassian.bamboo.chains.ChainExecution;
import com.atlassian.bamboo.chains.ChainResultsSummary;
import com.atlassian.bamboo.configuration.AdministrationConfiguration;
import com.atlassian.bamboo.plan.PlanResultKey;
import com.atlassian.bamboo.v2.build.BuildChanges;
import com.atlassian.bamboo.v2.build.BuildRepositoryChanges;
import com.atlassian.spring.container.ContainerManager;
import org.eclipse.egit.github.core.CommitStatus;
import org.eclipse.egit.github.core.RepositoryId;
import org.eclipse.egit.github.core.service.CommitService;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

public class BuildStatusAction {

    private Map<String, String> configuration = null;

    protected void sendBuildPendingStatus(ChainExecution chainExecution) throws IOException {
        int buildNumber = chainExecution.getPlanResultKey().getBuildNumber();
        String buildUrl = getBuildUrlForPlanResultKey(chainExecution.getPlanResultKey());

        CommitStatusBuilder builder = new CommitStatusBuilder(buildNumber, buildUrl);
        CommitStatus status = builder.buildPendingStatus();

        String sha = getCommitHashFromChanges(chainExecution.getBuildChanges());
        sendCommitStatus(status, sha);
    }

    protected void sendBuildResultStatus(ChainExecution chainExecution, ChainResultsSummary result) throws IOException {
        int buildNumber = chainExecution.getPlanResultKey().getBuildNumber();
        String buildUrl = getBuildUrlForPlanResultKey(chainExecution.getPlanResultKey());

        CommitStatusBuilder builder = new CommitStatusBuilder(buildNumber, buildUrl);
        CommitStatus status = builder.buildResultStatus(result);

        String sha = getCommitHashFromChanges(chainExecution.getBuildChanges());
        sendCommitStatus(status, sha);
    }

    protected void setConfiguration(Map<String, String> configuration) {
        this.configuration = configuration;
    }

    protected String getCommitHashFromChanges(BuildChanges buildChanges) {
        Iterator<BuildRepositoryChanges> iterator = buildChanges.getRepositoryChanges().iterator();

        BuildRepositoryChanges changes = null;
        while(iterator.hasNext()) changes = iterator.next();

        return changes.getVcsRevisionKey();
    }

    protected void sendCommitStatus(CommitStatus status, String sha) throws IOException {
        CommitService service = getGithubCommitService();
        RepositoryId  repo    = getGithubRepository();
        service.createStatus(repo, sha, status);
    }

    protected CommitService getGithubCommitService() {
        CommitService service = new CommitService();

        String token = configuration.get(BuildStatusConfigurator.GITHUB_TOKEN);
        service.getClient().setOAuth2Token(token);

        return service;
    }

    protected RepositoryId getGithubRepository() {
        String owner = configuration.get(BuildStatusConfigurator.GITHUB_OWNER);
        String repo  = configuration.get(BuildStatusConfigurator.GITHUB_REPO);

        return new RepositoryId(owner, repo);
    }

    private String getBuildUrlForPlanResultKey(PlanResultKey planResultKey) {
        return String.format("%s/browse/%s", getBambooBaseUrl(), planResultKey.toString());
    }

    private AdministrationConfiguration getAdministrationConfiguration() {
        return (AdministrationConfiguration) ContainerManager.getComponent("administrationConfiguration");
    }

    private String getBambooBaseUrl() {
        return getAdministrationConfiguration().getBaseUrl();
    }

}
