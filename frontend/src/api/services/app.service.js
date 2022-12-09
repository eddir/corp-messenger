export default api => {
    api.app = {
        login(data) {
            return api.instance.request({
                method: 'post',
                url: 'auth/login',
                data
            })
        },

        renew(data) {
            return api.instance.request({
                method: 'post',
                url: 'auth/renew',
                data
            })
        }
    }
}