package eshop.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import eshop.components.HtmlInputTextComponent;
import eshop.components.ListDataModelComponent;
import eshop.manager.DataManager;
import eshop.model.Book;
import eshop.model.CartItem;
import eshop.model.Category;
import eshop.model.Customer;

@Controller("shopManager")
@Scope("session")
public class ShopManager implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5295449897868910970L;
	private Book book;
	private List<?> books;
	private ListDataModelComponent booksDataModel = new ListDataModelComponent();
	private ListDataModelComponent cartItems = new ListDataModelComponent();
	private ListDataModelComponent categoriesDataModel = new ListDataModelComponent();
	private String categoryName;
	private Customer customer;

	@Autowired
	private DataManager dataManager;

	private long orderId;
	private double orderTotal;
	private HtmlInputTextComponent searchTxt = new HtmlInputTextComponent();
	private Hashtable<String, CartItem> shoppingCart = new Hashtable<String, CartItem>();

	public String addToCart() {
		CartItem item = shoppingCart.get(book.getId());
		if (item == null) {
			item = new CartItem(book, 1);
			shoppingCart.put(book.getId(), item);
		} else {
			item.setQuantity(item.getQuantity() + 1);
		}
		return "showCart";
	}

	public String checkOut() {
		orderId = dataManager.insertOrder(customer, shoppingCart);
		if (orderId != 0) {
			customer = null;
			shoppingCart.clear();
		}
		return "orderConfirmation";
	}

	public String deleteItem() {
		CartItem item = (CartItem) cartItems.getRowData();
		shoppingCart.remove(item.getBookID());
		return null;
	}

	public Book getBook() {
		return book;
	}

	public ListDataModelComponent getBooks() {
		if (books == null) {
			booksDataModel.setWrappedData(new LinkedList<Book>());
		} else {
			booksDataModel.setWrappedData(books);
		}
		return booksDataModel;
	}

	public ListDataModelComponent getCategories() {
		categoriesDataModel.setWrappedData(dataManager.getCategories());
		return categoriesDataModel;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public String getContactName() {
		return getCustomer().getContactName();
	}

	public Customer getCustomer() {
		if (customer == null) {
			customer = new Customer();
		}
		return customer;
	}

	public String getOrderId() {
		if (orderId == 0)
			return "";
		return Long.toHexString(orderId).toUpperCase();
	}

	public double getOrderTotal() {
		orderTotal = 0.;
		Enumeration<CartItem> items = shoppingCart.elements();
		while (items.hasMoreElements()) {
			orderTotal += items.nextElement().getSubtotal();
		}
		orderTotal = Math.round(orderTotal * 100.) / 100.;
		return orderTotal;
	}

	public HtmlInputTextComponent getSearchTxt() {
		return searchTxt;
	}

	public ListDataModelComponent getShoppingCart() {
		ArrayList<CartItem> itemList = new ArrayList<CartItem>(
				shoppingCart.values());
		cartItems.setWrappedData(itemList);
		return cartItems;
	}

	public boolean isShoppingCartEmpty() {
		return shoppingCart.isEmpty();
	}

	public String searchBooks() {
		categoryName = null;
		String searchKeyword = (String) searchTxt.getSubmittedValue();
		books = dataManager.getSearchResults(searchKeyword);
		return "listBooks";
	}

	public String selectBook() {
		book = (Book) booksDataModel.getRowData();
		return "showBook";
	}

	public String selectCategory() {
		Category category = (Category) categoriesDataModel.getRowData();
		categoryName = category.getName();
		books = dataManager.getBooksInCategory(Integer.toString(category
				.getId()));
		return "listBooks";
	}

	public void setContactName(String contactName) {
		getCustomer().setContactName(contactName);
	}

	public void setCustomer(Customer order) {
		this.customer = order;
	}

	public void setSearchTxt(HtmlInputTextComponent val) {
		searchTxt = val;
	}

	public String updateItem() {
		return null;
	}
}
