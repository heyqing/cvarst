<template>
  <view class="all-view">
    <view class="up">
      <van-dialog id="van-dialog" />
      <swiper :current="index">
        <swiper-item v-for="(item,i) in photoList" :key="i">
          <view class="swiper-item">
            <image class="img-swiper" :src="item.tempFilePath"></image>
          </view>
        </swiper-item>
      </swiper>

      <view class="img-bom">
        <view class="img-front" @click="subIndex()">
          <van-icon size=16px name="arrow-left" />
          上一页
        </view>
        <text style="font-size: 40rpx; justify-content: flex;color: #21231e;font-style:italic;">第{{index + 1}}张</text>
        <view class="img-back" @click="addIndex()">
          下一页
          <van-icon size=16px name="arrow" />
        </view>
      </view>
      <view class="img-bom">
        <view class="delete-icon">
          <van-icon size=18px name="delete-o" @click="deletePhoto(index)" />
        </view>
        <view class="img-camera">
          <van-icon size=24px name="photograph" @click="takePhoto()" />
        </view>
        <view class="file-icon">
          <van-icon size=18px :name="iconName" @click="dataAdd(index)" />
        </view>
      </view>
      <view style="margin-top: 12rpx;">
        <van-button color="#1781b5" round size="large" loading-text="正在加载,请耐心等待..."
          @click="gotoDetail()">数据分析</van-button>
      </view>
    </view>

    <!-- 渲染其他样式 -->
    <view class="down">
      <view class="vr">
        <image :src="`http://coderfun.fun/todo3.png`" mode="widthFix"></image>
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
    name: "my-data-process",
    data() {
      return {
        iconName: 'like-o',
        index: 0,
        judge: 0,
        assets: {
          duration: 1000,
          delay: 500
        }
      };
    },
    computed: {
      ...mapState('m_camera', ['photoList', 'photoLikeList'])
    },
    methods: {
      ...mapMutations('m_camera', ['takePhoto', 'deletePhoto', 'addLikeList', 'deleteLikeList']),
      addIndex() {
        if (this.index === this.photoList.length - 1) return uni.$showMsg('已经是最后一张图片了，亲^_^')
        if (this.photoLikeList.includes(this.photoList[this.index + 1].tempFilePath)) this.iconName = 'like'
        else this.iconName = 'like-o'
        this.index += 1
      },
      subIndex() {
        if (this.index === 0) return uni.$showMsg('已经是第一张图片了，亲^_^')
        if (this.photoLikeList.includes(this.photoList[this.index - 1].tempFilePath)) this.iconName = 'like'
        else this.iconName = 'like-o'
        this.index -= 1
      },
      dataAdd(index) {
        this.iconName = this.iconName === 'like-o' ? 'like' : 'like-o'
        if (this.iconName === 'like') {
          this.addLikeList(this.photoList[index].tempFilePath)
          uni.$showMsg('添加成功^_^')
        } else {
          this.deleteLikeList(this.photoList[index].tempFilePath)
          uni.$showMsg('取消添加')
        }
      },
      gotoDetail() {
        uni.navigateTo({
          url: '/subpkg/photo_detail/photo_detail?photoPath=' + this.photoList[this.index].tempFilePath
        })
      }
    }
  }
</script>

<style lang="scss">
  .swiper-item {
    width: 100%;

    .img-swiper {
      height: 355rpx;
      width: 100%;
    }
  }

  .img-bom {
    background-color: #efefef;
    height: 50rpx;
    display: flex;
    justify-content: space-between;
    font-size: 20rpx;
    font-style: oblique;
    margin-left: 8rpx;
    margin-right: 8rpx;
  }

  van-icon {
    margin: 10rpx;
  }

  image {
    width: 100%;
    height: flex;
  }
</style>