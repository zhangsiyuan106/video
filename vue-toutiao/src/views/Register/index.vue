<template>
    <div class="record-wrapper">
        <HeaderBar title="注册用户"></HeaderBar>
        <van-form @submit="onSubmit">
            <van-field
                    v-model="username"
                    name="username"
                    clearable
                    label="用户名"
                    placeholder="用户名"
                    maxlength="8"
                    :rules="[{ required: true, message: '请填写用户名' }]"
            />
            <van-field
                    v-model="password"
                    type="password"
                    clearable
                    name="password"
                    label="密码"
                    placeholder="密码"
                    maxlength="16"
                    :rules="[{ required: true, message: '请填写密码' }]"
            />
            <van-field
                    v-model="password2"
                    type="password"
                    clearable
                    name="password2"
                    label="确认密码"
                    placeholder="确认密码"
                    maxlength="16"
                    :rules="[{ required: true, message: '请填写密码' }]"
            />

            <van-field
                    v-model="phone"
                    name="phone"
                    clearable
                    label="手机号"
                    placeholder="手机号"
                    :rules="[{ pattern:/^1(3[0-9]|4[01456879]|5[0-35-9]|6[2567]|7[0-8]|8[0-9]|9[0-35-9])\d{8}$/,required: true, message: '请填写正确的手机号' }]"
            />

            <van-field
                    v-model="email"
                    name="email"
                    clearable
                    label="邮箱号"
                    placeholder="邮箱号"
                    :rules="[{ pattern: /^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$/,required: true, message: '请填写正确的邮箱号'}]"
            />

            <van-field
                    v-model="verificationCode"
                    center
                    clearable
                    label="邮箱验证码"
                    name="verificationCode"
                    placeholder="请输入邮箱验证码"
                    maxlength="6"
                    :rules="[{ required: true, message: '请填写正确的验证码' }]"
            >
                <template #button>
                    <van-button size="small" type="primary" native-type="button" v-if="flag" @click="sendEmail">发送验证码
                    </van-button>
                    <van-button disabled size="small" type="primary" v-if="!flag">{{js}}后再次获取</van-button>
                </template>
            </van-field>

            <van-field
                    v-model="code"
                    type="password"
                    name="code"
                    style="display: none"
            />

            <div style="margin: 16px;">
                <van-button round block type="info" native-type="submit">注册</van-button>
            </div>
        </van-form>
    </div>
</template>

<script>

    export default {
        data() {
            return {
                username: '',
                password: '',
                password2: '',
                phone: '',
                email: '',
                verificationCode: '',
                js: 30,
                flag: true,
                emailPattern:/^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$/,
                code: ''
            };
        },
        methods: {
            onSubmit(values) {
                this.$axios.post('http://localhost:8081/api/user/register2', {
                    values
                }).then(res => {
                    this.$toast.fail(res.data);
                    if(res.data === "注册成功"){
                        this.$router.push("/account");
                    }
                }, err => {
                    console.log(err);
                })
            },
            sendEmail() {
                if(!this.emailPattern.test(this.email)){
                    this.$toast.fail('邮箱号错误，请重新输入');
                    return;
                }
                this.$axios.post('http://localhost:8081/api/mail/sendmail', {
                    email: this.email
                }).then(res => {
                    this.code=res.data;
                }, err => {
                    console.log(err);
                });
                this.flag = !this.flag;
                let timers = setInterval(() => {
                    this.js--;
                    if (this.js === -1) {
                        this.flag = !this.flag;
                        this.js = 30;
                        clearInterval(timers);
                    }
                }, 1000)
            }
        },
    };
</script>

<style lang="less" scoped>
    .el-register-title {
        height: 40px;
        line-height: 40px;
        position: fixed;
        width: 100%;
        top: 45px;
        text-align: center;
        color: #10ffa4;
        font-family: Arial;
        font-size: 30px;
        letter-spacing: 1px;
    }

    // 跟节点样式
    .register_container {
        background-color: #164ca7;

        background-repeat: no-repeat;
        background-size: cover;
        height: 100%;
    }

    .el-register-footer {
        height: 40px;
        line-height: 40px;
        position: fixed;
        bottom: 0;
        width: 100%;
        text-align: center;
        color: #fff;
        font-family: Arial;
        font-size: 12px;
        letter-spacing: 1px;
    }

    // 登录框
    .register_box {
        width: 400px;
        height: 350px;
        background-color: #fff;
        border-radius: 3px; // 圆角
        position: absolute; // 绝对定位
        left: 50%;
        top: 50%;
        transform: translate(-50%, -50%); // 根据自己位置 以自己为长度位移
        // 头像框

        .btns {
            display: flex; // 弹性布局
            justify-content: flex-end; // 尾部对齐
        }

        .register_form {
            left: 10%;
            position: absolute;

            bottom: 50%;
            height: 20%;
            width: 80%;
            padding: 0px 10px;
            box-sizing: border-box; // 设置边框
        }

    }

</style>
