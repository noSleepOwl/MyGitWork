package com.cn.sleep.git;

import org.eclipse.jgit.api.errors.GitAPIException;

public interface GitCommand {
    void run() throws GitAPIException;
}
