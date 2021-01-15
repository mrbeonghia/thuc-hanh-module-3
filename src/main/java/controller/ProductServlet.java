package controller;

import dao.CategoryDAO;
import dao.ProductDAO;
import model.Category;
import model.Product;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.rmi.ServerException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(urlPatterns = "/product")
public class ProductServlet extends HttpServlet {
    private ProductDAO productDAO;
    private CategoryDAO categoryDAO;
    @Override
    public void init() throws ServletException {
        productDAO  = new ProductDAO();
        categoryDAO = new CategoryDAO();

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServerException, IOException, ServletException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("utf-8");
        String action = request.getParameter("action");
        if (action == null) {
            action = "";
        }
        try {
            switch (action) {
                case "create":
                    insertProduct(request, response);
                    break;
                case "edit":
                    updateProduct(request, response);
                    break;
                case "search":
                    searchProduct(request,response);
                    break;

            }
        } catch (SQLException ex) {
            throw new ServletException(ex);
        }
    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("utf-8");
        if (action == null) {
            action = "";
        }

        try {
            switch (action) {
                case "create":
                    showNewForm(request, response);
                    break;
                case "edit":
                    showEditForm(request, response);
                    break;
                case "delete":
                    deleteProduct(request, response);
                    break;
                case "search":
                    searchProduct(request,response);
                    break;

                default:
                    listProducts(request, response);
                    break;
            }
        } catch (SQLException ex) {
            throw new ServletException(ex);
        }

    }

    private void updateProduct(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        String name = request.getParameter("name");
        long price = Long.parseLong(request.getParameter("price"));
        int quantity = Integer.parseInt(request.getParameter("quantity"));
        String color = request.getParameter("color");
        String description = request.getParameter("description");
        int categoryId = Integer.parseInt(request.getParameter("category"));
        Product product = new Product(name,price,quantity,color,description,categoryId);
        productDAO.updateProduct(product);
        request.setAttribute("message","success");
        RequestDispatcher dispatcher = request.getRequestDispatcher("edit.jsp");
        dispatcher.forward(request,response);

    }

    private void insertProduct(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        String name = request.getParameter("name");
        long price = Long.parseLong(request.getParameter("price"));
        int quantity= Integer.parseInt(request.getParameter("quantity"));
        String color = request.getParameter("color");
        String desc = request.getParameter("description");
        int cateId = Integer.parseInt(request.getParameter("category"));
        Product product = new Product(name,price,quantity,color,desc,cateId);
        productDAO.insertProduct(product);
            request.setAttribute("message", "successful");
            RequestDispatcher dispatcher = request.getRequestDispatcher("insert.jsp");
            dispatcher.forward(request,response);
    }

    private void listProducts(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Product> listProduct = productDAO.selectAllProducts();
        req.setAttribute("listProduct",listProduct);
        RequestDispatcher dispatcher = req.getRequestDispatcher("listProduct.jsp");
        dispatcher.forward(req,resp);
    }



    private void deleteProduct(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        int id = Integer.parseInt(request.getParameter("id"));
        productDAO.deleteProduct(id);
        List<Product> listProduct = productDAO.selectAllProducts();
        request.setAttribute("listProduct", listProduct);
        RequestDispatcher dispatcher = request.getRequestDispatcher("listProduct.jsp");
        dispatcher.forward(request, response);

    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Product existingProduct = productDAO.selectProduct(id);
        request.setAttribute("product", existingProduct);
        RequestDispatcher dispatcher = request.getRequestDispatcher("edit.jsp");
        request.setAttribute("user", existingProduct);
        dispatcher.forward(request, response);
    }

    private void showNewForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ArrayList<Category> categories = categoryDAO.selectAllCategories();
        request.setAttribute("allCategories",categories);
        RequestDispatcher dispatcher = request.getRequestDispatcher("insert.jsp");
        dispatcher.forward(request,response);
    }

    private void searchProduct(HttpServletRequest request, HttpServletResponse response) {
    }
}
