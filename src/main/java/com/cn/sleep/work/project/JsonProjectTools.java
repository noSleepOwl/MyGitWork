package com.cn.sleep.work.project;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cn.sleep.RrpositoryTool;
import com.cn.sleep.work.Project;

import java.io.*;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class JsonProjectTools {
    private String jsonPath;
    private String json;
    private JSONObject jsonObject;
    private List<Project> parseResult = new ArrayList<>();
    public static final String PROJECTS = "projects";
    public static final String WORK_SPACE = "workSpace";

    private JsonProjectTools(String jsonPath) {
        this.jsonPath = jsonPath;
        getResource();
        readJson();
        parseJson();
    }

    /**
     * 读取项目信息
     */
    public static List<Project> build(String jsonPath) {
        return new JsonProjectTools(jsonPath).get();
    }

    public static void searchProjectFromJson(String jsonPath) {
        try {
            JsonProjectTools tools = new JsonProjectTools(jsonPath);
            List<Project> projects = tools.searchWorkSpace();
            tools.setProjectJson(projects);
            tools.writeResource();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void writeResource() {
        File file = new File(jsonPath);
        try (FileWriter fileWriter = new FileWriter(file);
             BufferedWriter writer = new BufferedWriter(fileWriter)) {
            String json = JSONObject.toJSONString(jsonObject, true);
            writer.write(json);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    private void getResource() {
        File file = new File(jsonPath);

        try (FileReader inputStream = new FileReader(file);
             BufferedReader bufferedReader = new BufferedReader(inputStream)) {
            String line = null;
            StringBuffer stringBuffer = new StringBuffer();
            while ((line = bufferedReader.readLine()) != null) {
                stringBuffer.append(line);
            }
            this.json = stringBuffer.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void parseJson() {
        JSONArray jsonArray = jsonObject.getJSONArray(PROJECTS);
        for (int i = 0; i < jsonArray.size(); i++) {
            JsonProject jsonProject = jsonArray.getObject(i, JsonProject.class);
            parseResult.add(jsonProject);
        }
    }

    private void readJson() {
        jsonObject = JSON.parseObject(json);
    }

    private void setProjectJson(List<Project> projects) {
        projects = projects
                .stream()
                .filter(o -> parseResult.stream().noneMatch(pro -> pro.equals(o)))
                .map(o -> {
                    o.setControl(false);
                    return o;
                })
                .collect(Collectors.toList());
        projects.addAll(parseResult);
        String projectsJson = JSON.toJSONString(projects);

        JSONArray projectsJsonObject = JSON.parseArray(projectsJson);

        jsonObject.put(PROJECTS, projectsJsonObject);

    }

    private List<Project> get() {
        return parseResult;
    }

    private List<Project> searchWorkSpace() throws IOException {
        Path workSpace = Paths.get(jsonObject.getString(WORK_SPACE));
        List<Project> projects = new ArrayList<>();
        Path treePath = Files.walkFileTree(workSpace, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {

                if (dir.getNameCount() > (workSpace.getNameCount() + 2)) {
                    return FileVisitResult.SKIP_SIBLINGS;
                }

                if (!dir.endsWith(".git")) {
                    return FileVisitResult.CONTINUE;
                } else {
                    projects.add(parseDirToProject(dir));
                }
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                return FileVisitResult.SKIP_SIBLINGS;
            }
        });

        return projects;
    }

    private Project parseDirToProject(Path dir) {
        JsonProject jsonProject = new JsonProject();
        String name = dir.getName(dir.getNameCount() - 2).toString();
        jsonProject.setName(name);
        jsonProject.setControl(false);
        jsonProject.setPath(dir.toString());
        RrpositoryTool.getRemoteName(dir.toString(), (remoteName, url) -> {
            OriginModel originModel = new OriginModel();
            originModel.setName(remoteName);
            originModel.setUrl(url);
            jsonProject.addOrign(originModel);
        });
        return jsonProject;
    }

}
