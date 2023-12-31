<script setup>


import {ref} from 'vue'

//文章分类数据模型
const categories = ref([
  {
    "id": 3,
    "categoryName": "美食",
    "categoryAlias": "my",
    "createTime": "2023-09-02 12:06:59",
    "updateTime": "2023-09-02 12:06:59"
  },
  {
    "id": 4,
    "categoryName": "娱乐",
    "categoryAlias": "yl",
    "createTime": "2023-09-02 12:08:16",
    "updateTime": "2023-09-02 12:08:16"
  },
  {
    "id": 5,
    "categoryName": "军事",
    "categoryAlias": "js",
    "createTime": "2023-09-02 12:08:33",
    "updateTime": "2023-09-02 12:08:33"
  }
])

//用户搜索时选中的分类id
const categoryId = ref('')

//用户搜索时输入文章标题
const title = ref('')
//默认图片
const defaultImg = ref('https://kirito-oss.oss-cn-shenzhen.aliyuncs.com/42edcc8d-2ac5-4448-a3e7-b7fca9e4e2c9.jpg')

//文章列表数据模型
const articles = ref([
  {
    "id": 5,
    "title": "陕西旅游攻略",
    "content": "兵马俑,华清池,法门寺,华山...爱去哪去哪...",
    "coverImg": "https://big-event-gwd.oss-cn-beijing.aliyuncs.com/9bf1cf5b-1420-4c1b-91ad-e0f4631cbed4.png",
    "state": "草稿",
    "categoryId": 2,
    "createTime": "2023-09-03 11:55:30",
    "updateTime": "2023-09-03 11:55:30"
  },
  {
    "id": 5,
    "title": "陕西旅游攻略",
    "content": "兵马俑,华清池,法门寺,华山...爱去哪去哪...",
    "coverImg": "https://big-event-gwd.oss-cn-beijing.aliyuncs.com/9bf1cf5b-1420-4c1b-91ad-e0f4631cbed4.png",
    "state": "草稿",
    "categoryId": 2,
    "createTime": "2023-09-03 11:55:30",
    "updateTime": "2023-09-03 11:55:30"
  },
  {
    "id": 5,
    "title": "陕西旅游攻略",
    "content": "兵马俑,华清池,法门寺,华山...爱去哪去哪...",
    "coverImg": "https://big-event-gwd.oss-cn-beijing.aliyuncs.com/9bf1cf5b-1420-4c1b-91ad-e0f4631cbed4.png",
    "state": "草稿",
    "categoryId": 2,
    "createTime": "2023-09-03 11:55:30",
    "updateTime": "2023-09-03 11:55:30"
  },
])

//分页条数据模型
const pageNum = ref(1)//当前页
const total = ref(20)//总条数
const pageSize = ref(10)//每页条数

//文章列表查询
import {
  articleCategoryListServiceV2, articleSelectService
} from '@/api/article.js'

const getArticleCategoryList = async () => {
  //获取所有分类
  let resultC = await articleCategoryListServiceV2();
  categories.value = resultC.data
}

//文章列表查询
const getArticleList = async () => {
  let params = {
    pageNum: pageNum.value,
    pageSize: pageSize.value,
    categoryId: categoryId.value ? categoryId.value : null,
    title: title.value ? title.value : null
  }
  let result = await articleSelectService(params);
  //渲染列表数据
  articles.value = result.data.items
  loading.value = false
  //渲染总条数
  total.value = result.data.total
}
getArticleCategoryList();
getArticleList()

//当每页条数发生了变化，调用此函数
const onSizeChange = (size) => {
  pageSize.value = size
  getArticleList()
}
//当前页码发生变化，调用此函数
const onCurrentChange = (num) => {
  pageNum.value = num
  getArticleList()
}
import router from "@/router";

const gotoLogin = () => {
  router.push('/login')
}

const loading = ref(true)
const drawer = ref(false)
const selectArticle = ref("")

const openDrawer = (Article) => {
  selectArticle.value = Article;
  drawer.value = true;
}

</script>

<template>

  <div class="common-layout background">
    <el-card style="height: 105px;">
      <div style="display: flex; justify-content: space-between; align-items: center;">
        <span><strong>Welcome!</strong> </span>
        <div class="extra">
          <el-button type="primary" @click="gotoLogin">登录</el-button>
        </div>
      </div>
      <!-- 搜索表单 -->
      <el-form inline>

        <el-form-item label="文章分类:">
          <el-select placeholder="请选择分类" v-model="categoryId">
            <el-option
                v-for="c in categories"
                :key="c.id"
                :label="c.categoryName"
                :value="c.id">
            </el-option>
          </el-select>
        </el-form-item>

        <el-form-item label="文章标题:">
          <el-input v-model="title" placeholder="请输入"/>
        </el-form-item>

        <el-form-item>
          <el-button type="primary" @click="getArticleList">搜索</el-button>
          <el-button @click="categoryId='';title='';getArticleList()">重置</el-button>
        </el-form-item>
      </el-form>
    </el-card>

    <!--      </el-header>-->
    <!--      <el-main>-->

    <el-space direction="horizontal" alignment="center" style="margin: 3% 15%; flex-wrap: wrap ;row-gap: 20px">
      <el-skeleton v-for="(article, index) in articles" :key="index" style="width: 250px" :loading="loading"
                   animated>
        <template #template>
          <el-skeleton-item variant="image" style="width: 240px; height: 240px"/>
          <div style="padding: 14px">
            <el-skeleton-item variant="h3" style="width: 50%"/>
            <div style="
            display: flex;
            align-items: center;
            justify-items: space-between;
            margin-top: 16px;
            height: 16px;
          ">
              <el-skeleton-item variant="text" style="margin-right: 16px"/>
              <el-skeleton-item variant="text" style="width: 30%"/>
            </div>
          </div>
        </template>

        <template #default>
          <el-card :body-style="{ padding: '8px', marginBottom: '0px' }">
            <img :src="article.coverImg?article.coverImg:defaultImg" class="image" style="width: 240px"/>
            <div style="padding: 14px; text-align: center">
              <div class="bottom card-header">
                <div class="time">{{ article.createTime }}</div>
                <el-button text class="button" @click="openDrawer(article)" style="font-size: 18px;">
                  {{ article.title }}
                </el-button>
              </div>
            </div>
          </el-card>
        </template>
      </el-skeleton>

    </el-space>

    <!-- 分页条 -->
    <el-pagination v-model:current-page="pageNum" v-model:page-size="pageSize" :page-sizes="[10,20,30]"
                   layout="jumper, total, sizes, prev, pager, next" background :total="total"
                   @size-change="onSizeChange"
                   @current-change="onCurrentChange" style="margin-top: 20px; justify-content: flex-end"/>
    <!-- 回到顶部 -->
    <el-backtop :right="100" :bottom="100"/>
    <!--      </el-main>-->
    <!--    </el-container>-->
  </div>

  <!-- 抽屉 -->
  <el-drawer
      v-model="drawer"
      :title="selectArticle ? selectArticle.title : ''"
      direction="ttb"
  >
    <span class="content" v-html="selectArticle ? selectArticle.content : ''"></span>
  </el-drawer>


</template>


<style lang="scss" scoped>

.background {
  background-image: url("https://webgradients.com/public/webgradients_png/008%20Rainy%20Ashville.png");
  background-size: cover;
  z-index: 9999;
}

</style>