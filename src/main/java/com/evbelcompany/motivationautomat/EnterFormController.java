package com.evbelcompany.motivationautomat;

import com.evbelcompany.motivationautomat.models.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.io.Serializable;


public class EnterFormController implements Serializable {
    private Stage STAGE = new Stage();

    private Main main;
    private final MainController mainController = new MainController();

    private Alert alertINFO;

    //-----------------------------------------------------------------------
    @FXML
    private TextField taskDescriptionText;

    @FXML
    private TextField countPointsText;

    @FXML
    private Button cancelEnterForm;

    @FXML
    private Button saveEnterForm;
    //-----------------------------------------------------------------------
    private Integer idText = 1;
    private String descriptionText;
    private Integer countPoints;
    //-----------------------------------------------------------------------
    public void setMain(Main main){
        this.main = main;
    }

    //-----------------------------------------------------------------------
    public void initializeEnterFormController() throws IOException {
         Parent ROOT = FXMLLoader.load(EnterFormController.class.getResource("EnterForm.fxml"));
         Scene SCENE = new Scene(ROOT,300,140);
         STAGE.setScene(SCENE);
         STAGE.setTitle("Новая задача");
         STAGE.initStyle(StageStyle.UNDECORATED);
         STAGE.getIcons().add(new Image(Main.class.getResourceAsStream("icons/iconAppLarge.png")));
         STAGE.showAndWait();
        }


    public void btnCancelEnterForm(ActionEvent actionEvent)  {
        STAGE = (Stage) cancelEnterForm.getScene().getWindow();
        System.out.println("Cancel the new task");
        STAGE.close();
    }

    public void btnSaveEnterForm(ActionEvent actionEvent)  {
        STAGE = (Stage)  saveEnterForm.getScene().getWindow();

        try {
            if (!countPointsText.getText().matches("[A-zА-я]") &
                    Integer.parseInt(countPointsText.getText()) <= 50 & Integer.parseInt(countPointsText.getText()) >= 1) {

                descriptionText = taskDescriptionText.getText();
                countPoints = Integer.parseInt(countPointsText.getText());
                for (Task currentTask : mainController.getTasksData()) {
                    if (currentTask.getTask() != null)  idText++;
                }

                Task newTask = new Task(idText,
                        descriptionText, countPoints, "NEW" );
                mainController.addTasksData(newTask);
                System.out.println("Made the new task " +newTask.getNumber() +" "+ newTask.getTask() + " " + newTask.getPoint() +" " + newTask.getStatus());
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
        alertINFO.setContentText("Введите новое описание задачи и значение баллов от 1 до 50");
        alertINFO.show();
    }

}
