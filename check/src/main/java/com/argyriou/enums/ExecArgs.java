package com.argyriou.enums;

import org.jetbrains.annotations.Contract;

/**
 * 06-10-2021 DD/MM/YYYY
 *
 * @author PI9525
 *
 */
public enum ExecArgs {
    CONSOLE( "cmd.exe" ),
    RUN_AND_THEN_CLOSE( "/c" ),
    CVS( "CVS_INFO" );

    private final String arg;

    ExecArgs(String arg) {
        this.arg = arg;
    }

    @Contract( pure = true )
    public String getArg() {
        return arg;
    }
}
