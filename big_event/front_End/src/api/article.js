//导入请求工具类
import request from '@/utils/request'


//文章分类列表查询
export const articleCategoryListService = () => {
    return request.get('/categories')
}

//文章分类列表查询(未登录版本)
export const articleCategoryListServiceV2 = () => {
    return request.get('/categories/getCategoryAll')
}

//添加文章分类
export const articleCategoryAddService = (categoryModel) => {
    return request.post('/categories', categoryModel)
}

//修改分类
export const articleCategoryUpdateService = (categoryModel) => {
    return request.put('/categories', categoryModel)
}


//删除分类
export const articleCategoryDeleteService = (id) => {
    return request.delete('/categories?id=' + id)
}

//文章列表查询
export const articleListService = (params) => {
    return request.get('/article', {params: params})
}


//添加文章
export const articleAddService = (articleModel) => {
    return request.post('/article', articleModel)
}

//删除文章
export const articleDeleteService = (id) => {
    return request.delete('/article', {params: {id}});
}

//修改文章
export const articleUpdateService = (articleModel) => {
    return request.put('article', articleModel)
}

//搜索文章
export const articleSelectService=(params)=>{
    return request.get('/article/selectArticle', {params: params})
}


// For 管理员
