<template>
  <el-container class="home-container">
    <el-header>
      <div>
        <img :src=srcUrl class="logo_img">
        <span style=" font-size: 30px">天天资讯管理系统</span>
      </div>

      <span>
         <el-button type="danger" @click="logout">注销</el-button>
      </span>
    </el-header>
    <el-container>
<!--侧边-->
      <el-aside :width="isCollapse?'50px':'200px'">
        <div class="toggle_box" @click="toggleCollapse" >|||</div>
        <el-menu :default-active="activePath"
                 active-text-color="#409eff"
                 background-color="#2b8eff"
                 unique-opened
                 text-color="white"
                 :collapse="isCollapse"
                 :router="true"
                 :collapse-transition="false">
        <MenuTree :menuList="this.menuList"></MenuTree>
        </el-menu>
      </el-aside>
      <el-main>
        <router-view></router-view>
          <p style=" font-size: 30px;text-align: center;color: #ff1512">欢迎,管理员{{username}}！</p>
          <el-divider></el-divider>
          <el-calendar v-model="value">
          </el-calendar>

      </el-main>
    </el-container>
  </el-container>

</template>
<script>
import MenuTree from "../../components/MenuTree/MenuTree";
export default {
  components:{
    MenuTree
  },
    data() {
        return {
            value:new Date(),
            srcUrl: '../../src/assets/images/1.png',
            isCollapse:false,
            activePath: "",
            username:"",
            menuList:[  //菜单栏
              {
                "id": 1,
                "parentId": 0,
                "menuName": "系统管理",
                "url": "",
                "icon": "el-icon-setting",
                "orderNum": 1,
                "open": 1,
                "disabled": false,
                "perms": null,
                "type": 0,
                "children": [
                  {
                    "id": 253,
                    "parentId": 1,
                    "menuName": "欢迎页面",
                    "url": "/masterWelHome",
                    "icon": "el-icon-star-off",
                    "orderNum": 1,
                    "open": 0,
                    "disabled": false,
                    "perms": "welcome:view",
                    "type": 0,
                    "children": []
                  },
                  {
                    "id": 226,
                    "parentId": 1,
                    "menuName": "用户管理",
                    "url": "/userMaster",
                    "icon": "el-icon-user",
                    "orderNum": 2,
                    "open": 0,
                    "disabled": false,
                    "perms": "users",
                    "type": 0,
                    "children": []
                  },
                  {
                    "id": 319,
                    "parentId": 1,
                    "menuName": "公告管理",
                    "url": "/notices",
                    "icon": "el-icon-s-flag",
                    "orderNum": 4,
                    "open": 0,
                    "disabled": true,
                    "perms": "",
                    "type": 0,
                    "children": []
                  }
                ]
              },
              {
                "id": 312,
                "parentId": 0,
                "menuName": "作品管理",
                "url": null,
                "icon": "el-icon-s-goods",
                "orderNum": 2,
                "open": 0,
                "disabled": false,
                "perms": null,
                "type": 0,
                "children": [
                  {
                    "id": 229,
                    "parentId": 312,
                    "menuName": "文章管理",
                    "url": "/articleMaster",
                    "icon": "el-icon-date",
                    "orderNum": 1,
                    "open": 1,
                    "disabled": false,
                    "perms": "el-icon-date",
                    "type": 0,
                    "children": []
                  },
                  {
                        "id": 230,
                        "parentId": 229,
                        "menuName": "评论管理",
                        "url": "/comment",
                        "icon": "el-icon-edit",
                        "orderNum": 1,
                        "open": 1,
                        "disabled": false,
                        "perms": "el-icon-date",
                        "type": 0,
                        "children": []
                  },
                  {
                        "id": 267,
                        "parentId": 229,
                        "menuName": "视频管理",
                        "url": "/videoList",
                        "icon": "el-icon-video-camera",
                        "orderNum": 2,
                        "open": 0,
                        "disabled": false,
                        "perms": "",
                        "type": 0,
                        "children": []
                  },
                ]
              },

              {
                "id": 295,
                "parentId": 0,
                "menuName": "其他管理",
                "url": "",
                "icon": "el-icon-s-marketing",
                "orderNum": 5,
                "open": 0,
                "disabled": false,
                "perms": "",
                "type": 0,
                "children": [
                  {
                    "id": 297,
                    "parentId": 295,
                    "menuName": "监控管理",
                    "url": "",
                    "icon": "el-icon-warning",
                    "orderNum": 1,
                    "open": 0,
                    "disabled": false,
                    "perms": "",
                    "type": 0,
                    "children": [
                      {
                        "id": 298,
                        "parentId": 297,
                        "menuName": "SQL监控",
                        "url": "/druid",
                        "icon": "el-icon-view",
                        "orderNum": 1,
                        "open": 0,
                        "disabled": false,
                        "perms": null,
                        "type": 0,
                        "children": []
                      }
                    ]
                  },
                  {
                    "id": 296,
                    "parentId": 295,
                    "menuName": "接口文档",
                    "url": "/swagger",
                    "icon": "el-icon-document",
                    "orderNum": 2,
                    "open": 0,
                    "disabled": false,
                    "perms": null,
                    "type": 0,
                    "children": []
                  },
                ]
              },

              {
                "id": 5,
                "parentId": 0,
                "menuName": "日志管理",
                "url": "",
                "icon": "el-icon-camera",
                "orderNum": 6,
                "open": 0,
                "disabled": false,
                "perms": null,
                "type": 0,
                "children": [
                  {
                    "id": 307,
                    "parentId": 5,
                    "menuName": "操作日志",
                    "url": "/logs",
                    "icon": "el-icon-edit",
                    "orderNum": 1,
                    "open": 1,
                    "disabled": false,
                    "perms": "",
                    "type": 0,
                    "children": []
                  }
                ]
              }
            ]
        }
    },
  //onload事件
  mounted() {
      //页面加载时保存原来的信息
    this.activePath = window.sessionStorage.getItem("activePath");
    this.username = window.sessionStorage.getItem("user");
    // console.log(this.username)
  },
  methods: {
      //折叠面板
    toggleCollapse(){
      this.isCollapse=!this.isCollapse;
    },
    logout() {
      window.sessionStorage.clear(); //清除session
      this.$router.push("/master"); //注销回到登录页
    },
    //点击刷新，保持原来的信息

  }
};
</script>
<style lang="less" scoped>
//头部样式
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
//侧边栏样式
.el-aside {
  background-color: #2b8eff;
  .el-menu{
    border-right: none;// 对其右边框f

  }
  .toggle_box{
    background-color: #21b2ff;
    text-align: center;
    color: #efefef;
    letter-spacing: 0.2em;
    cursor: pointer; //变成小手
  }
}
//主题样式
.el-main {
  background-color: #efefef;
}
.home-container {
  height: 100%;
}
.logo_img {
  width:120px;
  height: 40px;
  border-radius: 2%; // 加圆角
}
.iconfont{
  margin-right: 10px;
}
.el-menu-vertical-demo{
  background-color: #2b8eff;
}
.my-swipe .van-swipe-item {
    color: #fff;
    font-size: 20px;
    line-height: 200px;
    text-align: center;
    background-color: #fff;
}
</style>
