package rocks.zipcode.atm.bank;

/**
 * @author ZipCodeWilmington
 */
public class PremiumAccount extends Account {

    private static final double OVERDRAFT_LIMIT = 100.0;

    public PremiumAccount(AccountData accountData) {
        super(accountData);
    }

    /**
     * Checks if withdrawal amount is allowable. Premium accounts can be overdrawn up to the OVERDRAFT_LIMIT.
     *
     * @param amount amount that is being attempted to be withdrawn
     * @return true if withdrawal is allowable, false otherwise.
     */
    @Override
    protected boolean canWithdraw(double amount) {
        return (getBalance() + OVERDRAFT_LIMIT - amount >= ZERO_BALANCE_THRESHOLD);
    }
}
