package dao;

import model.Category;

import java.sql.*;
import java.util.ArrayList;

public class CategoryDAO implements ICategoryDAO{
    private static String jdbcURL = "jdbc:mysql://localhost:3306/productManager?useSSL=false";
    private static String username = "root";
    private static String password = "1122";
    public static final String GET_ALL_CATEGORIES = "select * from category";

    public static Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(jdbcURL, username, password);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    @Override
    public ArrayList<Category> selectAllCategories() {
        ArrayList<Category> categories = null;
        try {
            Connection connection = getConnection();
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(GET_ALL_CATEGORIES);
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                Category category = new Category(id, name);
                categories.add(category);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return categories;
    }
}
