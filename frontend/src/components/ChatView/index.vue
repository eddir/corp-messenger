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
            <AComment v-for="message in messages" :key="message.id" class="chat-view__messages__message">
                <template #author>{{ getUsername(message.user_id) }}</template>
                <template #avatar>
                    <img v-if="getUser(message.user_id).img_url" :src="getUser(message.user_id).img_url" alt="Автарка пользователя" />
                    <UserOutlined v-else />
                </template>
                <template #content>
                    {{ message.text }}
                </template>
                <template #datetime>
                    <ATooltip :title="getDate('auto', message.created_at)">
                        <span>{{ getDate('fromNow', message.created_at) }}</span>
                    </ATooltip>
                </template>
            </AComment>
        </div>

        <ASpace class="chat-view__sender" :size="8">
            <div class="chat-view__sender__emoji">
                <SmileOutlined />
            </div>
            <div class="chat-view__sender__attach">
                <PaperClipOutlined />
            </div>
            <AInput class="chat-view__sender__input" v-model:value="model.text" :placeholder="`Написать в ${chat.name}...`" />
            <AButton type="text" class="chat-view__sender__send" @click="save()">
                <SendOutlined />
            </AButton>
        </ASpace>
    </div>
</template>

<script>
import { TeamOutlined, MoreOutlined, SmileOutlined, PaperClipOutlined, SendOutlined, UserOutlined } from '@ant-design/icons-vue'
import { mapActions, mapGetters } from 'vuex'
import moment from "moment/moment";

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
            model: {
                chat_id: this.chat.id,
                text: null,
                isPinned: false
            }
        }
    },

    computed: {
        ...mapGetters('ChatsStore', [
            'members',
            'messages'
        ]),
        ...mapGetters('AppStore', [
            'user'
        ])
    },

    methods: {
        ...mapActions('AppStore', [
            'setBlockPreloader'
        ]),

        ...mapActions('ChatsStore', [
            'send'
        ]),

        save() {
            this.setBlockPreloader(true)
            this.send(this.model).then(() => this.setBlockPreloader(true))
            this.model.text = null
        },

        getUsername(id) {
            const { last_name, first_name, login } = this.getUser(id)

            if (first_name && last_name) {
                return `${user.last_name} ${user.first_name}`
            }

            return login
        },

        getDate(type, date) {
            if (type === 'fromNow')
                return moment(date).fromNow()
            else return moment(date).format('HH:mm DD.MM.YYYY')
        },

        getUser(id) {
            return this.members.find((x) => x.id === id)
        }
    },

    components: {
        InputSearch, TeamOutlined, MoreOutlined, SmileOutlined, PaperClipOutlined, SendOutlined, UserOutlined
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
                max-width: 252px;
                width: 100%;
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

        &__messages {
            overflow: auto;
            width: 100%;
            height: calc(100% - 140px);
            padding: 24px 32px;

            /deep/ .ant-comment-content-author-name {
                font-size: 14px;
                color: #000;
                font-weight: 500;
            }

            /deep/ .ant-comment-avatar {
                .anticon {
                    width: 32px;
                    height: 32px;
                    display: flex;
                    justify-content: center;
                    align-items: center;
                    border-radius: 50%;
                    border: 1px solid #D9D9D9;
                    color: #D9D9D9;
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
                padding: 0;
                color: #1890FF;
                cursor: pointer;
            }
        }
    }
</style>