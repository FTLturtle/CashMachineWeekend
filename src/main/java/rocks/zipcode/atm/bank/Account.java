package rocks.zipcode.atm.bank;

/**
 * @author ZipCodeWilmington
 */
public abstract class Account {
    final double ZERO_BALANCE_THRESHOLD = -.0001;
    // use this amount rather than zero when checking if amount can be withdrawn

    private AccountData accountData; // Object where account data is kept

    public Account(AccountData accountData) {
        this.accountData = accountData;
    }

    /**
     * getter method to get accountdata
     *
     * @return accountdata
     */
    public AccountData getAccountData() {
        return accountData;
    }

    /**
     * This method increases the balance by the deposited amount.
     *
     * @param amount amount to be deposited. gets rounded to two decimal places.
     */
    public void deposit(double amount) {
        amount = (double)Math.round(amount * 100d) / 100d; // rounding to two decimal places
        updateBalance(getBalance() + amount);
    }

    /**
     * This method checks if a withdrawal is allowable, and if it is withdraws the requested amount.
     *
     * @param amount amount to be withdrawn
     * @return true if withdrawal has occurred, false otherwise
     */
    public boolean withdraw(double amount) {
        amount = (double)Math.round(amount * 100d) / 100d; // rounding to two decimal places
        if (canWithdraw(amount)) {
            updateBalance(getBalance() - amount);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Checks if a withdrawal is allowable. Uses a threshold of -.0001 to do the double comparisons.
     *
     * @param amount amount that is being attempted to be withdrawn
     * @return true if withdrawal is allowable, false otherwise.
     */
    protected boolean canWithdraw(double amount) {
        return (getBalance() - amount >= ZERO_BALANCE_THRESHOLD);
    }

    /**
     * getter method to get current balance
     *
     * @return account balance
     */
    public double getBalance() {
        return accountData.getBalance();
    }

    /**
     * changes balance to provided newBalance.
     * @param newBalance amount to which balance is to be set
     */
    private void updateBalance(double newBalance) {
        double newBalanceRoundedToTwoDecimalPlaces = (double)Math.round(newBalance * 100d) / 100d; // rounding to two decimal places
        accountData = new AccountData(accountData.getId(), accountData.getName(), accountData.getEmail(), newBalanceRoundedToTwoDecimalPlaces);
    }
}

