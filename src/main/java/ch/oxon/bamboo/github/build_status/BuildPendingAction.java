package ch.oxon.bamboo.github.build_status;

import com.atlassian.bamboo.chains.Chain;
import com.atlassian.bamboo.chains.ChainExecution;
import com.atlassian.bamboo.chains.plugins.PreChainAction;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class BuildPendingAction extends BuildStatusAction implements PreChainAction {

    @Override
    public void execute(@NotNull Chain chain, @NotNull ChainExecution chainExecution) {
        setConfiguration(chain.getBuildDefinition().getCustomConfiguration());

        try {
            sendBuildPendingStatus(chainExecution);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
