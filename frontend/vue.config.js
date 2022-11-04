const { defineConfig } = require("@vue/cli-service");

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
    }
});
