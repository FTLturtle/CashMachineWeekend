package rocks.zipcode.atm;

import javafx.scene.control.Label;
import javafx.stage.Stage;
import rocks.zipcode.atm.bank.Bank;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.layout.FlowPane;

/**
 * @author ZipCodeWilmington
 */
public class CashMachineApp extends Application {

    private CashMachine cashMachine = new CashMachine(new Bank()); // instantiating a cash machine

    private Scene mainMenuScene, registerAccountScene, loginAccountScene, accessAccountScene;
    // this GUI has four screens that you can navigate through: the main menu,
    // the register new account screen, the login screen, and the access your account screen

    private TextArea accessScreenDisplay = new TextArea(); // for displaying your account info in the accessAccountScene
    private TextArea loginScreenDisplay = new TextArea(); // for displaying feedback in the loginAccountScene
    private TextArea registerScreenDisplay = new TextArea(); // for displaying feedback in the registerAccountScene

    /**
     * This method creates the scene for the mainMenuScene. The main menu only has two buttons, one to
     * go to the login scene, and one to go to the register new account scene.
     *
     * @param cashMachineStage this method to be passed the cashMachineStage in order to define the buttons for
     *                         navigating to new scenes, for example under one button's definition it says:
     *                         cashMachineStage.setScene(mainMenuScene)
     * @return the scene for the main menu
     */
    private Scene createMainScene(Stage cashMachineStage) {
        Label mainMenuLabel= new Label("Cash Machine Main Menu\nPlease select one of the options below");

        Button goToRegisterFromMainButton= new Button("Register a new bank account");
        goToRegisterFromMainButton.setOnAction(e -> {
            registerScreenDisplay.setText("Currently Used Account Numbers:\n" + cashMachine.getListOfAllAccountIds()); // Sets the registerAccountScene's TextArea to display the current account numbers
            cashMachineStage.setScene(registerAccountScene);
        });

        Button goToLoginFromMainButton = new Button("Log into existing bank account");
        goToLoginFromMainButton.setOnAction(e -> {
            loginScreenDisplay.setText("Current Account Numbers:\n" + cashMachine.getListOfAllAccountIds()); // Sets the loginAccountScene's TextArea to display the current account numbers
            cashMachineStage.setScene(loginAccountScene);
        });

        VBox mainMenuVBox = new VBox(20);
        mainMenuVBox.getChildren().addAll(mainMenuLabel, goToRegisterFromMainButton, goToLoginFromMainButton);
        return new Scene(mainMenuVBox, 480, 480);
    }

    /**
     * This method creates the scene for registerAccountScene. This scene asks the user for input to create a new
     * account. It requires the user to enter an unused account number, an account type (basic or premium), a name,
     * an email, and a starting balance.
     *
     * If the user inputs an invalid account type, doesn't fill out all of the info, or puts the wrong type of info
     * into one of the fields (e.g. a string of characters where it's asking for a starting balance), then nothing will
     * happen, but the user will still be able to try again.
     *
     * If the user inputs an account number that is already in use, then the TextArea will tell them that that account
     * number is already in use.
     *
     * There is also a button for returning to the main menu.
     *
     * @param cashMachineStage this method to be passed the cashMachineStage in order to define the buttons for
     *                         navigating to new scenes, for example under one button's definition it says:
     *                         cashMachineStage.setScene(mainMenuScene)
     * @return the scene for the registerAccountScene
     */
    private Scene createRegisterScene(Stage cashMachineStage) {
        Label registerAccountLabel= new Label("Register a new bank accountby entering an unused bank account\nnumber (an integer) below. Currently used numbers are listed towards\nthe bottom of the window");
        Label accountTypeLabel = new Label("Enter the account type below. Available types are basic or premium.\nFailure to write one of these types will prevent account creation");
        Label nameEmailBalanceLabel = new Label("Enter your name, email, and starting balance below");
        Label successExplanationLabel = new Label("If you have successfully created an account, it will say 'Success!'\nbelow. If you hit 'Create Account' and it does not say 'Success!'\nthen you have input something incorrectly above");

        TextField accountNumberInputField = new TextField();
        accountNumberInputField.setPromptText("Enter an unused account number for the account you'd like to create");

        TextField accountTypeInputField = new TextField();
        accountTypeInputField.setPromptText("Enter an account type of basic or premium");

        TextField nameInputField = new TextField();
        nameInputField.setPromptText("Enter your name");

        TextField emailInputField = new TextField();
        emailInputField.setPromptText("Enter your email");

        TextField balanceInputField = new TextField();
        balanceInputField.setPromptText("Enter your starting balance (example format: 100.00)");

        Button createAccountButton = new Button("Create Account");
        createAccountButton.setOnAction(e -> {
            int id = Integer.parseInt(accountNumberInputField.getText());
            if (!cashMachine.containsAccountWithGivenId(id)) {
                String accountType = accountTypeInputField.getText().toLowerCase();
                String name = nameInputField.getText();
                String email = emailInputField.getText();
                double startingBalance = Double.parseDouble(balanceInputField.getText());

                boolean result = cashMachine.createNewAccount(accountType, id, name, email, startingBalance);

                if (result) {
                    String text = String.format("Success! Account %d has been created.\nAccount Type: %s, Name: %s, Email:%s\nStarting Balance: %.2f\nCurrent Account Numbers:\n%s", id, accountType, name, email, startingBalance, cashMachine.getListOfAllAccountIds());
                    registerScreenDisplay.setText(text);
                }
            } else {
                registerScreenDisplay.setText("Error: account number " + id + " is already in use\nPlease enter an unused account number\nCurrently Used Account Numbers:\n" + cashMachine.getListOfAllAccountIds());
            }
        });

        Button goToMainFromRegisterButton = new Button("Go back to the main menu");
        goToMainFromRegisterButton.setOnAction(e -> cashMachineStage.setScene(mainMenuScene));

        VBox registerAccountVBox= new VBox(8);
        registerAccountVBox.getChildren().addAll(registerAccountLabel, accountNumberInputField, accountTypeLabel, accountTypeInputField, nameEmailBalanceLabel, nameInputField, emailInputField, balanceInputField, createAccountButton, successExplanationLabel, registerScreenDisplay, goToMainFromRegisterButton);
        return new Scene(registerAccountVBox,480,620);
    }

