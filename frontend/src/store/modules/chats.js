import api from '@/api'

export const mutation = {
	SET_CHATS_LIST: 'SET_CHATS_LIST',
    SAVE_CHAT: 'SAVE_CHAT',
    DELETE_CHAT: 'DELETE_CHAT'
}

export default {
    namespaced: true,

	state: {
        chatsList: null
	},

 	getters: {
        getChatsList: state => {
            return state.chatsList
        }
	},

	mutations: {
		[mutation.SET_CHATS_LIST]: (state, payload) => {
            state.chatsList = payload
        },

        [mutation.SAVE_CHAT]: ({ commit }, state, payload) => {
            const newChat = { id: state.chatsList.length, ...payload }
            const newList = [ ...state.chatsList, newChat ]

            commit(mutation.SET_CHATS_LIST, newList)
        },

        [mutation.DELETE_CHAT]: ({ commit }, state, payload) => {
            const newlist = state.chatsList.filter((c) => c.id !== payload)

            commit(mutation.SET_CHATS_LIST, newlist)
        }
	},

	actions: {
        getChats: async ({ commit }) => {
            await api.chats.getAll()
                .then(({ data }) => {
                    commit(mutation.SET_CHATS_LIST, data)
                })
                .catch((e) => console.error(e))
        },

        saveChat: ({ commit }, payload) => {
            commit(mutation.SAVE_CHAT, payload)
        }, 

        deleteChat: ({ commit }, payload) => {
            commit(mutation.DELETE_CHAT, payload)
        }
	}
}
