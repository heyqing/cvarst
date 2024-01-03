<template>
  <view>
    <view class="search-box">
      <my-search @click="gotoSearch"></my-search>
      <van-dialog id="van-dialog" />
    </view>
    <!-- 轮播图区域 -->
    <swiper :indicator-dots="true" :autoplay="true" :interval="3000" :duration="1000" :circular="true">
      <!-- 循环渲染轮播图的 item 项 -->
      <swiper-item v-for="(item, i) in swiperList" :key="i">
        <navigator class="swiper-item" :url="'/subpkg/article_detail/article_detail?article_id='+ item.id">
          <!-- 动态绑定图片的 src 属性 -->
          <image :src="item.avatar"></image>
        </navigator>
      </swiper-item>
    </swiper>
    <!-- 分类暂时不展示 -->
    <!-- 楼层区域 -->
    <view class="floor-list">
      <!-- 楼层item -->
      <view v-for="(item,i) in floorList" :key="i">
        <view class="floor-img-box">
          <navigator class="floor-item" :url="'/subpkg/article_detail/article_detail?article_id='+item.id">
            <image class="floor-img" :src="item.avatar"></image>
            <text class="floor-text">{{item.title}}</text>
          </navigator>
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
  import {
    mapState,
    mapMutations
  } from 'vuex'
  export default {
    data() {
      return {
        //返回轮播图数据
        swiperList: [],
        //返回分类导航数据
        navList: [],
        //返回楼层数据
        floorList: [],
        //设置下拉刷新,从最热文章的第几个开始查询
        hotStart: 0,
        //设置swiper，从最新文章的第几个开始查询
        newStart: 0,
        //页码值
        pagenum: 1,
        //显示数量
        pagesize: 10,
        //数据总量
        total: 0,
        //是否在请求数据
        isloading: false
      };
    },
    onLoad() {
      //2.在页面刚刚加载时就获取轮播图数据
      this.getSwiperList(this.newStart)
      // 2. 在 onLoad 中调用获取数据的方法
      this.getNavList()
      //2.在onload中调用获取数据的方法
      this.getFloorList()
      //提示用户文章的
      this.dialog()
      //获取likeId
      this.getArticleLikeId()
    },
    computed: {
      ...mapState('m_user', ['token'])
    },
    methods: {
      ...mapMutations('m_myLike', ['setList','addLike']),
      async getArticleLikeId() {
        const listInfo = {
          page: 1,
          pageSize: 10,
          token: this.token,
          isloading: false
        }
        const {
          data: res
        } = await uni.$http.post('/person/like', listInfo)
        if (res.code !== 200) return
        for (let i = 0; i < res.data.length; i++) {
          this.addLike(res.data[i].id)
        }
        this.setList()
      },
      dialog() {
        Dialog.alert({
          title: '声明',
          message: '我所转载的文章内容均来自其他可靠的来源，并在转载过程中严格遵守了版权法规。我深知保护知识产权的重要性，因此我始终致力于合法转载和使用他人作品。我转载文章的目的在于分享和传播信息，而非用于商业用途。我相信，通过互相学习和共享知识，我们可以共同提高并扩展我们的视野。对于我转载的文章，我尊重其原始作者和出版机构的权益。我尽可能地引用原文来源，并在必要时与原始版权持有者联系以获取使用许可。此外，我会在转载的文章中明确标注来源，以向读者传达原始信息的来源。如果您对转载的文章有任何疑问或需要进一步的解释，请随时与我联系。我将乐意为您提供帮助并解决任何问题',
          theme: 'round-button',
        }).then(() => {
          // on close
        });
      },

      //方法-获取轮播图
      async getSwiperList(cb) {

        //发起请求

        // const res = await uni.$http.post('/articles/new')
        // console.log(res)
        // console.log(res.data)
        //返回状态码 成功 200
        // console.log(res.data.code)
        //返回页面数据 后端返回的data
        // console.log(res.data.data)
        //返回data中的avatar 即文章的显示图片
        // console.log(res.data.data[0].avatar)
        const {
          data: res //相当于res = res.data
        } = await uni.$http.post('/articles/new?newStart=' + this.newStart)
        //请求失败
        //console.log(res.code)
        if (res.code !== 200) return uni.$showMsg()
        // 3.3 请求成功，为 data 中的数据赋值
        this.swiperList = res.data
      },

      //获取导航数据
      async getNavList() {
        const {
          data: res
        } = await uni.$http.get('/category')
        if (res.code !== 200) return uni.$showMsg()
        this.navList = res.data
      },
      //获取楼层数据
      async getFloorList() {
        this.isloading = true
        const listInfo = {
          page: 1,
          pageSize: 10
        }
        const {
          data: res
        } = await uni.$http.post('/articles', listInfo)
        if (res.code !== 200) return uni.$showMsg()
        this.floorList = [...this.floorList, ...res.data]
        //获取数据总量
        this.total = res.total
        this.isloading = false
      },
      gotoSearch() {
        uni.navigateTo({
          url: '/subpkg/search/search'
        })
      },
      //获取最热文章
      async getHotList(cb) {
        const {
          data: res
        } = await uni.$http.post('/articles/hot?hotStart=' + cb)
        if (res.code !== 200) return uni.$showMsg()
        this.floorList = [...res.data,...this.floorList]
        uni.stopPullDownRefresh()
      },

      // 下拉刷新的事件
      onPullDownRefresh() {
        // 重新发起请求
        this.getHotList(this.hotStart)
        if (this.hotStart + 5 >= this.total) this.hotStart = 0
        this.hotStart = this.hotStart + 5
      },

      // 上拉触底的事件
      onReachBottom() {
        // 判断是否还有下一页数据
        if (this.pagenum * this.pagesize >= this.total) return uni.$showMsg('数据加载完毕！')
        // 判断是否正在请求其它数据，如果是，则不发起额外的请求
        if (this.isloading) return

        this.pagenum += 1
        // console.log(this.pagenum)
        this.getFloorList()
      }
    },
  }
</script>

<style lang="scss">
  .search-box {
    // 设置定位效果为“吸顶”
    position: sticky;
    // 吸顶的“位置”
    top: 0;
    // 提高层级，防止被轮播图覆盖
    z-index: 999;
  }

  swiper {
    height: 330rpx;

    .swiper-item,
    image {
      width: 100%;
      height: 100%;
    }
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