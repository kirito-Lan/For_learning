package com.kirito.management.service.impl;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.kirito.management.mapper.CategoryMapper;
import com.kirito.management.pojo.Category;
import com.kirito.management.service.CategoryService;
import com.kirito.management.utils.ThreadLocalUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public boolean addCategory(Category category) {
        DecodedJWT decodeJwt = ThreadLocalUtils.get();
        //System.out.println(decodeJwt.getClaims().get("id").asInt());
        category.setCreateUser(Integer.valueOf(decodeJwt.getClaim("id").asString()));
        category.setCreateTime(LocalDateTime.now());
        category.setUpdateTime(LocalDateTime.now());
        return categoryMapper.addCategory(category)!=0;
    }

    @Override
    public List<Category> getCategories() {
        DecodedJWT decodedJWT = ThreadLocalUtils.get();
        return categoryMapper.getCategories(decodedJWT.getClaim("id").asString());
    }

    @Override
    public Category getCategoryById(String id) {

        return categoryMapper.getCategoryById(id);
    }

    @Override
    public boolean modifyCategory(Category category) {
        category.setUpdateTime(LocalDateTime.now());
        return categoryMapper.modifyCategory(category)!=0;
    }

    @Override
    public boolean deleteCategory(String id) {
        return categoryMapper.deleteCategory(id)!=0;
    }
}
