package com.cn.sleep.work;

import java.util.List;

/**
 * 项目接口 项目的基础信息设置， 计划可以用 build 的方式来配置项目或者使用json 或者使用 xml 。。。。。
 */
public interface Project {
    /**
     * 项目名称
     */
    String getName();

    /**
     * 项目本地地址
     */
    String getPath();

    /**
     * 在批量操作中是否执行
     */
    Boolean getControl();

    void setControl(Boolean control);

    /**
     * 项目远程仓库地址
     */
    List<Origin> getOriginList();


}
