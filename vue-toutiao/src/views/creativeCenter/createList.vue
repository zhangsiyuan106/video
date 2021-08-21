<template>
    <div id="app">
        <HeaderBar title="创作编辑"></HeaderBar>

        我的文章：
        <div class="block">
            <el-timeline >
                <el-timeline-item v-bind:timestamp="article.publishtime" placement="top" v-for="(article,index) in articles" :key="index">
                    <el-card>
                        <!--{name: 'creativeEdit', params: {articleId: article.articleid}}-->
                        <h4><router-link :to="'/article/' + article.id">{{article.headLine}}</router-link></h4>
                    </el-card>
                </el-timeline-item>
            </el-timeline>

        </div>
        <el-pagination class="mpage"
                       background
                       layout="prev, pager, next"
                       :current-page=currentPage
                       :page-size=pageSize
                       @current-change=page
                       :total="total">
        </el-pagination>

    </div>
</template>

<script>
    export default {
        name: "createList",
        data() {
            return {
                articles: {},
                currentPage: 1,
                total: 5,
                pageSize: 5,
                userid: 0
            }
        },
        methods: {
            page:function () {
                this.userid = this.$route.params.articleId;
                const that = this;
                console.log(this.userid);
                this.$axios.get('/home/getArticle?uid='+this.userid).then((res) => {
                    that.articles = res.data.data;
                    console.log(res.data)
                })

            }
        },
        created () {
            this.page();
        }

    }
</script>

<style scoped>

</style>