package Project1.BankSystem.Exception;

public class InvalidAmountException extends BankException {
	public InvalidAmountException(double amount) {
		super("Invalid balance: " + amount);
	}
}
