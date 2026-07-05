package Project1.BankSystem.Exception;

public class InsufficientFundsException extends BankException {
	public InsufficientFundsException(int accountNumber, double requested, double available) {
		super("Invalid balance: Account " + accountNumber + " requested " + requested + " but only " + available + " available");
	}
}