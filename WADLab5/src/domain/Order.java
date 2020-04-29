package domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Order {
	
	private int id;
	private List<OrderItem> items;
	private Date date;
	
	public Order(int id, Date date){
		this.id = id;
		this.date = date;
		items = new ArrayList<>();
	}

	public Order(int id, List<OrderItem> items, Date date) 
	{
		this.id = id;
		this.date = date;
		this.items = items;
	}
	
	public int getId() {
		return this.id;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public List<OrderItem> getItems() {
		return items;
	}

	public void setItems(List<OrderItem> items) {
		this.items = items;
	}
	
	public double getTotal()
	{
		double total = 0;
		
		for(OrderItem item : this.items)
		{
			total += item.getTotalPrice();
		}
		
		return total;
	}

}
