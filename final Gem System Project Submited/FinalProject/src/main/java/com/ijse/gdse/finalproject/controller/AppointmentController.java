package com.ijse.gdse.finalproject.controller;

import com.ijse.gdse.finalproject.bo.BOFactory;
import com.ijse.gdse.finalproject.bo.custom.AppointmentBO;
import com.ijse.gdse.finalproject.bo.custom.CustomerBO;
import com.ijse.gdse.finalproject.dao.DAOFactory;
import com.ijse.gdse.finalproject.dao.custom.AppointmentDAO;
import com.ijse.gdse.finalproject.dto.AppointmentDTO;
import com.ijse.gdse.finalproject.dto.tm.AppointmentTM;
import com.ijse.gdse.finalproject.dao.custom.impl.AppointmentDAOImpl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import javax.swing.text.html.ImageView;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class AppointmentController implements Initializable {
    public AnchorPane ancAppointment;
    public Button btnAddAppointment;
    public Button btnDelete;
    public Button btnUpdate;
    public TableView tblAppointment;

    public TextField txtCustomerId;
    public TextField txtDate;
    public TextField txtIsAttendance;
    public TextField txtTime;
    public Label lblAppointmentId;
    public javafx.scene.image.ImageView iHome;
    public Label lblAppointmentmessage;
    public Label lblTime;
    public Label lblDate;


    @FXML
    private TableColumn<AppointmentTM, String> colAppointmentId;

    @FXML
    private TableColumn<AppointmentTM, String> colCustomerId;

    @FXML
    private TableColumn<AppointmentTM, String> colDate;

    @FXML
    private TableColumn<AppointmentTM, String> colIsAttendance;

    @FXML
    private TableColumn<AppointmentTM, String> colTime;

    @FXML
    private ImageView imgHome;


    AppointmentBO appointmentBO = (AppointmentBO) BOFactory.getInstance().getBO(BOFactory.BOType.APPOINTMENT);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        lblDate.setText(LocalDate.now().toString());

        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("hh:mm a");
        lblTime.setText(LocalTime.now().format(timeFormatter));

        colAppointmentId.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
        colCustomerId.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colTime.setCellValueFactory(new PropertyValueFactory<>("time"));
        colIsAttendance.setCellValueFactory(new PropertyValueFactory<>("isAttendance"));

        try {
            // Check if there are appointments for today
            LocalDate today = LocalDate.now();
            ArrayList<AppointmentDTO> appointments = appointmentBO.getAllAppointment();
            boolean hasAppointmentToday = appointments.stream()
                    .anyMatch(appointment -> appointment.getDate().equals(today));

            // Display appropriate message with styling
            if (hasAppointmentToday) {
                lblAppointmentmessage.setText("Today has an appointments.");
                lblAppointmentmessage.setStyle("-fx-text-fill: red; -fx-font-weight: bold;");
            } else {
                lblAppointmentmessage.setText("No appointments for today.");
                lblAppointmentmessage.setStyle("-fx-text-fill: blue; -fx-font-weight: bold;");
            }

            refreshPage();
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Failed to load appointments").show();
        }
    }



    private void refreshPage() throws SQLException {
        loadNextAppointmentId();
        loadAppointmentTableData();


        btnAddAppointment.setDisable(false);

        btnUpdate.setDisable(true);
        btnDelete.setDisable(true);


        txtCustomerId.setText("");
        txtDate.setText("");
        txtTime.setText("");
        txtIsAttendance.setText("");
    }


    private void loadAppointmentTableData() throws SQLException {
        ArrayList<AppointmentDTO> appointmentDTOS = appointmentBO.getAllAppointment();

        ObservableList<AppointmentTM> appointmentTMS = FXCollections.observableArrayList();

        for (AppointmentDTO appointmentDTO : appointmentDTOS) {
            AppointmentTM appointmentTM = new AppointmentTM(
                    appointmentDTO.getAppointmentId(),
                    appointmentDTO.getCustomerId(),
                    appointmentDTO.getDate(),
                    appointmentDTO.getTime(),
                    appointmentDTO.getIsAttendance()
            );
            appointmentTMS.add(appointmentTM);
        }

        tblAppointment.setItems(appointmentTMS);
    }

    private void loadNextAppointmentId() throws SQLException {
        String nextAppointmentId = appointmentBO.getNextAppointmentId();
        lblAppointmentId.setText(nextAppointmentId);
    }



    public void addAppointmentOnAction(ActionEvent actionEvent) throws SQLException {

        String appointmentId = lblAppointmentId.getText();
        String customerId = txtCustomerId.getText();
        String dateText = txtDate.getText();
        String timeText = txtTime.getText();
        String isAttendanceText = txtIsAttendance.getText();

        txtDate.setStyle(txtDate.getStyle() + ";-fx-border-color: #7367F0;");
        txtTime.setStyle(txtTime.getStyle() + ";-fx-border-color: #7367F0;");
        txtIsAttendance.setStyle(txtIsAttendance.getStyle() + ";-fx-border-color: #7367F0;");

        String dateTextPattern = "^\\d{4}-\\d{2}-\\d{2}$";
        String timeTextPattern = "^([01]?\\d|2[0-3]):[0-5]\\d$";
        String isAttendanceTextPattern = "^(?i:true|false)$";

        boolean isValidDate = dateText.matches(dateTextPattern);
        boolean isValidTime = timeText.matches(timeTextPattern);
        boolean isValidIsAttendance = isAttendanceText.matches(isAttendanceTextPattern);

        if (!isValidDate) {
            txtDate.setStyle("-fx-border-color: red;");
            System.out.println("Invalid price");
        }
        if (!isValidTime) {
            txtTime.setStyle("-fx-border-color: red;");
            System.out.println("Invalid quantity");
        }
        if (!isValidIsAttendance) {
            txtIsAttendance.setStyle("-fx-border-color: red;");
            System.out.println("Invalid certification status");
        }

        if (isValidDate && isValidTime &&  isValidIsAttendance) {
            LocalDate date = LocalDate.parse(dateText);
            LocalTime time = LocalTime.parse(timeText);
            Boolean is_attendance = Boolean.valueOf(isAttendanceText);

            AppointmentDTO appointmentDTO = new AppointmentDTO(
                    appointmentId,
                    customerId,
                    date,
                    time,
                    is_attendance

            );

            boolean isSaved = appointmentBO.saveAppointment(appointmentDTO);
            if (isSaved) {
                refreshPage();
                new Alert(Alert.AlertType.INFORMATION, "Appointment added...!").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Fail to add appointment...!").show();
            }
        }
    }

    public void deleteOnAction(ActionEvent actionEvent) throws SQLException {
        String appointmentId = lblAppointmentId.getText();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> optionalButtonType = alert.showAndWait();

        if (optionalButtonType.isPresent() && optionalButtonType.get() == ButtonType.YES) {

            boolean isDeleted = appointmentBO.deleteAppointment(appointmentId);
            if (isDeleted) {
                refreshPage();
                new Alert(Alert.AlertType.INFORMATION, "Appointment deleted...!").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Fail to delete appointment...!").show();
            }
        }
    }

    public void updateOnAction(ActionEvent actionEvent) throws SQLException {
        String appointmentId = lblAppointmentId.getText();
        String customerId = txtCustomerId.getText();
        String dateText = txtDate.getText();
        String timeText = txtTime.getText();
        String isAttendanceText = txtIsAttendance.getText();

        txtCustomerId.setStyle(txtCustomerId.getStyle() + ";-fx-border-color:  #7367F0;");
        txtDate.setStyle(txtDate.getStyle() + ";-fx-border-color: #7367F0;");
        txtTime.setStyle(txtTime.getStyle() + ";-fx-border-color: #7367F0;");
        txtIsAttendance.setStyle(txtIsAttendance.getStyle() + ";-fx-border-color: #7367F0;");

        String customerIdpattern = "^C\\d{3}$";
        String dateTextPattern = "^\\d{4}-\\d{2}-\\d{2}$";
        String timeTextPattern = "^([01]?\\d|2[0-3]):[0-5]\\d$";
        String isAttendanceTextPattern = "^(?i:true|false)$";

        boolean isValidCustomerId = customerId.matches(customerIdpattern);
        boolean isValidDate = dateText.matches(dateTextPattern);
        boolean isValidTime = timeText.matches(timeTextPattern);
        boolean isValidIsAttendance = isAttendanceText.matches(isAttendanceTextPattern);

        if (!isValidCustomerId) {
            txtCustomerId.setStyle("-fx-border-color: red;");
            System.out.println("Invalid price");
        }

        if (!isValidDate) {
            txtDate.setStyle("-fx-border-color: red;");
            System.out.println("Invalid price");
        }
        if (!isValidTime) {
            txtTime.setStyle("-fx-border-color: red;");
            System.out.println("Invalid quantity");
        }
        if (!isValidIsAttendance) {
            txtIsAttendance.setStyle("-fx-border-color: red;");
            System.out.println("Invalid certification status");
        }

        if (isValidCustomerId && isValidDate && isValidTime &&  isValidIsAttendance) {

            LocalDate date = LocalDate.parse(dateText);
            LocalTime time = LocalTime.parse(timeText);
            Boolean is_attendance = Boolean.valueOf(isAttendanceText);

            AppointmentDTO appointmentDTO = new AppointmentDTO(
                    appointmentId,
                    customerId,
                    date,
                    time,
                    is_attendance

            );

            boolean isSaved = appointmentBO.updateAppointment(appointmentDTO);
            if (isSaved) {
                refreshPage();
                new Alert(Alert.AlertType.INFORMATION, "Appointment updated...!").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Fail to update appointment...!").show();
            }
        }
    }

    public void OnClickTable(MouseEvent mouseEvent) {
        AppointmentTM appointmentTM = (AppointmentTM) tblAppointment.getSelectionModel().getSelectedItem();;
        if (appointmentTM != null) {
            lblAppointmentId.setText(appointmentTM.getAppointmentId());
            txtCustomerId.setText(appointmentTM.getCustomerId());
            txtDate.setText(String.valueOf(appointmentTM.getDate()));
            txtTime.setText(String.valueOf(appointmentTM.getTime()));
            txtIsAttendance.setText(String.valueOf(appointmentTM.getIsAttendance()));


            btnAddAppointment.setDisable(true);

            btnDelete.setDisable(false);
            btnUpdate.setDisable(false);
        }
    }


    public void OnclickHome(MouseEvent mouseEvent) throws IOException {
        AnchorPane load = FXMLLoader.load(getClass().getResource("/view/HomePage.fxml"));
        ancAppointment.getChildren().clear();
        ancAppointment.getChildren().add(load);
    }

    public void resetOnAction(ActionEvent actionEvent) throws SQLException {
        refreshPage();
    }
}
