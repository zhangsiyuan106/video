<template>
    <section class="body-wrapper border-half">
        <div class="li border-half-top" @click="$router.push('/msg')">
            <van-icon name="comment-o" class="icond"  size="25" />
            <div>消息通知</div>
            <Icon name="arrow"></Icon>
        </div>
        

      <div class="li border-half-top" @click="$router.push('/creative')">
          <van-icon name="edit"  class="icond"  size="25"/>
        <div>创作中心</div>
        <Icon name="arrow"></Icon>
      </div>
        <div class="li border-half-top"  @click="$router.push('/invite')">
            <van-icon name="hot-o" class="icond-inv" size="25" />
            <div>邀请好友赢现金</div>
            <Icon name="arrow"></Icon>
        </div>

        <div class="li border-half-top" @click="$router.push('/system')">
            <van-icon name="setting-o" class="icond" size="25" />
            <div>系统设置</div>
            <Icon name="arrow"></Icon>
        </div>

        <div class="li border-half-top" @click="$router.push('/master')">
            <van-icon name="desktop-o" class="icond" size="25"/>
        <div>后台管理</div>
        <Icon name="arrow"></Icon>
      </div>
    </section>
</template>
<script>
    import Vue from 'vue';
    import { Icon } from 'vant';
    Vue.use(Icon);
    export default {
        data() {
            return{
            }
        },
        methods:{

        },

    }
</script>
<style lang="less" scoped>
    .body-wrapper {
        border-left: none;
        border-right: none;
    }
    .li {
        height: 0.5rem;
        line-height: 0.5rem;
        width: 100%;
        padding: 0 0.1rem;
        background-color: #fff;
        display: flex;
        justify-content: space-between;
        align-items: center;
        svg {
            color: #bbb;
        }
        span {
            color: @font-gray;
            font-size: 0.12rem;
            margin-right: 0.08rem;
        }

        &:nth-child(1) {
            margin: 0.1rem 0;
            &:before {
                border-left: none;
                border-right: none;
            }
        }

        &:nth-child(n) {
            margin-bottom: 0.1rem;
            &:before {
                border-left: none;
                border-right: none;
            }
        }
    }

    .con_red{
        margin-right: 2%;
        width: 33px;
        height: 25px;
        text-align: center;
        line-height: 25px;
        border-radius: 20px;
        background: #F56C6C;
    }
    .icond{
        margin-right: -60%;
    }
    .icond-inv{
        margin-right: -48%;
    }
    .msgs_cls{
        margin-top: 17px;
        margin-left: 25px;
    }
</style>