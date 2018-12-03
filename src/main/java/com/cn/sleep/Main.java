package com.cn.sleep;

import org.eclipse.jgit.lib.Config;
import org.eclipse.jgit.lib.Repository;

import java.io.IOException;
import java.util.Set;

/**
 * 1.项目git 地址,
 * 1.1.项目名称
 * 1.2.项目git地址
 * 2.工作目录
 * 2.1 项目本地目录
 * 3.1 项目中心仓库地址
 */
public class Main {
    public static final String gitDir = "E:\\intellijWorkSpace\\ICloudClassroomDev\\.git";

    public static void main(String[] arg) {
        try (Repository repository = RrpositoryTool.buildTool(gitDir)) {
            Config storedConfig = repository.getConfig();
            Set<String> remotes = storedConfig.getSubsections("remote");
            for (String remoteName : remotes) {
                String url = storedConfig.getString("remote", remoteName, "url");
                System.out.println(remoteName + " " + url);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}
