package com.bookapp.main;

import java.util.Scanner;
import com.bookapp.bean.*;
import com.bookapp.dao.BookImpl;
import com.bookapp.dao.BookInter;
import com.bookapp.exception.AuthorNotFoundException;
import com.bookapp.exception.BookNotFoundException;
import com.bookapp.exception.CategoryNotFoundException;

public class Client {

	public static void main(String[] args) {
		BookInter bookRef = new BookImpl();

		Scanner s = new Scanner(System.in);
		System.out.println(
				" 1.Add Books\n 2.Delete Book\n 3.Book By Id\n 4.Update Book\n 5.Get All Book\n 6.Book by Author\n 7.Book by Category");
		System.out.println("Enter your choice....");
		int n = s.nextInt();

		String author = null;
		String category = null, title = null;
		int id = 0;
		int price = 0;

		switch (n) {
		case 1:
			System.out.println("Enter the author name:");
			author = s.next();
			System.out.println("Enter the book id:");
			id = s.nextInt();
			System.out.println("Enter the category:");
			category = s.next();
			System.out.println("Enter the title:");
			title = s.next();
			System.out.println("Enter the price:");
			price = s.nextInt();
			Book book1 = new Book(title, author, category, id, price);
			bookRef.addBook(book1);
			break;
		case 2:
			System.out.println("Enter the book id to delete:");
			id = s.nextInt();
			try {
				System.out.println(bookRef.deleteBook(id));
			} catch (BookNotFoundException e) {
				System.out.println(e.getMessage());
			}
			break;
		case 3:
			System.out.println("Enter the book id:");
			id = s.nextInt();
			try {
				System.out.println(bookRef.getBookById(id));
			} catch (BookNotFoundException e) {
				System.out.println(e.getMessage());
			}
			break;
		case 4:
			System.out.println("Enter the book id:");
			id = s.nextInt();
			System.out.println("Enter the price:");
			price = s.nextInt();
			bookRef.updateBook(id, price);
			System.out.println("Updated ..");
			break;
		case 5:
			System.out.println(bookRef.getAllBooks());
			break;
		case 6:
			System.out.println("Enter the author name:");
			author = s.next();
			try {
				System.out.println(bookRef.getBookbyAuthor(author));
			} catch (AuthorNotFoundException e1) {
				// TODO Auto-generated catch block
				System.out.println(e1.getMessage());
			}
			break;
		case 7:
			System.out.println("Enter the category:");
			category = s.next();
			try {
				System.out.println(bookRef.getBookbycategory(category));
			} catch (CategoryNotFoundException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
				System.out.print(e.getMessage());
			}
			break;
		default:
			System.out.println("Read Books!!!");
		}
	}
}
