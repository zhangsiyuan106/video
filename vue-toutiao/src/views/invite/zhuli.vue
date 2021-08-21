<template>
    <div>
    <HeaderBar title="好友助力"></HeaderBar>
        <div class="hongbao">
            <el-button type="danger" round @click="zhuli" class="btn-zhu">帮好友助力!!</el-button>
        </div>
    </div>
</template>

<script>
    export default {
        name: "zhuli",
        data() {
            return{
                userid : 0,//被邀请的id
               askid: 1,//请求人的id
            }
        },
        created(){
            // console.log(this.$route.path)
            this.askid = this.$route.path.split("-")[1];
            // console.log(this.askid)
        },
        methods:{
            zhuli:function () {

                this.userid = this.$store.getters.user.id;
                var that = this;
                if(this.askid === 0 || this.userid === 0)
                {
                    layer.open({
                        title:'错误',
                        offset: 'auto',
                        content: '网址错误，请重新输入'
                    });

                }else {

                this.$axios.get('/invite/'+this.askid+'/invitedpage/'+this.userid ).then((res) => {
                        layer.open({
                            title:'助力',
                            offset: 'auto',
                            content: ''+res.data.msg
                        });


                })}
            }
        }

    }
</script>

<style scoped>
    .hongbao{
        width: 100%;
        height: 100%;
        background-image: url("../../assets/images/honngbao2.png") ;
        background-repeat: no-repeat;
        background-size: contain;
        background-position: center;
        position: relative;
    }
    .btn-zhu{
        position: absolute;
        top: 47%;
        left: 50%;
        transform: translateX(-50%);

   }

</style>
