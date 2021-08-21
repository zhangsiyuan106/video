<template>
  <!-- 首页列表内容 -->

  <div class="article-list">
    <TopBar></TopBar>
    <!-- 下拉刷新 -->
    <van-pull-refresh
      v-model="isreFreshLoading"
      @refresh="onRefresh"
      :success-text="refreshSuccessText"
      :success-duration="1500"
    >
      <!-- 列表组件 向下滚动刷新 -->
      <van-list
        v-model="loading"
        :finished="finished"
        finished-text="没有更多了"
        :error.sync="error"
        error-text="请求失败，点击重新加载"
        @load="onLoad"
      >
        <!-- 单元格组件 原本每一条数据的格式 -->

        <!-- 文章列表的数据 -->
        <!-- 刷新后重新渲染的数据格式 -->
        <article-item
          v-for="(article, index) in list"
          :key="index"
          :article="article"
        />
      </van-list>
    </van-pull-refresh>
  </div>
</template>

<script>
import ArticleItem from '../../components/Article-item'
import TopBar from './topbar/index.vue'

export default {
  name: 'ArticleList',
  components: {
    ArticleItem,
    TopBar
  },
  // props接收父组件传递过来的数据
  data () {
    return {
      list: [], // 储存数据的数组
      loading: false, // 控制加载中的 loading 状态
      finished: false, // 控制数据加载结束的状态
      error: false, // 是否加载失败
      timestamp: null, // 请求下一页数据的时间戳
      isreFreshLoading: false, // 控制下拉刷新的 loading 状态
      refreshSuccessText: '刷新成功' // 下拉刷新成功提示文本
    }
  },
  created () {},
  mounted () {},
  methods: {
    // 初始化或滚动到底部的时候会触发调用 onLoad 上拉
    async onLoad () {
      // setTimeout 仅做示例，真实场景中一般为 ajax 请求
      try {
        // 1. 请求获取数据
        const { data } = await this.$http.get('home/list')
        // 2. 把数据添加到 list 数组中
        const { articleList } = data
        // console.log(...articleList)
        this.list.push(...articleList)

        this.list.reverse();
        // 3.设置本次加载中 loading 状态结束
        this.loading = false
        console.log(articleList.length)
        // 4. 判断数据是否加载结束
        // if (articleList.length) {
        //   // 更新获取下一页数据的时间戳
        //   this.loading = false                        // this.timestamp = data.data.pre_timestamp
        // } else {
        //   // 没有数据了，设置加载状态结束，不再触发上拉加载更多了
        this.finished = true
        // }
      } catch (err) {
        // console.log(err)
        this.loading = false // 关闭 loading 效果
        this.error = true // 开启错误提示
      }
    },

    // 当下拉刷新的时候会触发调用该函数
    async onRefresh () {
      //   console.log('onRefresh')
      try {
        // 请求获取数据
        // 1.请求获取数据
        const { data } = await this.$http.get('home/list')
        // 2. 将数据追加到列表的顶部
        const { articleList } = data
        this.list.unshift(...articleList)

        // 3. 关闭下拉刷新的 loading 状态
        this.isreFreshLoading = false

        // 更新下拉刷新成功时的提示文本
        this.refreshSuccessText = `刷新成功,更新了${articleList.length}条数据`
      } catch (error) {
        this.refreshSuccessText = '刷新失败'
        this.isreFreshLoading = false
      }
    }
  }
}
</script>
<style scoped lang="less">
// 让每一个标签内容文章列表产生自己的滚动容器
// 设置固定高度 height:xxx  和 溢出滚动 overflow-y: auto
// css3 中新增了一种视口单位 vw 和 vh ，何谓视口，就是根据你浏览器窗口的大小的单位，不受父元素的影响
// 1vm = 可视窗口宽度的百分之一
// 1vh = 可视窗口高度的百分之一
// 兼容性好  推荐使用
.article-list {
  height: 79vh;
  overflow-y: auto;
}
</style>
