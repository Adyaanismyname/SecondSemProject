package com.example.secondsemproject;

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
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
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
    private Label monthly_income;

    @FXML
    private Label monthly_expenses;

    @FXML
    private Label monthly_savings;
    @FXML
    private Label total_savings;

    @FXML
    private Label zakaat;

    double mon_expense = Expenditure.getMonthExpense(LocalDate.now().getMonthValue() , LocalDate.now().getYear()) ;

    double mon_income = Income.getMonthIncome(LocalDate.now().getMonthValue() , LocalDate.now().getYear());

    double mon_savings = mon_income - mon_expense;

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



        main.setIdForTable("Wishlist");
        main.setIdForTable("Expenditure");
        main.setIdForTable("Income");
        main.setIdForTable("Reminder");

        //SETTING HOME PAGE
        main.load_Data_AL();

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

        monthly_expenses.setText("");
        monthly_income.setText("");
        monthly_savings.setText("");
        zakaat.setText("");


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
        getUpcomingReminders();
        reminders();
        BarGraphs.displayBarChart(HomeBarChart);
        HomeLabels();

    }

    //method to exit
    @FXML
    public void exit(Event event){
     Wishlist.calculateAmountSaved();
     main.delete_Previous_Data();
     main.Load_into_databsae();
        System.exit(0);
    }

    //minimize method
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



    public void addIncome() {
        // Add income function

        try {
            // Get income date
            LocalDate date = income_date.getValue();

            // Check if any of the fields are empty
            if ((income_value.getText().isEmpty()) || (income_source.getText().isEmpty()) || (income_date.getValue() == null)) {

                // Set label styles and texts based on conditions
                income_label_1.setStyle("-fx-text-fill: red;");
                if (income_date.getValue() == null && !((income_value.getText().isEmpty()) || (income_source.getText().isEmpty()))) {
                    income_label_1.setText("Enter a valid date");
                } else {
                    income_label_1.setText("Field cannot be empty");
                }

            } else {
                // Check if income value is positive
                if (Double.parseDouble(income_value.getText()) <= 0) {
                    income_label_1.setStyle("-fx-text-fill: red;");
                    income_label_1.setText("Value should be positive");
                } else {
                    // Create Income object
                    Income income = new Income(income_source.getText(), income_date.getValue(), Double.parseDouble(income_value.getText()));
                    income_label_1.setStyle("-fx-text-fill: green;");
                    income_label_1.setText("Income added!");

                    // Update table and clear fields
                    setAllIncomes_table();
                    income_source.setText("");
                    income_date.setValue(null);
                    income_value.setText("");

                    // Update labels
                    HomeLabels();
                }
            }
        } catch (NumberFormatException e) {
            // Handle non-numeric input in value field
            income_label_1.setStyle("-fx-text-fill: red;");
            income_label_1.setText("Need a number in the Value field");
        } catch (DateTimeParseException e) {
            // Handle invalid date format
            income_label_1.setText("Enter valid dates");
            income_label_1.setStyle("-fx-text-fill: red;");
        }
    }

    public void removeIncome() {
        // Remove income function

        try {
            // Check if income ID field is empty
            if (income_ID.getText().isEmpty()) {
                income_label_2.setStyle("-fx-text-fill: red;");
                income_label_2.setText("Please enter an ID");
            } else {
                int ID = Integer.parseInt(income_ID.getText());

                if (ID >= 0) {
                    // Delete income if ID is valid
                    if (Income.deleteIncome(ID)) {
                        income_label_2.setStyle("-fx-text-fill: green;");
                        income_label_2.setText("Deleted!");

                        // Update table and labels
                        setAllIncomes_table();
                        HomeLabels();
                    } else {
                        // Handle if no income recorded with the ID
                        income_label_2.setText("No income recorded with the ID:" + ID);
                        income_label_2.setTextFill(Color.RED);
                    }
                    // Clear ID field
                    income_ID.setText("");
                } else {
                    income_label_2.setStyle("-fx-text-fill: red;");
                    income_label_2.setText("Please enter a valid ID");
                }
            }
        } catch (NumberFormatException e) {
            // Handle non-numeric input in ID field
            income_label_2.setStyle("-fx-text-fill: red;");
            income_label_2.setText("ID is a positive number");
        }
    }

    public void setAllIncomes_table() {
        // Update table with all incomes

        table_Income.getItems().clear();

        // Create observable list from income list
        ObservableList<Income> incomeObservableList = FXCollections.observableArrayList(Income.incomeList);

        // Set cell value factories
        table_IncomeID.setCellValueFactory(new PropertyValueFactory<Income, Integer>("ID"));
        table_IncomeDate.setCellValueFactory(new PropertyValueFactory<Income, LocalDate>("Date"));
        table_IncomeValue.setCellValueFactory(new PropertyValueFactory<Income, Double>("Value"));
        table_IncomeSource.setCellValueFactory(new PropertyValueFactory<Income, String>("Source"));

        // Set items in table
        table_Income.setItems(incomeObservableList);
    }

    public void setSearchIncomes_table() {
        // Search and update income table

        table_Income.getItems().clear();

        // List to hold search results
        ArrayList<Income> searchResult = new ArrayList<>();
        try {
            if (income_date_start.getValue() == null || income_date_end.getValue() == null) {
                // Handle if start or end date is not selected
                income_label_3.setText("Enter valid dates");
                income_label_3.setStyle("-fx-text-fill: red;");
            } else {
                LocalDate start = income_date_start.getValue();
                LocalDate end = income_date_end.getValue();

                if (start.isAfter(end)) {
                    // Handle if start date is after end date
                    income_label_3.setText("Start date should be smaller than the End date");
                    income_label_3.setStyle("-fx-text-fill: red; -fx-font-size: 9;");
                } else {
                    // Search for incomes within the specified date range
                    for (int i = 0; i < Income.incomeList.size(); i++) {
                        LocalDate income_date = Income.incomeList.get(i).getDate();
                        boolean isBetween = (income_date.isAfter(start) && income_date.isBefore(end)) || income_date.equals(start) || income_date.equals(end);

                        if (isBetween) {
                            searchResult.add(Income.incomeList.get(i));
                        }
                    }

                    // Update table with search results
                    ObservableList<Income> incomeObservableList = FXCollections.observableArrayList(searchResult);
                    table_IncomeID.setCellValueFactory(new PropertyValueFactory<Income, Integer>("ID"));
                    table_IncomeDate.setCellValueFactory(new PropertyValueFactory<Income, LocalDate>("Date"));
                    table_IncomeValue.setCellValueFactory(new PropertyValueFactory<Income, Double>("Value"));
                    table_IncomeSource.setCellValueFactory(new PropertyValueFactory<Income, String>("Source"));
                    table_Income.setItems(incomeObservableList);
                    income_label_3.setText("");
                }
            }
        } catch (DateTimeParseException e) {
            // Handle invalid date format
            income_label_3.setText("Enter valid dates");
            income_label_3.setStyle("-fx-text-fill: red;");
        }
    }


    public void addExpense() {
        // Add expense function

        try {
            // Get expense date
            LocalDate date = expense_date.getValue();

            // Check if any of the fields are empty
            if ((expense_value.getText().isEmpty()) || (expense_category.getText().isEmpty()) || (expense_date.getValue() == null)) {

                // Set label styles and texts based on conditions
                expense_label_1.setStyle("-fx-text-fill: red;");
                if (expense_date.getValue() == null && !((expense_value.getText().isEmpty()) || (expense_category.getText().isEmpty()))) {
                    expense_label_1.setText("Enter a valid date");
                } else {
                    expense_label_1.setText("Field cannot be empty");
                }

            } else {
                // Check if expense value is positive
                if (Double.parseDouble(expense_value.getText()) <= 0) {
                    expense_label_1.setStyle("-fx-text-fill: red;");
                    expense_label_1.setText("Value should be positive");
                } else {
                    // Create Expenditure object
                    Expenditure expense1 = new Expenditure(expense_category.getText(), expense_date.getValue(), Double.parseDouble(expense_value.getText()));
                    expense_label_1.setStyle("-fx-text-fill: green;");
                    expense_label_1.setText("Expense added!");

                    // Update table and clear fields
                    setAllExpenses_table();
                    expense_category.setText("");
                    expense_date.setValue(null);
                    expense_value.setText("");
                    HomeLabels();
                }
            }

        } catch (NumberFormatException e) {
            // Handle non-numeric input in value field
            expense_label_1.setStyle("-fx-text-fill: red;");
            expense_label_1.setText("Need a number in the Value field");
        } catch (DateTimeParseException e) {
            // Handle invalid date format
            expense_label_1.setText("Enter valid dates");
            expense_label_1.setStyle("-fx-text-fill: red;");
        }

    }


    public void removeExpense() {
        // Remove expense function

        try {

            // Check if expense ID field is empty
            if (expense_ID.getText().isEmpty()) {
                expense_label_2.setStyle("-fx-text-fill: red;");
                expense_label_2.setText("Please enter an ID");
            } else {
                int ID = Integer.parseInt(expense_ID.getText());

                if (ID >= 0) {
                    // Delete expense if ID is valid
                    if (Expenditure.deleteExpense(ID)) {
                        expense_label_2.setStyle("-fx-text-fill: green;");
                        expense_label_2.setText("Deleted!");

                        // Update table and labels
                        setAllExpenses_table();
                        HomeLabels();

                    } else {
                        // Handle if no expense recorded with the ID
                        expense_label_2.setText("No expense recorded with the ID:" + ID);
                        expense_label_2.setTextFill(Color.RED);
                    }
                    // Clear ID field
                    expense_ID.setText("");
                } else {
                    expense_label_2.setStyle("-fx-text-fill: red;");
                    expense_label_2.setText("Please enter a valid ID");

                }
            }
        } catch (NumberFormatException e) {
            // Handle non-numeric input in ID field
            expense_label_2.setStyle("-fx-text-fill: red;");
            expense_label_2.setText("ID is a positive number");

        }

    }

    public void setAllExpenses_table() {
        // Update table with all expenses

        table_expense.getItems().clear();

        // Create observable list from expense list
        ObservableList<Expenditure> expenseObservableList = FXCollections.observableArrayList(Expenditure.ExpenditureList);

        // Set cell value factories
        table_expenseID.setCellValueFactory(new PropertyValueFactory<Expenditure, Integer>("ID"));
        table_expenseDate.setCellValueFactory(new PropertyValueFactory<Expenditure, LocalDate>("Date"));
        table_expenseValue.setCellValueFactory(new PropertyValueFactory<Expenditure, Double>("Value"));
        table_expenseCategory.setCellValueFactory(new PropertyValueFactory<Expenditure, String>("Category"));

        // Set items in table
        table_expense.setItems(expenseObservableList);
    }


    public void setSearchExpense_table() {
        // Search and update expense table

        table_expense.getItems().clear();

        // List to hold search results
        ArrayList<Expenditure> searchResult = new ArrayList<>();
        try {
            if (expense_date_start.getValue() == null || expense_date_end.getValue() == null) {
                // Handle if start or end date is not selected
                expense_label_3.setText("Enter valid dates");
                expense_label_3.setStyle("-fx-text-fill: red;");
            } else {
                LocalDate start = expense_date_start.getValue();
                LocalDate end = expense_date_end.getValue();

                if (start.isAfter(end)) {
                    // Handle if start date is after end date
                    expense_label_3.setText("Start date should be smaller than the End date");
                    expense_label_3.setStyle("-fx-text-fill: red;-fx-font-size: 9");
                } else {
                    // Search for expenses within the specified date range
                    for (int i = 0; i < Expenditure.ExpenditureList.size(); i++) {
                        LocalDate expense_date = Expenditure.ExpenditureList.get(i).getDate();

                        boolean isBetween = (expense_date.isAfter(start) && expense_date.isBefore(end)) || expense_date.equals(start) || expense_date.equals(end);

                        if (isBetween) {
                            searchResult.add(Expenditure.ExpenditureList.get(i));
                        }
                    }

                    // Update table with search results
                    ObservableList<Expenditure> expenseObservableList = FXCollections.observableArrayList(searchResult);
                    table_expenseID.setCellValueFactory(new PropertyValueFactory<Expenditure, Integer>("ID"));
                    table_expenseDate.setCellValueFactory(new PropertyValueFactory<Expenditure, LocalDate>("Date"));
                    table_expenseValue.setCellValueFactory(new PropertyValueFactory<Expenditure, Double>("Value"));
                    table_expenseCategory.setCellValueFactory(new PropertyValueFactory<Expenditure, String>("Category"));
                    table_expense.setItems(expenseObservableList);
                    expense_label_3.setText("");
                }
            }
        } catch (DateTimeParseException e) {
            // Handle invalid date format
            expense_label_3.setText("");
            expense_label_3.setStyle("-fx-text-fill: red;");

        }
    }


    public void addReminder() {
        try {
            // Getting input values
            LocalDate date = reminder_date.getValue();

            // Checking for empty fields
            if (reminder_value.getText().isEmpty() || reminder_category.getText().isEmpty() || reminder_date.getValue() == null || reminder_name.getText().isEmpty()) {
                reminder_label_1.setStyle("-fx-text-fill: red;");
                if (reminder_date.getValue() == null && !(reminder_value.getText().isEmpty() || reminder_name.getText().isEmpty() || reminder_category.getText().isEmpty())) {
                    reminder_label_1.setText("Enter a valid date");
                } else {
                    reminder_label_1.setText("Field cannot be empty");
                }
            } else {
                // Checking for positive value
                if (Double.parseDouble(reminder_value.getText()) <= 0) {
                    reminder_label_1.setStyle("-fx-text-fill: red;");
                    reminder_label_1.setText("Value should be positive");
                } else {
                    // Checking for yearly or monthly reminder
                    boolean is_yearly = false, is_monthly = false;
                    if (reminder_status.getSelectedToggle().equals(yearly)) {
                        is_yearly = true;
                    } else if (reminder_status.getSelectedToggle().equals(monthly)) {
                        is_monthly = true;
                    }

                    // Creating reminder object
                    Reminder reminder1 = new Reminder(reminder_name.getText(), reminder_category.getText(), reminder_date.getValue(), Double.parseDouble(reminder_value.getText()), is_monthly, is_yearly);
                    reminder_label_1.setStyle("-fx-text-fill: green;");
                    reminder_label_1.setText("Reminder added!");

                    // Refreshing UI
                    setAllReminders_table();
                    HomeLabels();
                    reminders();

                    // Clearing input fields
                    reminder_category.setText("");
                    reminder_date.setValue(null);
                    reminder_value.setText("");
                    reminder_name.setText("");
                }
            }
        } catch (NumberFormatException e) {
            reminder_label_1.setStyle("-fx-text-fill: red;");
            reminder_label_1.setText("Need a number in the Value field");
        } catch (DateTimeParseException e) {
            reminder_label_1.setText("Enter valid dates");
            reminder_label_1.setStyle("-fx-text-fill: red;");
        }
    }

    public void removeReminder() {
        try {
            // Removing reminder by ID
            if (reminder_ID.getText().isEmpty()) {
                reminder_label_2.setStyle("-fx-text-fill: red;");
                reminder_label_2.setText("Please enter an ID");
            } else {
                int ID = Integer.parseInt(reminder_ID.getText());
                if (ID >= 0) {
                    // Deleting reminder
                    if (Reminder.deleteReminder(ID)) {
                        reminder_label_2.setStyle("-fx-text-fill: green;");
                        reminder_label_2.setText("Deleted!");
                        setAllReminders_table();
                        HomeLabels();
                    } else {
                        reminder_label_2.setText("No reminder recorded with the ID:" + ID);
                        reminder_label_2.setTextFill(Color.RED);
                    }
                    reminder_ID.setText("");
                } else {
                    reminder_label_2.setStyle("-fx-text-fill: red;");
                    reminder_label_2.setText("Please enter a valid ID");
                }
            }
        } catch (NumberFormatException e) {
            reminder_label_2.setStyle("-fx-text-fill: red;");
            reminder_label_2.setText("ID is a positive number");
        }
    }

    public void setAllReminders_table() {
        // Clearing table items
        table_reminder.getItems().clear();

        // Creating observable list
        ObservableList<Reminder> reminderObservableList = FXCollections.observableArrayList(Reminder.reminderList);

        // Setting cell value factories
        table_reminderID.setCellValueFactory(new PropertyValueFactory<Reminder, Integer>("ID"));
        table_reminderDate.setCellValueFactory(new PropertyValueFactory<Reminder, LocalDate>("Date"));
        table_reminderName.setCellValueFactory(new PropertyValueFactory<Reminder, String>("Name"));
        table_reminderValue.setCellValueFactory(new PropertyValueFactory<Reminder, Double>("Value"));
        table_reminderCategory.setCellValueFactory(new PropertyValueFactory<Reminder, String>("Category"));

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

    public void setUpcomingReminders_table() {
        // Clearing table items
        table_reminder.getItems().clear();

        // Getting upcoming reminders
        Reminder.getUpcomingReminders();
        ObservableList<Reminder> reminderObservableList = FXCollections.observableArrayList(Reminder.upcomingReminders);

        // Setting cell value factories
        table_reminderID.setCellValueFactory(new PropertyValueFactory<Reminder, Integer>("ID"));
        table_reminderDate.setCellValueFactory(new PropertyValueFactory<Reminder, LocalDate>("Date"));
        table_reminderName.setCellValueFactory(new PropertyValueFactory<Reminder, String>("Name"));
        table_reminderValue.setCellValueFactory(new PropertyValueFactory<Reminder, Double>("Value"));
        table_reminderCategory.setCellValueFactory(new PropertyValueFactory<Reminder, String>("Category"));

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
            // Completing reminder by ID
            if (reminder_ID.getText().isEmpty()) {
                reminder_label_2.setStyle("-fx-text-fill: red;");
                reminder_label_2.setText("Please enter an ID");
            } else {
                int ID = Integer.parseInt(reminder_ID.getText());
                if (ID >= 0) {
                    // Marking reminder as completed
                    if (Reminder.payReminder(ID)) {
                        reminder_label_2.setStyle("-fx-text-fill: green;");
                        reminder_label_2.setText("Completed!");
                        setAllReminders_table();
                        HomeLabels();
                    } else {
                        reminder_label_2.setText("No reminder recorded with the ID:" + ID);
                        reminder_label_2.setTextFill(Color.RED);
                    }
                    reminder_ID.setText("");
                } else {
                    reminder_label_2.setStyle("-fx-text-fill: red;");
                    reminder_label_2.setText("Please enter a valid ID");
                }
                setAllReminders_table();
            }
        } catch (NumberFormatException e) {
            reminder_label_2.setStyle("-fx-text-fill: red;");
            reminder_label_2.setText("ID is a positive number");
        }
    }

    public void complete_reminder() {
        // Completing reminder
        int id = currently_showing_reminder.getID();
        deleteShowingReminders(id);
        reminders();
    }

    public void reminders() {
        // Displaying reminders

        if (showing_reminders.isEmpty()) {
            reminder_label.setVisible(false);
            complete_reminder_button.setVisible(false);
            reminder_label.setText("");
        }

        for (Reminder reminder : showing_reminders) {
            if (reminder.datePassed()) {
                reminder_label.setStyle("-fx-text-fill: red;");
                complete_reminder_button.setVisible(true);
                reminder_label.setVisible(true);
                reminder_label.setText(reminder.getName());
                currently_showing_reminder = reminder;
                break;
            }
        }

        for (Reminder reminder : showing_reminders) {
            if (reminder.getDate().isEqual(LocalDate.now())) {
                reminder_label.setStyle("-fx-text-fill: white;");
                complete_reminder_button.setVisible(true);
                reminder_label.setVisible(true);
                reminder_label.setText(reminder.getName());
                currently_showing_reminder = reminder;
                break;
            }
        }
    }

    public void add_wishlist() {
        try {
            // Adding wishlist
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
                        // Creating wishlist object
                        Wishlist w1 = new Wishlist(wishlist_name.getText(), Double.parseDouble(wishlist_price.getText()), Double.parseDouble(wishlist_rate.getText()));
                        wishlist_error_lbl.setStyle("-fx-text-fill: green;");
                        wishlist_error_lbl.setText("Wishlist added!");
                        wishlist_name.setText("");
                        wishlist_rate.setText("");
                        wishlist_price.setText("");
                        setAllWishlist_table();
                        HomeLabels();
                    }
                }
            }
        } catch (NumberFormatException e) {
            wishlist_error_lbl.setStyle("-fx-text-fill: red;");
            wishlist_error_lbl.setText("Need a number in the Numeric fields");
        }
    }

    public void removeWishlist() {
        try {
            // Removing wishlist
            if (redeem_wishlist_id.getText().isEmpty()) {
                wishlist_label_2.setStyle("-fx-text-fill: red;");
                wishlist_label_2.setText("Please enter an ID");
            } else {
                int ID = Integer.parseInt(redeem_wishlist_id.getText());
                if (ID >= 0) {
                    // Deleting wishlist
                    if (Wishlist.deleteWishlist(ID)) {
                        wishlist_label_2.setStyle("-fx-text-fill: green;");
                        wishlist_label_2.setText("Deleted!");
                        setAllWishlist_table();
                        HomeLabels();
                    } else {
                        wishlist_label_2.setText("No wishlist recorded with the ID:" + ID);
                        wishlist_label_2.setTextFill(Color.RED);
                    }
                    redeem_wishlist_id.setText("");
                } else {
                    wishlist_label_2.setStyle("-fx-text-fill: red;");
                    wishlist_label_2.setText("Please enter a valid ID");
                }
                setAllWishlist_table();
            }
        } catch (NumberFormatException e) {
            wishlist_label_2.setStyle("-fx-text-fill: red;");
            wishlist_label_2.setText("ID is a positive number");
        }
    }

    public void setAllWishlist_table() {
        // Clearing table items
        table_wishlist.getItems().clear();
        Wishlist.calculateAmountSaved();

        // Creating observable list
        ObservableList<Wishlist> wishlistObservableList = FXCollections.observableArrayList(Wishlist.wishlists);

        // Setting cell value factories
        table_wishlistID.setCellValueFactory(new PropertyValueFactory<Wishlist, Integer>("ID"));
        table_wishlistname.setCellValueFactory(new PropertyValueFactory<Wishlist, String>("item_name"));
        table_wishlistprice.setCellValueFactory(new PropertyValueFactory<Wishlist, Double>("item_price"));
        table_wishlistrate.setCellValueFactory(new PropertyValueFactory<Wishlist, Double>("rate"));
        table_wishlistAmount.setCellValueFactory(new PropertyValueFactory<Wishlist, Double>("AmountSaved"));
        table_wishlistdate.setCellValueFactory(new PropertyValueFactory<Wishlist, LocalDate>("lastCalculationDate"));

        // Custom cell value factory for redeemable column
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

    public void setRedeemableWishlist_table() {
        // Clearing table items
        table_wishlist.getItems().clear();
        Wishlist.calculateAmountSaved();

        // Creating observable list
        ObservableList<Wishlist> wishlistObservableList = FXCollections.observableArrayList(Wishlist.redeemable);
        System.out.println(Wishlist.redeemable.size());

        // Setting cell value factories
        table_wishlistID.setCellValueFactory(new PropertyValueFactory<Wishlist, Integer>("ID"));
        table_wishlistname.setCellValueFactory(new PropertyValueFactory<Wishlist, String>("item_name"));
        table_wishlistprice.setCellValueFactory(new PropertyValueFactory<Wishlist, Double>("item_price"));
        table_wishlistrate.setCellValueFactory(new PropertyValueFactory<Wishlist, Double>("rate"));
        table_wishlistAmount.setCellValueFactory(new PropertyValueFactory<Wishlist, Double>("AmountSaved"));
        table_wishlistdate.setCellValueFactory(new PropertyValueFactory<Wishlist, LocalDate>("lastCalculationDate"));

        // Custom cell value factory for redeemable column
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
            // Redeeming wishlist item
            if (redeem_wishlist_id.getText().isEmpty()) {
                wishlist_label_2.setStyle("-fx-text-fill: red;");
                wishlist_label_2.setText("Please enter an ID");
            } else {
                int ID = Integer.parseInt(redeem_wishlist_id.getText());
                if (ID >= 0) {
                    // Redeeming wishlist
                    if (Wishlist.redeem(ID)) {
                        wishlist_label_2.setStyle("-fx-text-fill: green;");
                        wishlist_label_2.setText("Redeemed!");
                        setAllWishlist_table();
                    } else {
                        wishlist_label_2.setText("Not redeemable or no wishlist recorded with the ID:" + ID);
                        wishlist_label_2.setTextFill(Color.RED);
                    }
                    redeem_wishlist_id.setText("");
                } else {
                    wishlist_label_2.setStyle("-fx-text-fill: red;");
                    wishlist_label_2.setText("Please enter a valid ID");
                }
                setAllWishlist_table();
                HomeLabels();
            }
        } catch (NumberFormatException e) {
            wishlist_label_2.setStyle("-fx-text-fill: red;");
            wishlist_label_2.setText("ID is a positive number");
        }
    }

    public void show_home() {
        // Displaying home pane
        expanded_menu_pane.setVisible(false);
        income.setVisible(false);
        expense.setVisible(false);
        home_pane.setVisible(true);
        reminder.setVisible(false);
        wishlist.setVisible(false);
        HomeLabels();
        BarGraphs.displayBarChart(HomeBarChart);
        clearHome();
    }

    public void show_income() {
        // Displaying income pane
        expanded_menu_pane.setVisible(false);
        income.setVisible(true);
        expense.setVisible(false);
        home_pane.setVisible(false);
        reminder.setVisible(false);
        wishlist.setVisible(false);
        setAllIncomes_table();
        clearHome();
    }

    public void show_expense() {
        // Displaying expense pane
        expanded_menu_pane.setVisible(false);
        income.setVisible(false);
        expense.setVisible(true);
        home_pane.setVisible(false);
        reminder.setVisible(false);
        wishlist.setVisible(false);
        setAllExpenses_table();
        clearHome();
    }

    public void show_reminder() {
        // Displaying reminder pane
        expanded_menu_pane.setVisible(false);
        income.setVisible(false);
        expense.setVisible(false);
        home_pane.setVisible(false);
        reminder.setVisible(true);
        wishlist.setVisible(false);
        setAllReminders_table();
        clearHome();
    }

    public void show_wishlist() {
        // Displaying wishlist pane
        expanded_menu_pane.setVisible(false);
        income.setVisible(false);
        expense.setVisible(false);
        home_pane.setVisible(false);
        reminder.setVisible(false);
        wishlist.setVisible(true);
        Wishlist.calculateAmountSaved();
        setAllWishlist_table();
        clearHome();
    }

    @FXML
    public void handleImageClick(MouseEvent event) {
        // Displaying context menu on image click
        contextMenu.show(avatar_image, event.getScreenX(), event.getScreenY());
    }

    public void logout() {
        // Logging out user
        Wishlist.calculateAmountSaved();
        main.delete_Previous_Data();
        main.Load_into_databsae();
        helloController.helloApplication.showLoginPage();
    }

    public void HomeLabels() {
        // Updating home labels
        double mon_income, mon_expense, mon_savings;
        mon_expense = Expenditure.getMonthExpense(LocalDate.now().getMonthValue(), LocalDate.now().getYear());
        mon_income = Income.getMonthIncome(LocalDate.now().getMonthValue(), LocalDate.now().getYear());
        mon_savings = mon_income - mon_expense;
        monthly_expenses.setText(String.valueOf(mon_expense));
        monthly_income.setText(String.valueOf(mon_income));


        if (mon_savings > 0) {
            monthly_savings.setText(String.valueOf(mon_savings));
        } else {
            monthly_savings.setText("0");
        }

        double total = Income.getTotal() - Expenditure.getTotal();

        if (total > 0) {
            total_savings.setText(String.valueOf(total));
        } else {
            total_savings.setText("0");
        }

        zakaat.setText(String.valueOf(Double.max(.025 * (Income.getTotal() - Expenditure.getTotal()), 0)));

    }

    public void clearHome() {
        // Resetting home UI components
        wishlist_name.setText("");
        wishlist_price.setText("");
        wishlist_rate.setText("");
        redeem_wishlist_id.setText("");
        income_ID.setText("");
        income_source.setText("");
        income_value.setText("");
        expense_ID.setText("");
        expense_category.setText("");
        expense_value.setText("");
        reminder_ID.setText("");
        reminder_category.setText("");
        reminder_name.setText("");
        reminder_value.setText("");
        income_date.setValue(null);
        income_date_end.setValue(null);
        income_date_start.setValue(null);
        expense_date.setValue(null);
        expense_date_end.setValue(null);
        expense_date_start.setValue(null);
        reminder_date.setValue(null);
        wishlist_error_lbl.setText("");
        wishlist_label_2.setText("");
        income_label_1.setText("");
        income_label_2.setText("");
        income_label_3.setText("");
        expense_label_1.setText("");
        expense_label_2.setText("");
        expense_label_3.setText("");
        reminder_label_1.setText("");
        reminder_label_2.setText("");
    }
}
