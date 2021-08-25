package com.bookapp.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import com.bookapp.bean.Book;
import com.bookapp.exception.BookNotFoundException;

public class BookImpl implements BookInter{
     
	
     String isql="insert into Book(Author,BookId,Category,Title,Price) values(?,?,?,?,?)";//author,id,category,title,price
     PreparedStatement ps=null;
     Connection connection=null;
	@Override
	public void addBook(Book book)  {
		Connection connection=ModelDAO.openConnection();
		
		try {
			ps=connection.prepareStatement(isql);
			
			ps.setString(1,book.getAuthor());
			ps.setInt(2,book.getBookid());
			ps.setString(3,book.getCategory());
			ps.setString(4,book.getTitle());
			ps.setInt(5,book.getPrice());
			ps.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
				try {
					if(ps!=null)
					ps.close();
					if(connection!=null)
						connection.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				ModelDAO.closeConnection();
		}
			
	}

	String delSql="delete from Book where BookId=?";
	@Override
	public boolean deleteBook(int bookid) throws BookNotFoundException {
     Connection connection=ModelDAO.openConnection();
		
		try {
			ps=connection.prepareStatement(delSql);
			ps.setInt(1,bookid);
			ps.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
				try {
					if(ps!=null)
					ps.close();
					if(connection!=null)
						connection.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				ModelDAO.closeConnection();
		}
		return false;
	}

	String idSql="Select * from Book where BookId=?";
	@Override
	public Book getBookById(int bookid) throws BookNotFoundException {
    Connection connection=ModelDAO.openConnection();
		Book book=new Book();
		try {
			ps=connection.prepareStatement(idSql);
			ps.setInt(1, bookid);
			ResultSet rs=null; 
			rs.first(); 
			while(rs.next()) { 
				book.setAuthor(rs.getString(1));
			    book.setBookid(rs.getInt(2));
			    book.setCategory(rs.getString(3));
				book.setTitle(rs.getString(4));
				book .setPrice(rs.getInt(5));
			}
				//System.out.println(author+"\t"+id+"\t"+category+"\t"+title+"\t"+price1); }
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
				try {
					if(ps!=null)
					ps.close();
					if(connection!=null)
						connection.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				ModelDAO.closeConnection();
		}

		return book;
	}

	String uSql="update Book set price=? where BookId=?";
	@Override
	public boolean updateBook(int bookid, int price) {
Connection connection=ModelDAO.openConnection();
		
		try {
			ps=connection.prepareStatement(uSql,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
			
			ps.setInt(1, price);
			ps.setInt(2, bookid);
			int i=ps.executeUpdate();
			System.out.println(i);
			/*
			 * ResultSet rs=null; rs.first(); while(rs.next()) { String
			 * author=rs.getString(1); int id=rs.getInt(2); String category=rs.getString(3);
			 * String title=rs.getString(4); int price1=rs.getInt(5);
			 * System.out.println(author+"\t"+id+"\t"+category+"\t"+title+"\t"+price1); }
			 */
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
				try {
					if(ps!=null)
					ps.close();
					if(connection!=null)
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
		List<Book> bookAll=new ArrayList<>();
        Connection connection=ModelDAO.openConnection();
       
		try {
			 Book book=new Book();
			String allSql="Select * from Book";
			ps=connection.prepareStatement(allSql,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
			ResultSet rs=ps.executeQuery();
			rs.first();
			while(rs.next()) {
				
			   book.setAuthor(rs.getString(1));
			    book.setBookid(rs.getInt(2));
			    book.setCategory(rs.getString(3));
				book.setTitle(rs.getString(4));
				book .setPrice(rs.getInt(5));
				//System.out.println(bookAll);
				bookAll.add(book);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
				try {
					if(ps!=null)
					ps.close();
					if(connection!=null)
						connection.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		return bookAll;
	}

	String aSql="Select * from Book where Author=?";
	ResultSet rs=null;
	@Override
	public List<Book> getBookbyAuthor(String author) {
		List<Book> bookAuthor=new ArrayList<>();
     Connection connection=ModelDAO.openConnection();
     
		try {
			//ps=connection.prepareStatement(aSql);
			ps=connection.prepareStatement(aSql,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
			ps.setString(1, author);
			rs=ps.executeQuery();
			//rs.first();
			while(rs.next()) {
				Book book=new Book();
				book.setAuthor(rs.getString(1));
				book.setBookid(rs.getInt(2));
				book.setCategory(rs.getString(3));
			    book.setTitle(rs.getString(4));
				book.setPrice(rs.getInt(5));
				bookAuthor.add(book);
				/*
				 * if(book.getAuthor().equals(author)) return bookAuthor;
				 */
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
				try {
					if(ps!=null)
					ps.close();
					if(connection!=null)
						connection.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				ModelDAO.closeConnection();
		}
		return bookAuthor;
	}

	String cSql="Select * from book where category=?";
	@Override
	public List<Book> getBookbycategory(String category) {
		List<Book> bookCategory=new ArrayList<>();
       Connection connection=ModelDAO.openConnection();
		
		try {
					//ps=connection.prepareStatement(cSql);
					ps=connection.prepareStatement(cSql,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
					ps.setString(1, category);
					ResultSet rs=ps.executeQuery();
					while(rs.next()) {
						Book book=new Book();
						book.setAuthor(rs.getString(1));
						book.setBookid(rs.getInt(2));
						book.setCategory(rs.getString(3));
						book.setTitle(rs.getString(4));
						book.setPrice(rs.getInt(5));
						//System.out.println(bookCategory);
						bookCategory.add(book);
					}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
				try {
					if(ps!=null)
					ps.close();
					if(connection!=null)
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
