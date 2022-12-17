<template>
    <AModal 
        class="create-chat-modal"
        title="Создание чата"
        okText="Создать"
        cancelText="Отмена"
        closeble
        @ok="save()"
    >
        <AForm layout="vertical" :model="formState">
            <ARadioGroup v-model:value="formState.type" class="col">
                <ARadio value="INDIVIDUAL">Приватный</ARadio>
                <ARadio value="GROUP">Групповой</ARadio>
            </ARadioGroup>
            <ADivider />
            <div v-if="formState.type === 'GROUP'">
                <AFormItem label="Название:" name="name">
                    <AInput v-model:value="formState.name" />
                </AFormItem>
            </div>
            <AFormItem label="Пользователи:" name="userId">
                <ASelect v-model:value="membersIds" placeholder="Выберите пользователя..." allowClear :mode="mode">
                    <ASelectOption v-for="employee in employees" :key="employee.id" :value="employee.id">{{ getUsername(employee) }}</ASelectOption>
                </ASelect>
            </AFormItem>
            <div class="col">
                <ACheckbox v-model:checked="formState.is_private">Закрытый чат</ACheckbox>
                <ACheckbox v-model:checked="formState.is_pinned">Закрепить</ACheckbox>
            </div>
        </AForm>
    </AModal>
</template>

<script>
import { mapActions, mapGetters } from 'vuex'

export default {
    data() {
        return {
            formState: {
                id: null,
                name: null,
                company_id: null,
                is_pinned: false,
                is_private: false,
                img_url: null,
                type: "INDIVIDUAL" // GROUP,INDIVIDUAL,CHANNEL
            },
            membersIds: [],
            employees: []
        }
    },

    computed: {
        ...mapGetters('AppStore', [
            'user'
        ]),

        mode() {
            if (this.formState.type === 'GROUP') {
                this.membersIds = []
                return 'multiple'
            }

            this.membersIds = null
            return ''
        }
    },

    methods: {
        ...mapActions('ChatsStore', [
            'saveChat'
        ]),

        getUsers() {
            this.$api.company.getInfo(this.user.company[0].id)
                .then(({ data }) => {
                    this.employees = data.employers.filter((x) => x.id !== this.user.id)
                })
                .catch((e) => console.error(e))
        },

        getUsername(user) {
            const { first_name, last_name, login } = user
            
            if (first_name && last_name) {
                return `${first_name} ${last_name}`
            }

            return login
        },

        save() {
            this.saveChat({ chat: this.formState, members: this.membersIds })
            this.$emit('close-modal')
        }
    },

    watch: {
        user(val) {
            if (val) {
                this.formState.company_id = val.company[0].id
                this.getUsers()
            }
        }
    },

    components: {

    }
}
</script>

<style lang="less" scoped>
    .create-chat-modal {
        .ant-checkbox-wrapper + .ant-checkbox-wrapper {
            margin-left: 0;
        }   
    }
</style>