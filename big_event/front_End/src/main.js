import './assets/main.scss'
import router from "@/router";
import {createApp} from "vue";
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import App from './App.vue'
import {createPinia} from 'pinia'
//导入持久化插件
import {createPersistedState} from 'pinia-persistedstate-plugin'
import locale from 'element-plus/dist/locale/zh-cn'


const persist = createPersistedState()
const pinia = createPinia()
const app = createApp(App);
//app.use(ElementPlus);
app.use(ElementPlus, {locale})
app.use(router)
app.use(pinia)
//pinia使用持久化插件
pinia.use(persist)
app.mount('#app')

