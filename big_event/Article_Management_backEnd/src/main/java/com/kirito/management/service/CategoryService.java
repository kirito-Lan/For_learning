package com.kirito.management.service;

import com.kirito.management.pojo.Category;

import java.util.List;

public interface CategoryService {

    //添加分类
    boolean addCategory(Category category);

    //获取当前全部分类(用于用户登录界面)
    List<Category> getCategories();

    //通过id获取分类
    Category getCategoryById(String id);

    //修改分类
    boolean modifyCategory(Category category);
    //删除分类
    boolean deleteCategory(String id);

    //获取当前全部分类(用户无需登录)
    List<Category> getCategoriesAll();

}
