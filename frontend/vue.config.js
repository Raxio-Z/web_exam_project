module.exports = {
  transpileDependencies: true
}

module.exports={
  devServer:{
    port:8081,
    proxy:{
      'api':{
        target:'http://localhost:8095',
        changeOrigin:true,
        pathRewrite:{
          'api':''
        }
      }
    }
  },

}
