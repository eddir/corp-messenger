import Vuex from 'vuex'

import { AppStore } from './modules'

const store = () => {
    return new Vuex.Store({
        modules: {
            AppStore
        }
    })
}

export default store
