<template>
  <div>
    <el-breadcrumb separator="/" style="padding-left:10px;padding-bottom:10px;font-size:12px;">
      <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>其他管理</el-breadcrumb-item>
      <el-breadcrumb-item>日志监控</el-breadcrumb-item>
    </el-breadcrumb>
    <iframe src="http://localhost:8081/druid/index.html" frameborder="no" border="0" marginwidth="0" marginheight="0"  allowtransparency="yes" style="overflow:hidden;" height="800px;" width="100%"></iframe>

  </div>



</template>
<script>
export default {
  name: "Druid"
}

</script>
