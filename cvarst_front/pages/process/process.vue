<template>
  <view>
    <van-dialog id="van-dialog" />
    <my-camera v-if="photoList.length === 0"></my-camera>
    <my-data-process v-else></my-data-process>
  </view>
</template>

<script>
  import Dialog from '../../wxcomponents/vant/dialog/dialog';
  import {
    mapState
  } from 'vuex'
  import {
    mapMutations
  } from 'vuex'
  export default {
    data() {
      return {

      };
    },
    onLoad() {
      //提示用户文章的
      this.dialog()
    },
    computed: {
      ...mapState('m_camera', ['photoList'])
    },
    methods: {
      dialog() {
        Dialog.alert({
          message: '此功能尚未完善\n(了解更多：个人->关于我们)',
          theme: 'round-button',
        }).then(() => {
          // on close
        });
      },
    }
  }
</script>

<style lang="scss">

</style>