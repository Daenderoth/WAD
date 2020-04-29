package daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.NamingException;

import database.DBConnection;
import domain.Account;

public class UserDAO {
	
	private static UserDAO instance;
	
	public static UserDAO getInstance() {
		
		if (instance == null) 
		{
			instance = new UserDAO();
		}
		
		return instance;
		
	}
	
	private UserDAO() {}
	
	public boolean userExists(String username) throws SQLException, NamingException
	{	
		//DB Connection
		String sql = "SELECT * FROM users where username=?"; //our query
		
		Connection dbconn = DBConnection.getConnection();

		PreparedStatement instr = dbconn.prepareStatement(sql);
		instr.setString(1, username);
		ResultSet rs = instr.executeQuery();
		
		if(rs.next())
		{
			return true;
		}
		
		return false;
			
	}
	
	public boolean userExists(String username, String password) throws SQLException, NamingException
	{	
		//DB Connection
		String sql = "SELECT * FROM users where username=? and password=?"; //our query
		
		Connection dbconn = DBConnection.getConnection();

		PreparedStatement instr = dbconn.prepareStatement(sql);
		instr.setString(1, username);
		instr.setString(2, password);
		ResultSet rs = instr.executeQuery();
		
		if(rs.next())
		{
			return true;
		}
		
		return false;
			
	}
	
	public boolean userExists(Account user) throws SQLException, NamingException
	{
		Connection dbconn = DBConnection.getConnection();
		//first check email - must be unique
		String email = user.getEmail();
		String checkEmail = "SELECT * from users where email=?";
		PreparedStatement check = dbconn.prepareStatement(checkEmail);
		check.setString(1, email);
		ResultSet emailrs = check.executeQuery();
		if(emailrs.next())
			
			return true;
		
		
		//DB Connection
		String sql = "SELECT * FROM users where username=? and password=?"; //our query
		
		
		PreparedStatement instr = dbconn.prepareStatement(sql);
		instr.setString(1, user.getUserName());
		instr.setString(2, user.getPassword());
		ResultSet rs = instr.executeQuery();
		
		if(rs.next())
		{
			return true;
		}
		
		return false;
			
	}
	
	public boolean addUser(Account user) throws NamingException, SQLException
	{
		
		if(!(userExists(user)))
		{
			//DB Connection
			String sql = "insert into users(firstname,lastname,username,email,password) values(?,?,?,?,?)"; //our query
			Connection dbconn = DBConnection.getConnection();
			PreparedStatement instr = dbconn.prepareStatement(sql);
			instr.setString(1, user.getFirstName());
			instr.setString(2, user.getLastName());
			instr.setString(3, user.getUserName());
			instr.setString(4, user.getEmail());
			instr.setString(5, user.getPassword());
			@SuppressWarnings("unused")
			int row = instr.executeUpdate();
			return true;
		}
		
		return false;
	}
	
	public boolean isAdmin(String username) throws NamingException, SQLException
	{
		String sql = "SELECT * FROM users where username=?";
		Connection dbconn = DBConnection.getConnection();
		PreparedStatement instr = dbconn.prepareStatement(sql);
		instr.setString(1, username);
		ResultSet rs = instr.executeQuery();
		
		rs.next();
		
		return rs.getBoolean("isAdmin");
	}
	
	public int getUserId(String username) throws SQLException, NamingException
	{
		String sql = "SELECT id FROM users where username=?";
		Connection dbconn = DBConnection.getConnection();
		PreparedStatement instr = dbconn.prepareStatement(sql);
		instr.setString(1, username);
		ResultSet rs = instr.executeQuery();
		
		rs.next();
		
		return rs.getInt("id");
	}
	
}
