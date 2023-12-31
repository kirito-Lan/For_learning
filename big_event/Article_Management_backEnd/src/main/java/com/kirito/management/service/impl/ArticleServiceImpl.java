package com.kirito.management.service.impl;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.kirito.management.mapper.ArticleMapper;
import com.kirito.management.pojo.Article;
import com.kirito.management.pojo.PageBean;
import com.kirito.management.service.ArticleService;
import com.kirito.management.utils.ThreadLocalUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ArticleServiceImpl implements ArticleService {
    @Autowired
    private ArticleMapper articleMapper;

    @Override
    public PageBean<Article> getPage(Integer pageNum, Integer pageSize, Integer categoryId, String state) {
        PageBean<Article> pageBean = new PageBean<>();
        //使用PageHelper
        PageHelper.startPage(pageNum, pageSize);
        //从ThreadLocal获取id
        DecodedJWT decodedJWT = ThreadLocalUtils.get();
        Integer userid = Integer.valueOf(decodedJWT.getClaim("id").asString());
        List<Article> articles = articleMapper.getPage(userid, categoryId, state);
        Page<Article> page = (Page<Article>) articles;
        //把数据填充到PageBean中
        pageBean.setTotal(page.getTotal());
        pageBean.setItems(page.getResult());
        return pageBean;
    }

    @Override
    public boolean addArticle(Article article) {
        DecodedJWT decodedJWT = ThreadLocalUtils.get();
        //通过JWT获取用户id
        Integer id = Integer.valueOf(decodedJWT.getClaim("id").asString());
        //设置创建人id
        article.setCreateUser(id);
        //设置时间
        article.setCreateTime(LocalDateTime.now());
        article.setUpdateTime(LocalDateTime.now());
        return articleMapper.addArticle(article) != 0;
    }


    @Override
    public Article getArticleInfo(String id) {
        return articleMapper.getArticleInfo(id);
    }

    public boolean deleteArticleById(String id) {
        return articleMapper.deleteArticleById(id) != 0;
    }

    @Override
    public boolean modifyArticle(Article article) {
        article.setUpdateTime(LocalDateTime.now());
        return articleMapper.modifyArticle(article) != 0;
    }

    //全部的查询
    @Override
    public PageBean<Article> selectArticle(Integer pageNum, Integer pageSize, Integer categoryId, String title) {
        PageBean<Article> pageBean = new PageBean<>();
        //使用PageHelper
        PageHelper.startPage(pageNum, pageSize);
        List<Article> articles = articleMapper.selectArticle(categoryId, title);
        Page<Article> page = (Page<Article>) articles;
        //把数据填充到PageBean中
        pageBean.setTotal(page.getTotal());
        pageBean.setItems(page.getResult());
        return pageBean;
    }


}
