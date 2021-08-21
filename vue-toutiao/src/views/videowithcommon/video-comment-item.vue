<template>
    <!-- 用户评论组件 -->

    <van-cell class="comment-item">
        <van-image
                slot="icon"
                class="avatar"
                round
                fit="cover"
                :src="srcUrl"
        />

        <div slot="title" class="title-wrap">
            <!-- 用户名 -->
            <!--{{comment.id}}-->
            <div class="user-name">{{ comment.nickname }}</div>
            <!-- 有就显示点赞的数量，没有就显示“赞”这个字 -->
            <!-- comment.is_liking是否为true, true的话就显示实心的点赞，false就是空心的点赞 -->
            <!-- v-bind=class === :class 属性绑定， class也同样拥有liked类名 -->
            <van-button
                    class="like-btn"
                    :icon="isLikeVideoComment? 'good-job' : 'good-job-o'"
                    :class="{ liked: isLikeVideoComment }"
                    :loading="commentLoading"
                    @click="onCommentLike"
            >{{ comment.likenum || '赞' }}</van-button
            >
        </div>

        <div slot="label">
            <!-- 评论内容 -->
            <p class="comment-content">{{ comment.content }}</p>
            <div class="bottom-info">
                <!-- 评论的时间，并处理成 相对时间 通过管道符 | relativeTime -->
                <span class="comment-pubdate">
            {{comment.publishtime | relativeTime}}</span>
                <!-- 回复的数量 -->
                <!-- $emit( 发布事件，传参) -->
                <van-button
                        class="reply-btn"
                        round
                        @click="$emit('reply-click', comment)"
                >回复 {{ comment.replynum }}</van-button
                >
            </div>
        </div>
    </van-cell>
</template>

<script>

    export default {
        name: 'video-comment-item',
        data () {
            return {
                commentLoading: false,
                srcUrl: '',
                isLikeVideoComment:false
            }
        },
        props: {
            comment: {
                type: Object,
                required: true
            },
            nowUserId:{
                type: [Number,String,Object]
            }
        },
        created () {},
        async mounted () {
           await this.findLikeComment();
           await this.getPhoto();
        },
        methods: {
            async findLikeComment(){
                const {data: res} = await this.$http.get("/userOpera/findLikeArticle/"+this.nowUserId)
                // console.log(res.data)
                if (res.data == null){
                    this.isLikeVideoComment = false;
                    return;
                }
                if (res.data.likeVideoComment != null){
                    for (let i = 0; i < res.data.likeVideoComment.length; i++) {
                        if (res.data.likeVideoComment[i] === this.comment.id){

                            this.isLikeVideoComment = true;

                        }
                    }
                }
            },
            async onCommentLike () {
                this.commentLoading = true

                //正确
                try {
                    if (this.isLikeVideoComment) {
                        // 已点赞，取消点赞
                        // 删除点击的评论点赞的id
                        // 点赞的数量减一

                        const {data} = await this.$http.get("/videoComment/deleteLikeNum?id="+this.comment.id)
                        const {res} = await this.$http.get("/userOpera/delLikeVideoComment/"+this.nowUserId + "/"+this.comment.id)
                        console.log(data)
                        this.comment.likenum--
                    } else {
                        // 没有赞，添加点赞
                        // 添加点击的评论点赞的id
                        // 点赞的数量加一

                        const {data} = await this.$http.get("/videoComment/updateLikeNum?id="+this.comment.id)
                        const {res} = await this.$http.get("/userOpera/addLikeVideoComment/"+this.nowUserId + "/"+this.comment.id)

                        console.log(data)
                        this.comment.likenum++
                    }
                    // 更新视图
                    this.isLikeVideoComment =  !this.isLikeVideoComment

                    // 错误
                } catch (err) {
                    this.$toast('操作失败，请重试')
                }
                this.commentLoading = false
            },
            async getPhoto(){
                const {data} = await this.$http.get("/api/user/getPhoto/?id="+this.comment.userid)
                this.srcUrl=data.data;
            },
        }
    }
</script>

<style scoped lang="less">
    .comment-item {
        .avatar {
            width: 50px;
            height: 50px;
            margin-right: 15px;
        }
        .title-wrap {
            display: flex; // 弹性布局
            justify-content: space-between; // 两端布局
            align-items: center; // 侧轴距中
            .user-name {
                color: #406599;
                font-size: 18px;
            }
        }
        .comment-content {
            font-size: 20px;
            color: #222;
            word-break: break-all;
            line-height: 40px;
            text-align: justify; // 左右两端都对齐，且在父元素的内边距里
        }
        .comment-pubdate {
            font-size: 19px;
            color: red;
            margin-right: 25px;
        }
        .bottom-info {
            display: flex;
            align-items: center;
        }
        .reply-btn {
            width: 105px;
            height: 40px;
            line-height: 48px;
            font-size: 21px;
            color: #222;
        }
        .like-btn {
            height: 30px;
            padding: 0;
            border: none;
            font-size: 19px;
            line-height: 30px;
            margin-right: 7px;
            .van-icon {
                font-size: 30px;
            }
            &.liked {
                color: rgb(200, 60, 80);
            }
        }
    }
</style>
