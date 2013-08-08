package ch.oxon.bamboo.github_build_status;

import com.atlassian.bamboo.plan.Plan;
import com.atlassian.bamboo.plan.TopLevelPlan;
import com.atlassian.bamboo.v2.build.BaseBuildConfigurationAwarePlugin;
import com.atlassian.bamboo.v2.build.configuration.MiscellaneousBuildConfigurationPlugin;
import com.atlassian.bamboo.ww2.actions.build.admin.create.BuildConfiguration;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public class PlanConfiguration extends BaseBuildConfigurationAwarePlugin implements MiscellaneousBuildConfigurationPlugin {

    public boolean isApplicableTo(Plan plan) {
        return plan instanceof TopLevelPlan;
    }

    protected void populateContextForView(@NotNull Map<String, Object> context, @NotNull Plan plan) {
        super.populateContextForView(context, plan);
    }

    protected void populateContextForEdit(Map<String, Object> context, BuildConfiguration buildConfiguration, Plan plan) {
        super.populateContextForEdit(context, buildConfiguration, plan);

        context.put("enabled", buildConfiguration.getBoolean("enabled"));
    }
}
