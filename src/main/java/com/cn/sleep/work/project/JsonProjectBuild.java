package com.cn.sleep.work.project;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cn.sleep.work.ProjectInterface;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class JsonProjectBuild {
    private String jsonPath;
    private String json;
    private List<ProjectInterface> parseResult = new ArrayList<>();

    private JsonProjectBuild(String jsonPath) {
        this.jsonPath = jsonPath;
    }

    public static List<ProjectInterface> build(String jsonPath) {
        return new JsonProjectBuild(jsonPath).get();
    }

    private void getResource() {
        try (InputStream inputStream = this.getClass().getResourceAsStream("/" + jsonPath);
             InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
             BufferedReader bufferedReader = new BufferedReader(inputStreamReader);) {
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
        JSONObject jsonObject = JSON.parseObject(json);
        JSONArray jsonArray = jsonObject.getJSONArray("projects");
        for (int i = 0; i < jsonArray.size(); i++) {
            JsonProject jsonProject = jsonArray.getObject(i, JsonProject.class);
            parseResult.add(jsonProject);
        }

    }


    private List<ProjectInterface> get() {
        getResource();
        parseJson();
        return parseResult;
    }
}
