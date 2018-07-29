package com.quick.cache.repo;

import com.quick.cache.entity.Book;

import java.util.List;

public interface BookRepository {

    Book getByIsbn(String isbn);

	Book update(Book book);

	void clear();

}