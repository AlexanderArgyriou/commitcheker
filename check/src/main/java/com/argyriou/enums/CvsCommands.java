package com.argyriou.enums;

import org.jetbrains.annotations.Contract;

/**
 * 06-10-2021 DD/MM/YYYY
 *
 * @author PI9525
 *
 */
public enum CvsCommands {
    @Deprecated
    LAST_HISTORIC_ENTRY ( "cvs -d " + System.getProperty( ExecArgs.CVS.getArg(), System.getenv( ExecArgs.CVS.getArg() ) ) + " history -a -c -l " ),
    LOG ( "cvs -d " + System.getProperty( ExecArgs.CVS.getArg(), System.getenv( ExecArgs.CVS.getArg() ) ) + " log -h " ),
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
