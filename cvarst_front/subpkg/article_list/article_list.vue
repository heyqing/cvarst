<template>
  <view>
    <view class="search-kw">
      <p>{{kw}}</p>
    </view>
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
  export default {
    data() {
      return {
        kw: '',
        floorList: []
      };
    },
    onLoad(op) {
      this.kw = op.query
      this.searchArticleList()
    },
    methods: {
      async searchArticleList() {
        console.log(this.kw)
        const {
          data: res
        } = await uni.$http.get('/articles/qsearch?kw=' + this.kw)
        console.log(res)
        if (res.code !== 200) return uni.$showMsg('暂无信息')
        this.floorList = res.data
      }
    }
  }
</script>

<style lang="scss">
  .search-kw {
    margin: 10rpx 5px 15rpx 5px;
    padding-left: 2%;
    box-shadow: 1px 2px 2px 1px #efefef;
    border-top: #eff;
    background-color: #efefef;
    text-align: center;
    font-family:cursive;
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