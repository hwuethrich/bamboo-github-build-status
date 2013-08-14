package ch.oxon.bamboo.github.build_status;

import com.atlassian.bamboo.configuration.AdministrationConfiguration;
import com.atlassian.bamboo.plan.Plan;
import com.atlassian.bamboo.plan.TopLevelPlan;
import com.atlassian.bamboo.utils.error.ErrorCollection;
import com.atlassian.bamboo.v2.build.BaseBuildConfigurationAwarePlugin;
import com.atlassian.bamboo.v2.build.configuration.MiscellaneousBuildConfigurationPlugin;
import com.atlassian.bamboo.ww2.actions.build.admin.create.BuildConfiguration;
import com.atlassian.spring.container.ContainerManager;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public class BuildStatusConfigurator extends BaseBuildConfigurationAwarePlugin implements MiscellaneousBuildConfigurationPlugin {

    public static String ENABLED      = "custom.githubBuildStatus.enabled";
    public static String GITHUB_OWNER = "custom.githubBuildStatus.githubOwner";
    public static String GITHUB_REPO  = "custom.githubBuildStatus.githubRepo";
    public static String GITHUB_TOKEN = "custom.githubBuildStatus.githubToken";

    private AdministrationConfiguration administrationConfiguration;

    public boolean isApplicableTo(Plan plan) {
        return plan instanceof TopLevelPlan;
    }

    @NotNull
    @Override
    public ErrorCollection validate(@NotNull BuildConfiguration buildConfiguration) {
        return super.validate(buildConfiguration);
    }

    @Override
    public boolean isConfigurationMissing(@NotNull BuildConfiguration buildConfiguration) {
        if(!buildConfiguration.getBoolean(ENABLED))
            return false;

        return buildConfiguration.getString(GITHUB_OWNER) == null ||
               buildConfiguration.getString(GITHUB_REPO)  == null ||
               buildConfiguration.getString(GITHUB_TOKEN) == null;
    }
}
