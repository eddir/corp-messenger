<template>
    <div class="auth-sider">
        <UserOutlined class="auth-sider__icon" />
        <ADivider class="auth-sider__title">Авторизация</ADivider>
        <AForm class="auth-sider__form" :v-model="formState" layout="vertical">
            <AFormItem class="auth-sider__form__item" label="Логин" name="login" >
                <AInput :v-model:value="formState.login" placeholder="Логин" />
            </AFormItem>
            <AFormItem class="auth-sider__form__item" label="Пароль" name="password">
                <AInput :v-model:value="formState.password" type="password" placeholder="Пароль" />
            </AFormItem>
            <div class="auth-sider__form__links">
                <AButton type="link">Забыли пароль?</AButton>
                <AButton @click="goTo('/register')" type="link">Регистрация</AButton>
            </div>
            <AButton @click="goTo('/')" type="primary">Войти</AButton>
        </AForm>
    </div>
</template>

<script>
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