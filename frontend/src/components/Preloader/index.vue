<template>
    <div>
        <div class="preloader" v-if="preloader">
            <ASpin :indicator="indicator" />
        </div>
    </div>
</template>

<script>
import { h } from 'vue'
import { LoadingOutlined } from '@ant-design/icons-vue'
import { mapGetters } from 'vuex'

export default {
    data() {
        return {
            
        }
    },

    computed: {
        ...mapGetters('AppStore', [
            'preloader'
        ]),
    },

    methods: {
        indicator() {
            return h(LoadingOutlined, { 
                style: {
                    fontSize: '48px'
                },
                spin: true
            })
        }
    },

    components: {
        LoadingOutlined
    }
}
</script>

<style lang="less" scoped>
    .preloader {
        position: absolute;
        top: 0;
        left: 0;
        width: 100%;
        height: 100%;
        display: flex;
        justify-content: center;
        align-items: center;
        background-color: rgba(0, 0, 0, 0.5);
    }
</style>