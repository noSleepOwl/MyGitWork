package com.cn.sleep.work.workinterface.impl;

import com.cn.sleep.work.workinterface.WorkFlowAbstract;
import org.eclipse.jgit.api.errors.GitAPIException;

import java.io.IOException;

public class WorkFlowImpl extends WorkFlowAbstract {


    @Override
    public void runWork() {
        try {
            addCommand(gitController.add());
            addCommand(gitController.commit("测试提交"));
            gitController.run(gitCommands);
        } catch (GitAPIException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
