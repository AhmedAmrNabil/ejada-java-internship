package Project1.BankSystem;

@FunctionalInterface
public interface TransactionRule {
	boolean test(Account account);
}
