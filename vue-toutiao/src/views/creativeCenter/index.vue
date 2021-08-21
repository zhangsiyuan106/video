<template>
    <div>
        <HeaderBar title="创作中心"></HeaderBar>

        <div class="li border-half-top">
            <router-link :to="{name: '发布', params: {articleId: articleId}}">
            文章发布
            </router-link></div>
        <!-- @click="$router.push('/creative/list')"-->
        <div class="li border-half-top" >
            <router-link :to="{name: '创作列表', params: {articleId: articleId}}">
            我的创作查看
            </router-link> </div>
        <div class="li border-half-top">
            <router-link :to="{name:'视频上传'}">
                视频发布
            </router-link>
        </div>
    </div>
</template>

<script>
    export default {
        name: "index",
        data (){
        return {
            articleId: ""
            }
        },
        created() {
            this.articleId = this.$store.getters.user.id;
        }

    }
</script>

<style  lang="less" scoped>
    .li {
        height: 0.5rem;
        line-height: 0.5rem;
        width: 100%;
        padding: 0 0.1rem;
        background-color: #fff;
        display: flex;
        justify-content: space-between;
        align-items: center;
        svg {
            color: #bbb;
        }
        span {
            color: @font-gray;
            font-size: 0.12rem;
            margin-right: 0.08rem;
        }

        &:nth-child(1) {
            margin: 0.1rem 0;
            &:before {
                border-left: none;
                border-right: none;
            }
        }
        &:nth-child(4) {
            margin-bottom: 0.1rem;
            &:before {
                border-left: none;
                border-right: none;
            }
        }
    }
</style>