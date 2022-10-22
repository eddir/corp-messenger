export default api => {
    api.echo = {
        test(data) {
            return api.instance.request({
                method: 'post',
                url: 'echo',
                data
            })
        }
    }
}