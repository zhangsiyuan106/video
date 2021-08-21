<template>
    <div>
        <van-sticky>
            <van-nav-bar
                    :title="city"
                    right-text="更换城市"
                    left-arrow
                    @click-left="returnBack"
                    @click-right="onClickRight"
            />
        </van-sticky>
        <div style="position: relative">
            <el-card class="box-card" shadow="hover" :body-style="{ backgroundColor:  '#F3F3F3' }">
                <img v-if="weather[1].weather.indexOf('阴') !== -1"
                     src="../../assets/images/weather/top/阴.jpg" style="height: 100px;width: 100px;margin-left: 10%">
                <img v-else-if="weather[1].weather.indexOf('雨') !== -1"
                     src="../../assets/images/weather/top/雨.jpg" style="height: 100px;width: 100px;margin-left: 10%">
                <img v-else-if="weather[1].weather.indexOf('云') !== -1"
                     src="../../assets/images/weather/top/多云2.jpg" style="height: 100px;width: 100px;margin-left: 10%">
                <img v-else-if="weather[1].weather.indexOf('晴') !== -1"
                     src="../../assets/images/weather/top/晴.jpg" style="height: 100px;width: 100px;margin-left: 10%">

                <div style="display: inline-block;position: absolute;right: 10%">
                    <van-button round size="mini" type="primary" color="#a7a7a7">今天</van-button>
                    <span style="position:absolute;top:5px;left: 40px"> {{weather[1].dayS}}</span>
                    <h2>{{weather[1].temperature}}</h2>
                    <h1>{{weather[1].weather}}</h1>
                    <van-button round size="mini" type="primary" color="#F8C21C" v-if="weather[0].air[0]>50">
                        {{weather[0].air[0]}} 空气质量良
                    </van-button>
                    <van-button round size="mini" type="primary" v-else>{{weather[0].air[0]}} 空气质量优</van-button>
                </div>
            </el-card>
        </div>

        <div style="position: relative">
            <el-card class="box-card" shadow="hover" :body-style="{ backgroundColor:  '#F3F3F3' }">
                <img v-if="weather[2].weather.indexOf('阴') !== -1"
                     src="../../assets/images/weather/top/阴.jpg" style="height: 100px;width: 100px;margin-left: 10%">
                <img v-else-if="weather[2].weather.indexOf('雨') !== -1"
                     src="../../assets/images/weather/top/雨.jpg" style="height: 100px;width: 100px;margin-left: 10%">
                <img v-else-if="weather[2].weather.indexOf('云') !== -1"
                     src="../../assets/images/weather/top/多云2.jpg" style="height: 100px;width: 100px;margin-left: 10%">
                <img v-else-if="weather[2].weather.indexOf('晴') !== -1"
                     src="../../assets/images/weather/top/晴.jpg" style="height: 100px;width: 100px;margin-left: 10%">
                <div style="display: inline-block;position: absolute;right: 12%">
                    <van-button round size="mini" type="primary" color="#a7a7a7">明天</van-button>
                    <span style="position:absolute;top:5px;left: 40px"> {{weather[2].dayS}}</span>
                    <h2>{{weather[2].temperature}}</h2>
                    <h1>{{weather[2].weather}}</h1>
                </div>
            </el-card>
        </div>

        <van-swipe class="my-swipe" indicator-color="white">
            <van-swipe-item>
                <div style="color: #000000;width: 100%" class="weather-context">
                    <div style="font-size:12px;line-height: 30px;width: 60px;
                    margin-left: 1px;display: inline-block;margin-top: 10px" v-for="i in 6">
                        <p>{{weather[i-1].dayC}}</p>
                        <p>{{weather[i-1].dayS}}</p>
                        <img v-if="weather[i-1].weather.indexOf('阴') !== -1"
                             src="../../assets/images/weather/under/阴.jpg" style="height: 30px;width: 30px">
                        <img v-else-if="weather[i-1].weather.indexOf('雨') !== -1"
                             src="../../assets/images/weather/under/雨.jpg" style="height: 30px;width: 30px">
                        <img v-else-if="weather[i-1].weather.indexOf('云') !== -1"
                             src="../../assets/images/weather/under/多云.jpg" style="height: 30px;width: 30px">
                        <img v-else-if="weather[i-1].weather.indexOf('晴') !== -1"
                             src="../../assets/images/weather/under/晴.jpg" style="height: 30px;width: 30px">
                        <p>{{weather[i-1].temperature}}</p>
                        <p>{{weather[i-1].weather}}</p>
                        <p>{{weather[i-1].windDirection}}</p>
                        <p>{{weather[i-1].quality}}</p>
                    </div>
                </div>
            </van-swipe-item>
            <template #indicator>
                <div></div>
            </template>
        </van-swipe>

        <el-card class="box-card">
            <template #header>
                <div class="card-header">
                    <span>今天空气质量</span>
                    <span style="font-size: 12px;color: #a7a7a7">中国坏境保护部：08：00发布</span>
                </div>
            </template>
            <div class="text item">
                <van-progress
                        :percentage="weather[0].air[0]/10"
                        :pivot-text="weather[0].air[0]"
                        pivot-color="#92C160"
                        color="#92C160"
                />
                <h3 style="margin-top: 10px">{{weather[0].air[2]}}</h3>
                <div style="display: inline-block;margin-left: 10px">
                    <img src="../../assets/images/weather/context/mask.jpg" alt="" style="width: 50px;height: 50px">
                    <p style="font-size: 12px">{{weather[0].air[3]}}</p>
                </div>
                <div style="display: inline-block;margin-left: 20px">
                    <img src="../../assets/images/weather/context/play.jpg" alt="" style="width: 50px;height: 50px">
                    <p style="font-size: 12px">{{weather[0].air[4]}}</p>
                </div>
                <div style="display: inline-block;margin-left: 20px">
                    <img src="../../assets/images/weather/context/go.jpg" alt="" style="width: 50px;height: 50px">
                    <p style="font-size: 12px">{{weather[0].air[5]}}</p>
                </div>
                <div style="display: inline-block;margin-left: 20px">
                    <img src="../../assets/images/weather/context/window.jpg" alt="" style="width: 50px;height: 50px">
                    <p style="font-size: 12px">{{weather[0].air[6]}}</p>
                </div>
            </div>
        </el-card>

    </div>
