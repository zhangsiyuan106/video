<template>
    <div>

    <HeaderBar title="发布文章："></HeaderBar>
        <br>
        <div class="m-content">
            <el-form ref="editForm" status-icon :model="editForm" :rules="rules" label-width="80px">
                <el-form-item label="标题" prop="title">
                    <el-input v-model="editForm.title"></el-input>
                </el-form-item>
                <!--<el-form-item label="摘要" prop="description">-->
                    <!--<el-input type="textarea" v-model="editForm.description"></el-input>-->
                <!--</el-form-item>-->
                <el-form-item label="内容" prop="content">
                    <!--引入md文本编辑-->
                    <mavon-editor ref="md"  v-model="editForm.content" :toolbars="toolbars" @imgAdd="$imgAdd"  @imgDel="$imgDel"/>

                </el-form-item>
                <el-form-item>
                    <el-button type="primary" @click="submitForm()">下一步</el-button>
                    <el-button @click="back($router)">取消</el-button>
                </el-form-item>
            </el-form>
        </div>

    </div>
</template>

<script>
    import axios from 'axios'
    import creativeArticleCover from './creativeArticleCover'
    export default {
        name: "creativeEdit",
        data() {
            return {
                img_file:[],
                userName:"",
                userId:0,
                editForm: {
                    articleId: "",
                    title: '',

                    content: ''
                },
                rules: {
                    title: [
                        {required: true, message: '请输入标题', trigger: 'blur'},
                        {min: 2, max: 50, message: '长度在 2 到 50 个字符', trigger: 'blur'}
                    ],
                    content: [
                        {required: true, message: '请输入内容', trigger: 'blur'}
                    ]
                },
                toolbars: {
                    bold: true, // 粗体
                    italic: true, // 斜体
                    header: true, // 标题
                    underline: true, // 下划线
                    strikethrough: true, // 中划线
                    mark: false, // 标记
                    superscript: false, // 上角标
                    subscript: false, // 下角标
                    quote: true, // 引用
                    ol: true, // 有序列表
                    ul: true, // 无序列表
                    link: true, // 链接
                    imagelink: true, // 图片链接
                    code: true, // code
                    table: true, // 表格
                    fullscreen: true, // 全屏编辑
                    readmodel: false, // 沉浸式阅读
                    htmlcode: true, // 展示html源码
                    help: false, // 帮助
                    /* 1.3.5 */
                    undo: true, // 上一步
                    redo: false, // 下一步
                    trash: false, // 清空
                    save: false, // 保存（触发events中的save事件）
                    /* 1.4.2 */
                    navigation: false, // 导航目录
                    /* 2.1.8 */
                    alignleft: true, // 左对齐
                    aligncenter: true, // 居中
                    alignright: true, // 右对齐
                    /* 2.2.1 */
                    subfield: true, // 单双栏模式
                    edit: true,
                    preview: false,// 预览
                    boxShadow: false
                }

            }
        },
        mounted() {
            //创建获取blog的信息
            this.userName = localStorage.getItem("nickname")

            this.userId = localStorage.getItem("id");
            this.editForm.articleId = this.$route.params.articleId;
        },
        methods: {
            async submitForm() {
                if (this.editForm.content == null || this.editForm.content ==""){
                    this.$message.error("内容不能为空")
                    return;
                }
                const markdown = this.editForm.content
                // 渲染的数据 带标签的html页面
                const html = this.$refs.md.d_render;

                let jsonObject={
                    'headLine': this.editForm.title,
                    'content': html,
                    'userId': this.userId,
                    'userName': this.userName
                }
                localStorage.setItem("headLine",this.editForm.title)
                localStorage.setItem("content",html)
                localStorage.setItem("userId",this.userId)
                this.$router.push("/creativeArticleCover")

            },
            async $imgAdd(pos, file) {
                let formData = new FormData();
                formData.append("image", file);
                await this.$http({
                    url: '/home/picFile/upload',
                    method: 'post',
                    data: formData,
                }).then((response)=>{
                    console.log(response.data)
                    if (response.data.status === 200){
                        this.$refs.md.$img2Url(pos, response.data.data)
                    }else {
                        this.$message("上传图片失败")
                    }
                });
            },

            $imgDel(pos) {
                delete this.img_file[pos];
            }

        },
        components:{
            creativeArticleCover
        }

    }
</script>

<style scoped>
    .m-content{
        text-align: center;
        /*max-width: ;*/
        margin: 0 auto;
    }
</style>