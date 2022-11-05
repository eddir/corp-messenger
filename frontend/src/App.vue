<template>
    <component :is="layout">
        <router-view />
    </component>
</template>

<script>
import { mapActions } from 'vuex'

import AuthLayout from "@/layouts/AuthLayout.vue"
import MainLayout from "@/layouts/MainLayout.vue"

export default {
    computed: {
        layout() {
            return `${this.$route.meta.layout || 'auth'}-layout`
        }
    },

    methods: {
        ...mapActions('AppStore', [
            'authorize',
            'unauthorize'
        ]),

        init() {
            const isAuth = localStorage.getItem('IS_AUTH')

            if (isAuth)
                return this.authorize()
            else 
                return this.unauthorize()
        }
    },

    mounted() {
        this.init()
    },

    components: {
        AuthLayout, MainLayout
    }
}
</script>