</template>

<script>
    export default {
        name: "weather",
        data() {
            return {
                city: this.$route.query.city || '成都',
                weather: [{dayC: '', dayS: '', quality: '', temperature: '', weather: '', windDirection: '', air: []}],
                cityNum: this.$route.query.cityNum || '56294',

            }
        },
        created() {
            this.$axios.get('http://localhost:8081/api/getWeather', {
                params: {
                    cityNum: this.cityNum,
                }
            }).then(res => {
                this.weather = res.data
            }, err => {
                console.log(err);
            })
        },
        methods: {
            onClickRight() {
                this.$router.push('/city')
            },
            returnBack(){
                this.$store.state.home.newsIndex = 0;
                this.$router.push('/')

            }
        },
    }
</script>

<style scoped>

    .my-swipe .van-swipe-item {
        color: #fff;
        font-size: 20px;
        line-height: 150px;
        text-align: center;
        background-color: #F9F9F9;
    }

    .custom-indicator {
        position: absolute;
        right: 5px;
        bottom: 5px;
        padding: 2px 5px;
        font-size: 12px;
        background: rgba(0, 0, 0, 0.1);
    }

    .card-header {
        display: flex;
        justify-content: space-between;
        align-items: center;
    }

    .text {
        font-size: 14px;
    }

    .item {
        margin-bottom: 18px;
    }

    .box-card {
        width: 100%;
    }
</style>
