package sample;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class ControllerOpcje {


    static double volume;
    @FXML
    CheckBox mCheckBoxMusic, mCheckBoxSounds, mCheckBoxMortal;
    @FXML
    Button apply;
    @FXML
    Slider slide;
    @FXML
    GridPane roota;
    @FXML
    TextField pole;
    @FXML
    Label textowe;
    StringProperty slider1;
    double mSlider = 55;

    public ControllerOpcje() {
        slider1 = new SimpleStringProperty();

    }

    public String getSlider1() {
        return slider1.get();
    }

    public void setSlider1(String slider1) {
        this.slider1.set(slider1);
    }

    public StringProperty slider1Property() {
        return slider1;
    }

    @FXML
    public void initialize() {

        mCheckBoxMusic.setSelected(Controller.getMuzyka());
        mCheckBoxSounds.setSelected(Controller.getDzwieki());
        mCheckBoxMortal.setSelected(Controller.getNiesmiertelnosc());
        System.out.println("inicjalizacja opcji");
        slide.setValue(mSlider);

        setSlider1(String.valueOf((int) slide.getValue()));
        slide.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) {
                if (newValue == oldValue) {
                    setSlider1(String.valueOf((int) oldValue));
                    Controller.mVolume = (double) oldValue / 100;
                    mSlider = (double) newValue;

                } else {
                    setSlider1(String.valueOf(newValue.intValue()));
                    apply.setDisable(false);
                    Controller.mVolume = (double) newValue / 100;
                    mSlider = (double) newValue;
                }

            }
        });

        mCheckBoxMortal.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (newValue == null) {
                    apply.setDisable(true);
                    return;
                }
                apply.setDisable(false);
            }
        });

        pole.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (newValue == null) {
                    textowe.setText("");
                    return;
                }
                textowe.setText(pole.getText());
                apply.setDisable(false);
            }
        });


    }

    public void checkBoxSounds(ActionEvent event) {

        apply.setDisable(false);
/*                if (Controller.getDzwieki()==true){
            Controller.setDzwieki(false);

        }else Controller.setDzwieki(true);
        Controller.getDzwieki();*/

    }


    public void checkBoxMortal(ActionEvent event) {
        // apply.setDisable(false);
/*        if (Controller.getNiesmiertelnosc()==true){
            Controller.setNiesmiertelnosc(false);

        }else Controller.setNiesmiertelnosc(true);
        Controller.getNiesmiertelnosc();*/
    }

    public void checkBoxMusic(ActionEvent event) {
        apply.setDisable(false);
/*        if (Controller.getMuzyka()==true){
            Controller.setMuzyka(false);

        }else Controller.setMuzyka(true);
        Controller.getMuzyka();*/
    }

    public void Apply(ActionEvent event) {

        Controller.setDzwieki(mCheckBoxSounds.isSelected());
        Controller.setMuzyka(mCheckBoxMusic.isSelected());
        Controller.setNiesmiertelnosc(mCheckBoxMortal.isSelected());
        apply.setDisable(true);
        //setSlider1((int) slide.getValue());
        //apply.setOnMousePressed();
    }

    public void lol(Event event) {
        Button buton1 = (Button) event.getSource();
        buton1.setStyle("-fx-background-color: #c3c4c4");
    }

    public void lol2(Event event) {
        Button buton1 = (Button) event.getSource();
        buton1.setStyle(".button");
    }

    public void Close(ActionEvent event) {
        // get a handle to the stage
        Stage stage = (Stage) apply.getScene().getWindow();
        // do what you have to do
        stage.close();

    }
    ///////////////////////////////////////////////////////////////////////////////


}