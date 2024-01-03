export default {
  // 开启命名空间
  namespaced: true,

  // state 数据
  state: () => ({
    articleLikeList: JSON.parse(uni.getStorageSync('articleLikeList') || '[]'),
  }),

  // 方法
  mutations: {
    setList(state){
      state.articleLikeList = [...new Set(state.articleLikeList)]
      this.commit('m_myLike/saveToStorage')
    },
    addLike(state,articleId){
      state.articleLikeList.push(articleId)
      this.commit('m_myLike/saveToStorage')
    },
    deleteLike(state,articleId){
      state.articleLikeList = state.articleLikeList.filter(item => item !== articleId)
      this.commit('m_myLike/saveToStorage')
    },
    updataList(state){
      //清空
      state.articleLikeList.length = 0
      this.commit('m_myLike/saveToStorage')
    },
    saveToStorage(state) {
      uni.setStorageSync('articleLikeList', JSON.stringify(state.articleLikeList))
    },
  },
  // 数据包装器
  getters: {},
}