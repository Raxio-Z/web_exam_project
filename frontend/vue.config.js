module.exports = {
  transpileDependencies: true
}

module.exports={
  devServer:{
    proxy:{
      'api':{
        target:'http://localhost:9095',
        changeOrigin:true,
        pathRewrite:{
          'api':''
        }
      }
    },
    port:8081
  },
}

