package com.evbelcompany.motivationautomat;


import com.evbelcompany.motivationautomat.models.Motivator;
import com.evbelcompany.motivationautomat.models.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class RealizedMotivationsController implements Initializable {
    private Stage STAGE = new Stage();
    private Main main;
    private Task task;
    private Motivator motivator;

    private final MainController mainController = new MainController();
    private RealizedMotivationsController realizedMotivationsController;

    @FXML
    private TableView<Task> completedTaskTable;

    @FXML
    private TableColumn<Task, Integer>  completedTaskIdColumn;

    @FXML
    private TableColumn<Task, String>  completedTaskNameColumn;

    @FXML
    private TableColumn<Task, Integer>  completedTaskPointsColumn;

    @FXML
    private TableView<Motivator> completedMotivatorTable;

    @FXML
    private TableColumn<Motivator, String> completedMotivatorName;

    @FXML
    private TableColumn<Motivator, Integer> completedMotivatorPoints;

    @FXML
    private Button cancelReportForm;

    @FXML
    Label counterCompletedTask;

    @FXML
    Label counterCompletedMotivations;

    public void setMain(Main main){
        this.main = main;
    }

    public void initializeRealizedMotivationsFrom() throws IOException {
        Parent ROOT = FXMLLoader.load(RealizedMotivationsController.class.getResource("RealizedMotivationsForm.fxml"));
        Scene SCENE = new Scene(ROOT,600,700);
        STAGE.setScene(SCENE);
        STAGE.setTitle("Отчет");
        STAGE.initStyle(StageStyle.UNDECORATED);
        STAGE.getIcons().add(new Image(Main.class.getResourceAsStream("icons/iconAppLarge.png")));
        STAGE.showAndWait();
    }

    public void startContextMenu() {
        ContextMenu contextMenuTasks = new ContextMenu();

        MenuItem item1 = new MenuItem("Отменить");
        MenuItem item2 = new MenuItem("Удалит");

        item1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                int selectedIndex = completedTaskTable.getSelectionModel().getSelectedIndex();
                if (selectedIndex >= 0 ) {
                        task = mainController.getCompletedTasksDataReport().get(selectedIndex);
                        task.setStatus("CURRENT");
                        mainController.addCurrentTasksData(task);
                        completedTaskTable.getItems().remove(selectedIndex);
                        System.out.println("Task was canceled!");
                        getCounterPointsCompleted();
                    }
                else {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Внимание!");
                    alert.setHeaderText("Не выбрана ни одна задача для отмены!");
                    alert.setContentText("Пожалуйста, выбери из таблицы задачу для отмены.");
                    alert.showAndWait();
                }
            }
        });

        item2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                int selectedIndex = completedTaskTable.getSelectionModel().getSelectedIndex();
                if (selectedIndex >= 0 ) {
                    task = mainController.getCompletedTasksDataReport().get(selectedIndex);
                    task.setStatus("REMOVED");
                    completedTaskTable.getItems().remove(selectedIndex);
                    System.out.println("Task was removed!");
                    getCounterPointsCompleted();
                }
                else {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Внимание!");
                    alert.setHeaderText("Не выбрана ни одна задача для удаления!");
                    alert.setContentText("Пожалуйста, выбери из таблицы задачу для удаления.");
                    alert.showAndWait();
                }
            }
        });

        ContextMenu contextMenuMotivators = new ContextMenu();

        MenuItem menuItem1 = new MenuItem("Отменить");
        MenuItem menuItem2 = new MenuItem("Удалить");

        menuItem1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                int selectedIndex = completedMotivatorTable.getSelectionModel().getSelectedIndex();
                if (selectedIndex >= 0 ) {
                    motivator = mainController.getCompletedMotivatorsDataReport().get(selectedIndex);
                    motivator.setStatus("NEW");
                    mainController.addMotivatorsData(motivator);
                    completedMotivatorTable.getItems().remove(selectedIndex);
                    System.out.println("Motivation was canceled!");
                    getCounterPointsCompleted();
                }
                else {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Внимание!");
                    alert.setHeaderText("Не выбрана ни одна мотивация для отмены!");
                    alert.setContentText("Пожалуйста, выбери из таблицы мотивацию для отмены.");
                    alert.showAndWait();
                }
            }
        });
        menuItem2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                int selectedIndex = completedMotivatorTable.getSelectionModel().getSelectedIndex();
                if (selectedIndex >= 0 ) {
                    motivator = mainController.getCompletedMotivatorsDataReport().get(selectedIndex);
                    motivator.setStatus("REMOVED");
                    completedMotivatorTable.getItems().remove(selectedIndex);
                    System.out.println("Motivation was removed!");
                    getCounterPointsCompleted();
                }
                else {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Внимание!");
                    alert.setHeaderText("Не выбрана ни одна мотивация для удаления!");
                    alert.setContentText("Пожалуйста, выбери из таблицы мотивацию для удаления.");
                    alert.showAndWait();
                }
            }
        });

        contextMenuTasks.getItems().addAll(item1, item2);
        contextMenuMotivators.getItems().addAll(menuItem1, menuItem2);

        completedTaskTable.setContextMenu(contextMenuTasks);
        completedMotivatorTable.setContextMenu(contextMenuMotivators);

    }

    public void btnCancelReportForm(ActionEvent actionEvent) {
        STAGE = (Stage) cancelReportForm.getScene().getWindow();
        System.out.println("Cancel the report form");
        STAGE.close();
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        completedTaskTable.setEditable(true);
        completedTaskNameColumn.setCellValueFactory(new PropertyValueFactory<Task, String>("task"));
        completedTaskPointsColumn.setCellValueFactory(new PropertyValueFactory<Task, Integer>("point"));
        completedTaskTable.setItems(mainController.getCompletedTasksDataReport());


        completedMotivatorTable.setEditable(true);
        completedMotivatorName.setCellValueFactory(new PropertyValueFactory<Motivator, String>("motivator"));
        completedMotivatorPoints.setCellValueFactory(new PropertyValueFactory<Motivator, Integer>("points"));
        completedMotivatorTable.setItems(mainController.getCompletedMotivatorsDataReport());

        getCounterPointsCompleted();
        startContextMenu();
        }

    public void getCounterPointsCompleted() {
        int counterPointsCompletedTask  = 0;
        int counterPointsCompletedMotivations = 0;

        for (Task task : mainController.getCompletedTasksDataReport()) {
            if (task != null & task.getStatus().contains("FINISHED")) {
                counterPointsCompletedTask += task.getPoint();
            }
        }
        for (Motivator motivator : mainController.getCompletedMotivatorsDataReport()) {
            if (motivator != null & motivator.getStatus().contains("COMPLETED")) {
                counterPointsCompletedMotivations += motivator.getPoints();
            }
        }
        counterCompletedTask.setText(String.valueOf(counterPointsCompletedTask));
        counterCompletedMotivations.setText(String.valueOf(counterPointsCompletedMotivations));

        System.out.println("The report was updated.");
    }

}

