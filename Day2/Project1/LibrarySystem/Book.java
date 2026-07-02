package Project1.LibrarySystem;

public class Book {
	private String title;
	private String author;
	private String isbn;
	private boolean isAvailabe;

	private static int totalBooks;

	static {
		totalBooks = 0;
	}

	public Book(String title, String author, String isbn) {
		this(title, author, isbn, true);
	}

	public Book(String title, String author, String isbn, boolean isAvailabe) {
		this.title = title;
		this.author = author;
		this.isbn = isbn;
		this.isAvailabe = isAvailabe;
		++totalBooks;
	}

	public String toString() {
		String output = title + " by " + author + (isAvailabe ? ", Available to borrow" : ", Borrowed") + ", ISBN: " + isbn;
		return output;
	}

	public void setIsAvailable(boolean isAvailabe) {
		this.isAvailabe = isAvailabe;
	}

	public boolean getIsAvailable() {
		return isAvailabe;
	}

	public static int getTotalBooks() {
		return totalBooks;
	}

	public String getIsbn() {
		return isbn;
	}

}
