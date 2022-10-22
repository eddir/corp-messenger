import { createApp } from "vue";
import App from "./App.vue";
import router from "@/router";
import store from "@/store";
import api from '@/api'

createApp(App).use(store).use(router).use(api).mount("#app");
