package eshop.manager;

import java.io.Serializable;
import java.util.Hashtable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import eshop.dao.IBookPeer;
import eshop.dao.ICategoryPeer;
import eshop.dao.IOrderPeer;
import eshop.model.Book;
import eshop.model.Category;
import eshop.model.Customer;

@Component("dataManager")
public class DataManager implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6341726998849308671L;

	/**
	 * Appel de BookPeer
	 */

	@Autowired
	@Qualifier("bookPeer")
	private IBookPeer bookPeer;

	@Autowired
	@Qualifier("categoryPeer")
	private ICategoryPeer categoryPeer;

	@Autowired
	@Qualifier("orderPeer")
	private IOrderPeer orderPeer;

	public String getCategoryName(String categoryID) {
		Category category = categoryPeer.getCategoryById(categoryID);
		return (category == null) ? null : category.getName();
	}

	public List<Category> getCategories() {
		return categoryPeer.getAllCategories();
	}

	public List<?> getSearchResults(String keyword) {
		return bookPeer.searchBooks(keyword);
	}

	public List<?> getBooksInCategory(String categoryID) {
		return bookPeer.getBooksByCategory(categoryID);
	}

	public Book getBookDetails(String bookID) {
		return bookPeer.getBookById(bookID);
	}

	public long insertOrder(Customer customer, Hashtable<?, ?> shoppingCart) {
		return orderPeer.insertOrder(customer, shoppingCart);
	}

}
