export default api => {
    api.vehicles = {
        test(data) {
            return api.instance.request({
                method: 'get',
                url: 'vehicle',
                data
            })
        }
    }
}