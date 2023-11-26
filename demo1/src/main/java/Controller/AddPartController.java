package Controller;

import Model.Inventory;
import com.example.demo1.MainGUI;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static Model.Inventory.getUniquePartId;
import static java.lang.Integer.parseInt;

/*
    AddPart TextFields
    Radio Buttons
    Machine ID / Company Name Label
    GUI Buttons
    InHouse Radio button
    Outsource RadioButton
    Save AddPart
    Cancel AddPart
 */

/**
 * @author John C. Costa Sr.
 */

public class AddPartController {

    /** Part Id TextField */
    public TextField addPartId;

    /** Part Name TextField */
    @FXML
    private TextField AddPartName;

    /** Part Inventory TextField. */
    @FXML
    private TextField AddPartInv;

    /** Part Price TextField. */
    @FXML
    private TextField AddPartPrice;

    /** Part Maximum TextField. */
    @FXML
    private TextField AddPartMax;

    /** Part Minimum TextField. */
    @FXML
    private TextField AddPartMin;

    /** Part In-House and Outsource TextField. */
    @FXML
    private TextField addPartInOut;

    /** RadioButton In-House to set In-House. */
    @FXML
    private RadioButton rADDInHouse;
    /** RadioButton Outsourced to set Outsourced. */
    @FXML
    public RadioButton addOutsourced;

    /** Machine ID / Company Name Label */
    @FXML
    public Label addPartLabel;

    /** Save Buttons */
    @FXML
    private Button SaveAddINV;
    @FXML
    private Button CancelAddINV;

    /**  InHouse Radio button. While true, it changes the addPartLabel to MachineID. */
    public void onClickInSource(ActionEvent actionEvent) {
        addPartLabel.setText(("Machine ID"));
    }

