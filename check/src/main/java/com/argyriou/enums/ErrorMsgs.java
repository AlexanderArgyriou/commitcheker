package com.argyriou.enums;

import org.jetbrains.annotations.Contract;

/**
 * 06-10-2021 DD/MM/YYYY
 *
 * @author PI9525
 *
 */
public enum ErrorMsgs {
    PROCESS_EXIT( "Failure on process exit!" ),
    RISKY( "Risky Commit, Outdated Binary Versions" );

    private final String msg;

    ErrorMsgs(String msg) {
        this.msg = msg;
    }

    @Contract( pure = true )
    public String getMsg() {
        return msg;
    }
}
