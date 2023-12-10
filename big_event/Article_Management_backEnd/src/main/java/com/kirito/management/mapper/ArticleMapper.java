package com.kirito.management.mapper;

import com.kirito.management.pojo.Article;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ArticleMapper {

    //添加文章
    @Insert("insert into big_event.article " +
            "(title, content, cover_img, state, category_id, create_user, create_time, update_time)" +
            " VALUES" +
            "(#{title},#{content},#{coverImg},#{state},#{categoryId},#{createUser},#{createTime},#{updateTime}) ")
    int addArticle(Article article);

    //需要书写动态SQL
    List<Article> getPage(Integer userid, Integer categoryId, String state);

    //通过id获取文章信息
    @Select("select * from article where id=#{id}")
    Article getArticleInfo(String id);

    @Delete("delete from article where id=#{id}")
    int deleteArticleById(String id);
    @Update("update big_event.article set " +
            "title=#{title} ,content=#{content},cover_img=#{coverImg}," +
            "state=#{state},category_id=#{categoryId},update_time=#{updateTime} where id=#{id}")
    int modifyArticle(Article article);
}
