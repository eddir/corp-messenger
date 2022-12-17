import api from '@/api'

export const mutation = {
    SET_MESSAGES: 'SET_MESSAGES',
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
        members: null,
        messages: null
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
        },

        messages: state => {
            return state.messages
        }
	},

	mutations: {
        [mutation.SET_MESSAGES]: (state, payload) => {
            state.messages = payload
        },

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
        getMessages: async ({ commit, state }) => {
            await api.chats.getMessages(state.selectedChat.id)
                .then(({ data }) => {
                    commit(mutation.SET_MESSAGES, data.messages)
                })
                .catch((e) => console.error(e))
        },

        selectChat: async ({ dispatch, commit }, id) => {
            await api.chats.find(id)
                .then(({ data }) => {
                    commit(mutation.SET_SELECTED_CHAT, data)
                    commit(mutation.SET_MEMBERS, data.employees)
                })
                .then(() => {
                    dispatch('getMessages')
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

        addMembers: async ({ dispatch, commit }, { chatId, members }) => {
            let promises = []

            if (Array.isArray(members)) {
                promises = members.map((id) => api.chats.addMembers(chatId, id))
            } else {
                promises = [ api.chats.addMembers(chatId, members) ]
            }

            return Promise.all(promises)
        },

        saveChat: async ({ dispatch, commit }, payload) => {
            await api.chats.createChat(payload.chat)
                .then(({ data }) => {
                    dispatch('addMembers', { chatId: data.id, members: payload.members })
                        .then(() => dispatch('getChats'))
                })
                .catch((e) => console.error(e))
        }, 

        deleteChat: ({ commit }, payload) => {
            commit(mutation.DELETE_CHAT, payload)
        }
	}
}
