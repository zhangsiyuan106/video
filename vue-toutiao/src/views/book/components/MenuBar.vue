<template>
  <move name="move-down">
    <div class="bottom-wrapper"
         v-show="show"
    >
      <transition name="fold">
        <div class="setting-wrapper" v-show="settingShow">
          <div
                  class="setting-font-size"
                  v-if="showTag === 0"
          >
            <div
                    class="preview"
                    :style="{fontSize: fontSizeList[0].fontSize + 'px'}"
            >A</div>
            <div class="select">
              <div
                      class="select-wrapper"
                      v-for="item in fontSizeList"
                      :key="item.fontSize"
                      @click="setFontSize(item.fontSize)"
              >
                <div class="line"></div>
                <div class="point-wrapper">
                  <div
                          class="point"
                          v-show="defaultFontSize === item.fontSize"
                  >
                    <div class="small-point"></div>
                  </div>
                </div>
                <div class="line"></div>
              </div>
            </div>
            <div
                    class="preview"
                    :style="{fontSize: fontSizeList[fontSizeList.length -1].fontSize + 'px'}"
            >A</div>
          </div>
          <div
                  class="setting-theme"
                  v-else-if="showTag === 1"
          >
            <div
                    class="item"
                    v-for="(item, index) in themeList"
                    :key="index"
                    @click="setTheme(index)"
            >
              <div class="preview" :style="item.style.body"></div>
              <div
                      class="text"
                      :class="{'current-theme': defaultTheme === index}"
              >{{item.name}}</div>
            </div>
          </div>
          <div
                  class="setting-progress"
                  v-else-if="showTag === 2"
          >
            <div class="progress-wrapper">
              <input
                      class="progress"
                      type="range"
                      max="100"
                      min="0"
                      step="1"
                      ref="progress"
                      :disabled="!bookAvailable"
                      :style="{'backgroundSize': `${progress}% 100%`}"
                      v-model="progress"
                      @change="onProgressChange($event.target.value)"
              />
            </div>
            <p class="mes">{{bookAvailable ? progress + '%' : '加载中...'}}</p>
          </div>
        </div>
      </transition>
      <div
              class="menu-wrapper"
      >
        <div class="icon-wrapper  icon icon-jindu" @click="showSetting(2)">
          <span class="icon-progress"></span>
        </div>
        <div class="icon-wrapper icon icon-liangdu"
             @click="showSetting(1)"

        >
          <span class="icon-bright "></span>
        </div>
        <div class="icon-wrapper icon-font" @click="showSetting(0)">
          <span>A</span>
        </div>
      </div>
    </div>
  </move>
</template>
<script>
  import Move from "./Move"
  export default {
    name: 'MenuBar',
    props: {
      show: {
        type: Boolean,
        default: false
      },
      fontSizeList: Array,
      defaultFontSize: Number,
      themeList: Array,
      defaultTheme: Number,
      bookAvailable: Boolean
    },
    components: {
      Move
    },
    data () {
      return {
        settingShow: false,
        showTag: null,
        progress: 0
      }
    },
    methods: {
      // 拖动进度条时触发事件 使用@input来监听 此处用v-model代替
      onProgressInput (progress) {
        this.progress = progress
        this.$refs.progress.style.backgroundSize = `${this.progress}% 100%`
      },
      // 进度条松开后触发事件，根据进度条数值跳转到指定位置 使用@change来监听
      onProgressChange (progress) {
        this.$emit('onProgressChange', progress)
      },
      setTheme (index) {
        this.$emit('setTheme', index)
      },
      showSetting (tag) {
        if (this.showTag === tag) {
          this.showTag = null
          this.hideSetting()
        } else {
          this.showTag = tag
          this.settingShow = true
        }
      },
      hideSetting () {
        this.settingShow = false
      },
      setFontSize (fontSize) {
        this.$emit('setFontSize', fontSize)
      }
    }
  }
</script>
<style lang="stylus" scoped>
  .fold-enter, .fold-leave-to
    transform: translate(0, 0) !important
  .fold-enter-active, .fold-leave-active
    transition: all .3s
  .bottom-wrapper
    position: fixed
    bottom: 0
    right: 0
    left: 0
    width: 100%
    height: 48px
    z-index: 102
    box-shadow: 0 -6px 6px rgba(0, 0, 0, .13)
    transition: height .3s
    &.addHeight
      height: 108px
    .menu-wrapper
      position relative
      z-index: 102
      display: flex
      align-items: center
      width: 100%
      height: 48px
      background: #fff
      &.shadow
        box-shadow: 0 -6px 6px rgba(0, 0, 0, .13)
      .icon-wrapper
        flex: 1
        text-align: center
        .icon-jindu
          margin-left 0

    .setting-wrapper
      position: absolute
      left: 0
      right: 0
      top: 0
      transform: translate(0, -100%)
      display: flex
      width: 100%
      height: 60px
      background: #fff
      box-shadow: 0 -6px 6px rgba(0, 0, 0, .13)
      .setting-font-size
        display: flex
        flex: 1
        width: 100%
        .preview
          flex: 0 0 40px
          display: flex
          justify-content: center
          align-items: center
        .select
          display: flex
          flex: 1
          .select-wrapper
            flex: 1
            display: flex
            align-items: center
            &:first-child
              .line:first-child
                border: none
            &:last-child
              .line:last-child
                border: none
            .line
              flex: 1
              height: 0
              border-top: 1px solid #cccccc
            .point-wrapper
              position: relative
              flex: 0 0 0
              width: 0
              height: 7px
              border-left: 1px solid #cccccc
              .point
                position: absolute
                top: -6.5px
                left: -10px
                display: flex
                justify-content: center
                align-items: center
                width: 20px
                height: 20px
                border-radius: 50%
                border: 1px solid #ccc
                background: #fff
                box-shadow: 0 2px 5px #ccc
                .small-point
                  width: 5px
                  height: 5px
                  border-radius: 50%
                  background: #000
      .setting-theme
        display: flex
        flex: 1
        .item
          flex: 1
          display: flex
          flex-direction: column
          justify-content: center
          align-items: center
          box-sizing: border-box
          padding: 0 10px
          &:first-child
            .preview
              border: 1px solid #777
          .preview
            width: 100%
            height: 20px
            background: yellow
            border-radius: 3px
          .text
            margin: 5px 0
            color: #777
            &.current-theme
              color: #333
              text-shadow: 1px 2px 5px #ccc
      .setting-progress
        flex: 1
        width: 100%
        display: flex
        flex-direction: column
        background: #fff
        .progress-wrapper
          flex: 1
          display: flex
          align-items: center
          padding: 0 15px
          box-sizing: border-box
          .progress
            width: 100%
            appearance: none
            height: 2px
            background: linear-gradient(#999, #999) no-repeat #ddd
            background-size: 20% 100%
            &:focus
              outline: none
            &::-webkit-slider-thumb
              appearance: none
              width: 20px
              height: 20px
              border-radius: 50%
              background: #fff
              border: 1px solid #ddd
              box-shadow: 0 2px 5px #ddd
        .mes
          font-size: 14px
          text-align: center
</style>
