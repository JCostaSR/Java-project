package Controller;

import Model.Inventory;
import com.example.demo1.MainGUI;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
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
import java.util.ResourceBundle;

    /*
    AddProduct TextFields
    Part Table
    Associated Part Table
    Search TextField
    GUI Buttons
    Array list
    Save - AddProduct
    Add part - AddProduct
    Remove Associated Part - AddProduct
    Cancel - AddProduct
    Search Parts - AddProduct
    Update Part Table
    Update Product Table
    Columns and Table for parts
    Columns and Table for associated parts
     */

/**
 * @author John C. Costa Sr.
 */

public class AddProductController implements Initializable {

    /** Product ID TextField disabled. */
    public TextField addProductId;

    /** Product Name TextField to enter data. */
    public TextField addProductName;

    /** Product Inventory TextField to enter Data. */
    public TextField addProductInv;

    /** Product Price TextField to enter Data. */
    public TextField addProductPrice;

    /** Product Max TextField to enter Data. */
    public TextField addProductMax;

    /** Product Min TextField to enter Data. */
    public TextField addProductMin;

    /**  Part Table */
    public TableView <Part> productPartsTable;

    /** Product Parts Id column. */
    public TableColumn <Part,Integer> productIdAddProduct;

    /** Product Parts Name column. */
    public TableColumn <Part,String> productNameAddProduct;

    /** Product Parts Inventory column. */
    public TableColumn <Part,Integer> productInvAddProduct;

    /** Product Parts Price column. */
    public TableColumn <Part,Double> productPriceAddProduct;

    /**  Associated Part Table */
    public TableView <Part> associatedPartsTable;

    /** Associated Parts Id column. */
    public TableColumn <Product,Integer> productIdAddAssociatedPart;

    /** Associated Parts Name column. */
    public TableColumn <Product,String> productNameAddAssociatedPart;

    /** Associated Parts Inventory column. */
    public TableColumn <Product,Integer> productInvAddAssociatedPart;

    /** Associated Parts Price column. */
    public TableColumn <Product,Double> productPriceAddAssociatedPart;

    /**  Search TextField to find products */
    public TextField addProductSearch;
    Product pro = new Product(1, "two", 3, 4, 5, 6);


    /** Save button. The button for Save action event. */
    public Button saveButtonAddProduct;

    /** Add associated parts. The button to move a part from the parts table to Associated Part.*/
    public Button addAssociatedPart;

    /** Delete associated part. The button to remove associated part from the associated part table. */
    public Button deleteAssociatedPart;

    /** Cancel button. Cancels adding a new product. */
    public Button cancelButtonAddProduct;

    /**  ObservableLists Array list */
    private ObservableList<Part> invParts = FXCollections.observableArrayList();
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();

