import api from '@/api'

export const mutation = {
    SET_MEMBERS:  'SET_MEMBERS',
    SET_SELECTED_CHAT:  'SET_SELECTED_CHAT',
	SET_CHATS_LIST: 'SET_CHATS_LIST',
    SAVE_CHAT: 'SAVE_CHAT',
    DELETE_CHAT: 'DELETE_CHAT'
}

export default {
    namespaced: true,

	state: {
        chatsList: null,
        selectedChat: null,
        members: null
	},

 	getters: {
        chatsList: state => {
            return state.chatsList
        },

        selectedChat: state => {
            return state.selectedChat
        },

        members: state => {
            return state.members
        }
	},

	mutations: {
        [mutation.SET_MEMBERS]: (state, payload) => {
            state.members = payload
        },

        [mutation.SET_SELECTED_CHAT]: (state, payload) => {
            state.selectedChat = payload
        },

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
        selectChat: async ({ commit }, id) => {
            await api.chats.find(id)
                .then(({ data }) => {
                    commit(mutation.SET_SELECTED_CHAT, data.chat)
                    commit(mutation.SET_MEMBERS, data.members)
                })
                .catch((e) => console.error(e))
        },

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
