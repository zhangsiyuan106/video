<template>
  <div class="article-item">
    <!-- 文章的数据使用单元格组件 -->
    <van-cell class="article-item" :to="'/article/' + article.id">
      <!-- 文章标题的插槽 (图片前面的标题) -->
      <div slot="title" class="title van-multi-ellipsis--l2">
        {{ article.headLine }}
      </div>
      <div slot="label">
        <van-image
                v-if="article.imgUrl != ''"
                slot="default"
                class="right-cover"
                fit="cover"
                :src="article.imgUrl"
        />
        <div class="label-info-wrap">
          <span>{{ article.userName }}</span>
          <span>{{ article.replynum }}评论</span>
          <span>总点赞 {{article.likenum}}</span>
          <span>{{ article.publishtime | relativeTime }}</span>
        </div>
        <!-- <p>{{ 日期数据 | relativeTime }}</p> -->
      </div>
      <!-- 如果该条数据的图片大于等于1 则插入第一张-->


    </van-cell>
  </div>
</template>

<script>
export default {
  name: 'ArticleItem',
  // 获取传过来的数据
  props: {
    article: {
      type: Object, // 获取类型为对象
      required: true // 是必须的
    }
  },
  data () {
    return {}
  },
  created () {},
  mounted () {},
  methods: {}
}
</script>

<style scoped lang="less">
.article-item {
  // 标题
  .title {
    font-size: 20px;
    color: #3a3a3a;
  }

  // 图片部分
  .van-cell__value {
    flex: unset; // 取消弹性布局,只保留图片自身的宽度和padding-left 。
    width: 232px;
    height: 110px;
    padding-left: 25px;
  }

  //
  .right-cover {
    width: 200px;
    height: 100px;
  }

  //
  .label-info-wrap span {
    font-size: 15px;
    color: #b4b4b4;
    margin-right: 25px;
  }


  .label-info-wrap {
    margin-right: 5px;
    margin-top: 10px;
  }
}
</style>
