package rocks.zipcode.atm.bank;

/**
 * @author ZipCodeWilmington
 */
public abstract class Account {
    final double DOUBLE_COMPARISON_THRESHOLD = .001;
    // threshold for comparing doubles. since all of the doubles use only two decimal places,
    // the threshold has been set to + or - .001

    private AccountData accountData;

    public Account(AccountData accountData) {
        this.accountData = accountData;
    }

    public AccountData getAccountData() {
        return accountData;
    }

    public void deposit(double amount) {
        amount = (double)Math.round(amount * 100d) / 100d; // rounding to two decimal places
        updateBalance(getBalance() + amount);
    }

    public boolean withdraw(double amount) {
        amount = (double)Math.round(amount * 100d) / 100d; // rounding to two decimal places
        if (canWithdraw(amount)) {
            updateBalance(getBalance() - amount);
            return true;
        } else {
            return false;
        }
    }

    protected boolean canWithdraw(double amount) {
        return (Math.abs(getBalance() - amount) < DOUBLE_COMPARISON_THRESHOLD);
    }

    public double getBalance() {
        return accountData.getBalance();
    }

    private void updateBalance(double newBalance) {
        double newBalanceRoundedToTwoDecimalPlaces = (double)Math.round(newBalance * 100d) / 100d; // rounding to two decimal places
        accountData = new AccountData(accountData.getId(), accountData.getName(), accountData.getEmail(), newBalanceRoundedToTwoDecimalPlaces);
    }
}

