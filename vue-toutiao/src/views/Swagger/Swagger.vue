<template>
<div>
    <!-- 面包导航 -->
    <el-breadcrumb separator="/" style="padding-left:10px;padding-bottom:10px;font-size:12px;">
        <el-breadcrumb-item :to="{ path: '/masterHome' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>其他管理</el-breadcrumb-item>
        <el-breadcrumb-item>接口文档</el-breadcrumb-item>
    </el-breadcrumb>
    <el-card>
        <iframe src="http://localhost:8081/swagger-ui.html" frameborder="no" border="0" marginwidth="0" marginheight="0"  allowtransparency="yes" style="overflow:hidden;" height="800px;" width="100%"></iframe>
    </el-card>
</div>
</template>
<script>
  export default {
    name: "Swagger"
  }

</script>
