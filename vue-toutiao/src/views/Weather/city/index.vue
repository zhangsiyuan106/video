<template>
    <div>
            <van-nav-bar
                    title="地区选择"
                    left-arrow
                    @click-left="back($router)"
            />
        <van-index-bar :index-list="indexList">
            <van-index-anchor index="A"/>
            <van-cell title="安徽" @click="sendNum(58321,'安徽')"/>
            <van-cell title="澳门" @click="sendNum(45011,'澳门')"/>
            <van-index-anchor index="B"/>
            <van-cell title="北京" @click="sendNum(57516,'北京')"/>
            <van-index-anchor index="C"/>
            <van-cell title="重庆" @click="sendNum(54511,'重庆')"/>
            <van-index-anchor index="F"/>
            <van-cell title="福建" @click="sendNum(58847,'福建')"/>
            <van-index-anchor index="G"/>
            <van-cell title="甘肃" @click="sendNum(52889,'甘肃')"/>
            <van-cell title="广东" @click="sendNum(59287,'广东')"/>
            <van-cell title="广西" @click="sendNum(59431,'广西')"/>
            <van-cell title="贵州" @click="sendNum(57816,'贵州')"/>
            <van-index-anchor index="H"/>
            <van-cell title="海南" @click="sendNum(59758,'海南')"/>
            <van-cell title="河北" @click="sendNum(53698,'河北')"/>
            <van-cell title="河南" @click="sendNum(57083,'河南')"/>
            <van-cell title="黑龙江" @click="sendNum(50953,'黑龙江')"/>
            <van-cell title="湖北" @click="sendNum(57494,'湖北')"/>
            <van-cell title="湖南" @click="sendNum(57687,'湖南')"/>
            <van-index-anchor index="J"/>
            <van-cell title="吉林" @click="sendNum(54161,'吉林')"/>
            <van-cell title="江苏" @click="sendNum(58238,'江苏')"/>
            <van-cell title="江西" @click="sendNum(58606,'江西')"/>
            <van-index-anchor index="L"/>
            <van-cell title="辽宁" @click="sendNum(54342,'辽宁')"/>
            <van-index-anchor index="N"/>
            <van-cell title="内蒙古" @click="sendNum(53463,'内蒙古')"/>
            <van-cell title="宁夏" @click="sendNum(53614,'宁夏')"/>
            <van-index-anchor index="Q"/>
            <van-cell title="青海" @click="sendNum(52866,'青海')"/>
            <van-index-anchor index="S"/>
            <van-cell title="山东" @click="sendNum(54823,'山东')"/>
            <van-cell title="山西" @click="sendNum(53772,'山西')"/>
            <van-cell title="陕西" @click="sendNum(57036,'陕西')"/>
            <van-cell title="上海" @click="sendNum(58362,'上海')"/>
            <van-cell title="四川" @click="sendNum(56294,'四川')"/>
            <van-index-anchor index="T"/>
            <van-cell title="台湾" @click="sendNum(59554,'台湾')"/>
            <van-cell title="天津" @click="sendNum(54527,'天津')"/>
            <van-index-anchor index="X"/>
            <van-cell title="西藏" @click="sendNum(55591,'西藏')"/>
            <van-cell title="香港" @click="sendNum(45007,'香港')"/>
            <van-cell title="新疆" @click="sendNum(51463,'新疆')"/>
            <van-index-anchor index="Y"/>
            <van-cell title="云南" @click="sendNum(56778,'云南')"/>
            <van-index-anchor index="Z"/>
            <van-cell title="浙江" @click="sendNum(58457,'浙江')"/>

        </van-index-bar>
    </div>

</template>

<script>
    export default {
        name: "city",
        data() {
            return {
                indexList: ['A', 'B', 'C', 'F', 'G', 'H', 'J', 'L', 'N', 'Q', 'S', 'T', 'X', 'Y', 'Z']
            }
        },
        methods: {
            sendNum(cityNum, city) {
                this.$router.push({
                    path: '/weather',
                    query: {cityNum, city}

                })
            }

        }

    }
</script>

<style scoped>

</style>
