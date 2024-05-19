package com.example.secondsemproject;

import ch.qos.logback.core.net.SyslogOutputStream;
import javafx.animation.FadeTransition;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
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
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

import static com.example.secondsemproject.Reminder.*;


public class HomeController implements Initializable {


    Reminder currently_showing_reminder;

    @FXML
    HelloController helloController;

    @FXML
    private Label reminder_label;

    @FXML
    private AnchorPane expanded_menu_pane;

    @FXML
    private AnchorPane home_pane;

    @FXML
    private AnchorPane income;

    @FXML
    private TextField income_ID;


    @FXML
    private Button complete_reminder_button;

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
    private Label income_label_3;

    @FXML
    private TextField income_source;

    @FXML
    private TextField income_value;

    @FXML
    private ImageView menu_toggle_button;

    @FXML
    private Label username_field;

    @FXML
    private TableView<Income> table_Income;

    @FXML
    private TableColumn<Income, LocalDate> table_IncomeDate;

    @FXML
    private TableColumn<Income,String> table_IncomeSource;

    @FXML
    private TableColumn< Income, Double> table_IncomeValue;

    @FXML
    private TableColumn<Income, Integer> table_IncomeID;
    @FXML
    private BarChart<String, Number> HomeBarChart;

    @FXML
    private AnchorPane expense;
    @FXML
    private TextField expense_ID;
    @FXML
    private DatePicker expense_date;

    @FXML
    private DatePicker expense_date_end;

    @FXML
    private DatePicker expense_date_start;

    @FXML
    private Label expense_label_1;

    @FXML
    private Label expense_label_2;

    @FXML
    private Label expense_label_3;

    @FXML
    private TextField expense_category;

    @FXML
    private TextField expense_value;
    @FXML
    private TableView<Expenditure> table_expense;

    @FXML
    private TableColumn<Expenditure, LocalDate> table_expenseDate;

    @FXML
    private TableColumn<Expenditure,String> table_expenseCategory;

    @FXML
    private TableColumn< Expenditure, Double> table_expenseValue;

    @FXML
    private TableColumn<Expenditure, Integer> table_expenseID;


    @FXML
    private AnchorPane reminder;
    @FXML
    private TextField reminder_ID;
    @FXML
    private DatePicker reminder_date;

    @FXML
    private Label reminder_label_1;

    @FXML
    private Label reminder_label_2;

    @FXML
    private TextField reminder_category;
    @FXML
    private TextField reminder_name;

    @FXML
    private TextField reminder_value;
    @FXML
    private TableView <Reminder> table_reminder;

    @FXML
    private TableColumn <Reminder, LocalDate> table_reminderDate;

    @FXML
    private TableColumn<Reminder,String> table_reminderCategory;

    @FXML
    private TableColumn< Reminder, Double> table_reminderValue;

    @FXML
    private TableColumn<Reminder, Integer> table_reminderID;
    @FXML
    private TableColumn<Reminder, String> table_reminderName;
    @FXML
    private TableColumn<Reminder, String> table_reminderRepeat;

    @FXML
    private TableView <Wishlist> table_wishlist;


    @FXML
    private TableColumn<Wishlist,String> table_wishlistname;

    @FXML
    private TableColumn< Wishlist, Double> table_wishlistprice;

    @FXML
    private TableColumn<Wishlist, Integer> table_wishlistID;
    @FXML
    private TableColumn<Wishlist, Double> table_wishlistrate;

    @FXML
    private TableColumn<Wishlist , LocalDate> table_wishlistdate;

    @FXML
    private TableColumn<Wishlist , String> table_wishlistredeemable;
    @FXML
    private TableColumn<Wishlist, Double> table_wishlistAmount;




    @FXML
    private TextField redeem_wishlist_id;




    @FXML
    private RadioButton monthly;

    @FXML
    private RadioButton once;
    @FXML
    private RadioButton yearly;
    @FXML
    private ToggleGroup reminder_status;
    @FXML
    private AnchorPane wishlist;

    @FXML
    private TextField wishlist_name;
    @FXML
    private TextField wishlist_price;
    @FXML
    private TextField wishlist_rate;
    @FXML
    private Label wishlist_error_lbl;
    @FXML
    private Label wishlist_label_2;

