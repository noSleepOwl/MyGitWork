package com.cn.sleep.work.project;

import com.cn.sleep.work.Origin;
import com.cn.sleep.work.Project;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class JsonProject implements Project {

    private String name;
    private String path;
    private List<Origin> originList = new ArrayList<>();

    private Boolean control;

    public void setOriginList(List<Origin> originList) {
        this.originList = originList;
    }

    public void addOrign(Origin origin) {
        this.originList.add(origin);
    }

    public void setControl(Boolean control) {
        this.control = control;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPath(String path) {
        this.path = path;
    }


    @Override
    public Boolean getControl() {
        return control;
    }

    @Override
    public List getOriginList() {
        return originList;
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
    public boolean equals(Object obj) {
        if (Objects.isNull(obj)) {
            return false;
        }
        Project project = (Project) obj;
        return this
                .getName()
                .equals(project.getName()) &&
                path.equals(project.getName());
    }

    @Override
    public String toString() {
        return this.name;
    }
}
