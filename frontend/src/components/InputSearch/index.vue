<template>
    <AInput :placeholder="placeholder">
        <template #suffix>
            <SearchOutlined :style="{ fontSize: '18px', color: '#8F8F8F' }"/>
        </template>
    </AInput>
</template>

<script>
import { SearchOutlined } from '@ant-design/icons-vue'

export default {
    props: {
        placeholder: {
            type: String,
            default: () => 'Поиск...'
        }
    },

    data() {
        
    },

    components: {
        SearchOutlined
    }
}
</script>