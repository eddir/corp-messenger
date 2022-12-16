export default api => {
    api.chats = {
        getAll() {
            return api.instance.request({
                method: 'get',
                baseURL: 'https://stoplight.io/mocks/corp-messenger/corp-messenger/104831872/',
                url: 'chats',
            })
        },

        find(id) {
            return api.instance.request({
                method: 'get',
                baseURL: 'https://stoplight.io/mocks/corp-messenger/corp-messenger/104831872/',
                url: `chat/${id}`,
            })
        },

        getMessages(id) {
            return api.instance.request({
                method: 'get',
                baseURL: 'https://stoplight.io/mocks/corp-messenger/corp-messenger/104831872/',
                url: `chat/${id}/messages`,
            })
        }
    }
}