    /**
     * This method creates the scene for the loginAccountScene. This scene asks the user to input their account number.
     *
     * If the user doesn't fill out the field, or puts the wrong type of info into the field (e.g. a string of
     * characters where it's asking for the integer of the account), then nothing will happen, but the user will
     * still be able to try again.
     *
     * If the user inputs an account number that is not in use, then the TextArea will tell them that that account
     * number invalid.
     *
     * There is also a button for returning to the main menu.
     *
     * @param cashMachineStage this method to be passed the cashMachineStage in order to define the buttons for
     *                         navigating to new scenes, for example under one button's definition it says:
     *                         cashMachineStage.setScene(mainMenuScene)
     * @return the scene for the registerAccountScene
     */
    private Scene createLoginScene(Stage cashMachineStage) {
        Label loginAccountLabel = new Label("Please enter your account number (an integer) in the field below\nand then press 'Log In' to access your account");

        TextField loginInputField = new TextField();
        loginInputField.setPromptText("Enter the account number (an integer) for the account you'd like to access");

        Button logIntoAccountButton = new Button("Log In");
        logIntoAccountButton.setOnAction(e -> {
            int id = Integer.parseInt(loginInputField.getText());
            if (cashMachine.containsAccountWithGivenId(id)){
                cashMachine.login(id);
                accessScreenDisplay.setText(cashMachine.getAccountDataAsString());
                cashMachineStage.setScene(accessAccountScene);
            } else {
                loginScreenDisplay.setText("Error: " + id + " is not a valid account number\nPlease enter an existing account number\nCurrent Account Numbers:\n" + cashMachine.getListOfAllAccountIds());
            }
        });

        Button goToMainFromLoginButton= new Button("Go back to the main menu");
        goToMainFromLoginButton.setOnAction(e -> cashMachineStage.setScene(mainMenuScene));

        VBox loginAccountVBox = new VBox(20);
        loginAccountVBox.getChildren().addAll(loginAccountLabel, loginInputField, logIntoAccountButton, loginScreenDisplay, goToMainFromLoginButton);
        return new Scene(loginAccountVBox, 480, 480);
    }

    /**
     * This method creates the scene for the accessAccountScene. This scene asks the user to enter a number to be
     * withdrawn or deposited in their account.
     *
     * If the user doesn't fill out the field, tries to withdraw more than is valid, or puts the wrong type of info
     * into the field (e.g. a string of characters where it's asking for the double of the amount to deposit or
     * withdraw), then nothing will happen, but the user will still be able to try again.
     *
     * There is also a button for logging out and returning to the loginAccountScene.
     *
     * @param cashMachineStage this method to be passed the cashMachineStage in order to define the buttons for
     *                         navigating to new scenes, for example under one button's definition it says:
     *                         cashMachineStage.setScene(mainMenuScene)
     * @return the scene for the registerAccountScene
     */
    private Scene createAccessScene(Stage cashMachineStage) {
        Label accessAccountLabel = new Label("Your account\nEnter a dollar amount in the in the field below (example format: 100.00)\nPress deposit to deposit that amount or\npress withdraw to withdraw that amount");

        TextField accessInputField = new TextField();
        accessInputField.setPromptText("Enter the amount you'd like to deposit or withdraw (example format: 100.00)");

        Button btnDeposit = new Button("Deposit");
        btnDeposit.setOnAction(e -> {
            double amount = Double.parseDouble(accessInputField.getText());
            cashMachine.deposit(amount);

            accessScreenDisplay.setText(cashMachine.getAccountDataAsString());
        });

        Button btnWithdraw = new Button("Withdraw");
        btnWithdraw.setOnAction(e -> {
            double amount = Double.parseDouble(accessInputField.getText());
            cashMachine.withdraw(amount);

            accessScreenDisplay.setText(cashMachine.getAccountDataAsString());
        });

        Button btnExit = new Button("Log Out, and go back to login menu");
        btnExit.setOnAction(e -> {
            cashMachine.exit();
            cashMachineStage.setScene(loginAccountScene);
        });

        FlowPane depositWithdrawFlowPane = new FlowPane(btnDeposit, btnWithdraw);

        VBox accessAccountVBox = new VBox(20);
        accessAccountVBox.getChildren().addAll(accessAccountLabel, accessInputField, depositWithdrawFlowPane, accessScreenDisplay, btnExit);
        return new Scene(accessAccountVBox, 480, 480);
    }

    /**
     * This start method creates the four main scenes of the GUI, sets the initial scene to the mainMenuScene, and
     * reveals the stage.
     *
     * @param cashMachineStage The main stage of the GUI
     * @throws Exception
     */
    @Override
    public void start(Stage cashMachineStage) throws Exception{

        cashMachineStage.setTitle("ZipCloudBank Cash Machine");

        // Main Menu
        mainMenuScene = createMainScene(cashMachineStage);

        // Register new bank account
        registerAccountScene = createRegisterScene(cashMachineStage);

        // Log into existing bank account
        loginAccountScene = createLoginScene(cashMachineStage);

        // Access account info
        accessAccountScene = createAccessScene(cashMachineStage);

        cashMachineStage.setScene(mainMenuScene);
        cashMachineStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
