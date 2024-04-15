package com.operatingsystem.operaingsystems;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import eu.iamgio.animated.transition.AnimatedThemeSwitcher;
import eu.iamgio.animated.transition.animations.clip.CircleClipOut;
import eu.iamgio.animated.transition.container.AnimatedHBox;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import javafx.util.Duration;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;
import java.util.ResourceBundle;


public class RRController implements Initializable {
    @FXML
    private AnchorPane mainPane;
    @FXML
    private TableColumn<Process, String> ATCol;
    @FXML
    private TableColumn<Process, String> PNCol;

    @FXML
    private TableColumn<Process, String> RBTCol;

    @FXML
    private ColorPicker ColorField;

    @FXML
    private TableColumn<Process, String> colorCol;

    @FXML
    private TableColumn<Process, String> BTCol;

    @FXML
    private TableView<Process> Table;

    @FXML
    private TextField arrivalTimeField;

    @FXML
    private TextField burstTimeField;

    @FXML
    private TextField processNameField;

    @FXML
    private JFXButton runBtn;
    private boolean isRunning = false;
    @FXML
    private JFXButton ResetBtn;
    @FXML
    private AnimatedHBox hboxColor;
    @FXML
    private AnimatedHBox hboxText;
    @FXML
    private JFXCheckBox liveSimulation;
    @FXML
    private JFXCheckBox autoColoring;
    @FXML
    private Text cpuStatus;
    @FXML
    private Text averageTA;
    @FXML
    private Text averageWT;
    ObservableList<Process> processesList;
    private Timeline timeLine;
    private int counterSeconds = 0;
    private ArrayList<Process> temp;
    private int maxSeconds;
    private ArrayList<Process> secondsArray = new ArrayList<>();
    double xOffset;
    double yOffset;

