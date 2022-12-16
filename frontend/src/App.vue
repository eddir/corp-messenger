<template>
    <div>
        <component :is="layout">
            <router-view />
        </component>
        <Preloader />
    </div>
</template>

<script>
import { mapActions } from 'vuex'

import AuthLayout from "@/layouts/AuthLayout.vue"
import MainLayout from "@/layouts/MainLayout.vue"

import { Preloader } from '@/components'

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
            const isAuth = JSON.parse(localStorage.getItem('IS_AUTH'))
            const tokens = JSON.parse(localStorage.getItem('TOKENS'))

            if (isAuth && tokens)
                return this.authorize(tokens)
            else 
                return this.unauthorize()
        }
    },

    created() {
        this.init()
    },

    components: {
        AuthLayout, MainLayout, Preloader
    }
}
</script>
