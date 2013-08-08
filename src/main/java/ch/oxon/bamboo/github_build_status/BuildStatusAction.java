package ch.oxon.bamboo.github_build_status;

import com.atlassian.bamboo.repository.RepositoryDefinition;
import com.atlassian.bamboo.v2.build.BuildContext;
import com.atlassian.bamboo.v2.build.task.BuildTask;
import org.eclipse.egit.github.core.CommitStatus;
import org.eclipse.egit.github.core.IRepositoryIdProvider;
import org.eclipse.egit.github.core.RepositoryId;
import org.eclipse.egit.github.core.service.CommitService;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public abstract class BuildStatusAction implements BuildTask {

    public static String GITHUB_OWNER = "custom.githubBuildStatus.github.owner";
    public static String GITHUB_REPO  = "custom.githubBuildStatus.github.repo";
    public static String GITHUB_TOKEN = "custom.githubBuildStatus.github.token";

    private CommitStatusBuilder commitStatusBuilder;
    private BuildContext buildContext;

    public void init(BuildContext buildContext) {
        this.buildContext = buildContext;
        this.commitStatusBuilder = new CommitStatusBuilder(buildContext);
    }

    public BuildContext call() {
        try {
            publishCommitStatus();
        } catch (Exception e) {
            //
        }
        return buildContext;
    }

    public abstract CommitStatus getCommitStatus();

    protected CommitStatusBuilder getCommitStatusBuilder() {
        return commitStatusBuilder;
    }

    protected void publishCommitStatus() throws IOException {
        CommitService service = getGithubCommitService();
        CommitStatus  status  = getCommitStatus();
        RepositoryId  repo    = getGithubRepository();
        String        sha     = getCommitHash();

        service.createStatus(repo, sha, status);
    }

    protected String getCommitHash() throws IllegalArgumentException {
        List<RepositoryDefinition> repositories = buildContext.getRepositoryDefinitions();

        if(repositories.isEmpty()) {
            throw new IllegalArgumentException("Could not find any repositories for build!");
        }

        RepositoryDefinition repository = repositories.get(0);
        return buildContext.getBuildChanges().getVcsRevisionKey(repository.getId());
    }

    protected CommitService getGithubCommitService() {
        CommitService service = new CommitService();
        service.getClient().setOAuth2Token("token");
        return service;
    }

    protected RepositoryId getGithubRepository() {
        String owner = getBuildConfiguration().get(GITHUB_OWNER);
        String repo  = getBuildConfiguration().get(GITHUB_REPO);

        return new RepositoryId(owner, repo);
    }

    protected Map<String, String> getBuildConfiguration() {
        return buildContext.getBuildDefinition().getCustomConfiguration();
    }

}
