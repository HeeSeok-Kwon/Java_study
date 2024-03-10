package com.springmvc.repository;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.springmvc.domain.Book;
import com.springmvc.exception.BookIdException;

@Repository
public class BookRepositoryImpl implements BookRepository {
	private List<Book> listOfBooks = new ArrayList<Book>();
	
	private JdbcTemplate template;
	
	@Autowired
	public void setJdbctemplate(DataSource dataSource) {
		this.template = new JdbcTemplate(dataSource);
	}
	
	public BookRepositoryImpl() {
		Book book1 = new Book("ISBN1234", "C#������", 30000);
		book1.setAuthor("�ڿ���");
		book1.setDescription("C#�������� ���� ù ���α׷��� ���� C#�� �����ϴ� ���ڸ� ����� �Ѵ�.");
		book1.setPublisher("���");
		book1.setCategory("IT������");
		book1.setUnitsInStock(1000);
		book1.setReleaseDate("2020-05-29");
		
		Book book2 = new Book("ISBN1235", "Node.js ������", 36000);
		book2.setAuthor("������");
		book2.setDescription("�� å�� ����Ʈ���� ����, �����ͺ��̽�, �������� �ƿ츣�� �������� ������ �ٷ��.");
		book2.setPublisher("���");
		book2.setCategory("IT������");
		book2.setUnitsInStock(1000);
		book2.setReleaseDate("2020-07-25");
		
		Book book3 = new Book("ISBN1236", "��� XD CC 2020", 25000);
		book3.setAuthor("�����");
		book3.setDescription("��� XD ���α׷��� ���� UI/UX ����׸� ������ �ϴ� ���� �����̳��� �����̿� �°� �⺻���� ������ Ȱ���� ������ �����ΰ� ~");
		book3.setPublisher("���");
		book3.setCategory("ITȰ�뼭");
		book3.setUnitsInStock(1000);
		book3.setReleaseDate("2019-05-29");
		
		listOfBooks.add(book1);
		listOfBooks.add(book2);
		listOfBooks.add(book3);
	}

	@Override
	public List<Book> getAllBookList() {
		// TODO Auto-generated method stub
		// return listOfBooks;
		String SQL = "SELECT * FROM book";
		List<Book> listOfBooks = template.query(SQL, new BookRowMapper());
		return listOfBooks;
	}
	
	public List<Book> getBookListByCategory(String category) {
		List<Book> booksByCategory = new ArrayList<Book>();
		
		/*for(int i=0;i<listOfBooks.size();i++) {
			Book book = listOfBooks.get(i);
			if(category.equalsIgnoreCase(book.getCategory())) {
				booksByCategory.add(book);
			}
		}*/
		
		// DB ����
		String SQL = "SELECT * FROM book where b_catetory LIKE '%"+category+"%'";
		booksByCategory = template.query(SQL, new BookRowMapper());
		
		return booksByCategory;
	}
	
	public Set<Book> getBookListByFilter(Map<String, List<String>> filter) {
		Set<Book> booksByPublisher = new HashSet<Book>();
		Set<Book> booksByCategory = new HashSet<Book>();
		
		/*Set<String> booksByFilter = filter.keySet();
		
		if(booksByFilter.contains("publisher")) {
			for(int j=0;j<filter.get("publisher").size();j++) {
				String publisherName = filter.get("publisher").get(j);
				for(int i=0;i<listOfBooks.size();i++) {
					Book book = listOfBooks.get(i);
					
					if(publisherName.equalsIgnoreCase(book.getPublisher())) {
						booksByPublisher.add(book);
					}
				}
			}
		}
		
		if(booksByFilter.contains("category")) {
			for(int j=0;j<filter.get("category").size();j++) {
				String category = filter.get("category").get(j);
				for(int i=0;i<listOfBooks.size();i++) {
					Book book = listOfBooks.get(i);
					
					// ���� �� �޼��带 �����ϰ� �������� ���� book.getPublisher() >> book.getCategory()
					if(category.equalsIgnoreCase(book.getCategory())) {
						booksByCategory.add(book);
					}
				}
			}
		}*/
		
		// DB ����
		Set<String> criterias = filter.keySet();
		
		if(criterias.contains("publisher")) {
			for(int j = 0;j<filter.get("publisher").size();j++) {
				String publisherName = filter.get("publisher").get(j);
				String SQL = "SELECT * FROM book WHERE b_publisher LIKE '%"+publisherName+"%'";
				booksByPublisher.addAll(template.query(SQL, new BookRowMapper()));
			}
		}
		
		if(criterias.contains("category")) {
			for(int j = 0;j<filter.get("category").size();j++) {
				String category = filter.get("category").get(j);
				String SQL = "SELECT * FROM book WHERE b_category LIKE '%"+category+"%'";
				booksByCategory.addAll(template.query(SQL, new BookRowMapper()));
			}
		}
		
		booksByCategory.retainAll(booksByPublisher);
		return booksByCategory;
 	}
	
