package com.cn.sleep.work.workinterface;

import com.cn.sleep.git.GitCommand;
import com.cn.sleep.git.GitController;
import com.cn.sleep.work.ProjectInterface;
import com.cn.sleep.work.WorkFlowInterface;

import java.util.ArrayList;
import java.util.List;

public abstract class WorkFlowAbstract implements WorkFlowInterface {
    protected List<GitCommand> gitCommands = new ArrayList<>();
    protected ProjectInterface projectInterface;
    protected GitController gitController;

    @Override
    public void setGitController(GitController gitController) {
        this.gitController = gitController;
    }

    @Override
    public void setProjectInterface(ProjectInterface projectInterface) {
        this.projectInterface = projectInterface;
    }

    @Override
    public void addCommand(GitCommand gitCommand) {
        gitCommands.add(gitCommand);
    }
}
