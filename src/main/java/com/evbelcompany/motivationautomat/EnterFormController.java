package com.evbelcompany.motivationautomat;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;



public class EnterFormController  {
    private Stage STAGE = new Stage();
    private Scene SCENE;
    private Parent ROOT;
    private Task newTask;

    private Main main;
    private MainController mainController = new MainController();

    private Alert alertINFO;


    @FXML
    private TextField taskDiscriptionText;

    @FXML
    private TextField countPointsText;

    @FXML
    private Button cancelEnterForm;

    @FXML
    private Button saveEnterForm;

    private Integer idText = 1;
    private String discriptionText;
    private Integer countPoints;

    public void setMain(Main main){
        this.main = main;
    }


    public void initializeEnterFormController() throws IOException {
         ROOT = FXMLLoader.load(EnterFormController.class.getResource("EnterForm.fxml"));
         SCENE = new Scene(ROOT,300,140);
         STAGE.setScene(SCENE);
         STAGE.setTitle("Новая задача");
         STAGE.showAndWait();
        }


    public void btnCancelEnterForm(ActionEvent actionEvent)  {
        STAGE = (Stage) cancelEnterForm.getScene().getWindow();
        STAGE.close();
    }
    @FXML
    public void btnSaveEnterForm(ActionEvent actionEvent)  {
        STAGE = (Stage)  saveEnterForm.getScene().getWindow();

        try {
            if (!countPointsText.getText().matches("[A-zА-я]") &
                    Integer.parseInt(countPointsText.getText()) <= 50 & Integer.parseInt(countPointsText.getText()) >= 1) {
                discriptionText = taskDiscriptionText.getText();
                countPoints = Integer.parseInt(countPointsText.getText());
                for (Task currenTask : mainController.getTasksData()) {
                    if (currenTask.getTask() != null) {
                        idText++;
                    }
                }

                newTask = new Task(idText,
                        discriptionText, countPoints);

                mainController.addTasksData(newTask);

                STAGE.close();

            } else {
                showAlertINFO();
            }

        } catch (NumberFormatException e) {
            showAlertINFO();

        }
    }

    public void showAlertINFO() {
        alertINFO = new Alert(Alert.AlertType.WARNING);
        alertINFO.setTitle("Внимание");
        alertINFO.setHeaderText("Информация:");
        alertINFO.setContentText("Введите описание задачи и значение баллов от 1 до 50");
        alertINFO.show();
    }

}
