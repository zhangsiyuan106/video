<template>
  <div class="comment-post">
    <van-field
      class="post-field"
      v-model.trim="message"
      rows="2"
      autosize
      type="textarea"
      maxlength="50"
      placeholder="请留下您精彩的评论！！！"
      show-word-limit
    />
    <van-button class="post-btn" @click="onPost" :disabled="!message.length"
      >发布</van-button
    >
  </div>
</template>

<script>
// 导入 评论内容发布 的接口
// import { addComment } from '@/api/comment.js'

export default {
  name: 'CommentPost',
  inject:['reload'],
  data () {
    return {
      // 用来接收发表的数据
      message: '',
      userName:"",
      userid:"",
      _this:this
    }
  },
  inject: {
    articleId: {
      type: [Number, Object, String],
      default: null
    }
  },
  props: {
    artId: {
      type: [Number, String, Object],
      required: true
    },
    commentType: {
      type: String,
      // 自定义 Prop 数据验证
      vaildator (value) {
        return ['', 'commentReply'].includes(value)
      },
      default: ''
    }
  },
  mounted(){
    this.userName = localStorage.getItem("nickname")
    this.userid = localStorage.getItem("id");
  },

  methods: {

    // 发布评论
    async onPost () {
      // 发布评论之前，添加 loading 效果
      this.$toast.loading({
        duration: 0, // 持续展示 toast
        message: '发布中...',
        forbidClick: true // 是否禁止背景点击
      })
      try {
        if (this.commentType === ""){
          const {data} =  await this.$http("relComment/addRealComment?id="+this.artId+"&content="+this.message+"&username="+this.userName+"&userid="+this.userid)
          this.message = ''
          // 2.将数据传递到父级
          this.$emit('post-message', this.artId)
          this.$emit('post-success', this.message)

          this.$toast.success('发布成功')
        } else {
          const {data} = await this.$http.get("relComment/addRealCommentReply?id="+this.artId+"&content="+this.message+"&username="+this.userName+"&userid="+this.userid);
          this.message = ''
          // 2.将数据传递到父级
          this.$emit('post-success', this.artId)
          this.$toast.success('发布成功')

           // this.$router.go(0);
        }// 1.清空文本框的内容
      } catch (err) {
        this.$toast.fail('发布失败')
      }finally {
        // var that = this

      }
    }
  }
}
</script>

<style scoped lang="less">
.comment-post {
  display: flex;
  align-items: center;
  padding: 32px 0 32px 32px;
  .post-field {
    background-color: #f5f7f9;
  }
  .post-btn {
    width: 150px;
    border: none;
    padding: 0;
    color: #6ba3d8;
    &::before {
      display: none;
    }
  }
}
</style>
