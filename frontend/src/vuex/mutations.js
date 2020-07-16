export default {
    addUserInfo(state, userInfo) {
        state.userInfo = userInfo;
    },
    removeUserInfo(state) {
        state.userInfo = {};
    }
}