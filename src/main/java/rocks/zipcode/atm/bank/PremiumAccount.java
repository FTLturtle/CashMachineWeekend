package rocks.zipcode.atm.bank;

/**
 * @author ZipCodeWilmington
 */
public class PremiumAccount extends Account {

    private static final double OVERDRAFT_LIMIT = 100.0;

    public PremiumAccount(AccountData accountData) {
        super(accountData);
    }

    @Override
    protected boolean canWithdraw(double amount) {
        return (Math.abs(getBalance() + OVERDRAFT_LIMIT - amount) < DOUBLE_COMPARISON_THRESHOLD);
    }
}
