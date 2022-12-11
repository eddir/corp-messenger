export default api => {
    api.user = {
        getById(id) {
            return api.instance.request({
                method: 'get',
                url: `/users/${id}`
            })
        }
    }
}