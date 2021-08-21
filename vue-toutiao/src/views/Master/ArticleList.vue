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
      <el-breadcrumb-item>文章管理</el-breadcrumb-item>
    </el-breadcrumb>
    <!-- 右侧卡片区域 -->
    <!-- 用户列表卡片区 -->
    <el-card class="box-card">
      <el-form :inline="true"  class="demo-form-inline">
        <el-form-item>
          <el-button  class="el-icon-delete"  @click="deleteAll(selectValue)" :disabled="this.selectValue.length === 0"> 批量删除</el-button>

        </el-form-item>
      </el-form>

      <!-- 表格区域 -->
      <template>
        <el-table :data="artData" border stripe  @selection-change="selsChange">
           <el-table-column  type="selection" width="55" ></el-table-column>
          <el-table-column label="序号" type="index" width="60"></el-table-column>

          <el-table-column label="文章内容" prop="content" width="1000">

            <template slot-scope="scope">
              <p v-html='scope.row.content'></p>
            </template>
          </el-table-column>

          <el-table-column label="文章评论数" prop="replynum" ></el-table-column>
          <el-table-column label="文章作者" prop="userName"></el-table-column>
          <el-table-column label="操作">
            <template slot-scope="scope">
              <!-- 操作 -->
              <el-tooltip effect="dark" content="删除文章" placement="top-start" :enterable="false">
                <el-button type="danger" icon="el-icon-delete" size="mini" @click="deleteArt(scope.row.id)"></el-button>
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
        :current-page="this.articlePage.pageNo"
        :page-sizes="[3, 5, 8, 10]"
        :page-size="this.articlePage.pageSize"
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
      selectValue:[],
      srcUrl: '../../src/assets/images/1.png',
      username:"",
      artData:[],
      total:0,
      time:"",
      articlePage:{
        pageNo: 1, //当前页码
        pageSize: 3
      }
    };
  },
  mounted() {
    this.username = window.sessionStorage.getItem("user");

    this.getArtList();
  },
  methods: {
    logout() {
      window.sessionStorage.clear(); //清除session
      this.$router.push("/master"); //注销回到登录页
    },
    selsChange(sels) {
      this.selectValue = sels
      // console.log(this.selectValue)
    },
    async getArtList() {
      const {data: res} = await this.$http.get("/home/getArticleByPage/" + this.articlePage.pageNo + "/" + this.articlePage.pageSize);
      if (res.flag === "error") return this.$message.error("获取文章失败")
      this.artData = res.articleList;
      this.total = res.totalArticle;
    },

    handleSizeChange(newSize) {
      this.articlePage.pageSize = newSize;
      this.getArtList();
    },
    //点击下一页触发
    handleCurrentChange(newPage) {
      this.articlePage.pageNo = newPage;
      this.getArtList();
    },
    async deleteArt(id) {
      const confirmResult = await this.$confirm("删除该文章，是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).catch(err => err);//捕获异常

      if (confirmResult != 'confirm') { //取消
        return this.$message.info("已取消删除")

      }
      const {data: res} = await this.$http.get("/home/deleteArticleById/" + id);
      if (res != 'success') {
        return this.$message.error("删除失败")
      }
      this.$message.success("删除成功");
      this.$http.delete("/relComment/deleteAll/"+id).then((res) =>{
          console.log(res)
      })
      await this.getArtList();//刷新列表
    },
    async deleteAll() {
      var ids = this.selectValue.map(item => item.id).join()//获取所有选中行的id组成的字符串，以逗号分隔
      console.log(ids)
      var id = ids.split(",")
      console.log(id)
      const confirmResult =  await  this.$confirm('此操作将永久删除' + id.length + '篇文章, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).catch(err => err);

      if (confirmResult != 'confirm'){
        return this.$message.info("已取消删除")
      } else {

          this.$http.delete("/home/deleteArticleByIds/"+id).then(resp => {
            if (resp.status == 200){
              this.getArtList();//刷新列表
              this.$message.success("删除成功！！！")
            }else {
              this.$message.fail("删除失败！！！")
            }
          })
      }
    }
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
