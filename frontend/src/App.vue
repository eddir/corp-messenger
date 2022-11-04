<template>
    <component :is="layout">
        <router-view />
    </component>
</template>

<script>
import AuthLayout from "@/layouts/AuthLayout.vue"
import MainLayout from "@/layouts/MainLayout.vue"

export default {
    computed: {
        layout() {
            return `${this.$route.meta.layout || 'auth'}-layout`
        }
    },

    components: {
        AuthLayout, MainLayout
    }
}
</script>
