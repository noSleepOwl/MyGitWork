package com.cn.sleep;

import com.cn.sleep.work.LogFilterMyCommit;
import com.cn.sleep.work.Origin;
import com.cn.sleep.work.Project;
import com.cn.sleep.work.project.JsonProjectTools;
import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.lib.PersonIdent;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.revwalk.RevCommit;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class GitMain {
    public static final String gitDir = "E:\\intellijWorkSpace\\My-Blog";
    public static final String CONFIG_JSON = "E:\\intellijWorkSpace\\MyGitWork\\src\\main\\resources\\project.json";
    public static final String MASTER = "master";
    public static final String REMOTE_CENTER = "center";
    public static final String REMOTE_ORIGIN = "origin";
    public static final String REMOTE_CENTER_MASTER = "center/master";


    public static void main(String[] arg) {

        List<Project> projects = JsonProjectTools.build(CONFIG_JSON);

    }

    public static void searchProjectFromJson() {
        JsonProjectTools.searchProjectFromJson(CONFIG_JSON);
    }

    public static List<Project> getPorjects() {
        return JsonProjectTools.build(CONFIG_JSON);
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
        RrpositoryTool.openGit(project.getPath(), tool -> {

            List<DiffEntry> entryList = tool.diff();
            //本地无更改
            if (entryList == null || entryList.size() == 0) {
                tool.checkout(MASTER);
                tool.pull(REMOTE_CENTER, MASTER);
                tool.push(REMOTE_ORIGIN, MASTER);
            }
            return null;
        });
    }

    public static void openGitThread(Project project, RrpositoryTool.GitOpenBack back) {
        RrpositoryTool.openGitThread(project.getPath(), back);
    }

    public static void openGit(Project project, RrpositoryTool.GitOpenBack back) {
        RrpositoryTool.openGit(project.getPath(), back);
    }

    public static Map<String, List<Path>> branchListAll(Project project) {
        List<Ref> refs = RrpositoryTool.openGit(project.getPath(), RrpositoryTool::branchAllList);
        for (Ref ref : refs) {
            System.out.println(ref.getName());
        }
        return refs.stream().map(o -> Paths.get(o.getName()))
                .collect(Collectors.groupingBy(o -> o.getName(2).toString()));

    }


    public static Iterable<RevCommit> localNoCommit(Project project) {
        return RrpositoryTool.openGit(project.getPath(), tool -> {
            LogFilterMyCommit revFilter = new LogFilterMyCommit();

            tool.fetch(REMOTE_CENTER);
            // 本地我的提交
            Iterable<RevCommit> localCommit = tool.log(revFilter);
            revFilter.setLocalCommit(localCommit);
            // center 中我的提交
            Iterable<RevCommit> centerCommit = tool.log(REMOTE_CENTER_MASTER, revFilter);
            return centerCommit;
        });
    }


}
