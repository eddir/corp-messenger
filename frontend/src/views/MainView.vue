<template>
    <div>Hello, world!</div>
</template>

<script>
    export default {
        name: "MainView",
        
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
            }
        }
    }
</script>
