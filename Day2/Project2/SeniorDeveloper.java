package Project2;

public class SeniorDeveloper extends Developer {
	private int stockValue;

	public SeniorDeveloper(String name, String id, double salary, int overtimeHours, double hourlyOvertimeRate,
			int stockValue) {
		super(name, id, salary, overtimeHours, hourlyOvertimeRate);
		this.stockValue = stockValue;
	}

	public double calculateSalary() {
		return super.calculateSalary() + this.stockValue;
	}
};
