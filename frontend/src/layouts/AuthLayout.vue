<template>
    <div class="layout auth-layout">
        <div class="auth-layout__preview">
            <img src="@/assets/home.png" alt="" />
        </div>
        <div class="auth-layout__content">
            <router-view />
        </div>
    </div>
</template>

<script>
import { mapGetters } from 'vuex'

export default {
    computed: {
        ...mapGetters('AppStore', [
            'isAuth'
        ])
    },

    methods: {
        init() {
            if (this.isAuth) {
                this.$router.push(window.location.pathname)
            }
        },
    },

    mounted() {
        this.init()
    }
}
</script>
