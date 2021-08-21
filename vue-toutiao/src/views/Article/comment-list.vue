<template>
  <div class="comment-list">
    <van-list
      v-model="loading"
      :finished="finished"
      finished-text="没有更多了"
      :error.sync="error"
      :immediate-check="false"
      error-text="加载失败，请点击重试！！！"
      @load="onLoadComment"
    >
      <comment-item
        v-if="item"
        v-for="(item, index) in list"
        :key="index"
        :comment="item"
        :nowUserId="userId"
        @reply-click="$emit('reply-click', $event)"
      />
    </van-list>
  </div>
</template>

<script>

// 导入评论的每一项的模板
import CommentItem from './comment-item'

export default {
  name: 'CommentList',
  components: {
    CommentItem
  },
  props: {
    source: {
      type: [Number, String, Object],
      required: true
    },

    userId:{
        type:[Number,String,Object]
      },
    type: {
      type: String,
      // 自定义 Prop 数据验证
      vaildator (value) {
        return ['a', 'c'].includes(value)
      },
      default: 'a'
    },
  },
  data () {
    return {
       list:[],
      loading: false,
      finished: false,
      offset: null, // 获取下一页数据的标记
      limit: 10, // 每页的的数据条数
      error: false,
    }
  },
  async created () {
    this.loading = true
    await  this.onLoadComment();
  },

  methods: {
    async onLoadComment () {

      if (this.type === 'a'){
        const {data:res} =await this.$http.get("/relComment/getRealComm/"+this.source.toString())
        this.list=res.data
        this.list.reverse();
        this.loading = false;
        this.finished = true;
      } else if (this.type === 'c'){
        const {data:res} =await this.$http.get("/relComment/getRealCommByParentId/"+this.source.toString())
        this.list=res.data
        this.list.reverse();

        this.loading = false;
        this.finished = true;
      }

    },
  },
  watch:{
    '$route':'onLoadComment'
  }
}
</script>

<style scoped lang="less"></style>
