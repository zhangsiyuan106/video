<template>
    <div class="record-wrapper">

        <HeaderBar title="消息通知"></HeaderBar>

        <div class="li border-half" @click="$router.push('/tomemsg')">
            <div>文章评论</div>

            <Icon name="arrow"></Icon>
        </div>
        <div class="li border-half" @click="$router.push('/videomsg')">
            <div>视频评论</div>

            <Icon name="arrow"></Icon>
        </div>
        <div class="li border-half" @click="$router.push('/replymsg')">
            <div>回复我的</div>

            <Icon name="arrow"></Icon>
        </div>
    </div>
</template>
<script>
    export default {
            data(){
                return{
                msg_me: 0,
                msg_Tome: 0,
                msg_reply: 1,
                msg_system: 1,
                uid: 0,
            }
            },
        computed:{
            all(){
                return parseInt(this.msg_me)+parseInt(this.msg_Tome)+parseInt(this.msg_reply)+parseInt(this.msg_system);
            }
        },
        created(){
            this.uid = this.$store.getters.user.id;
        },
        methods:{
        },


    }
</script>
<style lang="less" scoped>

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

        &:nth-child(n) {
            margin: 0.1rem 0;
            &:before {
                border-left: none;
                border-right: none;
            }
        }
    }

    .con_red{
        margin-right: -64%;
        width: 33px;
        height: 25px;
        text-align: center;
        line-height: 25px;
        border-radius: 20px;
        background: #F56C6C;
    }
    .con_red-top{
        width: 33px;
        height: 25px;
        text-align: center;
        line-height: 25px;
        border-radius: 20px;
        background: #F56C6C;
        position: absolute;
        top: 2.4%;
        left: 61%;
        z-index: 1;
    }

</style>
