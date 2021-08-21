<template>
    <div>
        <HeaderBar title="文章评论"></HeaderBar>
        <div class="li border-half" @click="" v-for="mg in msg" v-if="mg.userid != uid">

            <div class="comuser">{{mg.nickname}}<p>评论了你:</p></div>
            <div class="content">{{mg.content}}</div>
            <div class="works">{{mg.articleId}}作品</div>
        </div>
        <div v-if="msg.length<=0">
            <van-empty description="没有更多了~~" /></div>
    </div>
</template>

<script>
    export default {
        name: "tomemsg",
        data(){
            return{
                uid: this.$store.getters.user.id,
                msg: [],
                msgTwo: "没有更多了~~"
            }
        },
        methods:{
            fn(){
                this.$axios.get('http://localhost:8081/msg/article/'+this.uid).then((res) => {

                    this.msg = res.data.data;
                })
            }
        },
        created() {
            this.fn();
        }
    }
</script>

<style lang="less" scoped>
    .li {
        height: 1rem;
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
        font-size: 0.5rem;
        margin-right: 0.08rem;
    }

    &:nth-child(n) {
         margin: 0.1rem 0;
    &:before {
         border-left: none;
        border-bottom: 1px solid;
         border-right: none;
     }
    }
    /*&:nth-child(n) {*/
    /*margin-bottom: 0.1rem;*/
    /*&:before {*/
    /*border-left: none;*/
    /*border-right: none;*/
    /*}*/
    /*}*/
    }
    .content{
        width: 50%;
    }

    .comuser{
        width: 100px;
        height: 100%;
    }
</style>
