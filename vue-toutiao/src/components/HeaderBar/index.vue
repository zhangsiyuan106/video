<template>
    <section>
        <div class="head">
            <Icon name="jiantou" class="back" @click.native="back($router)"></Icon>
            <h2 style="display : block;">{{title}}
                <span v-if="anjian" style="padding-left: 120px">{{anjian}}</span>
            </h2>
        </div>
    </section>
</template>
<script>
    export default {
        props: {
            title: {
                type: String,
                default: '天天资讯'
            },
            anjian: {
                type: String,
                default: ""
            }
        }
    }
</script>
<style lang="less" scoped>
    .head {
        width: 100%;
        height: 0.5rem;
        position: relative;
        background-color: #409EFF;

        svg {
            font-size: 0.25rem;
        }

        h2 {
            height: 100%;
            line-height: 0.5rem;
            font-size: 0.15rem;
            text-align: center;
            color: #555;
        }

        .back {
            position: absolute;
            left: 0.1rem;
            top: 50%;
            transform: translateY(-50%);
        }
    }
</style>
