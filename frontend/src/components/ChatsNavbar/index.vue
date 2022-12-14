<template>
    <div class="chats-navbar">
        <div class="chats-navbar__search">
            <InputSearch />
        </div>
        <div class="chats-navbar__sections">
            <ACollapse v-model:activeKey="activeKey">
                <ACollapsePanel key="1" :header="getLabelChannel">
                    <div v-if="!channelList.length" class="chats-navbar__sections__empty">
                        Нет созданных каналов
                    </div>
                    <template #extra>
                        <PlusOutlined @click.native.stop="openModal('editChanelModal')" />
                    </template>
                </ACollapsePanel>
                <ACollapsePanel key="2" :header="getLabelChat">
                    <template #extra>
                        <PlusOutlined @click.native.stop="openModal('editChatModal')" />
                    </template>
                    <div v-if="!chatList.length" class="chats-navbar__sections__empty">
                        Нет созданных чатов
                    </div>
                    <ChatItem v-else v-for="chat in chatList" :key="chat.id" :chat="chat" @click="select(chat.id)" />
                </ACollapsePanel>
            </ACollapse>
        </div>
        <EditChanelModal v-model:visible="editChanelModal" @close-modal="closeModal('editChanelModal')" />
        <EditChatModal v-model:visible="editChatModal" @close-modal="closeModal('editChatModal')" />
    </div>
</template>

<script>
import { PlusOutlined } from '@ant-design/icons-vue'
import { InputSearch, EditChatModal, EditChanelModal, ChatItem } from '@/components'
import { mapActions, mapGetters } from 'vuex'

export default {
    data() {
        return {
            activeKey: ['1', '2'],
            editChatModal: false,
            editChanelModal: false,
            channelList: [],
            chatList: []
        }
    },

    computed: {
        ...mapGetters('ChatsStore', {
            chats: 'chatsList'
        }),

        getLabelChannel() {
            return `Каналы (${this.channelList.length})`
        },

        getLabelChat() {
            return `Чаты (${this.chatList.length})`
        }
    },

    methods: {
        ...mapActions('ChatsStore', [
            'getChats',
            'selectChat'
        ]),

        init() {
            this.getChats()
        },

        openModal(type) {
            this[type] = true
        },

        closeModal(type) {
            this[type] = false
        },

        select(id) {
            this.chatList = this.chatList.map((chat) => ({
                ...chat,
                isSelected: chat.id === id
            }))

            this.selectChat(id)
        }
    },

    mounted() {
        this.init()
    },

    watch: {
        chats(val) {
            if (val) {
                this.chatList = val.map((chat) => ({
                    ...chat,
                    isSelected: false
                }))
            }
        }
    },

    components: {
        InputSearch, PlusOutlined, EditChatModal, EditChanelModal, ChatItem
    }
}
</script>

<style lang="less" scoped>
    .chats-navbar {
        box-sizing: border-box;
        width: 300px;
        border-left: 2px solid #8F8F8F;
        background-color: #1c1c1c;

        &__search {
            padding: 24px;
        }

        &__sections {
            /deep/ .ant-collapse {
                border: none;
                border-radius: 0;
                background-color: #1c1c1c;

                .ant-collapse-item {
                    border: none;

                    &:not(:first-child) {
                        margin-top: 32px;
                    }
                }

                .ant-collapse-header {
                    position: relative;
                    flex-direction: row-reverse;
                    justify-content: start;
                    align-items: center;
                    padding: 4px 24px;
                    font-size: 14px;
                    font-weight: 500;
                    text-transform: uppercase;
                    color: #8F8F8F;

                    & > div {
                        display: flex;
                        justify-content: center;
                        align-items: center;
                        width: 24px;
                        height: 24px;

                        .anticon {
                            margin: 0;
                        }
                    }

                    .ant-collapse-extra {
                        position: absolute;
                        right: 24px;
                        font-size: 18px;
                        border-radius: 2px;

                        &:hover {
                            background-color: #1890FF;
                            color: #FFF;
                        }
                    }
                }

                .ant-collapse-content {
                    background-color: #1c1c1c;
                    border: none;

                    &-box {
                        padding: 0 32px;
                        color: #8F8F8F;
                    }
                }
            }
        }
    }
</style>