package Project1;

import java.util.Scanner;

import Project1.BankSystem.Bank;
import Project1.BankSystem.TransactionRule;
import Project1.BankSystem.Exception.AccountNotFoundException;
import Project1.BankSystem.Exception.InsufficientFundsException;
import Project1.BankSystem.Exception.InvalidAmountException;
import Project1.BankSystem.Account;

public class Main {

	static String mainMenu = """
			Main Menu:
			1. List Accounts
			2. Deposit
			3. Withdraw
			4. Transfer
			5. List Accounts with balance > 5000
			6. Exit
			Enter selection:""";

	public static void main(String[] args) {
		Bank bank = new Bank();
		if (args.length > 0)
			bank.loadFromFile(args[0]);
		else {
			System.err.println("please provide file path for the accounts");
			return;
		}

		try (Scanner input = new Scanner(System.in)) {
			boolean running = true;
			while (running) {
				System.out.print(mainMenu + ' ');
				int action = input.nextInt();
				switch (action) {
					case 1 -> {
						bank.listAccountsSortedByBalance();
					}
					case 2 -> {
						System.out.print("Enter Account Number: ");
						int accountNumber = input.nextInt();
						System.out.print("Enter Balance to deposit: ");
						double balance = input.nextDouble();
						try {
							Account acc = bank.findAccount(accountNumber);
							acc.deposit(balance);
						} catch (AccountNotFoundException e) {
							System.err.println("ERROR: " + e.getMessage());
						} catch (InvalidAmountException e) {
							System.err.println("ERROR: " + e.getMessage());
						}
					}

					case 3 -> {
						System.out.print("Enter Account Number: ");
						int accountNumber = input.nextInt();
						System.out.print("Enter Balance to withdraw: ");
						double balance = input.nextDouble();
						try {
							Account acc = bank.findAccount(accountNumber);
							acc.withdraw(balance);
						} catch (AccountNotFoundException e) {
							System.err.println("ERROR: " + e.getMessage());
						} catch (InsufficientFundsException e) {
							System.err.println("ERROR: " + e.getMessage());
						}
					}

					case 4 -> {
						System.out.print("Enter From Account Number: ");
						int from = input.nextInt();
						System.out.print("Enter To Account Number: ");
						int to = input.nextInt();
						System.out.print("Enter Amount: ");
						double amount = input.nextDouble();
						try {
							bank.transfer(from, to, amount);
							System.out.println("Transfer successful");
						} catch (AccountNotFoundException | InsufficientFundsException | InvalidAmountException e) {
							System.err.println("ERROR: " + e.getMessage());
						}
					}

					case 5 -> {
						TransactionRule rule = acc -> acc.getBalance() > 5000;
						Account accs[] = bank.findAccountsMatching(rule);
						for (Account acc : accs) {
							System.out.println("\t" + acc);
						}
					}

					case 6 -> running = false;

					default -> {

					}
				}
			}
		}

	}
}