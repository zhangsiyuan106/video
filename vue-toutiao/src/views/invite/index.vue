<template>
    <div>

        <HeaderBar title="邀请好友赢现金"></HeaderBar>
        <van-dialog v-model="show" title="邀请好友赢现金！！"  confirm-button-text="分享" @confirm="share()" >
            {{url}}
        </van-dialog>
        <div class="hongbao">
            <el-button type="danger" round @click="invited()" class="btn-inv">邀请好友领红包</el-button>
            <div class="roll" id="roll">
                <ul>          <li>附近用户：dda*** 抢到10元现金</li>
                    <li>附近用户：hh*** 抢到9.9元现金</li>
                    <li>附近用户：vq*** 抢到8元现金</li>
                    <li>附近用户：io*** 抢到7元现金</li>
                    <li>附近用户：fr*** 抢到10元现金</li>
                    <li>附近用户：mh*** 抢到7元现金</li>
                    <li>附近用户：poi*** 抢到8.2元现金</li>
                    <li>附近用户：qew*** 抢到4.5元现金</li>
                </ul> </div>
            <div class="list-invite-txt" v-show="!((invitelist.length%3)===0)">你还差 <span style="color: red;font-size: 20px;">{{3-(invitelist.length%3)}}</span>位好友就可以参与抽奖!!!</div>

            <el-button v-show="((invitelist.length%3)===0)" type="danger" round @click="qhongbao()" class="btn-inv-txt">抢红包!!!</el-button>
        <div v-if="invitelist.length>0" class="list-invite">
                <div style="margin-top: 0.2rem ;border-bottom: #8c939d ">已邀请的好友:</div>
            <li v-for="(item,index) in invitelist">第{{index+1}}位好友:  {{item.userName}}</li>
        </div>
        </div>

        <van-share-sheet
                v-model="showShare"
                title="立即分享给好友"
                :options="options"
                @select="onselect"
        />
        <van-overlay :show="show22" >
            <div class="wrapper" @click.stop>
                <div id="WX">
                    <div class="bd_weixin_popup_head">
                <span>分享到微信朋友圈</span>
                <a class="bd_weixin_popup_close" @click="show22=false">×</a>
                <div class="weixin"></div>
                <div class="bd_weixin_popup_foot">打开微信，点击底部的“发现”，<br>使用“扫一扫”即可将网页分享至朋友圈。</div>
                    </div>
                </div>
            </div>
        </van-overlay>
    </div>
</template>

