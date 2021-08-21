<template>
    <div id="app">

        <h5 v-show="text">{{text}}</h5>
        <a>{{url}}</a>
        <btnCopy :code="url"></btnCopy>
        <br>
        <div class="social-share share-component" data-initialized="true">
            <a href="#" class="social-share-icon icon-weibo"></a>
            <a href="#" class="social-share-icon icon-wechat"></a>
            <a href="#" class="social-share-icon icon-qq"></a>
            <a href="#" class="social-share-icon icon-qzone"></a>
            <a href="#" class="social-share-icon icon-douban"></a>
        </div>



    </div>
</template>

<script>
    import '../../../node_modules/social-share.js/dist/js/social-share.min'
    import btnCopy from "./btnCopy"
    import { Toast } from 'vant';
    export default {
        name: "invitepage",
        data(){
          return{
              text: "你的邀请连接:",
              url:"localhost:8081/help-"+this.$store.getters.user.id,


          }
        },
        methods:
            {
                share:function () {

                    var t1 = setInterval(() => {
                        if((".social-share").length > 0) {   // 判断容器节点是否已经渲染完毕
                            clearInterval(t1)
                            var $config = {
                                url: this.url,//分享的网页
                                title: "",//标题
                                image               : '', // 图片, 默认取网页中第一个img标签
                                sites               : ['qzone', 'qq', 'weibo','wechat', 'douban'], // 启用的站点
                                disabled            : ['google', 'facebook', 'twitter'], // 禁用的站点
                                wechatQrcodeTitle   : "优质文章分享", // 微信二维码提示文字
                                wechatQrcodeHelper  : '<p>微信里点“发现”，扫一下</p><p>二维码便可将本文分享至朋友圈。</p>',
                            }
                            socialShare('.social-share',$config)
                        }
                    }, 10);
                },
                onSelect(option) {
                    Toast(option.name);
                    this.showShare = false;
                },

            },
        created() {
            // this.share();

        },
        components:{
            btnCopy
        }
    }
</script>

<style scoped>

    @import "../../../node_modules/social-share.js/dist/css/share.min.css";
</style>
