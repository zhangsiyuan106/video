<template>
    <article class="video-wrapper" v-scroll="loadingMore">
        <toBar></toBar>
        <div class="video-container">
            <section class="item border-half-bottom" v-for="(item, index) in videoList">
                <div class="video">
                    <video id="my-video" controls="true"  >
                        <source v-bind:src="item.tag+item.fileUrl" type="video/mp4" />
                    </video>
                    <!-- canvas代替video播放 -->
                    <div class="canvas-video bg-cover" :style="{backgroundImage: `url(${item.tag+item.imgPath})`} ">
                        <canvas @click="pause(index, item)"></canvas>
                    </div>

                    <div class="title" v-show="!item.playBol">
                        <h4>{{item.mimeType}}</h4>
                        <small>{{item.playCount}}次播放</small>
                    </div>
                    <div class="play" @click="play(index, item)" v-show="!item.playBol">
                        <Icon name="play"></Icon>
                    </div>
                    <time v-show="item.uploadTime">{{item.uploadTime}}</time>
                    <div class="avatar bg-cover-all" v-show="!item.playBol" :style="{backgroundImage: `url(${item.image})`}"></div>
                </div>
                <div class="intro df-sb">

                    作者:<div class="source">{{item.userName}}</div>
                    <div class="box df-c">
                        <div>
                            <van-icon name="chat-o" dot  color="gray" size="18" @click="intocommon(item.fileId)"/>
                        </div>
                        <div>
                            <Icon name="More" @click.native="more" color="gray"></Icon>
                        </div>
                    </div>
                </div>
            </section>
        </div>

        <DefaultLoading v-show="videoLoading"></DefaultLoading>
        <NoneData v-if="videoEnd"></NoneData>
    </article>
</template>
<script>
    import { mapGetters } from 'vuex'
    import axios from 'axios';
    import {toBar} from './index'
    import $ from 'jquery'

    export default {
        data () {
            return {
                pageindex: 1,
                audioReady: false,
            }
        },
        created () {
            this.$store.dispatch('getVideoList', { pageindex: this.pageindex })
        },


        methods: {

            more () {
                this.$alert('没有更多信息哦!')
            },
            // 加载更多
            loadingMore (){
                return new Promise( async (resolve, reject) => {
                    this.pageindex ++
                    await this.$store.dispatch('getVideoList', { pageindex: this.pageindex })
                    resolve()
                })
            },
            // 播放
            play (index, item) {
                item.playBol = true

                for (let i = 0; i < $(".video").length; i++) {
                        if (i !== index) {
                            document.querySelectorAll('video')[i].pause()

                        }
                }
                this.$set(this.videoList, index, item)
                this.dragVideo(index)
                axios.get('http://localhost:8081/video/list/playCount',{
                    params:{
                        id:item.fileId,
                    }
                    }
                );
                // console.log(item.fileId)
            },
            // 暂停
            pause (index, item) {
                document.querySelectorAll('video')[index].pause()
                item.playBol = false
                this.$set(this.videoList, index, item)

            },
            // canvas 绘制
            dragVideo (index) {
                let video = document.querySelectorAll('video')[index],
                    ctx = document.querySelectorAll('canvas')[index].getContext('2d')
                video.play()
                let fps = 1000/30,
                    w = document.querySelectorAll('.video')[index].clientWidth,
                    h = document.querySelectorAll('.video')[index].clientHeight
                video.addEventListener('play', () => {
                    setInterval( () => {
                        ctx.drawImage(video, 0, 0, 320, 176);
                    }, fps)
                })
            },
            intocommon(id){
                window.sessionStorage.setItem("mediaId",id);
                this.$router.push({path:"/video/"+id})

            }
        },
        components: {

            toBar,

        },
        computed: {
            ...mapGetters([
                'videoList',
                'videoLoading',
                'videoEnd'
            ])
        }
    }
</script>
<style lang="less" scoped>
    .video-wrapper {
        overflow-y: scroll;
    }
    .roating{
        color: #6a737d;
    }
    .item {
        padding: 0.1rem 0;
        .video {
            width: 100%;
            height: 2rem;
            background-color: #000;
            position: relative;
            video {
                width: 100%;
                /*display: none;*/
                height: 100%;
            }
            .canvas-video {
                width: 0%;
                height: 0%;
                position: absolute;
                left: 0;top: 0;
                canvas {
                    width: 0%;
                    height: 0%;
                }
            }

            .play {
                position: absolute;
                left: 50%;top: 50%;
                transform: translate(-50%, -50%);
                background-color: rgba(0, 0, 0, .3);
                border-radius: 50%;
                z-index: 3;
                svg {
                    color: #fff;
                    font-size: 0.4rem;
                }
            }
            .title {
                position: absolute;
                left: 0;top: 0;
                line-height: 0.2rem;
                width: 100%;
                color: #f4f4f4;
                padding: 0.04rem 0.1rem;
                background: linear-gradient(top , rgba(0, 0, 0, .45), rgba(0, 0, 0, 0) 100%);
                background: -webkit-linear-gradient(top , rgba(0, 0, 0, .45), rgba(0, 0, 0, 0) 100%);
                small {
                    font-size: 0.12rem;
                }
            }
            time {
                position: absolute;
                right: 0.1rem; top: 0.1rem;
                font-size: 0.1rem;
                padding: 0.04rem 0.08rem;
                border-radius: 0.06rem;
                background-color: rgba(0, 0, 0, .5);
                color: #f4f4f4;
            }
            .avatar {
                width: 0.4rem;
                height: 0.4rem;
                border-radius: 50%;
                position: absolute;
                bottom: -0.1rem;
                left: 0.2rem;
            }
        }

        .intro {
            margin-top: 0.1rem;
            padding: 0 0.1rem;
            .source {
                font-size: 0.14rem;
            }
            .box {
                >*{
                    margin-left: 0.14rem;
                    font-size: 0.12rem;
                    color: @font-gray;
                }
            }
        }

    }
</style>
