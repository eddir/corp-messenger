<template>
    <div class="profile">
        <div class="profile__title">
            <div class="profile__title__label">
                <span>Профиль</span>
                <span class="profile__title__label-ghost">@{{ login }}</span>
            </div>
            <div class="profile__title__btns">
                <template v-if="isEdit">
                    <AButton @click="edit(false)">Отмена</AButton>
                    <AButton type="primary" @click="save()">Сохранить</AButton>
                </template>
                <AButton v-else type="primary" @click="edit(true)">Редактировать</AButton>
            </div>
        </div>

        <div class="profile__content">
            <div class="profile__content__avatar">
                <div class="image">
                    <UserOutlined />
                </div>
            </div>
            <div class="profile__content__info">
                <div v-for="(value, name) in user" :key="name" class="profile__content__info__section">
                    <div class="profile__content__info__section-label"><ADivider orientation="left" orientation-margin="0px">{{ locale(name) }}</ADivider></div>
                    <AFormItem v-for="(val, key) in value" :key="key" :label="`${locale(key)}:`">
                        <span v-if="!isEdit" class="profile__content__info__section-value">
                            {{ val }}
                            <MinusOutlined v-if="!val" />
                        </span>
                        <AInput v-else v-model:value="user[name][key]" allowClear />
                    </AFormItem>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
import { mapGetters } from 'vuex'
import { UserOutlined, MinusOutlined } from '@ant-design/icons-vue'

import { localization } from '@/utils/helpers'

export default {
    data() {
        return {
            isEdit: false,
            userInit: {
                fio: {
                    firstName: null,
                    lastName: null,
                    middleName: null,
                },
                communication: {
                    email: null,
                    phone: null,
                },
                work: {
                    post: null,
                    department: null,
                    division: null,
                    office: null
                }
            },
            user: {
                fio: {
                    firstName: null,
                    lastName: null,
                    middleName: null,
                },
                communication: {
                    email: null,
                    phone: null,
                },
                work: {
                    post: null,
                    department: null,
                    division: null,
                    office: null
                }
            },
            url: null,
        }
    },

    computed: {
        ...mapGetters('AppStore', [
            'userLogin'
        ]),

        login() {
            return this.userLogin || 'login'
        },
    },

    methods: {
        edit(isEdit) {
            this.isEdit = isEdit

            if (!isEdit) {
                for (let key in this.user)
                    Object.assign(this.user[key], this.userInit[key])  
            }
        },

        save() {
            for (let key in this.userInit)
                Object.assign(this.userInit[key], this.user[key])         
            
            this.isEdit = false
        },

        locale(text) {
            return localization[text]
        }
    },

    components: {
        UserOutlined, MinusOutlined
    }
}
</script>

<style lang="less" scoped>
    .profile {
        width: 100%;
        height: 100%;

        &__title {
            box-sizing: border-box;
            display: flex;
            justify-content: space-between;
            padding: 14px 32px;
            padding-bottom: 13px;
            border-bottom: 1px solid #D9D9D9;

            &__label {
                height: 32px;
                line-height: 32px;
                font-size: 24px;
                font-weight: 500;

                &-ghost {
                    margin-left: 16px;
                    font-size: 18px;
                    color: #8F8F8F;
                }
            }

            &__btns {
                /deep/ .ant-btn:last-child {
                    margin-left: 16px;
                }
            }
        }

        &__content {
            display: flex;
            padding: 32px;

            &__avatar {
                flex: 1 0 auto;
                display: flex;
                justify-content: center;
                align-items: center;
                width: 300px;
                height: 300px;
                border: 1px solid #D9D9D9;
                background-color: #FFF;
                border-radius: 5px;

                .image {
                    font-size: 80px;
                    color: #D9D9D9;
                }
            }
            
            &__info {
                width: 100%;
                margin-left: 32px;

                &__section {
                    margin-bottom: 52px;

                    &-label {
                        font-size: 18px;
                        font-weight: 500;
                        text-transform: uppercase;

                        /deep/ .ant-divider {
                            border-color: #D9D9D9;
                        }
                    }

                    &-value {
                        .anticon {
                            color: #8F8F8F;
                        }
                    }

                    /deep/ .ant-form-item-label {
                        width: 200px;
                        text-align: left;

                        & > label {
                            color: #8F8F8F;
                        }
                    }
                }
            }
        }
    }
</style>