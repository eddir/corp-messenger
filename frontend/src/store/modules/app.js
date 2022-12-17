import api from '@/api'
import parseJWT from '@/helpers/parseJWT'

export const mutation = {
    SET_BLOCK_PRELOADER: 'SET_BLOCK_PRELOADER',
    SET_PRELOADER: 'SET_PRELOADER',
	SET_AUTHORIZATION: 'SET_AUTHORIZATION',
    SET_USER: 'SET_USER'
}

export default {
    namespaced: true,

	state: {
        preloader: false,
        isBlockedPreloader: false,
        isAuth: false,
        user: null,
        access_token: null,
        refresh_token: null,
        checkAuthInterval: 60,
        offsetExpDate: 60
	},

 	getters: {
        isAuth: state => {
            return state.isAuth
        },

        user: state => {
            return state.user
        },

        getTokens: state => {
            return { access_token: state.access_token, refresh_token: state.refresh_token }
        },

        preloader: state => {
            return state.preloader
        }
	},

	mutations: {
		[mutation.SET_BLOCK_PRELOADER]: (state, payload) => {
            state.isBlockedPreloader = payload
        },

		[mutation.SET_PRELOADER]: (state, payload) => {
            if (!state.isBlockedPreloader)
                state.preloader = payload
        },

		[mutation.SET_AUTHORIZATION]: (state, payload) => {
            let isAuth = false

            if (payload) {
                isAuth = true
                state.isAuth = isAuth
                state.access_token = payload.accessToken
                state.refresh_token = payload.refreshToken
                localStorage.setItem('TOKENS', JSON.stringify(payload))
            } else {
                state.access_token = null
                state.refresh_token = null
                localStorage.clear('TOKENS')
            }
            
            localStorage.setItem('IS_AUTH', isAuth);
            state.isAuth = isAuth
        },

        [mutation.SET_USER]: (state, payload) => {
            if (payload) {
                api.user.getById(payload)
                    .then(({ data }) => {
                        state.user = data
                    })
                    .catch((e) => console.error(e))
            }
        }
	},

	actions: {
        setBlockPreloader: ({ commit }, payload) => {
            commit(mutation.SET_BLOCK_PRELOADER, payload)
        },

        setPreloader: ({ commit }, payload) => {
            commit(mutation.SET_PRELOADER, payload)
        },

		authorize: ({ dispatch, commit }, payload) => {
            commit(mutation.SET_AUTHORIZATION, payload)
            commit(mutation.SET_USER, payload.user_id)

            dispatch('startTokenTracking')
        },

		unauthorize: ({ dispatch, commit }) => {
            commit(mutation.SET_AUTHORIZATION, null)

            dispatch('stopTokenTracking')
        },

        startTokenTracking: ({ dispatch, state }) => {
            clearInterval()

            if (state.isAuth && state.access_token) {
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
                        commit(mutation.SET_USER, data.user_id)
                    })
                    .catch((e) => console.error(e))
            }
        }
	}
}
