<template>
  <view v-if="articleAvatarAndTitle.length === 0">
    <image class="zw-img" :src="`http://coderfun.fun/todo1.png`"></image>
  </view>
  <view v-else>
    <!-- 楼层区域 -->
    <view class="floor-list"><!-- 楼层item -->
      <van-dialog id="van-dialog" />
      <van-notify id="van-notify" />
      <view v-for="(item,i) in articleAvatarAndTitle" :key="i">
        <view class="floor-img-box">
          <van-swipe-cell id="swipe-cell" right-width="65">
            <van-cell-group>
              <navigator class="floor-item" :url="'/subpkg/article_detail/article_detail?article_id='+item.id">
                <image class="floor-img" :src="item.avatar"></image>
                <text class="floor-text">{{item.title}}</text>
              </navigator>
            </van-cell-group>
            <view class="r-view" slot="right" @click="onClose(item.id)">删除</view>
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
  } from 'vuex'
  export default {
    data() {
      return {
        articleAvatarAndTitle: [],
        total: 0
      };
    },
    onLoad() {
      this.getFloorList()
      this.getLikeList()
    },
    computed: {
      ...mapState('m_user', ['token']),
      ...mapState('m_myLike', ['articleLikeList'])
    },
    methods: {
      ...mapMutations('m_myLike', ['addLike', 'deleteLike', 'setList']),
      onClose(articleId) {
        Dialog.confirm({
          message: '确定删除吗？',
        }).then(() => {
          //删除后端储存
          this.deleteLikeById(articleId)
          this.deleteLike(articleId)
          //重新渲染页面
          for (let i = 0; i < this.articleAvatarAndTitle.length; i++) {
            if (this.articleAvatarAndTitle[i].id === articleId) {
              this.articleAvatarAndTitle.splice(i, 1)
            }
          }
          //提示完成
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
      async deleteLikeById(articleId) {
        console.log(articleId)
        const {
          data: res
        } = await uni.$http.post('/person/deleteLike', articleId)
        if (res.code !== 200) return uni.$showMsg()
      },
      //获取存储的 根据token查articleId 在查询文章
      async getFloorList() {
        const listInfo = {
          page: 1,
          pageSize: 10,
          token: this.token
        }
        const {
          data: res
        } = await uni.$http.post('/person/like', listInfo)
        if (res.code !== 200) return uni.$showMsg('加载失败，请重新登录')
        this.articleAvatarAndTitle = res.data
        for (let i = 0; i < res.data.length; i++) {
          this.addLike(res.data[i].id)
        }
        this.setList()
        this.articleAvatarAndTitle = [...new Set(this.articleAvatarAndTitle)]
      },
      //获取未存储的 根据articleIdList查询文章
      async getLikeList() {
        const {
          data: res
        } = await uni.$http.post('/person/likebyid', this.articleLikeList)
        if (res.code !== 200) return uni.$showMsg('加载失败，请重新登录')
        this.articleAvatarAndTitle = [...this.articleAvatarAndTitle, ...res.data]
        this.articleAvatarAndTitle = [...new Set(this.articleAvatarAndTitle)]
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
    margin: 3px 0;
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