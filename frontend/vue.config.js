module.exports = {
  configureWebpack: {

    performance: {
      hints: false
    },
  },

  "transpileDependencies": [
    "vuetify"
  ],
  // assetDir: 'assets/',
  // publicPath: '/',
  // css: {
  //   sourceMap: true,
  //   loaderOptions: {
  //     sass: {
  //       data: `@import "@/styles/main.scss";`
  //     }
  //   }
  // },
  // chainWebpack: config => {
  //   config
  //     .module
  //     .rule("file")
  //     .test(/\.(woff2?|eot|ttf|otf)(\?.*)?$/,)
  //     .use("url-loader")
  //     .loader("url-loader")
  //     .options({
  //       limit: 10000,
  //       name: 'assets/fonts/[name].[ext]'
  //     })
  //     .end();
  // }
}