import axios from "axios"

const
    requireService = require.context('./services', false, /.service.js$/),
    instance = axios.create({		
        baseURL: '/api',
        responseType: 'json',
        headers: {
            'Content-Type': 'application/json',
            'Cache-Control': 'no-cache',
            'Pragma': 'no-cache'
        }
    })

class Api {
	constructor () {
		this.instance = instance

		// Register local services
		requireService.keys().forEach(filename => requireService(filename).default(this))
	}

	install (Vue) {
		// Vue.prototype.$api = this
        Vue.config.globalProperties.$api = this
	}
}

export default new Api()