package com.evbelcompany.motivationautomat;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;


import java.io.IOException;



public class MainController  {

    private static ObservableList<Task> tasksData = FXCollections.observableArrayList();

    private static ObservableList<Task> currentTasksData = FXCollections.observableArrayList();

    private static ObservableList<Task> completedTasksData = FXCollections.observableArrayList();

    private static ObservableList<Motivator> motivatorsData = FXCollections.observableArrayList();

    private EnterFormController enterFormController;
    private EnterMotivatorsFormController enterMotivatorsFormController;
    private Integer counterPoints;

    //--------------------------------------------------------

    //Список с задачами

    @FXML
    private TableView<Task> tableTasks;

    @FXML
    private TableColumn<Task, Integer> idColumn;

    @FXML
    private TableColumn<Task, String> taskColumn;

    @FXML
    private TableColumn<Task, Integer> pointsColumn;

    @FXML
    private Button btnNewTask;

    //Список с текущими задачами

    @FXML
    private TableView<Task> currentTableTask;

    @FXML
    private TableColumn<Task, Integer> currentIdColumn;

    @FXML
    private TableColumn<Task, String> currentTaskColumn;

    @FXML
    private TableColumn<Task, Integer> currentPointsColumn;

    //Список с выполненными задачами

    @FXML
    private TableView<Task> completedTaskDataTable;

    @FXML
    private TableColumn<Task, Integer> completedIdColumn;

    @FXML
    private TableColumn<Task, String> completedTaskColumn;

    @FXML
    private TableColumn<Task, Integer> completedPointsColumn;

    @FXML
    private Label summaryCountPoints;

    //Списко с мотиваторами

    @FXML
    private TableView<Motivator> motivatorsTable;

    @FXML
    private TableColumn<Motivator, String> motivatorsColumn;

    @FXML
    private TableColumn<Motivator, Integer> countPointsMotivatorsColumn;

    //--------------------------------------------------------


    public MainController(){

    }

    @FXML
    public void initialize() {

        tableTasks.setEditable(true);
        idColumn.setCellValueFactory(new PropertyValueFactory<Task, Integer>("number"));
        taskColumn.setCellValueFactory(new PropertyValueFactory<Task,String>("task"));
        pointsColumn.setCellValueFactory(new PropertyValueFactory<Task, Integer>("point"));
        tableTasks.setItems(tasksData);

        currentTableTask.setEditable(true);
        currentIdColumn.setCellValueFactory(new PropertyValueFactory<Task, Integer>("number"));
        currentTaskColumn.setCellValueFactory(new PropertyValueFactory<Task,String>("task"));
        currentPointsColumn.setCellValueFactory(new PropertyValueFactory<Task, Integer>("point"));
        currentTableTask.setItems(currentTasksData);

        completedTaskDataTable.setEditable(true);
        completedIdColumn.setCellValueFactory(new PropertyValueFactory<Task, Integer>("number"));
        completedTaskColumn.setCellValueFactory(new PropertyValueFactory<Task,String>("task"));
        completedPointsColumn.setCellValueFactory(new PropertyValueFactory<Task, Integer>("point"));
        completedTaskDataTable.setItems(completedTasksData);

        motivatorsTable.setEditable(true);
        motivatorsColumn.setCellValueFactory(new PropertyValueFactory<Motivator,String>("motivator"));
        countPointsMotivatorsColumn.setCellValueFactory(new PropertyValueFactory<Motivator, Integer>("points"));
        motivatorsTable.setItems(motivatorsData);

    }
    //--------------------------------------------------------

    public ObservableList<Task> getTasksData() {
        return tasksData;
    }

    public ObservableList<Task> getCurrentTasksData() {
        return currentTasksData;
    }

    public ObservableList<Task> getCompletedTasksData() {
        return completedTasksData;
    }

    public ObservableList<Motivator> getMotivatorsData() {
        return motivatorsData;
    }

