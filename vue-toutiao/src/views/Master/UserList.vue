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
      <el-breadcrumb-item>系统管理</el-breadcrumb-item>
      <el-breadcrumb-item>用户管理</el-breadcrumb-item>
    </el-breadcrumb>

<!--   卡片区 用户列表主体部分  引用了全局样式 -->
    <el-card>
<!--      间隙和行宽-->
      <el-row :gutter="25">
        <el-col :span="10">
<!--  搜索区 -->
      <el-input placeholder="请输入搜索内容" v-model="str" clearable @clear="getUserList">
        <el-button slot="append" icon="el-icon-search" @click="getUserList"></el-button>
      </el-input>
        </el-col>

        <el-col :span="8">
          <el-button type="primary" @click="addDialogVisible = true">添加用户</el-button>
        </el-col>
      </el-row>


    <!-- 用户列表  border stripe 边框以及隔行变色  其中prop的值与实体类属性一致 -->
    <el-table :data="userList" border stripe>
      <el-table-column type="index"></el-table-column>
      <el-table-column label="姓名" prop="userName"></el-table-column>
      <el-table-column label="别名" prop="nickname"></el-table-column>
      <el-table-column label="角色权限" prop="perms"></el-table-column>
      <el-table-column label="操作">
        <template slot-scope="scope">
          <!-- 修改 -->
          <el-button type="primary" icon="el-icon-edit" size="mini" @click="showUpdateDialog(scope.row.id)"></el-button>
          <!-- 删除    scope.row 就是每一行数据  scope.row.id每一行的id  -->
          <el-button type="danger" icon="el-icon-delete" size="mini" @click="deleteUser(scope.row.id)" ></el-button>
        </template>
      </el-table-column>
    </el-table>
    <!-- 分页   size-change 每页最大变化-->
    <div class="block">
      <el-pagination
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page="userPage.pageNo"
        :page-sizes="[1, 3, 5, 7]"
        :page-size="userPage.pageSize"
        layout="total, sizes, prev, pager, next, jumper"
        :total="total">
      </el-pagination>

 <!--对话框 新增用户    -->
      <el-dialog title="添加用户" :visible.sync="addDialogVisible" @close="addDialogClosed">
        <el-form :model="addForm" :rules="addFormRules" ref="addFormRef">

          <el-form-item label="用户名" prop="username">
            <el-input v-model="addForm.userName" autocomplete="off"></el-input>
          </el-form-item>
          <el-form-item label="密码" prop="password">
            <el-input v-model="addForm.userPassword" autocomplete="off"></el-input>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button @click="addDialogVisible = false">取 消</el-button>
          <el-button type="primary" @click="addUser">添加</el-button>
        </div>
      </el-dialog>

      <el-dialog title="修改用户" :visible.sync="updateDialogVisible" @close="updateDialogClosed">
        <el-form :model="updateForm" :rules="updateFormRules" ref="updateFormRef">

          <el-form-item label="用户名" prop="username">
            <el-input v-model="updateForm.userName" disabled></el-input>
          </el-form-item>
          <el-form-item label="权限设置" prop="perms">
            <el-input v-model="updateForm.perms" ></el-input>
          </el-form-item>
          <el-form-item label="别名" prop="nickname">
            <el-input v-model="updateForm.nickname" ></el-input>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button @click="updateDialogVisible = false">取 消</el-button>
          <el-button type="primary" @click="updateUser">修改</el-button>
        </div>
      </el-dialog>
    </div>
    </el-card>
  </div>

