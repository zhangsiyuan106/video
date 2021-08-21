<template>
  <div>
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
    <!-- 面包屑导航 -->
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/masterHome' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>作品管理</el-breadcrumb-item>
      <el-breadcrumb-item>评论管理</el-breadcrumb-item>
    </el-breadcrumb>

    <el-card class="box-card">
      <el-form :inline="true"  class="demo-form-inline">
        <el-form-item>
          <el-button  class="el-icon-delete"  @click="deleteAll(selectValue)" :disabled="this.selectValue.length === 0"> 批量删除</el-button>

        </el-form-item>
      </el-form>
<!--       卡片区 评论列表主体部分  引用了全局样式 -->
    <el-table :data="commentList" border stripe @selection-change="selsChange">
       <el-table-column  type="selection" width="55" ></el-table-column>

      <el-table-column label="评论编号" prop="id"></el-table-column>
      <el-table-column label="发布人" prop="nickname"></el-table-column>
      <el-table-column label="评论内容" prop="content"></el-table-column>
      <el-table-column label="评论时间" prop="publishtime"></el-table-column>

      <el-table-column label="文章编号" prop="articleId"></el-table-column>
      <el-table-column label="点赞数" prop="likenum"></el-table-column>
      <el-table-column label="父级ID" prop="parentid"></el-table-column>
      <el-table-column label="操作">
        <template slot-scope="scope">
          <!-- 审核 -->
          <el-tooltip effect="dark" content="审核" placement="top-start" :enterable="false">
            <el-button type="warning" icon="el-icon-setting" size="mini" @click="showExamineDialog(scope.row.id)" ></el-button>
          </el-tooltip>
        </template>
      </el-table-column>
    </el-table>

    <el-dialog title="审核界面" :visible.sync="commentDialogVisible" @close="commentDialogClose">
      <el-form :model="validForm" ref="validFormRef">
        <el-form-item label="评论内容" prop="content">
          <el-input v-model="validForm.content" autocomplete="off" disabled></el-input>
        </el-form-item>

        <el-form-item label="原因" prop="reason">
          <el-input v-model="validForm.reason"></el-input>
        </el-form-item>

      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="deleteComment ">删除</el-button>
        <el-button type="primary" @click="addComment">通 过</el-button>
      </div>
    </el-dialog>
    <!-- 分页   size-change 每页最大变化-->
    <div class="block">
      <el-pagination
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page="commentPage.pageNo"
        :page-sizes="[3, 8, 15,20]"
        :page-size="commentPage.pageSize"
        layout="total, sizes, prev, pager, next, jumper"
        :total="total">
      </el-pagination>
  </div>
    </el-card>
  </div>
</template>

<script>
export default {
  name: "CommentList",
  mounted() {
    this.getCommentList();
  },
  data(){
    return {
      selectValue:[],
      srcUrl: '../../src/assets/images/1.png',
      username:"",
      commentList:[],
      commentDialogVisible:false,
      total:0,
      commId:"",
      articleId:0,
      parentId:0,
      commentPage: {
        pageNo: 1, //当前页码
        pageSize: 3
      },
      validForm:[
        {
          reason:""
        }
      ],
    }
  },
 async mounted(){
    this.username = window.sessionStorage.getItem("user");
   await this.getCommentList()
},
  methods:{
     format (data) {
      var date = new Date(data)
      var Y = date.getFullYear() + '-'
      var M = (date.getMonth() + 1 < 10 ? '0' + (date.getMonth() + 1) : date.getMonth() + 1) + '-'
      var D = (date.getDate() < 10 ? '0' + date.getDate() : date.getDate()) + ' '
      var h = (date.getHours() < 10 ? '0' + date.getHours() : date.getHours()) + ':'
      var m = (date.getMinutes() < 10 ? '0' + date.getMinutes() : date.getMinutes()) + ':'
      var s = (date.getSeconds() < 10 ? '0' + date.getSeconds() : date.getSeconds())
      return Y + M + D + h + m + s
    },

    selsChange(sels) {
      this.selectValue = sels
      // console.log(this.selectValue)
    },

    async getCommentList(){
      const {data:res} = await this.$http.get("/relComment/getCommentList/"+this.commentPage.pageNo+"/"+this.commentPage.pageSize);

      // let arr = [];
      let time;
      if (res.flag == "ok"){
        for (let i = 0; i < res.commList.length; i++) {
           time = this.format(res.commList[i].publishtime)
          // console.log(time)
          res.commList[i].publishtime = time
          // res.commList.push(time)
        }
        this.commentList=res.commList;
        this.commentList.reverse()
        console.log(res.commList)
        this.total=res.totalComments;
      }else {
        return this.$message.error(res.flag);
      }
    },
    handleSizeChange(newSize){
      this.commentPage.pageSize = newSize;
      this.getCommentList();
    },
    //点击下一页触发
    handleCurrentChange(newPage){
      this.commentPage.pageNo=newPage;
      this.getCommentList();
    },
    async  showExamineDialog(id){
      const{data:res} = await this.$http.get("/relComment/getComment/"+id);
      console.log(res)
      if (res == "error") return this.$message.error("错误！！");
      this.commId = id;
      this.articleId  = res.articleId;
      this.parentId = res.parentid;
      this.validForm=res;
      //console.log(res);
      this.commentDialogVisible=true;//开启审核对话框

    },
    logout() {
      window.sessionStorage.clear(); //清除session
      this.$router.push("/master"); //注销回到登录页
    },
    async addComment(){
      this.$message.success("审核成功！！");
      this.commentDialogVisible=false;//开启审核对话框
      await this.getCommentList();
    },
    commentDialogClose(){
      this.$refs.validFormRef.resetFields(); //重置信息

    },
    async deleteComment(){
      this.commentDialogVisible = false

      const{data:res} = await this.$http.delete("/relComment/deleteComment/"+this.commId);
      if (this.parentId == 0){
        this.$http.get("/home/deleteArticleReplyNum/" + this.articleId).then((response) => {
          this.$message.success("删除成功！！");
        })
        await this.getCommentList();
      } else {
        //更新父评论的回复数
        await this.$http.get("/relComment/deleteReplyNum/"+this.parentId).then((res) =>{
          if (res.status == 200) 
          this.$message.success("删除成功！！");
        });
        await this.getCommentList();
      }

    },
    async updateAllReplyNum(ids){
      console.log(ids)
      this.$http.get("/relComment/updateAllReplyNum/"+ids).then(resp =>{

      })
    },
    async deleteAll() {
      var ids = this.selectValue.map(item => item.id).join()//获取所有选中行的id组成的字符串，以逗号分隔
      console.log(ids)
      var id = ids.split(",")
      console.log(id)
      const confirmResult =  await  this.$confirm('此操作将永久删除' + id.length + '条评论, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).catch(err => err);

      if (confirmResult != 'confirm'){
        return this.$message.info("已取消删除")
      } else {
        // this.updateAllReplyNum(id);
        this.$http.delete("/relComment/deleteCommByIds/"+id).then(resp => {
          if (resp.status == 200){
            this.getCommentList();//刷新列表
            this.$message.success("删除成功！！！")
          }else {
            this.$message.fail("删除失败！！！")
          }
        })
      }
    }


  }
}
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
