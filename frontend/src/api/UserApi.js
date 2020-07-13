/*
 User API 예시
 */

import axios from "axios";

const requestLogin = (data,callback,errorCallback) => {
    // data 를 통해 사용자 정보가 있는지 확인하고
    // 있으면 call back 호출
    // 없으면 errorCallback 호출

    //백앤드와 로그인 통신하는 부분
    axios.get('http://localhost:9000/account/login?email=' + data.email + '&password=' + data.password)
        .then(res => {
            if(res == null) {
                errorCallback();
            } else {
                callback();
            }
        })
        .catch(error => {
            errorCallback();
        });

}

const requestJoin = (data,callback,errorCallback) => {

    //백앤드와 로그인 통신하는 부분
    axios.post('http://localhost:9000/account/signup', {
            nickname: data.nickName
            , email: data.email
            , password: data.password
        })
        .then(res => {
            if(res == null) {
                errorCallback('알 수 없는 오류 발생');
            } else {

                if(res.status == false) {
                    errorCallback(res.data);
                } else {
                    callback();
                }
            }
        })
        .catch(error => {
            errorCallback('서버 요청에서 오류 발생');
        });

}

const UserApi = {
    requestLogin:(data,callback,errorCallback)=>requestLogin(data,callback,errorCallback)
    , requestJoin:(data,callback,errorCallback)=>requestJoin(data,callback,errorCallback)
}

export default UserApi
