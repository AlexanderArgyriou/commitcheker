package com.argyriou.enums;

import org.jetbrains.annotations.Contract;

/**
 * 08-10-2021 DD/MM/YYYY
 *
 * @author PI9525
 *
 *   Result of a cvs status command in a file( head ), works for branch the same + tags and so on.
 *
 *   File: TarifSteuerung.xls	Status: (Up-to-date) - (Needs Patch) - (Locally Modified) ......
 *
 *
 *      Working revision:	1.332
 *      Repository revision:	1.332	/var/cvsfuture/LofuApp/DERules/rules/DE0/DETarif/Basis/TarifSteuerung.xls,v
 *      Commit Identifier:	6e08614deba44567
 *      Sticky Tag:		(none)
 *      Sticky Date:		(none)
 *      Sticky Options:	-kb
 */
public enum KeyWords {
    @Deprecated
    NEEDS_PATCH( "Needs Patch" ), // BRANCH
    @Deprecated
    NEEDS_MERGE( "Needs Merge" ), // HEAD
    LOC_MOD( "Locally Modified" ), // LATEST VERSION MODIFIED (important)
    UP_TO_DATE( "Up-to-date" ), // PERFECT (important)
    ADDED("Locally Added"), // NEW FILE (important)
    UNDEFINED( "Undefined");

    private final String keyWord;

    KeyWords(String keyWord) {
        this.keyWord = keyWord;
    }

    @Contract( pure = true )
    public String getKeyWord() {
        return keyWord;
    }
}
