package com.cn.sleep.git;

import com.cn.sleep.work.WorkFlowInterface;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;

import java.io.Closeable;
import java.io.IOException;
import java.util.List;

/**
 * git 的一些操作
 */
public interface GitController extends Closeable {

    GitCommand add() ;

    GitCommand commit(String msg) ;

    GitCommand branchListAll() ;

    void openGit() throws IOException;

    void run(List<GitCommand> commands) throws IOException;


}
