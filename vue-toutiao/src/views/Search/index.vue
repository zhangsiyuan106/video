<template>
    <div class="search-wrapper">
        <HeadComponent @data="onSearch"></HeadComponent>
        <BodyComponent :dataList="searchListData" :urlList="avUrl" ></BodyComponent>
    </div>
</template>
<script>
    import HeadComponent from './head/index'
    import BodyComponent from './body/index'
    export default {
        components: { HeadComponent, BodyComponent },
        data () {
            return {
                searchListData:[],
                avUrl:[]
            }
        },
        methods:{
            onSearch(data){
                let urlList = [];
                this.searchListData = data;
                for (let i = 0; i < data.length; i++) {
                    this.$axios.get("/api/user/getPhoto?id="+ data[i].userid).then((res =>{
                        if (res.status === 200){
                            urlList.push(res.data.data)
                        }else {
                            this.$message.fail("请求错误！")
                        }
                    }))
                }
                 this.avUrl=urlList;
            }
        }
    }
</script>
<style lang="less" scoped>
    .search-wrapper {
        width: 100%; height: 100%;
        background: #fff;
    }
</style>