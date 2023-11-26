package Controller;

import Model.Inventory;
import com.example.demo1.MainGUI;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
    Modify Product TextFields
    Products Parts Table
    Products Parts Table
    Product Search
    GUI Buttons
    Array lists
    Saves Modify Products
    Adds Products to Associated Parts
    Removes Associate Parts
    Cancels Modified Products
    Searches for Products
    Updates Product Part Table
    Updates Associated Parts Table
    Product Data
    Columns and Table for parts
    Columns and Table for associated parts
 */

/**
 * @author John C. Costa Sr.
 */

public class ModifyProductController implements Initializable {

    /** Modify Product TextFields */
    public TextField modifyProductId;

    /** Modify Product Name */
    public TextField modifyProductName;

    /** Modify Product Inventory */
    public TextField modifyProductInv;

    /** Modify Product Price */
    public TextField modifyProductPrice;

    /** Modify Product Maximum */
    public TextField modifyProductMax;

    /** Modify product Minimum */
    public TextField modifyProductMin;

    /** Products Parts Table */
    public TableView <Part> productPartsTable;

    /** Part Id column */
    public TableColumn <Part, Integer> productIdAddProduct;

    /** Part Name column */
    public TableColumn <Part, String> productNameAddProduct;

    /** Part Inventory column */
    public TableColumn <Part, Integer> productInvAddProduct;

    /** Part Price column */
    public TableColumn <Part, Double> productPriceAddProduct;

    /** Associated Parts Table */
    public TableView <Part> associatedPartsTable;

    /** Associated part Id column */
    public TableColumn <Product, Integer> productIdAddAssociatedPart;

    /** Associated part Name column */
    public TableColumn <Product, String> productNameAddAssociatedPart;

    /** Associated part Inventory column */
    public TableColumn <Product, Integer> productInvAddAssociatedPart;

    /** Associated part Price column */
    public TableColumn <Product, Integer> productPriceAddAssociatedPart;

    /** Product Search */
    public TextField modifyProductSearch;

    /** Method used to for product data */
    Product pro;

    /** Save changes made */
    @FXML
    public Button saveModifyProduct;

    /** Add button to move selected part from Parts table to the Associated parts table */
    @FXML
    public Button addModifyProduct;

    /** remove button to remove associated part from the associated part table. */
    @FXML
    public Button removeModifyProduct;

    /** Cancel any adjustments made on the modify Product screen and return to the main screen. */
    @FXML
    private Button CancelModifyProduct;

    /** selectIndex is for set Product Data */
    public int selectedIndex;

    /** myIndex is for Set Product Data */
    public int myIndex;


    /** Array lists */
    private ObservableList<Part> invParts = FXCollections.observableArrayList();
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();

    /** Saves Modify Products information and returns to the main screen */
    public void onClickSaveModifyProduct(ActionEvent actionEvent) throws IOException {
        try{
            pro.setId(Integer.parseInt(modifyProductId.getText()));
            pro.setName(modifyProductName.getText());
            pro.setPrice(Double.parseDouble(modifyProductPrice.getText()));
            pro.setStock(Integer.parseInt(modifyProductInv.getText()));
            pro.setMin(Integer.parseInt(modifyProductMin.getText()));
            pro.setMax(Integer.parseInt(modifyProductMax.getText()));

            //int id = Integer.parseInt(modifyProductId.getText());
            String name = modifyProductName.getText(); // not blank
            if (modifyProductName.getText().isEmpty()) {
                MainGUIController.confirmPopUp("Input Error", "Blank Field(s) Detected, please check all fields");
                return;
            }
            int stock = Integer.parseInt(modifyProductInv.getText()); // not blank, <= Max and >= Min
            if (modifyProductInv.getText().isEmpty()) {
                MainGUIController.confirmPopUp("Input Error", "Blank Field(s) Detected, please check all fields");
                return;
            }
            if (Integer.parseInt(modifyProductMin.getText()) > Integer.parseInt(modifyProductMax.getText())) {
                MainGUIController.confirmPopUp("Input Error", "Product minimum cannot exceed product maximum");
                return;
            }
            if (Integer.parseInt(modifyProductMax.getText()) < Integer.parseInt(modifyProductMin.getText())) {
                MainGUIController.confirmPopUp("Input Error", "Product maximum cannot be less than minimum");
                return;
            }
            if (Integer.parseInt(modifyProductInv.getText()) > Integer.parseInt(modifyProductMax.getText()) || Integer.parseInt(modifyProductInv.getText()) < Integer.parseInt(modifyProductMin.getText())) {
                MainGUIController.confirmPopUp("Input Error", "Inventory can not be greater or less than  limits, make sure set limits are correct");
                return;
            }
            double price = Double.parseDouble(modifyProductPrice.getText()); // not blank
            if (modifyProductPrice.getText().isEmpty()) {
                MainGUIController.confirmPopUp("Input Error", "Blank Field(s) Detected, please check all fields");
                return;
            }
            int max = Integer.parseInt(modifyProductMax.getText()); // not blank, > Min
            if (modifyProductMax.getText().isEmpty()) {
                MainGUIController.confirmPopUp("Input Error", "Blank Field(s) Detected, please check all fields");
                return;
            }
            int min = Integer.parseInt(modifyProductMin.getText()); // not blank, < Max
            if (modifyProductMin.getText().isEmpty()) {
                MainGUIController.confirmPopUp("Input Error", "Blank Field(s) Detected, please check all fields");
                return;
            }
            Inventory.deleteProduct(pro);
            Inventory.addProduct(pro);
             //Inventory.updateProduct(selectedIndex, product);

        }catch (Exception e){
        MainGUIController.confirmPopUp("Input Error", "Check fields for correct input entry");
        return;
         }


        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/demo1/mainGUI.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        System.out.println("Associated save Modify Product data");
        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.show();

        updateInvTable();
        updateAssociatedParts();

    }

