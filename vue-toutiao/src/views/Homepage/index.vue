<template>
    <section>
        <div class="head" :class="{'hlight':this.top>40}">
            <div class="head-main" :class="{'dlight':this.top>40}">
                <Icon name="jiantou" class="back" @click.native="back($router)"></Icon>
                <div class="name" :class="{'nlight':this.top<160}">{{this.$store.state.nickname}}</div>
                <!--<i class="el-icon-share"></i>-->
                <!--<i class="el-icon-search"></i>-->
                <!--<i class="el-icon-more"></i>-->
            </div>
        </div>
        <el-scrollbar ref="scrollbar" height="400px" always class="scrollbar">
            <div class="img">
                <el-image
                        style="width: 100%; height: 130px"
                        :src="this.$store.state.backgroundImage"
                        :fit="'fill'">
                </el-image>
                <el-upload
                        class="avatar-uploader"
                        action="http://localhost:8081/api/user/uploadBackgroundImage"
                        :data="fileData"
                        :show-file-list="false"
                        :on-success="handleAvatarSuccess"
                        :before-upload="beforeAvatarUpload"
                >
                    <el-button plain size="mini" class="btn-img">设置背景</el-button>
                </el-upload>

                <div class="content">
                    <div class="block">
                        <el-image
                                style="width: 65px; height: 65px;border-radius: 50%"
                                :src="srcList[0]"
                                :preview-src-list="srcList"
                        ></el-image>
                    </div>
                    <el-button plain size="small" class="button1" @click="$router.push('/editInformation')">编辑资料</el-button>
                    <div class="content-bottom">
                        <div class="name">
                            <span>{{this.$store.state.nickname}}</span>
                            <img src="../../assets/images/boy.png" style="width: 35px;height: 20px"
                                 v-if="this.$store.state.sex==='男'">
                            <img src="../../assets/images/girl.png" style="width: 35px;height: 20px"
                                 v-if="this.$store.state.sex==='女'">
                        </div>

                        <div class="information" v-if="$store.state.introduction ==='null' || $store.state.introduction.length ===0">
                            暂无描述！！！！！
                        </div>
                        <div class="information" v-else>
                            {{$store.state.introduction}}
                        </div>
                    </div>
                </div>

                <div class="release">

                    <div class="content_none" v-if="articles.length>0">
                        <h2 style="">我的文章</h2>
                        <div class="articleList">
                            <el-timeline>
                                <el-timeline-item v-bind:timestamp="article.publishtime" placement="top"
                                                  v-for="(article,index) in articles" :key="index">
                                    <el-card>
                                        <!--{name: 'creativeEdit', params: {articleId: article.articleid}}-->
                                        <h4>
                                            <router-link :to="'/article/' + article.id">{{article.headLine}}
                                            </router-link>
                                        </h4>
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

                    <van-empty description="您还没有发表文章" v-if="articles.length<=0" />
                </div>
            </div>
        </el-scrollbar>
    </section>
</template>
<script>
    export default {
        data() {
            return {
                srcList: [
                    this.$store.state.avatar,
                ],

                articles: {},
                currentPage: 1,
                total: 5,
                pageSize: 5,
                userid: 0,
                articleId: 0,
                introduction: "",
                value: 0,
                max: 0,
                top: 0,
                nickname: this.$store.state.nickname,
                imageUrl: this.$store.state.backgroundImage,
                fileData: {
                    id: localStorage.getItem("id"),
                },
            }
        },
        mounted() {
            window.addEventListener('scroll', () => {
                this.top = document.documentElement.scrollTop || document.body.scrollTop || window.pageYOffset
                console.log(this.top);
            })
            this.articleId = this.$store.getters.user.id;
            this.introduction = this.$store.state.introduction;
            this.page();

        },
        methods: {
            page: function () {
                this.userid = this.articleId;
                const that = this;
                console.log(this.userid);
                this.$axios.get('/home/getArticle?uid=' + this.userid).then((res) => {
                    that.articles = res.data.data;
                    console.log(res.data)
                })

            },
            handleAvatarSuccess(res, file) {
                this.imageUrl = URL.createObjectURL(file.raw);
                this.$store.state.backgroundImage = this.imageUrl;
                localStorage.setItem("backgroundImage", this.imageUrl);
            },
            beforeAvatarUpload(file) {
                console.log(file.type)
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
    }
</script>
<style lang="less" scoped>
    .head {
        z-index: 20;
        position: fixed;
        width: 100%;
        height: 0.5rem;

        &.hlight {
            background-color: #fff;
            color: #ffffff;
        }

        .head-main {
            color: #ffffff;

            &.dlight {
                color: #000000;
            }

            .back {
                position: absolute;
                top: 50%;
                transform: translateY(-50%);
                left: 0.1rem;
            }

            .name {
                position: absolute;
                top: 50%;
                left: 50%;
                transform: translateY(-50%) translateX(-50%);

                &.nlight {
                    display: none;
                }
            }

            .el-icon-share {
                position: absolute;
                top: 50%;
                transform: translateY(-50%);
                right: 0.6rem;
            }

            .el-icon-search {
                position: absolute;
                top: 50%;
                transform: translateY(-50%);
                right: 0.35rem;
            }

            .el-icon-more {
                position: absolute;
                top: 50%;
                transform: translateY(-50%);
                right: 0.1rem;
            }
        }
    }

    .scrollbar {
        position: relative;
        height: 900px;
        width: 100%;

        .img {
            position: relative;

            .btn-img {
                position: absolute;
                top: 85px;
                right: 10px;
            }
        }

        .content {
            position: relative;
            width: 100%;
            height: 180px;
            top: -25px;

            .block {
                position: absolute;
                top: -30px;
                left: 20px;
            }

            .button1 {
                position: absolute;
                right: 10px;
                top: 10px;
            }

            .button2 {
                position: absolute;
                right: 100px;
                top: 10px;
            }

            .content-bottom {
                position: absolute;
                top: 40px;
                width: 100%;
                height: 160px;

                .name {
                    position: absolute;
                    top: 5px;
                    left: 10px;
                    font-size: 30px;
                }

                .information {
                    position: absolute;
                    top: 50px;
                    left: 10px;
                    color: #a7a7a7;
                }

                .introduction {
                    position: absolute;
                    top: 75px;
                    left: 10px;
                    color: #a7a7a7;
                }

            }
        }

        .release {
            position: absolute;
            background: #ffffff;
            width: 100%;
            height: 600px;
            top: 300px;

            .content_none {
                position: relative;
                text-align: center;
                color: #a7a7a7;
                top: 20px;
                font-size: 12px;
            }

            .articleList {
                top: 10px;
            }
        }

    }
</style>
