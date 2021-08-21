<template>
    <div class="article-container">
        <!-- 文章详情 -->
        <!-- 导航栏 -->
        <van-nav-bar
                class="page-nav-bar"
                left-arrow
                @click-left="onClickLeft"
                title="天天视频"
        ></van-nav-bar>

        <!-- 页面内容 -->
        <div class="main-wrap">
            <!-- 加载中 -->
            <article class="video-wrapper">
                <div class="video-container">
                    <section class="item border-half-bottom" v-for="(item, index) in videoInfo">
                        <div class="video">
                            <video id="my-video" controls="true"  >
                                <source v-bind:src="item.tag+item.fileUrl" type="video/mp4" />
                            </video>
                            <!-- canvas代替video播放 -->
                            <div class="canvas-video bg-cover" :style="{backgroundImage: `url(${item.tag+item.imgPath})`} ">
                                <canvas @click="pause(index, item)"></canvas>
                            </div>

                            <div class="title" v-show="!item.playBol">
                                <h4>{{item.mimeType}}</h4>
                                <small>{{item.playCount}}次播放</small>
                            </div>
                            <div class="play" @click="play(index, item)" v-show="!item.playBol">
                                <Icon name="play"></Icon>
                            </div>
                            <time v-show="item.uploadTime">{{item.uploadTime}}</time>
                            <div class="avatar bg-cover-all" v-show="!item.playBol" :style="{backgroundImage: `url(${item.image})`}"></div>


                        </div>
                        <div class="intro df-sb">

                            作者:<div class="source">{{item.userName}} </div>
                            <div class="box df-c">
                                <div @click="onFollow(item)">
                                    <div v-if="isFollowUser">已关注</div>
                                    <div v-else>
                                        <Icon name="attention"></Icon>
                                        <span>关注</span>
                                    </div>
                                </div>
                                <div>
                                    <Icon name="More" @click.native="more"></Icon>
                                </div>
                            </div>
                        </div>

                        <van-divider>评论区</van-divider>


                        <comment-list
                                :userId = "nowUserId"
                                :getMediaId="item.fileId"
                                v-model="commentList"
                                @reply-click="onReplyClick"
                        />

                        <div class="article-bottom">
                            <van-button
                                    class="comment-btn"
                                    type="default"
                                    round
                                    size="small"
                                    @click="isPostShow = true"
                            >写评论</van-button
                            >
                            <!--&lt;!&ndash; 评论图标 &ndash;&gt;-->
                            <van-icon name="comment-o" :info="item.replyNum" color="#777" />
                            <!--&lt;!&ndash; 收藏图标  &ndash;&gt;-->
                            <colletion-video
                                    v-model="isCollectedVideo"
                                    class="btn-item"
                                    :video-id="item.fileId"
                                    :userId="nowUserId"
                            ></colletion-video>

                        </div>
                        <!--发布评论-->
                        <van-popup v-model="isPostShow" position="bottom">
                            <!-- 把文章id传到发表评论的组件里 -->
                            <comment-post
                                    :target="item.fileId"
                                    @post-success="onPostSuccess"
                                    @post-message="addReplyNum"
                            />
                        </van-popup>
                    </section>
                </div>

                <DefaultLoading v-show="videoLoading"></DefaultLoading>
                <NoneData v-if="videoEnd"></NoneData>
            </article>
        </div>

        <!--评论回复-->
        <van-popup v-model="isReplyShow" position="bottom" style="height: 90%">
            <comment-reply
                    v-if="isReplyShow"
                    :comment="currentComment"
                    @close="isReplyShow = false"
                    :userId = "nowUserId"
            />
        </van-popup>
    </div>
