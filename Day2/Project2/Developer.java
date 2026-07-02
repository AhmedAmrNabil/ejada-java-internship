package Project2;

public class Developer extends Employee {
	private int overtimeHours;
	private double hourlyOvertimeRate;

	public Developer(String name, String id, double salary, int overtimeHours, double hourlyOvertimeRate) {
		super(name, id, salary);
		this.overtimeHours = overtimeHours;
		this.hourlyOvertimeRate = hourlyOvertimeRate;
	}

	public double calculateSalary() {
		return this.baseSalary + (this.overtimeHours * this.hourlyOvertimeRate);
	}
};
