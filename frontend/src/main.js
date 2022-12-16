import { createApp } from "vue"
import moment from "moment/moment";
import Antd, { notification } from "ant-design-vue"

import App from "./App.vue";
import router from "@/router"
import store from "@/store"
import api from "@/api"

import "@/styles"

require('@/utils/register-assets')

moment.locale('ru')

notification.config({
    placement: 'bottomRight',
    bottom: '50px',
    duration: 10
});

createApp(App).use(Antd).use(store()).use(router).use(api).mount("#app")
