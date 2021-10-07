package com.argyriou.intrf;

import java.io.File;
import java.util.List;

/**
 * 06-10-2021 DD/MM/YYYY
 *
 * @author PI9525
 *
 */
public interface CvsOperatorIf {
    public void doProcess();

    public void setCliArgs(List<String> cliArgs);

    public void setFileWorkDir(File fileWorkDir);

    public String getResult();

    public void refresh();
}
