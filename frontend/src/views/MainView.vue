<template>
    <div>Hello, world!</div>
    <p v-for="vehicle in vehicles" v-text="vehicle"></p>
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
                    console.log('test rest:', res)
                }).catch((e) => {
                    console.error(e)
                })
                this.$api.vehicles.test().then((res) => {
                    this.vehicles = res.data
                }).catch((e) => {
                    console.error(e)
                })
            }
        }
    }
</script>
