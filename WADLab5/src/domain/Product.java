package domain;

public class Product {
	
	private String name, type, description, imgPath;
	private double price;
	
	public Product() {}
	
	public Product(String name, String type, String description, double price, String imgPath) {
		this.name = name;
		this.type = type;
		this.description = description;
		this.imgPath = imgPath;
		this.price = price;
	}
	
	public Product(String name, String type, double price, String imgPath) {
		this.name = name;
		this.type = type;
		this.imgPath = imgPath;
		this.price = price;
	}


	public Product(String name, String type, String description, double price)
	{
		this.name = name;
		this.type = type;
		this.description = description;
		this.price = price;
	}
	
	public Product(String name, String description, double price)
	{
		this.name = name;
		this.description = description;
		this.price = price;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getImgPath() {
		return imgPath;
	}

	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}
	
}
