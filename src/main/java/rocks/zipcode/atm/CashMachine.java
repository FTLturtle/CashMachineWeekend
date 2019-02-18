package rocks.zipcode.atm;

import rocks.zipcode.atm.bank.AccountData;
import rocks.zipcode.atm.bank.Bank;

import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * @author ZipCodeWilmington
 */
public class CashMachine {

    private final Bank bank;
    private AccountData accountData = null;

    public CashMachine(Bank bank) {
        this.bank = bank;
    }

    private Consumer<AccountData> update = data -> {
        accountData = data;
    };

    public void login(int id) {
        tryCall(
                () -> bank.getAccountById(id),
                update
        );
    }

    public void deposit(double amount) {
        if (accountData != null) {
            tryCall(
                    () -> bank.deposit(accountData, amount),
                    update
            );
        }
    }

    public void withdraw(double amount) {
        if (accountData != null) {
            tryCall(
                    () -> bank.withdraw(accountData, amount),
                    update
            );
        }
    }

    public void exit() {
        if (accountData != null) {
            accountData = null;
        }
    }

    /**
     * Checks if the bank has an account that matches the id given
     *
     * @param id this is the account number to check if the bank has it
     * @return true if bank contains that account, false otherwise
     */
    public boolean containsAccountWithGivenId(int id) {
        return bank.containsAccountWithGivenId(id);
    }

    /**
     * This method creates a new bank account in the bank. returns true if the account was successfully created, and
     * false if the account was not created.
     *
     * @param accountType
     * @param id
     * @param name
     * @param email
     * @param balance
     * @return true if account was created, false otherwise
     */
    public boolean createNewAccount(String accountType, int id, String name, String email, double balance) {
        return bank.createNewAccount(accountType, id, name, email, balance);
    }

    /**
     * This method returns account data as a string. If the account that's being checked doesn't exist, it returns
     * and empty string.
     *
     * @return account data as a string, or an empty string if account data doesn't exist
     */
    public String getAccountDataAsString() {
        return accountData != null ? accountData.toString() : "";
    }

    /**
     * This method returns a String list of all account ids currently in use at the bank
     *
     * @return a String list of all account ids currently in use at the bank
     */
    public String getListOfAllAccountIds() {
        return bank.listOfAllAccountIds();
    }

    private <T> void tryCall(Supplier<ActionResult<T> > action, Consumer<T> postAction) {
        try {
            ActionResult<T> result = action.get();
            if (result.isSuccess()) {
                T data = result.getData();
                postAction.accept(data);
            } else {
                String errorMessage = result.getErrorMessage();
                throw new RuntimeException(errorMessage);
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
