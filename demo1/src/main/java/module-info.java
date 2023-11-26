module com.example.demo1 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.logging;


    opens com.example.demo1 to javafx.fxml;
    exports com.example.demo1;
    exports Controller;
    opens Controller to javafx.fxml;
    exports Model;
    opens Model to javafx.fxml;
}