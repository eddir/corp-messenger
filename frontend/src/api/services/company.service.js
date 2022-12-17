export default api => {
    api.company = {
        getInfo(id) {
            return api.instance.request({
                method: 'get',
                url: `/companies/${id}`
            })
        }
    }
}