<template>
    <div class="record-wrapper">
        <van-nav-bar
                title="后台管理"
                left-text="返回"
                left-arrow
                @click-left="onClickLeft"

        />


    <div class="login_container">
        <div class="login_box">
            <div class="avatar_box">
                <!--头像-->
                <img :src=srcUrl alt />
            </div>

            <!--添加表单 loginRules 校验规则  :model="loginForm"双向绑定-->
            <el-form ref="loginFormRef" :model="loginForm" :rules="loginRules" class="login_form"
                     label-width="0px">
                <!--       prop="username" 校验属性 -->
                <el-form-item prop="userName">
                    <!--          iconfont icondenglu 样式 图标-->
                    <el-input v-model="loginForm.userName" prefix-icon="iconfont icondenglu"></el-input>
                </el-form-item>
                <el-form-item prop="userPassword">
                    <el-input v-model="loginForm.userPassword" prefix-icon="iconfont iconmima" type="password"></el-input>
                </el-form-item>
                <el-form-item class="btns">

                    <el-button type="primary" @click="login">登录</el-button>
                    <el-button type="info" @click="resetLoginForm">重置</el-button>
                </el-form-item>
            </el-form>
        </div>


        <div class="el-login-footer">

            <span >Copyright © 2018-2021 tt.vip All Rights Reserved.</span>
        </div>
    </div>
    </div>
</template>

<script>
    import { Toast } from 'vant';
    export default {
        //数据
        data() {
            return {
                srcUrl: 'http://localhost:80/group1/M00/00/01/rBYSRGEPXweABDRWAAAaEiIIUIM387.jpg',
                loginForm: {
                    userName: "",
                    userPassword: "",
                },
                //username 对应 prop参数 绑定参数
                loginRules: {
                    userName: [
                        //失去焦点触发
                        { required: true, message: '请输入用户名称', trigger: 'blur' },
                        { min: 3, max: 12, message: '长度在 3 ~ 12 个字符', trigger: 'blur' }
                    ],
                    userPassword: [
                        { required: true, message: '请输入密码', trigger: 'blur' },
                        { min: 6, max: 16, message: '长度在 8 ~ 16 个字符', trigger: 'blur' }
                    ],

                },
            };
        },
        //方法
        methods: {
            onClickLeft() {
                this.$router.back()
            },
            resetLoginForm() {
                //resetFields重置表单的方法
                // this.$refs.loginFormRef 是form表单对象
                //console.log( this.$refs.loginFormRef)
                this.$refs.loginFormRef.resetFields();
            },

            login() {
                //页面加载后对表单进行验证 validate({}）
                //async 异步  await 等待一个异步方法执行完成
                this.$refs.loginFormRef.validate(async valid => {
                    //valid做验证判断  返回true或者false
                    if (!valid) return;
                    // 调用get请求  res 也就是后台返回的数据
                    const {data: res} = await this.$http.get("/user/adminLogin/"+this.loginForm.userName + "/"+ this.loginForm.userPassword);
                    // console.log(res.data)
                    if (res.status === 200 && res.data.perms==="admin"){
                        window.sessionStorage.setItem("user",this.loginForm.userName);
                        this.$router.push({path: "/masterHome"}); //路由跳转到home页面
                    }else {
                        this.$router.push({path: "/unauthorized"}); //无权进入
                    }
                });
            }
        }
    };
</script>

<style lang="less" scoped>
    .el-login-title{
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
    .title{
        display: inline-block;
        margin-left: 0px;
    }
    // 跟节点样式
    .login_container {
        background-color: #164ca7;
        background-image: url("../../assets/bg1.jpg");
        background-repeat: no-repeat;
        background-size: cover;
        height: 100%;
    }
    .el-login-footer {
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
    .master {
        width: 100%;
        font-size: 25px;

        height: 0.25rem;
        position: relative;
        background-color: #3affd1;
    }
    // 登录框
    .login_box {
        width: 400px;
        height: 350px;
        background-color: #fff;
        border-radius: 3px;// 圆角
        position: absolute;// 绝对定位
        left: 50%;
        top: 50%;
        transform: translate(-50%, -50%);// 根据自己位置 以自己为长度位移
        // 头像框
        .avatar_box {
            width: 120px;
            height: 120px;
            border: 1px solid #eee;
            border-radius: 50%; // 加圆角
            padding: 10px;
            box-shadow: 0 0 10px #ddd;// 盒子阴影
            position: absolute;
            left: 50%;
            transform: translate(-50%, -50%);
            background-color: #0ee;
            img {
                width: 100%;
                height: 100%;
                border-radius: 50%; // 加圆角
                background-color: #eee;
            }
        }
        .btns {
            display: flex;// 弹性布局
            justify-content: flex-end; // 尾部对齐
        }
        .login_form {
            left: 10%;
            position: absolute;

            bottom: 50%;
            height: 20%;
            width: 80%;
            padding: 0px 10px;
            box-sizing: border-box;// 设置边框
        }

    }

</style>
