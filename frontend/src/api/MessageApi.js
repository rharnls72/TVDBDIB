import http from "./http-common.js";
import header from "./header.js"
const requestProfile = (data,callback,errorCallback) => {
    http.get('/account/followcnt', {
        params: {
          my_nick_name : data.my_nick_name,
          other_nick_name: data.other_nick_name
        }
    }, header())
    .then(res => {
        if(res == null) {
            let error = {msg : '알 수 없는 오류 발생'};
            errorCallback(error);
        }else {
            if(res.data.status) {
                callback({
                    info: res.data.data.userInfo
                    , followcnt: res.data.data.followCnt});
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
const requestFollow = (data,callback,errorCallback) => {
    http.post('/following/user/add', {
        follower : data.follower,
        following: data.following
    }, header())
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
const requestDeFollow = (data,callback,errorCallback) => {
    http.post('/following/user/cancel', {
        follower : data.follower,
        following: data.following
    }, header())
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
const requestFindNick = (data,callback,errorCallback) => {

    http.get('/account/checkNick', {
        params: {
            nick_name : data.nick_name,
            new_nick_name : data.new_nick_name
        }
    }, header())
    .then(res => {
        if(res == null) {
            let error = {msg : '알 수 없는 오류 발생'};
            errorCallback(error);
        } else {
            callback({isNick: res.data.status});
        }
    })
    .catch(error => {
        error.msg = '서버 요청에서 오류 발생';
        errorCallback(error);
    });

}
const requestModifyProfile = (data,callback,errorCallback) => {
    http.put('/account/modifyprofile', data, header())
        .then(res => {
            if(res == null) {
                let error = {msg : '알 수 없는 오류 발생'};
                errorCallback(error);
            } else {
                callback({success: res.data.status, user: res.data.data});
            }
        })
        .catch(error => {
            error.msg = '서버 요청에서 오류 발생';
            errorCallback(error);
        });

}
const AccountApi = {
    requestProfile:(data,callback,errorCallback)=>requestProfile(data,callback,errorCallback)
    , requestFollow:(data,callback,errorCallback)=>requestFollow(data,callback,errorCallback)
    , requestDeFollow:(data,callback,errorCallback)=>requestDeFollow(data,callback,errorCallback)
    , requestFindNick:(data,callback,errorCallback)=>requestFindNick(data,callback,errorCallback)
    , requestModifyProfile:(data,callback,errorCallback)=>requestModifyProfile(data,callback,errorCallback)
}

export default AccountApi
