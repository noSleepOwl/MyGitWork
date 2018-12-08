package com.cn.sleep.Ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URL;


public class JavaFx extends Application {


    public static void main(String[] args) {

        Application.launch(JavaFx.class, args);
    }


    @Override
    public void start(Stage primaryStage) throws Exception {
        Application.setUserAgentStylesheet(STYLESHEET_CASPIAN);
        URL url = getClass().getResource("/MainView.fxml");

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(url);
        fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());

        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/myCss.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.setTitle("test window");
        primaryStage.show();

        MainController mainController = fxmlLoader.getController();
        mainController.init();

    }


}
