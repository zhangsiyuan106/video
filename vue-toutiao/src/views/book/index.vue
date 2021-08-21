<template>
    <div class="book">
        <v-touch @swipeleft="swiperleft" @swiperight="swiperright">
            <TitleBar :show="show"></TitleBar>
            <div class="mask flex-row">
                <div class="left flex" @click.stop="pagePrev()"></div>
                <div class="center flex2" @click.stop="pageCenter()"></div>
                <div class="right flex" @click.stop="pageNext()"></div>
            </div>
            <div id="read"></div>
            <MenuBar
                    ref="menuBar"
                    :show="show"
                    :fontSizeList="fontSizeList"
                    :defaultFontSize="defaultFontSize"
                    :themeList="themeList"
                    :defaultTheme="defaultTheme"
                    @setFontSize="setFontSize"
                    @fontUp="fontUp"
                    @fontLow="fontLow"
                    @setTheme="setTheme"
                    :bookAvailable="bookAvailable"
                    @onProgressChange="onProgressChange"
                    :navigation="navigation"
                    @jumpTo="jumpTo"
            ></MenuBar>
        </v-touch>

    </div>
</template>

<script>
    import Epub from "epubjs";


    import TitleBar from "./components/TitleBar";
    import MenuBar from "./components/MenuBar";

    export default {
        components: { TitleBar, MenuBar},

        props: {},

        data() {
            return {
                bookName:'',
                // 显示隐藏菜单栏
                show: false,
                //   字体大小列表
                fontSizeList: [
                    {
                        fontSize: 12,
                    },
                    {
                        fontSize: 14,
                    },
                    {
                        fontSize: 16,
                    },
                    {
                        fontSize: 18,
                    },
                    {
                        fontSize: 20,
                    },
                    {
                        fontSize: 22,
                    },
                    {
                        fontSize: 24,
                    },
                ],
                //   默认字体大小
                defaultFontSize: 16,

                // 主题列表
                themeList: [
                    {
                        name: "default",
                        style: {
                            body: {
                                color: "#000",
                                background: "#fff",
                            },
                        },
                    },
                    {
                        name: "eye",
                        style: {
                            body: {
                                color: "#000",
                                background: "#ceeaba",
                            },
                        },
                    },
                    {
                        name: "night",
                        style: {
                            body: {
                                color: "#fff",
                                background: "#000",
                            },
                        },
                    },
                    {
                        name: "gold",
                        style: {
                            body: {
                                color: "#000",
                                background: "gold",
                            },
                        },
                    },
                ],
                //   默认主题
                defaultTheme: 0,
                // 图书是否处于可用状态
                bookAvailable: false,
                navigation: {},
            };
        },


        watch:{
            '$route':"showEpub()" //需要处理数据的方法
        },


        mounted() {
            this.showEpub();

        },

        computed: {},
        created() {
            this.refresh();
        },
        methods: {
            refresh() {},

            // 电子书的解析和渲染
            showEpub() {
                this.bookName = this.$route.params.bookname;
                var DOWNLOAD_URL = "static/" + this.bookName + ".epub";
                if (this.bookName==null){
                    DOWNLOAD_URL="static/大主宰.epub"
                }
                // 生成Book对象
                this.book = new Epub(DOWNLOAD_URL);
                //   console.log(this.book);
                // 生成Rendition,通过book.renderTo
                this.rendition = this.book.renderTo("read", {
                    width: window.innerWidth,
                    height: window.innerHeight,
                });
                //   通过Rendition.display渲染电子书
                this.rendition.display();
                //   获取theme对象
                this.theme = this.rendition.themes;
                //   设置主题
                // this.theme.register(name,styles)
                // this.theme.select(name)
                this.registerTheme();
                this.setTheme(this.defaultTheme);
                //   设置进度条
                // 获取localtion对象，通过epubjs的钩子函数来实现
                this.book.ready
                    .then(() => {
                        this.navigation = this.book.navigation;
                        // 生成Locations对象
                        return this.book.locations.generate();
                    })
                    .then((res) => {
                        // 保存locations对象
                        this.locations = this.book.locations;
                        // 标记电子书为解析完毕状态
                        this.bookAvailable = true;
                    });
            },

            // 加载上一页
            pagePrev() {
                if (this.rendition) {
                    this.rendition.prev();
                }
            },

            // 显示隐藏菜单栏
            pageCenter() {
                this.show = !this.show;
                if (!this.show) {
                    this.$refs.menuBar.hiddenFontSetting();
                }
            },

            // 加载下一页
            pageNext() {
                if (this.rendition) {
                    this.rendition.next();
                }
            },

            // 左滑
            swiperleft() {
                this.pageNext();
            },

            // 右滑
            swiperright() {
                this.pagePrev();
            },

            // 设置字体大小
            setFontSize(size) {
                this.defaultFontSize = size;
                if (this.theme) {
                    this.theme.fontSize(size + "px");
                }
            },

            // 增大字体
            fontUp() {
                this.defaultFontSize += 2;
                if (this.defaultFontSize >= 24) {
                    this.defaultFontSize = 24;
                }

                if (this.theme) {
                    this.theme.fontSize(this.defaultFontSize + "px");
                }
            },

            // 缩小字体
            fontLow() {
                this.defaultFontSize -= 2;
                if (this.defaultFontSize <= 12) {
                    this.defaultFontSize = 12;
                }

                if (this.theme) {
                    this.theme.fontSize(this.defaultFontSize + "px");
                }
            },

            // 注册主题
            registerTheme() {
                this.themeList.forEach((item) => {
                    this.theme.register(item.name, item.style);
                });
            },

            // 修改主题
            setTheme(index) {
                this.defaultTheme = index;
                this.theme.select(this.themeList[index].name);
            },

            // 进度条数值
            onProgressChange(progress) {
                const percentage = progress / 100;
                const location =
                    percentage > 0 ? this.locations.cfiFromPercentage(percentage) : 0;
                // 跳到哪
                this.rendition.display(location);
            },

            // 根据链接跳转到指定位置
            jumpTo(href) {
                this.rendition.display(href);
                this.hideTitleAndMenu();
            },

            hideTitleAndMenu() {
                // 隐藏标题栏和菜单栏
                this.show = false;
                // 隐藏菜单栏弹出的设置栏
                this.$refs.menuBar.hiddenFontSetting();
                // 隐藏目录
                this.$refs.menuBar.hideContent();
            },
        },
    };
</script>
<style lang="scss" scoped>
    @import "../../sass/_global.scss";
    .book {
        position: relative;
        width: 100vw;
        height: 100vh;
        overflow: hidden;
        .mask {
            display: flex;
            position: absolute;
            left: 0;
            right: 0;
            top: 0;
            bottom: 0;
            width: 100%;
            height: 100%;
            z-index: 100;
            .left {
                height: 100%;
                width: 20%;

            }
            .center {
                flex: 1;


            }
            .right {
                flex: 0 0 15%;

            }
        }
    }
</style>