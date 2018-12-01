package com.cn.sleep.work.project;

import com.cn.sleep.work.GitStatus;
import com.cn.sleep.work.ProjectInterface;

import java.util.List;
import java.util.Map;

public class JsonProject implements ProjectInterface {

    private String name;
    private String path;
    private List<Map<String, String>> origin;
    private Boolean isGit;
    private GitStatus gitStatus;

    public void setName(String name) {
        this.name = name;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setOrigin(List<Map<String, String>> origin) {
        this.origin = origin;
    }

    public Boolean getGit() {
        return isGit;
    }

    public void setGit(Boolean git) {
        isGit = git;
    }

    public void setGitStatus(GitStatus gitStatus) {
        this.gitStatus = gitStatus;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getPath() {
        return path;
    }

    @Override
    public List<Map<String, String>> getOrigin() {
        return origin;
    }

    @Override
    public Boolean isGit() {
        return null;
    }

    @Override
    public GitStatus getGitStatus() {
        return gitStatus;
    }
}
