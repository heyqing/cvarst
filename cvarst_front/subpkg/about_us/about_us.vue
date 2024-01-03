<template>
  <view>
    <h1>关于我们</h1>
    <view class="about-content">
      <p>基于计算机视觉的遥感图像检测是一种利用计算机视觉技术对遥感图像进行分析和识别的方法。随着遥感技术的不断发展，遥感图像的分辨率和清晰度得到了极大的提高，这也为计算机视觉技术在遥感图像检测中的应用提供了广阔的空间。</p>
      <p>基于计算机视觉的遥感图像检测的基本流程包括以下几个步骤：</p>
      <p class="p-tt">预处理：对遥感图像进行预处理，包括降噪、增强、变换等操作，以提高图像的质量和清晰度，便于后续的特征提取和目标检测。</p>
      <p class="p-tt">特征提取：利用计算机视觉技术，从预处理后的遥感图像中提取出与目标相关的特征，包括纹理、形状、颜色等。这些特征可以反映目标的本质特征和属性，为后续的目标检测和分类提供依据。</p>
      <p class="p-tt">目标检测：利用计算机视觉技术，根据提取的特征，对遥感图像中的目标进行检测和定位。常用的目标检测算法包括基于滑动窗口的方法、基于边缘的方法、基于深度学习的方法等</p>
      <p class="p-tt">分类识别：根据目标检测的结果，对目标进行分类和识别。常用的分类识别算法包括基于贝叶斯分类器的方法、基于支持向量机的方法、基于深度学习的方法等。</p>
      <p>基于计算机视觉的遥感图像检测具有以下优点：</p>
      <p class="p-tt">高效率：计算机视觉技术可以快速地处理大量的遥感图像，提高了检测的效率。</p>
      <p class="p-tt">高精度：计算机视觉技术可以准确地提取目标的特征，提高了检测的精度。</p>
      <p class="p-tt">自动化：计算机视觉技术可以实现自动化的目标检测和分类识别，减少了人工干预。</p>
      <p class="p-tt">低成本：计算机视觉技术所需的硬件设备和软件成本相对较低，降低了检测的成本。</p>
      <p>总之，基于计算机视觉的遥感图像检测是一种非常有前途的技术，可以广泛应用于土地资源调查、环境监测、城市规划等领域。随着计算机视觉技术的不断发展，相信这种技术的应用前景将会更加广阔。</p>
    </view>
    <view class="tips">
      <h2>Tips</h2>
      <view class="about-content">
        <p class="tips-text">本产品因技术、时间等等原因部分功能还未实现，敬请理解！</p>
      </view>
    </view>
    <view class="feedback">
      <h3>*****反馈*****</h3>
      <textarea @confirm="confirm" cols="30" rows="10" adjust-position="true"  placeholder="独学而无友，则孤陋而寡闻,期待你的留言~~~"
        auto-blur="true" cursor-spacing=180 show-confirm-bar="false"></textarea>
      <button type="primary" @click="commit">提交</button>
      <van-popup :show="showFlag" round position="top" custom-style="height: 15%;">
        <view class="popup-x-body">
          <h4>谢君箴言警我心，勇者无惧披荆棘。</h4>
          <p>感谢您的反馈</p>
        </view>
      </van-popup>

    </view>
  </view>
</template>

<script>
  export default {
    data() {
      return {
        feedbackText: '',
        showFlag: false,
      }
    },
    methods: {
      confirm(e) {
        this.feedbackText = e.detail
      },
      async commit() {
        if (!this.feedbackText) return uni.$showMsg('请正确填写反馈内容！')
        const {
          data: res
        } = await uni.$http.post('/person/feedback', this.feedbackText)
        if (res.code !== 200) return uni.$showMsg()
        if (!this.showFlag) this.showFlag = true
        setTimeout(() => {
          this.showFlag = false
        }, 1800)
        this.feedbackText = ''
      }
    }
  }
</script>

<style lang="scss">
  h1 {
    font-size: 22px;
    text-align: center;
    margin-top: 10rpx;
    margin-bottom: 10rpx;
  }

  h2 {
    font-size: 22px;
    text-align: center;
    margin-top: 20rpx;
    margin-bottom: 20rpx;
  }

  h3 {
    font-size: 18px;
    text-align: center;
    margin-top: 20rpx;
    margin-bottom: 20rpx;
    background-color: #efefef;
  }

  h4 {
    margin-top: 40rpx;
  }

  .about-content {
    margin-left: 8rpx;
    margin-right: 8rpx;

    p {
      line-height: 1.5;
      letter-spacing: 0.1em;
      text-indent: 2em;
    }

    .p-tt {
      text-indent: 2em;
      font-style: oblique;
      color: #505050;
    }
  }

  .tips-text {
    text-shadow: 1rpx 1rpx 2rpx aqua, 0 0 1em lightskyblue, 0 0 0.2em lightskyblue;
  }

  .popup-x-body {
    text-align: center;
    font-family: fangsong;
  }
</style>