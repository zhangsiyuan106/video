<template>
    <div class="article-container">
        <!-- 文章详情 -->
        <!-- 导航栏 -->
        <van-nav-bar
                class="page-nav-bar"
                left-arrow
                @click-left="onClickLeft"
                title="微笑头条"
        ></van-nav-bar>

        <!-- 页面内容 -->
        <div class="main-wrap">
            <!-- 加载中 -->
            <div class="loading-wrap" v-if="loading">
                <van-loading color="#3296fa" vertical>加载中</van-loading>
            </div>

            <!-- 加载完成-文章详情 -->
            <div class="article-detail" v-else-if="this.articleInfo.content">
                <!-- 文章标题 -->
                <h3 class="article-title">{{articleInfo.headLine}}</h3>

                <!-- 用户信息 -->
                <van-cell class="user-info" center :border="false">
                    <van-image
                    class="avatar"
                    slot="icon"
                    round
                    fit="cover"
                    :src="srcUrl"
                    />
                    <div slot="title" class="user-name">{{ articleInfo.userName}}</div>
                    <div slot="label" class="publish-date">
                        {{ articleInfo.publishtime | relativeTime }}

                    </div>
                    <van-button
                            :loading="followLoading"
                            v-if="isFollowUser"
                            class="follow-btn"

                            round
                            size="small"
                            @click="onFollow"
                    >已关注</van-button
                    >
                    <van-button
                            :loading="followLoading"
                            class="follow-btn"
                            v-else
                            round
                            type="info"
                            color="#3296fa"
                            round
                            size="small"
                            icon="plus"
                            @click="onFollow"
                    >
                        关注
                    </van-button>
                </van-cell>

                <!-- 文章内容 -->

                <div
                        ref="article-content"
                        class="article-content markdown-body"
                        v-html="articleInfo.content"
                        style="font-size: 20px">
                </div>

                <van-divider>正文结束</van-divider>
                 <!--评论组件 -->
                 <!--评论区域-->
                <comment-list
                        :userId = "nowUserId"
                        :source="articleInfo.id"
                        :list="commentList"
                        @reply-click="onReplyClick"
                />

                <!-- 底部区域 -->
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
                    <van-icon name="comment-o" :info="articleInfo.replynum" color="#777" />
                    <!--&lt;!&ndash; 收藏图标  &ndash;&gt;-->
                    <collection-article
                            v-model="isCollectedArticle"
                            class="btn-item"
                            :article-id="articleInfo.id"
                            :userId="nowUserId"
                    ></collection-article>
                    <!--&lt;!&ndash; 点赞图标 &ndash;&gt;-->
                    <good-article
                            v-model="articleAttitude"
                            class="btn-item"
                            :article-id="articleInfo.id"
                            :userId="nowUserId"
                    ></good-article>
                    <!--&lt;!&ndash; 分享图标 &ndash;&gt;-->
                </div>

                 <!--发布评论-->
                <van-popup v-model="isPostShow" position="bottom">
                    <!-- 把文章id传到发表评论的组件里 -->
                    <comment-post
                            :artId="articleInfo.id"

                            @post-message="updateReplyNum"
                            @post-success="onPostSuccess"
                    />
                </van-popup>
            </div>

             <!--加载失败：404 -->
            <div class="error-wrap" v-else-if="errStatus === 404">
                <van-icon name="failure" />
                <p class="text">该资源不存在或已删除！</p>
            </div>

            <!-- 加载失败：其他未知错误(网络或服务端异常) -->
            <div class="error-wrap" v-else>
                <van-icon name="failure" />
                <p class="text">内容加载失败！</p>
                <van-button class="retry-btn">点击重试</van-button>
            </div>
        </div>

         <!--评论回复-->
        <van-popup v-model="isReplyShow" position="bottom" style="height: 90%">
            <comment-reply
                    v-if="isReplyShow"
                    :comment="currentComment"
                    :userId = "nowUserId"
                    @close="isReplyShow = false"
            />
        </van-popup>
    </div>
