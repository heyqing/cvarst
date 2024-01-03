<template>
  <view v-if="photoList.length === 0">
    <image class="zw-img" :src="`http://coderfun.fun/todo4.png`"></image>
  </view>
  <view v-else>
    <view class="floor-list">
      <van-dialog id="van-dialog" />
      <van-notify id="van-notify" />
      <!-- 楼层item -->
      <view v-for="(item,i) in photoList" :key="i">
        <view class="floor-img-box">
          <van-swipe-cell id="swipe-cell" right-width="65">
            <van-cell-group>
              <navigator class="floor-item" :url="'/subpkg/photo_detail/photo_detail?photoPath='+item">
                <image class="floor-img" :src="item"></image>
              </navigator>
            </van-cell-group>
            <view class="r-view" slot="right" @click="onClose(item)">删除</view>
          </van-swipe-cell>
        </view>
      </view>
    </view>
    <!-- 触底提示 -->
    <view class="onReachBottom-view">
      已经到底了哦 ^_^
    </view>
  </view>
</template>

<script>
  import Dialog from '../../wxcomponents/vant/dialog/dialog';
  import Notify from '../../wxcomponents/vant/notify/notify';
  import {
    mapState,
    mapMutations
  } from 'vuex';
  export default {
    data() {
      return {
        photoList: []
      };
    },
    computed: {
      ...mapState('m_camera', ['photoLikeList']),
      ...mapState('m_user', ['token'])
    },
    onLoad() {
      this.getPhotoList()
    },
    methods: {
      ...mapMutations('m_camera', ['deleteLikeList']),
      onClose(photoPath) {
        Dialog.confirm({
          message: '确定删除吗？',
        }).then(() => {
          //删除后端存储
          this.deletePhotoByPhotoPath(photoPath)
          //重新渲染
          for (let i = 0; i < this.photoList.length; i++) {
            if (this.photoList[i] === photoPath) {
              this.photoList.splice(i, 1)
            }
          }
          this.deleteLikeList(photoPath)
          //提示
          Notify({
            type: 'success',
            duration: 1000,
            message: '已删除'
          });
        }).catch(() => {
          Notify({
            type: 'warning',
            duration: 1000,
            message: '取消删除'
          });
        });
      },
      async deletePhotoByPhotoPath(photoPath) {
        const {
          data: res
        } = await uni.$http.post('/person/deleteData', photoPath)
        if (res.code !== 200) return uni.$showMsg()
      },
      async getPhotoList() {
        this.photoList = this.photoLikeList
        const {
          data: res
        } = await uni.$http.post('/person/allphoto', this.token)
        if (res.code !== 200) return uni.$showMsg('数据加载失败，请重新登录')
        this.photoList = [...this.photoList, ...res.data]
      }
    }
  }
</script>

<style lang="scss">
  .zw-img{
    margin-top: 42%;
    height: 700rpx;
    width: 100%;
  }
  .r-view {
    height: 335rpx;
    width: 65px;
    color: #fff;
    background-color: red;
    display: flex;
    justify-content: center;
    align-items: center;
  }

  .floor-img-box {
    margin: 5px 0;
    padding-left: 2%;
    box-shadow: 1px 2px 2px 1px #efefef;
    border-top: #eff;
  }

  .floor-img {
    height: 335rpx;
    width: 98%;
  }

  .floor-text {
    text-align: center;
    color: #000;
    font-family: 'Courier New', Courier, monospace;
  }

  .onReachBottom-view {
    background-color: #efefef;
    text-align: center;
    font-family: fangsong;
  }
</style>