package com.cn.sleep;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.PullResult;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.errors.IncorrectObjectTypeException;
import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.errors.StopWalkException;
import org.eclipse.jgit.lib.Config;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.revwalk.filter.RevFilter;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.eclipse.jgit.transport.PushResult;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Set;

public class RrpositoryTool {

    public static Repository buildRep(String path) throws IOException {
        FileRepositoryBuilder fileRepositoryBuilder = new FileRepositoryBuilder();
        return fileRepositoryBuilder
                .setGitDir(new File(path))
                .readEnvironment()
                .findGitDir()
                .build();
    }


    public static void getRemoteName(String gitDir, GetNameCallBack callBack) {
        try (Repository repository = RrpositoryTool.buildRep(gitDir)) {
            Config storedConfig = repository.getConfig();
            Set<String> remotes = storedConfig.getSubsections("remote");
            for (String remoteName : remotes) {
                String url = storedConfig.getString("remote", remoteName, "url");
                callBack.call(remoteName, url);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void openGit(String path, GitOpenBack back) {
        try (Git git = new Git(buildRep(path))) {

            back.call(new RrpositoryTool(git));

        } catch (IOException | GitAPIException e) {
            e.printStackTrace();
        }
    }


    private Git git;

    private RrpositoryTool(Git git) {
        this.git = git;
    }


    public List<DiffEntry> diff() throws GitAPIException {
        return git.diff().call();
    }

    public Ref checkout(String branchName) throws GitAPIException {
        return git.checkout().setName(branchName).call();
    }

    public PullResult pull(String remote, String branchName) throws GitAPIException {
        return git.pull().setRemote(remote).setRemoteBranchName(branchName).call();
    }

    public Iterable<PushResult> push(String remote) throws GitAPIException {
        return git.push().setRemote(remote).call();
    }

    public Iterable<RevCommit> log(RevFilter revFilter) throws GitAPIException {
        return git.log().setRevFilter(revFilter).call();
    }

    public interface GetNameCallBack {
        void call(String name, String url);
    }

    public interface GitOpenBack {
        void call(RrpositoryTool tool) throws GitAPIException;
    }

}
