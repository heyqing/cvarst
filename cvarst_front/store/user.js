export default {
  // 开启命名空间
  namespaced: true,

  // state 数据
  state: () => ({
    // 登录成功之后的 token 字符串
     token: uni.getStorageSync('token') || '',
    // 用户的基本信息
    userinfo: JSON.parse(uni.getStorageSync('userinfo') || '{}')
  }),

  // 方法
  mutations: {
    // 更新用户的基本信息
    updateUserInfo(state, userinfo) {
      state.userinfo = userinfo
      // 通过 this.commit() 方法，调用 m_user 模块下的 saveUserInfoToStorage 方法，将 userinfo 对象持久化存储到本地
      this.commit('m_user/saveUserInfoToStorage')
    },

    // 将 userinfo 持久化存储到本地
    saveUserInfoToStorage(state) {
      uni.setStorageSync('userinfo', JSON.stringify(state.userinfo))
    },
    
     // 更新 token 字符串
      updateToken(state, token) {
        state.token = token
        // 通过 this.commit() 方法，调用 m_user 模块下的 saveTokenToStorage 方法，将 token 字符串持久化存储到本地
        this.commit('m_user/saveTokenToStorage')
      },
    
      // 将 token 字符串持久化存储到本地
      saveTokenToStorage(state) {
        uni.setStorageSync('token', state.token)
      }
  },
  // 数据包装器
  getters: {},
}