module.exports = {
    //项目出口,webpack-dev-server 生成的包并没有写入硬盘,而是放在内存中！
    dev: {
        env: 'development',
        publicPath: '/',
        host: 'localhost',
        port: '8080',
        assetsSubDirectory: 'static',
        devtoolType: 'cheap-module-eval-source-map',
        proxyTable: {
            '/api': {
                target: 'http://localhost:8081/',  //这里后台的地址模拟的;应该填写你们真实的后台接口
                changOrigin: true,  //允许跨域
                pathRewrite: {
                    /* 重写路径，当我们在浏览器中看到请求的地址为：http://localhost:8080/api/core/getData/userInfo 时
                      实际上访问的地址是：http://121.121.67.254:8185/core/getData/userInfo,因为重写了 /api
                     */
                    '^/api': ''
                }
            },
        }
    },
    build: {
        env: 'production',
        publicPath: './',
        assetsPath: 'static',
        assetsSubDirectory: 'static',
        devtoolType: 'source-map',
        productionGzip: true,
        productionGzipExtensions: ['js', 'css']
    }
}
