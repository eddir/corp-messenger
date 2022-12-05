<template>
  <div>Hello, world! "dream"</div>
</template>

<script>
    export default {
        name: "MainView",

        data() {
            return {
                vehicles: []
            }
        },

        mounted() {
            this.init()
        },

        methods: {
            init() {
                this.$api.echo.test({ data: 'hello men!!!' }).then((res) => {
                    alert('test rest:' + res)
                }).catch((e) => {
                    alert(e)
                })
                this.$api.vehicles.test().then((res) => {
                    this.vehicles = res.data
                }).catch((e) => {
                    alert(e)
                })
            }
        }
    }
</script>
