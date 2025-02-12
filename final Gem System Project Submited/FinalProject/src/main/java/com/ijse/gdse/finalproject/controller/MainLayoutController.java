package com.ijse.gdse.finalproject.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class MainLayoutController implements Initializable {
    public Button btnCustomer;
    public AnchorPane ancMainLayout;
    public AnchorPane ancMain;
    public Label lblTime;
    public Label lblDate;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        lblDate.setText(LocalDate.now().toString());

        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("hh:mm a");
        lblTime.setText(LocalTime.now().format(timeFormatter));
        navigateTo("/view/Customer.fxml");
    }

    public void customerOnAction(ActionEvent actionEvent) throws IOException {
        navigateTo("/view/Customer.fxml");
    }

    public void ordersOnAction(ActionEvent actionEvent) {
        navigateTo("/view/Orders.fxml");
    }

    public void employeeOnAction(ActionEvent actionEvent) {
        navigateTo("/view/Employee.fxml");
    }

    public void supplierOnAction(ActionEvent actionEvent) {
        navigateTo("/view/Supplier.fxml");
    }

    public void gemsOnAction(ActionEvent actionEvent) {
        navigateTo("/view/Gems.fxml");
    }

    public void navigateTo(String fxmlPath) {
        try {
            ancMainLayout.getChildren().clear();
            AnchorPane load = FXMLLoader.load(getClass().getResource(fxmlPath));

//  -------- Loaded anchor edges are bound to the content anchor --------
//      (1) Bind the loaded FXML to all edges of the content anchorPane
            load.prefWidthProperty().bind(ancMainLayout.widthProperty());
            load.prefHeightProperty().bind(ancMainLayout.heightProperty());

//      (2) Bind the loaded FXML to all edges of the AnchorPane
//            AnchorPane.setTopAnchor(load, 0.0);
//            AnchorPane.setRightAnchor(load, 0.0);
//            AnchorPane.setBottomAnchor(load, 0.0);
//            AnchorPane.setLeftAnchor(load, 0.0);

            ancMainLayout.getChildren().add(load);
        } catch (IOException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Fail to load page!").show();
        }
    }




    public void userOnAction(ActionEvent actionEvent) {

    }

    public void homeClick(MouseEvent mouseEvent) throws IOException {
        AnchorPane load = FXMLLoader.load(getClass().getResource("/view/HomePage.fxml"));
        ancMain.getChildren().clear();
        ancMain.getChildren().add(load);
    }
}
