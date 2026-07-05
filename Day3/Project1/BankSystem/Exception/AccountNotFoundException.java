package Project1.BankSystem.Exception;

public class AccountNotFoundException extends BankException {
	public AccountNotFoundException(int accountNumber) {
		super("Account " + accountNumber + " not found");
	}
}
