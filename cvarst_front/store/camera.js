export default {
  // 为当前模块开启命名空间
  namespaced: true,

  // 模块的 state 数据
  state: () => ({
    photoList: JSON.parse(uni.getStorageSync('photoList') || '[]'),
    photoLikeList: JSON.parse(uni.getStorageSync('photoLikeList') || '[]')
  }),

  // 模块的 mutations 方法
  mutations: {
    //调用相机
    takePhoto(state) {
      const ctx = uni.chooseMedia({
        count: 1,
        mediaType: ['image'],
        sourceType: ['camera', 'album'],
        camera: 'back',
        success: (res) => {
          //添加
          state.photoList.push(res.tempFiles[0])
          this.commit('m_camera/saveToStorage')
        }
      })
    },
    //添加喜欢
    addLikeList(state, photoPath) {
      state.photoLikeList.push(photoPath)
      this.commit('m_camera/saveToStorage')
    },
    deleteLikeList(state,photoPath){
      state.photoLikeList = state.photoLikeList.filter(item => item !== photoPath)
      this.commit('m_camera/saveToStorage')
    },
    updataDataList(state) {
      state.photoLikeList.length = 0
      this.commit('m_camera/saveToStorage')
    },
    //删除
    deletePhoto(state, index) {
      state.photoList.splice(index, 1)
      this.commit('m_camera/saveToStorage')
      uni.$showMsg('已删除')
    },
    //保存
    saveToStorage(state) {
      uni.setStorageSync('photoList', JSON.stringify(state.photoList))
      uni.setStorageSync('photoLikeList', JSON.stringify(state.photoLikeList))
    },

  },

  // 模块的 getters 属性
  getters: {},
  Component: {
    "van-dialog": "../wxcomponents/vant/dialog/index"
  }
}