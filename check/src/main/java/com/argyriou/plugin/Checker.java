package com.argyriou.plugin;

import com.argyriou.impl.LofuCheckInHandler;
import com.intellij.openapi.vcs.CheckinProjectPanel;
import com.intellij.openapi.vcs.changes.CommitContext;
import com.intellij.openapi.vcs.checkin.CheckinHandler;
import com.intellij.openapi.vcs.checkin.CheckinHandlerFactory;

import org.jetbrains.annotations.NotNull;

/**
 * 06-10-2021 DD/MM/YYYY
 *
 * @author PI9525
 *
 * returns an implementation of a checkbox in "before commit" section, with relevant functionality.
 */
public class Checker extends CheckinHandlerFactory {
    @NotNull
    @Override
    public CheckinHandler createHandler(@NotNull CheckinProjectPanel panel,
                                        @NotNull CommitContext commitContext) {
        return new LofuCheckInHandler( panel, commitContext );
    }
}
