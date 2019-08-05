package lawn_mower;

import ControlSystem.ControlSystem;
import CuttingSystem.CuttingSystem;
import MovingSystem.MovingSystem;
import PowerSystem.PowerSupplySystem;
import Transmission.Transmission;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.AnchorPane;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Random;

public class View {
    Controller cl = new Controller();
    PowerSupplySystem pws = new PowerSupplySystem(cl.getPowerSystem());
    MovingSystem ms = new MovingSystem(cl.getMovingSystem());
    Random r1 = new Random();
    int index = r1.nextInt(cl.getCuttingInstruments().size());
    CuttingSystem cs = new CuttingSystem(cl.getCuttingInstruments().get(index));

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private Button setUpBtn;

    @FXML
    private Button turnOnBtn;

    @FXML
    private Label lawnMowerTypeLabel;

    @FXML
    private Label powerStatus;

    @FXML
    private Label startBtnStatus;

    @FXML
    private Label handleStatus;

    @FXML
    private Label transmissionStatus;

    @FXML
    private TextField widthTxtField;

    @FXML
    private TextField heightTxtField;

    @FXML
    private ImageView imageView;

    @FXML
    void initialize() throws FileNotFoundException {

        initialSetUp();
        loadNormalImage();
         if(!cl.getPowerSystem().equals("Manual") && cl.getMovingSystem().equals("Wheels")) {
             eventOnPressedSetUpButton(pws);
         } else if(!cl.getPowerSystem().equals("Manual") && cl.getMovingSystem().equals("Arms")) {
             eventOnPressedSetUpButton(pws);
         } else {
             setUpBtn.setOnAction(event->{
                 transmissionStatus.setVisible(true);
             });
             powerStatus.setVisible(false);
             turnOnBtn.setVisible(false);
         }
        adjustWidth();
        adjustHeight();
        startAndEndMoving();


        System.out.println(cl.getPowerSystem() + cl.getMovingSystem());
        System.out.println(cl.getAdditionalSystems().toString());
        System.out.println(cl.getCuttingInstruments().toString());

    }

    private void eventOnPressedSetUpButton(PowerSupplySystem psw) {
        setUpBtn.setOnAction(event->{
            boolean isWidthNormal = Integer.parseInt(widthTxtField.getText().trim()) <= 100 && Integer.parseInt(widthTxtField.getText().trim()) >= 10;
            System.out.println(isWidthNormal);
            boolean isHeightNormal = Integer.parseInt(heightTxtField.getText().trim()) <= 15 && Integer.parseInt(heightTxtField.getText().trim()) >= 5;
            System.out.println(isHeightNormal);
            if(widthTxtField.getLength() != 0 && heightTxtField.getLength() != 0 && isWidthNormal && isHeightNormal)
            {
                turnOnBtn.setVisible(true);
                powerStatus.setVisible(true);
                startBtnStatus.setVisible(true);
                transmissionStatus.setVisible(true);
                handleStatus.setVisible(true);
            }
            else if(widthTxtField.getLength() == 0 || heightTxtField.getLength() == 0)
            {
                Controller.alert(null,"Please fill in all fields");
            }
            else if(!isWidthNormal)
            {
                Controller.alert(null,"Please make sure you set up width between 10 and 100 sm");
            }
            else if(!isHeightNormal)
            {
                Controller.alert(null,"Please make sure you set up height between 5 and 15 sm");
            }

        });
        powerOnOff(psw);
    }

