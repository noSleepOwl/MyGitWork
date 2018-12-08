package com.cn.sleep.Ui;

import com.cn.sleep.GitMain;
import com.cn.sleep.work.Origin;
import com.cn.sleep.work.Project;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Side;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MainController {
    @FXML
    private BorderPane baseLayout;

    @FXML
    private ListView<Project> projectListView;


    void init() {
        ObservableList<Project> projects = FXCollections.observableArrayList(GitMain.getPorjects());
        projectListView.setCellFactory(view -> {
            CheckBox checkBox = new CheckBox();
            Label label = new Label("测试标签");

            HBox hBox = new HBox();
            hBox.getChildren().addAll(checkBox, label);


            ProjectListCell projectListCell = new ProjectListCell(hBox);

            checkBox.selectedProperty().addListener((value, old, now) -> {
                projectListCell.setCheck(now);
            });

            return projectListCell;
        });

        projectListView.getSelectionModel().selectedItemProperty().addListener((view, old, now) -> {
            Platform.runLater(() -> createLabel(now));
        });

        projectListView.setItems(projects);


    }

    private void createLabel(Project now) {
        baseLayout.setCenter(null);

        TabPane tabPane = new TabPane();
        tabPane.setSide(Side.TOP);
        Map<String, List<Path>> pathMap = GitMain.branchListAll(now);
        for (int i = 0; i < now.getOriginList().size(); i++) {
            Origin origin = null;
            if (i == now.getOriginList().size()) {
                origin = new Origin() {
                    @Override
                    public String getName() {
                        return "heads";
                    }

                    @Override
                    public String getUrl() {
                        return null;
                    }
                };
            } else {
                origin = now.getOriginList().get(i);
            }
            Tab tab = new Tab(origin.getName());
            VBox vBox = new VBox();
            List<Label> labels = new ArrayList<>();

            for (Map.Entry<String, List<Path>> ent : pathMap.entrySet()) {
                String key = ent.getKey();
                List<Path> paths = ent.getValue();
                if (key.equals(origin.getName())) {
                    for (Path path : paths) {
                        labels.add(new Label(path.toString()));
                    }
                }
            }


            vBox.getChildren().addAll(labels);

            tab.setContent(vBox);
            tabPane.getTabs().add(tab);
        }


        baseLayout.setCenter(tabPane);
    }


}
