package Controller;

import Model.Inventory;
import com.example.demo1.MainGUI;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;

   /* ModifyPart TextFields
      Radio Buttons
      Machine ID / Company Name Label
      GUI Buttons
      InHouse Radio button
      Outsource RadioButton
      Save AddPart
      Cancel AddPart
      In House Data
    */

/**
 * @author John C. Costa Sr.
 */

public class ModifyPartController {

    /** Modify Part Toggle Group */
    public ToggleGroup ModifyPart;

    /** Modify Id TextField to */
    public TextField modifyId;

    /** Modify Name TextField */
    public TextField modifyName;

    /** Modify Inventory TextField */
    public TextField modifyInv;

    /** Modify Price TextField */
    public TextField modifyPrice;

    /** Modify Maximum TextField */
    public TextField modifyMax;

    /** Modify Minimum TextField */
    public TextField modifyMin;

    /** Modify Part In-House and Outsource TextField */
    public TextField modifyPartInOut;

    /** Modify Outsource RadioButton */
    public RadioButton rModifyOutsourced;

    /** Modify In-House RadioButton */
    public RadioButton rModifyInHouse;

    /** Machine ID / Company Name Label */
    @FXML
    private Label modifyPartLabel;

    /** Save all adjustments */
    public Button saveModifyINV;

    /** Cancel and return to the main screen */
    @FXML
    private Button CancelModifyINV;

    /** method used for In-house and Outsourced switching information */
    public int selectedIndex;

    /** InHouse Radio button. While true, it changes the modifyPartLabel to Machine ID.*/
    public void onClickModIn(ActionEvent event)  {
        modifyPartLabel.setText(("Machine ID"));
    }

    /** Outsource RadioButton. While true, it changes the modifyPartLabel to Company Name. */
    public void onClickModOut(ActionEvent event){
        modifyPartLabel.setText(("Company Name"));
    }

