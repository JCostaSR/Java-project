package Controller;

import Model.Inventory;
import Controller.AddProductController;
import com.example.demo1.MainGUI;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Logger;

import static java.util.logging.Logger.getLogger;

/*  Parts Table
    Products Table
    Search TextFields
    Add Part Buttons
    Add Product Buttons
    Exit Button
    Search Parts
    Search Products
    Add Part
    Modify Product
    Delete Part
    Add Product
    Modify Product
    Delete Product
    Exit Program
    Alert - Popup Warning
    Alert - Popup Confirm
    Columns and Table for Parts
    Columns and Table for Products
 */

/**
 * @author John C. Costa Sr.
 */

public class MainGUIController implements Initializable {

    /** Parts Table */
    public TableView<Part> partsTable;

    /** Part Id column */
    public TableColumn<Inventory, Integer> partId;

    /** Part Name column */
    public TableColumn<Inventory, String> partName;

    /** Part Inventory column */
    public TableColumn<Inventory, Integer> partStock;

    /** Part Price column */
    public TableColumn<Inventory, Double> partPrice;

    /** Part Maximum column */
    public TableColumn<Inventory, Integer> partMax;

    /** Part Minimum column */
    public TableColumn<Inventory, Integer> partMin;

    /** Part Label column */
    public TableColumn<Inventory, String> partLabel;

    /** Products Table */
    public TableView<Product> productsTable;

    /** Product Id column */
    public TableColumn<Product, Integer> productId;

    /** Product Name column */
    public TableColumn<Product, String> productName;

    /** Product Stock column */
    public TableColumn<Product, Integer> productStock;

    /** Product Price solumn */
    public TableColumn<Product, Double> productPrice;

    /** Search Part TextField to find Parts*/
    public TextField searchPart;

    /** Search Product TextField to find Products */
    public TextField searchProduct;

    /** Add Part Button */
    @FXML
    private Button INVAddPart;

    /** Modify Part Button */
    @FXML
    private Button INVModifyPart;

    /** Delete Part Button */
    public Button deletePart;

    /** Add Product Button */
    @FXML
    private Button INVAddProduct;

    /** Modify Product Button */
    @FXML
    private Button INVModifyProduct;

    /** Delete Product Button */
    public Button deleteProduct;

    /** Exit Button */
    public Button exit;
    Part pro;



