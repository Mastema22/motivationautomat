module com.evbelcompany.motivationautomat {
    requires javafx.controls;
    requires javafx.fxml;

    exports com.evbelcompany.motivationautomat;
    opens com.evbelcompany.motivationautomat to javafx.fxml;
    exports com.evbelcompany.motivationautomat.models;
    opens com.evbelcompany.motivationautomat.models to javafx.fxml;

}