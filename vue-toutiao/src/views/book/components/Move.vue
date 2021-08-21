<template>
  <transition :name="name">
    <slot></slot>
  </transition>
</template>
<script>
export default {
  name: 'Move',
  props: {
    name: {
      type: String,
      default () {
        return 'move-up'
      }
    }
  }
}
</script>
<style lang="stylus" scoped>
  .move-up-enter,
  .move-up-leave-to
    transform: translate(0, -100%)
  .move-down-enter,
  .move-down-leave-to
    transform: translate(0, 100%)
  .move-up-enter-active,
  .move-up-leave-active,
  .move-down-enter-active,
  .move-down-leave-active
    transition all .3s linear
</style>