</template>
<script>
    import CollectionArticle from '../../components/Collection-article/index'
    import GoodArticle from '../../components/Good-article/index'
    import CommentList from './comment-list'
    import CommentPost from './comment-post'
    import CommentReply from './comment-reply'

    export default {
        inject:['reload'],
        data() {
            return {
                articleAttitude:0,
                articleInfo:{},
                contentInfo:[],
                loading: true, // 加载中的 loading
                nowUserId:0,
                srcUrl: '../../static/bg.jpg',
                followLoading: false, // loading 状态显示与否
                articleFollow:false,
                commentList:[],
                currentComment: {},// 接收 当前点击回复的评论项
                isReplyShow: false, // 评论回复
                isPostShow: false, // 控制发布评论的显示状态
                isCollectedArticle:false,
                isFollowUser:false,//是否关注了作者
                errStatus:0
            }

        },
         async mounted(){
            this.nowUserId = localStorage.getItem("id");
           await   this.getArticleById();
          await  this.getPhoto()
           await  this.findLikeArticle();
        },

        methods: {
            async getPhoto(){
                const {data} = await this.$http.get("/api/user/getPhoto/?id="+this.articleInfo.userid)
                this.srcUrl=data.data;
            },
            more() {
                this.$alert('没有更多信息哦!')
            },
            async getArticleById() {
                try {
                    const {data: res} = await this.$http.get('home/getArticleById?id=' + this.$route.params.id)
                    this.articleInfo = res.data
                    console.log(this.articleInfo)
                } catch (error) {
                    // console.log('获取数据失败', err)
                    if (error.response && error.response.status === 404) {
                        this.errStatus = 404
                    }
                }
                // 无论成功还是失败，都需要关闭 loading
                this.loading = false
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
            async findLikeArticle(){
                const {data: res} = await this.$http.get("/userOpera/findLikeArticle/"+this.nowUserId)
                 console.log( res.data)
                if (res.data=== null) {
                    this.articleAttitude = 0;
                    this.isCollectedArticle = false;
                    this.isFollowUser = false;
                    return;
                }
                if (res.data.likeArticle != null){
                    for (let i = 0; i < res.data.likeArticle.length; i++) {
                        if (res.data.likeArticle[i] === this.articleInfo.id){
                            this.articleAttitude = 1;

                        }
                    }
                }
                if (res.data.collectArticle != null){
                    for (let i = 0; i < res.data.collectArticle.length; i++) {
                        if (res.data.collectArticle[i] === this.articleInfo.id){
                            this.isCollectedArticle = true;

                        }
                    }
                }
               if (res.data.followUser != null){
                   for (let i = 0; i < res.data.followUser.length; i++) {
                       if (res.data.followUser[i] === this.articleInfo.userid){
                           this.isFollowUser = true;

                       }
                   }
               }
            },
            async updateReplyNum(data){
                this.isPostShow = false
                const {res} = await this.$http.get("/home/updateReplyNum/"+data)
                this.reload();
            },
            async onPostSuccess (data) {
                this.commentList.unshift(data)
            },
            async onFollow () {


                this.followLoading = true
                        let message = "";
                        if (this.articleInfo.userid == this.nowUserId){
                            message = '你不能关注你自己！'

                        }
                        else {
                        if (this.isFollowUser == true){
                            const {data} = await this.$http.get("/home/addFollow/"+this.articleInfo.userid)
                            const {res}  =await this.$http.post("/unfollow/"+this.nowUserId + "/"+this.articleInfo.userid )
                            this.isFollowUser =!this.isFollowUser;
                            message="取关成功！！"
                        }
                        else {
                            const {data} = await this.$http.get("/home/addFollow/"+this.articleInfo.userid)
                            const {res}  =await this.$http.post("/follow/"+this.nowUserId + "/"+this.articleInfo.userid );
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

        components: {
            CollectionArticle,
            GoodArticle,
            CommentList,
            CommentPost,
            CommentReply
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
        // .page-nav-bar {
        //   position: fixed;
        // }

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
                padding: 50px 32px;
                margin: 0;
                color: #3a3a3a;
            }

            .user-info {
                padding: 0 32px;
                .avatar {
                    width: 50px;
                    height: 50px;
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
                    width: 100px;
                    height: 38px;
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
                width: 200px;
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
    }
</style>