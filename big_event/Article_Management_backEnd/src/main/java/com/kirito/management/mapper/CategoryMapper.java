package com.kirito.management.mapper;

import com.kirito.management.pojo.Category;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CategoryMapper {

    @Insert("insert into big_event.category" +
            "(category_name, category_alias, create_user, create_time, update_time)" +
            "values(#{categoryName},#{categoryAlias},#{createUser},#{createTime},#{updateTime}) ")
    int addCategory(Category category);


    @Select("select id, category_name, category_alias, create_user, create_time, update_time from big_event.category where create_user=#{id}")
    List<Category> getCategories(String id);

    @Select("select id, category_name, category_alias, create_user, create_time, update_time from big_event.category where id=#{id}")
    Category getCategoryById(String id);

    @Update("update big_event.category set category_name=#{categoryName},category_alias=#{categoryAlias},update_time=#{updateTime} where id=#{id}")
    int modifyCategory(Category category);

    @Delete("delete from big_event.category where id=#{id}")
    int deleteCategory(String id);

    //这个是用于不要登录界面
    @Select("select id, category_name, category_alias, create_user, create_time, update_time from big_event.category")
    List<Category> getCategoriesAll();

}
