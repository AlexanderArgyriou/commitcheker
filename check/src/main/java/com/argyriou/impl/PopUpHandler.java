package com.argyriou.impl;

import com.argyriou.enums.CvsCommands;
import com.argyriou.enums.ErrorMsgs;
import com.argyriou.intrf.PopUpHandlerIf;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.vcs.CheckinProjectPanel;
import com.intellij.openapi.vcs.VcsBundle;

import org.jetbrains.annotations.NotNull;
import java.util.List;

/**
 * 06-10-2021 DD/MM/YYYY
 *
 * @author PI9525
 *
 */
public class PopUpHandler implements PopUpHandlerIf {
    List<String> diffs;
    CheckinProjectPanel panel;

    public PopUpHandler() {
    }

    public PopUpHandler(List<String> diffs, CheckinProjectPanel panel) {
        this.diffs = diffs;
        this.panel = panel;
    }

    /**
     * Pops up a window to inform you about diffs also returns your choice for further handling.
     * @return boolean
     */
    @Override
    public boolean doPopUp() {
        if ( !diffs.isEmpty() ) {
            StringBuilder msg = constructInfoMessage( diffs );
            return Messages.showYesNoDialog( panel.getProject(),
                    msg.toString(),
                    ErrorMsgs.RISKY.getMsg(),
                    VcsBundle.message( CvsCommands.COMMIT.getCommand() ),
                    VcsBundle.message( CvsCommands.CANCEL.getCommand() ), null ) == Messages.YES;
        }
        return true;
    }

    @NotNull
    private StringBuilder constructInfoMessage(List<String> diffs) {
        StringBuilder msg = new StringBuilder();
        for ( String entry : diffs ) {
            msg.append( entry )
                    .append(" is outdated!" )
                    .append("\n");
        }
        return msg;
    }

    public List<String> getDiffs() {
        return diffs;
    }

    public void setDiffs(List<String> diffs) {
        this.diffs = diffs;
    }

    public CheckinProjectPanel getPanel() {
        return panel;
    }

    public void setPanel(CheckinProjectPanel panel) {
        this.panel = panel;
    }
}
