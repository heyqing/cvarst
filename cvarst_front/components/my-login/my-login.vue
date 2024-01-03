<template>
  <view class="login-container">
    <!-- 提示登录的图标 -->
    <uni-icons type="contact-filled" size="100" color="#AFAFAF"></uni-icons>
    <!-- 登录按钮 -->
    <button type="primary" class="btn-login" @click="getUserProfile">一键登录</button>
    <!-- 登录提示 -->
    <view class="tips-text">登录后尽享更多权益</view>
  </view>
</template>

<script>
  // 1. 按需导入 mapMutations 辅助函数
  import {
    mapMutations
  } from 'vuex'
  export default {
    name: "my-login",
    data() {
      return {
      };
    },
    methods: {
      // 2. 调用 mapMutations 辅助方法，把 m_user 模块中的 updateUserInfo 映射到当前组件中使用
      ...mapMutations('m_user', ['updateUserInfo', 'updateToken']),
      // 获取微信用户的基本信息
      getUserProfile() {
        uni.getUserProfile({
          desc: '请求授权', // 声明获取用户个人信息后的用途，后续会展示在弹窗中，请谨慎填写
          success: (res) => {
            // 这部分是更新用户信息的
            this.updateUserInfo(res.userInfo)
            this.getToken(res)
          },
          fail: (res) => {
            return uni.$showMsg("已取消登录授权")
          }
        })
        uni.getLocation({
          type: 'wgs84',
          altitude: true,
          isHighAccuracy: true,
          success: (res) => {
            console.log(res)
          },
          fail: (res) => {
            return uni.$showMsg("获取地理位置失败")
          }
        })
      },
      // 调用登录接口，换取永久的 token
      async getToken(info) {
     // 调用微信登录接口
     const [err, res] = await uni.login({
       provider: 'weixin',
       onlyAuthorize: true
     }).catch(err => err)
     // 判断是否 uni.login() 调用失败
     if (err || res.errMsg !== 'login:ok') return uni.$showError('登录失败！')
        const query = {
          code: res.code,
          encryptedData: info.encryptedData,
          iv: info.iv,
          rawData: info.rawData,
          signature: info.signature,
        }
        // 换取 token
        const {
          data: loginResult
        } = await uni.$http.post('/login', query)
        if (loginResult.code !== 200) return uni.$showMsg('登录失败！')
        // 2. 更新 vuex 中的 token
        this.updateToken(loginResult.data)
      }
    },
  }
</script>

<style lang="scss">
  .login-container {
    // 登录盒子的样式
    height: 750rpx;
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
    background-color: #f8f8f8;
    position: relative;
    overflow: hidden;

    // 绘制登录盒子底部的半椭圆造型
    &::after {
      content: ' ';
      display: block;
      position: absolute;
      width: 100%;
      height: 40px;
      left: 0;
      bottom: 0;
      background-color: white;
      border-radius: 100%;
      transform: translateY(50%);
    }

    // 登录按钮的样式
    .btn-login {
      width: 90%;
      border-radius: 100px;
      margin: 15px 0;
      background-color: #1781B5;
    }

    // 按钮下方提示消息的样式
    .tips-text {
      font-size: 12px;
      color: gray;
    }
  }
</style>