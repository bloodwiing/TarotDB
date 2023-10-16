module me.bloodwiing.tarotdb {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.json;


    opens me.bloodwiing.tarotdb to javafx.fxml;
    exports me.bloodwiing.tarotdb;
    exports me.bloodwiing.tarotdb.controllers;
    exports me.bloodwiing.tarotdb.data;
    exports me.bloodwiing.tarotdb.builders;
    opens me.bloodwiing.tarotdb.controllers to javafx.fxml;
}