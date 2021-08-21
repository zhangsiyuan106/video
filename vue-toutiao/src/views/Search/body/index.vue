<template>
    <div class="search-body-wrapper" v-scroll="loadingMore">
        <div class="search-container">
            <div class="guess" v-if="searchInfoList.length < 1 && dataList.length < 1">
                <div class="tip">猜你想搜的</div>
                <ul class="search-menu border-half cf">
                    <li class="fl border-half" v-for="news in keywordList" @click="getSearchData(news)">{{news}}</li>
                </ul>

            </div>
            <div class="search-box" v-if="dataList.length>=1 && searchInfoList.length < 1">
                <section v-for="(item,index) in dataList"  class="item border-half-bottom" @click="skip(item.id)">

                    <el-card class="box-card"  shadow="always">
                        <div slot="header" class="clearfix">
                            <span v-html="item.headLine" style="font-size: 20px;color: #005cc5"></span>
                        </div>

                        <!-- 最多显示三行 -->
                        <div v-html="item.content" class="van-multi-ellipsis--l2 ">

                        </div>
                        <br>
                        <div>
                            <span class="photo-wrapper"  ><img :src="urlList[index]" alt="加载中" width="38px" height="38px" class="photo"></span>
                            <p style="color: #6a737d;font-size: 16px" v-text="item.userName" class="userName"></p>
                        </div>
                    </el-card>
                </section>
                <NoneData v-if="searchEnd"></NoneData>
            </div>
            <div class="search-box" v-if="searchInfoList.length>=1 && dataList.length < 1">
                <section v-for="item in searchInfoList" class="item border-half-bottom" @click="skip(item.id)">


                    <el-card class="box-card"  shadow="always">
                        <div slot="header" class="clearfix">
                            <span v-html="item.headLine" style="font-size: 20px;color: #005cc5"></span>
                        </div>
                        <!-- 最多显示三行 -->
                        <div v-html="item.content" class="van-multi-ellipsis--l2 ">

                        </div>
                    </el-card>
                </section>
                <NoneData v-if="searchEnd"></NoneData>
            </div>
        </div>
        <DefaultLoading v-show="searchLoading"></DefaultLoading>
    </div>
</template>
<script>
    import { mapGetters } from 'vuex'

    export default {
        data () {
            return {
                keywordList: ['阿里', '奥运', '塔利班', '朱丹'],
                searchInfoList:[],
            }
        },
        props:{
            dataList:{
                type:Array
            },
            urlList:{
                type:Array

            }
        },
        mounted(){
        },
        computed:{

        },



        methods: {
            skip (id) {
                this.$router.animate = 1
                this.$router.push('/article/'+id)
            },
             async getSearchData (news) {
                console.log(this.dataList)
                 this.$store.state.search.keyword = news
                 const {data} =await this.$http.get("/searchArticle/article?term="+news)
                 console.log(data)
                 this.searchInfoList =data.data;

            },
            loadingMore () {
            },
        },
        computed: {
            ...mapGetters([
                'keyword',
                'searchPageindex',
                'searchList',
                'searchLoading',
                'searchEnd'
            ])
        },

    }
</script>
<style lang="less" scoped>

    .search-body-wrapper {
        height: e("calc(100% - 0.5rem)");
        overflow-y: scroll;
    }
    .guess {
        .tip {
            color: #ccc;
            line-height: 0.28rem;
            font-size: 0.1rem;
            padding: 0 0.1rem;
        }
        .search-menu {
            li {
                width: e("calc(100% / 2)");
                text-align: center;
                line-height: 0.35rem;
                font-size: 0.14rem;
                color: #333;
                &:before {
                    border-top: none;
                }

            }
            li:nth-child(2n) {
                &:before {
                    border-left: none;
                }   
            }
        }
    }

    .search-box {
        .item {
            background-color: #efefef;
            padding: 0.2rem 0.1rem;
            h4 {
                color: @font-normal;
            }
            p {
                font-size: 0.16rem;
                line-height: 0.2rem;
                margin: 0.1rem 0;
            }
            .small-box {
                >* {
                    display: inline-block;
                    vertical-align: middle;
                    font-size: 0.1rem;
                    margin-right: 0.04rem;
                    color: #999;
                }
            }
        }
        .item-l {
            width: 70%;
            padding-right: 10px;
        }
        .item-r {
            width: 30%;
            img {
                width: 100%;
            }
        }
        .searchBoxLi{
            border: 1px solid red;
        }
        .userName{
            display: inline-block;
            padding-left: 8px;
        }
        .photo-wrapper{
            display: inline-block;
            float: left;

        }
        .photo{
            border-radius:25px;

        }

    }
    .text {
        font-size: 14px;
    }

</style>