    /** Saves the information in all fields and tables creating a new product. */
    /** FUTURE ENHANCEMENT - setting the data to a SQL server, so it is not lost when the program is shut down.*/
    public void onClickSaveAddProduct(ActionEvent actionEvent) throws IOException {
        try {

            //int id = Inventory.getUniqueProductId(); // disabled
            pro.setId(Integer.parseInt(addProductId.getText()));
            pro.setName(addProductName.getText());
            pro.setPrice(Double.parseDouble(addProductPrice.getText()));
            pro.setStock(Integer.parseInt(addProductInv.getText()));
            pro.setMin(Integer.parseInt(addProductMin.getText()));
            pro.setMax(Integer.parseInt(addProductMax.getText()));


            if (addProductName.getText().isEmpty()) {
                MainGUIController.confirmPopUp("Input Error", "Blank Field(s) Detected, please check all fields");
                return;
            }

            if (addProductPrice.getText().isEmpty()) {
                MainGUIController.confirmPopUp("Input Error", "Blank Field(s) Detected, please check all fields");
                return;
            }

            if (addProductInv.getText().isEmpty()) {
                MainGUIController.confirmPopUp("Input Error", "Blank Field(s) Detected, please check all fields");
                return;
            }
            if (Integer.parseInt(addProductMax.getText()) < Integer.parseInt(addProductMin.getText())) {
                MainGUIController.confirmPopUp("Input Error", "Product maximum cannot less than product minimum");
                return;
            }

            if (Integer.parseInt(addProductInv.getText()) > Integer.parseInt(addProductMax.getText()) || Integer.parseInt(addProductInv.getText()) < Integer.parseInt(addProductMin.getText())) {
                MainGUIController.confirmPopUp("Input Error", "Inventory can not be greater or less than  limits, make sure set limits are correct");
                return;
            }

            int min = Integer.parseInt(addProductMin.getText()); // not empty, min < max
            if (addProductMin.getText().isEmpty()) {
                MainGUIController.confirmPopUp("Input Error", "Blank Field(s) Detected, please check all fields");
                return;
            }

            int max = Integer.parseInt(addProductMax.getText()); // not empty, max > min
            if (addProductMax.getText().isEmpty()) {
                MainGUIController.confirmPopUp("Input Error", "Blank Field(s) Detected, please check all fields");
                return;
            }

            Inventory.addProduct(pro);

        }catch (Exception e){
            MainGUIController.confirmPopUp("Input Error", "Check field for correct input entry");
            return;
        }


        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/demo1/mainGUI.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.show();
        updateInvTable();
        updateAssociatedParts();
    }

    /**  Adds part from the parts table to associated parts table. */
    public void onClickAddProductAdd(ActionEvent actionEvent) {
        Part selectedPart = productPartsTable.getSelectionModel().getSelectedItem();

        if(selectedPart != null) {
            pro.addAssociatedPart(selectedPart);
           // updateInvTable();
            //updateAssociatedParts();
            associatedPartsTable.setItems(pro.getAllAssociatedParts());

        }
    }

    /**   Removes a Part from the Associated Parts Table. */
    public void onClickRemoveAssociatedProductAdd(ActionEvent actionEvent) {
        if (associatedPartsTable.getSelectionModel().isEmpty()) {
            MainGUIController.popUp("Warning!", "No AssociatedProduct Selected", "Please choose a part from the list");
        } else if (MainGUIController.confirmPopUp("Warning!", "Would you like to delete this item?")) {
            int selectedPart = associatedPartsTable.getSelectionModel().getSelectedIndex();
            associatedPartsTable.getItems().remove(selectedPart);

        }

    }

    /**  Cancels Adding a new Product */
    public void onClickCancelAddProduct(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) cancelButtonAddProduct.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(MainGUI.class.getResource("mainGUI.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("");
        stage.setScene(scene);
    }

    /** Search Parts in the parts table */
    public void searchForProducts(ActionEvent actionEvent) {

        try {
            String partName = addProductSearch.getText();
            ObservableList<Part> foundParts = Inventory.lookupPart(partName);
            if (foundParts.size() == 0) {

                int partId = Integer.parseInt(partName);
                Part pt = Inventory.lookupPart(partId);
                if (pt != null)
                    foundParts.add(pt);
                if(pt == null){
                    MainGUIController.confirmPopUp("Input Error", "Could not find part with a name or ID matching " + addProductSearch.getText());
                    return;
                }
                addProductSearch.setText("");
            }
            productPartsTable.setItems(foundParts);

        }catch (Exception exception){
            MainGUIController.confirmPopUp("Input Error", "Could not find part with a name or ID matching " + addProductSearch.getText());
        }

    }

    /** Update Part Table */
    public void updateInvTable() {
        productPartsTable.setItems(Inventory.getAllParts());
    }

    /** Update Product Table */
    public void updateAssociatedParts() {
        associatedPartsTable.setItems(associatedParts);
    }

    /**  Initialize Tables and Columns */
    @Override
    public void initialize(URL location, ResourceBundle resourceBundle) {
        invParts = Inventory.getAllParts();

        addProductId.setText(String.valueOf(Inventory.getAllProducts().size()+1));

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
