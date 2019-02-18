package rocks.zipcode.atm.bank;

import rocks.zipcode.atm.ActionResult;

import java.util.HashMap;
import java.util.Map;

/**
 * @author ZipCodeWilmington
 */
public class Bank {

    private Map<Integer, Account> accounts = new HashMap<>();

    public Bank() {
        accounts.put(1001, new BasicAccount(new AccountData(
                1001, "Example 1", "example1@gmail.com", 500.0
        )));

        accounts.put(1002, new PremiumAccount(new AccountData(
                1002, "Example 2", "example2@gmail.com", 200.0
        )));

        accounts.put(1003, new BasicAccount(new AccountData(
                1003, "Example 3", "example3@gmail.com", 0.0
        )));

        accounts.put(1004, new PremiumAccount(new AccountData(
                1004, "Example 4", "example4@gmail.com", 0.0
        )));

    }
    
    public String listOfAllAccountIds(){
        StringBuilder listOfAllIds = new StringBuilder();

        for (int key : accounts.keySet()) {
            listOfAllIds.append(key).append('\n');
        }

        return listOfAllIds.toString();
    }

    public ActionResult<AccountData> getAccountById(int id) {
        Account account = accounts.get(id);

        if (account != null) {
            return ActionResult.success(account.getAccountData());
        } else {
            return ActionResult.fail("No account with id: " + id + "\nTry account 1000 or 2000");
        }
    }

    public ActionResult<AccountData> deposit(AccountData accountData, double amount) {
        amount = (double)Math.round(amount * 100d) / 100d;
        Account account = accounts.get(accountData.getId());
        account.deposit(amount);

        return ActionResult.success(account.getAccountData());
    }

    public ActionResult<AccountData> withdraw(AccountData accountData, double amount) {
        amount = (double)Math.round(amount * 100d) / 100d;
        Account account = accounts.get(accountData.getId());
        boolean ok = account.withdraw(amount);

        if (ok) {
            return ActionResult.success(account.getAccountData());
        } else {
            return ActionResult.fail("Withdraw failed: " + amount + ". Account has: " + account.getBalance());
        }
    }
}
