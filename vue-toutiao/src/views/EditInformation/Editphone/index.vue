<template>
    <div class="app">
        <div class="head">
            <Icon name="jiantou" class="back" @click.native="back"></Icon>
            <div class="name">更换手机号</div>
        </div>

        <div style="margin-top: 15px;margin-left: 20px;font-size: 25px">请输入原手机号</div>
        <div style="margin-top: 30px">
            <el-input placeholder="请输入原机号" v-model="input1" minlength="11" maxlength="11" clearable
                      @input="anExamination">
                <template #prepend>+86</template>
            </el-input>
        </div>
        <div style="margin-top: 15px;margin-left: 20px;font-size: 25px">请输入修改后的手机号</div>
        <div style="margin-top: 30px">
            <el-input placeholder="请输入修改后的手机号" v-model="input2" minlength="11" maxlength="11" clearable
                      :disabled="disabled" @input="mobilePhoneRegular">
                <template #prepend>+86</template>
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
                disabled: true,
                disabled2: true,
                sjh: /^(13[0-9]|14[01456879]|15[0-35-9]|16[2567]|17[0-8]|18[0-9]|19[0-35-9])\d{8}$/,
            }
        },
        methods: {
            anExamination() {
                if (this.input1 === this.$store.state.phone) {
                    this.disabled = false;
                } else {
                    this.disabled = true;
                }
            },
            mobilePhoneRegular() {
                if (this.sjh.test(this.input2)) {
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
                this.$store.state.phone = this.input2;
                localStorage.setItem("phone", this.input2);
                this.$axios.post('http://127.0.0.1:8081/api/user/editPhone', {
                    id: localStorage.getItem("id"),
                    phone: this.input2,
                }).then(res => {
                    console.log(res.data);
                }, err => {
                    console.log(err);
                });

                this.input1 = "";
                this.input2 = "";
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
