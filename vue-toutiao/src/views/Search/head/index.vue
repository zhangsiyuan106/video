<template>
    <div class="search-head-wrapper df-sb">
        <div class="search-title df-sb border-half-bottom">
            <div class="previous" @click="back($router)">   < </div>

            <div class="search">
                <input id="autoComplete" type="text" placeholder="搜索些啥呢..." v-model="$store.state.search.keyword">

            </div>
            <div class="close" @click="searchNews">搜索</div>

        </div>
        <DefaultLoading v-show="searchLoading"></DefaultLoading>

    </div>
</template>
<script>
    import $ from 'jquery'
    import 'jquery-ui-dist/jquery-ui'
    import 'jquery-ui-dist/jquery-ui.min.css'
    import { mapGetters } from 'vuex'
    export default {
        data(){
            return {
                searchArticleList:[]
            }
        },

        mounted(){
            this.$store.state.search.keyword = "";
          $('#autoComplete').autocomplete({
              source:'http://localhost:8081/searchArticle',
              delay:30,
              minLength:1,
          })
        },
        methods: {
            skip (id) {
                this.$router.animate = 1
                this.$router.push('/article/'+id)
            },
            async searchNews(){
                let searchText = $("#autoComplete").val();
                if (searchText.trim()){
                    const {data} =await this.$http.get("/searchArticle/article?term="+searchText.trim())
                    // console.log(data.data)
                    this.searchArticleList = data.data;
                    this.$emit("data", this.searchArticleList)

                }
            }
        },
        computed: {
            ...mapGetters([
                'keyword',
                'searchPageindex',
                'searchList',
                'searchLoading',
                'searchEnd'
            ])
        }
    }
</script>
<style lang="less" scoped>


    .search-title {
        width: 100%;
        height: 0.4rem;
        padding: 0 0.1rem;
        background-color: #2b8eff;
        .previous{
            display: inline-block;
            color: white;

        }
        .search {
            width: e("calc(80% - 0.1rem)");
            position: relative;
            color: @font-gray;
            input {
                margin-left: 0px;
                margin-right: 10px;
                width:100%;
                height: 0.3rem;
                border: none;
                outline: none;
                background:white;
                text-indent: 0.3rem;
                border-radius: 0.04rem;
                font-size: 0.12rem;
                color: #ccc;
            }
        }
        .close {
            z-index: 10;
            font-size: 15px;
            color: white;
        }
    }
    .search-box {
        .item {

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

    }

</style>
