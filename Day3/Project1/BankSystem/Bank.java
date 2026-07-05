package Project1.BankSystem;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Arrays;
import java.util.Comparator;

import Project1.BankSystem.Exception.AccountNotFoundException;
import Project1.BankSystem.Exception.InsufficientFundsException;
import Project1.BankSystem.Exception.InvalidAmountException;

public class Bank {
	Account[] accounts;
	int currentAccountIndex;

	public Bank() {
		currentAccountIndex = 0;
		accounts = new Account[20];
	}

	public void loadFromFile(String filePath) {
		try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
			String accountLine = br.readLine(); // read the csv header
			while ((accountLine = br.readLine()) != null) {
				String[] values = accountLine.split(",");
				if (values.length < 3) {
					System.err.println("Invalid account format: " + accountLine);
					continue;
				}
				int accountNumber = Integer.parseInt(values[0]);
				String accountName = values[1];
				double balance = Double.parseDouble(values[2]);
				accounts[currentAccountIndex++] = new Account(accountNumber, accountName, balance);
			}
			System.out.println("Loaded " + currentAccountIndex + " Accounts");
		} catch (FileNotFoundException e) {
			System.err.println("File: " + filePath + " not found");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void listAccountsSortedByBalance() {
		Account[] sorted = Arrays.copyOf(accounts, accounts.length);
		Arrays.sort(sorted, Comparator.comparingDouble(Account::getBalance));
		System.out.println("Accounts: ");
		for (Account acc : sorted) {
			System.out.println("\t" + acc);
		}
	}

	public Account findAccount(int accountNumber) throws AccountNotFoundException {
		for (Account acc : accounts) {
			if (acc != null && acc.getAccountNumber() == accountNumber)
				return acc;
		}
		throw new AccountNotFoundException(accountNumber);
	}

	public Account[] findAccountsMatching(TransactionRule rule) {
		Account[] temp = new Account[accounts.length];
		int idx = 0;
		for (Account acc : accounts) {
			if (acc != null && rule.test(acc))
				temp[idx++] = acc;
		}
		return Arrays.copyOf(temp, idx);
	}

	public void transfer(int fromAccountNumber, int toAccountNumber, double amount)
			throws AccountNotFoundException, InsufficientFundsException, InvalidAmountException {
		Account from = findAccount(fromAccountNumber);
		Account to = findAccount(toAccountNumber);
		from.withdraw(amount);
		to.deposit(amount);
	}

}
