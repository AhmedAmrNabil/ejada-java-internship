package Project1.LibrarySystem;

public class Library {
	Book[] books;
	int freeIdx = 0;

	public Library() {
		this(new Book[10]);
	}

	public Library(Book[] books) {
		this.books = books;
		for (Book b : this.books) {
			if (b == null)
				break;
			freeIdx++;
		}
	}

	public void addBook(Book book) {
		if (freeIdx >= books.length) {
			System.out.println("ERROR: Library is full, books count: " + books.length);
			return;
		}
		books[freeIdx++] = book;
		System.out.println("Library: Added Book: " + book);
	}

	private Book bookByIsbn(String isbn) {
		Book targetBook = null;
		for (Book b : books) {
			if (b == null || b.getIsbn() == isbn) {
				targetBook = b;
				break;
			}
		}
		return targetBook;
	}

	public void borrowBook(String isbn) {
		Book targetBook = bookByIsbn(isbn);
		if (targetBook == null) {
			System.out.println("ERROR: book with isbn: " + isbn + " Not found");
			return;
		}

		if (targetBook.getIsAvailable() == false) {
			System.out.println("ERROR: book: " + targetBook + " is already borrowed!");
			return;
		}
		targetBook.setIsAvailable(false);
		System.out.println("Library: Borrowed book: " + targetBook);
	}

	public void returnBook(String isbn) {
		Book targetBook = bookByIsbn(isbn);
		if (targetBook == null) {
			System.out.println("ERROR: book with isbn: " + isbn + " Not found");
			return;
		}

		if (targetBook.getIsAvailable() == true) {
			System.out.println("ERROR: book: " + targetBook + " was never borrowed");
			return;
		}

		targetBook.setIsAvailable(true);
		System.out.println("Library: Returned book: " + targetBook);
	}

	public String toString() {
		StringBuffer sb = new StringBuffer("-------- Library --------\n");
		for (Book b : books) {
			if (b == null)
				break;
			sb.append(b + "\n");
		}
		sb.append("-------------------------");
		return sb.toString();
	};

}
