<template>
    <div class="system-wrapper">
        <HeaderBar title="账号设置"></HeaderBar>
        <div class="box border-half">
            <div class="li df-sb border-half-bottom" @click="$router.push('/editName')">头条名称 <div class="xbox">{{nickname}}</div></div>

            <div class="li df-sb border-half-bottom" @click="$router.push('/editPhone')">手机号码<div class="xbox">{{phone}}</div></div>
            <div class="li df-sb border-half-bottom" @click="$router.push('/editPassword')">修改密码</div>
        </div>

    </div>
</template>
<script>
    import { Local } from 'src/utils/storage'
    import { Cookie } from 'src/utils/storage'
    export default {
        data () {
            return {
                info: {
                    send: Local.get('system_send') || false,
                    list: Local.get('system_list') || false
                },
                nickname: Cookie.get("nickname"),
                phone: Cookie.get("phone"),
            }
        },
        methods: {
            changePassword(){

            },
        }
    }
</script>
<style lang="less" scoped>
    .xbox{
        font-size: 0.14rem;
        color: #a7a7a7;
    }
    .box {
        margin-top: 0.1rem;
        padding-left: 0.1rem;
        background: #fff;
        &:before {
            border-left: none;
            border-right: none;
        }
        :last-child {
            &:before {
                border-bottom: none;
            }
        }

        .li {
            width: 100%;
            height: 0.4rem;
            font-size: 0.14rem;
            color: #333;
            padding-right: 0.1rem;
        }
        svg {
            color: #bbb;
        }
        small {
            color: @font-gray;
            font-size: 0.12rem;
        }
    }
    .esc {
        text-align: center;
        line-height: 0.4rem;
        margin-top: 0.3rem;
        color: @theme-red;
    }
</style>
