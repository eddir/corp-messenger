import api from '@/api'
import { parseJWT } from '@/utils/helpers'

export const mutation = {
	SET_AUTHORIZATION: 'SET_AUTHORIZATION',
}

export default {
    namespaced: true,

	state: {
        isAuth: false,
        userLogin: null,
        access_token: null,
        refresh_token: null,
        checkAuthInterval: 60,
        offsetExpDate: 60
	},

 	getters: {
        isAuth: state => {
            return state.isAuth
        },

        userLogin: state => {
            return state.userLogin
        },

        getTokens: state => {
            return [ state.access_token, state.refresh_token ]
        }
	},

	mutations: {
		[mutation.SET_AUTHORIZATION]: (state, payload) => {
            let isAuth = false

            if (payload) {
                isAuth = true
                state.isAuth = isAuth
                state.access_token = payload.accessToken
                state.refresh_token = payload.refreshToken
                localStorage.setItem('TOKENS', JSON.stringify(payload))

                state.userLogin = parseJWT(payload.accessToken).sub
            } else {
                state.access_token = null
                state.refresh_token = null
                localStorage.clear('TOKENS')
            }
            
            localStorage.setItem('IS_AUTH', isAuth);
            state.isAuth = isAuth
        }
	},

	actions: {
		authorize: ({ dispatch, commit }, payload) => {
            commit(mutation.SET_AUTHORIZATION, payload)

            dispatch('startTokenTracking')
        },

		unauthorize: ({ dispatch, commit }) => {
            commit(mutation.SET_AUTHORIZATION, null)

            dispatch('stopTokenTracking')
        },

        startTokenTracking: ({ dispatch, state }) => {
            clearInterval()

            if (state.isAuth) {
                setInterval(() => dispatch('checkToken'), state.checkAuthInterval * 1000)
            }
        },
        
        stopTokenTracking: () => {
            clearInterval()
        },

        checkToken: ({ commit, state }) => {
            const jwtConfig = parseJWT(state.access_token),
                expDate = jwtConfig.exp,
                currDate = Date.now() / 1000

            if (expDate < currDate || expDate - currDate < state.offsetExpDate) {
                api.app.renew({ accessToken: state.access_token, refreshToken: state.refresh_token })
                    .then(({ data }) => {
                        commit(mutation.SET_AUTHORIZATION, data)
                    })
                    .catch((e) => console.error(e))
            }
        }
	}
}