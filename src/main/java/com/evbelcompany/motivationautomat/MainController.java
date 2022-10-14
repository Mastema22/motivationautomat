package com.evbelcompany.motivationautomat;


import com.evbelcompany.motivationautomat.models.Motivator;
import com.evbelcompany.motivationautomat.models.Task;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;


import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

public class MainController implements Serializable {

    //-------------Obsertvable Lists for all windows-----------------
    private static final ObservableList<Task> tasksData = FXCollections.observableArrayList();
    private static final ObservableList<Task> currentTaskData = FXCollections.observableArrayList();
    private static final ObservableList<Motivator> motivatorsData = FXCollections.observableArrayList();

    private static final ObservableList<Task> completedTaskDataReport = FXCollections.observableArrayList();
    private static final ObservableList<Motivator> completedMotivatorsDataReport = FXCollections.observableArrayList();

    //--------------Serializable ArrayLists for save/load data-----------------------------------------
    private final Set<Task> tasksDataList = new HashSet<>();
    private Set<Task> newTask = new HashSet<Task>();
    private final Set<Motivator> motivationDataList = new HashSet<>();
    private Set<Motivator> newMotivation = new HashSet<Motivator>();

    //---------------------------Variables---------------------------
    private EnterFormController enterFormController;
    private EnterMotivatorsFormController enterMotivatorsFormController;
    private RealizedMotivationsController realizedMotivationsController;
    private Task task;
    private Motivator motivator;
    private String status;

    //--------------------------Main file for save/load data------------------------------
    private String userName = System.getProperty("user.name");
    private File fileBaseTasks = new File("C://Users/" + userName + "/Motivation Automat/Data/base01.tmb");
    private File fileBaseMotivations = new File("C://Users/" + userName + "/Motivation Automat/Data/base02.tmb");

    //-------------------------FXML elements for the new tasks-------------------------------
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

    //-------------------------FXML elements for the current tasks-------------------------------
    @FXML
    private TableView<Task> currentTableTask;

    @FXML
    private TableColumn<Task, Integer> currentIdColumn;

    @FXML
    private TableColumn<Task, String> currentTaskColumn;

    @FXML
    private TableColumn<Task, Integer> currentPointsColumn;

    //-------------------------FXML elements for the completed tasks-------------------------------
    @FXML
    Label availablePoints;

    @FXML
    private Label totalCompletedTasks;

    @FXML
    private Label totalCompletedMotivations;

    @FXML
    private Label potentialTasks;

    @FXML
    private Label potentialMotivations;

    //-------------------------FXML elements for the motivators list-------------------------------

    @FXML
    private TableView<Motivator> motivatorsTable;

    @FXML
    private TableColumn<Motivator, String> motivatorsColumn;

    @FXML
    private TableColumn<Motivator, Integer> countPointsMotivatorsColumn;

    //-------------------------FXML of initialize-------------------------------
    @FXML
    public void initialize(){

        realizedMotivationsController = new RealizedMotivationsController();

        tableTasks.setEditable(true);
        taskColumn.setCellValueFactory(new PropertyValueFactory<Task, String>("task"));
        pointsColumn.setCellValueFactory(new PropertyValueFactory<Task, Integer>("point"));
        tableTasks.setItems(getTasksData());

        currentTableTask.setEditable(true);
        currentTaskColumn.setCellValueFactory(new PropertyValueFactory<Task, String>("task"));
        currentPointsColumn.setCellValueFactory(new PropertyValueFactory<Task, Integer>("point"));
        currentTableTask.setItems(getCurrentTaskData());

        motivatorsTable.setEditable(true);
        motivatorsColumn.setCellValueFactory(new PropertyValueFactory<Motivator, String>("motivator"));
        countPointsMotivatorsColumn.setCellValueFactory(new PropertyValueFactory<Motivator, Integer>("points"));
        motivatorsTable.setItems(getMotivatorsData());

        checkAndMakeFolderAndFilesBASE();

        loadDataTasks();
        loadDataMotivators();

        updateStatusInformation();

        delaySaveData();
    }

    //-----------------------------Metods for get observable lists---------------------------
    public ObservableList<Task> getTasksData() {
        return tasksData;
    }

    public ObservableList<Task> getCurrentTaskData() {
        return currentTaskData;
    }

    public ObservableList<Task> getCompletedTasksDataReport() {
        return completedTaskDataReport;
    }

    public ObservableList<Motivator> getMotivatorsData() {
        return motivatorsData;
    }

    public ObservableList<Motivator> getCompletedMotivatorsDataReport() {
        return completedMotivatorsDataReport;
    }

    //-----------------------------Metods for get Arrive lists---------------------------

    public Set<Task> getTasksDataList() {
        return tasksDataList;
    }

    //-----------------------------Metods for set Arrive lists---------------------------
    public void addTasksDataList(Task task) {
        tasksDataList.add(task);
    }
    public void addMotivatonDataList(Motivator motivator) {
        motivatorsData.add(motivator);
    }

    //-----------------------------Metods for set observable lists---------------------------
    public void addTasksData(Task newTask) {
        tasksData.add(newTask);
    }

