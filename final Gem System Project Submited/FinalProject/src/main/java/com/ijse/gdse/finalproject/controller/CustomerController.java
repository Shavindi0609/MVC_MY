package com.ijse.gdse.finalproject.controller;

import com.ijse.gdse.finalproject.bo.BOFactory;
import com.ijse.gdse.finalproject.bo.custom.CustomerBO;
import com.ijse.gdse.finalproject.bo.custom.UserBO;
import com.ijse.gdse.finalproject.dao.DAOFactory;
import com.ijse.gdse.finalproject.dao.custom.CustomerDAO;
import com.ijse.gdse.finalproject.dao.custom.UserDAO;
import com.ijse.gdse.finalproject.db.DBConnection;
import com.ijse.gdse.finalproject.dto.CustomerDTO;
import com.ijse.gdse.finalproject.dto.tm.CustomerTM;
import com.ijse.gdse.finalproject.dao.custom.impl.CustomerDAOImpl;
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
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.view.JasperViewer;

import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;

public class CustomerController implements Initializable {
    public AnchorPane ancCustomerPage;
    public Button btnSave;
    public Button btnUpdate;
    public Button btnDelete;
    public Button btnReset;
    public Button btnOrderReport;
    public TextField txtName;
    public TextField txtNic;
    public TextField txtEmail;
    public TextField txtPhone;
    public TextField txtAddress;
    public Label lblCustomerCount;
    public ComboBox cmbUserId;


    @FXML
    private TableColumn<CustomerTM, String> colCustomerId;

    @FXML
    private TableColumn<CustomerTM, String> colName;

    @FXML
    private TableColumn<CustomerTM, String> colNic;

    @FXML
    private TableColumn<CustomerTM, String> colEmail;

    @FXML
    private TableColumn<CustomerTM, String> colPhone;

    @FXML
    private TableColumn<CustomerTM, String> colAddress;

    @FXML
    private TableView<CustomerTM> tblCustomer;

    @FXML
    private TableColumn<CustomerTM, String> colUserId;

    @FXML
    private Label lblCustomerId;

