<template>
    <section>
        <div class="head">
            <Icon name="jiantou" class="back" @click.native="back"></Icon>
            <div class="name">用户名</div>
            <div class="submit" v-if="this.str===this.input" :class="{'nlight':this.str===this.input}">提交</div>
            <div class="submit" v-else @click="sub">提交</div>
        </div>
        <div style="margin-top: 15px">
            <el-input
                    placeholder="请输入内容"
                    v-model="input"
                    class="input-with-select"
            >
                <template #append>
                    <el-button @click="randomlyGenerated">随机生成</el-button>
                </template>
            </el-input>
        </div>
        <div v-if="input.length>=10" style="color: red;margin-left: 10px;font-size: 12px">已达最大字数</div>
        <div style="margin-left: 10px;color: #a7a7a7;margin-top: 10px">用户名可与兴趣、心情、地域相关</div>
    </section>
</template>
<script>
    export default {
        data() {
            return {
                input: this.$store.state.nickname,
                str: this.$store.state.nickname
            }
        },
        methods: {
            back() {
                this.input = this.$store.state.nickname;
                this.$router.go(-1);//返回上一层
            },
            sub() {
                this.$store.state.nickname = this.input;
                localStorage.setItem("nickname", this.input);
                this.$axios.post('http://127.0.0.1:8081/api/user/editNickname', {
                    id: localStorage.getItem("id"),
                    nickname: this.input,
                }).then(res => {
                    console.log(res.data);
                }, err => {
                    console.log(err);
                });

                this.$router.go(-1);//返回上一层
            },
            randomlyGenerated() {
                this.$axios.get('http://127.0.0.1:8081/api/user/getName', {
                    params: {}
                }).then(res => {
                    console.log(res.data);
                    this.input = res.data;
                }, err => {
                    console.log(err);
                })
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


</style>
