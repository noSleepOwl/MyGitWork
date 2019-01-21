package com.cn.sleep.work;

import org.eclipse.jgit.errors.IncorrectObjectTypeException;
import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.errors.StopWalkException;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.revwalk.filter.RevFilter;

import java.io.IOException;

public class LogFilterMyCommit extends RevFilter {
    public static final String E_MAIN = "844910815@qq.com";
    private Iterable<RevCommit> localCommit;

    @Override
    public boolean include(RevWalk walker, RevCommit cmit)
            throws StopWalkException, MissingObjectException, IncorrectObjectTypeException, IOException {
        boolean isMine = cmit.getCommitterIdent().getEmailAddress().equals(E_MAIN);
        // 本人提交
        if (!isMine) {
            return false;
        }
        // 本地存在
        if (localCommit != null) {
            for (RevCommit revCommit : localCommit) {
                if (revCommit.equals(cmit)) {
                    return false;
                }
            }
            return true;
        }
        return true;
    }

    @Override
    public RevFilter clone() {
        return null;
    }

    public void setLocalCommit(Iterable<RevCommit> localCommit) {
        this.localCommit = localCommit;
    }
}
