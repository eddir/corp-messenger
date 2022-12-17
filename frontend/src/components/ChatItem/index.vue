<template>
    <div :class="classnames">
        <div class="chat-item__icon">
            <img v-if="chat.img_url" :src="chat.img_url" alt="Иконка чата" />
            <TeamOutlined v-else-if="chat?.type === 'GROUP'" />
            <UserOutlined v-else />
        </div>
        <div class="chat-item__name">
            {{ chat.name }}
        </div>
    </div>
</template>

<script>
import { TeamOutlined, UserOutlined } from '@ant-design/icons-vue'

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
        TeamOutlined, UserOutlined
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
            width: 32px;
            height: 32px;
            background-color: #FFF;
            border-radius: 2px;
            color: #D9D9D9;
            display: flex;
            justify-content: center;
            align-items: center;
            font-size: 18px;

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