</template>
<script>
export default {

  name: "UserList",
  data(){
    return{
      username:"",
        srcUrl: '../../src/assets/images/1.png',
        userList:[],
        str:"",
        id:"", //用户id
        addDialogVisible:false,//对话框状态，默认关闭
        updateDialogVisible:false, //修改对话框
        total:0,//总记录数
        userPage: {
        pageNo: 1, //当前页码
        pageSize: 3
      },
      addForm:{
        userName:"",
        userPassword:"",
      },
      addFormRules:{
        userName: [
          { required: true, message: '请输入用户名', trigger: 'blur' },
          { min: 5, max: 12, message: '长度在 3 到 12 个字符', trigger: 'blur' }
        ],
        perms: [
          { required: true, message: '请输入新的权限', trigger: 'blur' },
          { min: 5, max: 12, message: '普通用户oradmin', trigger: 'blur' }
        ],
        nickname: [
          { required: true, message: '请输入新的别名', trigger: 'blur' },
          { min: 5, max: 12, message: '长度在 5 到 12 个字符', trigger: 'blur' }
        ],
      },
      updateFormRules:{
        userPassword: [
          { required: true, message: '请输入密码', trigger: 'blur' },
          { min: 5, max: 12, message: '长度在 5 到 12 个字符', trigger: 'blur' }
        ],
      },
      updateForm:{}
    }
  },
  mounted() {
    window.sessionStorage.setItem("userTotal",this.total);
    this.username = window.sessionStorage.getItem("user");
    this.getUserList();
  },
  methods:{
    logout() {
      window.sessionStorage.clear(); //清除session
      this.$router.push("/master"); //注销回到登录页
    },
    //获取用户列表
    async getUserList(){
      console.log(this.str)
      const {data :res} = await this.$http.get("api/user/userList/?pageNo="+this.userPage.pageNo+"&pageSize="+this.userPage.pageSize+"&str="+this.str);
      console.log(res);
      if (res.flag == "ok"){
        this.userList=res.allUserList;
        //console.log(res.totalElements);
        this.total = res.totalElements;//总的用户数目
      }else {
        this.$message.error(res.flag);
      }
    },
    handleSizeChange(newSize){
      this.userPage.pageSize = newSize;
      this.getUserList();
    },
    //点击下一页触发
    handleCurrentChange(newPage){
      this.userPage.pageNo=newPage;
      this.getUserList();
    },


    //监听添加用户
    addDialogClosed(){
        this.$refs.addFormRef.resetFields();
    },
    addUser(){
      this.$refs.addFormRef.validate(async valid => {
        //valid做验证判断  返回true或者false
        if (!valid) return;
        // 调用get请求  res 也就是后台返回的数据
        const {data: res} = await this.$http.post("/api/user/register", this.addForm);
        console.log(res)
        if (res === 'ok'){
          this.$message.success("添加成功！")
          this.addDialogVisible = false;
           await this.getUserList();
        }else {
          return this.$message.error("添加失败！！")
        }
      });
    },
    async deleteUser(id){
          const confirmResult = await this.$confirm("永久删除此用户，是否继续?","提示",{
            confirmButtonText:"确定",
            cancelButtonText:"取消",
            type:"warning"
          }).catch(err => err);//捕获异常
          if (confirmResult != 'confirm'){ //取消
            return this.$message.info("已取消删除")

          }
         const {data:res} = await this.$http.delete("/user/deleteUser/"+id);
          if (res != 'success'){
            return this.$message.error("删除失败")
          }
          this.$message.success("删除成功");

          await this.getUserList();//刷新列表
        },
    //回显
    async showUpdateDialog(id){
          const {data:res} = await this.$http.get("/user/getUpdateUser/"+id);
          this.updateForm = res;
        //  console.log(this.updateForm)
          this.updateDialogVisible=true; //开启编辑菜单对话框
     },
    //关闭窗口
    updateDialogClosed(){
      this.$refs.updateFormRef.resetFields(); //重置信息
    },
     updateUser(){
      this.$refs.updateFormRef.validate(async valid=>{
        if (!valid) return;
        //发起请求
        const {data:res}=await this.$http.post("/user/updateUser/"+this.username,this.updateForm);
        if (res != 'success'){
          return this.$message.error("修改失败")
        }
        this.$message.success("修改成功");
        this.updateDialogVisible=false; //开启编辑菜单对话框
        await this.getUserList();
      });
    }
  }
};

</script>

<style lang="less" scoped>
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
