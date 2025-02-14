package com.ijse.gdse.finalproject.controller;

import com.ijse.gdse.finalproject.bo.BOFactory;
import com.ijse.gdse.finalproject.bo.custom.AppointmentBO;
import com.ijse.gdse.finalproject.bo.custom.CustomerBO;
import com.ijse.gdse.finalproject.bo.custom.EmployeeBO;
import com.ijse.gdse.finalproject.bo.custom.HomePageBO;
import com.ijse.gdse.finalproject.dao.DAOFactory;
import com.ijse.gdse.finalproject.dao.custom.AppointmentDAO;
import com.ijse.gdse.finalproject.dao.custom.CustomerDAO;
import com.ijse.gdse.finalproject.dao.custom.EmployeeDAO;
import com.ijse.gdse.finalproject.dao.custom.SupplierDAO;
import com.ijse.gdse.finalproject.dao.custom.impl.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.net.URL;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class HomePageController implements Initializable {
    public Button btnView;
    public AnchorPane ancHome;
    public Label lblDate;
    public Label lblTime;
    public Label lblAppointmentStatus;
    public Label lblUsername;
    private static String username;
    public Label lblAppointmentmessage;
    public AnchorPane ancMainLayout;
    public Label lblDailyBalance;
    public Label lblTotalSales;
    public Label lblSupplierCount;
    public Label lblEmployeeCount;
    public Label lblCustomerCount;





   HomePageBO homePageBO = (HomePageBO) BOFactory.getInstance().getBO(BOFactory.BOType.HOME);


    //private final OrdersDAOImpl ordersModel = new OrdersDAOImpl();

    public static void setUsername(String username) {
        HomePageController.username = username;
    }


    public void viewOnAction(ActionEvent actionEvent) throws IOException {
        AnchorPane load = FXMLLoader.load(getClass().getResource("/view/MainLayout.fxml"));
        ancHome.getChildren().clear();
        ancHome.getChildren().add(load);
    }

    public void logOutOnAction(ActionEvent actionEvent) throws IOException {
        AnchorPane load = FXMLLoader.load(getClass().getResource("/view/Login.fxml"));
        ancHome.getChildren().clear();
        ancHome.getChildren().add(load);
    }

    public void appointmentOnAction(ActionEvent actionEvent) throws IOException {
        AnchorPane load = FXMLLoader.load(getClass().getResource("/view/Apointment.fxml"));
        ancHome.getChildren().clear();
        ancHome.getChildren().add(load);
    }

    public void inventoryOnAction(ActionEvent actionEvent) throws IOException {
        AnchorPane load = FXMLLoader.load(getClass().getResource("/view/SupplierOrder.fxml"));
        ancHome.getChildren().clear();
        ancHome.getChildren().add(load);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        lblUsername.setText(username);
        lblDate.setText(LocalDate.now().toString());

        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("hh:mm a");
        lblTime.setText(LocalTime.now().format(timeFormatter));

        try {
            setSupplierCount();
        } catch (SQLException e) {
            e.printStackTrace();
            lblSupplierCount.setText("Error loading supplier count");
        }

        try {
            setEmployeeCount();
        } catch (SQLException e) {
            e.printStackTrace();
            lblSupplierCount.setText("Error loading employee count");
        }

        getCountss();
        try {
            setCustomerCount();
        } catch (SQLException e) {
            e.printStackTrace();
            lblCustomerCount.setText("Error loading customer count");
        }

        try {
            checkTodayAppointments();
        } catch (SQLException e) {
            e.printStackTrace();
            lblAppointmentStatus.setText("Error loading appointment status.");
            lblAppointmentStatus.setStyle("-fx-text-fill: gray;"); // Default error style
        }


    }

    private void getCountss() {
        try {
            int xx = homePageBO.getTotCount();
            lblTotalSales.setText(""+xx+".00");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void checkTodayAppointments() throws SQLException {

        LocalDate today = LocalDate.now();

        boolean hasAppointmentToday = homePageBO.getAllAppointment()
                .stream()
                .anyMatch(appointment -> appointment.getDate().equals(today));

        if (hasAppointmentToday) {
            lblAppointmentStatus.setText("Today has appointments.");
            lblAppointmentStatus.setStyle("-fx-text-fill: #F70303; -fx-font-weight: bold; -fx-font-family: 'Georgia';");
        } else {
            lblAppointmentStatus.setText("No appointments for today.");
            lblAppointmentStatus.setStyle("-fx-text-fill: blue; -fx-font-weight: bold; -fx-font-family: 'Georgia';");
        }


    }

    private void setCustomerCount() throws SQLException {
        int customerCount = homePageBO.getCustomerCount();
        lblCustomerCount.setText("Total Customers: " + customerCount);

    }

    private void setEmployeeCount() throws SQLException {
        int employeeCount = homePageBO.getEmployeeCount();
        lblEmployeeCount.setText("Total Employees: " + employeeCount);
    }

    private void setSupplierCount() throws SQLException {
        int supplierCount = homePageBO.getSupplierCount();
        lblSupplierCount.setText("Total Suppliers: " + supplierCount);
    }



    public void GoProfile(MouseEvent mouseEvent) {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Profile.fxml"));
            Parent load = loader.load(); // Load and get the root node

            ProfileController controller = loader.getController();
            controller.setData(username);

            ancHome.getChildren().clear();
            ancHome.getChildren().add(load);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setData(String username) {
        lblUsername.setText(username);

    }


    public void ordersOnAction(ActionEvent actionEvent) throws IOException {
        AnchorPane load = FXMLLoader.load(getClass().getResource("/view/Orders.fxml"));
        ancHome.getChildren().clear();
        ancHome.getChildren().add(load);
    }

    public void categoryOnAction(ActionEvent actionEvent) throws IOException {
        AnchorPane load = FXMLLoader.load(getClass().getResource("/view/Category.fxml"));
        ancHome.getChildren().clear();
        ancHome.getChildren().add(load);
    }

}
