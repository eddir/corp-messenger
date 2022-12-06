<template>
    <div class="navbar">
        <AMenu class="navbar__menu" theme="dark" v-model:selectedKeys="selectedKeys" selectable mode="inline" :inline-collapsed="collapsed">
            <AMenuItem key="profile" class="navbar__menu__item" @click="goTo('/profile')">
                <template :v-show="collapsed" #icon>
                    <div class="avatar"><UserOutlined /></div>
                </template>
                <span class="username">{{ login }}</span>
            </AMenuItem>
            <AMenuItem key="chats" class="navbar__menu__item" @click="goTo('/chats')">
                <template #icon><WechatOutlined /></template>
                <span>Чаты</span>
            </AMenuItem>
            <AMenuItem key="contacts" class="navbar__menu__item" @click="goTo('/contacts')">
                <template #icon><PhoneOutlined /></template>
                <span>Контакты</span>
            </AMenuItem>
            <AMenuItem class="navbar__menu__item" @click.native.stop="exit()">
                <template #icon><ExportOutlined /></template>
                <span>Выход</span>
            </AMenuItem>
        </AMenu>
        <ChatsNavbar v-show="viewChatsNavbar" />
    </div>
</template>

<script>
import { mapActions, mapGetters } from 'vuex'
import { UserOutlined, WechatOutlined, PhoneOutlined, ExportOutlined } from '@ant-design/icons-vue'

import { ChatsNavbar } from '@/components'

export default {
    data() {
        return {
            selectedKeys: [ 'chats' ],
            collapsed: true
        }
    },

    computed: {
        ...mapGetters('AppStore', [
            'userLogin'
        ]),

        viewChatsNavbar() {
            return this.selectedKeys.includes('chats')
        },
        
        login() {
            return this.userLogin || 'user'
        }
    },

    methods: {
        ...mapActions('AppStore', [
            'unauthorize'
        ]),

        init() {
            const path = window.location.pathname.split('/')[1]
            this.selectedKeys = [ path ]
        },

        exit() {
            //TODO: выход из системы
            this.goTo('/login')
        },

        goTo(path) {
            if (path === '/login') {
                this.unauthorize()
            }

            this.$router.push(path)
        }
    },

    mounted() {
        this.init()
    },

    watch: {
        selectedKeys(val) {
            if (val.includes('chats')) {
                this.collapsed = true
            } else {
                this.collapsed = false
            }
        }
    },

    components: {
        UserOutlined, WechatOutlined, PhoneOutlined, ExportOutlined, ChatsNavbar
    }
}
</script>

<style lang="less" scoped>
    .navbar {
        height: 100vh;
        display: flex;

        &__menu {
            display: flex;
            flex-direction: column;
            height: 100%;

            &:not(.ant-menu-inline-collapsed) {
                width: 300px;
            }

            /deep/ &.ant-menu {
                background-color: #1c1c1c;
            }

            /deep/ &.ant-menu-inline-collapsed > .ant-menu-item {
                padding: 0 0 0 28px;

                &:first-child {
                    height: 80px;

                    .avatar {
                        width: 50px;
                        height: 50px;
                        top: 15px;
                        left: 15px;
                    
                        .anticon {
                            width: 50px;
                            height: 50px;
                            font-size: 24px;
                        }
                    }
                }

                .anticon {
                    font-size: 24px;
                }
            }

            /deep/ &__item.ant-menu-item {
                display: flex;
                align-items: center;
                height: 60px;
                margin: 0;
                padding: 0;
                font-size: 14px;
                font-weight: 500;

                &:not(:first-child) {
                    text-transform: uppercase;
                    
                    .ant-menu-title-content {
                        margin-left: 24px;    
                    }
                }

                &:not(:last-child) {
                    margin-bottom: 0;
                }

                &:first-child {
                    position: relative;
                    display: flex;
                    justify-content: center;
                    align-items: center;
                    height: 200px;

                    .avatar {
                        width: 100px;
                        height: 100px;
                        top: 24px;
                        left: calc(50% - 50px);
                    }

                    .anticon {
                        width: 100px;
                        height: 100px;
                        display: flex;
                        justify-content: center;
                        align-items: center;
                        font-size: 50px;
                        color: #D9D9D9;
                    }

                    .ant-menu-title-content {
                        margin-top: 100px;
                        font-size: 18px;
                        text-align: center;
                        margin-left: -24px;
                        width: 100%;
                    }
                }

                &:last-child {
                    margin-top: auto;
                    color: red;
                }

                .anticon {
                    font-size: 24px;
                }
            }
        }

        .avatar {
            position: absolute;
            left: 15px;
            top: 15px;
            width: 50px;
            height: 50px;
            border-radius: 50%;
            background-color: #FFF;
        }
    }
</style>