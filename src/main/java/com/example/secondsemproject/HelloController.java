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
    // Array to store sign-up information
    String[] signup_array = new String[3];
    // Variable to store username for password reset
    String username_for_password_reset;
    // Reference to the main application
    HelloApplication helloApplication;

    // FXML elements
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
    private Label Lbl_error_SU;
    @FXML
    private TextField reset_password;
    @FXML
    private TextField reset_password_confirm;
    @FXML
    private Label reset_pass_errorlbl;
    @FXML
    private Label verification_error_label;
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
    private TextField SV_email;
    @FXML
    private TextField sv_answer;
    @FXML
    private TextField Username;
    @FXML
    private PasswordField Password;
    @FXML
    private Label secQS_lbl;

    // Static variable to store the username for passing between methods
    private static String username_to_pass;

    // Getter for username_to_pass
    public static String getUsername_to_pass() {
        return username_to_pass;
    }

    // Method to exit the application
    @FXML
    public void exit(Event event) {
        System.exit(0);
    }

    // Method to minimize the application window
    @FXML
    public void minimize(Event event) {
        // Get the stage from the event source
        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        // Minimize the window
        stage.setIconified(true);
    }

    // Method to handle login button click
    @FXML
    public void LoginButton() throws SQLException {
        String entered_username = Username.getText();
        String entered_password = Password.getText();

        // Check if username or password fields are empty
        if (entered_username.isEmpty() || entered_password.isEmpty()) {
            login_error_label.setText("Username and Password are Required");
            login_error_label.setStyle("-fx-text-fill: red;");
        } else {
            // Connect to the database
            JDBCConnection.getConnection();
            try {
                login_error_label.setStyle("-fx-text-fill: red;");
                // Query the database for the entered username
                ResultSet User_password = JDBCConnection.ExecuteQuery("Select Password from User where Username = \"" + entered_username + "\"");

                // Check if the username exists
                if (!User_password.next()) {
                    login_error_label.setText("Username doesn't exist");
                } else {
                    // Check if the entered password matches the stored password
                    if (User_password.getString("Password").equals(entered_password)) {
                        login_error_label.setText("");
                        Username.setText("");
                        Password.setText("");
                        username_to_pass = entered_username;
                        clearAll();
                        helloApplication.showHomePage();
                    } else {
                        login_error_label.setText("Incorrect Password, Try again");
                        Password.setText("");
                    }
                }
            } catch (SQLException e) {
                login_error_label.setText("Username doesn't exist");
            }
        }
    }

    // Method called to initialize the controller after its root element has been completely processed
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        nonfocusButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            nonfocusButton.requestFocus(); // Request focus on the non-focusable button
        });
        // Set initial visibility of panes
        Login.setVisible(true);
        SignUp.setVisible(false);
        SQs.setVisible(false);
        ResetPass.setVisible(false);
        Verification.setVisible(false);
        clearAll();
    }

    // Method to switch to the sign-up page
    @FXML
    public void getSingUp() {
        Login.setVisible(false);
        SignUp.setVisible(true);
        SQs.setVisible(false);
        ResetPass.setVisible(false);
        Verification.setVisible(false);
        clearAll();
    }

    // Method to switch to the login page
    @FXML
    public void getLogIn(Event event) {
        Login.setVisible(true);
        SignUp.setVisible(false);
        SQs.setVisible(false);
        ResetPass.setVisible(false);
        Verification.setVisible(false);
        clearAll();
    }

    // Method to switch to the verification page
    @FXML
    public void getVerification(Event event) {
        Login.setVisible(false);
        SignUp.setVisible(false);
        SQs.setVisible(false);
        ResetPass.setVisible(false);
        Verification.setVisible(true);
        clearAll();
    }

    // Method to handle security question finish
    @FXML
    public void SQFinish() throws InterruptedException {
        String sue_answer = su_answer.getText();

        // Check if the answer field is empty
        if (sue_answer.isEmpty()) {
            secQS_lbl.setText("Field cannot be empty");
        } else {
            // Connect to the database
            JDBCConnection.getConnection();
            try {
                // Insert user information into the database
                JDBCConnection.ExecuteQueryWithNoResult("insert into User values(\"" + signup_array[0] + "\" , \"" + signup_array[1] + "\", \"" + signup_array[2] + "\", \"" + sue_answer + "\");");
            } catch (SQLException e) {
                System.out.println(e);
            }
            JDBCConnection.close();

            // Switch to the login page after successful registration
            Login.setVisible(true);
            SignUp.setVisible(false);
            SQs.setVisible(false);
            ResetPass.setVisible(false);
            Verification.setVisible(false);
            clearAll();
            login_error_label.setText("Account created!");
            login_error_label.setStyle("-fx-text-fill: green;");
        }
    }

    // Method to handle sign-up process
    public void signup() {
        String sue_username = su_username.getText();
        String entered_password = su_password.getText();
        String entered_email = su_email.getText();
        String entered_password_confirm = su_password_confirm.getText();

        // Store the sign-up information in the array
        signup_array[0] = sue_username;
        signup_array[1] = entered_password;
        signup_array[2] = entered_email;

        // Connect to the database
        JDBCConnection.getConnection();

        try {
            // Query the database for the entered username
            ResultSet resultSet = JDBCConnection.ExecuteQuery("Select * from User where Username = \"" + sue_username + "\";");

            // Check if any field is empty
            if (sue_username.isEmpty() || entered_password.isEmpty() || entered_email.isEmpty() || entered_password_confirm.isEmpty()) {
                Lbl_error_SU.setText("A field is empty, please fill all the fields");
            } else if (resultSet.next()) {
                Lbl_error_SU.setText("Username already in use");
            } else if (!entered_password.equals(entered_password_confirm)) {
                Lbl_error_SU.setText("Passwords do not match, Try Again");
            } else {
                // Query the database for the entered email
                ResultSet resultSet_for_email = JDBCConnection.ExecuteQuery("Select * from User where email = \"" + entered_email + "\";");
                if (resultSet_for_email.next()) {
                    Lbl_error_SU.setText("Email already in use");
                } else {
                    // Switch to the security questions page
                    Login.setVisible(false);
                    SignUp.setVisible(false);
                    SQs.setVisible(true);
                    ResetPass.setVisible(false);
                    Verification.setVisible(false);
                    clearAll();
                }
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        JDBCConnection.close();
    }

    // Method to set the HelloApplication instance
    public void setHelloApplication(HelloApplication helloApplication) {
        this.helloApplication = helloApplication;
    }

    // Method to handle forgot password verification
    public void forgot_password_SV() {
        String entered_email = SV_email.getText();
        String entered_answer = sv_answer.getText();

        // Connect to the database
        JDBCConnection.getConnection();

        try {
            // Check if email or answer fields are empty
            if (entered_email.isEmpty() || entered_answer.isEmpty()) {
                verification_error_label.setText("Fields cannot be empty");
            } else {
                // Query the database for the entered email
                ResultSet SV_email_checker = JDBCConnection.ExecuteQuery("Select Answer_to_Security_Question, Username from User where Email = \"" + entered_email + "\";");

                // Check if the email exists
                if (!SV_email_checker.next()) {
                    verification_error_label.setText("This email does not exist");
                } else if (!SV_email_checker.getString("Answer_to_Security_Question").equals(entered_answer)) {
                    verification_error_label.setText("Wrong answer to question, Try again");
                } else {
                    // Store the username for password reset
                    username_for_password_reset = SV_email_checker.getString("Username");

                    // Switch to the reset password page
                    Login.setVisible(false);
                    SignUp.setVisible(false);
                    SQs.setVisible(false);
                    ResetPass.setVisible(true);
                    Verification.setVisible(false);
                    clearAll();
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        JDBCConnection.close();
    }

    // Method to handle password reset
    public void forgot_password_reset() throws SQLException {
        String password = reset_password.getText();
        String confirm = reset_password_confirm.getText();

        // Connect to the database
        JDBCConnection.getConnection();
        // Check if password or confirm fields are empty
        if (password.isEmpty() || confirm.isEmpty()) {
            reset_pass_errorlbl.setText("Field cannot be empty");
        } else if (!password.equals(confirm)) {
            reset_pass_errorlbl.setText("Passwords do not match");
        } else {
            // Update the password in the database
            JDBCConnection.ExecuteQueryWithNoResult("Update User set password = \"" + password + "\" where Username = \"" + username_for_password_reset + "\";");

            // Switch to the login page after successful password reset
            Login.setVisible(true);
            SignUp.setVisible(false);
            SQs.setVisible(false);
            ResetPass.setVisible(false);
            Verification.setVisible(false);
            clearAll();
            login_error_label.setText("Password Changed!");
            login_error_label.setStyle("-fx-text-fill: green;");

            JDBCConnection.close();
        }
    }

    // Method to clear all input fields and labels
    public void clearAll() {
        // Clear PasswordFields
        su_password.clear();
        su_password_confirm.clear();
        reset_password.clear();
        reset_password_confirm.clear();
        Password.clear();

        // Clear TextFields
        su_username.clear();
        su_email.clear();
        su_answer.clear();
        SV_email.clear();
        sv_answer.clear();
        Username.clear();

        // Clear Labels
        Lbl_error_SU.setText("");
        reset_pass_errorlbl.setText("");
        verification_error_label.setText("");
        login_error_label.setText("");
        secQS_lbl.setText("");
    }
}
