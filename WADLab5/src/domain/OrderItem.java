package domain;

public class OrderItem extends Product{

	private int quantity;
	public OrderItem(String name, String description, double price, int quantity) {
		super(name, description, price);
		this.quantity = quantity;
		// TODO Auto-generated constructor stub
	}
	
	public OrderItem(String name, String description, double price, String imgPath, int quantity) {
		super(name, description, price, imgPath);
		this.quantity = quantity;
		// TODO Auto-generated constructor stub
	}
	
	public OrderItem(String name, String type, String description, double price, String imgPath, int quantity) {
		super(name, type, description, price, imgPath);
		this.quantity = quantity;
		// TODO Auto-generated constructor stub
	}

	public int getQuantity()
	{
		return this.quantity;
	}
	
	public void setQuantity(int quantity)
	{
		this.quantity = quantity;
	}
	
	public double getTotalPrice()
	{
		return this.quantity*this.getPrice();
	}
}