    UserBO userBO = (UserBO) BOFactory.getInstance().getBO(BOFactory.BOType.USER);
    CustomerBO customerBO = (CustomerBO) BOFactory.getInstance().getBO(BOFactory.BOType.CUSTOMER);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        colCustomerId.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colNic.setCellValueFactory(new PropertyValueFactory<>("nic"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colUserId.setCellValueFactory(new PropertyValueFactory<>("userId"));

        try {
            refreshPage();
            updateCustomerCount();
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Fail to load customer id").show();
        }

    }

    private void updateCustomerCount() throws SQLException {
        int customerCount = customerBO.getCustomerCount();
        lblCustomerCount.setText("Customer Count: " + customerCount);
    }

    private void refreshPage() throws SQLException {
        loadNextCustomerId();
        loadCustomerTableData();
        loadUserIds();
        updateCustomerCount();


        btnSave.setDisable(false);

        btnUpdate.setDisable(true);
        btnDelete.setDisable(true);

        txtName.setText("");
        txtNic.setText("");
        txtEmail.setText("");
        txtPhone.setText("");
        txtAddress.setText("");
    }

    private void loadUserIds() throws SQLException {
        ArrayList<String> userIds = userBO.getAllUserIds();
        cmbUserId.setItems(FXCollections.observableArrayList(userIds));
    }



    private void loadCustomerTableData() throws SQLException {
        ArrayList<CustomerDTO> customerDTOS = customerBO.getAllCustomer();

        ObservableList<CustomerTM> customerTMS = FXCollections.observableArrayList();

        for (CustomerDTO customerDTO : customerDTOS) {
            CustomerTM customerTM = new CustomerTM(
                    customerDTO.getCustomerId(),
                    customerDTO.getName(),
                    customerDTO.getNic(),
                    customerDTO.getEmail(),
                    customerDTO.getPhone(),
                    customerDTO.getAddress(),
                    customerDTO.getUserId()
            );
            customerTMS.add(customerTM);
        }

        tblCustomer.setItems(customerTMS);
      //  System.out.println("Table Data: " + customerTMS);

    }

    public void loadNextCustomerId() throws SQLException {
        String nextCustomerId = customerBO.getNextCustomerId();
        lblCustomerId.setText(nextCustomerId);
    }

    public void btnSaveOnAction(ActionEvent actionEvent) throws SQLException {
        String customerId = lblCustomerId.getText();
        String name = txtName.getText();
        String nic = txtNic.getText();
        String email = txtEmail.getText();
        String phone = txtPhone.getText();
        String address = txtAddress.getText();
        String userId = (String) cmbUserId.getValue();

        if (userId == null) {
            new Alert(Alert.AlertType.ERROR, "Please select a user ID.").show();
            return;
        }

        txtName.setStyle(txtName.getStyle() + ";-fx-border-color: #7367F0;");
        txtNic.setStyle(txtNic.getStyle() + ";-fx-border-color: #7367F0;");
        txtEmail.setStyle(txtEmail.getStyle() + ";-fx-border-color: #7367F0;");
        txtPhone.setStyle(txtPhone.getStyle() + ";-fx-border-color: #7367F0;");
        txtAddress.setStyle(txtAddress.getStyle() + ";-fx-border-color: #7367F0;");


        String namePattern = "^[A-Za-z ]+$";
        String nicPattern = "^[0-9]{9}[vVxX]||[0-9]{12}$";
        String emailPattern = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
        String phonePattern = "^(\\d+)||((\\d+\\.)(\\d){2})$";
        String addressPattern = "^[A-Za-z ]+$";

        boolean isValidName = name.matches(namePattern);
        boolean isValidNic = nic.matches(nicPattern);
        boolean isValidEmail = email.matches(emailPattern);
        boolean isValidPhone = phone.matches(phonePattern);
        boolean isValidAddress = address.matches(addressPattern);

        if (!isValidName) {
            System.out.println(txtName.getStyle());
            txtName.setStyle(txtName.getStyle() + ";-fx-border-color: red;");
            System.out.println("Invalid name.............");
//            return;
        }

        if (!isValidNic) {
            txtNic.setStyle(txtNic.getStyle() + ";-fx-border-color: red;");
//            return;
        }

        if (!isValidEmail) {
            txtEmail.setStyle(txtEmail.getStyle() + ";-fx-border-color: red;");
        }

        if (!isValidPhone) {
            txtPhone.setStyle(txtPhone.getStyle() + ";-fx-border-color: red;");
        }

        if (!isValidAddress) {
            txtAddress.setStyle(txtAddress.getStyle() + ";-fx-border-color: red;");
        }



        if (isValidName && isValidNic && isValidEmail && isValidPhone && isValidAddress) {
            CustomerDTO customerDTO = new CustomerDTO(
                    customerId,
                    name,
                    nic,
                    email,
                    phone,
                    address,
                    userId
            );

            boolean isSaved = customerBO.saveCustomer(customerDTO);
            if (isSaved) {
                refreshPage();
                updateCustomerCount();
                new Alert(Alert.AlertType.INFORMATION, "Customer saved...!").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Fail to save customer...!").show();
            }
        }
    }

    public void onClickTable(MouseEvent mouseEvent) {
        CustomerTM customerTM = tblCustomer.getSelectionModel().getSelectedItem();
        if (customerTM != null) {
            lblCustomerId.setText(customerTM.getCustomerId());
            txtName.setText(customerTM.getName());
            txtNic.setText(customerTM.getNic());
            txtEmail.setText(customerTM.getEmail());
            txtPhone.setText(customerTM.getPhone());
            txtAddress.setText(customerTM.getAddress());
            cmbUserId.setValue(customerTM.getUserId());

            btnSave.setDisable(true);

            btnDelete.setDisable(false);
            btnUpdate.setDisable(false);
        }
    }

    public void btnDeleteOnAction(ActionEvent actionEvent) throws SQLException {
        String customerId = lblCustomerId.getText();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> optionalButtonType = alert.showAndWait();

        if (optionalButtonType.isPresent() && optionalButtonType.get() == ButtonType.YES) {

            boolean isDeleted = customerBO.deleteCustomer(customerId);
            if (isDeleted) {
                refreshPage();
                updateCustomerCount();
                new Alert(Alert.AlertType.INFORMATION, "Customer deleted...!").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Fail to delete customer...!").show();
            }
        }
    }

    public void btnUpdateOnAction(ActionEvent actionEvent) throws SQLException {
        String customerId = lblCustomerId.getText();
        String name = txtName.getText();
        String nic = txtNic.getText();
        String email = txtEmail.getText();
        String phone = txtPhone.getText();
        String address = txtAddress.getText();
        String userId = (String) cmbUserId.getValue();

        txtName.setStyle(txtName.getStyle() + ";-fx-border-color: #7367F0;");
        txtNic.setStyle(txtNic.getStyle() + ";-fx-border-color: #7367F0;");
        txtEmail.setStyle(txtEmail.getStyle() + ";-fx-border-color: #7367F0;");
        txtPhone.setStyle(txtPhone.getStyle() + ";-fx-border-color: #7367F0;");
        txtAddress.setStyle(txtAddress.getStyle() + ";-fx-border-color: #7367F0;");

        String namePattern = "^[A-Za-z ]+$";
        String nicPattern = "^[0-9]{9}[vVxX]||[0-9]{12}$";
        String emailPattern = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
        String phonePattern = "^(\\d+)||((\\d+\\.)(\\d){2})$";
        String addressPattern = "^[A-Za-z ]+$";

        boolean isValidName = name.matches(namePattern);
        boolean isValidNic = nic.matches(nicPattern);
        boolean isValidEmail = email.matches(emailPattern);
        boolean isValidPhone = phone.matches(phonePattern);
        boolean isValidAddress = address.matches(addressPattern);

        if (!isValidName) {
            System.out.println(txtName.getStyle());
            txtName.setStyle(txtName.getStyle() + ";-fx-border-color: red;");
            System.out.println("Invalid name.............");
//            return;
        }

        if (!isValidNic) {
            txtNic.setStyle(txtNic.getStyle() + ";-fx-border-color: red;");
//            return;
        }

        if (!isValidEmail) {
            txtEmail.setStyle(txtEmail.getStyle() + ";-fx-border-color: red;");
        }

        if (!isValidPhone) {
            txtPhone.setStyle(txtPhone.getStyle() + ";-fx-border-color: red;");
        }

        if (!isValidAddress) {
            txtAddress.setStyle(txtAddress.getStyle() + ";-fx-border-color: red;");
        }

        if (isValidName && isValidNic && isValidEmail && isValidPhone && isValidAddress) {
            CustomerDTO customerDTO = new CustomerDTO(
                    customerId,
                    name,
                    nic,
                    email,
                    phone,
                    address,
                    userId
            );

            boolean isUpdate = customerBO.updateCustomer(customerDTO);
            if (isUpdate) {
                refreshPage();
                loadCustomerTableData();
                new Alert(Alert.AlertType.INFORMATION, "Customer update...!").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Fail to update customer...!").show();
            }
        }
    }

    public void resetOnAction(ActionEvent actionEvent) throws SQLException {
        refreshPage();
    }

    public void orderReportOnAction(ActionEvent actionEvent) {
        CustomerTM customerTM = tblCustomer.getSelectionModel().getSelectedItem();

        if (customerTM == null) {
            return;
        }

        try {
            JasperReport jasperReport = JasperCompileManager.compileReport(
                    getClass()
                            .getResourceAsStream("/report/CustomerOrderDetailsReport.jrxml"
                            ));

            Connection connection = DBConnection.getInstance().getConnection();

            Map<String, Object> parameters = new HashMap<>();

            parameters.put("P_Date", LocalDate.now().toString());
            parameters.put("P_Customer_Id", customerTM.getCustomerId());

            JasperPrint jasperPrint = JasperFillManager.fillReport(
                    jasperReport,
                    parameters,
                    connection
            );

            JasperViewer.viewReport(jasperPrint, false);
        } catch (JRException e) {
            new Alert(Alert.AlertType.ERROR, "Fail to generate report...!").show();
            e.printStackTrace();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "DB error...!").show();
        }
    }

    public void genarateAllCustomerReportOnAction(ActionEvent actionEvent) {
        try {
            JasperReport jasperReport = JasperCompileManager.compileReport(
                    getClass()
                            .getResourceAsStream("/report/customer_report.jrxml"
                            ));

            Connection connection = DBConnection.getInstance().getConnection();

            JasperPrint jasperPrint = JasperFillManager.fillReport(
                    jasperReport,
                    null,
                    connection
            );

            JasperViewer.viewReport(jasperPrint, false);
        } catch (JRException e) {
            new Alert(Alert.AlertType.ERROR, "Fail to generate report...!").show();
         e.printStackTrace();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "DB error...!").show();
        }
    }
}