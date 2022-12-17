import axios from "axios"
import { notification } from "ant-design-vue"
import store from '@/store'

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
    requestsArray = []
    
	constructor () {
		this.instance = instance
        
		// Register local services
		requireService.keys().forEach(filename => requireService(filename).default(this))

        this.setInterceptors()
	}

	install (Vue) {
        Vue.config.globalProperties.$api = this
	}

	setPreloader(value) {
        if (store().getters['AppStore/preloader'] !== value) {
            store().dispatch('AppStore/setPreloader', value)
        }
	}

    setInterceptors() {
		this.instance.interceptors.request.use(
			(config) => {
				this.setPreloader(true)

				this.requestsArray.push(config)

				const { access_token } = store().getters['AppStore/getTokens']
				
				if (access_token) {
					config.headers['Authorization'] = 'Bearer '.concat(access_token)
				}

				return config;
			},
			(err) => Promise.reject(err),
		)

		this.instance.interceptors.response.use(
			(config) => {
				setTimeout(() => {
					this.requestsArray = this.requestsArray.slice(1)

					if (!this.requestsArray.length) {
						this.requestsArray = []
						this.setPreloader(false)
					}
				}, 200);

				sessionStorage.clear()

				return config;
			},
			(error) => {
				this.errorHandler(error)
				this.requestsArray = []
				this.setPreloader(false)

				return Promise.reject(error)
			},
		)
	}

    errorHandler({ response }) {
        const message = `${response?.status} ${response?.statusText}`
        const description = sessionStorage.getItem('ERROR_DESCRIPTION')
        sessionStorage.clear()

        notification.error({
            message,
            description
        })
    }
}

export default new Api()