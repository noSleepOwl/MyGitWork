package com.cn.sleep.Ui;

import com.cn.sleep.GitMain;
import com.cn.sleep.RrpositoryTool;
import com.cn.sleep.work.LogFilterMyCommit;
import com.cn.sleep.work.Project;
import com.cn.sleep.work.project.JsonProjectTools;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.revwalk.RevCommit;

import java.net.URL;
import java.util.List;

import static com.cn.sleep.GitMain.CONFIG_JSON;


public class JavaFx extends Application {
    private ListView<Project> view;
    private TextArea textArea;
    private Button pullCenterToMaster;
    public static void main(String[] args) {
        Application.launch(JavaFx.class, args);
    }





    @Override
    public void start(Stage primaryStage) throws Exception {
        URL url = getClass().getResource("/MainView.fxml");
        Parent root = FXMLLoader.load(url);
        initView(root);
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("test window");
        primaryStage.show();

    }

    private void initView(Parent root) {
        view = (ListView<Project>) root.lookup("#projectList");
        textArea = (TextArea) root.lookup("#resultConsole");
        pullCenterToMaster = (Button) root.lookup("#pullCenterToMaster");


        List<Project> projects = JsonProjectTools.build(CONFIG_JSON);
        ObservableList<Project> projects1 = FXCollections.observableArrayList(projects);
        view.setItems(projects1);

        pullCenterToMaster.setOnAction(event -> {
            Project project = view.getSelectionModel().selectedItemProperty().getValue();
            if(project != null){
                GitMain.pullCenterToMaster(project);
            }

        });

        view.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            RrpositoryTool.openGit(newValue.getPath(), tool -> {
                List<DiffEntry> entryList = tool.diff();
                String msg = "";
                for (DiffEntry diffEntry : entryList) {
                    msg += diffString(diffEntry);
                }
                msg+="------------\n";
                for (RevCommit revCommit : tool.log(new LogFilterMyCommit())) {
                    msg+= GitMain.logMessage(revCommit) +"\n";
                }
                textArea.setText(msg);
            });
        });
    }


    private String diffString(DiffEntry diffEntry){
        DiffEntry.ChangeType changeType = diffEntry.getChangeType();
        StringBuilder buf = new StringBuilder();


        switch (changeType) {
            case ADD:
                buf.append("添加：");
                buf.append(" ");
                buf.append(diffEntry.getNewPath());
                break;
            case COPY:
                buf.append("复 制：");
                buf.append(" ");
                buf.append(diffEntry.getOldPath() + "->" + diffEntry.getNewPath());
                break;
            case DELETE:
                buf.append("删 除：");
                buf.append(" ");
                buf.append(diffEntry.getOldPath());
                break;
            case MODIFY:
                buf.append("更 新：");
                buf.append(" ");
                buf.append(diffEntry.getOldPath());
                break;
            case RENAME:
                buf.append("重命名：");
                buf.append(" ");
                buf.append(diffEntry.getOldPath() + "->" + diffEntry.getNewPath());
                break;
        }
        buf.append("\n");
        return buf.toString();
    }

}
