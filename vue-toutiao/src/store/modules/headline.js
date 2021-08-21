import axios from 'src/utils/fetch'

const headline = {
    state: {
        list: [],
        loading: false,
        loadingMore: false,
        end: false
    },
    actions: {

    mutations: {
        GETHEADLINELIST (state, list) {
            state.list = state.list.concat(list)
        },
        ADDHEADLINE (state, info) {
            state.list.unshift(info)
        }
    }
}
export default headline