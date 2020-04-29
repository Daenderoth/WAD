package daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.naming.NamingException;

import database.DBConnection;
import domain.Order;
import domain.OrderItem;

public class OrderDAO {
	
	private static OrderDAO instance;
		
		public static OrderDAO getInstance() {
			
			if (instance == null) 
			{
				instance = new OrderDAO();
			}
			
			return instance;
			
		}
		
		private OrderDAO() {}
		
		public void registerOrder(String customerName, List<OrderItem> items) throws NamingException, SQLException
		{
			//Getting current customer ID
			String sql = "SELECT id FROM users where username=?"; //our query
			
			Connection dbconn = DBConnection.getConnection();

			PreparedStatement instr = dbconn.prepareStatement(sql);
			instr.setString(1, customerName);
			ResultSet rs = instr.executeQuery();
			int customerID = 0;
			while(rs.next())
			{
				customerID = rs.getInt("id");
			}
			
			//Registering Order
			String sqlOrder = "INSERT INTO ECOMMERCE.ORDER(customer_id, date) VALUES(?,?)";
			instr = dbconn.prepareStatement(sqlOrder);
			instr.setInt(1, customerID);
			instr.setDate(2, new Date(System.currentTimeMillis()));
			int row = instr.executeUpdate();
			
			//Getting registered order ID
			String sqlOrderID = "SELECT MAX(id) FROM ECOMMERCE.ORDER";
			instr = dbconn.prepareStatement(sqlOrderID);
			rs = instr.executeQuery();
			int orderID = 0;
			
			while(rs.next())
			{
				orderID = rs.getInt("MAX(id)");
			}
			
			//Registering Order Items
			String sqlOrderItem = "INSERT INTO ORDERITEM(order_id, product_id, quantity) VALUES(?,?,?)";
			instr = dbconn.prepareStatement(sqlOrderItem);
			
			//First we have to retrieve product ID
			String sqlProductID = "SELECT id FROM PRODUCTS WHERE name=?";
			PreparedStatement iter = dbconn.prepareStatement(sqlProductID);
			int productID = 0;
			for(OrderItem item : items)
			{
				//Getting product ID
				iter.setString(1, item.getName());
				rs = iter.executeQuery();
				
				while(rs.next())
				{
					productID = rs.getInt("id");
				}
				
				
				//Registering Order Item
				instr.setInt(1, orderID);
				instr.setInt(2, productID);
				instr.setInt(3, item.getQuantity());
				row = instr.executeUpdate();
			}
			
		}
		
		public List<Order> getOrders(int userID) throws NamingException, SQLException
		{
			List<Order> orders = new ArrayList<>();
			
			//Getting orders of user with id userID
			String sql = "select o.id, o.date from ecommerce.order o\r\n" + 
					"join users u on o.customer_id=u.id\r\n" + 
					"where u.id=?"; //our query
			
			Connection dbconn = DBConnection.getConnection();

			PreparedStatement instr = dbconn.prepareStatement(sql);
			instr.setInt(1, userID);
			ResultSet rs = instr.executeQuery();
			
			while(rs.next())
			{
				orders.add(new Order(rs.getInt("id"), rs.getDate("date")));
			}
			
			return orders;
			
		}
		
		public List<OrderItem> getItems(int orderID) throws NamingException, SQLException
		{
			List<OrderItem> items = new ArrayList<>();
			
			//Getting orders of user with id userID
			String sql = "select name, type, description, price, image, quantity from products p\r\n" + 
					"join orderitem o on o.product_id=p.id\r\n" + 
					"join ecommerce.order ord on o.order_id=ord.id\r\n" + 
					"where ord.id=?"; //our query
			
			Connection dbconn = DBConnection.getConnection();

			PreparedStatement instr = dbconn.prepareStatement(sql);
			instr.setInt(1, orderID);
			ResultSet rs = instr.executeQuery();
			
			while(rs.next())
			{
				items.add(new OrderItem(rs.getString("name"), rs.getString("type"), rs.getString("description"), rs.getDouble("price"), rs.getString("image"), rs.getInt("quantity")));
			}
			
			return items;
		}

}
