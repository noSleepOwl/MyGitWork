package com.cn.sleep;

import com.cn.sleep.work.LogFilterMyCommit;
import com.cn.sleep.work.Origin;
import com.cn.sleep.work.Project;
import com.cn.sleep.work.project.JsonProjectTools;
import org.eclipse.jgit.api.PullResult;
import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.lib.PersonIdent;
import org.eclipse.jgit.revwalk.RevCommit;

import java.text.SimpleDateFormat;
import java.util.List;

public class GitMain {
    public static final String gitDir = "E:\\intellijWorkSpace\\My-Blog";
    public static final String CONFIG_JSON = "E:\\intellijWorkSpace\\MyGitWork\\src\\main\\resources\\project.json";
    public static final String MASTER = "master";
    public static final String REMOTE_CENTER = "center";
    public static final String REMOTE_ORIGIN = "origin";

    public static void main(String[] arg) {
        JsonProjectTools.searchProjectFromJson(CONFIG_JSON);
        List<Project> projects = JsonProjectTools.build(CONFIG_JSON);

    }

    private static String remote(Project project, String remoteName) {
        return project.getOriginList()
                .stream()
                .filter(o -> o.getName().equals(remoteName))
                .map(Origin::getUrl)
                .findFirst()
                .get();
    }


    public static String logMessage(RevCommit revCommit) {
        PersonIdent committer = revCommit.getCommitterIdent();
        String user = committer.getName();
        String msg = revCommit.getFullMessage().replace("\n", "").trim();
        String when = new SimpleDateFormat("yyyy年MM月dd日hh点").format(committer.getWhen());
        String email = committer.getEmailAddress();

        return String.format("\n\n%-25s%-20s%-18s\n       %s", when, user, email, msg);
    }

    public static void pullCenterToMaster(Project project) {
        new Thread(()-> RrpositoryTool.openGit(project.getPath(), tool -> {

            List<DiffEntry> entryList = tool.diff();
            //本地无更改
            if (entryList == null || entryList.size() == 0) {
                tool.checkout(MASTER);
                tool.pull(REMOTE_CENTER, MASTER);
                tool.push(REMOTE_ORIGIN);
            }

        })).start();
    }
}