    public void startAndEndMoving()
    {
        anchorPane.setFocusTraversable(true);

        SimpleIntegerProperty aCount = new SimpleIntegerProperty(0);
        SimpleIntegerProperty bCount = new SimpleIntegerProperty(0);

        KeyCombination a = new KeyCodeCombination(KeyCode.A);
        KeyCombination b = new KeyCodeCombination(KeyCode.B);
        anchorPane.setOnKeyPressed(ke -> {
            if(cl.getPowerSystem().equals("Manual"))
            {
                aCount.set(a.match(ke) ? aCount.get() + 1 : 0);
                if(aCount.getValue() != 0) {
                    transmissionStatus.setText("Going straight");
                    try {
                        loadSpeedImage();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                else if(ke.getCode() == KeyCode.T)
                {
                    transmissionStatus.setText("Turning left");
                    try {
                        loadSpeedImage();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                else if(ke.getCode() == KeyCode.Y)
                {
                    transmissionStatus.setText("Turning right");
                    try {
                        loadSpeedImage();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                else
                {
                    transmissionStatus.setText("Stopped");
                    try {
                        loadNormalImage();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }

            }
            if(pws.isPowerOn())  {
                aCount.set(a.match(ke) ? aCount.get() + 1 : 0);
                bCount.set(b.match(ke) ? bCount.get() + 1 : 0);
                if(aCount.getValue() != 0) {
                    ControlSystem.setStartButtongHolded(true);
                    startBtnStatus.setText("Start holded: " + ControlSystem.isStartButtongHolded());
                }

                if(bCount.getValue() != 0) {
                    ms.setHandleHolded(true);
                    handleStatus.setText("Handle holded: " + ms.isHandleHolded());
                }


                if(ke.getCode() == KeyCode.Y) {
                    Transmission.setDirectionOfTurn("right");
                    transmissionStatus.setText("Turning " + Transmission.getDirectionOfTurn());
                    try {
                        loadSpeedImage();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                } else if(ke.getCode() == KeyCode.T)  {
                    Transmission.setDirectionOfTurn("left");
                    transmissionStatus.setText("Turning " + Transmission.getDirectionOfTurn());
                    try {
                        loadSpeedImage();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                } else if(ms.isHandleHolded() && ControlSystem.isStartButtongHolded()) {
                    transmissionStatus.setText("Going straight");
                    try {
                        loadSpeedImage();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }

            }
        });
        anchorPane.setOnKeyReleased(ke -> {
            if(cl.getPowerSystem().equals("Manual"))
            {
                if(a.match(ke))
                {
                    aCount.set(0);
                    transmissionStatus.setText("Stopped");
                    try {
                        loadNormalImage();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                } else if(ke.getCode() == KeyCode.T || ke.getCode() == KeyCode.Y)
                {
                    transmissionStatus.setText("Going straight");
                }

            }
            if(pws.isPowerOn()) {
                if(a.match(ke)) {
                    aCount.set(0);
                    ControlSystem.setStartButtongHolded(false);
                    startBtnStatus.setText("Start holded: " + ControlSystem.isStartButtongHolded());
                } else if(b.match(ke)) {
                    bCount.set(0);
                    ms.setHandleHolded(false);
                    handleStatus.setText("Handle holded: " + ms.isHandleHolded());
                }
                if(ke.getCode() == KeyCode.T || ke.getCode() == KeyCode.Y) {
                    transmissionStatus.setText("Going straight");
                    try {
                        loadSpeedImage();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                if(!ms.isHandleHolded() || !ControlSystem.isStartButtongHolded()) {
                    transmissionStatus.setText("Stopped");
                    try {
                        loadNormalImage();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    public void adjustWidth()
    {
        widthTxtField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                widthTxtField.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });

    }

    public void adjustHeight()
    {

        heightTxtField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                heightTxtField.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
    }

    public void powerOnOff(PowerSupplySystem psw)
    {
        turnOnBtn.setOnAction(event -> {
            if(!psw.isPowerOn())
            {
                psw.setPowerOn(true);
                turnOnBtn.setText("Turn off power");
                widthTxtField.setDisable(true);
                heightTxtField.setDisable(true);
                powerStatus.setText("Power on: " + psw.isPowerOn());
            }
            else
            {
                psw.setPowerOn(false);
                turnOnBtn.setText("Turn on power");
                powerStatus.setText("Power on: " + psw.isPowerOn());
                widthTxtField.setDisable(false);
                heightTxtField.setDisable(false);

            }

        });
    }

    public void initialSetUp()
    {

        turnOnBtn.setVisible(false);
        powerStatus.setVisible(false);
        startBtnStatus.setVisible(false);
        transmissionStatus.setVisible(false);
        handleStatus.setVisible(false);
        lawnMowerTypeLabel.setContentDisplay(ContentDisplay.CENTER);
        lawnMowerTypeLabel.setText(cl.getPowerSystem() + " lawn mower " + " on " + cl.getMovingSystem() + " with cutting instruments " + cl.getCuttingInstruments().toString() +
                " and additional systems " + cl.getAdditionalSystems().toString());
    }

    public void loadSpeedImage() throws FileNotFoundException {
        if(pws.getTypeOfSystem().equals("Manual"))
        {
            Image image = new Image(new FileInputStream("C:\\Users\\galey\\Desktop\\move3.jpg"));
            imageView.setImage(image);
            imageView.setFitHeight(300);
            imageView.setFitHeight(300);
        }
        else if(!pws.getTypeOfSystem().equals("Manual") && cl.getMovingSystem().equals("Arms"))
        {
            Image image = new Image(new FileInputStream("C:\\Users\\galey\\Desktop\\move2.jpg"));
            imageView.setImage(image);
            imageView.setFitHeight(300);
            imageView.setFitHeight(300);
        }
        else if(!pws.getTypeOfSystem().equals("Manual") && cl.getMovingSystem().equals("Wheels"))
        {
            eventOnPressedSetUpButton(pws);
            Image image = new Image(new FileInputStream("C:\\Users\\galey\\Desktop\\move1.jpg"));
            imageView.setImage(image);
            imageView.setFitHeight(300);
            imageView.setFitHeight(300);
        }
    }

    public void loadNormalImage() throws FileNotFoundException {
        if(pws.getTypeOfSystem().equals("Manual"))
        {
            Image image = new Image(new FileInputStream("C:\\Users\\galey\\Desktop\\manual.jpg"));
            imageView.setImage(image);
            imageView.setFitHeight(300);
            imageView.setFitHeight(300);
        }
        else if(!pws.getTypeOfSystem().equals("Manual") && cl.getMovingSystem().equals("Arms"))
        {
            Image image = new Image(new FileInputStream("C:\\Users\\galey\\Desktop\\trimmer.jpg"));
            imageView.setImage(image);
            imageView.setFitHeight(300);
            imageView.setFitHeight(300);
        }
        else if(!pws.getTypeOfSystem().equals("Manual") && cl.getMovingSystem().equals("Wheels"))
        {
            eventOnPressedSetUpButton(pws);
            Image image = new Image(new FileInputStream("C:\\Users\\galey\\Desktop\\Tondeuse.png"));
            imageView.setImage(image);
            imageView.setFitHeight(300);
            imageView.setFitHeight(300);
        }

    }
}
