package com.example.secondsemproject;

import ch.qos.logback.core.net.SyslogOutputStream;
import javafx.animation.FadeTransition;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

import javax.security.auth.login.LoginContext;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;


public class HomeController implements Initializable {


    @FXML
    HelloController helloController;

    @FXML
    private AnchorPane calculator_pane;

    @FXML
    private AnchorPane currency_conv_pane;

    @FXML
    private AnchorPane expanded_menu_pane;

    @FXML
    private AnchorPane home_pane;

    @FXML
    private AnchorPane income;

    @FXML
    private TextField Income_ID;

    @FXML
    private Button income_all;

    @FXML
    private DatePicker income_date;

    @FXML
    private DatePicker income_date_end;

    @FXML
    private DatePicker income_date_start;

    @FXML
    private Label income_label_1;

    @FXML
    private Label income_label_2;

    @FXML
    private Button income_search;

    @FXML
    private TextField income_source;

    @FXML
    private TextField income_value;

    @FXML
    private ImageView menu_toggle_button;

    @FXML
    private Label username_field;






    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        expanded_menu_pane.setVisible(false);



    }

    @FXML
    public void exit(Event event){
        System.exit(0);
    }

    @FXML
    public void minimize(Event event){
        // Get the stage from the event source
        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();

        // Minimize the window
        stage.setIconified(true);
    }


    public void show_menu() {
        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(0.25), expanded_menu_pane);
        fadeTransition.setFromValue(0.0);
        fadeTransition.setToValue(1.0);
        fadeTransition.play();
        username_field.setText(helloController.getUsername_to_pass());

        expanded_menu_pane.setVisible(!expanded_menu_pane.isVisible());

    }

    public void setLoginController(HelloController helloController) {
        this.helloController = helloController;
    }

    public void addIncome(){
        //create an income object
        Income income = new Income(income_source.getText(), income_date.getValue(),Double.parseDouble(income_value.getText()));
        income_label_1.setText("Income added!");
    }

    public void removeIncome(){

        int ID = Integer.parseInt(Income_ID.getText());

        if(Income.deleteIncome(ID)){
            income_label_2.setText("Deleted!");

        }
        else {
            income_label_2.setText("No income recorded with the ID:" + ID + ".");
            income_label_2.setTextFill(Color.RED);
        }
    }
}
