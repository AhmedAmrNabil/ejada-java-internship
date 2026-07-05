package Project1.BankSystem;

import Project1.BankSystem.Exception.InsufficientFundsException;
import Project1.BankSystem.Exception.InvalidAmountException;

public class Account {
	private int accountNumber;
	private String ownerName;
	double balance;

	public Account(int accountNumber, String accountName, double balance) {
		this.ownerName = accountName;
		this.accountNumber = accountNumber;
		this.balance = balance;
	}

	public void deposit(double amount) throws InvalidAmountException {
		if (amount < 0)
			throw new InvalidAmountException(amount);
		this.balance += amount;
	}

	public void withdraw(double amount) throws InsufficientFundsException {
		if (amount > balance)
			throw new InsufficientFundsException(accountNumber, amount, balance);
		this.balance -= amount;
	}

	public double getBalance() {
		return balance;
	}

	public int getAccountNumber() {
		return accountNumber;
	}

	public String getOwnerName() {
		return ownerName;
	}

	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("AccNum: ");
		builder.append(accountNumber);
		builder.append(",\tOwner: ");
		builder.append(ownerName);
		builder.append(",\tbalance: ");
		builder.append(balance);
		return builder.toString();
	}
}
