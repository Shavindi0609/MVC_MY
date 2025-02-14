package com.ijse.gdse.finalproject.controller;

import com.ijse.gdse.finalproject.bo.BOFactory;
import com.ijse.gdse.finalproject.bo.custom.EmployeeBO;
import com.ijse.gdse.finalproject.bo.custom.UserBO;
import com.ijse.gdse.finalproject.dao.DAOFactory;
import com.ijse.gdse.finalproject.dao.custom.EmployeeDAO;
import com.ijse.gdse.finalproject.dao.custom.UserDAO;
import com.ijse.gdse.finalproject.dto.EmployeeDTO;
import com.ijse.gdse.finalproject.dto.tm.EmployeeTM;
import com.ijse.gdse.finalproject.dao.custom.impl.EmployeeDAOImpl;
import com.ijse.gdse.finalproject.dao.custom.impl.UserDAOImpl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class EmployeeController implements Initializable {

    public AnchorPane ancEmployeePage;
    public Button btnSave, btnUpdate, btnDelete, btnReset;
    public TextField txtName, txtNic, txtEmail, txtPhone, txtAddress;
    public Label lblEmployeeCount;
    public ComboBox<String> cmbUserId;

    @FXML
    private TableColumn<EmployeeTM, String> colAddress, colEmail, colEmployeeId, colName, colNic, colPhone, colUserId;
    @FXML
    private Label lblEmployeeId;
    @FXML
    private TableView<EmployeeTM> tblEmployee;

    UserBO userBO = (UserBO) BOFactory.getInstance().getBO(BOFactory.BOType.USER);
    EmployeeBO employeeBO = (EmployeeBO) BOFactory.getInstance().getBO(BOFactory.BOType.EMPLOYEE);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colEmployeeId.setCellValueFactory(new PropertyValueFactory<>("employeeId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colNic.setCellValueFactory(new PropertyValueFactory<>("nic"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colUserId.setCellValueFactory(new PropertyValueFactory<>("userId"));

        try {
            refreshPage();
            updateEmployeeCount();
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Failed to load employee data.").show();
        }
    }

    private void updateEmployeeCount() throws SQLException {
        int employeeCount = employeeBO.getEmployeeCount();
        lblEmployeeCount.setText("Employee Count: " + employeeCount);
    }

    private void refreshPage() throws SQLException {
        loadNextEmployeeId();
        loadEmployeeTableData();
        loadUserIds();
        resetFields();

        btnSave.setDisable(false);
        btnUpdate.setDisable(true);
        btnDelete.setDisable(true);
    }

    private void loadUserIds() throws SQLException {
        ArrayList<String> userIds = userBO.getAllUserIds();
        cmbUserId.setItems(FXCollections.observableArrayList(userIds));
    }

    private void loadEmployeeTableData() throws SQLException {
        ArrayList<EmployeeDTO> employeeDTOS = employeeBO.getAllEmployee();
        ObservableList<EmployeeTM> employeeTMS = FXCollections.observableArrayList();

        for (EmployeeDTO employeeDTO : employeeDTOS) {
            EmployeeTM employeeTM = new EmployeeTM(
                    employeeDTO.getEmployeeId(),
                    employeeDTO.getName(),
                    employeeDTO.getNic(),
                    employeeDTO.getEmail(),
                    employeeDTO.getPhone(),
                    employeeDTO.getAddress(),
                    employeeDTO.getUserId()
            );
            employeeTMS.add(employeeTM);
        }
        tblEmployee.setItems(employeeTMS);
    }

    private void loadNextEmployeeId() throws SQLException {
        String nextEmployeeId = employeeBO.getNextEmployeeId();
        lblEmployeeId.setText(nextEmployeeId);
    }

    private void resetFields() {
        txtName.clear();
        txtNic.clear();
        txtEmail.clear();
        txtPhone.clear();
        txtAddress.clear();
        cmbUserId.getSelectionModel().clearSelection();
    }

    public void saveOnAction(ActionEvent actionEvent) throws SQLException {
        if (!validateFields()) return;

        String employeeId = lblEmployeeId.getText();
        String selectedUserId = cmbUserId.getValue();

        EmployeeDTO employeeDTO = new EmployeeDTO(
                employeeId,
                txtName.getText(),
                txtNic.getText(),
                txtEmail.getText(),
                txtPhone.getText(),
                txtAddress.getText(),
                selectedUserId
        );

        boolean isSaved = employeeBO.saveEmployee(employeeDTO);
        if (isSaved) {
            refreshPage();
            new Alert(Alert.AlertType.INFORMATION, "Employee saved successfully!").show();
        } else {
            new Alert(Alert.AlertType.ERROR, "Failed to save employee.").show();
        }
    }

    public void updateOnAction(ActionEvent actionEvent) throws SQLException {
        if (!validateFields()) return;

        String employeeId = lblEmployeeId.getText();
        String selectedUserId = cmbUserId.getValue();

        EmployeeDTO employeeDTO = new EmployeeDTO(
                employeeId,
                txtName.getText(),
                txtNic.getText(),
                txtEmail.getText(),
                txtPhone.getText(),
                txtAddress.getText(),
                selectedUserId
        );

        boolean isUpdated = employeeBO.updateEmployee(employeeDTO);
        if (isUpdated) {
            refreshPage();
            new Alert(Alert.AlertType.INFORMATION, "Employee updated successfully!").show();
        } else {
            new Alert(Alert.AlertType.ERROR, "Failed to update employee.").show();
        }
    }

    public void deleteOnAction(ActionEvent actionEvent) throws SQLException {
        String employeeId = lblEmployeeId.getText();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> optionalButtonType = alert.showAndWait();

        if (optionalButtonType.isPresent() && optionalButtonType.get() == ButtonType.YES) {
            boolean isDeleted = employeeBO.deleteEmployee(employeeId);
            if (isDeleted) {
                refreshPage();
                new Alert(Alert.AlertType.INFORMATION, "Employee deleted successfully!").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to delete employee.").show();
            }
        }
    }

    public void resetOnAction(ActionEvent actionEvent) throws SQLException {
        refreshPage();
    }

    public void OnClickTable(MouseEvent mouseEvent) {
        EmployeeTM employeeTM = tblEmployee.getSelectionModel().getSelectedItem();
        if (employeeTM != null) {
            lblEmployeeId.setText(employeeTM.getEmployeeId());
            txtName.setText(employeeTM.getName());
            txtNic.setText(employeeTM.getNic());
            txtEmail.setText(employeeTM.getEmail());
            txtPhone.setText(employeeTM.getPhone());
            txtAddress.setText(employeeTM.getAddress());
            cmbUserId.setValue(employeeTM.getUserId());

            btnSave.setDisable(true);
            btnUpdate.setDisable(false);
            btnDelete.setDisable(false);
        }
    }

    private boolean validateFields() {
        String name = txtName.getText();
        String nic = txtNic.getText();
        String email = txtEmail.getText();
        String phone = txtPhone.getText();
        String address = txtAddress.getText();

        String namePattern = "^[A-Za-z ]+$";
        String nicPattern = "^[0-9]{9}[vVxX]|[0-9]{12}$";
        String emailPattern = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
        String phonePattern = "^(\\+\\d{1,3}[- ]?)?\\d{10}$";
        String addressPattern = "^[A-Za-z0-9 ,.-]+$";

        boolean isValidName = name.matches(namePattern);
        boolean isValidNic = nic.matches(nicPattern);
        boolean isValidEmail = email.matches(emailPattern);
        boolean isValidPhone = phone.matches(phonePattern);
        boolean isValidAddress = address.matches(addressPattern);

        if (!isValidName) showValidationError(txtName, "Invalid name!");
        if (!isValidNic) showValidationError(txtNic, "Invalid NIC!");
        if (!isValidEmail) showValidationError(txtEmail, "Invalid email!");
        if (!isValidPhone) showValidationError(txtPhone, "Invalid phone number!");
        if (!isValidAddress) showValidationError(txtAddress, "Invalid address!");

        return isValidName && isValidNic && isValidEmail && isValidPhone && isValidAddress;
    }

    private void showValidationError(TextField field, String message) {
        field.setStyle("-fx-border-color: red;");
        new Alert(Alert.AlertType.ERROR, message).show();
    }
}