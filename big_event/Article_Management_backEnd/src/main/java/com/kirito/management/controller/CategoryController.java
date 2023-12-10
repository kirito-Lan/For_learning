package com.kirito.management.controller;


import com.kirito.management.pojo.Category;
import com.kirito.management.pojo.Result;
import com.kirito.management.service.CategoryService;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    //添加分类
    @PostMapping
    public Result<String> addCategory(@RequestBody @Validated(Category.addCategory.class) Category category) {
        return categoryService.addCategory(category) ? Result.success() : Result.fail("添加失败!请重试");
    }

    //获取全部分类
    @GetMapping
    public Result<List<Category>> getCategory() {
        return Result.success(categoryService.getCategories());
    }

    //获取单个类别
    @GetMapping("/getCategory")
    public Result<Category> getCategoryById(String id) {
        return Result.success(categoryService.getCategoryById(id));
    }

    @PutMapping
    public Result<String> modifyCategory(@RequestBody @Validated(Category.modifyCategory.class) Category category) {
        return categoryService.modifyCategory(category) ? Result.success() : Result.fail("修改失败请重试");
    }

    @DeleteMapping
    public Result<String> deleteCategory(@RequestParam String id){
        return categoryService.deleteCategory(id) ? Result.success() : Result.fail("修改失败请重试");
    }


}