	public Book getBookById(String bookId) {
		Book bookInfo = null;
		/*for(int i=0;i<listOfBooks.size();i++) {
			Book book = listOfBooks.get(i);
			if(book != null && book.getBookId() != null && book.getBookId().equals(bookId)) {
				bookInfo = book;
				break;
			}
		}*/
		// DB ����
		String SQL = "SELECT count(*) FROM book where b_bookId=?";
		int rowCount = template.queryForObject(SQL, Integer.class, bookId);
		if(rowCount != 0) {
			SQL = "SELECT * FROM book where b_bookId=?";
			bookInfo = template.queryForObject(SQL, new Object[] {bookId}, new BookRowMapper());
		}
		
		if(bookInfo == null) {
			// throw new IllegalArgumentException("���� ID�� "+bookId+"�� �ش� ������ ã�� �� �����ϴ�.");
			throw new BookIdException(bookId);
		}
		
		return bookInfo;
	}
	
	public void setNewBook(Book book) {
		// listOfBooks.add(book);
		
		// DB ����
		String SQL = "INSERT INTO book (b_bookId, b_name, b_unitPrice, b_author, "
				+ "b_description, b_publisher, b_category, b_unitsInStock, b_releaseDate, b_condition, b_fileName) "
				+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		
		template.update(SQL, book.getBookId(), book.getName(), book.getUnitPrice(), 
				book.getAuthor(), book.getDescription(), book.getPublisher(),
				book.getCategory(), book.getUnitsInStock(), book.getReleaseDate(),
				book.getCondition(), book.getFileName());
	}
	
	public void setUpdateBook(Book book) {
		// listOfBooks.add(book);
		
		if(book.getFileName() != null) {
			// DB ����
			String SQL = "UPDATE book SET b_name = ?, b_unitPrice = ?, b_author = ?, "
					+ "b_description = ?, b_publisher = ?, b_category = ?, b_unitsInStock = ?, "
					+ "b_releaseDate = ?, b_condition = ?, b_fileName = ? "
					+ "WHERE b_bookId = ?";
			
			template.update(SQL, book.getName(), book.getUnitPrice(), 
					book.getAuthor(), book.getDescription(), book.getPublisher(),
					book.getCategory(), book.getUnitsInStock(), book.getReleaseDate(),
					book.getCondition(), book.getFileName(), book.getBookId());
		} else if (book.getFileName() == null) {
			// DB ����
			String SQL = "UPDATE book SET b_name = ?, b_unitPrice = ?, b_author = ?, "
					+ "b_description = ?, b_publisher = ?, b_category = ?, b_unitsInStock = ?, "
					+ "b_releaseDate = ?, b_condition = ? "
					+ "WHERE b_bookId = ?";
			
			template.update(SQL, book.getName(), book.getUnitPrice(), 
					book.getAuthor(), book.getDescription(), book.getPublisher(),
					book.getCategory(), book.getUnitsInStock(), book.getReleaseDate(),
					book.getCondition(), book.getBookId());
		}
	}
	
	public void setDeleteBook(String bookId) {
		String SQL = "DELETE FROM book WHERE b_bookId = ?";
		this.template.update(SQL, bookId);
	}
}
