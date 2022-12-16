<template>
    <div class="chat-view">
        <div class="chat-view__title">
            <img v-if="chat.img_url" class="chat-view__title__icon" :src="chat.img_url" alt="Картинка чата" />
            <div class="chat-view__title__label">{{ chat.name }}</div>
            <div class="chat-view__title__members">
                <TeamOutlined :style="{ fontSize: '16px' }" />
                {{ members?.length }}
            </div>
            <InputSearch class="chat-view__title__search" />
            <AButton type="text" class="chat-view__title__more"><MoreOutlined /></AButton>
        </div>

        <div class="chat-view__messages">
            
        </div>

        <ASpace class="chat-view__sender">
            <div class="chat-view__sender__emoji">
                <SmileOutlined />
            </div>
            <div class="chat-view__sender__attach">
                <PaperClipOutlined />
            </div>
            <AInput class="chat-view__sender__input" :placeholder="`Написать в ${chat.name}...`" />
            <div class="chat-view__sender__send">
                <SendOutlined />
            </div>
        </ASpace>
    </div>
</template>

<script>
import { TeamOutlined, MoreOutlined, SmileOutlined, PaperClipOutlined, SendOutlined } from '@ant-design/icons-vue'
import { mapActions, mapGetters } from 'vuex'

import { InputSearch } from '@/components'

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
        ...mapGetters('ChatsStore', [
            'members'
        ])
    },

    methods: {
        init() {
            
        }
    },

    mounted() {
        this.init()
    },

    components: {
        InputSearch, TeamOutlined, MoreOutlined, SmileOutlined, PaperClipOutlined, SendOutlined
    }
}
</script>

<style lang="less" scoped>
    .chat-view {
        position: relative;
        width: 100%;
        height: 100%;

        &__title {
            display: flex;
            align-items: center;
            width: 100%;
            height: 80px;
            padding: 0 32px;
            border-bottom: 1px solid #D9D9D9;

            &__icon {
                width: 32px;
                height: 32px;
                border-radius: 2px;
            }

            &__label {
                font-size: 18px;
                font-weight: bold;
                margin-left: 16px;
            }

            &__members {
                margin-left: auto;
                font-size: 16px;
                color: #8F8F8F;
            }

            &__search {
                margin-left: 32px;
                width: 252px;
            }

            &__more {
                display: flex;
                align-items: center;
                justify-content: center;
                width: 32px;
                height: 32px;
                padding: 0;
                font-size: 24px;
                color: #8F8F8F;
                margin-left: 32px;

                &:hover {
                    background-color: #D9D9D9;
                }
            }
        }

        &__sender {
            position: absolute;
            bottom: 0;
            width: 100%;
            border-top: 1px solid #D9D9D9;
            height: 60px;
            padding: 0 24px;

            &__emoji,
            &__attach,
            &__send {
                font-size: 20px;
                color: #8F8F8F;
            }

            &__input {
                border: none;
            }
            
            /deep/ .ant-space-item:nth-child(3) {
                width: 100%;
            }

            &__send {
                color: #1890FF;
                cursor: pointer;
            }
        }
    }
</style>