    /** Adds a part from the parts table to Associated Parts */
    public void onClickAddModifyProduct(ActionEvent actionEvent) throws IOException {
        Part selectedPart = productPartsTable.getSelectionModel().getSelectedItem();

        if (selectedPart != null) {
            pro.addAssociatedPart(selectedPart);

            associatedPartsTable.setItems(pro.getAllAssociatedParts());

        }
    }

    /** Removes Associate Parts */
    public void onClickRemoveAssociatedProductModify(ActionEvent actionEvent) {
        if (associatedPartsTable.getSelectionModel().isEmpty()) {
            MainGUIController.popUp("Warning!", "No AssociatedProduct Selected", "Please choose a part from the list");
        } else if (MainGUIController.confirmPopUp("Warning!", "Would you like to delete this item?")) {
            int selectedPart = associatedPartsTable.getSelectionModel().getSelectedIndex();
            associatedPartsTable.getItems().remove(selectedPart);

        }

    }

    /** Cancels any changes made to Modified Products */
    public void onClickCancelModifyProduct(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) CancelModifyProduct.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(MainGUI.class.getResource("mainGUI.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("");
        stage.setScene(scene);
    }

    /** Searches for Products */
    public void searchForProducts(ActionEvent actionEvent) {

        try {
        String partName = modifyProductSearch.getText();
        ObservableList<Part> foundParts = Inventory.lookupPart(partName);

        if (foundParts.size() == 0) {

            int partId = Integer.parseInt(partName);
            Part pd = Inventory.lookupPart(partId);
            if (pd != null)
                foundParts.add(pd);
            if(pd == null){
                MainGUIController.confirmPopUp("Input Error", "Could not find product with a name or ID matching " + modifyProductSearch.getText());
                return;
            }
            modifyProductSearch.setText("");
        }
        productPartsTable.setItems(foundParts);

    }catch (Exception exception){
        MainGUIController.confirmPopUp("Input Error", "Could not find product with a name or ID matching " + modifyProductSearch.getText());
    }

}

    /** Updates Product Part Table */
    public void updateInvTable() {
        productPartsTable.setItems(Inventory.getAllParts());
    }
    /** Updates Associated Parts */
    private void updateAssociatedParts() {
        associatedPartsTable.setItems(associatedParts);
    }
    /** Sets Product Data */
    public void setProductData(int selectedIndex, Product prod) {

        pro = prod;
        myIndex = selectedIndex;

        this.selectedIndex = selectedIndex;
        modifyProductId.setText(String.valueOf(pro.getId()));
        modifyProductName.setText(pro.getName());
        modifyProductInv.setText(String.valueOf(pro.getStock()));
        modifyProductPrice.setText(String.valueOf(pro.getPrice()));
        modifyProductMax.setText(String.valueOf(pro.getMax()));
        modifyProductMin.setText(String.valueOf(pro.getMin()));


        associatedPartsTable.setItems(pro.getAllAssociatedParts());

    }
    /** initializes Table and Columns */
    @Override
    public void initialize(URL location, ResourceBundle resource) {

        //Columns and Table for parts
        productIdAddProduct.setCellValueFactory(new PropertyValueFactory<>("Id"));
        productNameAddProduct.setCellValueFactory(new PropertyValueFactory<>("name"));
        productInvAddProduct.setCellValueFactory(new PropertyValueFactory<>("stock"));
        productPriceAddProduct.setCellValueFactory(new PropertyValueFactory<>("price"));
        productPartsTable.setItems(invParts);

        //Columns and Table for associated parts
        productIdAddAssociatedPart.setCellValueFactory(new PropertyValueFactory<>("Id"));
        productNameAddAssociatedPart.setCellValueFactory(new PropertyValueFactory<>("name"));
        productInvAddAssociatedPart.setCellValueFactory(new PropertyValueFactory<>("stock"));
        productPriceAddAssociatedPart.setCellValueFactory(new PropertyValueFactory<>("price"));
        associatedPartsTable.setItems(associatedParts);

        updateInvTable();
        updateAssociatedParts();

    }
}
