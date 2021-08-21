<template>

    <div class="video-upload-wrapper">
        <div class="video-upload">
        <HeaderBar title="视频发布"></HeaderBar>
            <!--<van-icon name="video" color="blue" size="88" class="video-icon"/>-->
        <el-upload
                class="upload-demo"
                ref="upload"
                :action="uploadUrl"
                :on-preview="handlePreview"
                :on-remove="handleRemove"
                :file-list="fileList"
                :auto-upload="false"
                :data="itemForm">

            <el-button slot="trigger" size="small" type="primary" class="selectVideo">选取文件</el-button>
            <!--<el-button @click="videoSrcUrl">显示视频</el-button>-->

            <el-form ref="itemForm" class="itemForm">
                <el-form :inline="true" :model="formInline" class="demo-form-inline" ref="itemForm">
                    <el-form-item class="video-type">
                        <el-select v-model="formInline.region" placeholder="视频类型">
                            <el-option label="音乐" value="音乐"></el-option>
                            <el-option label="游戏" value="游戏"></el-option>
                            <el-option label="舞蹈" value="舞蹈"></el-option>
                            <el-option label="娱乐" value="娱乐"></el-option>
                            <el-option label="vlog" value="vlog"></el-option>
                        </el-select>
                    </el-form-item>
                </el-form>
            </el-form>

            <el-button size="small" type="primary" @click="submitUpload" class="uploadButton">发布视频</el-button>
        </el-upload>
        </div>
        <van-divider  :style="{ color: '#1989fa', borderColor: '#1989fa', padding: '0 16px' }">视频</van-divider>
        <div class="picture">
        </div>
    </div>
</template>

<script>
    import HeaderBar from  '../../components/HeaderBar/index.vue'
    export default {
        name: "videoupload",
        comments:{
            HeaderBar
        },
        data() {
            return {
                dialogImageUrl: '',
                dialogVisible: false,
                uploadUrl: 'http://localhost:8081/video/upload',
                formInline: {
                    user: '',
                    region: ''
                },
                itemForm: {

                    userId: '',

                    label:'',
                },
                fileList: [{name: '', url: ''},],
                fd: '',


            };
        },
        methods: {
            submitUpload() {
                this.itemForm.label=this.formInline.region
                this.itemForm.userId=localStorage.getItem("id")
                console.log(this.itemForm.label)
                console.log(this.itemForm.userId)
                this.$refs.itemForm.validate(valid => {
                    if (valid) {
                        this.fd = new FormData()
                        this.fd.append('userId', this.itemForm.userId)

                        this.fd.append('label', this.itemForm.label)


                    } else {
                            this.$message({
                                showClose: true,
                                type: 'error',
                                message: '请检查表单信息的正确性'
                            })
                            return false

                    }
                });
                this.$refs.upload.submit();
                this.$message.success("发布成功！")
                this.$router.push("/video")
            },
            videoSrcUrl(){
                console.log(this.videoname)
            },
            handleRemove(file, fileList) {
                console.log(file, fileList);
            },
            handlePreview(file) {
                console.log(file);
            },

        },


    }
</script>

<style lang="less" scoped>
.selectVideo{
    display: inline-block;
    margin-left: 5px;
    /*position: relative;*/
    float: left;
    margin-top: 5px;
}

.itemForm{
    display: inline-block;
    /*float: left;*/
    float: right;
    margin-right: 40px;

}
    .video-upload-wrapper{

        background-size: 100%;
        font-size: 24px;

    }
    .video-type{
        display: inline-block;
    }
    .uploadButton{
        display: inline-block;
        margin-left: 5px;
        /*position: relative;*/
        float: left;
        margin-top: 5px;

    }
    .uploadButton{
        margin-top: 20px;
    }

</style>
