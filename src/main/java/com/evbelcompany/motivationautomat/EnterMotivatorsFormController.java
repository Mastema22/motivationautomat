package com.evbelcompany.motivationautomat;

import com.evbelcompany.motivationautomat.models.Motivator;
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

public class EnterMotivatorsFormController implements Serializable {
    private Stage STAGE = new Stage();
    private Scene SCENE;
    private Parent ROOT;
    private Motivator newMotivator;


    private final MainController mainController = new MainController();

    private Alert alertINFO;
    private Main main;
    private String discriptionMotivator;
    private Integer countPointsMotivator;


    @FXML
    private TextField motivatorDiscriptionText;

    @FXML
    private TextField countPointsMotivatorText;

    @FXML
    private Button cancelEnterMotivatorForm;

    @FXML
    private Button saveEnterMotivatorForm;

    public void setMain(Main main) {
        this.main = main;
    }

    public void initializeEnterMotivatorFormController() throws IOException {
        ROOT = FXMLLoader.load(EnterFormController.class.getResource("EnterMotivatorsForm.fxml"));
        SCENE = new Scene(ROOT,300,140);
        STAGE.setScene(SCENE);
        STAGE.setTitle("Новая мотивация");
        STAGE.initStyle(StageStyle.UNDECORATED);
        STAGE.getIcons().add(new Image(Main.class.getResourceAsStream("icons/iconAppLarge.png")));
        STAGE.showAndWait();
    }

    public void btnCancelEnterMotivatorForm(ActionEvent actionEvent) {
        STAGE = (Stage) cancelEnterMotivatorForm.getScene().getWindow();
        System.out.println("Cancel the new motivation");
        STAGE.close();
    }

    public void btnSaveEnterMotivatorForm(ActionEvent actionEvent) {
        STAGE = (Stage)  saveEnterMotivatorForm.getScene().getWindow();

        try {
            if (!countPointsMotivatorText.getText().matches("[A-zА-я]") &
                    Integer.parseInt(countPointsMotivatorText.getText()) <= 1000000 & Integer.parseInt(countPointsMotivatorText.getText()) >= 1) {

                discriptionMotivator = motivatorDiscriptionText.getText();
                countPointsMotivator = Integer.parseInt(countPointsMotivatorText.getText());

                newMotivator = new Motivator(discriptionMotivator, countPointsMotivator, "NEW");

                mainController.addMotivatorsData(newMotivator);
                System.out.println("Made the new motivation " + newMotivator.getMotivator() + " " + newMotivator.getPoints() + " " + newMotivator.getStatus());

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
