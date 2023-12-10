package com.kirito.management.controller;


import com.kirito.management.pojo.Article;
import com.kirito.management.pojo.PageBean;
import com.kirito.management.pojo.Result;
import com.kirito.management.service.ArticleService;
import jakarta.validation.Valid;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/article")
public class ArticleController {
    @Autowired
    private ArticleService articleService;

    @PostMapping
    public Result<String> addArticle(@RequestBody @Validated Article article) {
        return articleService.addArticle(article)
                ? Result.success()
                : Result.fail("failed to add the article please try again");
    }

    //分页查询
    @GetMapping
    public Result<PageBean<Article>> getPage(
            Integer pageNum,
            Integer pageSize,
            @RequestParam(required = false) Integer categoryId,
            @RequestParam(required = false) String state
    ) {

        PageBean<Article> pageBean = articleService.getPage(pageNum, pageSize, categoryId, state);
        return pageBean != null
                ? Result.success(pageBean)
                : Result.fail("查询失败");
    }

    @GetMapping("/detail")
    public Result<Article> getArticleById(String id) {

        return Result.success(articleService.getArticleInfo(id));

    }

    @DeleteMapping
    public Result<String>deleteArticle(String id){
        return articleService.deleteArticleById(id)
                ?Result.success()
                :Result.fail("删除失败,请重试");
    }

    @PutMapping
    public Result<String>updateArticle(@RequestBody @Validated Article article){
        return articleService.modifyArticle(article)
                ?Result.success()
                :Result.fail("更新失败,请重试");
    }


}
