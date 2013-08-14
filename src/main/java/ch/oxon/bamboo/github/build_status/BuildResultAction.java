package ch.oxon.bamboo.github.build_status;

import com.atlassian.bamboo.chains.Chain;
import com.atlassian.bamboo.chains.ChainExecution;
import com.atlassian.bamboo.chains.ChainResultsSummary;
import com.atlassian.bamboo.chains.plugins.PostChainAction;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class BuildResultAction extends BuildStatusAction implements PostChainAction {

    @Override
    public void execute(@NotNull Chain chain, @NotNull ChainResultsSummary chainResultsSummary, @NotNull ChainExecution chainExecution) throws Exception {
        setConfiguration(chain.getBuildDefinition().getCustomConfiguration());

        try {
            sendBuildResultStatus(chainExecution, chainResultsSummary);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

