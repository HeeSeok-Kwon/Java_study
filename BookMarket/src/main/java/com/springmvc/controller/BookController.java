package com.springmvc.controller;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.MatrixVariable;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.springmvc.domain.Book;
import com.springmvc.exception.BookIdException;
import com.springmvc.exception.CategoryException;
import com.springmvc.service.BookService;
import com.springmvc.validator.BookValidator;

@Controller
@RequestMapping("/books")
public class BookController {
	@Autowired
	private BookService bookService;
	
	// @Autowired
	// private UnitsInStockValidator unitsInStockValidator;
	
	@Autowired
	private BookValidator bookValidator;
	
	// @RequestMapping(value="/books", method=RequestMethod.GET)
	@GetMapping
	public String requestBookList(Model model) {
		List<Book> list = bookService.getAllBookList();
		model.addAttribute("bookList", list);
		return "books";
	}
	
	@GetMapping("/all")
	public ModelAndView requestAllBookList() {
		ModelAndView model = new ModelAndView();
		List<Book> list = bookService.getAllBookList();
		model.addObject("bookList", list);
		model.setViewName("books");
		return model;
	}
	
	@GetMapping("/{category}")
	public String requestBookByCategory(@PathVariable("category") String bookCategory, Model model) {
		List<Book> booksByCategory = bookService.getBookListByCategory(bookCategory);
		
		if(booksByCategory == null || booksByCategory.isEmpty()) {
			throw new CategoryException();
		}
		model.addAttribute("bookList", booksByCategory);
		return "books";
	}
	
	@GetMapping("/filter/{bookFilter}")
	public String requestBookByFilter(@MatrixVariable(pathVar="bookFilter") Map<String, List<String>> bookFilter,
			Model model) {
		Set<Book> booksByFilter = bookService.getBookListByFilter(bookFilter);
		model.addAttribute("bookList", booksByFilter);
		return "books";
	}
	
	@GetMapping("/book")
	public String requestBookById(@RequestParam("id") String bookId, Model model) {
		Book bookById = bookService.getBookById(bookId);
		model.addAttribute("book", bookById);
		return "book";
	}
	
	@GetMapping("/add")
	public String requestAddBookForm(@ModelAttribute("NewBook") Book book) {
		return "addBook";
	}
	
	@PostMapping("/add")
	public String submitAddNewBook(@Valid @ModelAttribute("NewBook") Book book, BindingResult result) {
		
		if(result.hasErrors()) {
			return "addBook";
		}
		
		MultipartFile bookImage = book.getBookImage();
		
		String saveName = bookImage.getOriginalFilename();
		// File saveFile = new File("C:\\Users\\계정명\\eclipse-workspace\\BookMarket\\src\\main\\webapp\\resources\\images", saveName);
		File saveFile = new File("C:/Users/계정명/eclipse-workspace/BookMarket/src/main/webapp/resources/images/", saveName);
		
		if(bookImage != null && !bookImage.isEmpty()) {
			try {
				bookImage.transferTo(saveFile);
				// 17장 추가
				book.setFileName(saveName);
			} catch(Exception e) {
				throw new RuntimeException("도서 이미지 업로드가 실패했습니다.", e);
			}
		}
		
		bookService.setNewBook(book);
		return "redirect:/books";
	}
	
	// 메서드 수준의 @ModelAttribute
	// 공통으로 사용할 커맨드 객체 프로퍼티 설정 - 먼저 호출됨
	@ModelAttribute
	public void addAttributes(Model model) {
		model.addAttribute("addTitle", "신규 도서 등록");
	}
	
	// 입력 데이터가 커맨드 객체 프로퍼티에 입력되기 전 호출되고 필터링 함
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		// binder.setValidator(unitsInStockValidator);
		binder.setValidator(bookValidator);
		binder.setAllowedFields("bookId", "name", "unitPrice", "author", "description",
				"publisher", "category", "unitsInStock", "totalPages", "releaseDate", "condition", "bookImage");
	}
	
	@ExceptionHandler(value= {BookIdException.class})
	public ModelAndView handleError(HttpServletRequest req, BookIdException exception) {
		ModelAndView mav = new ModelAndView();
		mav.addObject("invalidBookId", exception.getBookId());
		mav.addObject("exception", exception);
		mav.addObject("url", req.getRequestURL() + "?" + req.getQueryString());
		mav.setViewName("errorBook");
		
		return mav;
	}
	
	@GetMapping("/update")
	public String getUpdateBookForm(@ModelAttribute("updateBook") Book book, @RequestParam("id") String bookId, Model model) {
		Book bookById = bookService.getBookById(bookId);
		model.addAttribute("book", bookById);
		return "updateForm";
	}
	
	@PostMapping("/update")
	public String submitUpdateBookForm(@ModelAttribute("updateBook") Book book) {
		MultipartFile bookImage = book.getBookImage();
		
		String rootDirectory = "C:/Users/계정명/eclipse-workspace/BookMarket/src/main/webapp/resources/images/";
		
		if(bookImage != null && !bookImage.isEmpty()) {
			try {
				String fname = bookImage.getOriginalFilename();
				bookImage.transferTo(new File(rootDirectory+fname));
				book.setFileName(fname);
			} catch(Exception e) {
				throw new RuntimeException("Book Image saving failed", e);
			}
		}
		bookService.setUpdateBook(book);
		return "redirect:/books";
	}
	
	@RequestMapping(value="/delete")
	public String getDeleteBookForm(Model model, @RequestParam("id") String bookId) {
		bookService.setDeleteBook(bookId);
		return "redirect:/books";
	}
}
