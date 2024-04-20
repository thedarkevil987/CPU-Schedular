package com.operatingsystem.operatingsystems;

import com.jfoenix.controls.JFXButton;
import eu.iamgio.animated.transition.AnimatedThemeSwitcher;
import eu.iamgio.animated.transition.animations.clip.CircleClipOut;
import javafx.application.Platform;
import javafx.fxml.FXML;

import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class MainControllerUI {
    public static boolean isDark = true;
    @FXML
    private JFXButton FCFSBtn;
    @FXML
    private JFXButton PriorityBtn;
    @FXML
    private JFXButton RRBtn;
    @FXML
    private JFXButton SJFBtn;
    @FXML
    private AnchorPane rootPane;
    @FXML
    private AnchorPane titlePane;
    @FXML
    private AnchorPane mainPane;
    double xOffset;
    double yOffset;
    @FXML
    void FCFSBtnClicked(MouseEvent event) throws IOException {
        displayFXML("FCFS-view.fxml");
    }

    @FXML
    void PriorityBtnClicked(MouseEvent event) throws IOException {
        displayFXML("Priority-view.fxml");
    }

    @FXML
    void RRBtnClicked(MouseEvent event) throws IOException {
        displayFXML("RR-view.fxml");
    }

    @FXML
    void SJFBtnClicked(MouseEvent event) throws IOException {
        displayFXML("SJF-view.fxml");
    }
    private void displayFXML(String fxml) throws IOException {
        mainPane.getScene().getWindow().hide();
        Parent root = FXMLLoader.load(getClass().getResource(fxml));
        Scene scene = new Scene(root);
        if(isDark){
            scene.getStylesheets().add(getClass().getResource("/styles/dark.css").toExternalForm());
        }else{
            scene.getStylesheets().add(getClass().getResource("/styles/light.css").toExternalForm());
        }
        AnimatedThemeSwitcher themeSwitcher = new AnimatedThemeSwitcher(scene, new CircleClipOut());
        themeSwitcher.init();
        Stage stage =new Stage();
        stage.getIcons().add(new Image(getClass().getResource("/com/operatingsystem/operatingsystems/cpu.png").toExternalForm()));
        stage.setResizable(false);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    void switchBtnClicked(MouseEvent event){
        if(isDark){
            mainPane.getScene().getStylesheets().clear();
            mainPane.getScene().getStylesheets().add(getClass().getResource("/styles/light.css").toExternalForm());
        }else{
            mainPane.getScene().getStylesheets().clear();
            mainPane.getScene().getStylesheets().add(getClass().getResource("/styles/dark.css").toExternalForm());
        }
        isDark = !isDark;
    }
    @FXML
    void closeBtnClicled(MouseEvent event) {
        Platform.exit();
    }

    @FXML
    void mainPaneDragged(MouseEvent event) {
        Stage stage = (Stage) mainPane.getScene().getWindow();
        stage.setX(event.getScreenX() - xOffset);
        stage.setY(event.getScreenY() - yOffset);
    }
    @FXML
    void mainPanePressed(MouseEvent event) {
        xOffset = event.getSceneX();
        yOffset = event.getSceneY();
        mainPane.setCursor(Cursor.CLOSED_HAND);
    }
    @FXML
    void mainPaneReleased(){
        mainPane.setCursor(Cursor.DEFAULT);
    }

}
