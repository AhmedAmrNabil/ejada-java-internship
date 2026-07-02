package Project2;

public class Manager extends Employee {
	private double bonusPercent;

	public Manager(String name, String id, double salary, double bonusPercent) {
		super(name, id, salary);
		this.bonusPercent = bonusPercent;
	}

	public double calculateSalary() {
		return this.baseSalary * (1 + bonusPercent / 100.0);
	}
};