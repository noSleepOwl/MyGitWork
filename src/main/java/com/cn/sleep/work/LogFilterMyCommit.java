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

    @Override
    public boolean include(RevWalk walker, RevCommit cmit)
            throws StopWalkException, MissingObjectException, IncorrectObjectTypeException, IOException {
        return cmit.getCommitterIdent().getEmailAddress().equals(E_MAIN);
    }

    @Override
    public RevFilter clone() {
        return null;
    }
}
