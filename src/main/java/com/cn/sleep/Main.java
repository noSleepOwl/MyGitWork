package com.cn.sleep;

import com.cn.sleep.work.Origin;
import com.cn.sleep.work.Project;
import com.cn.sleep.work.project.JsonProjectTools;
import org.eclipse.jgit.api.PullResult;
import org.eclipse.jgit.diff.DiffEntry;

import java.util.List;

public class Main {
    public static final String gitDir = "E:\\intellijWorkSpace\\My-Blog";
    public static final String CONFIG_JSON = "E:\\intellijWorkSpace\\MyGitWork\\src\\main\\resources\\project.json";
    public static final String MASTER = "master";
    public static final String REMOTE_CENTER = "center";
    public static final String REMOTE_ORIGIN = "origin";

    public static void main(String[] arg) {
        JsonProjectTools.searchProjectFromJson(CONFIG_JSON);
        List<Project> projects = JsonProjectTools.build(CONFIG_JSON);

        projects.stream().filter(Project::getControl).forEach(project -> {
            RrpositoryTool.openGit(project.getPath(), tool -> {
                List<DiffEntry> entryList = tool.diff();
                //本地无更改
                if (entryList.size() == 0) {
                    tool.checkout(MASTER);
                    PullResult result = tool.pull(REMOTE_CENTER, MASTER);
                    System.out.println(result.isSuccessful());
                }
            });
        });
    }

    private static String remote(Project project, String remoteName) {
        return project.getOriginList()
                .stream()
                .filter(o -> o.getName().equals(remoteName))
                .map(Origin::getUrl)
                .findFirst()
                .get();
    }

}
