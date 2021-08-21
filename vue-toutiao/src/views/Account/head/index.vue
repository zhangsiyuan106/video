<template>
    <section class="head-wrapper">
        <div class="hd-t">
            <div class="info">
                <span class="alert-box-item" v-if="nickname">

                  <img v-if="imageUrl" :src="imageUrl" class="avatar">

                </span>
                <span class="avatar bg-cover-all" v-else
                      :style="{backgroundImage: `${nickname ? 'url('+user.avatar+')'
                                         :'url('+require('assets/images/account-circle.svg')+')' }`}">
                </span>
                <span class="name" style="font-size: 20px;font-family: 微软雅黑;letter-spacing: 1px" v-if="nickname">{{this.$store.state.nickname}}
                <span style="font-size: 15px;color: #ffffff;letter-spacing: 1px" @click="$router.push('/homepage')">个人主页<i
                        class="el-icon-arrow-right"></i></span>
                </span>
                <span class="login" @click="login" v-else>点击登录</span>
            </div>
        </div>

        <div class="hd-m df-c">
            <div class="hd-m-item df-c" v-for="(v,index) in infoList" @click="Follws(index)">
                <span>{{v.num=== "undefined"? 0 : v.num}}</span>
                <div class="hd-m-t">{{v.title}}</div>
            </div>
        </div>

    </section>
</template>
<script>
    import {mapGetters} from 'vuex'
    let inputElement = null;

    export default {
        data() {
            return {
                infoList: [
                    {num: 0, title: '获赞'},
                    {num: 0, title: '关注'},
                    {num: 0, title: '粉丝'}
                ],

                nickname: localStorage.getItem("nickname"),
                username: localStorage.getItem("username"),
                fileData: {
                    id: localStorage.getItem("id"),
                },
                imageUrl: localStorage.getItem("avatar"),
            }
        },
        mounted(){

            this.$axios.get("/init").then((res) =>{});

            var that = this;
            if(this.$store.getters.user.id !== "0") {
                this.$axios.get("/followeestable/" + this.$store.getters.user.id).then((res) => {
                    // console.log(res.data.msg);
                    that.infoList[2].num = res.data.msg
                });
                this.$axios.get("/followerstable/" + this.$store.getters.user.id).then((res) => {
                    // localStorage.setItem('followers', res.data.msg);
                    that.infoList[1].num = res.data.msg
                });
                this.$axios.get("/like/"+this.$store.getters.user.id).then((res) =>{
                    // console.log(res.data.data);
                    that.infoList[0].num = res.data.data
                });
            }

        },
        methods: {

            Follws(index){
                if(index == 1){
                this.$router.push({name: '我的关注'})}
                else if(index == 2){
                    this.$router.push({name: '我的粉丝'})}
            },
            login() {
                this.$store.state.user.isLogin = true;

            },
            handleAvatarSuccess(res, file) {
                this.imageUrl = URL.createObjectURL(file.raw);
                localStorage.setItem("avatar", this.imageUrl);
            },
            beforeAvatarUpload(file) {
                // console.log(file.type)
                const isJPEG = file.type === 'image/jpeg';
                const isPNG = file.type === 'image/png';
                const isJPG = file.type === 'image/jpg';
                const isLt1M = file.size / 1024 / 1024 < 1;

                if (!isJPG && !isPNG && !isJPEG) {
                    this.$message.error('上传头像图片只能是JPG或者PNG格式!');
                }
                if (!isLt1M) {
                    this.$message.error('上传头像图片大小不能超过 1MB!');
                }

                return file.type && isLt1M;
            },




        },
        computed: {
            ...mapGetters(['user']),
        }
    }
</script>
<style lang="less" scoped>
    .head-wrapper {
        width: 100%;
        background-color: #2b8eff;
    }

    .hd-t {
        padding: 0.2rem;

        .info {
            color: #fff;

            > * {
                display: inline-block;
                vertical-align: middle;
            }

            .avatar {
                width: 0.5rem;
                height: 0.5rem;
                border-radius: 50%;
            }

            .alert-box-item {
                overflow: hidden;
            }

            .bigImg-div {
                width: 0.6rem;
                height: 0.6rem;
                border-radius: 100%;
                overflow: hidden;
                border: 1px solid #777;
            }

            .bigImg {
                display: block;
                width: 0.6rem;
                height: 0.6rem;
                border-radius: 100%;
            }

        }
    }

    .hd-m {
        font-size: 0.12rem;
        color: @font-gray;
        padding-bottom: 0.1rem;

        span {
            color: #fff;
            margin-bottom: 0.08rem;
        }

        .hd-m-t {
            color: #efefef;
        }
    }

    .hd-b {
        padding: 0.1rem 0;
        background: #fff;
    }

    .hd-m-item, .hd-b-item {
        width: e("calc(100% / 3)");
        height: 0.5rem;
        flex-direction: column;
    }

    .hd-b-item {
        font-size: 0.12rem;

        svg {
            font-size: 0.2rem;
            margin-bottom: 0.08rem;
        }

        &:nth-child(1) {
            svg {
                color: @theme-blue;
            }
        }

        &:nth-child(2) {
            svg {
                color: @theme-green;
            }
        }

        &:nth-child(3) {
            svg {
                color: @theme-red;
            }
        }



    }

</style>
