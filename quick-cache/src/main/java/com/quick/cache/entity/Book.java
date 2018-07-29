package com.quick.cache.entity;

public class Book {

    private String isbn;
    private String title;
	private String auto;

	public Book(String isbn, String title, String auto) {
		this.isbn = isbn;
		this.title = title;
		this.auto = auto;
	}

	public String getAuto() {
		return auto;
	}

	public void setAuto(String auto) {
		this.auto = auto;
	}

	public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

	@Override
	public String toString() {
		return "Book{" + "isbn='" + isbn + '\'' + ", title='" + title + '\'' + ", auto='" + auto + '\'' + '}';
	}
}