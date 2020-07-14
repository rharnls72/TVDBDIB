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
            // 서버에서 정상적으로 처리되었으면 res 가 null 이 될 수 없음
            // 서버에서 db 쿼리 하다 오류난 경우일듯
            if(res == null) {
                let error = {msg : '알 수 없는 오류 발생'};
                errorCallback(error);
            }
            // 서버에서 처리되어 데이터가 제대로 넘어왔을 때
            else {
                // 왜 안되는지 확인하기 위해 res 값 보기
                // ㅋㅋㅋ res.data 까지 들어가야 서버에서 준 데이터
                // 이런 실수를
                console.log(res.status);
                console.log(res.data);

                // 로그인 성공
                if(res.data.status) {
                    callback();
                }
                // 로그인 실패시(닉네임 또는 이메일 중복)
                else {
                    let error = {msg : res.data.data};
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
    axios.post('http://localhost:9000/account/signup', {
            nickname: data.nickName
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
                    let error = {msg : res.data.data};
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

const UserApi = {
    requestLogin:(data,callback,errorCallback)=>requestLogin(data,callback,errorCallback)
    , requestJoin:(data,callback,errorCallback)=>requestJoin(data,callback,errorCallback)
}

export default UserApi
