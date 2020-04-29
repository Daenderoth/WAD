package controllers;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import daos.ProductDAO;
import domain.OrderItem;

/**
 * Servlet implementation class BuyController
 */
@WebServlet("/BuyController")
public class BuyController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BuyController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    @SuppressWarnings("unchecked")
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

		
		if(request.getSession(false) != null && request.getSession().getAttribute("USER")!=null)
		{
			
			try {
				boolean productExists = false;
				double total = 0;
				
				//ORDER ITEM PARAMETERS
				String name = request.getParameter("name");
				String description = request.getParameter("description");
				double price = Double.parseDouble(request.getParameter("price"));
				int quantity = Integer.parseInt(request.getParameter("quantity"));
				
				String imgPath;
				
				if(request.getParameter("imgPath")==null)
				{
					imgPath = ProductDAO.getInstance().getProductImg(name);
				}
				
				else 
				{
					imgPath = request.getParameter("imgPath");
				}
				
				OrderItem p = new OrderItem(name, description, price, imgPath, quantity);
				
				List<OrderItem> orderedProducts = (List<OrderItem>) request.getSession().getAttribute("orderedProducts");
				for(OrderItem o : orderedProducts)
				{
					if(name.equals(o.getName()))
					{
						o.setQuantity(o.getQuantity() + quantity);
						productExists = true;
						break;
					}
				}
				
				if(!productExists)
				{
					orderedProducts.add(p);
				}
				
				for(OrderItem o : orderedProducts)
				{
					total += o.getTotalPrice();
				}
				request.getSession().setAttribute("total", total);
				request.getSession().setAttribute("orderedProducts", orderedProducts);
				response.sendRedirect("Products");	
				
			} catch (NamingException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		else
		{
			response.sendRedirect("Login");
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
