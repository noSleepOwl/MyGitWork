package com.cn.sleep.git;

import org.eclipse.jgit.api.ListBranchCommand;

public class BaseGitTool extends GitControllerImpl {


    public BaseGitTool(String path) {
        super(path);
    }

    @Override
    public GitCommand add() {
        return () -> git.add().addFilepattern(".").call();
    }

    @Override
    public GitCommand commit(String msg) {
        return () -> git.commit().setMessage(msg).call();
    }

    @Override
    public GitCommand branchListAll() {
        return () -> git.branchList().setListMode(ListBranchCommand.ListMode.ALL).call();
    }

}
