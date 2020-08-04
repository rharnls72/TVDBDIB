
import http from "./http-common.js";
import header from "./header.js"

const getUserList = (data,callback,errorCallback) => {
    // 피드 조회
    // 성공 : call back 호출
    // 실패 :  errorCallback 호출

    http.get('/search/nickname?str=' + data, header())
        .then(res => {
            // 서버에서 정상적으로 처리되었으면 res 가 null 이 될 수 없음
            // 서버에서 db 쿼리 하다 오류난 경우일듯
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

const getAllUser = (data,callback,errorCallback) => {
    // 피드 조회
    // 성공 : call back 호출
    // 실패 :  errorCallback 호출

    http.get('/search/all/user', header())
        .then(res => {
            // 서버에서 정상적으로 처리되었으면 res 가 null 이 될 수 없음
            // 서버에서 db 쿼리 하다 오류난 경우일듯
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

const addHistory = (data,callback,errorCallback) => {
    // 피드 조회
    // 성공 : call back 호출
    // 실패 :  errorCallback 호출

    http.post('/search/history/save', data, header())
        .then(res => {
            // 서버에서 정상적으로 처리되었으면 res 가 null 이 될 수 없음
            // 서버에서 db 쿼리 하다 오류난 경우일듯
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

const getHistoryList = (data,callback,errorCallback) => {
    // 피드 조회
    // 성공 : call back 호출
    // 실패 :  errorCallback 호출

    http.get('/search/history/list', header())
        .then(res => {
            // 서버에서 정상적으로 처리되었으면 res 가 null 이 될 수 없음
            // 서버에서 db 쿼리 하다 오류난 경우일듯
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

const getTagList = (data,callback,errorCallback) => {
    // 전체 태그 리스트 조회 (피드 검색 시 자동완성 용도).
    http.get('/search/taglist', header())
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

 const searchByTag = (data,callback,errorCallback) => {
    // 태그로 피드 검색
    http.get('/search/feed?str=' + data.word + '&startnum=' + data.requestCount, header())
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



const SearchApi = {
    getUserList:(data,callback, errorCallback)=>getUserList(data, callback, errorCallback)
    , getAllUser:(data,callback, errorCallback)=>getAllUser(data, callback, errorCallback)
    , addHistory:(data,callback, errorCallback)=>addHistory(data, callback, errorCallback)
    , getHistoryList:(data,callback, errorCallback)=>getHistoryList(data, callback, errorCallback)
    , getTagList: (data,callback, errorCallback)=>getTagList(data, callback, errorCallback)
    , searchByTag: (data,callback, errorCallback)=>searchByTag(data, callback, errorCallback)
}

export default SearchApi
