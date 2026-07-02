package Project1;

import Project1.LibrarySystem.Book;
import Project1.LibrarySystem.Library;

public class Main {
	public static void main(String[] args) {

		Book b1 = new Book("Java", "Navin", "123");
		System.out.println("Book1: " + b1 + " total books: " + Book.getTotalBooks());
		System.out.println("Anonymous book: " + new Book("Linux kernel", "Ahmed", "1234", false));

		Library library1 = new Library();
		library1.addBook(new Book("Effective Java", "Joshua Bloch", "978-0134685991"));
		library1.addBook(new Book("Clean Code", "Robert Martin", "978-0132350884"));
		library1.addBook(new Book("The Hobbit", "J.R.R. Tolkien", "978-0547928227"));
		library1.addBook(new Book("1984", "George Orwell", "978-0451524935"));

		// two books that start out already borrowed
		library1.addBook(new Book("Design Patterns", "Gang of Four", "978-0201633610", false));
		library1.addBook(new Book("The Pragmatic Programmer", "Andrew Hunt", "978-0135957059", false));

		System.out.println(library1);

		// try borrowing an available book -> should succeed
		library1.borrowBook("978-0134685991"); // Effective Java

		// try borrowing the same book again -> should fail (already borrowed now)
		library1.borrowBook("978-0134685991");

		// try borrowing a book that was already unavailable from the start -> should
		// fail
		library1.borrowBook("978-0201633610"); // Design Patterns

		// try borrowing a different, still-available book -> should succeed
		library1.borrowBook("978-0547928227"); // The Hobbit

		// try returning a book that's currently borrowed -> should succeed
		library1.returnBook("978-0134685991"); // Effective Java

		// try returning a book that's already available -> should fail (or no-op, your
		// call)
		library1.returnBook("978-0451524935"); // 1984, was never borrowed

		// try borrowing/returning an ISBN that doesn't exist in the library -> should
		// fail gracefully
		library1.borrowBook("000-0000000000");
		library1.returnBook("000-0000000000");

		System.out.println(library1);

	}
}
