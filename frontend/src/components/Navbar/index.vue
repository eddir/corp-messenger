<template>
    <div class="navbar">
        <div class="navbar__title">
            <ADropdown class="navbar__title__menu" :trigger="['click']" placement="bottomLeft">
                <AButton class="navbar__title__menu-trigger" type="link" @click.prevent>{{ title() }} <RightOutlined class="navbar__title__menu-trigger-icon" /></AButton>
                <template #overlay>
                    <AMenu class="navbar__title__menu-dropdown" theme="dark" v-model:selectedKeys="selectedKeys" selectable>
                        <AMenuItem key="profile" class="navbar__title__menu-dropdown-item" @click="goTo('/profile')">Профиль</AMenuItem>
                        <AMenuItem key="chats" class="navbar__title__menu-dropdown-item" @click="goTo('/chats')">Чаты</AMenuItem>
                        <AMenuItem key="contacts" class="navbar__title__menu-dropdown-item" @click="goTo('/contacts')">Контакты</AMenuItem>
                    </AMenu>
                </template>
            </ADropdown>
            <ADropdown class="navbar__title__settings" :trigger="['click']" placement="bottomLeft">
                <AButton class="navbar__title__settings-trigger" type="link" @click.prevent><SettingOutlined class="navbar__title__settings-trigger-icon" /></AButton>
                <template #overlay>
                    <AMenu class="navbar__title__settings-dropdown" theme="dark">
                        <AMenuItem class="navbar__title__settings-dropdown-item" @click="goTo('/settings')">Настройки</AMenuItem>
                        <AMenuItem class="navbar__title__settings-dropdown-item" @click="goTo('/login')"><AButton class="exit" type="link" size="small" danger>Выйти</AButton></AMenuItem>
                    </AMenu>
                </template>
            </ADropdown>
        </div>
    </div>
</template>

<script>
import { mapActions } from 'vuex'
import { RightOutlined, SettingOutlined } from '@ant-design/icons-vue'
import { MenuItemsEn } from './types'

export default {
    data() {
        return {
            selectedKeys: [ 'chats' ]
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

        title() {
            return MenuItemsEn[this.selectedKeys[0]]
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

    components: {
        RightOutlined, SettingOutlined
    }    
}
</script>

<style lang="less" scoped>
    .navbar {
        width: 350px;
        padding: 0 25px;
        flex: 1 0 auto;
        background-color: #1C1C1C;
        box-shadow: 0 0 10px 0 #000;

        &__title {
            width: 100%;
            height: 80px;
            display: flex;
            align-items: center;
            justify-content: space-between;

            &__menu {
                /deep/ &-trigger {
                    font-size: 22px;
                    padding: 0;
                    color: #FFF;

                    .navbar__title__menu-trigger-icon {
                        font-size: 16px;
                        transition: transform .2s ease-in-out;
                    }

                    &:focus {
                        .navbar__title__menu-trigger-icon {
                            transform: rotate(90deg);
                            transition: transform .2s ease-in-out;
                        }
                    }
                }
            }

            &__settings {
                /deep/ &-trigger {
                    padding: 0;
                    display: flex;
                    align-items: flex-end;
                    font-size: 18px;
                    color: #FFF;

                    .navbar__title__settings-trigger-icon {
                        transition: transform .2s ease-in-out;                        
                    }

                    &:focus {
                        .navbar__title__settings-trigger-icon {
                            transform: rotate(-90deg);
                            transition: transform .2s ease-in-out;
                        }
                    }
                }
            }
        }
    }

    .navbar__title__menu-dropdown,
    .navbar__title__settings-dropdown {
        background-color: #1C1C1C;
        box-shadow: 0 0 10px 0 #000;
    }
    
    .navbar__title__menu-dropdown {
        width: 300px;
    }

    .exit {
        padding: 0;
        border: none;
    }
</style>