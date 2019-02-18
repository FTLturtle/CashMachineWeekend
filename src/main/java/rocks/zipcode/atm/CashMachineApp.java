package rocks.zipcode.atm;

import javafx.scene.control.Label;
import javafx.stage.Stage;
import rocks.zipcode.atm.bank.Bank;
import javafx.application.Application;
import javafx.scene.Parent;
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

    private CashMachine cashMachine = new CashMachine(new Bank());






    private Scene mainMenuScene, registerAccountScene, loginAccountScene, accessAccountScene;

    private TextArea accessAccountInfo = new TextArea();
    private TextArea loginAccountInfo = new TextArea();
    private TextArea registerAccountInfo = new TextArea();

    private Scene createMainScene(Stage cashMachineStage) {
        Label mainMenuLabel= new Label("Cash Machine Main Menu\nPlease select one of the options below");

        Button goToRegisterFromMainButton= new Button("Register a new bank account");
        goToRegisterFromMainButton.setOnAction(e -> {
            registerAccountInfo.setText("Current Account Numbers:\n" + cashMachine.getListOfAllAccountIds());
            cashMachineStage.setScene(registerAccountScene);
        });

        Button goToLoginFromMainButton = new Button("Log into existing bank account");
        goToLoginFromMainButton.setOnAction(e -> {
            loginAccountInfo.setText("Current Account Numbers:\n" + cashMachine.getListOfAllAccountIds());
            cashMachineStage.setScene(loginAccountScene);
        });

        VBox mainMenuVBox = new VBox(20);
        mainMenuVBox.getChildren().addAll(mainMenuLabel, goToRegisterFromMainButton, goToLoginFromMainButton);
        return new Scene(mainMenuVBox, 480, 540);
    }

    private Scene createRegisterScene(Stage cashMachineStage) {
        Label registerAccountLabel= new Label("Register a new bank account\nEnter an unused bank account number\nEnter name, email, and starting balance");

        Button goToMainFromRegisterButton= new Button("Go back to the main menu");
        goToMainFromRegisterButton.setOnAction(e -> cashMachineStage.setScene(mainMenuScene));

        VBox registerAccountVBox= new VBox(20);
        registerAccountVBox.getChildren().addAll(registerAccountLabel, goToMainFromRegisterButton);
        return new Scene(registerAccountVBox,480,540);
    }

    private Scene createLoginScene(Stage cashMachineStage) {
        Label loginAccountLabel = new Label("Please enter your account number in the field below\nand then press 'Log In' to access your account");

        TextField loginInputField = new TextField();
        loginInputField.setPromptText("Enter the account number for the account you'd like to access");

        Button logIntoAccountButton = new Button("Log In");
        logIntoAccountButton.setOnAction(e -> {
            int id = Integer.parseInt(loginInputField.getText());
            cashMachine.login(id);
            if (!cashMachine.getAccountDataAsString().equals("")){
                accessAccountInfo.setText(cashMachine.getAccountDataAsString());
                cashMachineStage.setScene(accessAccountScene);
            } else {
                loginAccountInfo.setText("Error: " + id + " is not a valid account number\nPlease enter an existing account number\nCurrent Account Numbers:\n" + cashMachine.getListOfAllAccountIds());
            }
        });

        Button goToMainFromLoginButton= new Button("Go back to the main menu");
        goToMainFromLoginButton.setOnAction(e -> cashMachineStage.setScene(mainMenuScene));

        VBox loginAccountVBox = new VBox(20);
        loginAccountVBox.getChildren().addAll(loginAccountLabel, loginInputField, logIntoAccountButton, loginAccountInfo, goToMainFromLoginButton);
        return new Scene(loginAccountVBox, 480, 540);
    }

    private Scene createAccessScene(Stage cashMachineStage) {
        Label accessAccountLabel = new Label("Your account\nEnter a dollar amount\nPress deposit to deposit that amount or\npress withdraw to withdraw that amount");

        TextField accessInputField = new TextField();
        accessInputField.setPromptText("Enter the amount you'd like to deposit or withdraw");

        Button btnDeposit = new Button("Deposit");
        btnDeposit.setOnAction(e -> {
            double amount = Double.parseDouble(accessInputField.getText());
            cashMachine.deposit(amount);

            accessAccountInfo.setText(cashMachine.getAccountDataAsString());
        });

        Button btnWithdraw = new Button("Withdraw");
        btnWithdraw.setOnAction(e -> {
            double amount = Double.parseDouble(accessInputField.getText());
            cashMachine.withdraw(amount);

            accessAccountInfo.setText(cashMachine.getAccountDataAsString());
        });

        Button btnExit = new Button("Log Out, and go back to login menu");
        btnExit.setOnAction(e -> {
            cashMachine.exit();
            cashMachineStage.setScene(loginAccountScene);
        });



        VBox accessAccountVBox = new VBox(20);
        accessAccountVBox.getChildren().addAll(accessAccountLabel, accessAccountInfo, accessInputField, btnDeposit, btnWithdraw, btnExit);
        return new Scene(accessAccountVBox, 480, 540);
    }







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
