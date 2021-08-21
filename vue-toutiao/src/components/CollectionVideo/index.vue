<template>
  <van-button
    :class="{
      collected: value
    }"
    :icon="value ? 'star' : 'star-o'"
    @click="onCollect"
    :loading="false"
  />
</template>

<script>

export default {
  name: 'CollectionVideo',
  data () {
    return {
      loading: false
    }
  },
  props: {
    value: {
      type: Boolean,
      required: true
    },
    videoId: {
      type: [Number, String, Object]
    },
    userId:{
      type: [Number, String, Object]
    }
  },
  created () {},
  mounted () {},
  methods: {
    async onCollect () {
      this.loading = true
      try {
        if (this.value) {
          // 已收藏，取消收藏
          console.log(this.videoId)
          const {data} = await this.$http.get("/video/deleteCollect/"+this.videoId)
          const {res} = await this.$http.get("/userOpera/deleteCollectionVideo/"+this.userId + "/"+this.videoId)
          console.log(data)
        } else {
          // 没有收藏，添加收藏
          console.log(this.value)
          const {data} = await this.$http.get("/video/addCollect/"+this.videoId)
          const {res} = await this.$http.get("/userOpera/addCollectionVideo/"+this.userId + "/"+this.videoId)
          console.log(data)
        }

        // 更新视图
        // v-model 的自带属性 - input ，把!this.value 的值传给父组件，并修改父组件
        this.$emit('input', !this.value)

        this.$toast.success(!this.value ? '收藏成功' : '取消收藏')
      } catch (err) {
        this.$toast.fail('操作失败，需要重试!')
      }
      this.loading = false
    }
  }
}
</script>

<style scoped lang="less">
.collected {
  .van-icon {
    color: orange;
  }
}
</style>
