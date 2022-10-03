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

public class EnterMotivatorsFormController {
    private Stage STAGE = new Stage();
    private Scene SCENE;
    private Parent ROOT;
    private Motivator newMotivator;

    private Main main;
    private MainController mainController = new MainController();

    private Alert alertINFO;

    private String discriptionMotivatorText;
    private Integer countPointsMotivator;


    @FXML
    private TextField motivatorDiscriptionText;

    @FXML
    private TextField countPointsMotivatorText;

    @FXML
    private Button cancelEnterMotivatorForm;

    @FXML
    private Button saveEnterMotivatorForm;

    public void setMain(Main main){
        this.main = main;
    }

    public void initializeEnterMotivatorFormController() throws IOException {
        ROOT = FXMLLoader.load(EnterFormController.class.getResource("EnterMotivatorsForm.fxml"));
        SCENE = new Scene(ROOT,300,140);
        STAGE.setScene(SCENE);
        STAGE.setTitle("Новая мотивация");
        STAGE.showAndWait();
    }

    public void btnCancelEnterMotivatorForm(ActionEvent actionEvent) {
        STAGE = (Stage) cancelEnterMotivatorForm.getScene().getWindow();
        STAGE.close();
    }
    @FXML
    public void btnSaveEnterMotivatorForm(ActionEvent actionEvent) {
        STAGE = (Stage)  saveEnterMotivatorForm.getScene().getWindow();

        try {
            if (!countPointsMotivatorText.getText().matches("[A-zА-я]") &
                    Integer.parseInt(countPointsMotivatorText.getText()) <= 1000000 & Integer.parseInt(countPointsMotivatorText.getText()) >= 1) {

                discriptionMotivatorText = motivatorDiscriptionText.getText();
                countPointsMotivator = Integer.parseInt(countPointsMotivatorText.getText());

                newMotivator = new Motivator(discriptionMotivatorText, countPointsMotivator);

                mainController.addMotivatorsData(newMotivator);

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
        alertINFO.setContentText("Введите описание и значение баллов от 1 до 50");
        alertINFO.show();
    }

}
