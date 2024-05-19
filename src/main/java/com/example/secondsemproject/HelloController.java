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

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class HelloController implements Initializable {
    String[] signup_array = new String[3]; // Array to store sign-up information
    String username_for_password_reset; // Username for password reset
    HelloApplication helloApplication; // Reference to the main application

    @FXML
    private AnchorPane Login; // Pane for login screen
    @FXML
    private AnchorPane SQs; // Pane for security questions screen
    @FXML
    private AnchorPane SignUp; // Pane for sign-up screen
    @FXML
    private AnchorPane ResetPass; // Pane for password reset screen
    @FXML
    private AnchorPane Verification; // Pane for verification screen
    @FXML
    private Button nonfocusButton; // Button to handle focus
    @FXML
    private Label Lbl_error_SU; // Label for sign-up errors
    @FXML
    private TextField reset_password; // TextField for new password
    @FXML
    private TextField reset_password_confirm; // TextField to confirm new password
    @FXML
    private Label reset_pass_errorlbl; // Label for password reset errors
    @FXML
    private Label verification_error_label; // Label for verification errors
    @FXML
    private TextField su_username; // TextField for sign-up username
    @FXML
    private PasswordField su_password; // PasswordField for sign-up password
    @FXML
    private TextField su_email; // TextField for sign-up email
    @FXML
    private PasswordField su_password_confirm; // PasswordField to confirm sign-up password
    @FXML
    private TextField su_answer; // TextField for security question answer
    @FXML
    private Label login_error_label; // Label for login errors
    @FXML
    private TextField SV_email; // TextField for email in security verification
    @FXML
    private TextField sv_answer; // TextField for answer in security verification
    @FXML
    private TextField Username; // TextField for login username
    @FXML
    private PasswordField Password; // PasswordField for login password
    @FXML
    private Label secQS_lbl; // Label for security question error


    private static String username_to_pass; // Static variable to pass username

    public static String getUsername_to_pass() {
        return username_to_pass;
    }


    @FXML
    public void exit(Event event) {
        System.exit(0); // Exit the application
    }

    @FXML
    public void minimize(Event event) {
        // Get the stage from the event source
        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        // Minimize the window
        stage.setIconified(true);
    }

    @FXML
    public void LoginButton() throws SQLException {
        String entered_username = Username.getText();
        String entered_password = Password.getText();

        // Check if username or password is empty
        if (entered_username.isEmpty() || entered_password.isEmpty()) {
            login_error_label.setText("Username and Password are Required");
        } else {
            JDBCConnection.getConnection(); // Connect to the database
            try {
                ResultSet User_password = JDBCConnection.ExecuteQuery("Select Password from User where Username = \"" + entered_username + "\"");

                // Check if username exists
                if (!User_password.next()) {
                    login_error_label.setText("Username doesn't exist");
                } else {
                    // Verify password
                    if (User_password.getString("Password").equals(entered_password)) {
                        login_error_label.setText("");
                        Username.setText("");
                        Password.setText("");
                        username_to_pass = entered_username;
                        helloApplication.showHomePage(); // Show home page
                    } else {
                        login_error_label.setText("Incorrect Password, Try again");
                        Password.setText("");
                    }
                }
            } catch (SQLException e) {
                login_error_label.setText("Username doesn't exist");
            }
        }
        JDBCConnection.close(); // Close the database connection

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Set event handler for non-focus button
        nonfocusButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            nonfocusButton.requestFocus(); // Request focus on the non-focusable button
        });
        // Set initial visibility of panes
        Login.setVisible(true);
        SignUp.setVisible(false);
        SQs.setVisible(false);
        ResetPass.setVisible(false);
        Verification.setVisible(false);

        // Clear all fields and labels
        su_username.setText("");
        su_password.setText("");
        su_email.setText("");
        su_password_confirm.setText("");
        su_answer.setText("");
        Lbl_error_SU.setText("");
        reset_pass_errorlbl.setText("");
        verification_error_label.setText("");
    }

    @FXML
    public void getSingUp() {
        // Switch to sign-up pane
        Login.setVisible(false);
        SignUp.setVisible(true);
        SQs.setVisible(false);
        ResetPass.setVisible(false);
        Verification.setVisible(false);

        // Clear sign-up fields and label
        su_username.setText("");
        su_password.setText("");
        su_email.setText("");
        su_password_confirm.setText("");
        su_answer.setText("");
        Lbl_error_SU.setText("");
    }

    @FXML
    public void getLogIn(Event event) {
        // Switch to login pane
        Login.setVisible(true);
        SignUp.setVisible(false);
        SQs.setVisible(false);
        ResetPass.setVisible(false);
        Verification.setVisible(false);

        // Clear all fields and labels
        su_username.setText("");
        su_password.setText("");
        su_email.setText("");
        su_password_confirm.setText("");
        su_answer.setText("");
        login_error_label.setText("");
        Lbl_error_SU.setText("");
        Username.setText("");
        Password.setText("");
    }

    @FXML
    public void getVerification(Event event) {
        // Switch to verification pane
        Login.setVisible(false);
        SignUp.setVisible(false);
        SQs.setVisible(false);
        ResetPass.setVisible(false);
        Verification.setVisible(true);

        // Clear all fields and labels
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


        JDBCConnection.getConnection();
        try {
            if(su_answer.getText().isEmpty()){
                secQS_lbl.setText("Field cannot be empty");
            }
            else{
                String sue_answer = su_answer.getText();

                // Insert new user into the database
                JDBCConnection.ExecuteQueryWithNoResult("insert into User values(\"" + signup_array[0] + "\" , \"" + signup_array[1] + "\", \"" + signup_array[2] + "\", \"" + sue_answer + "\");");

                // Clear sign-up fields and labels
                su_username.setText("");
                su_password.setText("");
                su_email.setText("");
                su_password_confirm.setText("");
                su_answer.setText("");
                Lbl_error_SU.setText("");
                secQS_lbl.setText("");

                // Switch to login pane
                Login.setVisible(true);
                SignUp.setVisible(false);
                SQs.setVisible(false);
                ResetPass.setVisible(false);
                Verification.setVisible(false);


            }

        } catch (SQLException e) {
            System.out.println(e);
        }

        JDBCConnection.close();


    }

    public void signup() {
        String sue_username = su_username.getText();
        String entered_password = su_password.getText();
        String entered_email = su_email.getText();
        String entered_password_confirm = su_password_confirm.getText();

        // Store sign-up information in the array
        signup_array[0] = sue_username;
        signup_array[1] = entered_password;
        signup_array[2] = entered_email;

        JDBCConnection.getConnection();

        try {
            // Check if username already exists
            ResultSet resultSet = JDBCConnection.ExecuteQuery("Select * from User where Username = \"" + sue_username + "\";");

            if (sue_username.isEmpty() || entered_password.isEmpty() || entered_email.isEmpty() || entered_password_confirm.isEmpty()) {
                Lbl_error_SU.setText("A field is empty, please fill all the fields");
            } else if (resultSet.next()) {
                Lbl_error_SU.setText("Username already in use");
            } else if (!entered_password.equals(entered_password_confirm)) {
                Lbl_error_SU.setText("Passwords do not match, Try Again");
            } else {
                // Check if email already exists
                ResultSet resultSet_for_email = JDBCConnection.ExecuteQuery("Select * from User where email = \"" + entered_email + "\";");
                if (resultSet_for_email.next()) {
                    Lbl_error_SU.setText("Email already in use");
                } else {
                    // Switch to security questions pane
                    Login.setVisible(false);
                    SignUp.setVisible(false);
                    SQs.setVisible(true);
                    ResetPass.setVisible(false);
                    Verification.setVisible(false);
                }
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        JDBCConnection.close();

    }

    public void setHelloApplication(HelloApplication helloApplication) {
        this.helloApplication = helloApplication; // Set the main application reference
    }

    public void forgot_password_SV() {
        String entered_email = SV_email.getText();
        String entered_answer = sv_answer.getText();

        JDBCConnection.getConnection();

        try {
            if (SV_email.getText().isEmpty() || sv_answer.getText().isEmpty()) {
                verification_error_label.setText("Fields cannot be empty");
            } else {
                // Check if email exists and answer matches
                ResultSet SV_email_checker = JDBCConnection.ExecuteQuery("Select Answer_to_Security_Question, Username from User where Email = \"" + entered_email + "\";");
                if (!SV_email_checker.next()) {
                    verification_error_label.setText("This email does not exist");
                } else if (!SV_email_checker.getString("Answer_to_Security_Question").equals(entered_answer)) {
                    verification_error_label.setText("Wrong answer to question, Try again");
                } else {
                    // Store username for password reset
                    username_for_password_reset = SV_email_checker.getString("Username");

                    // Clear all fields and labels
                    su_username.setText("");
                    su_password.setText("");
                    su_email.setText("");
                    su_password_confirm.setText("");
                    su_answer.setText("");
                    Lbl_error_SU.setText("");

                    // Switch to password reset pane
                    Login.setVisible(false);
                    SignUp.setVisible(false);
                    SQs.setVisible(false);
                    ResetPass.setVisible(true);
                    Verification.setVisible(false);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        JDBCConnection.close();
    }

    public void forgot_password_reset() throws SQLException {
        String password = reset_password.getText();
        String confirm = reset_password_confirm.getText();

        JDBCConnection.getConnection();
        if (password.isEmpty() || confirm.isEmpty()) {
            reset_pass_errorlbl.setText("Field cannot be empty");
        } else if (!password.equals(confirm)) {
            reset_pass_errorlbl.setText("Passwords do not match");
        } else {
            // Update the user's password in the database
            JDBCConnection.ExecuteQueryWithNoResult("Update User set password = \"" + password + "\" where Username = \"" + username_for_password_reset + "\";");

            // Switch to login pane
            Login.setVisible(true);
            SignUp.setVisible(false);
            SQs.setVisible(false);
            ResetPass.setVisible(false);
            Verification.setVisible(false);

            // Clear all fields and labels
            su_username.setText("");
            su_password.setText("");
            su_email.setText("");
            su_password_confirm.setText("");
            su_answer.setText("");
            Lbl_error_SU.setText("");


        }
        JDBCConnection.close();
    }
}
