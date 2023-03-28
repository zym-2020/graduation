const { resolve } = require("path");
module.exports = {
  publicPath: "/",
  devServer: {
    host: "0.0.0.0",
    port: 7777,
    proxy: {
      "/nodeManage": {
        target: "http://localhost:8888/",
        changeOrigin: true,
        pathRewrite: {
          "^/nodeManage": "",
        },
      },
    },
  },

  configureWebpack: {
    resolve: {
      alias: {
        "@": resolve("src"),
      },
    },
  },
};
