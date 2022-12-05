import Vuex from 'vuex'

import { AppStore, ChatsStore } from './modules'

const store = () => {
    return new Vuex.Store({
        modules: {
            AppStore,
            ChatsStore
        }
    })
}

export default store
