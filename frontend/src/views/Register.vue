<template>
    <div class="auth-sider">
        <ADivider class="auth-sider__title">Регистрация</ADivider>
        <AForm 
            class="auth-sider__form" 
            layout="vertical" 
            autocomplete="off"
            ref="formRef"
            :model="formState" 
            :rules="rules" 
            @finish="register"
        >
            <AFormItem class="auth-sider__form__item" label="Логин" name="login" has-feedback>
                <AInput v-model:value="formState.login" placeholder="Логин" />
            </AFormItem>
            <ASpace>
                <AFormItem class="auth-sider__form__item" label="Фамилия" name="last_name" has-feedback>
                    <AInput v-model:value="formState.last_name" placeholder="Фамилия" />
                </AFormItem>
                <AFormItem class="auth-sider__form__item" label="Имя" name="first_name" has-feedback>
                    <AInput v-model:value="formState.first_name" placeholder="Имя" />
                </AFormItem>
                <AFormItem class="auth-sider__form__item" label="Отчество" name="middle_name" >
                    <AInput v-model:value="formState.middle_name" placeholder="Отчество" />
                </AFormItem>
            </ASpace>
            <AFormItem class="auth-sider__form__item" label="Пароль" name="password" has-feedback>
                <AInput v-model:value="formState.password" type="password" placeholder="Пароль" />
            </AFormItem>
            <AFormItem class="auth-sider__form__item" label="Повторить пароль" name="rePassword" has-feedback>
                <AInput v-model:value="formState.rePassword" type="password" placeholder="Повторить пароль" />
            </AFormItem>
            <AFormItem class="auth-sider__form__item" label="Ключ компании" name="company_id">
                <AInput v-model:value="formState.company_id" type="password" placeholder="Ключ компании" />
            </AFormItem>
            <div class="auth-sider__form__links">
                <AButton @click="goTo('/login')" type="link">Есть учетная запись?</AButton>
            </div>
            <AButton type="primary" html-type="submit">Зарегистрироваться</AButton>
        </AForm>
    </div>
</template>

<script>
import { defineComponent, reactive, ref } from 'vue'
import { reduce } from 'lodash'

import api from '@/api'
import router from '@/router'

export default defineComponent({
    setup() {
        const formRef = ref()
        const formState = reactive({
            login: null,
            first_name: null,
            last_name: null,
            middle_name: null,
            password: null,
            rePassword: null,
            company_id: null
        })

        const validatePas = async (_rule, value) => {
            if (!value)
                return Promise.reject('Поле обязательно для заполнения!')
            
            if (formState.rePassword) 
                formRef.value.validateFields('rePassword')
                
            return Promise.resolve()
        }

        const validateRePas = async (_rule, value) => {
            if (!value) 
                return Promise.reject('Поле обязательно для заполнения!')

            if (value !== formState.password)
                return Promise.reject('Пароли не совпадают!')
            
            return Promise.resolve()
        }

        const rules = {
            login: [{ required: true, message: 'Поле обязательно для заполнения!', trigger: 'change' }],
            first_name: [{ required: true, message: 'Поле обязательно для заполнения!', trigger: 'change' }],
            last_name: [{ required: true, message: 'Поле обязательно для заполнения!', trigger: 'change' }],
            password: [{ required: true, validator: validatePas, trigger: 'change' }],
            rePassword: [{ required: true, validator: validateRePas, trigger: 'change' }],
        }

        const register = () => {
            formRef.value.validateFields()
                .then(() => {
                    const dto = reduce(formState, (dto, value, key) => {
                        if (key === 'rePassword')
                            return dto

                        return { ...dto, [key]: value }
                    }, {})
                    
                    api.app.register(dto)
                        .then((res) => {
                            console.log(res.data)
                            goTo('/login')
                        })
                        .catch((e) => console.error(e))
                })
        }

        const goTo = (path) => {
            router.push(path)
        }

        return {
            formRef,
            formState,
            rules,
            register,
            goTo
        }
    }
})
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