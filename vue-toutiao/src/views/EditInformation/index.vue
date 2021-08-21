<template>
    <div class="app">
        <div class="head">
            <Icon name="jiantou" class="back" @click.native="back($router)"></Icon>
            <div class="name">编辑资料</div>
        </div>
        <div class="avatar-main">
            <el-upload
                    class="avatar-uploader"
                    action="http://localhost:8081/api/user/uploadAvatar"
                    :data="fileData"
                    :show-file-list="false"
                    :on-success="handleAvatarSuccess"
                    :before-upload="beforeAvatarUpload"
            >
                <img v-if="imageUrl" :src="imageUrl" class="avatar">
            </el-upload>
        </div>
        <div class="avatar-hint">点击更换头像</div>
        <ul class="from">
            <el-collapse accordion>
                <el-collapse-item disabled @click.native="$router.push('/editname')">
                    <template #title>
                        <div style="color: #000000">用户名</div>
                        <div style="position:absolute;right: 35px;color: #000000">{{$store.state.nickname}}</div>
                    </template>
                </el-collapse-item>
                <el-collapse-item disabled @click.native="$router.push('/Editintroduction')">
                    <template #title>
                        <div style="color: #000000">简介</div>
                        <div v-if="introduction.length<=20" style="position:absolute;right: 35px;color: #000000">
                            {{$store.state.introduction === "null" ? '待完善' : $store.state.introduction}}
                        </div>
                        <div v-else style="position:absolute;right: 35px;color: #000000">
                            {{introduction.substring(0,20)}}...
                        </div>
                    </template>
                </el-collapse-item>
                <el-collapse-item>
                    <template #title>
                        性别
                        <div style="position:absolute;right: 35px">{{sex === "null" ?"待完善":sex}}</div>
                    </template>
                    <el-radio-group v-model="sex" @change="sub">
                        <el-radio :label="'男'">男</el-radio>
                        <el-radio :label="'女'">女</el-radio>
                        <el-radio :label="''">不展示</el-radio>
                    </el-radio-group>
                </el-collapse-item>

                <el-collapse-item>
                    <template #title>
                        生日
                        <div style="position:absolute;right: 35px">{{value1}}</div>
                    </template>
                    <el-date-picker
                            v-model="value1"
                            type="date"
                            placeholder="选择日期"
                            value-format="yyyy-MM-DD">
                    </el-date-picker>
                </el-collapse-item>
            </el-collapse>
        </ul>


    </div>


</template>
<script>
    export default {
        data() {
            return {
                imageUrl: this.$store.state.avatar,
                nickname: this.$store.state.nickname,
                introduction: this.$store.state.introduction,
                sex: this.$store.state.sex,
                fileData: {id: localStorage.getItem("id"),},
                value1: '',
            }
        },
        methods: {
            handleAvatarSuccess(res, file) {
                this.imageUrl = URL.createObjectURL(file.raw);
                this.$store.state.avatar = this.imageUrl;
                localStorage.setItem("avatar", this.imageUrl);
            },
            beforeAvatarUpload(file) {
                // console.log(file.type)
                const isJPEG = file.type === 'image/jpeg';
                const isPNG = file.type === 'image/png';
                const isJPG = file.type === 'image/jpg';
                const isLt1M = file.size / 1024 / 1024 < 1;

                if (!isJPG && !isPNG && !isJPEG) {
                    this.$message.error('上传头像图片只能是JPG或者PNG格式!');
                }
                if (!isLt1M) {
                    this.$message.error('上传头像图片大小不能超过 1MB!');
                }
                return file.type && isLt1M;
            },
            sub() {
                this.$store.state.sex = this.sex;
                localStorage.setItem("sex", this.sex);
                this.$axios.post('http://127.0.0.1:8081/api/user/editSex', {
                    id: localStorage.getItem("id"),
                    sex: this.sex,
                }).then(res => {
                    console.log(res.data);
                }, err => {
                    console.log(err);
                });
            }
        }
    }
</script>
<style lang="less" scoped>
    .app {
        background-color: #ffffff;

        .head {
            position: relative;
            width: 100%;
            height: 0.5rem;
            background-color: #fff;
            border-bottom: #a7a7a7 1px solid;

            .back {
                position: absolute;
                left: 0.1rem;
                top: 50%;
                transform: translateY(-50%);
            }

            .name {
                position: absolute;
                left: 50%;
                top: 50%;
                transform: translateY(-50%) translateX(-50%);
                font-size: 15px;
            }

            .name-schedule {
                position: absolute;
                left: 50%;
                top: 50%;
                transform: translateY(50%) translateX(-50%);
                font-size: 6px;
            }
        }

        .avatar-main {
            position: relative;
            top: 20px;
            background: #1ed475;
            border-radius: 50%;
            width: 100px;
            height: 100px;
            left: 35%;
            text-align: center;

            .avatar {
                overflow: hidden;
                border-radius: 50%;
                width: 100px;
                height: 100px;
            }
        }

        .avatar-hint {
            position: relative;
            top: 30px;
            text-align: center;
        }

        .from {
            position: relative;
            top: 50px;
            width: 100%;
            padding-left: 10px;
            padding-right: 10px;
        }
    }

</style>