    @FXML
    void resetBtnClicked(MouseEvent event) {
        runBtn.setDisable(false);
        ResetBtn.setDisable(true);
        hboxText.getChildren().clear();
        hboxColor.getChildren().clear();
        timeLine.stop();
        isRunning = false;
        counterSeconds = 0;
        temp.clear();
        secondsArray.clear();
        cpuStatus.setText("Idle");
        averageTA.setText("0");
        averageWT.setText("0");
        Table.setItems(FXCollections.observableArrayList(temp));
        Table.refresh();
    }
    @FXML
    void runBtnClicked(MouseEvent event) {
        runBtn.setDisable(true);
        ResetBtn.setDisable(false);
        maxSeconds = (int)temp.get(temp.size()-1).getActualTime() + (int)temp.get(temp.size()-1).getBurstTime();
        if(liveSimulation.isSelected()){
            plot(1);
        }else{
            plot(0.01);
        }
        isRunning = true;
    }
    @FXML
    void addProcessBtnClicked() {
        if(!arrivalTimeField.getText().isEmpty() || !burstTimeField.getText().isEmpty() || !processNameField.getText().isEmpty()){
            if(isRunning){
                if(Integer.parseInt(arrivalTimeField.getText()) >= counterSeconds){
                    addProcess();
                }else{
                    System.out.println("Invalid arrival time");
                }
            }else {
                addProcess();
            }
        }
    }
    @FXML
    void mainPaneDragged(MouseEvent event) {
        Stage stage = (Stage) mainPane.getScene().getWindow();
        stage.setX(event.getScreenX() - xOffset);
        stage.setY(event.getScreenY() - yOffset);
    }
    @FXML
    void switchBtnClicked(){
        if(MainControllerUI.isDark){
            mainPane.getScene().getStylesheets().clear();
            mainPane.getScene().getStylesheets().add(getClass().getResource("/styles/light.css").toExternalForm());
        }else{
            mainPane.getScene().getStylesheets().clear();
            mainPane.getScene().getStylesheets().add(getClass().getResource("/styles/dark.css").toExternalForm());
        }
        MainControllerUI.isDark = !MainControllerUI.isDark;
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
    @FXML
    void autoColorClicked() {
        if(autoColoring.isSelected()){
            ColorField.setDisable(true);
        }else{
            ColorField.setDisable(false);
        }
    }
    @FXML
    void closeBtnClicked() {
        Platform.exit();
    }
    @FXML
    void returnBackBtn() throws IOException {
        displayFXML("mainui-view.fxml");
    }
    @FXML
    void processNameFieldKey(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            arrivalTimeField.requestFocus();
        }
    }
    @FXML
    void arrivalTimeFieldKey(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            burstTimeField.requestFocus();
        }
    }

    private void displayFXML(String fxml) throws IOException {
        mainPane.getScene().getWindow().hide();
        Parent root = FXMLLoader.load(getClass().getResource(fxml));
        Scene scene = new Scene(root);
        if(MainControllerUI.isDark){
            scene.getStylesheets().add(getClass().getResource("/styles/dark.css").toExternalForm());
        }else{
            scene.getStylesheets().add(getClass().getResource("/styles/light.css").toExternalForm());
        }
        AnimatedThemeSwitcher themeSwitcher = new AnimatedThemeSwitcher(scene, new CircleClipOut());
        themeSwitcher.init();
        Stage stage =new Stage();
        stage.setResizable(false);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(scene);
        stage.show();
    }
    private void addProcess(){
        Process p;
        if(autoColoring.isSelected()){
            p = new Process(processNameField.getText(), Double.parseDouble(arrivalTimeField.getText()), Double.parseDouble(burstTimeField.getText()), generateRandomColor().toString().substring(2));
        }else{
            p = new Process(processNameField.getText(), Double.parseDouble(arrivalTimeField.getText()), Double.parseDouble(burstTimeField.getText()), ColorField.getValue().toString().substring(2));
        }
        Table.getItems().add(p);
        sortProcesses();
        secondsArray.clear();
        secondsArray = RR.getRRSecondsArray(temp);
        updateAverage(temp);
    }
    private void updateAverage(ArrayList<Process> processes){
        double sumTA = 0;
        double sumWT = 0;
        for(Process p :processes){
            sumTA += p.getTurnAroundTime();
            sumWT += p.getWaitingTime();
        }
        averageTA.setText(String.format("%.2f", sumTA / processes.size()));
        averageWT.setText(String.format("%.2f", sumWT / processes.size()));
    }
    private void sortProcesses(){
        processesList = Table.getItems();
        temp = new ArrayList<>(processesList);
        Process.sort(temp);
        Process.calculateActualStartTimes(temp);
        Table.setItems(FXCollections.observableArrayList(temp));
        maxSeconds = (int)temp.get(temp.size()-1).getActualTime() + (int)temp.get(temp.size()-1).getBurstTime();
    }
    private Color generateRandomColor() {
        Random random = new Random();
        double red = random.nextDouble();
        double green = random.nextDouble();
        double blue = random.nextDouble();
        return new Color(red, green, blue, 1.0);
    }
    private void plot(double speed){
        Label l = new Label("0");
        l.setPrefWidth(4);
        hboxText.getChildren().add(l);
        timeLine = new Timeline();
        counterSeconds = 1;
        timeLine.getKeyFrames().add(new KeyFrame(Duration.seconds(speed), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if (counterSeconds < maxSeconds + 1) {
                    /* Adding the bar */
                    Pane bar = new Pane();
                    bar.setPrefHeight(80);
                    bar.setPrefWidth(25);
                    Process current = secondsArray.get(counterSeconds-1);
                    bar.setStyle("-fx-background-color:" + (current != null ? "#" + secondsArray.get(counterSeconds-1).getProcessColor():"transparent") + ";-fx-border-color:white");
                    hboxColor.getChildren().add(bar);
                    if(current != null){
                        cpuStatus.setText(secondsArray.get(counterSeconds-1).getName());
                        current.setRemainingBurstTime(current.getRemainingBurstTime() - 1);
                    }else{
                        cpuStatus.setText("Idle");
                    }
                    Table.refresh();
                    /* Adding the label */
                    Label l = new Label(Integer.toString(counterSeconds));
                    if(counterSeconds == 10){
                        l.setPrefWidth(27);
                    }else{
                        l.setPrefWidth(25);
                    }
                    l.setAlignment(Pos.CENTER_RIGHT);
                    hboxText.getChildren().add(l);
                    counterSeconds++;
                }else {
                    // Stop the timeline when all panes are added
                    timeLine.stop();
                }
            }
        }));
        timeLine.setCycleCount(Timeline.INDEFINITE);
        timeLine.play();
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ATCol.setCellValueFactory(new PropertyValueFactory<Process, String>("arrivalTime"));
        PNCol.setCellValueFactory(new PropertyValueFactory<Process, String>("name"));
        RBTCol.setCellValueFactory(new PropertyValueFactory<Process, String>("RemainingBurstTime"));
        BTCol.setCellValueFactory(new PropertyValueFactory<Process, String>("burstTime"));
        // Set color pane cell factory
        Callback<TableColumn<Process, String>, TableCell<Process, String>> colorCellFactory = (TableColumn<Process, String> param) -> {
            final TableCell<Process, String> cell = new TableCell<Process, String>() {
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                        setText(null);
                    } else {
                        Process process = getTableView().getItems().get(getIndex()); // Get the process for this row
                        Pane colorPane = new Pane();
                        colorPane.setPrefWidth(1);
                        colorPane.setPrefHeight(1);
                        colorPane.setStyle("-fx-background-color: #" + process.getProcessColor()); // Use process color
                        setGraphic(colorPane);
                        setText(null);
                    }
                }
            };
            return cell;
        };
        colorCol.setCellFactory(colorCellFactory);
    }

}
