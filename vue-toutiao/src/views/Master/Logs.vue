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
      <el-breadcrumb-item>日志管理</el-breadcrumb-item>
      <el-breadcrumb-item>系统日志</el-breadcrumb-item>
    </el-breadcrumb>
    <!-- 右侧卡片区域 -->
    <!-- 用户列表卡片区 -->
    <el-card class="box-card">

    <el-form :inline="true"  class="demo-form-inline">

        <el-form-item>
          <el-input placeholder="操作人" v-model="str" clearable @clear="getLogList">
            <el-button slot="append" icon="el-icon-search" @click="getLogList"></el-button>
          </el-input>
        </el-form-item>
        <el-form-item>
          <el-button  class="el-icon-delete"  @click="deleteAll(selectValue)" :disabled="this.selectValue.length === 0"> 批量删除</el-button>
        </el-form-item>
      </el-form>

      <!-- 表格区域 -->
      <template>
        <el-table :data="LogData" border stripe @selection-change="selsChange">
           <el-table-column  type="selection" width="55" ></el-table-column>

          <el-table-column type="index" label="序号"></el-table-column>
          <el-table-column label="操作人" prop="username"></el-table-column>
          <el-table-column label="操作模块" prop="module"></el-table-column>
          <el-table-column label="操作描述" prop="description"></el-table-column>
          <el-table-column label="操作时间" >
            <template slot-scope="scope">
                <p v-text="format(scope.row.createAt)"></p>
            </template>
          </el-table-column>
          <el-table-column label="操作内容" prop="content"></el-table-column>
          <el-table-column label="操作Ip" prop="ip"></el-table-column>

          <el-table-column label="操作">
            <template slot-scope="scope">
              <!-- 操作 -->
              <el-tooltip effect="dark" content="删除日志" placement="top-start" :enterable="false">
                <el-button type="danger" icon="el-icon-delete" size="mini" @click="deleteLog(scope.row.id)"></el-button>
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
        :current-page="this.logPage.pageNum"
        :page-sizes="[5, 10, 15, 20]"
        :page-size="this.logPage.pageSize"
        layout="total, sizes, prev, pager, next, jumper"
        :total="total"
      ></el-pagination>
    </el-card>
  </div>
</template>

<script>
export default {
  data() {
    return {
      str:"",
      selectValue:[],
      srcUrl: '../../src/assets/images/1.png',
      LogData: [],
      username:"",
      time:"",
      total: 0, //总共多少条数据
      logPage: {
        pageNum: 1,
        pageSize: 5} //查询对象
    };
  },
  methods: {
     add0(m)
     {return m<10?'0'+m:m },
 format(shijianchuo)
{
//shijianchuo是整数，否则要parseInt转换
  var time = new Date(shijianchuo);
  var y = time.getFullYear();
  var m = time.getMonth()+1;
  var d = time.getDate();
  var h = time.getHours();
  var mm = time.getMinutes();
  var s = time.getSeconds();
  return y+'-'+this.add0(m)+'-'+this.add0(d)+' '+this.add0(h)+':'+this.add0(mm)+':'+this.add0(s);
},

selsChange(sels){
      this.selectValue = sels;
      console.log(sels);
    },
    logout() {
      window.sessionStorage.clear(); //清除session
      this.$router.push("/master"); //注销回到登录页

    },
    //加载系统日志列表
    async getLogList() {
      const { data: res } = await this.$http.get("/log/getLogList/?pageNo="+this.logPage.pageNum+"&pageSize="+this.logPage.pageSize+"&str="+this.str)
      console.log(res)
        if (res.flag == "error") return this.$message.error("查询错误");
        this.LogData=res.allLog;
        this.total=res.count;
    },
    //删除日志
    async deleteLog(id){
      const confirmResult = await this.$confirm("永久删除此日志，是否继续?","提示",{
        confirmButtonText:"确定",
        cancelButtonText:"取消",
        type:"warning"
      }).catch(err => err);//捕获异常
      if (confirmResult != 'confirm'){ //取消
        return this.$message.info("已取消删除")

      }
      const {data:res} = await this.$http.delete("/log/deleteLog/"+id);
      if (res != 'success'){
        return this.$message.error("删除失败")
      }
      this.$message.success("删除成功");
       await this.getLogList();

    },
    async deleteAll() {
      var ids = this.selectValue.map(item => item.id).join()//获取所有选中行的id组成的字符串，以逗号分隔
      console.log(ids)
      var id = ids.split(",")
      console.log(id)
      const confirmResult =  await  this.$confirm('此操作将永久删除' + id.length + '条日志, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).catch(err => err);

      if (confirmResult != 'confirm'){
        return this.$message.info("已取消删除")
      } else {

        this.$http.delete("/log/deleteLogByIds/"+id).then(resp => {
          if (resp.status == 200){
            this.getLogList();//刷新列表
            this.$message.success("删除成功！！！")
          }else {
            this.$message.fail("删除失败！！！")
          }
        })
      }
    },

    //改变页码
    handleSizeChange(newSize) {
      this.logPage.pageSize = newSize;
      this.getLogList();
    },
    //翻页
    handleCurrentChange(current) {
      this.logPage.pageNum = current;
      this.getLogList();
    }
  },
  mounted() {
      this.getLogList();
      console.log(this.LogData)
      //页面加载时保存原来的信息
      this.username = window.sessionStorage.getItem("user");
    },

};
</script>

<style   lang="less" scoped>
.el-tooltip__popper{max-width: 400px;}
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
