package com.argyriou.impl;

import com.argyriou.enums.ErrorMsgs;
import com.argyriou.enums.KeyWords;
import com.argyriou.intrf.CvsOperatorIf;
import com.intellij.openapi.diagnostic.Logger;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;

/**
 * 06-10-2021 DD/MM/YYYY
 *
 * @author PI9525
 *
 */
public class CvsOperator implements CvsOperatorIf {
    private static final Logger logger = Logger.getInstance( CvsOperator.class );
    private List<String> cliArgs;
    File fileWorkDir;
    String result = KeyWords.UNDEFINED.getKeyWord();

    public CvsOperator() {
    }

    public CvsOperator(List<String> cliArgs, File fileWorkDir) {
        this.cliArgs = cliArgs;
        this.fileWorkDir = fileWorkDir;
    }

    @Override
    public void doProcess() {
        try {
            ProcessBuilder processBuilder = new ProcessBuilder();
            processBuilder.directory( fileWorkDir );
            processBuilder.command( cliArgs );
            Process p;

            try {
                logger.info( "About to run "
                        + String.join(" ", cliArgs ) + " in " + fileWorkDir.getAbsolutePath() );
                p = processBuilder.start();
            } catch ( IOException e ) {
                logger.error( e );
                return;
            }

            try ( Reader reader = new InputStreamReader( p.getInputStream() );
                 BufferedReader r = new BufferedReader( reader ) ) {
                String line;
                while ( true ) {
                    line = r.readLine();
                    if ( line == null || !result.equals( KeyWords.UNDEFINED.getKeyWord() ) ) {
                        break;
                    }
                    investigateLine( line );
                    logger.info( line );
                }
            }

            int exitValue;
            try {
                exitValue = p.waitFor();
            } catch ( InterruptedException e ) {
                p.destroy();
                logger.error( e );
                return;
            }
            if ( exitValue != 0 ) {
                logger.error( ErrorMsgs.PROCESS_EXIT.getMsg() );
            }
        } catch ( Exception e ) {
            logger.error( e );
        }
    }

    private void investigateLine(String line) {
        if ( line.contains( KeyWords.UP_TO_DATE.getKeyWord() ) ) {
            result = KeyWords.UP_TO_DATE.getKeyWord();
        }
        if ( line.contains( KeyWords.LOC_MOD.getKeyWord() ) ) {
            result = KeyWords.LOC_MOD.getKeyWord();
        }
        if ( line.contains( KeyWords.ADDED.getKeyWord() ) ) {
            result = KeyWords.ADDED.getKeyWord();
        }
    }

    @Override
    public void refresh() {
        result = KeyWords.UNDEFINED.getKeyWord();
        fileWorkDir = null;
        cliArgs = null;
    }

    public static Logger getLogger() {
        return logger;
    }

    public List<String> getCliArgs() {
        return cliArgs;
    }

    public void setCliArgs(List<String> cliArgs) {
        this.cliArgs = cliArgs;
    }

    public File getFileWorkDir() {
        return fileWorkDir;
    }

    public void setFileWorkDir(File fileWorkDir) {
        this.fileWorkDir = fileWorkDir;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
