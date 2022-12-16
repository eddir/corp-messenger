<template>
    <div :class="classnames">
        <div class="chat-item__icon">
            <img :src="chat.img_url" alt="Иконка чата" />
        </div>
        <div class="chat-item__name">
            {{ chat.name }}
        </div>
    </div>
</template>

<script>
// import { PlusOutlined } from '@ant-design/icons-vue'
// import { InputSearch, EditChatModal, EditChanelModal } from '@/components'
// import { mapActions, mapGetters } from 'vuex'

export default {
    props: {
        chat: {
            type: Object,
            required: true
        }
    },

    data() {
        return {
            
        }
    },

    computed: {
        classnames() {
            return `chat-item ${this.chat.isSelected ? 'selected' : ''}`
        }
    },

    methods: {
        
    },

    mounted() {
        
    },

    components: {
        
    }
}
</script>

<style lang="less" scoped>
    .chat-item {
        display: flex;
        align-items: center;
        margin: 0 -32px;
        margin-top: 16px;
        padding: 4px 32px;
        cursor: pointer;

        &.selected {
            background-color: #1890FF;
            color: #FFF;
        }

        &__icon {
            img {
                width: 32px;
                height: 32px;
                border-radius: 2px;
            }
        }

        &__name {
            font-size: 14px;
            font-weight: 500;
            margin-left: 8px;
        }
    }
</style>