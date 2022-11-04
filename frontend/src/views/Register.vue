<template>
    <div class="auth-sider">
        <!-- <UserOutlined class="auth-sider__icon" /> -->
        <ADivider class="auth-sider__title">Регистрация</ADivider>
        <AForm class="auth-sider__form" :v-model="formState" layout="vertical">
            <AFormItem class="auth-sider__form__item" label="Логин" name="login" >
                <AInput :v-model:value="formState.login" placeholder="Логин" />
            </AFormItem>
            <ASpace>
                <AFormItem class="auth-sider__form__item" label="Фамилия" name="lastName" >
                    <AInput :v-model:value="formState.lastName" placeholder="Фамилия" />
                </AFormItem>
                <AFormItem class="auth-sider__form__item" label="Имя" name="firstName" >
                    <AInput :v-model:value="formState.firstName" placeholder="Имя" />
                </AFormItem>
                <AFormItem class="auth-sider__form__item" label="Отчество" name="middleName" >
                    <AInput :v-model:value="formState.middleName" placeholder="Отчество" />
                </AFormItem>
            </ASpace>
            <AFormItem class="auth-sider__form__item" label="Пароль" name="password">
                <AInput :v-model:value="formState.password" type="password" placeholder="Пароль" />
            </AFormItem>
            <AFormItem class="auth-sider__form__item" label="Повторить пароль" name="rePassword">
                <AInput :v-model:value="formState.rePassword" type="password" placeholder="Пароль" />
            </AFormItem>
            <div class="auth-sider__form__links">
                <AButton type="link">Есть учетная запись?</AButton>
            </div>
            <AButton type="primary">Зарегистрироваться</AButton>
        </AForm>
    </div>
</template>

<script>
// import { UserOutlined } from '@ant-design/icons-vue'

export default {
    data() {
        return {
            formState: {
                login: null,
                firstName: null,
                lastName: null,
                middleName: null,
                password: null,
                rePassword: null,
                secretCorpKey: null
            }
        }
    },

    // components: {
    //     UserOutlined
    // }
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