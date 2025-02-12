package com.ijse.gdse.finalproject.controller;

import com.ijse.gdse.finalproject.db.DBConnection;
import com.ijse.gdse.finalproject.dto.Gem2DTO;
import com.ijse.gdse.finalproject.dto.GemDTO;
import com.ijse.gdse.finalproject.dto.tm.Gem2TM;
import com.ijse.gdse.finalproject.dto.tm.GemTM;
import com.ijse.gdse.finalproject.model.GemModel;
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
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class GemsController implements Initializable {
    public AnchorPane ancGemsPage;
    public Button btnReset;
    public Button btnDelete;
    public Button btnUpdate;
    public Button btnSave;
    public TextField txtName;
    public TextField txtColour;
    public TextField txtSize;
    public TextField txtPrice;
    public TextField txtQty;
    public TextField txtIsCetified;
    public TextField txtCategoryId;
    public TextField txtCategoryName;


    @FXML
    private TableColumn<GemTM, String> colGemId;

    @FXML
    private TableColumn<GemTM, String> colName;

    @FXML
    private TableColumn<GemTM, String> colColour;

    @FXML
    private TableColumn<GemTM, String> colSize;

    @FXML
    private TableColumn<GemTM, String> colPrize;

    @FXML
    private TableColumn<GemTM, Integer> colQty;

    @FXML
    private TableColumn<GemTM, Boolean> colIsCetified;

    @FXML
    private Label lblGemId;

    @FXML
    private TableView<GemTM> tblGems;

    @FXML
    private TableView<Gem2TM> tblCategory;


    @FXML
    private TableColumn<Gem2TM, String> colCategoryId;

    @FXML
    private TableColumn<GemTM, String> colCategoryItem;

    @FXML
    private TableColumn<Gem2TM, String> colCategoryName;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colGemId.setCellValueFactory(new PropertyValueFactory<>("gem_id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("gem_name"));
        colColour.setCellValueFactory(new PropertyValueFactory<>("gem_color"));
        colSize.setCellValueFactory(new PropertyValueFactory<>("size"));
        colPrize.setCellValueFactory(new PropertyValueFactory<>("price"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colIsCetified.setCellValueFactory(new PropertyValueFactory<>("is_cetified"));
        colCategoryItem.setCellValueFactory(new PropertyValueFactory<>("category_id"));

        colCategoryId.setCellValueFactory(new PropertyValueFactory<>("categoryId"));
        colCategoryName.setCellValueFactory(new PropertyValueFactory<>("categoryName"));


        try {
            refreshPage();
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Fail to load gem id").show();
        }
    }

    private void refreshPage() throws SQLException {
        loadNextGemId();
        loadTableData();
        loadTable2Data();

        btnSave.setDisable(false);

        btnUpdate.setDisable(true);
        btnDelete.setDisable(true);

        txtName.setText("");
        txtColour.setText("");
        txtSize.setText("");
        txtPrice.setText("");
        txtQty.setText("");
        txtIsCetified.setText("");
        txtCategoryId.setText("");

    }


    GemModel gemModel = new GemModel();

    private void loadTableData() throws SQLException {
        ArrayList<GemDTO> gemDTOS = gemModel.getAllGems();

        ObservableList<GemTM> gemTMS = FXCollections.observableArrayList();
        for (GemDTO gemDTO : gemDTOS) {
           GemTM gemTM = new GemTM(
                   gemDTO.getGem_id(),
                   gemDTO.getGem_name(),
                   gemDTO.getGem_color(),
                   gemDTO.getSize(),
                   gemDTO.getPrice(),
                   gemDTO.getQty(),
                   gemDTO.getIs_cetified(),
                   gemDTO.getCategory_id()

            );
            gemTMS.add(gemTM);
        }

       tblGems.setItems(gemTMS);
    }

    private void loadTable2Data() throws SQLException {
        ArrayList<Gem2DTO> Gem2DTOS = GemModel.getAllCategory();

        ObservableList<Gem2TM> Gem2TMS = FXCollections.observableArrayList();

        for (Gem2DTO Gem2DTO : Gem2DTOS) {
            Gem2TM Gem2TM = new Gem2TM(
                    Gem2DTO.getCategoryId(),
                    Gem2DTO.getCategoryName()
            );
            Gem2TMS.add(Gem2TM);
        }

        tblCategory.setItems(Gem2TMS);
    }

    public void loadNextGemId() throws SQLException {

        String nextGemId = gemModel.getNextGemId();
        lblGemId.setText(nextGemId);
    }

    public void btnSaveOnAction(ActionEvent actionEvent) throws SQLException {
        String gem_id = lblGemId.getText();
        String gem_name = txtName.getText();
        String gem_color = txtColour.getText();
        String size = txtSize.getText();
        String price = txtPrice.getText();
        String qtyText = txtQty.getText();
        String isCetifiedText = txtIsCetified.getText();
        String category_id = txtCategoryId.getText();

        txtName.setStyle(txtName.getStyle() + ";-fx-border-color: #7367F0;");
        txtColour.setStyle(txtColour.getStyle() + ";-fx-border-color: #7367F0;");
        txtSize.setStyle(txtSize.getStyle() + ";-fx-border-color: #7367F0;");
        txtPrice.setStyle(txtPrice.getStyle() + ";-fx-border-color: #7367F0;");
        txtQty.setStyle(txtQty.getStyle() + ";-fx-border-color: #7367F0;");
        txtIsCetified.setStyle(txtIsCetified.getStyle() + ";-fx-border-color: #7367F0;");

        // Define Patterns
        String gemNamePattern = "^[A-Za-z ]+$";
        String gemColorPattern = "^[A-Za-z ]+$";
        String sizePattern = "^(\\d+)(\\.\\d{1,2})?$";
        String pricePattern = "^(\\d+)(\\.\\d{1,2})?$";
        String qtyPattern = "^\\d+$";
        String isCetifiedPattern = "^(?i:true|false)$";

        // Validation Checks
        boolean isValidName = gem_name.matches(gemNamePattern);
        boolean isValidColour = gem_color.matches(gemColorPattern);
        boolean isValidSize = size.matches(sizePattern);
        boolean isValidPrice = price.matches(pricePattern);
        boolean isValidQty = qtyText.matches(qtyPattern);
        boolean isValidIsCetified = isCetifiedText.matches(isCetifiedPattern);



        // Highlight invalid fields
        if (!isValidName) {
            txtName.setStyle("-fx-border-color: red;");
            System.out.println("Invalid name");
        }
        if (!isValidColour) {
            txtColour.setStyle("-fx-border-color: red;");
            System.out.println("Invalid colour");
        }
        if (!isValidSize) {
            txtSize.setStyle("-fx-border-color: red;");
            System.out.println("Invalid size");
        }
        if (!isValidPrice) {
            txtPrice.setStyle("-fx-border-color: red;");
            System.out.println("Invalid price");
        }
        if (!isValidQty) {
            txtQty.setStyle("-fx-border-color: red;");
            System.out.println("Invalid quantity");
        }
        if (!isValidIsCetified) {
            txtIsCetified.setStyle("-fx-border-color: red;");
            System.out.println("Invalid certification status");
        }

        // Proceed with saving if all fields are valid
        if (isValidName && isValidColour && isValidSize && isValidPrice && isValidQty && isValidIsCetified) {
            Integer qty = Integer.valueOf(qtyText);
            Boolean is_cetified = Boolean.valueOf(isCetifiedText);

            GemDTO gemDTO = new GemDTO(
                    gem_id,
                    gem_name,
                    gem_color,
                    size,
                    price,
                    qty,
                    is_cetified,
                    category_id
            );

            boolean isSaved = gemModel.saveGem(gemDTO);
            if (isSaved) {
                loadTableData();
                refreshPage();
                new Alert(Alert.AlertType.INFORMATION, "Gem saved...!").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to save gem...!").show();
            }
        }
    }



    public void resetOnAction(ActionEvent actionEvent) throws SQLException {
        refreshPage();
    }

    public void btnDeleteOnAction(ActionEvent actionEvent) throws SQLException {
        String gemId = lblGemId.getText();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> optionalButtonType = alert.showAndWait();

        if (optionalButtonType.isPresent() && optionalButtonType.get() == ButtonType.YES) {

            boolean isDeleted = gemModel.deleteGem(gemId);
            if (isDeleted) {
                refreshPage();
                new Alert(Alert.AlertType.INFORMATION, "Gem deleted...!").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Fail to delete gem...!").show();
            }
        }
    }

    public void btnUpdateOnAction(ActionEvent actionEvent) throws SQLException {
        String gem_id = lblGemId.getText();
        String gem_name = txtName.getText();
        String gem_color = txtColour.getText();
        String size = txtSize.getText();
        String price = txtPrice.getText();
        String qtyText = txtQty.getText();
        String isCetifiedText = txtIsCetified.getText();
        String category_id = txtCategoryId.getText();

        // Define Patterns
        String gemNamePattern = "^[A-Za-z ]+$";
        String gemColorPattern = "^[A-Za-z ]+$";
        String sizePattern = "^(\\d+)(\\.\\d{1,2})?$";
        String pricePattern = "^(\\d+)(\\.\\d{1,2})?$";
        String qtyPattern = "^\\d+$";
        String isCetifiedPattern = "^(?i:true|false)$";

        // Validation Checks
        boolean isValidName = gem_name.matches(gemNamePattern);
        boolean isValidColour = gem_color.matches(gemColorPattern);
        boolean isValidSize = size.matches(sizePattern);
        boolean isValidPrice = price.matches(pricePattern);
        boolean isValidQty = qtyText.matches(qtyPattern);
        boolean isValidIsCetified = isCetifiedText.matches(isCetifiedPattern);

        // Style Reset
        txtName.setStyle(null);
        txtColour.setStyle(null);
        txtSize.setStyle(null);
        txtPrice.setStyle(null);
        txtQty.setStyle(null);
        txtIsCetified.setStyle(null);

        // Highlight invalid fields
        if (!isValidName) {
            txtName.setStyle("-fx-border-color: red;");
            System.out.println("Invalid name");
        }
        if (!isValidColour) {
            txtColour.setStyle("-fx-border-color: red;");
            System.out.println("Invalid colour");
        }
        if (!isValidSize) {
            txtSize.setStyle("-fx-border-color: red;");
            System.out.println("Invalid size");
        }
        if (!isValidPrice) {
            txtPrice.setStyle("-fx-border-color: red;");
            System.out.println("Invalid price");
        }
        if (!isValidQty) {
            txtQty.setStyle("-fx-border-color: red;");
            System.out.println("Invalid quantity");
        }
        if (!isValidIsCetified) {
            txtIsCetified.setStyle("-fx-border-color: red;");
            System.out.println("Invalid certification status");
        }

        // Proceed with saving if all fields are valid
        if (isValidName && isValidColour && isValidSize && isValidPrice && isValidQty && isValidIsCetified) {
            Integer qty = Integer.valueOf(qtyText);
            Boolean is_cetified = Boolean.valueOf(isCetifiedText);

            GemDTO gemDTO = new GemDTO(
                    gem_id,
                    gem_name,
                    gem_color,
                    size,
                    price,
                    qty,
                    is_cetified,
                    category_id
            );

            boolean isSaved = gemModel.updateGem(gemDTO);
            if (isSaved) {
                refreshPage();
                new Alert(Alert.AlertType.INFORMATION, "Gem saved...!").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to save gem...!").show();
            }
        }

    }


    public void onClickGemTable(MouseEvent mouseEvent) {
        GemTM gemTM = tblGems.getSelectionModel().getSelectedItem();
        if (gemTM != null) {
            lblGemId.setText(gemTM.getGem_id());
            txtName.setText(gemTM.getGem_name());
            txtColour.setText(gemTM.getGem_color());
            txtSize.setText(gemTM.getSize());
            txtPrice.setText(gemTM.getPrice());
            txtQty.setText(String.valueOf(gemTM.getQty()));
            txtIsCetified.setText(String.valueOf(gemTM.getIs_cetified()));
            txtCategoryId.setText(String.valueOf(gemTM.getCategory_id()));

            btnSave.setDisable(true);

            btnDelete.setDisable(false);
            btnUpdate.setDisable(false);
        }
    }


    public void onClickTable(MouseEvent mouseEvent) {
        Gem2TM Gem2TM = tblCategory.getSelectionModel().getSelectedItem();
        if (Gem2TM != null) {
            txtCategoryId.setText(Gem2TM.getCategoryId());


        }
    }

    public void genarateAllGemsReportOnAction(ActionEvent actionEvent) {

            try {
                // Load and compile the JRXML file
                JasperReport jasperReport = JasperCompileManager.compileReport(
                        getClass().getResourceAsStream("/report/gem_report.jrxml"));

                // Fetch database connection
                Connection connection = DBConnection.getInstance().getConnection();

                // Fill report with data
                JasperPrint jasperPrint = JasperFillManager.fillReport(
                        jasperReport,
                        null,
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


