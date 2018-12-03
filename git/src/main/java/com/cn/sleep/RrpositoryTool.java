package com.cn.sleep;

import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;

import java.io.File;
import java.io.IOException;

public class RrpositoryTool {
    public static Repository buildTool(String path) throws IOException {
        FileRepositoryBuilder fileRepositoryBuilder = new FileRepositoryBuilder();
        return fileRepositoryBuilder
                .setGitDir(new File(path))
                .readEnvironment()
                .findGitDir()
                .build();
    }
}
