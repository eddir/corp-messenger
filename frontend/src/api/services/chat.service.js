export default api => {
    api.chats = {
        getAll() {
            return api.instance.request({
                method: 'get',
                url: 'chats'
            })
        },
        
        createChat(data) {
            return api.instance.request({
                method: 'post',
                url: 'chats',
                data
            })
        },

        find(id) {
            return api.instance.request({
                method: 'get',
                url: `chats/${id}`
            })
        },

        getMessages(id) {
            return api.instance.request({
                method: 'post',
                url: `chats/${id}/messages`,
                data: {
                    chatId: id,
                    count: 100,
                    startMessageId: null
                }
            })
        },

        send(data) {
            return api.instance.request({
                method: 'post',
                url: `chats/${data.chatId}/messages/send`,
                data,
            })
        },
        
        addMembers(chatId, memberId) {
            return api.instance.request({
                method: 'post',
                url: `chats/${chatId}/member/${memberId}`
            })
        }
    }
}