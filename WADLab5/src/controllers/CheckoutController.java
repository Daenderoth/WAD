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
import domain.OrderItem;

/**
 * Servlet implementation class CheckoutController
 */
@WebServlet("/CheckoutController")
public class CheckoutController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CheckoutController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@SuppressWarnings("unchecked")
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//When checkout is clicked
		if(request.getParameter("checkout")!=null)
		{
			List<OrderItem> orderedProducts = (List<OrderItem>) request.getSession().getAttribute("orderedProducts");
			String customerName = (String) request.getSession().getAttribute("USER");
			
			try {
				
				OrderDAO.getInstance().registerOrder(customerName, orderedProducts);
				
				//We must empty the cart after order registration
				orderedProducts = new ArrayList<OrderItem>();
				request.getSession().setAttribute("orderedProducts", orderedProducts);
				
				response.sendRedirect("OrderSuccessful");
			} catch (NamingException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		//When remove is clicked
		else
		{
			String prodName = request.getParameter("prodName");
			List<OrderItem> orderedProducts = (List<OrderItem>) request.getSession().getAttribute("orderedProducts");
			Double total = (Double) request.getSession().getAttribute("total");
			for(OrderItem o : orderedProducts)
			{
				if(o.getName().equals(prodName))
				{
					orderedProducts.remove(o);
					total -= o.getTotalPrice();
					break;
				}
			}
			
			request.getSession().setAttribute("orderedProducts", orderedProducts);
			request.getSession().setAttribute("total", total);
			response.sendRedirect("Cart");
			
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
