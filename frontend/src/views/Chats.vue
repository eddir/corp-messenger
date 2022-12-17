<template>
    <div class="chat-layout">
        <ChatView v-if="selectedChat" :chat="selectedChat" />
        <div v-else class="chat-layout__empty">
            <div class="chat-layout__empty-icon">
                <InfoCircleOutlined />
            </div>
            <div class="chat-layout__empty-text">
                Выберите чат или канал
            </div>
        </div>
    </div>
</template>

<script>
import { mapGetters } from 'vuex'
import Pusher from 'pusher-js'
import { InfoCircleOutlined } from '@ant-design/icons-vue'

import { ChatView } from '@/components'

export default {
    data() {
        
    },

    computed: {
        ...mapGetters('ChatsStore', [
            'selectedChat'
        ]),
        ...mapGetters('AppStore', [
            'user'
        ])
    },

    methods: {
        init() {
            this.subscribe()
        },

        subscribe() {
                let pusher = new Pusher(1435118, { cluster: 'eu' })
                pusher.subscribe('messages-' + this.user.id)
                pusher.bind('message_added', data => {
                    console.log(data)
                })
            
        }
    },

    mounted() {
        this.init()
    },

    components: {
        InfoCircleOutlined, ChatView
    }
}
</script>

<style lang="less" scoped>
    .chat-layout {
        width: 100%;
        height: 100%;
        display: flex;
        align-items: center;
        justify-content: center;
        
        &__empty {
            &-icon {
                display: flex;
                justify-content: center;
                align-items: center;
                margin-bottom: 24px;
                font-size: 80px;
                color: #8F8F8F;
            }

            &-text {
                font-size: 18px;
                font-weight: 500;
                color: #8F8F8F;
            }
        }
    }
</style>
