module tn.badbud.nfttun {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    // This opens packages to javafx.fxml which is necessary for FXML loading.
    opens tn.badbud.nfttun to javafx.fxml;
    opens tn.badbud.nfttun.controllers to javafx.fxml;
    opens tn.badbud.nfttun.models to javafx.base; // Correct for access by PropertyValueFactory

    // Exports the packages that contain your main application and controllers.
    exports tn.badbud.nfttun;
    exports tn.badbud.nfttun.controllers;
}
