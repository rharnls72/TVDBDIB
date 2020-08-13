/*
 User API 예시
 */

import http from "./http-common.js";

const requestLogin = (data,callback,errorCallback) => {
    // data 를 통해 사용자 정보가 있는지 확인하고
    // 있으면 call back 호출
    // 없으면 errorCallback 호출

    //백앤드와 로그인 통신하는 부분
    http.post('/account/login', data)
        .then(res => {
            // 서버에서 정상적으로 처리되었으면 res 가 null 이 될 수 없음
            // 서버에서 db 쿼리 하다 오류난 경우일듯
            if(res == null) {
                let error = {msg : '알 수 없는 오류 발생'};
                errorCallback(error);
            }
            // 서버에서 처리되어 데이터가 제대로 넘어왔을 때
            else {
                // 로그인 성공
                if(res.data.status) {
                    callback({
                        userInfo: res.data.data.user
                        , jwtToken: res.data.data.token});
                }
                // 로그인 실패시(닉네임 또는 이메일 중복)
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

const requestJoin = (data,callback,errorCallback) => {
    // Join.vue 에서 보낸 데이터 확인
    console.log(data);

    //백앤드와 가입하기 통신하는 부분
    http.post('/account/signup', {
        nick_name: data.nick_name
            , email: data.email
            , password: data.password
        })
        .then(res => {
            if(res == null) {
                let error = {msg : '알 수 없는 오류 발생'};
                errorCallback(error);
            } else {
                // 서버에서 준 res 확인
                console.log(res.data);

                if(res.data.status == false) {
                    let error = {msg : res.data.msg};
                    errorCallback(error);
                } else {
                    callback();
                }
            }
        })
        .catch(error => {
            error.msg = '서버 요청에서 오류 발생';
            errorCallback(error);
        });

}
const requestJoinEmail = (data,callback,errorCallback) => {
    // Join.vue 에서 보낸 데이터 확인
    console.log(data);

    //백앤드와 가입하기 통신하는 부분
    http.post('/account/sendjoinEmail', data)
        .then(res => {
            if(res == null) {
                let error = {msg : '알 수 없는 오류 발생'};
                errorCallback(error);
            } else {
                // 서버에서 준 res 확인
                console.log(res.data);

                if(res.data.status == false) {
                    let error = {msg : res.data.msg};
                    errorCallback(error);
                } else {
                    callback();
                }
            }
        })
        .catch(error => {
            error.msg = '서버 요청에서 오류 발생';
            errorCallback(error);
        });

}
const requestModifyPw = (data,callback,errorCallback) => {
    // ModifyPw.vue 에서 보낸 데이터 확인
    console.log(data);

    //백앤드와 비밀번호 변경하기 통신하는 부분
    http.put('/account/modifypw', {
            password : data.password
            , newPassword : data.newPassword
            , email : data.email
        })
        .then(res => {
            if(res == null) {
                let error = {msg : '알 수 없는 오류 발생'};
                errorCallback(error);
            } else {
                // 서버에서 준 res 확인
                console.log(res.data);

                if(res.data.status == false) {
                    let error = {msg : res.data.msg};
                    errorCallback(error);
                } else {
                    callback();
                }
            }
        })
        .catch(error => {
            error.msg = '서버 요청에서 오류 발생';
            errorCallback(error);
        });

}
const requestModifyPwEmail = (data,callback,errorCallback) => {
    // ModifyPw.vue 에서 보낸 데이터 확인
    console.log(data);

    //백앤드와 비밀번호 변경하기 통신하는 부분
    http.put('/account/modifypwemail', {
            newPassword : data.newPassword
            , email : data.email
        })
        .then(res => {
            if(res == null) {
                let error = {msg : '알 수 없는 오류 발생'};
                errorCallback(error);
            } else {
                // 서버에서 준 res 확인
                console.log(res.data);

                if(res.data.status == false) {
                    let error = {msg : res.data.msg};
                    errorCallback(error);
                } else {
                    callback();
                }
            }
        })
        .catch(error => {
            error.msg = '서버 요청에서 오류 발생';
            errorCallback(error);
        });

}
const requestEmailConfirm = (data,callback,errorCallback) => {
    // ModifyPw.vue 에서 보낸 데이터 확인
    console.log(data);

    //백앤드와 비밀번호 변경하기 통신하는 부분
    http.get('/account/emailconfirm?email='+ data.email)
        .then(res => {
            if(res == null) {
                let error = {msg : '알 수 없는 오류 발생'};
                errorCallback(error);
            } else {
                // 서버에서 준 res 확인
                console.log(res.data);

                if(res.data.status == false) {
                    let error = {msg : res.data.msg};
                    errorCallback(error);
                } else {
                    callback();
                }
            }
        })
        .catch(error => {
            error.msg = '서버 요청에서 오류 발생';
            errorCallback(error);
        });

}
const requestFindEmail = (data,callback,errorCallback) => {
    // ModifyPw.vue 에서 보낸 데이터 확인
    console.log(data);

    //백앤드와 비밀번호 변경하기 통신하는 부분
    http.get('/account/findemail?email=' + data.email)
        .then(res => {
            if(res == null) {
                let error = {msg : '알 수 없는 오류 발생'};
                errorCallback(error);
            } else {
                // 서버에서 준 res 확인
                console.log(res.data);
                callback({isEmail: res.data.status});
            }
        })
        .catch(error => {
            error.msg = 'Find Email: 서버 요청에서 오류 발생';
            errorCallback(error);
        });

}
const requestFindPw = (data,callback,errorCallback) => {
    // ModifyPw.vue 에서 보낸 데이터 확인
    console.log(data);

    //백앤드와 비밀번호 변경하기 통신하는 부분
    http.get('/account/fnidpw', {
        params: {
          email: data.email
        }
      })
        .then(res => {
            if(res == null) {
                let error = {msg : '알 수 없는 오류 발생'};
                errorCallback(error);
            } else {
                // 서버에서 준 res 확인
                console.log(res.data);

                if(res.data.status == false) {
                    let error = {msg : res.data.msg};
                    errorCallback(error);
                } else {
                    callback();
                }
            }
        })
        .catch(error => {
            error.msg = '서버 요청에서 오류 발생';
            errorCallback(error);
        });

}

const loginWithSocial = (data,callback,errorCallback) => {

    http.get('/account/social/login/' + data)
        .then(res => {
            // 서버에서 정상적으로 처리되었으면 res 가 null 이 될 수 없음
            // 서버에서 db 쿼리 하다 오류난 경우일듯
            if(res == null) {
                let error = {msg : '알 수 없는 오류 발생'};
                errorCallback(error);
            }
            // 서버에서 처리되어 데이터가 제대로 넘어왔을 때
            else {
                // 로그인 성공
                if(res.data.status) {
                    callback({
                        userInfo: res.data.data.user
                        , jwtToken: res.data.data.token});
                }
                // 로그인 실패시(닉네임 또는 이메일 중복)
                else {
                    let error = {msg : res.data.msg};
                    errorCallback(error);
                }
            }
        })
        .catch(error => {
            error.msg = 'Social Login: 서버 요청에서 오류 발생';
            errorCallback(error);
        });

}

const joinWithSocial = (data,callback,errorCallback) => {
    
    http.post('/account/social/join', data)
        .then(res => {
            if(res == null) {
                let error = {msg : '알 수 없는 오류 발생'};
                errorCallback(error);
            } else {
                // 서버에서 준 res 확인
                console.log(res.data);

                if(res.data.status == false) {
                    let error = {msg : res.data.msg};
                    errorCallback(error);
                } else {
                    callback();
                }
            }
        })
        .catch(error => {
            error.msg = 'Social Join: 서버 요청에서 오류 발생';
            errorCallback(error);
        });

}
const UserApi = {
    requestLogin:(data,callback,errorCallback)=>requestLogin(data,callback,errorCallback)
    , requestJoin:(data,callback,errorCallback)=>requestJoin(data,callback,errorCallback)
    , requestModifyPw:(data,callback,errorCallback)=>requestModifyPw(data,callback,errorCallback)
    , requestFindEmail:(data,callback,errorCallback)=>requestFindEmail(data,callback,errorCallback)
    , requestFindPw:(data,callback,errorCallback)=>requestFindPw(data,callback,errorCallback)
    , requestJoinEmail:(data,callback,errorCallback)=>requestJoinEmail(data,callback,errorCallback)
    , requestModifyPwEmail:(data,callback,errorCallback)=>requestModifyPwEmail(data,callback,errorCallback)
    , requestEmailConfirm:(data,callback,errorCallback)=>requestEmailConfirm(data,callback,errorCallback)
    , loginWithSocial:(data,callback,errorCallback)=>loginWithSocial(data,callback,errorCallback)
    , joinWithSocial:(data,callback,errorCallback)=>joinWithSocial(data,callback,errorCallback)
}

export default UserApi
