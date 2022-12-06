<template>
    <div class="chat__empty">
        <div class="chat__empty-icon">
            <InfoCircleOutlined />
        </div>
        <div class="chat__empty-text">
            Выберите чат или канал
        </div>
    </div>
</template>

<script>
import { InfoCircleOutlined } from '@ant-design/icons-vue'

export default {
    data() {
        
    },

    components: {
        InfoCircleOutlined
    }
}
</script>

<style lang="less" scoped>
    .chat {
        &__empty {
            &-icon {
                display: flex;
                justify-content: center;
                align-items: center;
                margin-bottom: 24px;
                font-size: 80px;
                color: #8F8F8F;
            }

            &-text {
                font-size: 18px;
                font-weight: 500;
                color: #8F8F8F;
            }
        }
    }
</style>
