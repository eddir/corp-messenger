<template>
    <div class="auth-sider">
        <div class="auth-sider__appname">
            <p>YAM</p>
            <p>yet another messenger</p>
        </div>
        <ADivider class="auth-sider__title">Авторизация</ADivider>
        <AForm class="auth-sider__form" :v-model="formState" layout="vertical">
            <AFormItem class="auth-sider__form__item" label="Логин" name="login">
                <AInput v-model:value="formState.login" placeholder="Логин" />
            </AFormItem>
            <AFormItem class="auth-sider__form__item" label="Пароль" name="password">
                <AInput v-model:value="formState.password" type="password" placeholder="Пароль" />
            </AFormItem>
            <div class="auth-sider__form__links">
                <AButton type="link">Забыли пароль?</AButton>
                <AButton @click="goTo('/register')" type="link">Регистрация</AButton>
            </div>
            <AButton @click="login()" type="primary" :disabled="!formState.login || !formState.password">Войти</AButton>
        </AForm>
    </div>
</template>

<script>
import { mapActions } from 'vuex'
import { UserOutlined } from '@ant-design/icons-vue'

export default {
    data() {
        return {
            formState: {
                login: null,
                password: null
            }
        }
    },

    methods: {
        ...mapActions('AppStore', [
            'authorize'
        ]),

        login() {
            // this.authorize({ accessToken: '123', refreshToken: '123' })
            // this.goTo('/chats')
            sessionStorage.setItem('ERROR_DESCRIPTION', 'Неправильный логин или пароль')
            this.$api.app.login(this.formState)
                .then(({ data }) => {
                    this.authorize(data)
                    this.goTo('/chats')
                })
                .catch((e) => console.error(e))
        },

        goTo(path) {
            this.$router.push(path)
        }
    },

    components: {
        UserOutlined
    }
}
</script>

<style lang="less" scoped>
    .auth-sider {
        position: relative;

        &__appname {
            position: absolute;
            top: -50%;
            left: 0;
            display: flex;
            flex-direction: column;
            justify-content: center;
            align-items: center;
            width: 100%;

            p {
                margin: 0;
                font-size: 50px;
                font-weight: bold;
                color: #fff;
                letter-spacing: 5px;
                text-align: center;

                &:last-child {
                    font-size: 16px;
                    font-weight: normal;
                    letter-spacing: 1px;
                }
            }
        }
    }
    .auth-sider__form {
        &__item /deep/ label {
            font-size: 16px;
            color: #1890FF;
        }

        &__links /deep/ .ant-btn-link {
            padding: 0;
            margin: 0;

            &:first-child {
                color:#8F8F8F;
            }
        }
    }
</style>