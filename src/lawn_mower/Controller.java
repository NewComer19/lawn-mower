package lawn_mower;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class Controller {

   private static String powerSystem;
   private static String movingSystem;
   private static ArrayList<String> cuttingInstruments = new ArrayList<>();
   private static ArrayList<String> additionalSystems = new ArrayList<>();

    public static void setPowerSystem(String ps) {
        powerSystem = ps;
    }

    public static void setMovingSystem(String mv) {
        movingSystem = mv;
    }

    public String getPowerSystem() {
        return powerSystem;
    }

    public String getMovingSystem() {
        return movingSystem;
    }

    public ArrayList<String> getCuttingInstruments() {
        return cuttingInstruments;
    }

    public ArrayList<String> getAdditionalSystems() {
        return additionalSystems;
    }

    @FXML
    public RadioButton fuelRadBtn;

    @FXML
    public RadioButton electricityRadBtn;

    @FXML
    public RadioButton manualRadBtn;

    @FXML
    public RadioButton wheelsRadBtn;

    @FXML
    public RadioButton armsRadBtn;

    @FXML
    public CheckBox bladesCheckBx;

    @FXML
    public CheckBox stringCheckBx;

    @FXML
    public CheckBox chainsCheckBx;

    @FXML
    public CheckBox grassCheckBx;

    @FXML
    public CheckBox mulchingCheckBx;

    @FXML
    public CheckBox aerationCheckBx;

    @FXML
    public Button proceedBtn;


    @FXML
    void initialize()
    {
        ToggleGroup powerSystems = setToggleGroupForPowerSupplySystem(fuelRadBtn,electricityRadBtn,manualRadBtn);
        ToggleGroup movingSystems = setToggleGroupForMovingSystem(wheelsRadBtn,armsRadBtn);
        proceedBtn.setOnAction(event -> {
            if(checkIfAllFieldFilled())
            {
                passParameters(powerSystems,movingSystems);
                System.out.println(powerSystem + movingSystem);
                proceedBtn.getScene().getWindow().hide();
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/lawn_mower/view.fxml"));
                try {
                    loader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                load(loader);

            }
            else
            {
                alert(null,"Please select all needed options");
            }
        });

        powerSystems.selectedToggleProperty().addListener((ov, old_toggle, new_toggle) -> {
            // Has selection.
            if (powerSystems.getSelectedToggle() != null) {
                RadioButton button = (RadioButton) powerSystems.getSelectedToggle();
                if(button.getText().equals("Manual"))
                {
                    stringCheckBx.setDisable(true);
                    chainsCheckBx.setDisable(true);
                    grassCheckBx.setDisable(true);
                    mulchingCheckBx.setDisable(true);
                    aerationCheckBx.setDisable(true);
                    bladesCheckBx.setSelected(true);
                    bladesCheckBx.setDisable(true);
                    stringCheckBx.setSelected(false);
                    chainsCheckBx.setSelected(false);
                    grassCheckBx.setSelected(false);
                    mulchingCheckBx.setSelected(false);
                    aerationCheckBx.setSelected(false);
                }
                else
                {
                    bladesCheckBx.setDisable(false);
                    stringCheckBx.setDisable(false);
                    chainsCheckBx.setDisable(false);
                    grassCheckBx.setDisable(false);
                    mulchingCheckBx.setDisable(false);
                    aerationCheckBx.setDisable(false);
                }
                System.out.println("Button: " + button.getText());
            }
        });

        movingSystems.selectedToggleProperty().addListener((ov, old_toggle, new_toggle) -> {
            // Has selection.
            if (movingSystems.getSelectedToggle() != null) {
                RadioButton button = (RadioButton) movingSystems.getSelectedToggle();
                if(button.getText().equals("Arms") || manualRadBtn.isSelected())
                {
                    aerationCheckBx.setSelected(false);
                    mulchingCheckBx.setSelected(false);
                    grassCheckBx.setSelected(false);
                    aerationCheckBx.setDisable(true);
                    mulchingCheckBx.setDisable(true);
                    grassCheckBx.setDisable(true);
                }
                else
                {
                    aerationCheckBx.setDisable(false);
                    mulchingCheckBx.setDisable(false);
                    grassCheckBx.setDisable(false);
                }
                System.out.println("Button: " + button.getText());
            }
        });
    }

    public ToggleGroup setToggleGroupForPowerSupplySystem(RadioButton fuelRad,
                                                   RadioButton electricityRad,
                                                   RadioButton manualRad)
    {
        ToggleGroup group = new ToggleGroup();
        fuelRad.setToggleGroup(group);
        electricityRad.setToggleGroup(group);
        manualRad.setToggleGroup(group);
        return group;

    }

    public ToggleGroup setToggleGroupForMovingSystem(RadioButton wheelsRad,
                                              RadioButton armsRad)
    {
        ToggleGroup group = new ToggleGroup();
        wheelsRad.setToggleGroup(group);
        armsRad.setToggleGroup(group);
        return group;
    }
    public static Stage load(FXMLLoader loader) {
        Parent root = loader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.showAndWait();
        return stage;
    }

    public boolean checkIfAllFieldFilled()
    {
        boolean isEnergySourceSelected = isEnergySourceSelected();
        boolean isCuttingSystemSelected = isCuttingSystemSelected();
        boolean isMovingSYstemSelected = isMovingSystemSelected();
        System.out.println(isCuttingSystemSelected);
        System.out.println(isEnergySourceSelected );
        System.out.println(isCuttingSystemSelected);
        System.out.println(isMovingSYstemSelected);

        return isEnergySourceSelected && isCuttingSystemSelected && isMovingSYstemSelected;
    }

    public boolean isEnergySourceSelected()
    {
        return manualRadBtn.isSelected() || fuelRadBtn.isSelected() || electricityRadBtn.isSelected();
    }

    public boolean isCuttingSystemSelected()
    {
        return  bladesCheckBx.isSelected() || stringCheckBx.isSelected() || chainsCheckBx.isSelected();
    }

    public boolean isMovingSystemSelected()
    {
        return armsRadBtn.isSelected() || wheelsRadBtn.isSelected();
    }

    public static void alert(String headerText, String contentText) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Error");
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        alert.showAndWait();
    }

    public void passParameters(ToggleGroup powerSystems, ToggleGroup movingSystems)
    {
        RadioButton rb1 = (RadioButton) powerSystems.getSelectedToggle();
        powerSystem = rb1.getText();
        setPowerSystem(powerSystem);
        RadioButton rb2 = (RadioButton) movingSystems.getSelectedToggle();
        movingSystem = rb2.getText();
        setMovingSystem(movingSystem);
        if(bladesCheckBx.isSelected())
            cuttingInstruments.add(bladesCheckBx.getText());
        if(stringCheckBx.isSelected())
            cuttingInstruments.add(stringCheckBx.getText());
        if(chainsCheckBx.isSelected())
            cuttingInstruments.add(chainsCheckBx.getText());
        if(aerationCheckBx.isSelected())
            additionalSystems.add(aerationCheckBx.getText());
        if(grassCheckBx.isSelected())
            additionalSystems.add(grassCheckBx.getText());
        if(mulchingCheckBx.isSelected())
            additionalSystems.add(mulchingCheckBx.getText());
        System.out.println(powerSystem);
        System.out.println(movingSystem);

    }

}
