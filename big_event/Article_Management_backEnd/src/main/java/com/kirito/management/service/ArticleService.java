package com.kirito.management.service;

import com.kirito.management.pojo.Article;
import com.kirito.management.pojo.PageBean;

public interface ArticleService {

    //条件分页查询
    PageBean<Article> getPage(Integer pageNum, Integer pageSize, Integer categoryId, String state);

    //添加文章
    boolean addArticle(Article article);

    //通过id获取文章信息
    Article getArticleInfo(String id);

    //删除文章通过id
    boolean deleteArticleById(String id);

    //修改文章
    boolean modifyArticle(Article article);
}
