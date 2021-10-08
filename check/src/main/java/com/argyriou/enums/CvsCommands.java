package com.argyriou.enums;

import org.jetbrains.annotations.Contract;

/**
 * 06-10-2021 DD/MM/YYYY
 *
 * @author PI9525
 *
 */
public enum CvsCommands {
    CVS_PREFIX( "cvs -d " ),
    @Deprecated
    LAST_HISTORIC_ENTRY ( CVS_PREFIX.getCommand() + System.getProperty( ExecArgs.CVS.getArg(), System.getenv( ExecArgs.CVS.getArg() ) ) + " history -a -c -l " ),
    @Deprecated
    LOG ( CVS_PREFIX.getCommand() + System.getProperty( ExecArgs.CVS.getArg(), System.getenv( ExecArgs.CVS.getArg() ) ) + " log -h " ),
    STATUS ( CVS_PREFIX.getCommand() + System.getProperty( ExecArgs.CVS.getArg(), System.getenv( ExecArgs.CVS.getArg() ) ) + " status " ),
    COMMIT( "checkin.commit" ),
    CANCEL( "checkin.cancel" );

    private final String command;

    CvsCommands(String command) {
        this.command = command;
    }

    @Contract( pure = true )
    public String getCommand() {
        return command;
    }
}
