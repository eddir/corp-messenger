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
                method: 'get',
                url: `chats/${id}/messages`
            })
        }
    }
}