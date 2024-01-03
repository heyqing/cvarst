<template>
  <view class="my-userinfo-container">
    <!-- 头像昵称区域 -->
    <view class="top-box">
      <image :src="userinfo.avatarUrl" class="avatar"></image>
      <view class="nickname">{{userinfo.nickName}}</view>
    </view>

    <!-- 面板区域 -->
    <view class="panel-list">
      <!-- panel主体区域 -->
      <view class="panel">
        <!-- 第一个面板 -->
        <navigator url="/subpkg/person_like/person_like">
          <view class="panel-item">
            <text class="panel-text">我的喜欢</text>
            <van-icon name="arrow" />
          </view>
        </navigator>
      </view>
      <view class="panel">
        <!-- 第二个面板 -->
        <navigator url="/subpkg/person_data/person_data">
          <view class="panel-item">
            <text class="panel-text">我的数据</text>
            <van-icon name="arrow" />
          </view>
        </navigator>
      </view>
      <view class="panel">
        <!-- 第三个面板 -->
        <navigator url="/subpkg/about_us/about_us">
          <view class="panel-item">
            <text class="panel-text">关于我们</text>
            <van-icon name="arrow" />
          </view>
        </navigator>
      </view>
      <view class="panel">
        <!-- 第四个面板 -->
        <view class="panel-item" @click="logout()">
          <text class="panel-text">退出登录</text>
          <van-icon name="arrow" />
        </view>
      </view>
    </view>
  </view>
  </view>
</template>

<script>
  import {
    mapState,
    mapMutations
  } from 'vuex'
  export default {
    name: "my-userinfo",
    data() {
      return {

      };
    },
    computed: {
      ...mapState('m_user', ['userinfo','token']),
      ...mapState('m_myLike', ['articleLikeList']),
      ...mapState('m_camera', ['photoLikeList'])
    },
    methods: {
      ...mapMutations('m_user', ['updateUserInfo', 'updateToken']),
      ...mapMutations('m_myLike', ['updataList','setList','addLike']),
      ...mapMutations('m_camera', ['updataDataList']),
      // 退出登录
      async logout() {
        // 询问用户是否退出登录
        const [err, succ] = await uni.showModal({
          title: '提示',
          content: '确认退出登录吗？'
        }).catch(err => err)

        if (succ && succ.confirm) {
          // 用户确认了退出登录的操作
          // 需要清空 vuex 中的 userinfo、token 
          //提交我的数据给后端进行储存
          const query = {
            //文章
            articleList: this.articleLikeList,
            //数据
            photoList: this.photoLikeList,
            //token
            token: this.token
          }
          const {
            data: res
          } = await uni.$http.post('/logout', query)
          if (res.code !== 200) return uni.$showMsg('退出登录失败！！！')
          this.updateUserInfo({})
          this.updateToken('')
          this.updataList()
          this.updataDataList()
        }
      }

    }
  }
</script>

<style lang="scss">
  .my-userinfo-container {
    height: 100%;
    // 为整个组件的结构添加浅灰色的背景
    background-color: #f4f4f4;

    .top-box {
      height: 400rpx;
      background-color: #1781B5;
      display: flex;
      flex-direction: column;
      align-items: center;
      justify-content: center;

      .avatar {
        display: block;
        width: 90px;
        height: 90px;
        border-radius: 45px;
        border: 2px solid white;
        box-shadow: 0 1px 5px black;
      }

      .nickname {
        color: white;
        font-weight: bold;
        font-size: 16px;
        margin-top: 10px;
      }
    }
  }

  .panel-list {
    padding: 0 8px;
    position: relative;
    top: 10px;

    .panel {
      background-color: white;
      border-radius: 3px;
      margin-bottom: 4rpx;

      .panel-item {
        height: 45px;
        display: flex;
        justify-content: space-between;
        align-items: center;
        font-size: 15px;
        padding: 0 10px;

        .panel-text {
          color: #272b22;
          font-size: 20px;
          font-family: emoji;
        }
      }
    }
  }
</style>