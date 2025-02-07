import org.jetbrains.annotations.NotNull;

import java.io.Serial;
import java.io.Serializable;


public class User implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private final String name;
    private final int id;
    private int balance;
    private int overdraft;
    private @NotNull IBAN iban;

    private final char[] bank_code  = {1, 2, 0, 3, 0, 0, 0, 0};         // change as please
    private final char[] account_no = {9, 4, 9, 2, 2, 9, 0, 0, 0, 3};   // aswell

    /**
     * initial_balance and overdraft are in cents. So 100 initial balance is 1 currency.
     * If initial balance is 10000 (100.00) then the balance is $100.
     * @param name
     * @param initial_balance
     * @param overdraft
     */
    public User(String name, int id, int initial_balance, int overdraft) {
        this.name = name;
        this.id = id;
        this.balance = initial_balance;
        this.overdraft = -overdraft;
        this.iban = new IBAN(CountryCode.DE, bank_code, account_no);
    }


    /**
     * Returns wether or not the given user is a valid user.
     * <p>
     * Used for checking the user for validness
     *
     * @param user
     * @return boolish value
     */
    public boolean check_user(User user) {
        return (user != null && user.id > 0 && user.name != null);
    }

    // transaction checkers
    public boolean check_and_deposit(int amount) {
        if (!check_user(this) || amount <= 0) {
            return false;
        }

        if (this.deposit(amount)) {
            return true;
        }

        return false;
    }

    public boolean check_and_withdraw(int amount) {
        if (!check_user(this) || amount <= 0) {
            return false;
        }

        if (this.withdraw(amount)) {
            return true;
        }

        return false;
    }

    public boolean check_and_transfer(User receiver, int amount) {
        if (!check_user(this) || !check_user(receiver) || amount <= 0) {
            return false;
        }

        if (this.transfer(receiver, amount)) {
            return true;
        }

        return false;
    }

    // transaction methods
    private boolean deposit(int amount) {
        this.balance += amount;
        return true;
    }

    private boolean withdraw(int amount) {
        if (this.balance - amount < this.overdraft) {
            return false;       // insufficient funds
        }

        this.balance -= amount;
        return true;
    }

    private boolean transfer(User receiver, int amount) {
        if (this.check_and_withdraw(amount)) {
            if (receiver.check_and_deposit(amount)) {
                return true;
            }
        }
        // if deposit fails
        this.check_and_deposit(amount);
        return false;
    }

    public @NotNull IBAN getIban() {
        return this.iban;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.name);
        sb.append(", ");
        sb.append(this.id);
        sb.append(", ");
        sb.append(this.balance);
        sb.append(", ");
        sb.append(-this.overdraft);
        sb.append(", ");
        sb.append(this.iban.toString());
        return sb.toString();
    }

    public String toString(char userDelimeter) {
        StringBuilder sb = new StringBuilder();
        sb.append(this.name);
        sb.append(userDelimeter);
        sb.append(this.id);
        sb.append(userDelimeter);
        sb.append(this.balance);
        sb.append(userDelimeter);
        sb.append(-this.overdraft);
        sb.append(userDelimeter);
        sb.append(this.iban.toString());
        return sb.toString();
    }

    public String toString(char userDelimeter, char ibanDelimeter) {
        StringBuilder sb = new StringBuilder();
        sb.append(this.name);
        sb.append(userDelimeter);
        sb.append(this.id);
        sb.append(userDelimeter);
        sb.append(this.balance);
        sb.append(userDelimeter);
        sb.append(-this.overdraft);
        sb.append(userDelimeter);
        sb.append(this.iban.toString(ibanDelimeter));
        return sb.toString();
    }
}
