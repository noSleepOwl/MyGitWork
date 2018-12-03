package com.cn.sleep.work.project;

import com.cn.sleep.work.Origin;
import com.cn.sleep.work.ProjectInterface;

public class JsonProject implements ProjectInterface {

    private String name;
    private String path;
    private Origin origin;


    public void setName(String name) {
        this.name = name;
    }

    public void setPath(String path) {
        this.path = path;
    }


    @Override
    public Origin getOrigin() {
        return origin;
    }

    @Override
    public Boolean control() {
        return false;
    }

    public void setOrigin(Origin origin) {
        this.origin = origin;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getPath() {
        return path;
    }


}
