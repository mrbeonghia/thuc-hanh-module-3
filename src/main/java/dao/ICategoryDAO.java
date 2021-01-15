package dao;

import model.Category;

import java.util.ArrayList;

public interface ICategoryDAO {
    public ArrayList<Category> selectAllCategories();
}