</template>
<script>

    import CommentList from './video-comment-list'
    import GoodVideo from '../../components/GoodVideo'
    import ColletionVideo from '../../components/CollectionVideo'
    import  CommentPost from './video-comment-post'
    import CommentReply from './video-comment-reply'
    import axios from 'axios'
    import { mapGetters } from 'vuex'
    export default {
        inject:['reload'],
        data() {
            return {
                videoInfo:[],
                loading: true, // 加载中的 loading
                item: "",
                mymediaId:"",
                srcUrl: '../../static/bg.jpg',
                followLoading: false, // loading 状态显示与否
                articleFollow:false,
                isFollowUser:false,
                commentList:[],
                currentComment: {},// 接收 当前点击回复的评论项
                isReplyShow: false, // 评论回复
                isPostShow: false, // 控制发布评论的显示状态
                audioReady: false,
                isCollectedVideo:false,
                nowUserId:0

            }

        },
        async  mounted(){
            this.nowUserId = localStorage.getItem("id");
            this.mymediaId = window.sessionStorage.getItem("mediaId")
            await this.getVideoById();
            await this.findLikeVideo();
        },

        methods: {
            more() {
                this.$alert('没有更多信息哦!')
            },
            async getVideoById() {
                try {
                    const {data: res} = await this.$http.get('/video/findVideoByVideoId/'+this.mymediaId)

                    console.log(res.data)

                    this.videoInfo = res.data;


                } catch (error) {
                     console.log('获取数据失败', error)
                    if (error.response && error.response.status === 404) {
                        this.errStatus = 404
                    }
                }
                // 无论成功还是失败，都需要关闭 loading
                this.loading = false
            },
            async findLikeVideo(){
                const {data: res} = await this.$http.get("/userOpera/findLikeArticle/"+this.nowUserId)
                console.log( res.data)
                if (res.data=== null) {
                    this.isCollectedVideo = false;
                    this.isFollowUser = false;
                    return;
                }
                if (res.data.collectVideo != null){
                    for (let i = 0; i < res.data.collectVideo.length; i++) {
                        if (res.data.collectVideo[i] === parseInt(this.mymediaId)){
                            this.isCollectedVideo = true;
                        }
                    }
                }
                if (res.data.followUser != null){
                    for (let i = 0; i < res.data.followUser.length; i++) {
                        if (res.data.followUser[i] === this.videoInfo[0].userId){
                            this.isFollowUser = true;

                        }
                    }
                }

            },
            // 播放
            play (index, item) {
                item.playBol = true
                this.$set(this.videoList, index, item)
                this.dragVideo(index)
                axios.get('http://localhost:8081/video/list/playCount',{
                        params:{
                            id:item.fileId,
                        }
                    }
                );
                // console.log(item.fileId)
            },
            // 暂停
            pause (index, item) {
                document.querySelectorAll('video')[index].pause()
                item.playBol = false
                this.$set(this.videoList, index, item)

            },
            // canvas 绘制
            dragVideo (index) {
                let video = document.querySelectorAll('video')[index],
                    ctx = document.querySelectorAll('canvas')[index].getContext('2d')
                video.play()
                let fps = 1000/30,
                    w = document.querySelectorAll('.video')[index].clientWidth,
                    h = document.querySelectorAll('.video')[index].clientHeight
                video.addEventListener('play', () => {
                    setInterval( () => {
                        ctx.drawImage(video, 0, 0, 320, 176);
                    }, fps)
                })
            },
            format(shijianchuo) {
                //shijianchuo是整数，否则要parseInt转换
                let time = new Date(shijianchuo);
                let y = time.getFullYear();
                let m = time.getMonth() + 1;
                let d = time.getDate();
                let h = time.getHours();
                let mm = time.getMinutes()
                return y + "-" + m + "-" + d + " " + h + ":" + mm;

            },

            onClickLeft () {
                this.$router.back()
            },
            async addReplyNum(data){
                const {res} = await this.$http.get("/video/updateReplyNum/"+data)
            },
            async onPostSuccess (data) {
                this.isPostShow = false
                // 将发布的内容展示到列表

                console.log(data)
                 // this.$router.go(0)
                this.commentList.unshift(data)
                this.reload();
            },
            async onFollow (item) {
                this.followLoading = true
                let message = "";
                console.log(item.userId == this.nowUserId)
                if (item.userId == this.nowUserId){
                    message = '你不能关注你自己！'

                }else {
                    if (this.isFollowUser === true){
                        const {data} = await this.$http.get("/video/addFollow/"+item.userId)
                        console.log(data)
                        const {res}  =await this.$http.get("/userOpera/delFollowUser/"+this.nowUserId + "/"+item.userId)
                        this.isFollowUser =!this.isFollowUser;
                        message="取关成功！！"
                    }
                    else {
                        const {data} = await this.$http.get("/video/addFollow/"+item.userId)
                        console.log(data);
                        const {res}  =await this.$http.get("/userOpera/addFollowUser/"+this.nowUserId + "/"+item.userId)
                        this.isFollowUser =!this.isFollowUser;
                        message="关注成功！！"
                    }

                }
                this.$toast(message)
                this.followLoading = false
            },
            // 评论回复的弹出层
            onReplyClick (comment) {
                // console.log(comment)
                this.currentComment = comment
                // 显示评论回复弹出层
                this.isReplyShow = true
            },
        },

        computed: {
            ...mapGetters([
                'videoList',
                'videoLoading',
                'videoEnd'
            ])
        },
        components: {
            CommentList,
            GoodVideo,
            ColletionVideo,
            CommentPost,
            CommentReply,
            axios
        }
    }
