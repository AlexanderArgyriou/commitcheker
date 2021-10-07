package com.argyriou.impl;

import com.argyriou.enums.CvsCommands;
import com.argyriou.enums.ExecArgs;
import com.argyriou.intrf.CvsOperatorIf;
import com.argyriou.intrf.LofuCheckInHandlerIf;
import com.argyriou.intrf.PopUpHandlerIf;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.util.Pair;
import com.intellij.openapi.vcs.CheckinProjectPanel;
import com.intellij.openapi.vcs.changes.Change;
import com.intellij.openapi.vcs.changes.CommitContext;
import com.intellij.openapi.vcs.changes.ui.BooleanCommitOption;
import com.intellij.openapi.vcs.checkin.CheckinHandler;
import com.intellij.openapi.vcs.ui.RefreshableOnComponent;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.util.containers.ContainerUtil;

import org.jetbrains.annotations.NotNull;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 06-10-2021 DD/MM/YYYY
 *
 * @author PI9525
 *
 */
public class LofuCheckInHandler extends CheckinHandler implements LofuCheckInHandlerIf {
    private static final Logger logger = Logger.getInstance( LofuCheckInHandler.class );
    private boolean boxChecked; // false
    private CheckinProjectPanel panel;
    private PopUpHandlerIf popUpHandler = new PopUpHandler();
    private CvsOperatorIf cvsOperator = new CvsOperator();
    private CommitContext commitContext;

    public LofuCheckInHandler() {
    }

    public LofuCheckInHandler(CheckinProjectPanel panel, CommitContext commitContext) {
        this.panel = panel;
        this.commitContext = commitContext;
    }

    /**
     * handles check and un check operations in the checkbox ( UI ).
     * @return RefreshableOnComponent
     */
    @Override
    public RefreshableOnComponent getBeforeCheckinConfigurationPanel() {
        return new BooleanCommitOption( panel,"Commit Checker",false,
                this::isBoxChecked,
                this::setBoxChecked ); // could parse a lambda also, who cares, i don't like them.
    }

    @Override
    public boolean isBoxChecked() {
        return boxChecked;
    }

    public void setBoxChecked(boolean changedFilesAreDoneOnReposLastVersion) {
        this.boxChecked = changedFilesAreDoneOnReposLastVersion;
    }

    /**
     * Here u define what you want to happen when the commit button pressed, before the commit complete it's process.
     * @return ReturnResult
     */
    @Override
    public ReturnResult beforeCheckin() {
        if ( isBoxChecked() ) {
            List<Change> changes = getSelectedChangesToXls();
            return processChanges( changes ) ? ReturnResult.COMMIT : ReturnResult.CANCEL;
        }
        return super.beforeCheckin();
    }

    /**
     * Checks the versions of remote and local before "my" changes, if there is diff
     * Map.Entry<FilePath, Pair<localVersion, RemoteVersion>> added to the map
     * if there are diffs a pop up appears
     * @param changes
     * @return
     */
    private boolean processChanges(List<Change> changes) {
        Map<String, Pair<String, String>> diffs = new HashMap<>(); // Map<FilePath, Pair<localVersion, RemoteVersion>>
        for ( Change change : changes ) {
            VirtualFile localFile = Objects.requireNonNull( Objects.requireNonNull( change.getBeforeRevision() ).getFile().getVirtualFile() );
            String localRevisionBeforeChanges = change.getBeforeRevision().getRevisionNumber().asString();
            String remoteLatestVersion = getLatestRemoteVersionForTheChangedBinaryFile( localFile );
            if ( !localRevisionBeforeChanges.equals( remoteLatestVersion ) ) {
                diffs.put( localFile.getPath(), new Pair<>( localRevisionBeforeChanges, remoteLatestVersion) );
            }
        }
        logger.info( diffs.toString() );
        popUpHandler.setDiffs( diffs );
        popUpHandler.setPanel( panel );
        return popUpHandler.doPopUp();
    }

    /**
     * Runs a cmd process against remote repo.
     * @param localFile
     * @return String
     */
    @NotNull
    private String getLatestRemoteVersionForTheChangedBinaryFile(VirtualFile localFile) {
        File fileWorkDir = new File( localFile.getPath()
                .substring( 0, localFile.getPath().lastIndexOf( '/' ) ) ); // path without file name, cvs log -h should be executed there.
        List<String> cliArgs = constructCliArgsForLastHistoricChange( localFile.getName() );
        cvsOperator.setCliArgs( cliArgs );
        cvsOperator.setFileWorkDir( fileWorkDir );
        cvsOperator.doProcess();
        String remoteVersion = extractVersion( cvsOperator.getResult() );
        cvsOperator.refresh();
        return remoteVersion;
    }

    /**
     * Extracts version from a header.
     * @param remoteHistory
     * @return String
     */
    private String extractVersion( String remoteHistory ) {
        return remoteHistory.substring( remoteHistory.lastIndexOf(' ') + 1 );
    }

    /**
     * Constructs a cvs command which will be executed against a console to see do an operation in the remote repo.
     * @param fileName
     * @return List<String>
     */
    @NotNull
    private List<String> constructCliArgsForLastHistoricChange(String fileName) {
        return ContainerUtil.newArrayList( ( ExecArgs.CONSOLE ).getArg(),
                ( ExecArgs.RUN_AND_THEN_CLOSE ).getArg(),
                ( CvsCommands.LOG ).getCommand() + fileName );
    }

    /**
     * Returns a List of changes only for the selected( form the ui ) binary files.
     * @return List<Change>
     */
    @NotNull
    private List<Change> getSelectedChangesToXls() {
        return panel.getSelectedChanges()
                .stream()
                .filter( change -> "xls".equals( Objects.requireNonNull( Objects.requireNonNull( change.getBeforeRevision() )
                        .getFile()
                        .getVirtualFile() )
                        .getExtension() ) )
                .collect( Collectors.toList() );
    }

    public static Logger getLogger() {
        return logger;
    }

    public CheckinProjectPanel getPanel() {
        return panel;
    }

    public void setPanel(CheckinProjectPanel panel) {
        this.panel = panel;
    }

    public PopUpHandlerIf getPopUpHandler() {
        return popUpHandler;
    }

    public void setPopUpHandler(PopUpHandlerIf popUpHandler) {
        this.popUpHandler = popUpHandler;
    }

    public CvsOperatorIf getCvsOperator() {
        return cvsOperator;
    }

    public void setCvsOperator(CvsOperatorIf cvsOperator) {
        this.cvsOperator = cvsOperator;
    }

    public CommitContext getCommitContext() {
        return commitContext;
    }

    public void setCommitContext(CommitContext commitContext) {
        this.commitContext = commitContext;
    }
}