    //--------------------------------------------------------

    public void addTasksData(Task newTask){
        tasksData.add(newTask);
    }

    public void addCurrentTaskData(Task task){
        currentTasksData.add(task);
    }

    public void addCompletedTaskData(Task task){
        completedTasksData.add(task);
    }

    public void addMotivatorsData(Motivator motivator){
        motivatorsData.add(motivator);
    }

    //--------------------------------------------------------
    public Integer getCounterPoints() {
        counterPoints = 0;
        for(Task currentTask : completedTasksData){
            counterPoints += currentTask.getPoint();

        }
        return counterPoints;
    }

    //--------------------------------------------------------


    public void btnNewTask(ActionEvent actionEvent) throws IOException {
        enterFormController = new EnterFormController();
        enterFormController.initializeEnterFormController();
    }

    public void btnDeleteTask(ActionEvent actionEvent) {
        int selectedIndex = tableTasks.getSelectionModel().getSelectedIndex();
        if(selectedIndex >=0) {
            tableTasks.getItems().remove(selectedIndex);
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Внимание!");
            alert.setHeaderText("Не выбрана ни одна задача для удаления!");
            alert.setContentText("Пожалуйста, выбери из таблицы задачу для удаления.");
            alert.showAndWait();
        }
    }

    public void btnCompleteTask(ActionEvent actionEvent) {
        int selectedIndex = tableTasks.getSelectionModel().getSelectedIndex();
        if(selectedIndex >=0) {
            addCurrentTaskData(tasksData.get(selectedIndex));
            tableTasks.getItems().remove(selectedIndex);

        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Внимание!");
            alert.setHeaderText("Не выбрана ни одна задача для выполнения!");
            alert.setContentText("Пожалуйста, выбери из таблицы задачу для выполнения.");
            alert.showAndWait();
        }
    }

    public void btnCancelCurrentTask(ActionEvent actionEvent) {
        int selectedIndex = currentTableTask.getSelectionModel().getSelectedIndex();
        if(selectedIndex >=0) {
            addTasksData(currentTasksData.get(selectedIndex));
            currentTableTask.getItems().remove(selectedIndex);
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Внимание!");
            alert.setHeaderText("Не выбрана ни одна задача для удаления из текущих задач!");
            alert.setContentText("Пожалуйста, выбери из таблицы задачу для удаления из текущих задач.");
            alert.showAndWait();
        }
    }


    public void btnFinishCurrentTask(ActionEvent actionEvent) {
        int selectedIndex = currentTableTask.getSelectionModel().getSelectedIndex();
        if(selectedIndex >=0) {
            addCompletedTaskData(currentTasksData.get(selectedIndex));
            currentTableTask.getItems().remove(selectedIndex);
            getCounterPoints();
            summaryCountPoints.setText(String.valueOf(counterPoints));
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Внимание!");
            alert.setHeaderText("Не выбрана ни одна задача!");
            alert.setContentText("Пожалуйста, выбери из таблицы задачу, которую вы хотите закончить.");
            alert.showAndWait();
        }
    }

    public void btnRemoveMotivation(ActionEvent actionEvent) {
        int selectedIndex = motivatorsTable.getSelectionModel().getSelectedIndex();
        if(selectedIndex >=0) {
            motivatorsTable.getItems().remove(selectedIndex);
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Внимание!");
            alert.setHeaderText("Не выбрана ни одна мотивация для удаления!");
            alert.setContentText("Пожалуйста, выбери из таблицы мотивацию для удаления.");
            alert.showAndWait();
        }
    }

    public void btnNewMotivation(ActionEvent actionEvent) throws IOException {
        enterMotivatorsFormController = new EnterMotivatorsFormController();
        enterMotivatorsFormController.initializeEnterMotivatorFormController();
    }

    public void btnAchiveMotivation(ActionEvent actionEvent) {
    }
}