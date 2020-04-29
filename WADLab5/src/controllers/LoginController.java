package controllers;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import daos.OrderDAO;
import daos.UserDAO;
import domain.Order;

/**
 * Servlet implementation class LoginController
 */
@WebServlet("/LoginController")
public class LoginController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		List<String> errors = new ArrayList<String>();
		
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        
        try {
			if(!UserDAO.getInstance().userExists(username, password))
			{
				errors.add("Invalid username or password.");
				request.setAttribute("errors", errors);
				request.getRequestDispatcher("Login").forward(request, response);
			}
			else
			{
				boolean isAdmin = UserDAO.getInstance().isAdmin(username);
				int userID = UserDAO.getInstance().getUserId(username);
				List<Order> orders = OrderDAO.getInstance().getOrders(userID);
				request.getSession().setAttribute("orders", orders);
				request.getSession().setAttribute("userID", userID);
				request.getSession().setAttribute("isAdmin", isAdmin);
				request.getSession().setAttribute("USER",username);
				request.getRequestDispatcher("ProductController").forward(request, response);
			}
		} catch (SQLException | NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}

}
