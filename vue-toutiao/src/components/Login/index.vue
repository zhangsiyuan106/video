<template>
    <div class="login-wrapper">
        <Icon name="close" class="close" @click.native="$emit('close')"></Icon>
        <h2>登陆你的头条，精彩永不消失</h2>
        <div class="input username">
            <input type="text" placeholder="请输入用户名" v-model="username">
            <span :class="{'animate': username_msg}">{{username_msg}}</span>
        </div>
        <div class="input password">
            <input type="password" placeholder="请输入密码" v-model="password">
            <span :class="{'animate': password_msg}">{{password_msg}}</span>
        </div>
        <button class="login" @click="login">登录</button>
        <button class="register" @click="registerPage">注册</button>
    </div>
</template>
<script>
    export default {
        data() {
            return {
                username: '',      //用户名
                username_msg: '',
                password: '',      //密码
                password_msg: '',

            }
        },
        methods: {
            registerPage() {
                this.$store.state.user.isLogin = false;
                this.$store.state.user.isRegister = true;
                this.$router.push({path: "/registerPage"});
            },
            login() {
                if (!this.username) {
                    this.username_msg = '请输入用户名'
                } else {
                    this.username_msg = ''
                }
                if (this.username_msg) return;

                if (!this.password) {
                    this.password_msg = '请输入密码'
                } else {
                    this.password_msg = ''
                }
                if (this.password_msg) return;
                this.$axios.post('http://localhost:8081/api/user/login', {
                    "userName": this.username,
                    "userPassword": this.password,
                }).then(res => {
                    if (res.data.status === 400) {
                        this.password_msg = res.data.msg;
                    }
                    if (res.data.status === 200) {
                        console.log(res);
                        localStorage.setItem('id', res.data.id);
                        localStorage.setItem('username', this.username);

                        if (res.data.nickname === null) {
                            localStorage.setItem('nickname', this.username);
                        } else {
                            localStorage.setItem('nickname', res.data.nickname);
                        }
                        if (res.data.avatar === null) {
                            localStorage.setItem('avatar', "http://localhost:80/group1/M00/00/00/rBYSRGD2tKSAZ7gjAAFu3XNGxO0316.png");
                        } else {
                            localStorage.setItem('avatar', res.data.avatar);
                        }
                        if (res.data.backgroundImage === null) {
                            localStorage.setItem('backgroundImage', "http://localhost/group1/M00/00/01/rBYSRGD-a-iAV8UIAADBRygr6Ks544.jpg");
                        } else {
                            localStorage.setItem('backgroundImage', res.data.backgroundImage);
                        }
                        localStorage.setItem('phone', res.data.phone);
                        localStorage.setItem('email', res.data.email);
                        localStorage.setItem('sex', res.data.sex);
                        localStorage.setItem('introduction', res.data.introduction);
                        localStorage.setItem('token', res.data.taken);

                        this.$showLoading();
                        try {
                            this.$store.dispatch('login', {
                                username: this.username,
                                password: this.password
                            })
                        } catch (e) {
                        }
                        this.$hideLoading();

                        this.$set(this.$store.state.user.footerBarList, 3, {
                            title: '我的',
                            icon: 'account1',
                            path: '/account'
                        });
                        this.$emit('close');
                        this.$router.go(0);
                    }
                }, err => {
                    console.log(err);
                });
                if (localStorage.getItem("photo") === null) {

                }

            },

        }
    }
</script>
<style lang="less" scoped>
    .login-wrapper {
        position: fixed;
        width: 100%;
        height: 100%;
        left: 0;
        top: 100%;
        z-index: 1000;
        opacity: 0;
        background-color: #fff;
        border-top-right-radius: 0.05rem;
        border-top-left-radius: 0.05rem;
        text-align: center;
        transition: top .2s ease-out;

        .close {
            position: absolute;
            right: 0rem;
            top: 0rem;
            font-size: 0.2rem;
            width: 0.4rem;
            height: 0.4rem;
            padding: 0.1rem;
            color: #555;
        }

        h2 {
            margin: 0.3rem 0;
            color: #333;
            font-size: 0.2rem;
            text-align: center;
        }

        .input {
            width: 80%;
            height: 0.4rem;
            margin: 0 auto;
            margin-bottom: 0.3rem;
            position: relative;

            span {
                position: absolute;
                left: 0.15rem;
                top: 0.45rem;
                font-size: 0.1rem;
                color: @theme-red;
                opacity: 0;
            }

            .animate {
                opacity: 1;
                animation: swing .5s linear;
            }

            @keyframes swing {
                10% {
                    transform: translateX(-0.06rem);
                }

                30% {
                    transform: translateX(0.06rem);
                }

                50% {
                    transform: translateX(-0.06rem);
                }

                70% {
                    transform: translateX(0.06rem);
                }
                80% {
                    transform: translateX(-0.04rem);
                }
                90% {
                    transform: translateX(0.04rem);
                }

                100% {
                    transform: translateX(0rem);
                }
            }
        }

        input {
            border: 1px solid #ccc;
            border-radius: 0.2rem;
            width: 100%;
            height: 100%;
            text-indent: 0.15rem;
            outline: none;
            font-size: 0.14rem;
            color: #333;
        }

        .login {
            margin-top: 0.2rem;
            width: 80%;
            height: 0.4rem;
            border-radius: 0.2rem;
            line-height: 0.4rem;
            color: #f4f4f4;
            border: none;
            background-color: @theme-bg-red;
        }

        .register {
            margin-top: 0.2rem;
            width: 80%;
            height: 0.4rem;
            border-radius: 0.2rem;
            line-height: 0.4rem;
            color: #f4f4f4;
            border: none;
            background-color: @theme-bg-red;
        }
    }
</style>
