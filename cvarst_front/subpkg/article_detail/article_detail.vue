<template>
  <view v-if="articleDetil.contentHTML" class="all-view">
    <van-dialog id="van-dialog" />
    <van-toast id="van-toast" />
    <van-swipe-cell id="swipe-cell" right-width="65">
      <van-cell-group>
        <view v-html="articleDetil.contentHTML"></view>
      </van-cell-group>
      <view class="r-view" slot="right">
          <van-icon size=32 :name="iconName" @click="likeAdd()" />
      </view>
    </van-swipe-cell>
  </view>
  <view v-else>{{articleDetil.content}}</view>
</template>

<script>
  import Toast from '../../wxcomponents/vant/toast/toast'
  import Dialog from '../../wxcomponents/vant/dialog/dialog'
  import {
    mapState,
    mapMutations
  } from 'vuex'
  export default {
    data() {
      return {
        iconName: 'like-o',
        id: '',
        articleDetil: {}
      };
    },
    onLoad(op) {
      this.id = op.article_id || ''
      this.getArticleDetil()
    },
    computed: {
      ...mapState('m_user', ['token']),
      ...mapState('m_myLike', ['articleLikeList'])
    },
    methods: {
      ...mapMutations('m_myLike', ['addLike', 'deleteLike']),
      async getArticleDetil() {
        const {
          data: res
        } = await uni.$http.post('/articles/view/' + this.id, this.id)
        if (res.code !== 200) return uni.$showMsg()
        this.articleDetil = res.data

        if (this.token) {
          const {
            data: res
          } = await uni.$http.post('/login/checkToken', this.token)
          if (res.code === 200) {
            if (this.articleLikeList.includes(this.id)) this.iconName = 'like'
            else this.iconName = 'like-o'
          }
        }
      },
      async likeAdd() {
        //token为空
        if (!this.token) {
          //提示未登录 
          Dialog.alert({
            message: '您还未登录！',
          }).then(() => {

          });
        } else {
          //token不合法
          const {
            data: res
          } = await uni.$http.post('/login/checkToken', this.token)
          if (res.code !== 200) return uni.$showMsg('登录已过期，请重新登录！')
          //添加
          this.iconName = this.iconName === 'like-o' ? 'like' : 'like-o'
          if (this.iconName === 'like') {
            this.addLike(this.id)
            Toast.success()
          } else {
            this.deleteLike(this.id)
            Toast.fail('取消我的喜欢')
          }

        }
      }
    },
  }
</script>

<style lang="scss">
  .r-view {
    position: relative;
    background-color: #1781b5;
    height: 100%;
    width: 65px;

    van-icon {
      position: absolute;
      height: 100vh;
      margin-top: 50rpx;
      margin-left: 20px;
    }
  }

  .tips {
    display: inline-grid;
    background-color: #efefef;
    margin-top: 15px;
    margin-bottom: 15px;
  }

  .imginfo {
    color: gray;
    font-style: italic;
    white-space: break-spaces;
    margin-top: 15px;
    margin-bottom: 15px;
  }
</style>