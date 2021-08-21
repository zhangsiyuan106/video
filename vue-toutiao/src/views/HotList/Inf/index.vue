<template>
    <div class="app-wrapper">
        <div style="position: fixed;z-index: 10;width: 100%;">
            <van-search
                    v-model="input"
                    shape="round"
                    background="#2B8EFF"
                    placeholder="请输入搜索关键词"
                    disabled
                    left
            >
            <template #left>
                <div @click="back($router)" style="margin-top: 6px;margin-right: 10px">
                    <van-icon name="arrow-left" />
                </div>
            </template>
            </van-search>
        </div>


        <el-card class="box-card">
            <div class="text item" style="position: relative">
                <div style="margin-top: 20px">
                    <el-avatar shape="square" :size="50" :src=infs[0].img></el-avatar>
                    <div style="display: inline-block;position: absolute;top: 40px;left: 60px">
                        <h2>{{title}}</h2>
                        <p style="color: #a7a7a7">{{reading}}</p>
                    </div>
                    <p style="margin-top: 15px"><strong>导语：</strong>{{(infs[1].dy.split("："))[1]}}</p>
                    <el-tag type="warning" style="margin-top: 10px">热搜热榜第{{this.$store.state.hotList.rank}}位</el-tag>
                </div>

            </div>
        </el-card>

        <div v-for="(i,index) in infs">
            <el-card class="box-card">
                <div v-if="index!=infs.length-1" style="position: relative">
                    <el-avatar :src=infs[index+1].img></el-avatar>
                    <div style="display: inline-block;position: absolute;top: 10px;padding-left: 10px">{{i.name}}</div>
                </div>
                <div v-else>
                    <el-avatar :src=infs[8].img></el-avatar>
                    <div style="display: inline-block;">{{i.name}}</div>
                </div>

                <div class="text item">
                    {{i.inf}}
                </div>
            </el-card>
        </div>
    </div>
</template>

<script>
    import {footerBar} from '../index'

    export default {
        inject: ['reload'],
        components: {
            footerBar,
        },
        data() {
            return {
                hotList: this.$store.state.hotList,
                infs: [{img: "", dy: ""}],
                title: "",
                reading: "",
                input: "",
            }
        },
        mounted() {
            console.log(this.$store.state.hotList.url);
            this.$axios.get('http://localhost:8081/api/desc', {
                params: {
                    url: this.$store.state.hotList.url,
                }
            }).then(res => {
                this.infs = res.data;
                let s = this.infs[0].title;
                let strings = s.split("#");
                this.title = strings[1];
                this.reading = strings[2];
                this.input = "#" + this.title + "#";
                console.log(res.data);
            }, err => {
                console.log(err);
            })
        },
    }

</script>
<style lang="less" scoped>
    .app-wrapper {
        width: 100%;
        height: 80%;

        .text {
            font-size: 14px;
        }

        .item {
            padding: 18px 0;
        }

        .box-card {
            width: 100%;
        }
    }


</style>
