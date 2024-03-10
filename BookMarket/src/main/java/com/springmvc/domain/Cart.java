package com.springmvc.domain;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Cart implements Serializable {
	private String cartId;
	private Map<String, CartItem> cartItems;
	private int grandTotal;
	
	// 15장 추가 변수
	private static final long serialVersionUID = 2155125089108199199L;
	
	public Cart() {
		// super();
		// TODO Auto-generated constructor stub
		cartItems = new HashMap<String, CartItem>();
		grandTotal = 0;
	}

	public Cart(String cartId) {
		// super();
		this();
		this.cartId = cartId;
	}

	public String getCartId() {
		return cartId;
	}

	public void setCartId(String cartId) {
		this.cartId = cartId;
	}

	public Map<String, CartItem> getCartItems() {
		return cartItems;
	}

	public void setCartItems(Map<String, CartItem> cartItems) {
		this.cartItems = cartItems;
	}

	public int getGrandTotal() {
		return grandTotal;
	}

	public void setGrandTotal(int grandTotal) {
		this.grandTotal = grandTotal;
	}
	
	public void updateGrandTotal() {
		grandTotal = 0;
		for(CartItem item : cartItems.values()) {
			grandTotal = grandTotal + item.getTotalPrice();
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cartId == null) ? 0 : cartId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cart other = (Cart) obj;
		if (cartId == null) {
			if (other.cartId != null)
				return false;
		} else if (!cartId.equals(other.cartId))
			return false;
		return true;
	}
	
	public void addCartItem(CartItem item) {
		String bookId = item.getBook().getBookId(); // getBook 반환값은 Book class
		
		if(cartItems.containsKey(bookId)) {
			CartItem cartItem = cartItems.get(bookId);
			cartItem.setQuantity(cartItem.getQuantity() + item.getQuantity());
			cartItems.put(bookId, cartItem);
		} else {
			cartItems.put(bookId, item);
		}
		updateGrandTotal();
	}
	
	public void removeCartItem(CartItem item) {
		String bookId = item.getBook().getBookId(); // getBook 반환값은 Book class
		cartItems.remove(bookId);
		updateGrandTotal();
	}
}
