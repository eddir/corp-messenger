const { defineConfig } = require("@vue/cli-service");
// const path = require('path')

module.exports = defineConfig({
    transpileDependencies: true,

    pwa: {
        name: 'corp_mes'
    },
    
    devServer: {
		proxy: {
			'/api': {
                target: 'http://messenger.rostkov.me',
                pathRewriten: {
                    '^/api': '/api',
                },
                changeOrigin: true,
                secure: false
            }
			
		},
		host: 'localhost',
		port: 8080
    },

    // module: {
    //     rules: [
    //         {
    //             test: /\.svg$/,
    //             include: [
    //                 path.resolve(__dirname, './src/assets/icons/'),
    //             ],
    //             use: [
    //                 {
    //                     loader: 'svg-sprite-loader',
    //                     options: {}
    //                 }
    //             ]
    //         }
    //     ]
    // }
});
