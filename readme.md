# Getting Started

### Reference Documentation

#Druid数据库监控页面http://localhost:8081/druid
#swagger-Controller监控测试http://localhost:8081/swagger-ui.html

#相关端口号:mysql 3306 redis 6379 mongodb 27017 fastdfs 22122 es 9200

rabbitmq：  15672进入账号：root 密码：root
数据库文件存放在sql目录中


启动 storage /usr/bin/fdfs_storaged /etc/fdfs/storage.conf restart

启动 tracker /usr/bin/fdfs_trackerd /etc/fdfs/tracker.conf restart

小说上传暂未完成！目前是写死的 后期可能更新优化
记得将配置文件中的所有ip更换成你的
需要使用nginx反向代理 具体配置为
server {
        listen       80;
        server_name  localhost;

        #charset koi8-r;

			#add_header Access-Control-Allow-Origin $origin_list;
			add_header Access‐Control‐Allow‐Origin *;
			add_header Access-Control-Allow-Credentials true;
			add_header Access-Control-Allow-Methods GET;

        #access_log  logs/host.access.log  main;
		#nginx后端服务
        #location / {
         #  proxy_pass http://localhost:8080;

        #}
		#nginx前端文件指向本地服务
        location  /video {
                alias   E:/Video/;

        }


需要下载 ffmpeg 存放路径请看utils下的FFMPeg类

功能列表：
    文章发布、文章一级评论、二级评论、平台分享、小说阅读器、
    消息通知（你关注的人发布了文章、有人回复了你的评论）、
    视频上传、视频播放、 后台管理、微博热搜同步、天气查询、
    登录注册头像上传、个人资料修改、注册邮箱验证、
    背景上传、富文本编辑器、搜索、邀请好友抢红包
    关注、粉丝列表、点赞评论收藏、查看已发布的文章、

#前端

### npm run dll (打包一次变不在需要打包了)
### npm run build 打包
### npm run dev 本地开发

#前端所用技术栈
Vuex Vue-Router Mavon-Editor ElementUI  WeUI LayUI axios Echarts Epub.js Video.js

本项目现已开源 具体细节请详看代码 欢迎优化及bug指正！

如需手机端访问请将全局localhost替换为你的ip地址并在同一局域网下