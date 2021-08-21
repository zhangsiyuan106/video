// 引入
import dayjs from 'dayjs'
import Vue from 'vue'

// 加载中文语言包
import 'dayjs/locale/zh-cn'

import relativeTime from 'dayjs/plugin/relativeTime'

// 配置使用处理相对时间的插件
dayjs.extend(relativeTime)

// 配置使用中文语言包
dayjs.locale('zh-cn')

// 定义一个全局过滤：处理相对时间
// 仅供模板使用
// 参数一：过滤器名称
// 参数二：过滤器函数
// 使用方式：{ 表达式 | 过滤器名称 }
Vue.filter('relativeTime', value => {
  return dayjs().to(dayjs(value))
})

// console.log(dayjs().format('YYYY-MM-DD'))
