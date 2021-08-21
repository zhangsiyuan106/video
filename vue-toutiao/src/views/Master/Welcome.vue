<template>
  <div>
    <el-header>
      <div>
        <img :src=srcUrl[0] width="120px">
        <span style=" font-size: 30px">天天资讯管理系统</span>
      </div>

      <span>
          <span style="color:white; font-size: 20px">欢迎,{{username}}！</span>
         <el-button type="danger" @click="logout">注销</el-button>
      </span>
    </el-header>
    <br>
      <el-breadcrumb separator-class="el-icon-arrow-right">
        <el-breadcrumb-item :to="{ path: '/masterHome' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>系统管理</el-breadcrumb-item>
        <el-breadcrumb-item>欢迎页面</el-breadcrumb-item>
      </el-breadcrumb>

    <el-row class="home">
      <el-col :span="8">
        <el-card shadow="hover" style="height: 263px">
          <div class="user">
            <img src="http://localhost:80/group1/M00/00/01/rBYSRGEPXweABDRWAAAaEiIIUIM387.jpg" alt="" class="img">
              <div class="userInfo">
                <p class="name">{{ username }}</p>
                <p class="role">管理员</p>
              </div>
          </div>
          <div class="loginInfo">
            <p>登录时间：<span>{{ time }}</span></p>
            <p>登录地点：<span>四川省成都市双流区</span></p>
          </div>
        </el-card>
        <el-card shadow="hover" style="height: 664px;margin-top: 25px">
          <div style="height: 260px;margin-top: 4px">
            <el-table :data="artList" border stripe>
              <el-table-column type="index"></el-table-column>
              <el-table-column label="发布人ID" prop="userid"></el-table-column>
              <el-table-column label="文章编号" prop="id"></el-table-column>
              <el-table-column label="评论数" prop="replynum"></el-table-column>
              <el-table-column label="文章标题" prop="headLine"></el-table-column>
            </el-table>
          </div>

        </el-card>
      </el-col>
      <el-col :span="16">
        <div class="num">
            <el-card shadow="hover" v-for="item in countData" :key="item.name" :body-style="{display:'flex',padding:0}">
             <i class="icon" :class="`el-icon-${item.icon}`" :style="{background:item.color}" > </i>
               <div class="detail">
                 <p class="num">{{item.value}}</p>
                 <p class="txt">{{item.name}}</p>
               </div>

            </el-card>
        </div>
        <el-card shadow="hover" style="height: 350px">
          <div class="block">
            <el-carousel height="330px">
              <el-carousel-item v-for="item in srcUrl" :key="item">
                <img :src="item" alt="" width="1300px" height="330px">
              </el-carousel-item>
            </el-carousel>
          </div>

        </el-card>

        <div class="graph">
          <el-card shadow="hover">
            <echart style="height: 255px" :chartData="echartData.user"></echart>
          </el-card>
          <el-card shadow="hover">
            <echart style="height: 255px" :chartData="echartData.content" :is-axis-chart="false"></echart>
          </el-card>
        </div>
      </el-col>
    </el-row>
  </div>

</template>
<style lang="less" scoped>

