package com.cn.sleep.work;

import com.cn.sleep.git.GitCommand;
import com.cn.sleep.git.GitController;

/**
 * 工作流接口 所有的工作流程都实现他
 */
public interface WorkFlowInterface {
    void setProjectInterface(ProjectInterface projectInterface);

    void setGitController(GitController gitController);

    void runWork();

    void addCommand(GitCommand gitCommand);
}
