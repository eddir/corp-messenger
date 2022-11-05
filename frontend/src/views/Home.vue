<template>
    <div>
        <AButton @click="goTo('/login')" type="primary">Выйти</AButton>
    </div>
</template>

<script>
export default {
    data() {
        return {
            
        }
    },

    mounted() {
        this.init()
    },

    methods: {
        init() {
            
        },

        goTo(path) {
            this.$router.push(path)
        }
    }
}
</script>
