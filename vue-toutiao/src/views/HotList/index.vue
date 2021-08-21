<template>
    <div class="app-wrapper">
        <headerBar></headerBar>
        <toBar></toBar>

        <div style="background-color: #FFFFFF" class="scroll">
            <slide :on-refresh="onRefresh" :on-infinite="onInfinite">

                <el-card class="box-card">
                    <template #header>
                        <div class="card-header">
                            <span><img src="../../assets/images/rb.jpg" alt=""></span>
                            <div style="font-size: 12px;color: #a7a7a7">更新时间：{{new Date().getMonth()+1+"-"+(new
                                Date().getDay()+1)
                                +" "+new Date().getHours()+":"+new Date().getMinutes()+":"+new Date().getSeconds()}}
                            </div>
                        </div>
                    </template>
                    <div v-for="(item,index) in listdata" class="text item" style="border-bottom: #a7a7a7 1px solid;">
                        <div style="padding-bottom: 10px" @click="Inf(item)">
                            <a href=""></a>
                            <span style="color: #a7a7a7" v-if="item.rank==1"><img src="../../assets/images/rb1.jpg"
                                                                                  alt=""
                                                                                  style="width: 15px;height: 15px"></span>
                            <span style="color: #a7a7a7" v-if="item.rank==2"><img src="../../assets/images/rb2.jpg"
                                                                                  alt=""
                                                                                  style="width: 15px;height: 15px"></span>
                            <span style="color: #a7a7a7" v-if="item.rank==3"><img src="../../assets/images/rb3.jpg"
                                                                                  alt=""
                                                                                  style="width: 15px;height: 15px"></span>
                            <span style="color: #a7a7a7;padding-right: 5px"
                                  v-if="item.rank!=1 && item.rank!=2 && item.rank!=3">{{item.rank}}</span>
                            <span>{{item.title }}</span>
                            <span style="color: #a7a7a7;font-size: 12px">{{item.num }}</span>
                            <div style="position:absolute;right: 30px;display: inline-block"><i
                                    class="el-icon-arrow-right"></i></div>
                        </div>

                    </div>
                    <div v-for="(item,index) in downdata" class="text item" style="border-bottom: #a7a7a7 1px solid;">
                        <div style="padding-bottom: 10px" @click="Inf(item)">
                            <span style="color: #a7a7a7">{{item.rank}}</span> <span>{{item.title }}</span> <span
                                style="color: #a7a7a7;font-size: 12px">{{item.num }}</span>
                            <div style="position:absolute;right: 30px;display: inline-block"><i
                                    class="el-icon-arrow-right"></i></div>
                        </div>
                    </div>
                </el-card>


            </slide>
        </div>

        <footerBar></footerBar>
    </div>
</template>

<script>
    import 'swiper/dist/css/swiper.css'
    import {headerBar, footerBar, toBar} from './index'
    import slide from "./slide";

    export default {
        inject: ['reload'],
        components: {
            headerBar,
            footerBar,
            toBar,
            slide
        },
        data() {
            return {
                counter: 1, //默认已经显示出15条数据 count等于一是让从16条开始加载
                num: 15,  // 一次显示多少条
                pageStart: 0, // 开始页数
                pageEnd: 0, // 结束页数
                listdata: [], // 下拉更新数据存放数组
                downdata: [],  // 上拉更多的数据存放数组
                hotUrl: "",
            }
        },
        mounted: function () {
            this.getList();
        },

        methods: {
            Inf(item) {
                this.$store.state.hotList = item;
                localStorage.setItem("hotList", item);
                this.$router.push({
                    path: "/hotInf",
                });
            },
            getList() {
                this.$axios.get('http://localhost:8081/api/hot', {
                    params: {}
                }).then(res => {
                    this.listdata = res.data.slice(0, 15);
                }, err => {
                    console.log(err);
                });
            },
            onRefresh(done) {
                this.getList();
                done() // call done

            },
            onInfinite(done) {
                let vm = this;
                this.$axios.get('http://localhost:8081/api/hot', {
                    params: {}
                }).then(res => {
                    vm.counter++;
                    vm.pageEnd = vm.num * vm.counter;
                    vm.pageStart = vm.pageEnd - vm.num;
                    let arr = res.data;
                    let i = vm.pageStart;
                    let end = vm.pageEnd;
                    for (; i < end; i++) {
                        let obj = {};
                        obj["title"] = arr[i].title;
                        obj["num"] = arr[i].num;
                        obj["rank"] = arr[i].rank;
                        obj["url"] = arr[i].url;
                        vm.downdata.push(obj);
                        if ((i + 1) >= res.data.length) {
                            this.$el.querySelector('.load-more').style.display = 'none';
                            return;
                        }
                    }
                    done() // call done
                }, err => {
                    console.log(err);
                });
            }
        },

    }
</script>
<style lang="less" scoped>
    .app-wrapper {
        width: 100%;
        height: 100%;

        .scroll {
            .card-header {
                margin-top: 0;
                padding-top: 0;
                padding-bottom: 0;
                display: flex;
                justify-content: space-between;
                align-items: center;
            }

            .text {
                font-size: 14px;
            }

            .item {
                margin-bottom: 18px;
            }

            .box-card {
                margin-left: 5px;
                margin-right: 5px;
            }


        }

    }

</style>
