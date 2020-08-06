
import http from "./http-common.js";
import header from "./header.js"

const getTrendingProgram = (data,callback,errorCallback) => {
    http.get('/recommend/trending', header())
    .then(res => {
        if(res == null) {
            let error = {msg : '알 수 없는 오류 발생'};
            errorCallback(error);
        }
        // 서버에서 처리되어 데이터가 제대로 넘어왔을 때
        else {
            // 성공
            if(res.data.status) {
                callback(res);
            }
            // 실패
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

 const getNewProgram = (data,callback,errorCallback) => {
    http.get('/recommend/new', header())
    .then(res => {
        if(res == null) {
            let error = {msg : '알 수 없는 오류 발생'};
            errorCallback(error);
        }
        // 서버에서 처리되어 데이터가 제대로 넘어왔을 때
        else {
            // 성공
            if(res.data.status) {
                callback(res);
            }
            // 실패
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


const RecommendApi = {
    getTrendingProgram:(data,callback, errorCallback)=>getTrendingProgram(data, callback, errorCallback)
    , getNewProgram:(data,callback, errorCallback)=>getNewProgram(data, callback, errorCallback)
}

export default RecommendApi
