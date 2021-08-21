<template>
  <div style="height: 100%" ref="echart">
    myEchart
  </div>
</template>

<script>
import * as echarts from 'echarts';
export default {
  //数据
  props: {
    chartData: {
      type: Object,
      default(){
        return{
          xData:[], //x轴数据
          series:[{

          }],
        }
      }
    },
    isAxisChart: { //是否是有坐标轴的图表
      type: Boolean,
      default: true
    }
  },
  computed: {
    options(){
      return this.isAxisChart? this.axisOption: this.normalOption
    }
  },
  data() {
    return {
      myChart: null,
      axisOption:{
        title:{
          text:"",

        },
        legend: {
          data: []
        },
        tooltip: {
          trigger: ''
        },
        xAxis:
          {
            type: 'category', //类目轴

            data: [],
            axisLine:{
              lineStyle:{
                color:'#17b3a3'
              }
            },
            axisLabel:{
              color:'#333'
            }
          },
        yAxis:
          {
            type: 'value',//值
            axisLine:{
              lineStyle:{
                color:'#244b45'
              }
            }
          },
        series:[]  //存储值容器
      },// 有坐标轴
      normalOption:{
        title:{
          text:"",
          subtext:"",
          left:""
        },
        tooltip: {
          trigger: ''
        },
        legend: {
          orient:"",
          left:"",
        },
        series:[]
      } //无坐标轴
    }
  },
  watch:{
    chartData:{
      handler:function () {
        this.initChart()
      },
      deep:true
    }
  },
  mounted() {
    this.initChart();
    //浏览器窗口大小变化 使得图表自适应
    window.addEventListener('resize',this.resizeChart); //注册事件

  },
  destroyed() {
    window.removeEventListener("resize",this.resizeChart);//销毁
  },
  methods: {

    resizeChart(){
      this.myChart?this.myChart.resize():"";
    },
    //初始化图表
    initChart() {
      this.initChartData();//渲染数据
      if (this.myChart){ //如果图表存在直接渲染
        this.myChart.setOption(this.options);
      }else {
          this.myChart = echarts.init(this.$refs.echart);
          this.myChart.setOption(this.options);
      }
    },
    //数据处理
    initChartData(){
      if (this.isAxisChart){
       this.axisOption.xAxis.data=this.chartData.xData
        this.axisOption.series = this.chartData.series;
       this.axisOption.title.text=this.chartData.title;
       this.axisOption.legend.data = this.chartData.legend;
       this.axisOption.tooltip.trigger = this.chartData.trigger;
      }else {
       this.normalOption.title.text=this.chartData.title;
       this.normalOption.title.left=this.chartData.left;
       this.normalOption.legend.orient=this.chartData.legendOrient;
       this.normalOption.legend.left=this.chartData.legendLeft;
       this.normalOption.tooltip.trigger=this.chartData.trigger;
        this.normalOption.series = this.chartData.series;
      }
    },
  }
}
</script>
