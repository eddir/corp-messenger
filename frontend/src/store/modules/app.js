export const mutation = {
	SET_AUTHORIZATION: 'SET_AUTHORIZATION',
}

export default {
    namespaced: true,

	state: {
        isAuth: false
	},

 	getters: {
        isAuth: state => {
            return state.isAuth
        }
	},

	mutations: {
		[mutation.SET_AUTHORIZATION]: (state, payload) => {
            localStorage.setItem('IS_AUTH', payload);
            return state.isAuth = payload
        }
	},

	actions: {
		authorize: ({ commit }) => {
            commit(mutation.SET_AUTHORIZATION, true)
        },
		unauthorize: ({ commit }) => {
            commit(mutation.SET_AUTHORIZATION, false)
        }
	}
}
