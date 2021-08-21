<template>
    <div class="comment-list">
        <van-list
                v-model="loading"
                :finished="finished"
                finished-text="没有更多了"
                :error.sync="error"
                :immediate-check="false"
                error-text="加载失败，请点击重试！！！"
                @load="onLoadComment"
        >
            <comment-item
                    v-if="item"
                    v-for="(item, index) in commentList"
                    :key="index"
                    :now-user-id="userId"
                    :comment="item"
                    @reply-click="$emit('reply-click', $event)"
            />
        </van-list>
    </div>
</template>

<script>

// 导入评论的每一项的模板
import CommentItem from './video-comment-item'

export default {
    name: 'video-comment-list',
    components: {
         CommentItem
    },
    props: {
        type: {
            type: String,
            // 自定义 Prop 数据验证
            vaildator (value) {
                return ['', 'c'].includes(value)
            },
            default: ''
        },
        getMediaId:{
            type: Number,
            required: true
        },
        userId:{
            type:[Number,String,Object]
        },
        list:{
            type:Array
        }
    },

    data () {
        return {
            commentList:this.list,
            loading: false,
            // getMediaId:"",
            finished: false,
            offset: null, // 获取下一页数据的标记
            limit: 10, // 每页的的数据条数
            error: false
        }
    },
    async mounted () {
        this.loading = true
        await  this.onLoadComment()
    },

    methods: {
        async onLoadComment () {
             console.log(this.getMediaId)
            if (this.type === '') {
                const {data:res} =await this.$http.get("/videoComment/getVideoCommByVideoId/"+this.getMediaId)
                this.commentList=res.data

                this.loading = false;
                this.finished = true;

            } else if (this.type === 'c'){

                const {data:res} =await this.$http.get("/videoComment/getVideoCommByParentId/"+this.getMediaId)
                this.commentList=res.data


                this.loading = false;
                this.finished = true;
            }

        },

    },
    watch:{
        '$route':'onLoadComment'
    }
}
</script>

<style scoped lang="less"></style>