</script>
<style scoped lang="less">
    @import './github-markdown.css';
    .imgUrl{

        width: 80%;
        height: 320px;

    }
    .imgUrlStyle{
        display: table-cell; /*主要是这个属性*/
        vertical-align: middle;
        text-align: center;
    }
    .article-container {


        .main-wrap {
            position: fixed;
            left: 0;
            right: 0;
            top: 20px;
            bottom: 88px;
            overflow-y: scroll;
            background-color: #fff;
        }
        .article-detail {
            .article-title {
                font-size: 30px;
                padding: 50px 30px;
                margin: 0;
                color: #3a3a3a;
            }

            .user-info {
                padding: 0 32px;
                .avatar {
                    width: 70px;
                    height: 70px;
                    margin-right: 17px;
                }
                .van-cell__label {
                    margin-top: 0;
                }
                .user-name {
                    font-size: 24px;
                    color: #3a3a3a;
                }
                .publish-date {
                    font-size: 15px;
                    color: #b7b7b7;
                }
                .follow-btn {
                    width: 170px;
                    height: 58px;
                }
            }

            .article-content {
                padding: 25px 32px;
                /deep/ p {
                    text-align: justify;
                }
            }
        }

        .loading-wrap {
            padding: 200px 32px;
            display: flex;
            align-items: center;
            justify-content: center;
            background-color: #fff;
        }

        .error-wrap {
            padding: 200px 32px;
            display: flex;
            flex-direction: column;
            align-items: center;
            justify-content: center;
            background-color: #fff;
            .van-icon {
                font-size: 122px;
                color: #b4b4b4;
            }
            .text {
                font-size: 30px;
                color: #666666;
                margin: 33px 0 46px;
            }
            .retry-btn {
                width: 280px;
                height: 70px;
                line-height: 70px;
                border: 1px solid #c3c3c3;
                font-size: 30px;
                color: #666666;
            }
        }

        .article-bottom {
            position: fixed;
            left: 0;
            right: 0;
            bottom: 0;
            display: flex;
            justify-content: space-around;
            align-items: center;
            box-sizing: border-box;
            height: 88px;
            border-top: 1px solid #d8d8d8;
            background-color: #fff;
            .comment-btn {
                width: 282px;
                height: 46px;
                border: 2px solid #eeeeee;
                font-size: 15px;
                line-height: 46px;
                color: #a7a7a7;
            }
            .van-icon {
                font-size: 40px;
                .van-info {
                    font-size: 16px;
                    background-color: #e22829;
                }
            }
        }
        .video-wrapper {
            overflow-y: scroll;
            padding: 0.2rem 0rem;
        }
        .item {
            padding: 0rem 0rem;
            .video {
                width: 100%;
                height: 2rem;
                background-color: #000;
                position: relative;
                video {
                    width: 100%;
                    /*display: none;*/
                    height: 100%;
                }
                .canvas-video {
                    width: 0%;
                    height: 0%;
                    position: absolute;
                    left: 0;top: 0;
                    canvas {
                        width: 0%;
                        height: 0%;
                    }
                }

                .play {
                    position: absolute;
                    left: 50%;top: 50%;
                    transform: translate(-50%, -50%);
                    background-color: rgba(0, 0, 0, .3);
                    border-radius: 50%;
                    z-index: 3;
                    svg {
                        color: #fff;
                        font-size: 0.4rem;
                    }
                }
                .title {
                    position: absolute;
                    left: 0;top: 0;
                    line-height: 0.2rem;
                    width: 100%;
                    color: #f4f4f4;
                    padding: 0.04rem 0.1rem;
                    background: linear-gradient(top , rgba(0, 0, 0, .45), rgba(0, 0, 0, 0) 100%);
                    background: -webkit-linear-gradient(top , rgba(0, 0, 0, .45), rgba(0, 0, 0, 0) 100%);
                    small {
                        font-size: 0.12rem;
                    }
                }
                time {
                    position: absolute;
                    right: 0.1rem; top: 0.1rem;
                    font-size: 0.1rem;
                    padding: 0.04rem 0.08rem;
                    border-radius: 0.06rem;
                    background-color: rgba(0, 0, 0, .5);
                    color: #f4f4f4;
                }
                .avatar {
                    width: 0.4rem;
                    height: 0.4rem;
                    border-radius: 50%;
                    position: absolute;
                    bottom: -0.1rem;
                    left: 0.2rem;
                }
            }

            .intro {
                margin-top: 0.1rem;
                padding: 0 0.1rem;
                .source {
                    font-size: 0.14rem;
                }
                .box {
                    >*{
                        margin-left: 0.14rem;
                        font-size: 0.12rem;
                        color: @font-gray;
                    }
                }
            }

        }
    }

</style>
