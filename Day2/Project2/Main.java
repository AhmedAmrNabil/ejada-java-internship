package Project2;

public class Main {
	public static void main(String[] args) {
		Employee[] staff = {
				new Manager("Alice", "M001", 8000, 15),
				new Developer("Bob", "D001", 6000, 10, 25.0),
				new SeniorDeveloper("Cara", "SD001", 7500, 5, 30.0, 12000)
		};

		for (Employee e : staff) {
			System.out.println(e);
		}
	}
}
