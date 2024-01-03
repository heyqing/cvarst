<template>
  <view class="view-a">
    <van-dialog id="van-dialog" />
    <view class="heyqing">
      <image :src="`http://coderfun.fun/todo5.jpg`"></image>
    </view>
    <view class="text-view">
      点击下方的相机开启您的地理之旅
    </view>
    <view class="icon-view">
      <image  class="camera" :src="`http://coderfun.fun/todo2.png`" @click="checkUserAndTakePhoto()"></image>
    </view>
    <view class="bac-img">
      <image :src="`http://coderfun.fun/todo.png`" class="vr"></image>
    </view>
  </view>
</template>

<script>
  import Dialog from '../../wxcomponents/vant/dialog/dialog';
  import {
    mapMutations,
    mapState
  } from 'vuex'
  export default {
    name: "my-camera",
    data() {
      return {};
    },
    computed: {
      ...mapState('m_user', ['token'])
    },
    methods: {
      ...mapMutations('m_camera', ['takePhoto']),
      async checkUserAndTakePhoto() {
        //token为空
        if (!this.token) {
          //提示未登录 
          Dialog.alert({
            message: '您还未登录！',
          }).then(() => {
            
          });
        } else {
          console.log(this.token)
          //token不合法
          const {
            data: res
          } = await uni.$http.post('/login/checkToken', this.token)
          if (res.code !== 200) return uni.$showMsg('登录已过期，请重新登录！')
          //调用相机
          this.takePhoto()
        }

      }
    }
  }
</script>

<style lang="scss">
  .view-a{
    background-color: #fff;
  }
  .heyqing {
    width: 100%;
    height: 350rpx;

    image {
      width: 100%;
      height: 350rpx;
    }
  }

  .text-view {
    margin: 50rpx;
    justify-content: center;
    text-align: center;
  }

  .icon-view {
    height: 100rpx;
    display: flex;
    justify-content: center;
    .camera {
      height: 100rpx;
      width: 100rpx;
    }

    // 绘制登录盒子底部的半椭圆造型
    &::after {
      content: ' ';
      display: block;
      position: absolute;
      width: 100%;
      height: 40px;
      left: 0;
      bottom: 0;
      background-color: #efefef;
      border-radius: 100%;
      transform: translateY(50%);
    }
  }
  .vr {
    height: 625rpx;
    width: 100%;
  }
</style>