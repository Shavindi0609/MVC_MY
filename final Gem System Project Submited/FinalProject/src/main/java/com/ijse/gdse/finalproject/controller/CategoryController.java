package com.ijse.gdse.finalproject.controller;

import com.ijse.gdse.finalproject.dto.CategoryDTO;
import com.ijse.gdse.finalproject.dto.CustomerDTO;
import com.ijse.gdse.finalproject.dto.tm.CategoryTM;
import com.ijse.gdse.finalproject.dto.tm.CustomerTM;
import com.ijse.gdse.finalproject.model.CategoryModel;
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
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class CategoryController implements Initializable {
    public AnchorPane ancCategory;
    public Label lblCategoryId;
    public TableView tblCategory;
    public Button btnReset;
    public Button btnDelete;
    public Button btnUpdate;
    public Button btnSave;
    public TextField txtCategoryName;
    public Label lblDate;
    public Label lblTime;


    @FXML
    private TableColumn<CategoryTM, String> colCategoryId;

    @FXML
    private TableColumn<CategoryTM, String> colCategoryName;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        lblDate.setText(LocalDate.now().toString());

        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("hh:mm a");
        lblTime.setText(LocalTime.now().format(timeFormatter));
        
        colCategoryId.setCellValueFactory(new PropertyValueFactory<>("categoryId"));
        colCategoryName.setCellValueFactory(new PropertyValueFactory<>("categoryName"));
        try {
            refreshPage();
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Fail to load category id").show();
        }

    }

    private void refreshPage() throws SQLException {
        loadNextCategoryId();
        loadCategoryTableData();

        btnSave.setDisable(false);

        btnUpdate.setDisable(true);
        btnDelete.setDisable(true);

        txtCategoryName.setText("");

    }

    CategoryModel categoryModel = new CategoryModel();

    private void loadCategoryTableData() throws SQLException {
        ArrayList<CategoryDTO> categoryDTOS = categoryModel.getAllCategory();

        ObservableList<CategoryTM> categoryTMS = FXCollections.observableArrayList();

        for (CategoryDTO categoryDTO : categoryDTOS) {
            CategoryTM categoryTM = new CategoryTM(
                    categoryDTO.getCategoryId(),
                    categoryDTO.getCategoryName()

            );
            categoryTMS.add(categoryTM);
        }

        tblCategory.setItems(categoryTMS);
        //  System.out.println("Table Data: " + customerTMS);
    }

    private void loadNextCategoryId() throws SQLException {
        String nextCategoryId = categoryModel.getNextCategoryId();
        lblCategoryId.setText(nextCategoryId);
    }

    public void btnSaveOnAction(ActionEvent actionEvent) throws SQLException {
        String categoryId = lblCategoryId.getText();
        String categoryName = txtCategoryName.getText();

        txtCategoryName.setStyle(txtCategoryName.getStyle() + ";-fx-border-color: #7367F0;");

        String namePattern = "^[A-Za-z ]+$";

        boolean isValidName = categoryName.matches(namePattern);

        if (!isValidName) {
            System.out.println(txtCategoryName.getStyle());
            txtCategoryName.setStyle(txtCategoryName.getStyle() + ";-fx-border-color: red;");
            System.out.println("Invalid category name.............");
//            return;
        }

        if (isValidName) {
            CategoryDTO categoryDTO = new CategoryDTO(
                    categoryId,
                    categoryName
            );

            boolean isSaved = categoryModel.saveCategory(categoryDTO);
            if (isSaved) {
                refreshPage();
                new Alert(Alert.AlertType.INFORMATION, "Category saved...!").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Fail to save category...!").show();
            }
        }
    }

    public void OnClickTable(MouseEvent mouseEvent) {
        CategoryTM categoryTM = (CategoryTM) tblCategory.getSelectionModel().getSelectedItem();
        if (categoryTM != null) {
            lblCategoryId.setText(categoryTM.getCategoryId());
            txtCategoryName.setText(categoryTM.getCategoryName());


            btnSave.setDisable(true);

            btnDelete.setDisable(false);
            btnUpdate.setDisable(false);
        }
    }

    public void btnDeleteOnAction(ActionEvent actionEvent) throws SQLException {
        String categoryId = lblCategoryId.getText();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> optionalButtonType = alert.showAndWait();

        if (optionalButtonType.isPresent() && optionalButtonType.get() == ButtonType.YES) {

            boolean isDeleted = categoryModel.deleteCategory(categoryId);
            if (isDeleted) {
                refreshPage();
                new Alert(Alert.AlertType.INFORMATION, "Category deleted...!").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Fail to delete category...!").show();
            }
        }
    }

    public void btnUpdateOnAction(ActionEvent actionEvent) throws SQLException {
        String categoryId = lblCategoryId.getText();
        String categoryName = txtCategoryName.getText();

        txtCategoryName.setStyle(txtCategoryName.getStyle() + ";-fx-border-color: #7367F0;");

        String namePattern = "^[A-Za-z ]+$";

        boolean isValidName = categoryName.matches(namePattern);

        if (!isValidName) {
            System.out.println(txtCategoryName.getStyle());
            txtCategoryName.setStyle(txtCategoryName.getStyle() + ";-fx-border-color: red;");
            System.out.println("Invalid name.............");
//            return;
        }

        if (isValidName) {
            CategoryDTO categoryDTO = new CategoryDTO(
                    categoryId,
                    categoryName
            );

            boolean isUpdate = categoryModel.updateCategory(categoryDTO);
            if (isUpdate) {
                refreshPage();
                loadCategoryTableData();
                new Alert(Alert.AlertType.INFORMATION, "Category update...!").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Fail to update category...!").show();
            }
        }

    }
    public void resetOnAction(ActionEvent actionEvent) throws SQLException {
        refreshPage();
    }

    public void OnclickHome(MouseEvent mouseEvent) throws IOException {
        AnchorPane load = FXMLLoader.load(getClass().getResource("/view/HomePage.fxml"));
        ancCategory.getChildren().clear();
        ancCategory.getChildren().add(load);
    }
}

