<template>
    <!--文章封面创作组件-->

    <div >
        <HeaderBar title="封面设置"></HeaderBar>
        <br>

        <van-panel :title="'文章标题：'  +headLine" :desc="'作者：'  +userName">

            <template #footer>
                <van-button size="normal" type="info" icon="success" round  style="margin-left:30%; margin-top: 12%" @click="submitArticle">发布</van-button>

                <van-button size="normal" icon="cross" round @click="back($router)">取消</van-button>
            </template>
        </van-panel>

                        <div class="upload-wrapper" v-show="radio=='单图'">
                            <el-upload
                                    :action="actionUrl"
                                    list-type="picture-card"
                                    :limit="1"
                                    :on-success = "uploadSuccess"
                                    :on-preview="handlePictureCardPreview"
                                    :on-remove="handleRemove">
                                <i class="el-icon-plus"></i>
                            </el-upload>

                            <el-dialog :visible.sync="dialogVisible">
                                <img width="100%" :src="articleCoverUrl" alt="" class="img">
                            </el-dialog>

                        </div>


                    <div class="btn-item">
                        <van-divider></van-divider>
                        <span class="text">封面设置</span>
                        <div class="radio-item">
                            <el-radio-group v-model="radio">
                                <el-radio-button label="无图" ></el-radio-button>
                                <el-radio-button label="单图"></el-radio-button>
                            </el-radio-group>

                        </div>
                    </div>

    </div>
</template>

<script>
    export default {
        name: "creativeArticleCover",
        data() {
            return {
                articleCoverUrl: '',
                dialogVisible: false,
                userName:"",
                headLine:"",
                radio:"无图",
                actionUrl:"",
                articleId:0,
                userId:0,
                content:""

            };
        },
        mounted(){
            this.headLine  =  localStorage.getItem("headLine")
            this.content =  localStorage.getItem("content")
           this.userId =  localStorage.getItem("userId")
            this.userName = localStorage.getItem("nickname")
            this.actionUrl = "http://localhost:8081/home/articleCover/upload";
        },
        methods: {
            async submitArticle(){
              if (this.radio === '无图'){
                    this.actionUrl = "";
                  let jsonObject={
                      'headLine': this.headLine,
                      'content': this.content,
                      'userId': this.userId,
                      'userName': this.userName,
                      'imgUrl': ""
                  }
                  await this.$http({
                      url:'/home/addArticle/',
                      method: 'post',
                      headers:{'Content-Type':'application/json'},
                      data:jsonObject
                  }).then((res) => {
                      if (res.data.status === 200){
                          this.$message.success("发布成功！！")
                          console.log(this.articleId)

                          this.$router.push("/account")
                      }else {
                          this.$message.error("发布失败！")
                      }
                  });

              }else {

                  let jsonObject={
                      'headLine': this.headLine,
                      'content': this.content,
                      'userId': this.userId,
                      'userName': this.userName,
                      'imgUrl': this.articleCoverUrl
                  }
                  await this.$http({
                      url:'/home/addArticle/',
                      method: 'post',
                      headers:{'Content-Type':'application/json'},
                      data:jsonObject
                  }).then((res) => {
                      if (res.data.status === 200){
                          this.$message.success("发布成功！！")


                          this.$router.push("/account")
                      }else {
                          this.$message.error("发布失败！")
                      }
                  });
              }
            },
            uploadSuccess(response, file, fileList){
                this.articleCoverUrl = response.data.pictureUrl
            },
            handleRemove(file, fileList) {

            },
            handlePictureCardPreview(file) {
                this.articleCoverUrl = file.url;
                this.dialogVisible = true;
            }
        }
    }
</script>

<style>
    .
    .img{
        display: inline-block;
        float: right;
    }
    .upload-wrapper{
        margin-left: 5%;
        margin-top: 1%;
        width: 50%;
    }
    .text{
        margin-left: 5%;
        margin-top: 2%;
        display: inline-block;
        float: left;
        font-size: 20px;
        font-weight: 700;
    }
    .btn-item{
        margin-top: 20%;
        width: 500px;
        vertical-align: top;
    }
    .radio-item{
        vertical-align: top;
        margin-left: 45%;
    }

</style>
