// 1. 导入 Vue 和 Vuex
import Vue from 'vue'
import Vuex from 'vuex'
import moduleUser from '@/store/user.js'
import moduleCamera from '@/store/camera.js'
import moduleMyLike from '@/store/mylike.js'

// 2. 将 Vuex 安装为 Vue 的插件
Vue.use(Vuex)

// 3. 创建 Store 的实例对象
const store = new Vuex.Store({
  // TODO：挂载 store 模块
  modules: {
    'm_user': moduleUser,
    'm_camera': moduleCamera,
    'm_myLike': moduleMyLike
  },
})

// 4. 向外共享 Store 的实例对象
export default store