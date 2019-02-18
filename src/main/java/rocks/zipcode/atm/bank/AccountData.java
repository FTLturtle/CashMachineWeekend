package rocks.zipcode.atm.bank;

/**
 * @author ZipCodeWilmington
 */
public final class AccountData {

    private final int id;
    private final String name;
    private final String email;

    private final double balance;

    AccountData(int id, String name, String email, double balance) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.balance = balance;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public double getBalance() {
        return balance;
    }

    @Override
    public String toString() {
        String accountData;
        if (balance < 0) {
            accountData = String.format("Account ID: %d\nName: %s\nEmail: %s\nBalance: %.2f\nWARNING: ACCOUNT IS OVERDRAWN", id, name, email, balance);
        } else {
            accountData = String.format("Account ID: %d\nName: %s\nEmail: %s\nBalance: %.2f", id, name, email, balance);
        }

        return accountData;
    }
}
