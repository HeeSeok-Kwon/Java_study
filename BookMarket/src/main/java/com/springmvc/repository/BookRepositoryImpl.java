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
		Book book1 = new Book("ISBN1234", "C#교과서", 30000);
		book1.setAuthor("박용준");
		book1.setDescription("C#교과서는 생애 첫 프로그래밍 언어로 C#을 시작하는 독자를 대상을 한다.");
		book1.setPublisher("길벗");
		book1.setCategory("IT전문서");
		book1.setUnitsInStock(1000);
		book1.setReleaseDate("2020-05-29");
		
		Book book2 = new Book("ISBN1235", "Node.js 교과서", 36000);
		book2.setAuthor("조현영");
		book2.setDescription("이 책은 프론트부터 서버, 데이터베이스, 배포까지 아우르는 광범위한 내용을 다룬다.");
		book2.setPublisher("길벗");
		book2.setCategory("IT전문서");
		book2.setUnitsInStock(1000);
		book2.setReleaseDate("2020-07-25");
		
		Book book3 = new Book("ISBN1236", "어도비 XD CC 2020", 25000);
		book3.setAuthor("김두한");
		book3.setDescription("어도비 XD 프로그램을 통해 UI/UX 디장닝르 배우고자 하는 예비 디자이너의 눈높이에 맞게 기본적인 도구를 활용한 아이콘 디자인과 ~");
		book3.setPublisher("길벗");
		book3.setCategory("IT활용서");
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
		
		// DB 연동
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
					
					// 복사 후 메서드를 적절하게 변경하지 못함 book.getPublisher() >> book.getCategory()
					if(category.equalsIgnoreCase(book.getCategory())) {
						booksByCategory.add(book);
					}
				}
			}
		}*/
		
		// DB 연동
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
		// DB 연동
		String SQL = "SELECT count(*) FROM book where b_bookId=?";
		int rowCount = template.queryForObject(SQL, Integer.class, bookId);
		if(rowCount != 0) {
			SQL = "SELECT * FROM book where b_bookId=?";
			bookInfo = template.queryForObject(SQL, new Object[] {bookId}, new BookRowMapper());
		}
		
		if(bookInfo == null) {
			// throw new IllegalArgumentException("도서 ID가 "+bookId+"인 해당 도서를 찾을 수 없습니다.");
			throw new BookIdException(bookId);
		}
		
		return bookInfo;
	}
	
	public void setNewBook(Book book) {
		// listOfBooks.add(book);
		
		// DB 연동
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
			// DB 연동
			String SQL = "UPDATE book SET b_name = ?, b_unitPrice = ?, b_author = ?, "
					+ "b_description = ?, b_publisher = ?, b_category = ?, b_unitsInStock = ?, "
					+ "b_releaseDate = ?, b_condition = ?, b_fileName = ? "
					+ "WHERE b_bookId = ?";
			
			template.update(SQL, book.getName(), book.getUnitPrice(), 
					book.getAuthor(), book.getDescription(), book.getPublisher(),
					book.getCategory(), book.getUnitsInStock(), book.getReleaseDate(),
					book.getCondition(), book.getFileName(), book.getBookId());
		} else if (book.getFileName() == null) {
			// DB 연동
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
