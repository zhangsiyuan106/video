<template>
    <div>
        <HeaderBar title="关注列表"></HeaderBar>
        <!-- tabs -->
        <div class="tabs df-c border-half-top" v-for="item in followslist">

            <div class="avatar-div">
                <img :src="item.user.avatar" width="40px" height="40px" style="border-radius: 40%">
            </div>
            <div class="li border-half-top"  >
                <div>{{item.user.userName}}</div>
            </div>
        </div>
    </div>
</template>

<script>
    export default {
        name: "follows",
        data(){
            return{
                followslist:[],
            }
        },
        methods:{
            login(){
                this.$axios.get("/followees/"+this.$store.getters.user.id ).then((res) => {
                    this.followslist = res.data.data;
                })
            }
        },
        created() {
            this.login();
        }
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
    }

    .avatar-div{
        width: 15%;
        height: 100%;
        margin-left: 0px;
    }
</style>