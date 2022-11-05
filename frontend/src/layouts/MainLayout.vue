<template>
    <div class="layout main-layout">
        <Navbar />
        <div class="main-layout__content">
            <router-view />
        </div>
    </div>
</template>

<script>
import { mapGetters } from 'vuex'

import { Navbar } from '@/components'

export default {
    computed: {
        ...mapGetters('AppStore', [
            'isAuth'
        ])
    },

    mounted() {
        if (!this.isAuth) {
            this.$router.push('/login')
        }
    },

    watch: {
        isAuth (newVal) {
            if (!newVal) {
                this.$router.push('/login')
            }
        }
    },

    components: {
        Navbar
    }
}
</script>