import http from "./http-common.js";
import header from "./header.js"
const requestUserInfo = (data,callback,errorCallback) => {
    http.post('/message/userinfo', data, header())
    .then(res => {
        if(res == null) {
            let error = {msg : '알 수 없는 오류 발생'};
            errorCallback(error);
        }else {
            if(res.data.status) {
                callback({userInfo: res.data.data});
            }
            else {
                let error = {msg : res.data.msg};
                errorCallback(error);
            }
        }
    })
    .catch(error => {
        error.msg = '서버 요청에서 오류 발생';
        errorCallback(error);
    });
}
const deleteChatroom = (data,callback,errorCallback) => {
    http.delete('/message/delete/chatroom/'+ data, header())
    .then(res => {
        if(res == null) {
            let error = {msg : '알 수 없는 오류 발생'};
            errorCallback(error);
        }else {
            if(res.data.status) {
                callback(res);
            }
            else {
                let error = {msg : res.data.msg};
                errorCallback(error);
            }
        }
    })
    .catch(error => {
        error.msg = '서버 요청에서 오류 발생';
        errorCallback(error);
    });
}
const deleteMessage = (data,callback,errorCallback) => {
    http.delete('/message/delete/message/'+ data, header())
    .then(res => {
        if(res == null) {
            let error = {msg : '알 수 없는 오류 발생'};
            errorCallback(error);
        }else {
            if(res.data.status) {
                callback(res);
            }
            else {
                let error = {msg : res.data.msg};
                errorCallback(error);
            }
        }
    })
    .catch(error => {
        error.msg = '서버 요청에서 오류 발생';
        errorCallback(error);
    });
}
const sendMessage = (data,callback,errorCallback) => {
    http.post('/message/send', data, header())
    .then(res => {
        if(res == null) {
            let error = {msg : '알 수 없는 오류 발생'};
            errorCallback(error);
        }else {
            if(res.data.status) {
                callback();
            }
            else {
                let error = {msg : res.data.msg};
                errorCallback(error);
            }
        }
    })
    .catch(error => {
        error.msg = '서버 요청에서 오류 발생';
        errorCallback(error);
    });
}
const createChatRoom = (data,callback,errorCallback) => {
    http.post('/message/newchatroom', data, header())
    .then(res => {
        if(res == null) {
            let error = {msg : '알 수 없는 오류 발생'};
            errorCallback(error);
        }else {
            if(res.data.status) {
                callback({cno: res.data.data});
            }
            else {
                let error = {msg : res.data.msg};
                errorCallback(error);
            }
        }
    })
    .catch(error => {
        error.msg = '서버 요청에서 오류 발생';
        errorCallback(error);
    });
}
const getChatRoom = (data,callback,errorCallback) => {
    http.post('/message/getroom', data, header())
    .then(res => {
        if(res == null) {
            let error = {msg : '알 수 없는 오류 발생'};
            errorCallback(error);
        }else {
            callback({room: res.data.data});
        }
    })
    .catch(error => {
        error.msg = '서버 요청에서 오류 발생';
        errorCallback(error);
    });
}
const AccountApi = {
    requestUserInfo:(data,callback,errorCallback)=>requestUserInfo(data,callback,errorCallback)
    , deleteChatroom:(data,callback,errorCallback)=>deleteChatroom(data,callback,errorCallback)
    , deleteMessage:(data,callback,errorCallback)=>deleteMessage(data,callback,errorCallback)
    , sendMessage:(data,callback,errorCallback)=>sendMessage(data,callback,errorCallback)
    , createChatRoom:(data,callback,errorCallback)=>createChatRoom(data,callback,errorCallback)
    , getChatRoom:(data,callback,errorCallback)=>getChatRoom(data,callback,errorCallback)
}

export default AccountApi