    private ContextMenu contextMenu;

    @FXML
    private ImageView avatar_image;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        expanded_menu_pane.setVisible(false);
        income.setVisible(false);
        expense.setVisible(false);
        home_pane.setVisible(true);
        reminder.setVisible(false);
        wishlist.setVisible(false);
        getShowingReminders();
        reminder_label.setVisible(false);
        reminder_label.setText("");
        complete_reminder_button.setVisible(false);
        wishlist_error_lbl.setText("");


        income_date.setEditable(false);
        income_date_start.setEditable(false);
        income_date_end.setEditable(false);

        expense_date.setEditable(false);
        expense_date_end.setEditable(false);
        expense_date_start.setEditable(false);

        reminder_date.setEditable(false);


        contextMenu= new ContextMenu();

        // Create custom menu items
        MenuItem menuItem1 = new MenuItem("Logout");


        // Add action handlers to the menu items
        menuItem1.setOnAction(event -> {
            logout();
        });



        // Add the menu items to the context menu
        contextMenu.getItems().addAll(menuItem1);




        getUpcomingReminders();
        // for testing purposes
        ;

        Wishlist w1 = new Wishlist("Umiar", 100,100);



        getUpcomingReminders();

        reminders();

        setAllIncomes_table();
        setAllReminders_table();
        setAllExpenses_table();
        setAllWishlist_table();

