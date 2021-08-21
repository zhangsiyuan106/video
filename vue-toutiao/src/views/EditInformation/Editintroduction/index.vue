<template>
    <section>
        <div class="head">
            <Icon name="jiantou" class="back" @click.native="back"></Icon>
            <div class="name">个人简介</div>
            <div class="submit" v-if="this.str===this.textarea" :class="{'nlight':this.str===this.textarea}">提交</div>
            <div class="submit" v-else @click="sub">提交</div>
        </div>
        <div class="inp">
            <el-input
                    type="textarea"
                    placeholder="添加简介，让大家更好地认识你"
                    v-model="textarea"
                    maxlength="70"
                    show-word-limit
                    rows=8
                    resize="none"
            >
            </el-input>
        </div>
        <div style="font-size: 12px;padding-left: 10px;padding-top: 20px">参考示例</div>
        <el-carousel :interval="4000" type="card" height="200px">
            <el-carousel-item v-for="item in items" :key="item">
                <p class="medium" v-html="item"></p>
            </el-carousel-item>
        </el-carousel>
    </section>
</template>
<script>
    export default {
        data() {
            return {
                textarea: "",
                str: "",
                items: {
                    items1: "独立摄影师<br/>江湖行路人<br/>生活调色家<br/>记录和分享旅途中的美好",
                    items2: "大学金融教授、自由投资人<br/>定期分享行业动态、最新趋势<br/>有问题可在评论区回复、随机解答",
                    items3: "来自广东的北漂少女<br/>喜欢吃吃喝喝，走走看看<br/>很高兴认识大家，多多交流呀~~",
                },
            }
        },
        created() {
            if (localStorage.getItem("introduction") === "null") {
                this.textarea = "";
                this.str = "";
            } else {
                this.textarea = localStorage.getItem("introduction");
                this.str = localStorage.getItem("introduction");
            }
        },
        methods: {
            back(){
                this.textarea=this.$store.state.introduction;
                this.$router.go(-1);//返回上一层
            },
            sub() {
                this.$store.state.introduction = this.textarea;
                localStorage.setItem("introduction", this.textarea);
                this.$axios.post('http://127.0.0.1:8081/api/user/editIntroduction', {
                    id: localStorage.getItem("id"),
                    introduction: this.textarea,
                }).then(res => {
                    console.log(res.data);
                }, err => {
                    console.log(err);
                });

                this.$router.go(-1);//返回上一层
            },
        },
    }
</script>
<style lang="less" scoped>
    .head {
        width: 100%;
        height: 0.5rem;
        position: relative;
        background-color: #fff;
        border-bottom: 1px solid #C0C4CC;

        .back {
            position: absolute;
            left: 0.1rem;
            top: 50%;
            transform: translateY(-50%);
        }

        .name {
            position: absolute;
            left: 50%;
            top: 50%;
            transform: translateY(-50%) translateX(-50%);
            font-size: 15px;
        }

        .submit {
            position: absolute;
            right: 0.1rem;
            top: 50%;
            transform: translateY(-50%) translateX(-50%);
            font-size: 15px;
            color: red;

            &.nlight {
                color: #a7a7a7;
            }
        }

    }

    .el-carousel__item p {
        position: absolute;
        color: #475669;
        font-size: 14px;
        /*line-height: 200px;*/
        height: 200px;
        top: 50px;
        text-align: center;
    }

    .el-carousel__item:nth-child(2n) {
        background-color: #99a9bf;
    }

    .el-carousel__item:nth-child(2n+1) {
        background-color: #d3dce6;
    }
</style>