    /** Saves the modify part information and returns to the main screen. */
    public void onClickSaveModifyINV(ActionEvent actionEvent) throws IOException {


        if (rModifyOutsourced.isSelected()) {
            try {

                int id = Integer.parseInt(modifyId.getText());
                String name = modifyName.getText(); // not blank
                if (modifyName.getText().isEmpty()) {
                    MainGUIController.confirmPopUp("Input Error", "Blank Field(s) Detected, please check all fields");
                    return;
                }
                int stock = Integer.parseInt(modifyInv.getText()); // not blank, <= Max and >= Min
                if (modifyInv.getText().isEmpty()) {
                    MainGUIController.confirmPopUp("Input Error", "Blank Field(s) Detected, please check all fields");
                    return;
                }
                if (Integer.parseInt(modifyMin.getText()) > Integer.parseInt(modifyMax.getText())) {
                    MainGUIController.confirmPopUp("Input Error", "Part maximum cannot be lower than part minimum");
                    return;
                }
                if (Integer.parseInt(modifyInv.getText()) > Integer.parseInt(modifyMax.getText()) || Integer.parseInt(modifyInv.getText())< Integer.parseInt(modifyMin.getText())){
                    MainGUIController.confirmPopUp("Input Error", "Inventory can not be greater or less than  limits, make sure set limits are correct");
                    return;
                }
                double price = Double.parseDouble(modifyPrice.getText()); // not blank
                if (modifyPrice.getText().isEmpty()) {
                    MainGUIController.confirmPopUp("Input Error", "Blank Field(s) Detected, please check all fields");
                    return;
                }
                int max = Integer.parseInt(modifyMax.getText()); // not blank, > Min
                if (modifyMax.getText().isEmpty()) {
                    MainGUIController.confirmPopUp("Input Error", "Blank Field(s) Detected, please check all fields");
                    return;
                }

                int min = Integer.parseInt(modifyMin.getText()); // not blank, < Max
                if (modifyMin.getText().isEmpty()) {
                    MainGUIController.confirmPopUp("Input Error", "Blank Field(s) Detected, please check all fields");
                    return;
                }

                String companyName = modifyPartInOut.getText(); // not blank
                if (modifyPartInOut.getText().isEmpty()) {
                    MainGUIController.confirmPopUp("Input Error", "Blank Field(s) Detected, please check all fields");
                    return;
                }

                Outsourced outsourced = new Outsourced(id, name, price, stock, min, max, companyName);
                Inventory.updatePart(selectedIndex, outsourced);
            }catch (Exception e){
                MainGUIController.confirmPopUp("Input Error", "Check field for correct input entry");
                return;
            }


        } else if (rModifyInHouse.isSelected()) {
            try {

                int id = Integer.parseInt(modifyId.getText());
                String name = modifyName.getText();
                if (modifyName.getText().isEmpty()) {
                    MainGUIController.confirmPopUp("Input Error", "Blank Field(s) Detected, please check all fields");
                    return;
                }
                double price = Double.parseDouble(modifyPrice.getText());
                if (modifyPrice.getText().isEmpty()) {
                    MainGUIController.confirmPopUp("Input Error", "Blank Field(s) Detected, please check all fields");
                    return;
                }
                int stock = Integer.parseInt(modifyInv.getText());
                if (modifyInv.getText().isEmpty()) {
                    MainGUIController.confirmPopUp("Input Error", "Blank Field(s) Detected, please check all fields");
                    return;
                }
                if (Integer.parseInt(modifyMin.getText()) > Integer.parseInt(modifyMax.getText())) {
                    MainGUIController.confirmPopUp("Input Error", "Part maximum cannot be lower than part minimum");
                    return;
                }
                if (Integer.parseInt(modifyInv.getText()) > Integer.parseInt(modifyMax.getText()) || Integer.parseInt(modifyInv.getText()) < Integer.parseInt(modifyMin.getText())) {
                    MainGUIController.confirmPopUp("Input Error", "Inventory can not be greater or less than  limits, make sure set limits are correct");
                    return;
                }
                int min = Integer.parseInt(modifyMin.getText());
                if (modifyMin.getText().isEmpty()) {
                    MainGUIController.confirmPopUp("Input Error", "Blank Field(s) Detected, please check all fields");
                    return;
                }

                int max = Integer.parseInt(modifyMax.getText());
                if (modifyMax.getText().isEmpty()) {
                    MainGUIController.confirmPopUp("Input Error", "Blank Field(s) Detected, please check all fields");
                    return;
                }

                int machineId = Integer.parseInt(modifyPartInOut.getText());
                if (modifyPartInOut.getText().isEmpty()) {
                    MainGUIController.confirmPopUp("Input Error", "Blank Field(s) Detected, please check all fields");
                    return;
                }

                InHouse inHouse = new InHouse(id, name, price, stock, min, max, machineId);
                Inventory.updatePart(selectedIndex, inHouse);

            }catch (Exception e){
                MainGUIController.confirmPopUp("Input Error", "Check fields for correct input entry");
                return;
            }

        }
        Stage stage = (Stage) CancelModifyINV.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(MainGUI.class.getResource("mainGUI.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("");
        stage.setScene(scene);

    }

    /** Cancel AddPart. Cancels modify Part and sends the User back to the Main Screen. */
    public void onClickCancelModifyINV(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) CancelModifyINV.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(MainGUI.class.getResource("mainGUI.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("");
        stage.setScene(scene);
    }

    /** @param index,inout In House Data. Sets part data */
    /** RUNTIME ERROR - I had many of these while trying to get my data to move from the add part to the main GUI. Best way to put how I was able to
     * get past this was having 1 on 1 time with the instructors and watching webBlast videos.  */
    public void setInHouseData(int index, Part inout) {


        selectedIndex = index;
        modifyId.setText(String.valueOf(inout.getId()));
        modifyName.setText(String.valueOf(inout.getName()));
        modifyInv.setText(String.valueOf(inout.getStock()));
        modifyPrice.setText(String.valueOf(inout.getPrice()));
        modifyMax.setText(String.valueOf(inout.getMax()));
        modifyMin.setText(String.valueOf(inout.getMin()));
        if(inout instanceof InHouse){
            InHouse in = (InHouse) inout;
            modifyPartInOut.setText(String.valueOf((in.getMachineId())));
            modifyPartLabel.setText(("Machine ID"));
            rModifyInHouse.setSelected(true);
        }

        else{
            Outsourced out = (Outsourced) inout;
            modifyPartInOut.setText(String.valueOf(out.getCompanyName()));
            modifyPartLabel.setText(("Company Name"));
            rModifyOutsourced.setSelected(true);
        }


    }



}
