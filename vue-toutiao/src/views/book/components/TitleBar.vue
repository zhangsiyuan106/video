<template>
  <move>
    <div class="title-wrapper" v-show="show">
      <div class="left">
        <span class="icon-back icon" @click="returnback"></span>
      </div>
      </div>
  </move>
</template>
<script>
  import Move from './Move'
  export default {
    name: 'TilteBar',
    props: {
      show: {
        type: Boolean,
        default: false
      }
    },
    methods:{
      returnback(){
        this.$router.go(-1)
      }
    },
    components: {
      Move
    }
  }
</script>
<style lang="stylus" scoped>
  .title-wrapper
    position: absolute
    top: 0
    left: 0
    right: 0
    z-index 101
    display: flex
    width: 100%
    height: 48px
    line-height: 48px
    background: #fff
    box-shadow: 0 6px 6px rgba(0, 0, 0, .13)
    .left
      flex: 0 0 60px
      padding-left: 20px
    .right
      flex: 1
      display: flex
      justify-content: flex-end
      .icon-wrapper
        display: flex
        align-items: center
        width: 40px
</style>
