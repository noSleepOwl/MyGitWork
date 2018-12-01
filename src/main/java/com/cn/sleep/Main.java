package com.cn.sleep;

import com.cn.sleep.git.BaseGitTool;
import com.cn.sleep.git.GitController;
import com.cn.sleep.work.ProjectInterface;
import com.cn.sleep.work.WorkFlowInterface;
import com.cn.sleep.work.project.JsonProject;
import com.cn.sleep.work.project.JsonProjectBuild;
import com.cn.sleep.work.workinterface.impl.WorkFlowImpl;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.RebaseCommand;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.errors.IncorrectObjectTypeException;
import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.errors.StopWalkException;
import org.eclipse.jgit.lib.*;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.revwalk.filter.RevFilter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class Main {

    private Main() {
    }

    public static Main build() {
        return new Main();
    }

    public static void main(String[] arg) {
        List<ProjectInterface> projects = JsonProjectBuild.build("project.json");
        projects.forEach(o -> {
            GitController gc = new BaseGitTool(o.getPath());
            WorkFlowInterface workFlow = new WorkFlowImpl();
            workFlow.setProjectInterface(o);
            workFlow.setGitController(gc);
            workFlow.runWork();
        });

        System.out.println(projects);
    }

    public void writeFile(File file) {
        try (FileWriter fileWriter = new FileWriter(file)) {
            fileWriter.append("hello world_add\n");
            fileWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void rebase(Git git) throws GitAPIException {
        git.rebase().runInteractively(new RebaseCommand.InteractiveHandler() {
            @Override
            public void prepareSteps(List<RebaseTodoLine> steps) {
                for (RebaseTodoLine step : steps) {
                    System.out.println(step.getAction());
                    System.out.println(step.getComment());
                    System.out.println(step.getCommit());
                    System.out.println(step.getShortMessage());
                }
            }

            @Override
            public String modifyCommitMessage(String commit) {
                return "我成功的rebasel了一次";
            }
        }).call();
    }

    /**
     * 初始化本地仓库
     *
     * @param path 仓库地址
     * @throws GitAPIException git 异常
     */
    public void gitInit(String path) throws GitAPIException {
        File file = new File(path);
        Git.init().setGitDir(file).setDirectory(file.getParentFile()).call();
    }

    /**
     * 本地仓库配置
     *
     * @param path 路径
     */
    public void config(String path) {
        try (Repository build = new RepositoryBuilder().setGitDir(new File(path)).setMustExist(true).build()) {
            build.getConfig().setString(ConfigConstants.CONFIG_USER_SECTION, null, ConfigConstants.CONFIG_KEY_NAME, "XXXX");
            build.getConfig().save();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addCommand(Git git) throws GitAPIException {
        git.add().addFilepattern(".").call();

    }

    public void commitCommand(Git git, String message) throws GitAPIException {
        git.commit().setMessage(message).setAllowEmpty(true).call();
    }

    public void getOpen(GitCommend commend, String path) {
        try (Git git = Git.open(new File(path))) {
            commend.run(git);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void gitLog(Git git) throws GitAPIException {
        Iterable<RevCommit> results = git.log().setRevFilter(new RevFilter() {
            @Override
            public boolean include(RevWalk walker, RevCommit cmit) throws StopWalkException, MissingObjectException, IncorrectObjectTypeException, IOException {
                return true;
            }

            @Override
            public RevFilter clone() {
                return this;
            }
        }).call();

        results.forEach(commit -> {
            PersonIdent authoIdent = commit.getAuthorIdent();
            System.out.println("提交人：  " + authoIdent.getName() + "     <" + authoIdent.getEmailAddress() + ">");
            System.out.println("提交SHA1：  " + commit.getId().name());
            System.out.println("提交信息：  " + commit.getShortMessage());
            System.out.println("提交时间：  " + authoIdent.getWhen().toString());
        });
    }

    interface GitCommend {
        void run(Git git);
    }
}
