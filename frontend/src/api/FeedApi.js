
import http from "./http-common.js";

const createFeed = (data,callback,errorCallback) => {
    // 피드 생성
    // 성공 : call back 호출
    // 실패 :  errorCallback 호출

    //백앤드와 로그인 통신하는 부분
    http.post('/feed/create', data)
        .then(res => {
            // 서버에서 정상적으로 처리되었으면 res 가 null 이 될 수 없음
            // 서버에서 db 쿼리 하다 오류난 경우일듯
            if(res == null) {
                let error = {msg : '알 수 없는 오류 발생'};
                errorCallback(error);
            }
            // 서버에서 처리되어 데이터가 제대로 넘어왔을 때
            else {
                // 피드 생성 성공
                if(res.data.status) {
                    callback(res);
                }
                // 피드 생성 실패
                else {
                    let error = {msg : res.data.msg};
                    errorCallback(error);
                }
            }
        })
        .catch(err => {
            err.msg = '서버 요청에서 오류 발생';
            errorCallback(err);
        });
}

const FeedApi = {
    createFeed:(data,callback,errorCallback)=>createFeed(data,callback,errorCallback)
}

export default FeedApi