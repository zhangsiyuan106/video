<template>
    <div class="app">
        <div class="head">
            <Icon name="jiantou" class="back" @click.native="back"></Icon>
            <div class="name">修改密码</div>
        </div>

        <div style="margin-top: 15px;margin-left: 20px;font-size: 25px">请输入原密码</div>
        <div style="margin-top: 30px;margin-left: 20px;margin-right: 20px">
            <el-input placeholder="请输入原密码" v-model="input1" minlength="11" maxlength="11" clearable
                      type="password"
                      @input="anExamination">
            </el-input>
        </div>
        <div style="margin-top: 15px;margin-left: 20px;font-size: 25px">请输入修改后的密码</div>
        <div style="margin-top: 30px;margin-left: 20px;margin-right: 20px">
            <el-input placeholder="请输入修改后的密码" v-model="input2" minlength="11" maxlength="11" clearable type="password"
                      :disabled="disabled" @input="mobilePhoneRegular">
            </el-input>
        </div>
        <div style="margin-top: 30px;margin-left: 20px;margin-right: 20px">
            <el-input placeholder="再次确认密码" v-model="input3" minlength="11" maxlength="11" clearable type="password"
                      :disabled="disabled" @input="mobilePhoneRegular">
            </el-input>
        </div>
        <div style="text-align: center;margin-top: 30px">
            <el-button type="danger" :disabled="disabled2" @click="sub">修改</el-button>
        </div>

    </div>
</template>
<script>
    export default {
        data() {
            return {
                input1: "",
                input2: "",
                input3: "",
                disabled: true,
                disabled2: true,
                msg: ""
            }
        },
        methods: {
            anExamination() {
                this.$axios.post('http://127.0.0.1:8081/api/user/changePassword', {
                    id: localStorage.getItem("id"),
                    username: localStorage.getItem("username"),
                    password: this.input1,
                }).then(res => {
                    console.log(res.data);
                    this.msg = res.data;
                    this.disabled = this.msg !== 1;
                }, err => {
                    console.log(err);
                });

            },
            mobilePhoneRegular() {
                if (this.input2 === this.input3 && this.input2 !== "") {
                    this.disabled2 = false;
                } else {
                    this.disabled2 = true;
                }
            },
            back() {
                this.input1 = "";
                this.input2 = "";
                this.$router.go(-1);//返回上一层
            },
            sub() {
                this.$axios.post('http://127.0.0.1:8081/api/user/changePassword1', {
                    id: localStorage.getItem("id"),
                    username: localStorage.getItem("username"),
                    password: this.input3,
                }).then(res => {
                    console.log(res.data);
                }, err => {
                    console.log(err);
                });

                this.input1 = "";
                this.input2 = "";
                this.input3 = "";
                this.$router.go(-1);//返回上一层
            },


        },
    }
</script>
<style lang="less" scoped>
    .app {
        background-color: #ffffff;

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
        }
    }


</style>
