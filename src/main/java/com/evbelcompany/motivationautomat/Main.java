package com.evbelcompany.motivationautomat;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;


import java.io.IOException;


public class Main extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("Main.fxml"));
        Scene scene = new Scene(fxmlLoader.load(),1200,450);
        stage.setTitle("Автомат Мотиваций");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.getIcons().add(new Image(Main.class.getResourceAsStream("icons/iconAppLarge.png")));
        stage.show();

    }

    public void connectEnterForm() throws IOException{
        FXMLLoader loader = new FXMLLoader();
        EnterFormController enterFormController = loader.getController();
        enterFormController.setMain(this);
    }

    public void connectEnterMotivatorsForm() throws IOException{
        FXMLLoader loader = new FXMLLoader();
        EnterMotivatorsFormController enterMotivatorsFormController = loader.getController();
        enterMotivatorsFormController.setMain(this);
    }

    public void connectRealizedMotivationsForm() throws IOException{
        FXMLLoader loader = new FXMLLoader();
        RealizedMotivationsController realizedMotivationsController = loader.getController();
        realizedMotivationsController.setMain(this);

    }


    public static void main(String[] args) {
        launch();
    }

}

