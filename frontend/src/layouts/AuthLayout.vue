<template>
    <div class="layout auth-layout">
        <div class="auth-layout__preview">
            HELLO WORLD!!!
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
                this.$router.push('/chats')
            }
        },
    },

    mounted() {
        this.init()
    }
}
</script>
