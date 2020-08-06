export default {
    setAutoLogin(state, isAuto){
        state.isAutoLogin = isAuto;
    },
    addUserInfo(state, userInfo) {
        state.userInfo = userInfo;
    },
    removeUserInfo(state) {
        state.userInfo = {};
    },

    addLoginEmail(state, email) {
        state.loginEmail = email;
    },

    changeState(state, tabNumber) {
        state.tabState = tabNumber
    },
}