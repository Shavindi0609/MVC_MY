package com.ijse.gdse.finalproject.controller;

import com.ijse.gdse.finalproject.bo.BOFactory;
import com.ijse.gdse.finalproject.bo.custom.CustomerBO;
import com.ijse.gdse.finalproject.bo.custom.GemBO;
import com.ijse.gdse.finalproject.bo.custom.OrdersBO;
import com.ijse.gdse.finalproject.dao.DAOFactory;
import com.ijse.gdse.finalproject.dao.custom.CustomerDAO;
import com.ijse.gdse.finalproject.dao.custom.GemDAO;
import com.ijse.gdse.finalproject.dao.custom.OrdersDAO;
import com.ijse.gdse.finalproject.db.DBConnection;
import com.ijse.gdse.finalproject.dto.*;
import com.ijse.gdse.finalproject.dto.tm.CartTM;
import com.ijse.gdse.finalproject.dao.custom.impl.CustomerDAOImpl;
import com.ijse.gdse.finalproject.dao.custom.impl.GemDAOImpl;
import com.ijse.gdse.finalproject.dao.custom.impl.OrdersDAOImpl;
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
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.view.JasperViewer;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class OrdersController implements Initializable {


    public Label lblPaymentId;
    public ComboBox<String> cmbPaymentmethod;
    public Label lblTotalAmount;
    public Label txtTotalAmount;
    public AnchorPane ancOrders;
    public Label lblTime;
    public Label lblDate;
    private double totalAmount = 0.0;

    @FXML
    private Button btnAddToCart;

    @FXML
    private ComboBox<String> cmbCustomerId;

    @FXML
    private ComboBox<String> cmbGemId;

    @FXML
    private TableColumn<CartTM, ?> colAction;

    @FXML
    private TableColumn<CartTM, Integer> colCartQty;

    @FXML
    private TableColumn<CartTM, String> colItemId;

    @FXML
    private TableColumn<CartTM, String> colName;

    @FXML
    private TableColumn<CartTM, Double> colTotal;

    @FXML
    private TableColumn<CartTM, Double> colUnitPrice;

    @FXML
    private Label lblCustomerName;

    @FXML
    private Label lblGemName;

    @FXML
    private Label lblGemQty;

    @FXML
    private Label lblGemUnitPrice;

    @FXML
    private Label lblOrderDate;

    @FXML
    private Label lblOrderId;

    @FXML
    private TableView<CartTM> tblCart;

    @FXML
    private TextField txtAddToCartQty;


    OrdersBO ordersBO = (OrdersBO) BOFactory.getInstance().getBO(BOFactory.BOType.ORDERS);


    private final ObservableList<CartTM> cartTMS = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        lblDate.setText(LocalDate.now().toString());

        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("hh:mm a");
        lblTime.setText(LocalTime.now().format(timeFormatter));

        setCellValues();
        try {
            refreshPage();
            setupPaymentMethods();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to load data!").show();
        }
    }

    private void setupPaymentMethods() {
        ObservableList<String> paymentMethods = FXCollections.observableArrayList("Card", "Cash");
        cmbPaymentmethod.setItems(paymentMethods);
        cmbPaymentmethod.getSelectionModel().select("Cash");
    }

    private void setCellValues() {
        colItemId.setCellValueFactory(new PropertyValueFactory<>("gemId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("gemName"));
        colCartQty.setCellValueFactory(new PropertyValueFactory<>("cartQuantity"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));
        colAction.setCellValueFactory(new PropertyValueFactory<>("removeBtn"));
        tblCart.setItems(cartTMS);
    }

    private void refreshPage() throws SQLException {
        lblOrderId.setText(ordersBO.getNextOrderId());
        lblOrderDate.setText(LocalDate.now().toString());
        lblPaymentId.setText(ordersBO.getNextPaymentId());

        loadCustomerIds();
        loadGemIds();

        cmbCustomerId.getSelectionModel().clearSelection();
        cmbGemId.getSelectionModel().clearSelection();
        lblGemName.setText("");
        lblGemQty.setText("");
        lblGemUnitPrice.setText("");
        txtAddToCartQty.setText("");
        lblCustomerName.setText("");

        cartTMS.clear();
        tblCart.refresh();
        lblTotalAmount.setText("0.00");
    }

     private void loadGemIds() throws SQLException {
        ArrayList<String> gemIds = ordersBO.getAllGemIds();
        cmbGemId.setItems(FXCollections.observableArrayList(gemIds));

//        try {
//            ArrayList<String> arrayList = ordersBO.getAllGemIds();
//            ObservableList<String> allNames = FXCollections.observableArrayList();
//            allNames.addAll(arrayList);
//
//            brandNameCmb.setItems(allNames);
//
//        } catch (SQLException e) {
//            Alert alert = new Alert(Alert.AlertType.ERROR,"Fail to Load Brand Name");
//            alert.showAndWait();
//        }
    }

    private void loadCustomerIds() throws SQLException {
        ArrayList<String> customerIds = ordersBO.getAllCustomerIds();
        cmbCustomerId.setItems(FXCollections.observableArrayList(customerIds));
    }

    public void addToCartOnAction(ActionEvent event) {
        String selectedGemId = cmbGemId.getValue();
        if (selectedGemId == null) {
            new Alert(Alert.AlertType.ERROR, "Please select a gem!").show();
            return;
        }

        String cartQtyString = txtAddToCartQty.getText();
        if (!cartQtyString.matches("^[0-9]+$")) {
            new Alert(Alert.AlertType.ERROR, "Enter a valid quantity!").show();
            return;
        }

        int cartQty = Integer.parseInt(cartQtyString);
        int qtyOnHand = Integer.parseInt(lblGemQty.getText());
        if (cartQty > qtyOnHand) {
            new Alert(Alert.AlertType.ERROR, "Insufficient stock!").show();
            return;
        }

        double unitPrice = Double.parseDouble(lblGemUnitPrice.getText());
        double total = unitPrice * cartQty;

        for (CartTM cartTM : cartTMS) {
            if (cartTM.getGemId().equals(selectedGemId)) {
                int newQty = cartTM.getCartQuantity() + cartQty;
                cartTM.setCartQuantity(newQty);
                cartTM.setTotal(unitPrice * newQty);
                tblCart.refresh();
                updateTotalAmount();
                return;
            }
        }

        Button btn = new Button("Remove");
        CartTM newCartTM = new CartTM(selectedGemId, lblGemName.getText(), cartQty, unitPrice, total, btn);
        btn.setOnAction(e -> {
            cartTMS.remove(newCartTM);
            updateTotalAmount();
        });

        cartTMS.add(newCartTM);
        updateTotalAmount();
    }

    private void updateTotalAmount() {
        totalAmount = cartTMS.stream().mapToDouble(CartTM::getTotal).sum();
        lblTotalAmount.setText(String.format("%.2f", totalAmount));
    }

    public void placeOrderOnAction(ActionEvent actionEvent) throws SQLException {
        if (tblCart.getItems().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Cart is empty!").show();
            return;
        }

        if (cmbCustomerId.getSelectionModel().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Select a customer!").show();
            return;
        }

        if (cmbPaymentmethod.getSelectionModel().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Select a payment method!").show();
            return;
        }

        String orderId = lblOrderId.getText();
        String paymentId = lblPaymentId.getText();
        Date orderDate = Date.valueOf(lblOrderDate.getText());
        String customerId = cmbCustomerId.getValue();
        String paymentMethod = cmbPaymentmethod.getValue();

        ArrayList<OrderDetailsDTO> orderDetails = new ArrayList<>();
        for (CartTM cartTM : cartTMS) {
            orderDetails.add(new OrderDetailsDTO(orderId, cartTM.getGemId(), cartTM.getCartQuantity(), cartTM.getUnitPrice()));
        }

        OrdersDTO ordersDTO = new OrdersDTO(orderId, customerId, orderDate, paymentId, totalAmount, paymentMethod, orderDetails);

        if (ordersBO.saveOrder(ordersDTO)) {
            new Alert(Alert.AlertType.INFORMATION, "Order placed successfully!").show();
            refreshPage();
        } else {
            new Alert(Alert.AlertType.ERROR, "Order placement failed!").show();
        }
    }


    public void resetOnAction(ActionEvent actionEvent) throws SQLException {
        refreshPage();
    }


    public void cmbCustomerIdOnAction(ActionEvent actionEvent) throws SQLException {
        String selectedCustomerId = cmbCustomerId.getValue();
        CustomerDTO customerDTO = ordersBO.findByIdCustomer(selectedCustomerId);
        if (customerDTO != null) {
            lblCustomerName.setText(customerDTO.getName());
        }
    }

    public void cmbItemIdOnAction(ActionEvent actionEvent) throws SQLException {
        String selectedGemId = cmbGemId.getValue();
        GemDTO gemDTO = ordersBO.findByIdGem(selectedGemId);
        if (gemDTO != null) {
            lblGemName.setText(gemDTO.getGem_name());
            lblGemQty.setText(String.valueOf(gemDTO.getQty()));
            lblGemUnitPrice.setText(String.valueOf(gemDTO.getPrice()));
        }
    }

    public void OnclickHome(MouseEvent mouseEvent) throws IOException {
        AnchorPane load = FXMLLoader.load(getClass().getResource("/view/HomePage.fxml"));
        ancOrders.getChildren().clear();
        ancOrders.getChildren().add(load);
    }

    public void genarateAllOrdersReportOnAction(ActionEvent actionEvent) {
        try {
            // Load and compile the JRXML file
            JasperReport jasperReport = JasperCompileManager.compileReport(
                    getClass().getResourceAsStream("/report/CustomerOrdersReport.jrxml"));

            // Fetch database connection
            Connection connection = DBConnection.getInstance().getConnection();

            // Fill report with data
            JasperPrint jasperPrint = JasperFillManager.fillReport(
                    jasperReport,
                    null, // Parameters (null if no parameters are required)
                    connection
            );

            // Display the report
            JasperViewer.viewReport(jasperPrint, false);

        } catch (JRException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to generate report!").show();
            e.printStackTrace();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Database connection error!").show();
            e.printStackTrace();
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Unexpected error occurred!").show();
            e.printStackTrace();
        }
    }
    }
