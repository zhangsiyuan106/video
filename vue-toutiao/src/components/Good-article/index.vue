<template>
  <!-- loading 默认停止加载 -->
  <van-icon
    :name="value === 1 ? 'good-job' : 'good-job-o'"
    :color="value === 1 ? '#e5645f' : ''"
    @click="onCollect"
    :loading="loading"
  />
</template>

<script>
// import { addLike, deleteLike } from '@/api/article'

export default {
  name: 'GoodArticle',
  props: {
    value: {
      type: Number,
      required: true
    },
    articleId: {
      type: [Number, String, Object],
      required: true
    },
    userId: {
      type: [Number, String, Object],
      required: true
    },
  },
  data () {
    return {
      loading: false
    }
  },
  created () {

  },
  mounted () {},
  methods: {
    async onCollect () {
      this.$toast.loading({
        duration: 0, // 持续展示 toast
        message: '操作中...',
        forbidClick: true // 是否禁止背景点击
      })
      this.loading = true

      try {
        let status = 0
        if (this.value === 1) {
          // 已点赞，取消点
          const {data} = await this.$http.get("/home/deleteLike/"+this.articleId)
          const {res} = await this.$http.get("/userOpera/deleteLikeArticle/"+this.userId + "/"+this.articleId)

        } else {
          // 没有点赞，添加点赞
          //console.log("点赞成功")
          const {data} = await this.$http.get("/home/addLike/"+this.articleId)
          const {res} = await this.$http.get("/userOpera/addLikeArticle/"+this.userId + "/"+this.articleId)

          status = 1
        }
        // 更新视图
        // v-model 的自带属性 - input ，把status得到的值传给父组件，并修改父组件
        this.$emit('input', status)
        // 成功时，根据status的值反映页面
        this.$toast.success(status === 1 ? '点赞成功' : '取消点赞')
      } catch (err) {
        console.log(err)
        this.$toast.fail('操作失败，请重试！')
      }
      this.loading = false
    }
  }
}
</script>

<style scoped lang="less">
.van-icon {
  font-size: 45px;
}
</style>
