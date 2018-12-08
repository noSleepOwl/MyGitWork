package com.cn.sleep.Ui;

import com.cn.sleep.work.Project;
import javafx.scene.Node;
import javafx.scene.control.ListCell;

public class ProjectListCell extends ListCell<Project> {
    private Node node;
    private boolean isCheck = false;

    public ProjectListCell(Node node) {
        this.node = node;
    }


    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }


    @Override
    protected void updateItem(Project item, boolean empty) {
        super.updateItem(item, empty);
        if (empty || item == null) {
            setText(null);
            setGraphic(null);
        } else {
            setText(item.getName());
            setGraphic(node);
        }
    }

}
