package com.ijse.gdse.finalproject.controller;

import com.ijse.gdse.finalproject.bo.BOFactory;
import com.ijse.gdse.finalproject.bo.custom.GemBO;
import com.ijse.gdse.finalproject.bo.custom.SupplierOrderBO;
import com.ijse.gdse.finalproject.dao.DAOFactory;
import com.ijse.gdse.finalproject.dao.custom.GemDAO;
import com.ijse.gdse.finalproject.dao.custom.SupplierDAO;
import com.ijse.gdse.finalproject.dao.custom.SupplierOrderDAO;
import com.ijse.gdse.finalproject.dto.*;
import com.ijse.gdse.finalproject.dto.tm.SupplierOrderCartTM;
import com.ijse.gdse.finalproject.dao.custom.impl.GemDAOImpl;
import com.ijse.gdse.finalproject.dao.custom.impl.SupplierDAOImpl;
import com.ijse.gdse.finalproject.dao.custom.impl.SupplierOrderDAOImpl;
import com.ijse.gdse.finalproject.entity.Gem;
import com.ijse.gdse.finalproject.entity.Supplier;
import com.ijse.gdse.finalproject.entity.SupplierOrder;
import com.ijse.gdse.finalproject.entity.SupplierOrderDetails;
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

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class SupplierOrdersController implements Initializable {

    public AnchorPane ancSupplierOrder;
    public Label lblTotalAmount;
    public Label lblTime;
    public Label lblDate;
    private double totalAmount = 0.0;



    @FXML
    private ComboBox<String> cmbGemId;

    @FXML
    private ComboBox<String> cmbSupplierId;

    @FXML
    private TableColumn<?, ?> colAction;

    @FXML
    private TableColumn<SupplierOrderCartTM, Integer> colCartQty;

    @FXML
    private TableColumn<SupplierOrderCartTM, String> colItemId;

    @FXML
    private TableColumn<SupplierOrderCartTM, String> colName;

    @FXML
    private TableColumn<SupplierOrderCartTM, Double> colTotal;

    @FXML
    private TableColumn<SupplierOrderCartTM, String> colUnitPrice;

    @FXML
    private Label lblSupplierName;

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
    private Label lblSupplierPaymentId;


    @FXML
    private Label lblTotalAmount1;


    @FXML
    private TableView<SupplierOrderCartTM> tblSupplierOrder;

    @FXML
    private TextField txtAddToStockQty;

    @FXML
    private ComboBox<String> cmbPaymentmethod;



  SupplierOrderBO supplierOrderBO = (SupplierOrderBO) BOFactory.getInstance().getBO(BOFactory.BOType.SUPPLIERORDERS);

    private final ObservableList<SupplierOrderCartTM> supplierOrderCartTMS = FXCollections.observableArrayList();

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
            new Alert(Alert.AlertType.ERROR, "Fail to load data..!").show();
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
        tblSupplierOrder.setItems(supplierOrderCartTMS);

        System.out.println("table column value set");
    }

    private void refreshPage() throws SQLException {
        lblOrderId.setText(supplierOrderBO.getNextSupplierOrderId());
        lblOrderDate.setText(LocalDate.now().toString());
        lblSupplierPaymentId.setText(supplierOrderBO.getNextSupplierPaymentId());

        loadSupplierIds();
        loadGemId();
        System.out.println("ids load");

        cmbSupplierId.getSelectionModel().clearSelection();
        cmbGemId.getSelectionModel().clearSelection();
        lblGemName.setText("");
        lblGemQty.setText("");
        lblGemUnitPrice.setText("");
        txtAddToStockQty.setText("");
        lblSupplierName.setText("");

        // Clear the cart observable list
        supplierOrderCartTMS.clear();

        // Refresh the table to reflect changes
        tblSupplierOrder.refresh();
        lblTotalAmount.setText("0.00");
    }

    private void loadGemId() throws SQLException {
        ArrayList<String> gemIds = supplierOrderBO.getAllGemIds();
        cmbGemId.setItems(FXCollections.observableArrayList(gemIds));
    }

    private void loadSupplierIds() throws SQLException {
        ArrayList<String> supplierIds = supplierOrderBO.getAllSupplierIds();
        cmbSupplierId.setItems(FXCollections.observableArrayList(supplierIds));
    }

    public void addStockOnAction(ActionEvent event) {
        String selectedGemId = cmbGemId.getValue();
        if (selectedGemId == null) {
            new Alert(Alert.AlertType.ERROR, "Please select a gem!").show();
            return;
        }

        String cartQtyString = txtAddToStockQty.getText();
        if (!cartQtyString.matches("^[0-9]+$")) {
            new Alert(Alert.AlertType.ERROR, "Enter a valid quantity!").show();
            return;
        }

        int cartQty = Integer.parseInt(cartQtyString);
        int qtyOnHand = Integer.parseInt(lblGemQty.getText());
        if (cartQty > qtyOnHand) {
//            new Alert(Alert.AlertType.ERROR, "Insufficient stock!").show();
//            return;
        }

        double unitPrice = Double.parseDouble(lblGemUnitPrice.getText());
        double total = unitPrice * cartQty;

        for (SupplierOrderCartTM supplierOrderCartTM : supplierOrderCartTMS) {
            if (supplierOrderCartTM.getGemId().equals(selectedGemId)) {
                int newQty = supplierOrderCartTM.getCartQuantity() + cartQty;
                supplierOrderCartTM.setCartQuantity(newQty);
                supplierOrderCartTM.setTotal(unitPrice * newQty);
                tblSupplierOrder.refresh();
                updateTotalAmount();
                return;
            }
        }

        Button btn = new Button("Remove");
        SupplierOrderCartTM newSupplierOrderCartTM = new SupplierOrderCartTM(selectedGemId, lblGemName.getText(), cartQty, unitPrice, total, btn);
        btn.setOnAction(e -> {
            supplierOrderCartTMS.remove(newSupplierOrderCartTM);
            updateTotalAmount();
        });

        supplierOrderCartTMS.add(newSupplierOrderCartTM);
        updateTotalAmount();
    }

    private void updateTotalAmount() {
        totalAmount = supplierOrderCartTMS.stream().mapToDouble(SupplierOrderCartTM::getTotal).sum();
        lblTotalAmount.setText(String.format("%.2f", totalAmount));
    }

    public void getOrderOnAction(ActionEvent actionEvent) throws SQLException {
        if (tblSupplierOrder.getItems().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Cart is empty").show();
            return;
        }
        if (cmbSupplierId.getSelectionModel().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Please select supplier for place order..!").show();
            return;
        }
        if (cmbPaymentmethod.getSelectionModel().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Select a payment method!").show();
            return;
        }

        String orderId = lblOrderId.getText();
        String paymentId = lblSupplierPaymentId.getText();
        Date orderDate = Date.valueOf(lblOrderDate.getText());
        String supplierId = cmbSupplierId.getValue();
        String paymentMethod = cmbPaymentmethod.getValue();

//        System.out.println("combobox value hari");
//        String supplierOrderId = lblOrderId.getText();
//        Date dateOfOrder = Date.valueOf(lblOrderDate.getText());
//        String supplierId = cmbSupplierId.getValue();
//        //Integer totalAmount = Integer.valueOf(lblTotalAmount.getText());

        ArrayList<SupplierOrderDetails> supplierorderDetails = new ArrayList<>();
        for (SupplierOrderCartTM supplierOrderCartTM : supplierOrderCartTMS) {
            supplierorderDetails.add(new SupplierOrderDetails(orderId, supplierOrderCartTM.getGemId(), supplierOrderCartTM.getCartQuantity(), supplierOrderCartTM.getUnitPrice()));
        }

        SupplierOrder supplierOrder = new SupplierOrder(orderId, supplierId, orderDate, paymentId, totalAmount, paymentMethod, supplierorderDetails);

        if (supplierOrderBO.saveSupplierOrder(supplierOrder)) {
            new Alert(Alert.AlertType.INFORMATION, "Order placed successfully!").show();
            refreshPage();
        } else {
            new Alert(Alert.AlertType.ERROR, "Order placement failed!").show();
        }
    }
    public void resetOnAction(ActionEvent actionEvent) throws SQLException {
        refreshPage();
    }

    public void cmbSupplierIdOnAction(ActionEvent actionEvent) throws SQLException {
        String selectedSupplierId = cmbSupplierId.getSelectionModel().getSelectedItem();
        Supplier supplier = supplierOrderBO.findByIdSupplier(selectedSupplierId);

        // If customer found (customerDTO not null)
        if (supplier != null) {

            // FIll customer related labels
            lblSupplierName.setText(supplier.getName());
        }
    }

    public void cmbGemIdOnAction(ActionEvent actionEvent) throws SQLException {
        String selectedGemId = cmbGemId.getSelectionModel().getSelectedItem();
        Gem gem = supplierOrderBO.findByIdGem(selectedGemId);

        // If item found (itemDTO not null)
        if (gem != null) {

            // FIll item related labels
            lblGemName.setText(gem.getGem_name());
            lblGemQty.setText(String.valueOf(gem.getQty()));
            lblGemUnitPrice.setText(String.valueOf(gem.getPrice()));
        }
    }

    public void ClickHome(MouseEvent mouseEvent) throws IOException {
        AnchorPane load = FXMLLoader.load(getClass().getResource("/view/HomePage.fxml"));
        ancSupplierOrder.getChildren().clear();
        ancSupplierOrder.getChildren().add(load);
    }

}

