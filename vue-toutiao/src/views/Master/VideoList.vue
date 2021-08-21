<template>
  <div id="Logs">
    <el-header>
      <div>
        <img :src=srcUrl width="120px">
        <span style=" font-size: 30px">天天资讯管理系统</span>
      </div>

      <span>
          <span style="color:white; font-size: 20px">欢迎,{{username}}！</span>
         <el-button type="danger" @click="logout">注销</el-button>
      </span>
    </el-header>
    <br>
    <!-- 面包导航 -->
    <el-breadcrumb separator="/" style="padding-left:10px;padding-bottom:10px;font-size:12px;">
      <el-breadcrumb-item :to="{ path: '/masterHome' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>作品管理</el-breadcrumb-item>
      <el-breadcrumb-item>视频管理</el-breadcrumb-item>
    </el-breadcrumb>
    <!-- 右侧卡片区域 -->
    <!-- 用户列表卡片区 -->
    <el-card class="box-card">

      <el-form :inline="true"  class="demo-form-inline">

        <el-form-item label="视频类型">
          <el-input    placeholder="视频类型" v-model="kwd"></el-input>
        </el-form-item>

        <el-form-item>
          <el-button type="primary" icon="el-icon-search" @click="search">查询</el-button>
        </el-form-item>
      </el-form>

      <!-- 表格区域 -->
      <template>
        <el-table :data="mediaData" border stripe>
          <el-table-column type="index"></el-table-column>
          <el-table-column label="视频名称" prop="fileName"></el-table-column>
          <el-table-column label="视频类型" prop="mimeType"></el-table-column>
          <el-table-column label="视频封面" prop="imgPath" width="350">
            <template slot-scope="scope">
                <img :src="scope.row.tag + scope.row.imgPath" width="350px" height="350px">
            </template>
          </el-table-column>
          <el-table-column label="视频链接" prop="fileUrl">
            <template slot-scope="scope">
              <el-link :href="scope.row.tag + scope.row.fileUrl"  target="_blank" icon="el-icon-video-play">播放</el-link>
            </template>
          </el-table-column>
          <el-table-column label="操作">
            <template slot-scope="scope">
              <!-- 操作 -->
              <el-tooltip effect="dark" content="下架视频" placement="top-start" :enterable="false">
                <el-button type="danger" icon="el-icon-delete" size="mini" @click="deleteMedia(scope.row.fileId)"></el-button>
              </el-tooltip>
            </template>
          </el-table-column>
        </el-table>
      </template>
      <!-- 分页 -->
      <el-pagination
        style="margin-top:10px;"
        background
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page="this.mediaPage.pageNo"
        :page-sizes="[3, 5, 8, 10]"
        :page-size="this.mediaPage.pageSize"
        layout="total, sizes, prev, pager, next, jumper"
        :total="total"
      ></el-pagination>
    </el-card>
  </div>
</template>
<script>
/* eslint-disable */
export default {
  name: "Video",
  data() {
    return {
      kwd:"",
      srcUrl: '../../src/assets/images/1.png',
      username:"",

      mediaData:[],
      total:0,
      time:"",
      mediaPage:{
        pageNo: 1, //当前页码
        pageSize: 3
      }
    };
  },
  mounted() {
    this.username = window.sessionStorage.getItem("user");
    this.getMediaList();
  },
  methods: {
    logout() {
      window.sessionStorage.clear(); //清除session
      this.$router.push("/master"); //注销回到登录页

    },
    async getMediaList(){
      const {data:res}  = await  this.$http.get("/videoMaster/getVideoList/"+this.mediaPage.pageNo + "/"+this.mediaPage.pageSize);
      if (res.flag === "error") return this.$message.error("获取视频失败")
      this.mediaData = res.mediaList;
      console.log(this.mediaData)
      this.total = res.totalMedias;
      window.sessionStorage.setItem("totalMedia", this.total)

    },

    handleSizeChange(newSize){
      this.mediaPage.pageSize = newSize;
      this.getMediaList();
    },
    //点击下一页触发
    handleCurrentChange(newPage){
      this.mediaPage.pageNo= newPage;
      this.getMediaList();
    },
    async deleteMedia(id){
      const confirmResult = await this.$confirm("下架该视频，是否继续?","提示",{
        confirmButtonText:"确定",
        cancelButtonText:"取消",
        type:"warning"
      }).catch(err => err);//捕获异常
      if (confirmResult != 'confirm'){ //取消
        return this.$message.info("已取消下架")

      }
      const {data:res} = await this.$http.delete("/videoMaster/deleteMedia/"+id);
      if (res != 'success'){
        return this.$message.error("下架失败")
      }
      this.$message.success("下架成功");
      this.$http.delete("/videoComment/deleteAll/"+id).then((res) =>{
      })
      await this.getMediaList();//刷新列表
    },
    async search(){
      const {data:res}  = await  this.$http.get("/videoMaster/getMediaByMimeType/?mimeType="+this.kwd)
      this.mediaData = res.data;
      this.total = res.data.length;
    },
    async deleteAll(){
      const confirmResult = await this.$confirm("下架所有视频，是否继续?","提示",{
        confirmButtonText:"确定",
        cancelButtonText:"取消",
        type:"warning"
      }).catch(err => err);//捕获异常
      if (confirmResult != 'confirm'){ //取消
        return this.$message.info("已取消下架")

      }
      if (this.total === 0){
        return this.$message.error("视频列表为空")
      }

      const {data:res} = await this.$http.delete("/videoMaster/deleteAllMedia");
      if (res != 'success'){
        return this.$message.error("下架失败")
      }
      this.$message.success("下架成功");

    },
  }

};
</script>

<style   lang="less" scoped>
  .el-header {
    background-color: #2b8eff;
    display: flex;
    justify-content: space-between;// 左右贴边
    padding-left: 0%;// 左边界
    align-items: center;// 水平
    color: #ffffff;
    font-size: 20px;
    > div { //左侧div加布局
      display: flex;
      align-items: center;
      span {
        margin-left: 15px;
      }
    }
  }
</style>
