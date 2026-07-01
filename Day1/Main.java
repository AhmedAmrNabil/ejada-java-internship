import java.util.Scanner;

class Student {
	int id;
	String name;
	int age;
	char gender;
	double grade;

	public Student(int id, String name, int age, char gender, double grade) {
		this.id = id;
		this.name = name;
		this.age = age;
		this.gender = gender;
		this.grade = grade;
	}

	public String toString() {
		StringBuffer sb = new StringBuffer("==============================\n");
		sb.append("ID: ");
		sb.append(id);
		sb.append("\nName: ");
		sb.append(name);
		sb.append("\nAge: ");
		sb.append(age);
		sb.append("\nGender: ");
		sb.append(gender == 'F' ? "Female" : "Male");
		sb.append("\nGrade: ");
		sb.append(grade);
		sb.append("\n==============================");
		return sb.toString();
	}
}

public class Main {

	static String mainMenu = """
			Main Menu:
			1. List students
			2. Add student
			3. Delete student
			4. Search student by id
			5. Get average grades
			6. Exit
			Enter selection:""";

	public static void main(String[] args) {
		Student[] students = new Student[10];
		students[0] = new Student(0, "Ahmed", 22, 'M', 3.8);
		students[1] = new Student(1, "Farag", 22, 'M', 3.7);
		students[2] = new Student(2, "Wafa", 22, 'F', 3.9);
		students[3] = new Student(4, "Loay", 21, 'M', 2.1);
		int freeIdx = 4;

		Scanner input = new Scanner(System.in);
		boolean running = true;
		while (running) {
			System.out.print(mainMenu + ' ');
			int action = input.nextInt();
			switch (action) {
				case 1 -> {
					for (Student s : students) {
						if (s != null)
							System.out.println(s.toString());
					}
				}
				case 2 -> {
					if (freeIdx >= students.length) {
						System.out.println("Cannot add more than " + students.length + "students");
						break;
					}
					System.out.println("Adding a new student");
					int id = 0;
					boolean duplicated = true;
					while (duplicated) {
						duplicated = false;
						System.out.print("Enter ID: ");
						id = input.nextInt();
						for (Student s : students) {
							if (s != null && s.id == id) {
								duplicated = true;
								break;
							}
						}
						if (!duplicated)
							break;
						System.out.println("ID already exists, try again");
					}
					input.nextLine();

					System.out.print("Enter name: ");
					String name = input.nextLine();
					char g = 'g'; // gender;
					while (g != 'M' && g != 'F') {
						System.out.print("Enter Gender (M/F): ");
						g = input.next().charAt(0);
						g = Character.toUpperCase(g);
						if (g == 'M' || g == 'F') {
							break;
						}
						System.out.println("Gender can only be Male or Female, try again");
					}
					input.nextLine();

					int age = -1;
					while (age <= 0) {
						System.out.print("Enter Age: ");
						age = input.nextInt();
						if (age > 0)
							break;
						System.out.println("Age cannot be less than 0, try again");
					}
					input.nextLine();

					double grade = -1;
					while (grade < 0 || grade > 4) {
						System.out.print("Enter Grade: ");
						grade = input.nextDouble();
						if (grade >= 0 && grade <= 4)
							break;
						System.out.println("Grade can only be between 0 and 4, try again");
					}
					input.nextLine();

					Student s = new Student(id, name, age, g, grade);
					students[freeIdx++] = s;
				}
				case 3 -> {
					System.out.println("Enter Student ID to delete: ");
					int id = input.nextInt();
					int lastIdx = freeIdx - 1;
					int idx = -1;
					for (int i = 0; i < freeIdx; i++) {
						if (students[i].id == id) {
							idx = i;
							break;
						}
					}
					if (idx == -1) {
						System.out.println("Student with ID: " + id + " not found");
						break;
					}
					students[idx] = students[lastIdx];
					students[lastIdx] = null;
					freeIdx--;
					System.out.println("Deleted Successfully");
				}
				case 4 -> {
					System.out.print("Enter ID: ");
					int id = input.nextInt();
					boolean found = false;
					for (Student s : students) {
						if (s.id == id) {
							found = true;
							System.out.println("Student found:");
							System.out.println(s.toString());
							break;
						}
					}
					if (!found) {
						System.out.println("Student with ID: " + id + " not found");
					}
				}
				case 5 -> {
					double average = 0;
					for (Student s : students) {
						average += s.grade;
					}
					average /= freeIdx;
					System.out.println("Average grade: " + average);
				}
				case 6 -> running = false;
			}
		}

		input.close();
	}
}