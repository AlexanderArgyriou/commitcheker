package com.argyriou.intrf;

import com.intellij.openapi.vcs.CheckinProjectPanel;

import java.util.List;

/**
 * 06-10-2021 DD/MM/YYYY
 *
 * @author PI9525
 *
 */
public interface PopUpHandlerIf {
    public boolean doPopUp();

    public void setDiffs(List<String> diffs);

    public void setPanel(CheckinProjectPanel panel);
}
