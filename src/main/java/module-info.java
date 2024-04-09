module com.bsm.bsm {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires java.sql;
    requires jjwt.api;
    requires fontawesomefx;
    requires jbcrypt;

    opens com.bsm.bsm to javafx.fxml;
    opens com.bsm.bsm.user to javafx.fxml;
    opens com.bsm.bsm.auth to javafx.fxml;
    opens com.bsm.bsm.admin to javafx.fxml;
    opens com.bsm.bsm.admin.profileSetting to javafx.fxml;
    opens com.bsm.bsm.employee to javafx.fxml;
    opens com.bsm.bsm.author to javafx.fxml;
    opens com.bsm.bsm.publisher to javafx.fxml;
    opens com.bsm.bsm.admin.userAccount to javafx.fxml;
    opens com.bsm.bsm.employee.profileSetting to javafx.fxml;
    opens com.bsm.bsm.admin.bookRevenue to javafx.fxml;
    opens com.bsm.bsm.category to javafx.fxml;
    opens com.bsm.bsm.employee.book to javafx.fxml;
    opens com.bsm.bsm.employee.bookCategories to javafx.fxml;
    opens com.bsm.bsm.employee.bookAuthors to javafx.fxml;
    opens com.bsm.bsm.employee.bookPublishers to javafx.fxml;
    opens com.bsm.bsm.order to javafx.fxml;
    exports com.bsm.bsm;
    opens com.bsm.bsm.employee.orderManagement to javafx.fxml;
}