</style>
<script>
//引入echart组件
import Echart from "../../components/Echarts/Echart";
export default {
  components:{
    Echart
  },
  name:"Welcome",
  data(){
    return{
      srcUrl: ['../../src/assets/images/1.png','../../src/assets/images/2.png','../../src/assets/images/3.png','../../src/assets/images/4.png'],
      artList:[],
        username:"",
      totalMediaCount:0,
      articleCount:0,
      commentCount:0,
      userCount:0,
        time:"",
      commentPage: {
        pageNo: 1, //当前页码
        pageSize: 4
      },
      countData:[
          {
            name: "总用户数",
            value:0,
            icon:'user',
            color:"#bb082c"
          },
          {
            name: "总文章数",
            value:0,
            icon:'tickets',
            color:"#082d9c"
          },
          {
            name: "总视频数目",
            value:0,
            icon:'video-camera',
            color:"#2dbd0d"
          },
          {
            name: "总评论数",
            value:0,
            icon:'success',
            color:"#2ec7c9"
          },
        ],
      //图表数据
      echartData:{
        user:{
          title:"用户状态",
          trigger:"axis",//提示窗
          legend:["活跃用户","新增用户"],
          xData:[],
          series:[]
        },
        content:{
          title:"今日内容",
          subtext:'www.baidu.com',
          left:"center",
          trigger:"item",//提示窗
          legendLeft:"left",
          legendOrient:"vertical",
          series:[]
        }
      }
    }
  },
  //onload事件
  mounted() {
    //页面加载时保存原来的信息
    this.getCount();
    // console.log(this.articleCount)

    this.getCommentList();
    // this.getVisitList();
    this.getUserList();
    this.getContentList();
    this.username = window.sessionStorage.getItem("user");
    // this.time=new Date().getFullYear()+"-"+(new Date().getMonth()+1)+"-"+new Date().getDay();
    let date = new Date();
    this.time=this.getNowFormatDate(date);

  },

  methods: {
    async getCount(){
      const {data: res} = await this.$http.get("/home/getArtCount");
      const {data: res1} = await this.$http.get("/relComment/getCommCount");
      const {data: res2} = await this.$http.get("/videoMaster/getMediaCount");
      const {data: res3} = await this.$http.get("/user/getUserCount");
      this.articleCount = res.data;
      this.commentCount = res1.data;
      this.totalMediaCount = res2.data;
      this.userCount = res3.data;
      this.countData[1].value = this.articleCount
      this.countData[3].value = this.commentCount
      this.countData[2].value = this.totalMediaCount
      this.countData[0].value = this.userCount;
    },
    logout() {
      window.sessionStorage.clear(); //清除session
      this.$router.push("/master"); //注销回到登录页
    },
    getContentList(){
        this.echartData.content.series.push({
          name: '内容',
          type: 'pie',
          radius: '50%',
          data: [
            {value: 1048, name: '热点'},
            {value: 735, name: '新闻'},
            {value: 580, name: '明星'},
            {value: 484, name: '生活'},
            {value: 300, name: '小说'}
          ],

        })
    },
    getUserList(){
          // this.echartData.user.xData=this.echartData.read.xData;
          this.echartData.user.series.push(
            {
              name: '活跃用户',
              type: 'bar',
              barWidth: '30%',
              barGap: 0,
              emphasis: {
                focus: 'series'
              },
              data: [100, 255, 200, 334, 200,120,100]
            },
            {
              name: '新增用户',
              type: 'bar',
              barWidth: '30%',
              emphasis: {
                focus: 'series'
              },
              data: [50, 125, 120, 133, 120,52,80]
            })

    },
    format(shijianchuo) {
      //shijianchuo是整数，否则要parseInt转换
      let time = new Date(shijianchuo);

      let m = time.getMonth() + 1;
      let d = time.getDate();
      return m + "-" + d+"日";

    },
    getNowFormatDate(date) {

      let seperator1 = "-";
      let seperator2 = ":";
      let month = date.getMonth() + 1;
      let strDate = date.getDate();
      if (month >= 1 && month <= 9) {
        month = "0" + month;
      }
      if (strDate >= 0 && strDate <= 9) {
        strDate = "0" + strDate;
      }
      var currentdate = date.getFullYear() + seperator1 + month + seperator1 + strDate
        + " " + date.getHours() + seperator2 + date.getMinutes()
        + seperator2 + date.getSeconds();
      return currentdate;
    },
    async getCommentList() {
      const {data: res} = await this.$http.get("/home/getArticleByPage/" + this.commentPage.pageNo + "/" + this.commentPage.pageSize);
      if (res.flag == "ok") {
        this.artList = res.articleList;
        this.total = res.totalArticle;
      } else {
        return this.$message.error(res.flag);
      }
    },
  }
}
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
.home{


  .loginInfo{
    p{
      line-height: 20px;
      font-size: 14px;
      color: #999999;
      span{
        color: #666666;
        margin-left: 60px;
      }
    }
  }
  .user{
    display: flex;
    align-items: center;
    padding-bottom: 20px;
    margin-bottom: 15px;
    border-bottom: 1px solid #cccccc;
  }
  .num{
    display:flex;
    flex-wrap: wrap;
    justify-content: space-between; //水平对齐
    .el-card{
      width:30%;
      margin-bottom:25px;
      height: 118px;
    }
    .icon{
      width: 80px;
      height: 118px;
      line-height: 80px;
      font-size: 40px;
      text-align: center;
    }
    .detail{
      margin-left: 15px;
      display: flex;
      flex-direction: column;
      justify-content: center;
      .num{
        font-size: 20px;
        margin-bottom: 5px;
      }
      .txt{
        color: #999999;
        text-align: center;
        font-size: 15px;
      }
    }

  }
  .graph{
    margin-top: 20px;
    display: flex;

    justify-content: space-between;
    .el-card{
      width: 48%;
    }
  }
  .img{
    width: 150px;
    height: 150px;
    border-radius: 50%;
    margin-right: 40px;
  }
  .userInfo{
    .name{
      font-size: 35px;
      margin-bottom: 10px;
    }
    .role{
      color: #999999;
    }
  }
  .detail{
    margin-left: 15px;
    display: flex;
    flex-direction: column;
    justify-content: center;
  }
  .el-carousel__item h3 {
    color: #475669;
    font-size: 14px;
    opacity: 0.75;
    line-height: 150px;
    margin: 0;
  }

  .el-carousel__item:nth-child(2n) {
    background-color: #99a9bf;
  }

  .el-carousel__item:nth-child(2n+1) {
    background-color: #d3dce6;
  }

}
</style>
