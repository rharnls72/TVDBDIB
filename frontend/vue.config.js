module.exports = { 
    configureWebpack: { 
        // other webpack options to merge in ...
        entry: ["babel-polyfill", "./src/main.js"]
    }, 
    // devServer Options don't belong into `configureWebpack` 
    devServer: {
        host: '0.0.0.0',
        hot: true,
        disableHostCheck: true,
    }
};