    /** Outsource RadioButton. While true, it changes the addPartLabel to Company Name. */
    public void onClickOutSource(ActionEvent actionEvent) {
        addPartLabel.setText(("Company Name"));
    }
    /** Save AddPart puts the information from the text-fields in the Part Table and returns to the main GUI.
     * First If is for Outsourced with exception handling. The second if statement is for In-House with exception handling. */
    public void onClickSaveAddINV(ActionEvent actionEvent) throws IOException {


        if(addOutsourced.isSelected()) {
            //call add part method from inventory class.
            int id = getUniquePartId();
            String name = AddPartName.getText(); // not blank
            try{
            if(AddPartName.getText().isEmpty()){
                MainGUIController.confirmPopUp("Input Error", "Blank Field(s) Detected, please check all fields");
                return;
            }
            int stock = parseInt(AddPartInv.getText()); // not blank, <= Max and >= Min
            if(AddPartInv.getText().isEmpty()){
                MainGUIController.confirmPopUp("Input Error", "Blank Field(s) Detected, please check all fields");
                return;
            }
            if(parseInt(AddPartMin.getText()) > parseInt(AddPartMax.getText())) {
                MainGUIController.confirmPopUp("Input Error", "Part minimum cannot exceed part maximum");
                return;
            }
            if(parseInt(AddPartMax.getText()) < parseInt(AddPartMin.getText())){
                MainGUIController.confirmPopUp("Input Error", "Part maximum cannot be less than minimum");
                return;
            }
            if(parseInt(AddPartInv.getText()) > parseInt(AddPartMax.getText()) || parseInt(AddPartInv.getText()) < parseInt(AddPartMin.getText())){
                MainGUIController.confirmPopUp("Input Error", "Inventory can not be greater or less than  limits, make sure set limits are correct");
                return;
            }
            double price = Double.parseDouble(AddPartPrice.getText()); // not blank
            if(AddPartPrice.getText().isEmpty()){
                MainGUIController.confirmPopUp("Input Error", "Blank Field(s) Detected, please check all fields");
                return;
            }
            int max = parseInt(AddPartMax.getText()); // not blank, > Min
            if(AddPartMax.getText().isEmpty()){
                MainGUIController.confirmPopUp("Input Error", "Blank Field(s) Detected, please check all fields");
                return;
            }

            int min = parseInt(AddPartMin.getText()); // not blank, < Max
            if(AddPartMin.getText().isEmpty()){
                MainGUIController.confirmPopUp("Input Error", "Blank Field(s) Detected, please check all fields");
                return;
            }

            if(addPartInOut.getText().isEmpty()){
                MainGUIController.confirmPopUp("Input Error", "Blank Field(s) Detected, please check all fields");
                return;
                }else {
                String companyName = addPartInOut.getText(); // not blank
                Inventory.addPart(new Outsourced(id, name, price, stock, min, max,companyName));
                }
            }catch (Exception e){
            MainGUIController.confirmPopUp("Input Error", "Check field for correct input entry");
            return;
        }


            //send user back to main screen
            Stage stage = (Stage) SaveAddINV.getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(MainGUI.class.getResource("mainGUI.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            stage.setTitle("");
            stage.setScene(scene);
        }
        if (rADDInHouse.isSelected()) {
            try{
            int id = getUniquePartId();
            String name = AddPartName.getText(); // not blank
            if(AddPartName.getText().isEmpty()){
                MainGUIController.confirmPopUp("Input Error", "Blank Field(s) Detected, please check all fields");
                return;
            }
            int stock = parseInt(AddPartInv.getText()); // not blank, <= Max and >= Min
            if(AddPartInv.getText().isEmpty()){
                MainGUIController.confirmPopUp("Input Error", "Blank Field(s) Detected, please check all fields");
                return;
            }else if(parseInt(AddPartMax.getText()) < parseInt(AddPartMin.getText())){
                MainGUIController.confirmPopUp("Input Error", "Part maximum cannot be less than minimum");
                return;
            }else if(parseInt(AddPartMin.getText()) > parseInt(AddPartMax.getText())) {
                MainGUIController.confirmPopUp("Input Error", "Part minimum cannot exceed part maximum");
                return;
            }else if(parseInt(AddPartInv.getText()) > parseInt(AddPartMax.getText()) || parseInt(AddPartInv.getText()) < parseInt(AddPartMin.getText())){
                MainGUIController.confirmPopUp("Input Error", "Inventory can not be greater or less than  limits, make sure set limits are correct");
                return;
            }

            double price = Double.parseDouble(AddPartPrice.getText()); // not blank
            if (AddPartPrice.getText().isEmpty()) {
                MainGUIController.confirmPopUp("Input Error", "Blank Field(s) Detected, please check all fields");
                return;
            }
            int max = parseInt(AddPartMax.getText()); // not blank, > Min
            if(AddPartMax.getText().isEmpty()){
                MainGUIController.confirmPopUp("Input Error", "Blank Field(s) Detected, please check all fields");
                return;
            }

            int min = parseInt(AddPartMin.getText()); // not blank, < Max
            if(AddPartMin.getText().isEmpty()){
                MainGUIController.confirmPopUp("Input Error", "Blank Field(s) Detected, please check all fields");
                return;
            }


            if(addPartInOut.getText().isEmpty()) {
                MainGUIController.confirmPopUp("Input Error", "Blank Field(s) Detected, please check all fields");
                return;
                }else{
                int machineId = Integer.parseInt(addPartInOut.getText()); // not blank
                Inventory.addPart(new InHouse(id, name, price, stock, min, max, machineId));
                }
            }catch (Exception e){
                MainGUIController.confirmPopUp("Input Error", "Check field for correct input entry");
                return;
            }
            //send user back to main screen
            Stage stage = (Stage) SaveAddINV.getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(MainGUI.class.getResource("mainGUI.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            stage.setTitle("");
            stage.setScene(scene);

        }
    }

    /** Cancel AddPart. Cancels adding the part information to the part table and returns to the main GUI. */
    public void onClickCancelAddINV(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) CancelAddINV.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(MainGUI.class.getResource("mainGUI.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("");
        stage.setScene(scene);
    }
}