<script>
    import { Dialog } from 'vant';
    import { Toast } from 'vant';
    import ClipboardJS from 'clipboard'
    import "jquery-qrcode"
    import $ from "jquery"
    export default {

        name: "index",

        data() {
            return{
                userid : 0,
                text:'天天资讯火爆活动，邀请好友赢好礼！！',
                invitelist: [],
                showShare: false,
                show:false,
                show22:false,
                url:"",
                options:[ [
                    { name: '微信', icon: 'wechat',description: 'wechatFriend'},
                    { name: '朋友圈', icon: 'wechat-moments' ,description: 'wechatTimeline'},
                    { name: 'QQ', icon: 'qq' ,description: 'qqFriend'},
                ],[
                    { name: 'qq空间', icon: 'http://localhost/group1/M00/00/01/rBYSRGEZw5GALG51AAC5kRypY_Y317.png',description: 'qZone',className: 'qZone-c' },
                    { name: '微博', icon: 'weibo' ,description: 'weibo'},
                    { name: '复制链接', icon: 'link',description: 'copy',className: 'copy-btn'},
                ],],
                flag_num:0,
            }
        },

        methods:
         {
             qhongbao(){
                 if(this.flag_num !== 0){
                     Dialog.alert({
                         message: "你已经领过奖励，请再次邀请三人继续抢红包",
                         theme: 'round-button',
                     }).then(() => {
                         // on close
                     });
                 }else {
                     this.flag_num ++;
                 var rand = Math.random();
                 // num代表第几个奖品un
                 var num = 0;
                 var prize=["手速慢了","没有抢到","手气不佳","红包5元","红包10元"];
                 // 各个奖品的概率
                 if (rand < 0.2) num = 1;
                 else if (rand < 0.4) num = 2;
                 else if (rand < 0.8) num = 3;
                 else if (rand < 0.9) num = 4;
                 else if (rand < 1) num = 5;

                 Dialog.alert({
                     message: prize[num-1],
                     theme: 'round-button',
                 }).then(() => {
                     // on close
                 });
                 }

             },
            login: function () {
             const   that = this;
                this.userid = this.$store.getters.user.id;
             this.url = "http://localhost:8080/help-"+this.userid;
            this.$axios.get("/invite/"+this.userid ).then((res) => {
                that.invitelist = res.data.data;
              })
                $(document).ready(function(){
                    setInterval(function(){  // 添加定时器，每1.5s进行转换
                        $("#roll").find("ul:first").animate({
                            marginTop:"-40px"  //每次移动的距离
                        },1000,function(){   // 动画运动的时间
                            $(this).css({marginTop:"0px"}).find("li:first").appendTo(this);//$(this)指的是ul对象，
                            // ul复位之后把第一个元素和第二个元素插入
                            $(this).find("li:first").appendTo(this); //到ul的最后一个元素的位置
                        });             },2500)         });
             },
             invited(){
                this.show=true;
             },
             share(){
                 this.showShare=true;
             },
             onselect(option){
                 this.showShare = false;
                 var that = this.url;
                 if(option.description === 'copy'){
                     var clipboard  = new ClipboardJS('.copy-btn', {
                         text: function () {
                             var url = that;
                             // console.log(url);
                             return url;
                         }
                     });
                     clipboard.on('success', function (e) {
                         Toast.success('复制成功');
                         clipboard.destroy();
                     });

                     clipboard.on('error', function (e) {
                         Toast.fail('复制失败，请手动复制');
                     });
                 }else if(option.name === 'QQ') {
                     this.qqFriend(this.url,this.text);
                 }else if(option.name === 'qq空间'){
                     this.qqZone(this.url,this.text);
                 }else if(option.name === '微博'){
                     this.sinaWeiBo(this.url,this.text);
                 }else if(option.name === '朋友圈'){
                     if($("canvas")){
                         $("canvas").remove();
                     }
                        this.show22  = true;
                     $(".weixin").qrcode({
                         text: this.url, //设置二维码内容
            //            render: "table", //设置渲染方式
                         width: 185, //设置宽度,默认生成的二维码大小是 256×256
                         height: 185, //设置高度
                         typeNumber: -1, //计算模式
                         background: "#ffffff", //背景颜色
                         foreground: "#000000" //前景颜色
                     });
                 }else if(option.name === '微信'){
                     Toast('微信');
                 }
             },
             qqFriend(urldd,text) {
                    var p = {
                        /*获取URL，可加上来自分享到QQ标识，方便统计*/
                        url: urldd,
                        desc: '',
                        /*分享标题(可选)*/
                        title: text,
                        /*分享摘要(可选)*/
                        summary: '还不赶紧点击进来观看',
                        style: '201',
                        width: 32,
                        height: 32
                    };
                    var s = [];
                    for(var i in p) {
                        s.push(i + '=' + encodeURIComponent(p[i] || ''));
                    }
                    var url = "http://connect.qq.com/widget/shareqq/index.html?" + s.join('&');
                    window.open(url);
              },
             qqZone(urldd,text) {
                 var p = {
                     url: urldd,
                     desc: '',        /*默认分享理由(可选)*/
                     summary: '还不赶紧点击进来观看',   /*分享摘要(可选)*/
                     title: text,    /*分享标题(可选)*/
                     pics: '',
                     style: '203',
                     width: 98,
                     height: 22
                 };
                 var s = [];
                 for(var i in p) {
                     s.push(i + '=' + encodeURIComponent(p[i] || ''));
                 }
                var url = "http://sns.qzone.qq.com/cgi-bin/qzshare/cgi_qzshare_onekey?" + s.join('&');
                 window.open(url);
             },
             sinaWeiBo(urldd,text) {
                        var p = {
                            // 表示是否显示当前页面被分享数量(1 显示)(可选， 允许为空)
                            count: '1',
                            //将页面地址转成短域名， 并显示在内容文字后面。(可选， 允许为空)
                            url: urldd,
                            //用于发布微博的来源显示， 为空则分享的内容来源会显示来自互联网。(可选， 允许为空)
                            appkey: '',
                            //分享时所示的文字内容， 为空则自动抓取分享页面的title值(可选， 允许为空)
                            title: text,
                            //自定义图片地址， 作为微博配图(可选， 允许为空)
                            pic: '',
                            //转发时会 @相关的微博账号(可选， 允许为空)
                            ralateUid: '',
                            //语言设置(zh_cn | zh_tw)(可选)
                            language: 'zh_cn'
                        };
                        var s = [];
                        for(var i in p) {
                            s.push(i + '=' + encodeURIComponent(p[i] || ''));
                        }
                        var url = "http://service.weibo.com/share/share.php?" + s.join('&');
                        window.open(url);
                    },

    },
        created() {
            this.login();
        }
    }
