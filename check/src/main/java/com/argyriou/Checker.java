package com.argyriou;

import com.intellij.openapi.vcs.CheckinProjectPanel;
import com.intellij.openapi.vcs.changes.CommitContext;
import com.intellij.openapi.vcs.changes.ui.BooleanCommitOption;
import com.intellij.openapi.vcs.checkin.CheckinHandler;
import com.intellij.openapi.vcs.checkin.CheckinHandlerFactory;
import com.intellij.openapi.vcs.ui.RefreshableOnComponent;

import org.jetbrains.annotations.NotNull;

public class Checker extends CheckinHandlerFactory {

    @NotNull
    @Override
    public CheckinHandler createHandler(@NotNull CheckinProjectPanel panel,
                                        @NotNull CommitContext commitContext) {
        return new CheckinHandler() {
            private boolean changedFilesAreDoneOnReposLastVersion = false;

            @Override
            public RefreshableOnComponent getBeforeCheckinConfigurationPanel() {
                return new BooleanCommitOption( panel,"Commit Checker",false,
                        this::isChangedFilesAreDoneOnReposLastVersion,
                        this::setChangedFilesAreDoneOnReposLastVersion );
            }

            public boolean isChangedFilesAreDoneOnReposLastVersion() {
                return changedFilesAreDoneOnReposLastVersion;
            }

            public void setChangedFilesAreDoneOnReposLastVersion(boolean changedFilesAreDoneOnReposLastVersion) {
                this.changedFilesAreDoneOnReposLastVersion = changedFilesAreDoneOnReposLastVersion;
            }

            @Override
            public ReturnResult beforeCheckin() {
                // do your magic here.
                return super.beforeCheckin();
            }
        };
    }
}
