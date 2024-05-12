package com.example.secondsemproject;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import javax.security.auth.login.LoginContext;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class HelloController implements Initializable {
    String[] signup_array = new String[3];

    @FXML
    private AnchorPane Login;
    @FXML
    private AnchorPane SQs;
    @FXML
    private AnchorPane SignUp;
    @FXML
    private AnchorPane ResetPass;
    @FXML
    private AnchorPane Verification;
    @FXML
    private Button nonfocusButton;

    @FXML
    private Button SignupButton;
    @FXML
    private Label Lbl_error_SU;



    @FXML
    private TextField su_username;
    @FXML
    private PasswordField su_password;
    @FXML
    private TextField su_email;
    @FXML
    private PasswordField su_password_confirm;
    @FXML
    private TextField su_answer;




    @FXML

    private TextField Username;

    @FXML

    private PasswordField Password;


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

    @FXML

    public void LoginButton() throws SQLException {
        String entered_username = Username.getText();
        String entered_password = Password.getText();

        JDBCConnection.getConnection();
        try {
            ResultSet User_password =  JDBCConnection.ExecuteQuery("Select Password from User where Username = \"" + entered_username + "\"");

            while(User_password.next()){
                if(User_password.getString("Password").equals(entered_password)){
                    Username.setText(entered_username + "hahahahah working mf ");
                }
                else {
                    System.out.println("INcorrect password mf try again idiot baka yaro");
                }
            }

            JDBCConnection.close();
        }
        catch(SQLException e) {
            System.out.println(e);
        }




    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        nonfocusButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            nonfocusButton.requestFocus(); // Request focus on the non-focusable button
        });
        Login.setVisible(true);
        SignUp.setVisible(false);
        SQs.setVisible(false);
        ResetPass.setVisible(false);
        Verification.setVisible(false);



    }

    @FXML
    public void getSingUp(){
        Login.setVisible(false);
        SignUp.setVisible(true);
        SQs.setVisible(false);
        ResetPass.setVisible(false);
        Verification.setVisible(false);
    }

    //@FXML
    //public void getSecQs(Event event){
        //Login.setVisible(false);
       // SignUp.setVisible(false);
       // SQs.setVisible(true);
       // ResetPass.setVisible(false);
            //Verification.setVisible(false);
    //}

    @FXML
    public void getLogIn(Event event){
        Login.setVisible(true);
        SignUp.setVisible(false);
        SQs.setVisible(false);
        ResetPass.setVisible(false);
        Verification.setVisible(false);
    }

    @FXML
    public void getResetPassword(Event event){

        Login.setVisible(false);
        SignUp.setVisible(false);
        SQs.setVisible(false);
        ResetPass.setVisible(true);
        Verification.setVisible(false);
    }

    @FXML
    public void getVerification(Event event){

        Login.setVisible(false);
        SignUp.setVisible(false);
        SQs.setVisible(false);
        ResetPass.setVisible(false);
        Verification.setVisible(true);
    }



    @FXML
    public void SQFinish() {

        String sue_answer = su_answer.getText();

        JDBCConnection.getConnection();
        try {
        JDBCConnection.ExecuteQueryWithNoResult("insert into User values(\"" + signup_array[0] + "\" , \"" + signup_array[1] + "\", \"" + signup_array[2] + "\", \""+ sue_answer + "\");");

        }
        catch (SQLException e) {
            System.out.println(e);
        }

        JDBCConnection.close();


        Login.setVisible(true);
        SignUp.setVisible(false);
        SQs.setVisible(false);
        ResetPass.setVisible(false);
        Verification.setVisible(false);




    }
    public void signup() {
        String sue_username = su_username.getText();
        String entered_password = su_password.getText();
        String entered_email = su_email.getText();
        String entered_password_confirm = su_password_confirm.getText();

        signup_array[0] = sue_username;
        signup_array[1] = entered_password;
        signup_array[2] = entered_email;






        if(entered_password.equals(entered_password_confirm)){
            Login.setVisible(false);
            SignUp.setVisible(false);
            SQs.setVisible(true);
            ResetPass.setVisible(false);
            Verification.setVisible(false);
        }
        else {
            Lbl_error_SU.setText("Passwords do not match,Try Again");
            System.out.println("ERRORRRRR");
        }











    }








}