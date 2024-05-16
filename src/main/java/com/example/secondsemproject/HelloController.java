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
import javafx.scene.paint.Color;


import javax.security.auth.login.LoginContext;
import java.awt.*;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class HelloController implements Initializable {
    String[] signup_array = new String[3];

    private HelloApplication helloApplication;

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
    private Label login_error_label;



    @FXML

    private TextField Username;

    @FXML

    private PasswordField Password;


    private String username_to_pass;


    public String getUsername_to_pass() {
        return this.username_to_pass;
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

    @FXML

    public void LoginButton() throws SQLException {
        String entered_username = Username.getText();
        String entered_password = Password.getText();

        if (entered_username.isEmpty() || entered_password.isEmpty()){
            login_error_label.setText("Username and Password are Required");
            System.out.println("ERRORRRRRRR");
        }
        else {
            JDBCConnection.getConnection();
            try {
                ResultSet User_password =  JDBCConnection.ExecuteQuery("Select Password from User where Username = \"" + entered_username + "\"");

                if (!User_password.next()){
                    System.out.println("username doesn't exist");
                    login_error_label.setText("Username doesn't exist");
                } else {
                    if(User_password.getString("Password").equals(entered_password)){
                        login_error_label.setText("");

                        System.out.println("logged in mf ");// add other functionality
                        Username.setText("");
                        Password.setText("");
                        username_to_pass = entered_username;
                        helloApplication.showHomePage();
                    } else {
                        login_error_label.setText("Incorrect Password,Try again");
                        Password.setText("");
                        System.out.println("Incorrect Password,Try again");
                    }
                }



                JDBCConnection.close();
            }
            catch(SQLException e) {
                System.out.println("username doesn't exist");

                login_error_label.setText("Username doesn't exist");

            }

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

        su_username.setText("");
        su_password.setText("");
        su_email.setText("");
        su_password_confirm.setText("");
        su_answer.setText("");
        Lbl_error_SU.setText("");




    }

    @FXML
    public void getSingUp(){
        Login.setVisible(false);
        SignUp.setVisible(true);
        SQs.setVisible(false);
        ResetPass.setVisible(false);
        Verification.setVisible(false);

        su_username.setText("");
        su_password.setText("");
        su_email.setText("");
        su_password_confirm.setText("");
        su_answer.setText("");
        Lbl_error_SU.setText("");

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

        su_username.setText("");
        su_password.setText("");
        su_email.setText("");
        su_password_confirm.setText("");
        su_answer.setText("");
        Lbl_error_SU.setText("");
        Username.setText("");
        Password.setText("");

    }

    @FXML
    public void getResetPassword(Event event){

        Login.setVisible(false);
        SignUp.setVisible(false);
        SQs.setVisible(false);
        ResetPass.setVisible(true);
        Verification.setVisible(false);

        su_username.setText("");
        su_password.setText("");
        su_email.setText("");
        su_password_confirm.setText("");
        su_answer.setText("");
        Lbl_error_SU.setText("");
        Username.setText("");
        Password.setText("");

    }

    @FXML
    public void getVerification(Event event){

        Login.setVisible(false);
        SignUp.setVisible(false);
        SQs.setVisible(false);
        ResetPass.setVisible(false);
        Verification.setVisible(true);

        su_username.setText("");
        su_password.setText("");
        su_email.setText("");
        su_password_confirm.setText("");
        su_answer.setText("");
        Lbl_error_SU.setText("");
        Username.setText("");
        Password.setText("");

    }



    @FXML
    public void SQFinish() throws InterruptedException {

        String sue_answer = su_answer.getText();

        JDBCConnection.getConnection();
        try {
        JDBCConnection.ExecuteQueryWithNoResult("insert into User values(\"" + signup_array[0] + "\" , \"" + signup_array[1] + "\", \"" + signup_array[2] + "\", \""+ sue_answer + "\");");

        }
        catch (SQLException e) {
            System.out.println(e);
        }

        JDBCConnection.close();

        su_username.setText("");
        su_password.setText("");
        su_email.setText("");
        su_password_confirm.setText("");
        su_answer.setText("");
        Lbl_error_SU.setText("");




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


        JDBCConnection.getConnection();

        try {
            ResultSet resultSet = JDBCConnection.ExecuteQuery("Select * from User where Username = \"" + sue_username +"\";");
            if(sue_username.isEmpty() || entered_password.isEmpty() || entered_email.isEmpty() || entered_password_confirm.isEmpty()){
                Lbl_error_SU.setText("A field is empty , please fill all the fields");
            }
            else if(resultSet.next()){
                Lbl_error_SU.setText("Username already in use");
                System.out.println("ERRORRRRR");
            }
            else if (!entered_password.equals(entered_password_confirm)) {
                Lbl_error_SU.setText("Passwords do not match,Try Again");
                System.out.println("ERRORRRRR");

            }

            else {
                Login.setVisible(false);
                SignUp.setVisible(false);
                SQs.setVisible(true);
                ResetPass.setVisible(false);
                Verification.setVisible(false);





            }


        }
        catch (SQLException e) {
            System.out.println(e);
        }

    }

    public void setHelloApplication(HelloApplication helloApplication) {
        this.helloApplication = helloApplication;
    }












}