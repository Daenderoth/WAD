package views;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import domain.Product;

/**
 * Servlet implementation class ProductPage
 */
@WebServlet(name="product", value="/product")
public class ProductPage extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ProductPage() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub.
		
		String prodName = request.getParameter("name");
		List<Product> products = (List<Product>) request.getServletContext().getAttribute("products");
		Product selectedProd = new Product();
		for(Product p: products)
		{
			if(p.getName().equals(prodName))
			{
				selectedProd = p;
			}
		}
		request.setAttribute("selectedProd", selectedProd);
		request.getRequestDispatcher("/WEB-INF/ProductPage.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
