package com.ijse.gdse.finalproject.controller;

import com.ijse.gdse.finalproject.dto.UserDTO;
import com.ijse.gdse.finalproject.model.UserModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.sql.SQLException;

public class ProfileController {
    public AnchorPane ancProfile;
    public Label lblusername;
    public Label lbluserId;
    public Label lblrole;

    UserModel userModel = new UserModel();

    public void setData(String username) {
       lblusername.setText(username);
        System.out.println("Setting data for userId: " + username); // Debug output

        try {
            UserDTO userDetails = userModel.getUserDetails(username);

            lbluserId.setText(userDetails.getUserId());
            lblrole.setText(userDetails.getRole());

        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to load user details!").show();
            e.printStackTrace(); // Print stack trace to identify the error
        }
    }

    public void resetOnAction(ActionEvent actionEvent) {
    }

    public void btnDeleteOnAction(ActionEvent actionEvent) {
    }

    public void backClick(MouseEvent mouseEvent) throws IOException {
        AnchorPane load = FXMLLoader.load(getClass().getResource("/view/HomePage.fxml"));
        ancProfile.getChildren().clear();
        ancProfile.getChildren().add(load);
    }
}
