import { createApp } from "vue"
import Antd from "ant-design-vue"

import App from "./App.vue";
import router from "@/router"
import store from "@/store"
import api from "@/api"

import "@/styles"

require('@/utils/register-assets')

createApp(App).use(Antd).use(store()).use(router).use(api).mount("#app")
