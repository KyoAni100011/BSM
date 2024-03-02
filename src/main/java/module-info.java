module com.bsm.bsm {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires java.sql;
    requires jjwt.api;
    requires fontawesomefx;
    requires jbcrypt;

    opens com.bsm.bsm to javafx.fxml;
    opens com.bsm.bsm.auth to javafx.fxml;
    opens com.bsm.bsm.admin to javafx.fxml;
    opens com.bsm.bsm.admin.profileSetting to javafx.fxml;
    opens com.bsm.bsm.employee to javafx.fxml;
    opens com.bsm.bsm.admin.userAccount to javafx.fxml;
    exports com.bsm.bsm;
}
