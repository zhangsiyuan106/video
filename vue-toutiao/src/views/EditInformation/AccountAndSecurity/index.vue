<template>
    <div class="system-wrapper">
        <HeaderBar title="账号与安全"></HeaderBar>
        <div class="box border-half">
            <div class="li df-sb border-half-bottom" @click="$router.push('/editphone')">手机号
                <div>
                    <span>{{phone==="null"?"待完善":this.$store.state.phone.substring(0,3)+"****"+this.$store.state.phone.substring(7,11)}}</span>
                    <i class="el-icon-arrow-right"></i>
                </div>
            </div>
            <div class="li df-sb border-half-bottom" @click="$router.push('/changePassword')">修改密码
                <div>
                    <i class="el-icon-arrow-right"></i>
                </div>
            </div>
        </div>

        <div class="box border-half">
            <div class="li df-sb border-half-bottom">账号注销</div>
        </div>

    </div>
</template>
<script>
    export default {
        data() {
            return {
                phone: this.$store.state.phone
            }
        },
        methods: {
        }
    }
</script>
<style lang="less" scoped>
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

    .msgbox {
        .el-message-box {
            width: 250px;
        }
    }
</style>
