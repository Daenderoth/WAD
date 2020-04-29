package daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.NamingException;

import database.DBConnection;
import domain.Product;

public class ProductDAO {
	
	private static ProductDAO instance;
		
		public static ProductDAO getInstance() {
			
			if (instance == null) 
			{
				instance = new ProductDAO();
			}
			
			return instance;
			
		}
		
		private ProductDAO() {}
		
		public List<Product> getProducts() throws NamingException, SQLException
		{
			List<Product> products = new ArrayList<Product>();
			
			String sql = "SELECT name, type, description, price, image FROM products"; //our query
			
			Connection dbconn = DBConnection.getConnection();
			PreparedStatement instr = dbconn.prepareStatement(sql);
			ResultSet rs = instr.executeQuery();
			
			while(rs.next())
			{
				products.add(new Product(rs.getString("name"), rs.getString("type"), rs.getString("description"), rs.getDouble("price"), rs.getString("image")));
			}
			
			return products;
			
		}
		
		public String getProductImg(String name) throws NamingException, SQLException 
		{
			String sql = "SELECT image FROM products where name=?"; //our query
			
			Connection dbconn = DBConnection.getConnection();
			PreparedStatement instr = dbconn.prepareStatement(sql);
			instr.setString(1, name);
			ResultSet rs = instr.executeQuery();
			
			rs.next();
			
			return rs.getString("image");
		}
		
		public void addNewProduct(Product p) throws NamingException, SQLException
		{
			String sql = "INSERT INTO products(name,type,description,price,image) VALUES(?,?,?,?,?)"; //our query
			
			Connection dbconn = DBConnection.getConnection();
			PreparedStatement instr = dbconn.prepareStatement(sql);
			instr.setString(1, p.getName());
			instr.setString(2, p.getType());
			instr.setString(3, p.getDescription());
			instr.setDouble(4, p.getPrice());
			instr.setString(5, p.getImgPath());
			int row = instr.executeUpdate();
		}

}