package controllers;

import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.List;

import javax.management.InstanceNotFoundException;
import javax.management.MBeanException;
import javax.management.MBeanServer;
import javax.management.MBeanServerFactory;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import javax.management.ReflectionException;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import daos.ProductDAO;
import domain.Product;

/**
 * Servlet implementation class UploadController
 */
@WebServlet("/UploadController")
@MultipartConfig
public class UploadController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UploadController() {
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
		
		//UPLOADING FILE TO WEB APP DIRECTORY
	 	String UPLOAD_DIRECTORY="D:\\Eclipse projects\\WADLab5\\WebContent\\products"; //from web.xml, tomcat process has rw access to the path
	    final Part filePart = request.getPart("file");
	    String fileName = filePart.getSubmittedFileName(); //generate your own unique filename
	    Path uploadDir = Paths.get(UPLOAD_DIRECTORY);
	    try {
	    Files.createDirectory(uploadDir);}
	    catch (FileAlreadyExistsException fe) {}
	    Path uploadPath=Paths.get(uploadDir.toString(),fileName);
	    filePart.write(uploadPath.toString());

	    
	    //CREATING PRODUCT
	    String name = request.getParameter("name");
	    String type = request.getParameter("type");
	    String description = request.getParameter("description");
	    Double price = Double.parseDouble(request.getParameter("price"));
	    
	    //FORMATTING PATH TO PASS TO DB OBJECT
	    String imgPath = uploadPath.toString().replace("D:\\Eclipse projects\\WADLab5\\WebContent\\", "");
	    imgPath = imgPath.replace("\\", "/");
	    
	    Product p = new Product(name,type,description,price,imgPath);
	    
	    //Registering product to DB
	    try {
			ProductDAO.getInstance().addNewProduct(p);
		} catch (NamingException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
	    //We also need to update the product list within the servlet scope
	    List<Product> products = (List<Product>) request.getServletContext().getAttribute("products");
	    products.add(p);
	    request.getServletContext().setAttribute("products", products);

	    response.sendRedirect("AddProduct");
	    
		}

}
