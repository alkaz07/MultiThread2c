module com.example.multithread2 {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.multithread2 to javafx.fxml;
    exports com.example.multithread2;
}