        BarGraphs.displayBarChart(HomeBarChart);



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
        try {
            LocalDate date = income_date.getValue();
            if ((income_value.getText().isEmpty()) ||( income_source.getText().isEmpty()) || (income_date.getValue() == null)) {

                income_label_1.setStyle("-fx-text-fill: red;");

                if (income_date.getValue() == null && !((income_value.getText().isEmpty()) ||( income_source.getText().isEmpty()))){
                    income_label_1.setText("Enter a valid date");
                }
                else{
                    income_label_1.setText("Field cannot be empty");
                }

            }
            else{
                if(Double.parseDouble(income_value.getText()) < 0) {
                    income_label_1.setStyle("-fx-text-fill: red;");
                    income_label_1.setText("Value cannot be negative");
                }

                else {
                    Income income = new Income(income_source.getText(), income_date.getValue(),Double.parseDouble(income_value.getText()));
                    income_label_1.setStyle("-fx-text-fill: green;");
                    income_label_1.setText("Income added!");
                    setAllIncomes_table();
                    income_source.setText("");
                    income_date.setValue(null);
                    income_value.setText("");
                }

            }

        }
        catch(NumberFormatException e) {
            income_label_1.setStyle("-fx-text-fill: red;");
            income_label_1.setText("Need a number in the Value field");
        }
        catch (DateTimeParseException e) {
            income_label_1.setText("Enter valid dates");
            income_label_1.setStyle("-fx-text-fill: red;");
        }

    }


    public void removeIncome(){

        try {


            if (income_ID.getText().isEmpty()){
                income_label_2.setStyle("-fx-text-fill: red;");
                income_label_2.setText("Please enter an ID");
            }
            else {
                int ID = Integer.parseInt(income_ID.getText());

                if (ID > 0){
                    if(Income.deleteIncome(ID)){
                        income_label_2.setStyle("-fx-text-fill: green;");

                        income_label_2.setText("Deleted!");
                        setAllIncomes_table();

                    }
                    else {
                        income_label_2.setText("No income recorded with the ID:" + ID);
                        income_label_2.setTextFill(Color.RED);
                    }
                    income_ID.setText("");
                }
                else {
                    income_label_2.setStyle("-fx-text-fill: red;");
                    income_label_2.setText("Please enter a valid ID");

                }



            }
        }
        catch(NumberFormatException e) {
            income_label_2.setStyle("-fx-text-fill: red;");
            income_label_2.setText("ID is a positive number");

        }

    }

    public void setAllIncomes_table(){

        table_Income.getItems().clear();

        // In your initialize method or wherever you set up the TableView
        ObservableList<Income> incomeObservableList = FXCollections.observableArrayList(Income.incomeList);


        table_IncomeID.setCellValueFactory(new PropertyValueFactory<Income , Integer>("ID"));
        table_IncomeDate.setCellValueFactory(new PropertyValueFactory<Income , LocalDate>("Date"));
        table_IncomeValue.setCellValueFactory(new PropertyValueFactory<Income , Double>("Value"));
        table_IncomeSource.setCellValueFactory(new PropertyValueFactory<Income , String>("Source"));

        table_Income.setItems(incomeObservableList);
    }



    public void setSearchIncomes_table(){

        table_Income.getItems().clear();


        ArrayList <Income> searchResult = new ArrayList<>();
        try {
            if (income_date_start.getValue() == null || income_date_end.getValue() == null){
                income_label_3.setText("Enter valid dates");
                income_label_3.setStyle("-fx-text-fill: red;");

            }
            else{
                LocalDate start = income_date_start.getValue();
                LocalDate end = income_date_end.getValue();

                if(start.isAfter(end)){
                    income_label_3.setText("Start date should be smaller than the End date");
                    income_label_3.setStyle("-fx-text-fill: red;");
                }
                else {

                    for (int i = 0; i < Income.incomeList.size(); i++) {
                        LocalDate income_date = Income.incomeList.get(i).getDate();

                        boolean isBetween = (income_date.isAfter(start) && income_date.isBefore(end)) || income_date.equals(start) || income_date.equals(end);

                        if (isBetween) {
                            searchResult.add(Income.incomeList.get(i));

                        }
                    }

                    // In your initialize method or wherever you set up the TableView
                    ObservableList<Income> incomeObservableList = FXCollections.observableArrayList(searchResult);


                    table_IncomeID.setCellValueFactory(new PropertyValueFactory<Income , Integer>("ID"));
                    table_IncomeDate.setCellValueFactory(new PropertyValueFactory<Income , LocalDate>("Date"));
                    table_IncomeValue.setCellValueFactory(new PropertyValueFactory<Income , Double>("Value"));
                    table_IncomeSource.setCellValueFactory(new PropertyValueFactory<Income , String>("Source"));

                    table_Income.setItems(incomeObservableList);


                }
            }
        }catch (DateTimeParseException e){
            income_label_3.setText("Enter valid dates");
            income_label_3.setStyle("-fx-text-fill: red;");

        }
    }


    public void addExpense(){
        //create an expense object
        try {
            LocalDate date = expense_date.getValue();
            if ((expense_value.getText().isEmpty()) ||( expense_category.getText().isEmpty()) || (expense_date.getValue() == null)) {

                expense_label_1.setStyle("-fx-text-fill: red;");

                if (expense_date.getValue() == null && !((expense_value.getText().isEmpty()) ||( expense_category.getText().isEmpty()))){
                    expense_label_1.setText("Enter a valid date");
                }
                else{
                    expense_label_1.setText("Field cannot be empty");
                }

            }
            else{
                if(Double.parseDouble(expense_value.getText()) < 0) {
                    expense_label_1.setStyle("-fx-text-fill: red;");
                    expense_label_1.setText("Value cannot be negative");
                }

                else {
                    Expenditure expense1 = new Expenditure(expense_category.getText(), expense_date.getValue(),Double.parseDouble(expense_value.getText()));
                    expense_label_1.setStyle("-fx-text-fill: green;");
                    expense_label_1.setText("Expense added!");
                    setAllExpenses_table();
                    expense_category.setText("");
                    expense_date.setValue(null);
                    expense_value.setText("");
                }

            }

        }
        catch(NumberFormatException e) {
            expense_label_1.setStyle("-fx-text-fill: red;");
            expense_label_1.setText("Need a number in the Value field");
        }
        catch (DateTimeParseException e) {
            expense_label_1.setText("Enter valid dates");
            expense_label_1.setStyle("-fx-text-fill: red;");
        }

    }


    public void removeExpense(){

        try {


            if (expense_ID.getText().isEmpty()){
                expense_label_2.setStyle("-fx-text-fill: red;");
                expense_label_2.setText("Please enter an ID");
            }
            else {
                int ID = Integer.parseInt(expense_ID.getText());

                if (ID > 0){
                    if(Expenditure.deleteExpense(ID)){
                        expense_label_2.setStyle("-fx-text-fill: green;");

                        expense_label_2.setText("Deleted!");
                        setAllExpenses_table();

                    }
                    else {
                        expense_label_2.setText("No expense recorded with the ID:" + ID);
                        expense_label_2.setTextFill(Color.RED);
                    }
                    expense_ID.setText("");
                }
                else {
                    expense_label_2.setStyle("-fx-text-fill: red;");
                    expense_label_2.setText("Please enter a valid ID");

                }



            }
        }
        catch(NumberFormatException e) {
            expense_label_2.setStyle("-fx-text-fill: red;");
            expense_label_2.setText("ID is a positive number");

        }

    }

    public void setAllExpenses_table(){

        table_expense.getItems().clear();

        ObservableList<Expenditure> expenseObservableList = FXCollections.observableArrayList(Expenditure.ExpenditureList);


        table_expenseID.setCellValueFactory(new PropertyValueFactory<Expenditure , Integer>("ID"));
        table_expenseDate.setCellValueFactory(new PropertyValueFactory<Expenditure , LocalDate>("Date"));
        table_expenseValue.setCellValueFactory(new PropertyValueFactory<Expenditure , Double>("Value"));
        table_expenseCategory.setCellValueFactory(new PropertyValueFactory<Expenditure , String>("Category"));

        table_expense.setItems(expenseObservableList);
    }

    public void setSearchExpense_table(){

        table_expense.getItems().clear();

        ArrayList <Expenditure> searchResult = new ArrayList<>();
        try {
            if (expense_date_start.getValue() == null || expense_date_end.getValue() == null){
                expense_label_3.setText("Enter valid dates");
                expense_label_3.setStyle("-fx-text-fill: red;");

            }
            else{
                LocalDate start = expense_date_start.getValue();
                LocalDate end = expense_date_end.getValue();

                if(start.isAfter(end)){
                    expense_label_3.setText("Start date should be smaller than the End date");
                    expense_label_3.setStyle("-fx-text-fill: red;");
                }
                else {

                    for (int i = 0; i < Expenditure.ExpenditureList.size(); i++) {
                        LocalDate expense_date = Expenditure.ExpenditureList.get(i).getDate();

                        boolean isBetween = (expense_date.isAfter(start) && expense_date.isBefore(end)) || expense_date.equals(start) || expense_date.equals(end);

                        if (isBetween) {
                            searchResult.add(Expenditure.ExpenditureList.get(i));
                        }
                    }

                    // In your initialize method or wherever you set up the TableView
                    ObservableList<Expenditure> expenseObservableList = FXCollections.observableArrayList(searchResult);


                    table_expenseID.setCellValueFactory(new PropertyValueFactory<Expenditure, Integer>("ID"));
                    table_expenseDate.setCellValueFactory(new PropertyValueFactory<Expenditure , LocalDate>("Date"));
                    table_expenseValue.setCellValueFactory(new PropertyValueFactory<Expenditure , Double>("Value"));
                    table_expenseCategory.setCellValueFactory(new PropertyValueFactory<Expenditure , String>("Category"));

                    table_expense.setItems(expenseObservableList);


                }
            }
        }catch (DateTimeParseException e){
            expense_label_3.setText("Enter valid dates");
            expense_label_3.setStyle("-fx-text-fill: red;");

        }
    }



    public void addReminder(){

        try {
            LocalDate date = reminder_date.getValue();
            if ((reminder_value.getText().isEmpty()) ||( reminder_category.getText().isEmpty()) || (reminder_date.getValue() == null) || reminder_name.getText().isEmpty()) {

                reminder_label_1.setStyle("-fx-text-fill: red;");

                if (reminder_date.getValue() == null && !((reminder_value.getText().isEmpty()) || reminder_name.getText().isEmpty() ||( reminder_category.getText().isEmpty()))){
                    reminder_label_1.setText("Enter a valid date");
                }
                else{
                    reminder_label_1.setText("Field cannot be empty");
                }

            }
            else{
                if(Double.parseDouble(reminder_value.getText()) < 0) {
                    reminder_label_1.setStyle("-fx-text-fill: red;");
                    reminder_label_1.setText("Value cannot be negative");
                }

                else {

                    boolean is_yearly = false, is_monthly = false;
                    if(reminder_status.getSelectedToggle().equals(yearly)){
                        is_yearly = true;
                    }
                    else if (reminder_status.getSelectedToggle().equals(monthly)){
                        is_monthly = true;
                    }

                    Reminder reminder1 = new Reminder(reminder_name.getText(), reminder_category.getText(), reminder_date.getValue(), Double.parseDouble(reminder_value.getText()), is_monthly, is_yearly);
                    reminder_label_1.setStyle("-fx-text-fill: green;");
                    reminder_label_1.setText("Reminder added!");


                    System.out.println(showing_reminders.size());
                    setAllReminders_table();

                    reminders();
                    reminder_category.setText("");
                    reminder_date.setValue(null);
                    reminder_value.setText("");
                    reminder_name.setText("");
                }

            }

        }
        catch(NumberFormatException e) {
            reminder_label_1.setStyle("-fx-text-fill: red;");
            reminder_label_1.setText("Need a number in the Value field");
        }
        catch (DateTimeParseException e) {
            reminder_label_1.setText("Enter valid dates");
            reminder_label_1.setStyle("-fx-text-fill: red;");
        }

    }


    public void removeReminder(){

        try {

            if (reminder_ID.getText().isEmpty()){
                reminder_label_2.setStyle("-fx-text-fill: red;");
                reminder_label_2.setText("Please enter an ID");
            }
            else {
                int ID = Integer.parseInt(reminder_ID.getText());

                if (ID > 0){
                    if(Reminder.deleteReminder(ID)){
                        reminder_label_2.setStyle("-fx-text-fill: green;");

                        reminder_label_2.setText("Deleted!");
                        setAllReminders_table();

                    }
                    else {
                        reminder_label_2.setText("No reminder recorded with the ID:" + ID);
                        reminder_label_2.setTextFill(Color.RED);
                    }
                    reminder_ID.setText("");
                }
                else {
                    reminder_label_2.setStyle("-fx-text-fill: red;");
                    reminder_label_2.setText("Please enter a valid ID");

                }



            }
        }
        catch(NumberFormatException e) {
            reminder_label_2.setStyle("-fx-text-fill: red;");
            reminder_label_2.setText("ID is a positive number");

        }

    }

    public void setAllReminders_table(){

        table_reminder.getItems().clear();

        ObservableList<Reminder> reminderObservableList = FXCollections.observableArrayList(Reminder.reminderList);


        table_reminderID.setCellValueFactory(new PropertyValueFactory<Reminder , Integer>("ID"));
        table_reminderDate.setCellValueFactory(new PropertyValueFactory<Reminder , LocalDate>("Date"));
        table_reminderName.setCellValueFactory(new PropertyValueFactory<Reminder , String>("Name"));
        table_reminderValue.setCellValueFactory(new PropertyValueFactory<Reminder , Double>("Value"));
        table_reminderCategory.setCellValueFactory(new PropertyValueFactory<Reminder , String>("Category"));


        // Custom cell value factory for repeat column
        table_reminderRepeat.setCellValueFactory(cellData -> {
            Reminder reminder = cellData.getValue();
            boolean yearly = reminder.isYearly();
            boolean monthly = reminder.isMonthly();

           if (yearly) {
                return new SimpleStringProperty("Yearly");
            } else if (monthly) {
                return new SimpleStringProperty("Monthly");
            } else {
                return new SimpleStringProperty("Once");
            }
        });

        table_reminder.setItems(reminderObservableList);
    }

    public void setUpcomingReminders_table(){

        table_reminder.getItems().clear();


        Reminder.getUpcomingReminders();
        ObservableList<Reminder> reminderObservableList = FXCollections.observableArrayList(Reminder.upcomingReminders);


        table_reminderID.setCellValueFactory(new PropertyValueFactory<Reminder , Integer>("ID"));
        table_reminderDate.setCellValueFactory(new PropertyValueFactory<Reminder , LocalDate>("Date"));
        table_reminderName.setCellValueFactory(new PropertyValueFactory<Reminder , String>("Name"));
        table_reminderValue.setCellValueFactory(new PropertyValueFactory<Reminder , Double>("Value"));
        table_reminderCategory.setCellValueFactory(new PropertyValueFactory<Reminder , String>("Category"));

        // Custom cell value factory for repeat column
        table_reminderRepeat.setCellValueFactory(cellData -> {
            Reminder reminder = cellData.getValue();
            boolean yearly = reminder.isYearly();
            boolean monthly = reminder.isMonthly();

            if (yearly) {
                return new SimpleStringProperty("Yearly");
            } else if (monthly) {
                return new SimpleStringProperty("Monthly");
            } else {
                return new SimpleStringProperty("Once");
            }
        });


        table_reminder.setItems(reminderObservableList);
    }



    public void complete_reminderByID() {
        try {

            if (reminder_ID.getText().isEmpty()){
                reminder_label_2.setStyle("-fx-text-fill: red;");
                reminder_label_2.setText("Please enter an ID");
            }
            else {
                int ID = Integer.parseInt(reminder_ID.getText());

                if (ID > 0){
                    if(Reminder.payReminder(ID)){
                        reminder_label_2.setStyle("-fx-text-fill: green;");

                        reminder_label_2.setText("Completed!");
                        setAllReminders_table();

                    }
                    else {
                        reminder_label_2.setText("No reminder recorded with the ID:" + ID);
                        reminder_label_2.setTextFill(Color.RED);
                    }
                    reminder_ID.setText("");
                }
                else {
                    reminder_label_2.setStyle("-fx-text-fill: red;");
                    reminder_label_2.setText("Please enter a valid ID");

                }

                setAllReminders_table();

            }
        }
        catch(NumberFormatException e) {
            reminder_label_2.setStyle("-fx-text-fill: red;");
            reminder_label_2.setText("ID is a positive number");

        }
    }

    public void complete_reminder() {
        System.out.println("button clicked");
        int id = currently_showing_reminder.getID();


        System.out.println(deleteShowingReminders(id));



        reminders();
    }

    public void reminders() {
        System.out.println("reached the reminders ffunction ");


        if(showing_reminders.isEmpty()) {
            reminder_label.setVisible(false);
            complete_reminder_button.setVisible(false);
            reminder_label.setText("");
            System.out.println("Nothing ");
        }


        for(Reminder reminder : showing_reminders) {
            if(reminder.datePassed()) {
                reminder_label.setStyle("-fx-text-fill: red;");
                complete_reminder_button.setVisible(true);
                reminder_label.setVisible(true);
                reminder_label.setText(reminder.getName());
                currently_showing_reminder = reminder;

                //System.out.println("Reminder found " + reminder.getID());
                break;
            }
        }


        for(Reminder reminder : showing_reminders){
            if (reminder.getDate().isEqual(LocalDate.now())) {
                reminder_label.setStyle("-fx-text-fill: white;");
                complete_reminder_button.setVisible(true);
                reminder_label.setVisible(true);
                reminder_label.setText(reminder.getName());
                currently_showing_reminder = reminder;
                //System.out.println("Reminder found " + reminder.getID());
                break;
            }
        }


    }



    public void add_wishlist() {
        try {

            if (wishlist_name.getText().isEmpty() || wishlist_price.getText().isEmpty() || wishlist_rate.getText().isEmpty()) {

                wishlist_error_lbl.setStyle("-fx-text-fill: red;");
                wishlist_error_lbl.setText("Field cannot be empty");


            } else {
                if (Double.parseDouble(wishlist_price.getText()) <= 0 || Double.parseDouble(wishlist_rate.getText()) <= 0) {
                    wishlist_error_lbl.setStyle("-fx-text-fill: red;");
                    wishlist_error_lbl.setText("Price and Rate have to be greater than 0");


                } else {

                    if (Double.parseDouble(wishlist_rate.getText()) > 100) {
                        wishlist_error_lbl.setText("Rate cannot exceed 100.");
                    } else {
                        Wishlist w1 = new Wishlist(wishlist_name.getText(), Double.parseDouble(wishlist_price.getText()), Double.parseDouble(wishlist_rate.getText()));
                        wishlist_error_lbl.setStyle("-fx-text-fill: green;");
                        wishlist_error_lbl.setText("Wishlist added!");
                        wishlist_name.setText("");
                        wishlist_rate.setText("");
                        wishlist_price.setText("");
                        setAllWishlist_table();
                    }


                }

            }
        }
            catch(NumberFormatException e) {
                wishlist_error_lbl.setStyle("-fx-text-fill: red;");
                wishlist_error_lbl.setText("Need a number in the Numeric fields");
            }
    }

    public void removeWishlist(){

        try {

            if (redeem_wishlist_id.getText().isEmpty()){
                wishlist_label_2.setStyle("-fx-text-fill: red;");
                wishlist_label_2.setText("Please enter an ID");
            }
            else {
                int ID = Integer.parseInt(redeem_wishlist_id.getText());

                if (ID > 0){
                    if(Wishlist.deleteWishlist(ID)){
                        wishlist_label_2.setStyle("-fx-text-fill: green;");

                        wishlist_label_2.setText("Deleted!");
                        setAllWishlist_table();

                    }
                    else {
                        wishlist_label_2.setText("No wishlist recorded with the ID:" + ID);
                        wishlist_label_2.setTextFill(Color.RED);
                    }
                    redeem_wishlist_id.setText("");
                }
                else {
                    wishlist_label_2.setStyle("-fx-text-fill: red;");
                    wishlist_label_2.setText("Please enter a valid ID");

                }

                setAllWishlist_table();

            }
        }
        catch(NumberFormatException e) {
            wishlist_label_2.setStyle("-fx-text-fill: red;");
            wishlist_label_2.setText("ID is a positive number");

        }


    }

    public void setAllWishlist_table() {
        table_wishlist.getItems().clear();
        Wishlist.calculateAmountSaved();


        ObservableList<Wishlist> wishlistObservableList = FXCollections.observableArrayList(Wishlist.wishlists);


        table_wishlistID.setCellValueFactory(new PropertyValueFactory<Wishlist , Integer>("ID"));
        table_wishlistname.setCellValueFactory(new PropertyValueFactory<Wishlist , String>("item_name"));
        table_wishlistprice.setCellValueFactory(new PropertyValueFactory<Wishlist , Double>("item_price"));
        table_wishlistrate.setCellValueFactory(new PropertyValueFactory<Wishlist, Double>("rate"));
        table_wishlistAmount.setCellValueFactory(new PropertyValueFactory<Wishlist, Double>("AmountSaved"));
        table_wishlistdate.setCellValueFactory(new PropertyValueFactory<Wishlist , LocalDate>("lastCalculationDate"));

        // Custom cell value factory for repeat column
        table_wishlistredeemable.setCellValueFactory(cellData -> {
            Wishlist wishlist = cellData.getValue();
            Boolean is_redeemable = wishlist.isRedeemable();


            if (is_redeemable) {
                return new SimpleStringProperty("Yes");
            } else {
                return new SimpleStringProperty("No");
            }
        });

        table_wishlist.setItems(wishlistObservableList);

    }

    public void setRedeemableWishlist_table(){

        table_wishlist.getItems().clear();
        Wishlist.calculateAmountSaved();

        ObservableList<Wishlist> wishlistObservableList = FXCollections.observableArrayList(Wishlist.redeemable);
        System.out.println(Wishlist.redeemable.size());


        table_wishlistID.setCellValueFactory(new PropertyValueFactory<Wishlist , Integer>("ID"));
        table_wishlistname.setCellValueFactory(new PropertyValueFactory<Wishlist , String>("item_name"));
        table_wishlistprice.setCellValueFactory(new PropertyValueFactory<Wishlist , Double>("item_price"));
        table_wishlistrate.setCellValueFactory(new PropertyValueFactory<Wishlist, Double>("rate"));
        table_wishlistAmount.setCellValueFactory(new PropertyValueFactory<Wishlist, Double>("AmountSaved"));
        table_wishlistdate.setCellValueFactory(new PropertyValueFactory<Wishlist , LocalDate>("lastCalculationDate"));

        // Custom cell value factory for repeat column
        table_wishlistredeemable.setCellValueFactory(cellData -> {
            Wishlist wishlist = cellData.getValue();
            Boolean is_redeemable = wishlist.isRedeemable();


            if (is_redeemable) {
                return new SimpleStringProperty("Yes");
            } else {
                return new SimpleStringProperty("No");
            }
        });

        table_wishlist.setItems(wishlistObservableList);
    }

    public void redeem_wishlist_item() {
        try {

            if (redeem_wishlist_id.getText().isEmpty()){
                wishlist_label_2.setStyle("-fx-text-fill: red;");
                wishlist_label_2.setText("Please enter an ID");
            }
            else {
                int ID = Integer.parseInt(redeem_wishlist_id.getText());

                if (ID > 0){
                    if(Wishlist.redeem(ID)){
                        wishlist_label_2.setStyle("-fx-text-fill: green;");

                        wishlist_label_2.setText("Redeemed!");
                        setAllWishlist_table();

                    }
                    else {
                        wishlist_label_2.setText("Not redeemable or no wishlist recorded with the ID:" + ID);
                        wishlist_label_2.setTextFill(Color.RED);
                    }
                    redeem_wishlist_id.setText("");
                }
                else {
                    wishlist_label_2.setStyle("-fx-text-fill: red;");
                    wishlist_label_2.setText("Please enter a valid ID");

                }

                setAllWishlist_table();

            }
        }
        catch(NumberFormatException e) {
            wishlist_label_2.setStyle("-fx-text-fill: red;");
            wishlist_label_2.setText("ID is a positive number");

        }

    }



    public void show_home() {
        expanded_menu_pane.setVisible(false);
        income.setVisible(false);
        expense.setVisible(false);
        home_pane.setVisible(true);
        reminder.setVisible(false);
        wishlist.setVisible(false);

        BarGraphs.displayBarChart(HomeBarChart);

    }

    public void show_income() {

        expanded_menu_pane.setVisible(false);
        income.setVisible(true);
        expense.setVisible(false);
        home_pane.setVisible(false);
        reminder.setVisible(false);
        wishlist.setVisible(false);

        setAllIncomes_table();
        income_label_1.setText("");
        income_label_2.setText("");
        income_label_3.setText("");
        income_date.setValue(null);
        income_date_end.setValue(null);
        income_value.setText("");
        income_source.setText("");
        income_ID.setText("");
    }

    public void show_expense() {

        expanded_menu_pane.setVisible(false);
        income.setVisible(false);
        expense.setVisible(true);
        home_pane.setVisible(false);
        reminder.setVisible(false);
        wishlist.setVisible(false);

        setAllExpenses_table();
        expense_label_1.setText("");
        expense_label_2.setText("");
        expense_label_3.setText("");
        expense_date.setValue(null);
        expense_date_end.setValue(null);
        expense_date_start.setValue(null);
        expense_value.setText("");
        expense_category.setText("");
        expense_ID.setText("");
    }

    public void show_reminder() {
        expanded_menu_pane.setVisible(false);
        income.setVisible(false);
        expense.setVisible(false);
        home_pane.setVisible(false);
        reminder.setVisible(true);
        wishlist.setVisible(false);

        setAllReminders_table();
        reminder_label_1.setText("");
        reminder_label_2.setText("");
        reminder_date.setValue(null);
        reminder_value.setText("");
        reminder_category.setText("");
        reminder_ID.setText("");
        reminder_name.setText("");

    }

    public void show_wishlist() {
        expanded_menu_pane.setVisible(false);
        income.setVisible(false);
        expense.setVisible(false);
        home_pane.setVisible(false);
        reminder.setVisible(false);
        wishlist.setVisible(true);

        setAllWishlist_table();
        wishlist_error_lbl.setText("");
        wishlist_label_2.setText("");
        wishlist_price.setText("");
        wishlist_rate.setText("");
        redeem_wishlist_id.setText("");
        wishlist_name.setText("");
    }

    @FXML
    public void handleImageClick(MouseEvent event) {


        contextMenu.show(avatar_image, event.getScreenX(), event.getScreenY());

    }


    public void logout() {
        helloController.helloApplication.showLoginPage();
    }


}