    /** Add Part changes scenes to the add part window. */
    public void onClickAddPart(ActionEvent actionEvent) throws IOException {

        Stage stage = (Stage) INVAddPart.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(MainGUI.class.getResource("addPart.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("");
        stage.setScene(scene);
    }

    /** Modify Part changes scenes to the modify part window. */
    public void onClickModifyPart(ActionEvent actionEvent) throws IOException {

        if (partsTable.getSelectionModel().isEmpty()) {
            popUp("Warning!", "No Part Selected", "Please choose part from the list");
            return;
        } else {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/com/example/demo1/modifyPart.fxml"));
            Parent parentIn = loader.load();
            int index = partsTable.getSelectionModel().getSelectedIndex();
            ModifyPartController modifyPartController = loader.getController();
            modifyPartController.setInHouseData(index, partsTable.getSelectionModel().getSelectedItem());
            Scene scene = new Scene(parentIn, 600, 600);
            Stage stage = (Stage) (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        }
    }

    /** Delete Part removes a part from the parts table. */
    public void onClickDeletePart(ActionEvent actionEvent) {
        if (partsTable.getSelectionModel().isEmpty()) {
            popUp("Warning!", "No Part Selected", "Please choose part from the list");
            return;
        } else if (confirmPopUp("Warning!", "Would you like to delete this item?")) {
            int selectedPart = partsTable.getSelectionModel().getSelectedIndex();
            partsTable.getItems().remove(selectedPart);

        }

    }

    /** Add Product changes scenes to the add product window. */
    public void onClickAddProduct(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) INVAddProduct.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(MainGUI.class.getResource("addProduct.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("");
        stage.setScene(scene);
        stage.show();
    }

    /** Modify Product changes scenes to the modify product window. */
    public void onClickModifyProduct(ActionEvent actionEvent) throws IOException{
        if (productsTable.getSelectionModel().isEmpty()) {
            popUp("Warning!", "No Product Selected", "Please choose product from the list");
            return;
        } else {

            Product selectedProduct = productsTable.getSelectionModel().getSelectedItem();
            Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/demo1/modifyProduct.fxml"));
            Parent scene = loader.load();
            ModifyProductController modifyProductController = loader.getController();
            int index = productsTable.getSelectionModel().getSelectedIndex();
            modifyProductController.setProductData(index, selectedProduct);


            Product product = (Product) productsTable.getSelectionModel().getSelectedItem();

            System.out.println("Product Listsize " + product.getAllAssociatedParts().size());
            stage.setTitle("");
            stage.setScene(new Scene(scene));
            stage.show();
        }
    }

    /** Delete Product removes a product from the product table. */
    public void onClickDeleteProduct(ActionEvent actionEvent) {
            Product selectedProduct = productsTable.getSelectionModel().getSelectedItem();
            if (productsTable.getSelectionModel().isEmpty()) {
            popUp("Warning!", "No Product Selected", "Please choose product from the list");
            return;
            }
            ObservableList<Part> associatedParts = selectedProduct.getAllAssociatedParts();
            if(associatedParts.size() >= 1){
            popUp("Warning!", "Unable to remove", "Product contains associated parts");
            return;

            }
            if(confirmPopUp("Warning!", "Would you like to delete this item?")){
            //int selectedPart = productsTable.getSelectionModel().getSelectedIndex();
            //productsTable.getItems().remove(selectedPart);
            Inventory.deleteProduct(selectedProduct);

            }

    }

    /** Exit Program */
    public void exitButton(ActionEvent actionEvent) throws IOException{
        confirmPopUp("Exit", "Are you sure you would like to close the program?");
        {
            System.exit(0);
        }
    }

    /** Alert - Popup Warning */
    static void popUp(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    /** Alert - Popup Confirm */
    static boolean confirmPopUp(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText("Confirm");
        alert.setContentText(content);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            return true;
        }else {
            return false;
        }
    }

    /** Search Parts to find parts */
    public void searchParts(ActionEvent actionEvent) {

        try {
            String partName = searchPart.getText();
            ObservableList<Part> foundParts = Inventory.lookupPart(partName);

            if (foundParts.size() == 0) {

                int partId = Integer.parseInt(partName);
                Part pt = Inventory.lookupPart(partId);
                if (pt != null)
                    foundParts.add(pt);
                if(pt == null){
                    confirmPopUp("Input Error", "Could not find part with a name or ID matching " + searchPart.getText());
                    return;
                }
                searchPart.setText("");
            }
            partsTable.setItems(foundParts);

        }catch (Exception exception){
        confirmPopUp("Input Error", "Could not find part with a name or ID matching " + searchPart.getText());
        return;
        }

    }

    /** Search Products to find products */
    public void searchProducts(ActionEvent actionEvent) {

        try {
        String productName = searchProduct.getText();
        ObservableList<Product> temp = Inventory.lookupProduct(productName);

        if (temp.size() == 0) {

            int productId = Integer.parseInt(productName);
            Product pd = Inventory.lookupProduct(productId);
            if (pd != null)
                temp.add(pd);
            if(pd == null){
                confirmPopUp("Input Error", "Could not find product with a name or ID matching " + searchProduct.getText());
                return;
            }
            searchProduct.setText("");
        }
        productsTable.setItems(temp);

    }catch (Exception exception){
        confirmPopUp("Input Error", "Could not find product with a name or ID matching " + searchProduct.getText());
        return;
    }

}

    /** initialize Columns and Tables for Parts and Products */
    @Override
    public void initialize(URL location, ResourceBundle resourceBundle) {

        // Columns and Table for Parts
        partsTable.setItems(Inventory.getAllParts());
        partId.setCellValueFactory(new PropertyValueFactory<>("id"));
        partName.setCellValueFactory(new PropertyValueFactory<>("name"));
        partStock.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPrice.setCellValueFactory(new PropertyValueFactory<>("price"));

        // Columns and Table for Products
        productsTable.setItems(Inventory.getAllProducts());
        productId.setCellValueFactory(new PropertyValueFactory<>("id"));
        productName.setCellValueFactory(new PropertyValueFactory<>("name"));
        productStock.setCellValueFactory(new PropertyValueFactory<>("stock"));
        productPrice.setCellValueFactory(new PropertyValueFactory<>("price"));

    }

}
