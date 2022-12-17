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
            }
        }
    },

    computed: {
        ...mapGetters('AppStore', [
            'user'
        ])
    },

    methods: {
        ...mapActions('ChatsStore', [
            'saveChat'
        ]),

        save() {
            this.saveChat(this.formState)
            this.$emit('close-modal')
        }
    },

    watch: {
        user(val) {
            if (val) {
                this.formState.company_id = val.company[0].id
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