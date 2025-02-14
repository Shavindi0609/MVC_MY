package com.ijse.gdse.finalproject.controller;

import com.ijse.gdse.finalproject.bo.BOFactory;
import com.ijse.gdse.finalproject.bo.custom.UserBO;
import com.ijse.gdse.finalproject.entity.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class UserController implements Initializable {
    public Button btnRegister;
    public Button btnClear;
    public Button btnLogIn;
    public AnchorPane ancRegister;
    public TableView tblUser;


    @FXML
    private TextField txtConfirmPassword;

    @FXML
    private TextField txtPassword;

    @FXML
    private Label lblUserId;

    @FXML
    private TextField txtUserName;

    @FXML
    private TextField txtUserRole;

       UserBO userBO = (UserBO) BOFactory.getInstance().getBO(BOFactory.BOType.USER);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {
            refreshPage();
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Fail to load user id").show();
        }
    }

    private void refreshPage() throws SQLException {
        loadNextUserId();

        txtUserName.setText("");
        txtUserRole.setText("");
        txtPassword.setText("");


    }

    private void loadNextUserId() throws SQLException {
        String nextUserId = userBO.getNextUserId();
        lblUserId.setText(nextUserId);
    }




    public void registerOnAction(ActionEvent actionEvent) throws SQLException {
        String userId = lblUserId.getText();
        String username = txtUserName.getText();
        String role = txtUserRole.getText();
        String password = txtPassword.getText();
        String confirmPassword = txtConfirmPassword.getText();

        User user = new User(userId, username, role, password);

        boolean isSaved = userBO.saveUser(user);
        if (isSaved) {
            refreshPage();
            new Alert(Alert.AlertType.INFORMATION, "User Registerd...!").show();
        } else {
            new Alert(Alert.AlertType.ERROR, "Fail to register user...!").show();
        }

    }

    public void clearOnAction(ActionEvent actionEvent) throws SQLException {
        refreshPage();
    }

    public void logInOnAction(ActionEvent actionEvent) throws IOException {
        AnchorPane load = FXMLLoader.load(getClass().getResource("/view/Login.fxml"));
        ancRegister.getChildren().clear();
        ancRegister.getChildren().add(load);
    }


}