    public void addCurrentTasksData(Task task) {
        currentTaskData.add(task);
    }

    public void addCompletedTasksDataReport(Task task) {
        completedTaskDataReport.add(task);
    }

    public void addMotivatorsData(Motivator motivator) {
        motivatorsData.add(motivator);
    }

    public void addCompletedMotivatorsDataReport(Motivator motivator) { completedMotivatorsDataReport.add(motivator);}

    //------------------------------Metod for get counter points----------------------------


    //--------------------------------------------------------

    public MainController() {
    }

    public void btnNewTask(ActionEvent actionEvent) throws IOException {
        enterFormController = new EnterFormController();
        enterFormController.initializeEnterFormController();
        updateStatusInformation();

    }

    public void btnDeleteTask(ActionEvent actionEvent) {
        int selectedIndex = tableTasks.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            task = tableTasks.getItems().get(selectedIndex);
            task.setStatus("REMOVED");
            tableTasks.getItems().remove(selectedIndex);
            updateStatusInformation();
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
        if (selectedIndex >= 0) {
            task = tasksData.get(selectedIndex);
            task.setStatus("CURRENT");
            addCurrentTasksData(task);
            tableTasks.getItems().remove(selectedIndex);
            updateStatusInformation();
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
        if (selectedIndex >= 0) {
            task = currentTaskData.get(selectedIndex);
            task.setStatus("NEW");
            addTasksData(task);
            currentTableTask.getItems().remove(selectedIndex);
            updateStatusInformation();
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Внимание!");
            alert.setHeaderText("Не выбрана ни одна задача для удаления из текущих задач!");
            alert.setContentText("Пожалуйста, выбери из таблицы задачу для удаления из текущих задач.");
            alert.showAndWait();
        }
    }

    public void btnFinishCurrentTask(ActionEvent actionEvent) {
        realizedMotivationsController = new RealizedMotivationsController();
        int selectedIndex = currentTableTask.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            task = currentTaskData.get(selectedIndex);
            task.setStatus("FINISHED");
            addCompletedTasksDataReport(task);
            currentTableTask.getItems().remove(selectedIndex);
            updateStatusInformation();
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
        if (selectedIndex >= 0) {
            motivator = motivatorsTable.getItems().get(selectedIndex);
            motivator.setStatus("REMOVED");
            System.out.println("Removed the motivation");
            motivatorsTable.getItems().remove(selectedIndex);
            updateStatusInformation();
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Внимание!");
            alert.setHeaderText("Не выбрана ни одна мотивация для удаления!");
            alert.setContentText("Пожалуйста, выбери из таблицы мотивацию для удаления.");
            alert.showAndWait();
        }
    }

    public void btnNewMotivation(ActionEvent actionEvent) throws IOException {
        updateStatusInformation();
        enterMotivatorsFormController = new EnterMotivatorsFormController();
        enterMotivatorsFormController.initializeEnterMotivatorFormController();
        updateStatusInformation();

    }

    public void btnAchiveMotivation(ActionEvent actionEvent) throws IOException {
        int selectedIndex = motivatorsTable.getSelectionModel().getSelectedIndex();
        if(selectedIndex >= 0) {
            System.out.println("Achieved the motivation");
            motivator = motivatorsData.get(selectedIndex);
            motivator.setStatus("COMPLETED");
            addCompletedMotivatorsDataReport(motivator);
            System.out.println("Go to the report: " + motivator.getMotivator() + " " + motivator.getPoints() + " " + motivator.getStatus());
            motivatorsTable.getItems().remove(selectedIndex);
            updateStatusInformation();
        }else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Внимание!");
            alert.setHeaderText("Не выбрана ни одна мотивация для реализации!");
            alert.setContentText("Пожалуйста, выбери из таблицы мотивацию для реализации.");
            alert.showAndWait();
        }
    }

