<template>
    <div>
        <to-bar></to-bar>
        <van-grid :column-num="2" :gutter="8">
            <van-grid-item v-for="(booktitle,index) in booktitle" :key="index"  @click="toThisBook(booktitle.name)">
                <van-image  :src="booktitle.src"/>
                <span class="name">书名:{{booktitle.name}}</span>
                <span class="author">作者:{{booktitle.author}}</span>
            </van-grid-item>
        </van-grid>
    </div>

</template>
<script>
    import {toBar} from "../index"
    export default {
        data () {
            return {
                bookName :'',
                booktitle:[
                    {
                        author:"罗贯中",
                        name:"三国演义",
                        src:"static/三国演义.jpg"
                    },
                    {
                        author:"冯唐",
                        name:"活着活着就老了",
                        src:"static/活着活着就老了.jpg"
                    },
                    {
                        author:"天蚕土豆",
                        name:"大主宰",
                        src:"static/大主宰.png"
                    },
                    {
                        author:"邓天",
                        name:"修真高手混都市",
                        src:"static/修真高手混都市.jpg"
                    },
                    {
                        author:"沙发果断",
                        name:"史上最扣的幻想之主",
                        src:"static/校花.jpg"
                    },
                    {
                        author:"雨天下雨",
                        name:"大道之争",
                        src:"static/大道之争.jpg"
                    },
                    {
                        author:"七色小丑",
                        name:"赤色之瞳",
                        src:"static/赤色之瞳.jpg"
                    },
                    {
                        author:"刘胤",
                        name:"最后的三国",
                        src:"static/最后的三国.jpg"
                    }

                ]
            }
        },
        methods: {
            toThisBook(name){
                this.$router.push({name:'电子书',params:{bookname:name}})
            }
        },
        components:{
            toBar
        }
    }
</script>
<style lang="less" scoped>
.name,.author{
    font-family: 华光行楷_CNKI;
    font-weight: 400;
    font-size: 15px;
}

</style>