</script>

<style scoped>
    .hongbao{
        width: 100%;
        height: 100%;
        background-image: url("../../assets/images/honngbao2.png") ;
        background-repeat: no-repeat;
        background-position: center;
        position: relative;
    }
    .btn-inv{
        position: absolute;
        top: 47%;
        left: 50%;
        transform: translateX(-50%);
    }
    .btn-inv-txt{
        position: absolute;
        top: 56%;
        left: 50%;
        transform: translateX(-50%);
    }
    .list-invite{
        background: orange;
        border-radius: 10%;
        width: 40%;
        height: 20%;
        overflow: scroll;
        color: white;
        position: absolute;
        top: 63%;
        left: 50%;
        transform: translateX(-50%);
    }
    .list-invite-txt{
        background: orange;
        border-radius: 10%;
        width: 83%;
        height: 5%;
        overflow: scroll;
        color: white;
        position: absolute;
        top: 55%;
        left: 50%;
        text-align: center;
        transform: translateX(-50%);
    }
    .wrapper {
        display: flex;
        align-items: center;
        justify-content: center;
        height: 100%;
    }
    .bd_weixin_popup_head span {
        font-size: 12px;
        font-weight: bold;
        text-align: left;
        line-height: 16px;
    }
    .bd_weixin_popup_close {
        width: 16px;
        height: 16px;
        text-decoration: none;
        font-size: 16px;
        text-align: right;
        position: absolute;
        right: 0;
        top: 0;
        cursor: pointer;
    }
    .bd_weixin_popup_foot {
        font-size: 12px;
        text-align: left;
        line-height: 22px;
        color: #666;
    }
    .weixin {
        width: 185px;
        height: auto;
        margin: 10px auto;
    }

    #WX {
        width: 231px;
        height: 285px;
        background: red;
        padding: 10px;
        background: #fff;
        border: solid 1px #d8d8d8;
        position: fixed;
        top: 0;
        left: 0;
        right: 0;
        bottom: 0;
        margin: auto;
    }
     .bd_weixin_popup_head {
         font-size: 12px;
         font-weight: bold;
         text-align: left;
         line-height: 16px;
         height: 16px;
         position: absolute;
         color: #000;
     }
    .roll{
        margin: 100px auto;
        width: 245px;
        height: 40px;
        overflow:hidden;
        color:white;
        background: #ffbe07fc;
        border-radius: 5%;
        position: absolute;
        top: 19%;
        left: 50%;
        transform: translateX(-50%);
    }
    .roll ul{     list-style: none; }
    .roll li{
        line-height:20px;
        font-size:14px;
        text-align:center; }

</style>
