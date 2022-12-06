<template>
    <AModal 
        title="Создание чата"
        okText="Создать"
        cancelText="Отмена"
        closeble
    >
        <!-- @ok="save()" -->
        <!-- <AForm layout="vertical" :model="formState">
            <ARadioGroup v-model:value="formState.chatType" class="col">
                <ARadio value="private">Приватный</ARadio>
                <ARadio value="group">Групповой</ARadio>
            </ARadioGroup>
            <ADivider />
            <div v-if="chatType === 'group'">
                <AFormItem label="Название:" name="name">
                    <AInput v-model:value="formState.name" />
                </AFormItem>
            </div>
            <div v-else>
                <AFormItem name="userId">
                    <ASelect v-model:value="formState.userId" placeholder="Выберите пользователя:" allowClear>
                        <ASelectOption value="1">Кузнецов Михаил</ASelectOption>
                        <ASelectOption value="2">Проценко Антон</ASelectOption>
                        <ASelectOption value="3">Ростков Эдуард</ASelectOption>
                        <ASelectOption value="4">Ковалев Илья</ASelectOption>
                    </ASelect>
                </AFormItem>
            </div>
        </AForm> -->
    </AModal>
</template>

<script>
// import { mapActions } from 'vuex'

export default {
    data() {
        return {
            // formState: {
            //     chatType: 'private',
            //     name: null,
            //     userId: null,
            // }
        }
    },

    methods: {
        // ...mapActions('ChatsStore', [
        //     'saveChat'
        // ]),

        // save() {

        // }
    },

    components: {

    }
}
</script>

<style lang="less" scoped>

</style>