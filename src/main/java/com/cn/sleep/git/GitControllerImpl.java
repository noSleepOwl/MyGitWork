package com.cn.sleep.git;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;

import java.io.File;
import java.io.IOException;
import java.util.List;

public abstract class GitControllerImpl implements GitController {
    protected Git git;
    private String path;

    GitControllerImpl(String path) {
        this.path = path;
    }

    @Override
    public void openGit() throws IOException {
        this.git = Git.open(new File(path));

    }

    @Override
    public void close() {
        if (git != null)
            git.close();
    }

    @Override
    public final void run(List<GitCommand> commands) {
        try {
            openGit();
            commands.forEach(o -> {
                try {
                    o.run();
                } catch (GitAPIException e) {
                    e.printStackTrace();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            close();
        }
    }
}
