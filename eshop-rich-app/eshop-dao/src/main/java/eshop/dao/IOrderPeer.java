/**
 * 
 */
package eshop.dao;

import java.util.Hashtable;

import eshop.model.Customer;

/**
 * @author kholmes
 * 
 */
public interface IOrderPeer {

	/**
	 * 
	 * @param customer
	 * @param shoppingCart
	 */
	Long insertOrder(Customer customer, Hashtable<?, ?> shoppingCart);
}
