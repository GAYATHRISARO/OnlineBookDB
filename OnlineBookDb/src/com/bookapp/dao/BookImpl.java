package com.bookapp.dao;

import java.sql.*;
import java.util.*;

import com.bookapp.bean.Book;
import com.bookapp.exception.AuthorNotFoundException;
import com.bookapp.exception.BookNotFoundException;
import com.bookapp.exception.CategoryNotFoundException;

public class BookImpl implements BookInter {

	String isql = "insert into Book(Author,BookId,Category,Title,Price) values(?,?,?,?,?)";// author,id,category,title,price
	PreparedStatement ps = null;
	Connection connection = null;

	@Override
	public void addBook(Book book) {
		Connection connection = ModelDAO.openConnection();

		try {
			ps = connection.prepareStatement(isql);

			ps.setString(1, book.getAuthor());
			ps.setInt(2, book.getBookid());
			ps.setString(3, book.getCategory());
			ps.setString(4, book.getTitle());
			ps.setInt(5, book.getPrice());
			ps.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if (ps != null)
					ps.close();
				if (connection != null)
					connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			ModelDAO.closeConnection();
		}

	}

	String delSql = "delete from Book where BookId=?";

	@Override
	public boolean deleteBook(int bookid) throws BookNotFoundException {
		Connection connection = ModelDAO.openConnection();
        int isPresent=0;
		try {
			ps = connection.prepareStatement(delSql);
			ps.setInt(1, bookid);
			isPresent=ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (ps != null)
					ps.close();
				if (connection != null)
					connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			ModelDAO.closeConnection();
		}
		if(isPresent==0) {
        	throw new BookNotFoundException("Invalid id..");
		}
		return false;
	}

	String idSql = "Select * from Book where BookId=?";

	@Override
	public Book getBookById(int bookid) {
		Connection connection = ModelDAO.openConnection();
		ResultSet rs=null;
		Book book = new Book();
		try {
			ps = connection.prepareStatement(idSql);
			ps.setInt(1, bookid);
			rs=ps.executeQuery();
			while (rs.next()) {
				book.setAuthor(rs.getString(1));
				book.setBookid(rs.getInt(2));
				book.setCategory(rs.getString(3));
				book.setTitle(rs.getString(4));
				book.setPrice(rs.getInt(5));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (ps != null)
					ps.close();
				if (connection != null)
					connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			ModelDAO.closeConnection();
		}
		/*
		 * if (rs==null) throw new BookNotFoundException("No Books Available"); return
		 * null;
		 */
		return book;
	}

	String uSql = "update Book set price=? where BookId=?";

	@Override
	public boolean updateBook(int bookid, int price) {
		Connection connection = ModelDAO.openConnection();

		try {
			ps = connection.prepareStatement(uSql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

			ps.setInt(1, price);
			ps.setInt(2, bookid);
			int i = ps.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (ps != null)
					ps.close();
				if (connection != null)
					connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			ModelDAO.closeConnection();
		}
		return false;
	}

	@Override
	public List<Book> getAllBooks() {
		List<Book> bookAll = new ArrayList<>();
		Connection connection = ModelDAO.openConnection();

		try {
			Book book = new Book();
			String allSql = "Select * from Book";
			ps = connection.prepareStatement(allSql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
			ResultSet rs = ps.executeQuery();
			rs.first();
			while (rs.next()) {

				book.setAuthor(rs.getString(1));
				book.setBookid(rs.getInt(2));
				book.setCategory(rs.getString(3));
				book.setTitle(rs.getString(4));
				book.setPrice(rs.getInt(5));
				bookAll.add(book);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if (ps != null)
					ps.close();
				if (connection != null)
					connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return bookAll;
	}

	String aSql = "Select * from Book where Author=?";

	@Override
	public List<Book> getBookbyAuthor(String author) throws AuthorNotFoundException {
		List<Book> bookAuthor = new ArrayList<>();
		Connection connection = ModelDAO.openConnection();
		ResultSet rs = null;
		Book book = new Book();
		try {
			ps = connection.prepareStatement(aSql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
			ps.setString(1, author);
			rs = ps.executeQuery();
			rs.first();
			while (rs.next()) {
				book.setAuthor(rs.getString(1));
				book.setBookid(rs.getInt(2));
				book.setCategory(rs.getString(3));
				book.setTitle(rs.getString(4));
				book.setPrice(rs.getInt(5));
				bookAuthor.add(book);
			}
			if (bookAuthor.isEmpty())
				throw new AuthorNotFoundException("No Author...");
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (ps != null)
					ps.close();
				if (connection != null)
					connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			ModelDAO.closeConnection();
		}
		return bookAuthor;
	}

	String cSql = "Select * from book where category=?";

	@Override
	public List<Book> getBookbycategory(String category) throws CategoryNotFoundException {
		List<Book> bookCategory = new ArrayList<>();
		Connection connection = ModelDAO.openConnection();

		try {
			// ps=connection.prepareStatement(cSql);
			ps = connection.prepareStatement(cSql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
			ps.setString(1, category);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Book book = new Book();
				book.setAuthor(rs.getString(1));
				book.setBookid(rs.getInt(2));
				book.setCategory(rs.getString(3));
				book.setTitle(rs.getString(4));
				book.setPrice(rs.getInt(5));
				// System.out.println(bookCategory);
				bookCategory.add(book);
			}
			if (bookCategory.isEmpty())
				throw new CategoryNotFoundException("Invalid Category...");
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (ps != null)
					ps.close();
				if (connection != null)
					connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			ModelDAO.closeConnection();
		}
		return bookCategory;
	}

	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return super.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		return super.equals(obj);
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return super.toString();
	}

}