    public void saveDataTasks() {
        for (Task taskFormNewTasks : tasksData) {
            tasksDataList.add(taskFormNewTasks);
        }
        for (Task taskFromCurrentTasks : currentTaskData) {
            tasksDataList.add(taskFromCurrentTasks);
        }
        for (Task taskFromCompletedTasks : completedTaskDataReport) {
            tasksDataList.add(taskFromCompletedTasks);
        }

        for (Task task : tasksDataList) {
            System.out.println("Saved next the task: " + task.getNumber() + " " + task.getTask() + " " + task.getPoint() + " " + task.getStatus());
        }


        try (FileOutputStream fosNewTask = new FileOutputStream(fileBaseTasks);
             ObjectOutputStream oosNewTask = new ObjectOutputStream(fosNewTask)
        ) {

            oosNewTask.writeObject(tasksDataList);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void saveDataMotivators() {
        for (Motivator motivationFromNewMotivations : motivatorsData) {
            motivationDataList.add(motivationFromNewMotivations);
        }
        for (Motivator motivatorFromCompletedMotivations : completedMotivatorsDataReport) {
            motivationDataList.add(motivatorFromCompletedMotivations);
        }
        for (Motivator motivator : motivationDataList) {
            System.out.println("Saved next the motivator: " + motivator.getMotivator() + " " + motivator.getPoints() + " " + motivator.getStatus());
        }

        try (FileOutputStream fosNewMotivator = new FileOutputStream(fileBaseMotivations);
             ObjectOutputStream oosNewMotivator = new ObjectOutputStream(fosNewMotivator)
        ) {

            oosNewMotivator.writeObject(motivationDataList);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadDataTasks() {

        try (FileInputStream fisNewTask = new FileInputStream(fileBaseTasks);
             ObjectInputStream oisNewTask = new ObjectInputStream(fisNewTask)) {

            newTask = ((Set<Task>) oisNewTask.readObject());

            for (Task task : newTask) {
                if (task.getStatus().contains("NEW")) {
                    tasksData.add(task);
                    System.out.println("Loaded the new tasks: " + task.getNumber() + task.getTask() + task.getPoint());
                }
                if (task.getStatus().contains("CURRENT")) {
                    currentTaskData.add(task);
                    System.out.println("Loaded the current tasks: " + task.getNumber() + task.getTask() + task.getPoint());
                }
                if (task.getStatus().contains("FINISHED")) {
                    completedTaskDataReport.add(task);
                    System.out.println("Loaded the completed tasks: " + task.getNumber() + task.getTask() + task.getPoint());
                }
            }
        } catch (Exception e){
            System.out.println("First start");;
        }
    }

    public void loadDataMotivators() {
        try (
                FileInputStream fisNewMotivator = new FileInputStream(fileBaseMotivations);
                ObjectInputStream oisNewMotivator = new ObjectInputStream(fisNewMotivator)) {

            newMotivation = (Set<Motivator>) oisNewMotivator.readObject();

            for (Motivator motivator : newMotivation) {
                if(motivator.getStatus().contains("NEW")) {
                    motivatorsData.add(motivator);
                    System.out.println("Loaded the new motivator: " + motivator.getMotivator() + " " + motivator.getPoints());
                }
                if(motivator.getStatus().contains("COMPLETED")) {
                    completedMotivatorsDataReport.add(motivator);
                    System.out.println("Loaded the completed motivator: " + motivator.getMotivator() + " " + motivator.getPoints());
                }
            }
        }catch (Exception e) {
            System.out.println("First start!");;
        }
    }

    public void delaySaveData() {
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                saveDataTasks();
                saveDataMotivators();
                System.out.println("Timer for save the date is working.");
            }
        };
        Timer timer = new Timer(true);
        timer.scheduleAtFixedRate(timerTask,0,10*1000);
    }

    public void btnReport(ActionEvent actionEvent) throws IOException {
        updateStatusInformation();
        realizedMotivationsController = new RealizedMotivationsController();
        realizedMotivationsController.initializeRealizedMotivationsFrom();
        updateStatusInformation();
    }

    public void updateStatusInformation()  {
        int totalCompletedTasksCount = 0;
        int totalCompletedMotivationsCount = 0;
        int countPotentialTask = 0;
        int countPotentialMotivation = 0;

        int pointsCompletedTask = 0;
        int pointsCompletedMotivation = 0;
        for (Task task : completedTaskDataReport) {
            pointsCompletedTask += task.getPoint();
        }
        for (Motivator motivator : completedMotivatorsDataReport) {
            pointsCompletedMotivation += motivator.getPoints();
        }
        availablePoints.setText(String.valueOf(pointsCompletedTask-pointsCompletedMotivation));
        System.out.println("The available points were updated.");

        for (Task task : getCompletedTasksDataReport()) {
            if(task != null) {
                totalCompletedTasksCount += 1;
            }
        }
        totalCompletedTasks.setText(String.valueOf(totalCompletedTasksCount));
        System.out.println("The completed tasks were updated.");

        for (Motivator motivator : getCompletedMotivatorsDataReport()) {
            if (motivator != null) {
                totalCompletedMotivationsCount += 1;
            }
        }
        totalCompletedMotivations.setText(String.valueOf(totalCompletedMotivationsCount));
        System.out.println("The completed motivations were updated.");

        for (Task task : getTasksData()) {
            if(task != null) {
                countPotentialTask += task.getPoint();
            }
        }
        potentialTasks.setText(String.valueOf(countPotentialTask));
        System.out.println("The potential tasks were updated.");

        for (Motivator motivator : getMotivatorsData()) {
            if (motivator != null) {
                countPotentialMotivation += motivator.getPoints();
            }

        }
        potentialMotivations.setText(String.valueOf(countPotentialMotivation));
        System.out.println("The potential motivations were updated.");
    }


    private void checkAndMakeFolderAndFilesBASE()  {
        Path file1 = Paths.get(fileBaseTasks.getAbsolutePath());
        Path file2 = Paths.get(fileBaseMotivations.getAbsolutePath());
        Path directoryBase = Paths.get(fileBaseTasks.getParent());

        if(!Files.exists(directoryBase)) {
            try {
                Files.createDirectories(directoryBase);
                Files.createFile(file1);
                Files.createFile(file2);
           }  catch (IOException exception) {
                System.err.println("Problem with make a new base-files!");
            }
        }

    }
}