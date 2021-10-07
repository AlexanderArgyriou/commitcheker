package com.argyriou.intrf;

import com.intellij.openapi.util.Pair;
import com.intellij.openapi.vcs.CheckinProjectPanel;

import java.util.Map;

/**
 * 06-10-2021 DD/MM/YYYY
 *
 * @author PI9525
 *
 */
public interface PopUpHandlerIf {
    public boolean doPopUp();

    public void setDiffs(Map<String, Pair<String, String>> diffs);

    public void setPanel(CheckinProjectPanel panel);
}
