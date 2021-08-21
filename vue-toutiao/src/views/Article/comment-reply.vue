<template>
  <div class="comment-reply">
    <van-nav-bar
      :title="
        comment.replynum > 0 ? `${comment.replynum}条回复` : '暂无回复'
      "
    >
      <van-icon name="cross" slot="left" @click="$emit('close')" />
    </van-nav-bar>

    <div class="scroll-wrap">
      <!-- 当前评论 传入数据 -->
      <comment-item :comment="comment" :nowUserId = "userId"/>

      <!-- 评论的回复列表 -->
      <van-cell title="全部回复" />

      <!-- 将最新获得的数组传递到列表组件中 -->
      <comment-list :list="commentList" :source="comment.id" type="c"  :userId = "userId"/>
    </div>

    <!-- 发布评论 -->
    <div class="post-wrap">
      <van-button round class="post-btn" @click="isPostShow = true"
        >写评论</van-button
      >
    </div>

    <!-- 发布评论 -->
    <van-popup v-model="isPostShow" position="bottom">
      <comment-post :art-id="comment.id" @post-success="onPostSuccess" comment-type="commentReply"/>
    </van-popup>
  </div>
</template>

<script>
// 因为回复评论组件的模板和评论组件的模板一样，所以可以直接导入评论组建的模板，节约代码
import CommentItem from './comment-item'
import CommentList from './comment-list'
// 导入 写评论 的模块
import CommentPost from './comment-post'

export default {
  name: 'CommentReply',
  inject:["reload"],
  data () {
    return {
      isPostShow: false,
      commentList: [],// 声明一个空数组， 将最新的数据传到数组中
      currentComment: {},// 接收 当前点击回复的评论项
      isReplyShow: false, // 评论回复
      nowUserId:0,
    }
  },
  props: {
    comment: {
      type: Object,
      required: true
    },
    userId:{
      type: [Number,Object,String]
    }
  },
  created () {},
  mounted () {
  },
  methods: {
    async onPostSuccess (data) {
      console.log(data)
      // 更新回复的数量
      // this.comment.replynum++;
      const {res} = await this.$http.get("/relComment/updateReplyNum/"+data)
      // 关闭弹出层
      this.isPostShow = false
      // 将最新的回复内容展示到列表的最上方
      this.commentList.unshift(data.new_obj)
      this.reload();
    },
  },
  components: {
    CommentItem,
    CommentList,
    CommentPost
  }
}
</script>

<style scoped lang="less">
.comment-reply {
  .scroll-wrap {
    position: fixed;
    top: 225px;
    left: 0;
    right: 0;
    bottom: 88px;
    overflow-y: auto;
  }
  .post-wrap {
    position: fixed;
    bottom: 0;
    left: 0;
    right: 0;
    height: 88px;
    display: flex;
    align-items: center;
    justify-content: center;
    background-color: #fff;
    border-top: 1px solid #d8d8d8;
    .post-btn {
      width: 60%;
      height: 60%;
    }
  }
}
</style>
