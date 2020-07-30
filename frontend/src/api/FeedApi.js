
import http from "./http-common.js";
import header from "./header.js"

const createFeed = (data,callback,errorCallback) => {
    http.post('/feed/create', data, header())
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

const getFeedList = (data,callback,errorCallback) => {
    // 피드 조회
    // 성공 : call back 호출
    // 실패 :  errorCallback 호출

    http.post('/feed/list', data, header())
        .then(res => {
            // 서버에서 정상적으로 처리되었으면 res 가 null 이 될 수 없음
            // 서버에서 db 쿼리 하다 오류난 경우일듯
            if(res == null) {
                let error = {msg : '알 수 없는 오류 발생'};
                errorCallback(error);
            }
            // 서버에서 처리되어 데이터가 제대로 넘어왔을 때
            else {
                // 피드 조회 성공
                if(res.data.status) {
                    callback({list: res.data.data});
                }
                // 피드 조회 실패
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
const feedDetail = (data,callback,errorCallback) => {
    http.get('/feed/detail/'+data.id, header())
    .then(res => {
        if(res == null) {
            let error = {msg : '알 수 없는 오류 발생'};
            errorCallback(error);
        }else {
            if(res.data.status) {
                callback({list: res.data.data});
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
const feedUpdate = (data,callback,errorCallback) => {
    http.put('/feed/update', data, header())
    .then(res => {
        if(res == null) {
            let error = {msg : '알 수 없는 오류 발생'};
            errorCallback(error);
        }else {
            if(res.data.status) {
                callback({list: res.data.data});
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
const createReply = (data,callback,errorCallback) => {
    http.post('/reply/feed/create', data, header())
        .then(res => {
            if(res == null) {
                let error = {msg : '알 수 없는 오류 발생'};
                errorCallback(error);
            }
            else {
                if(res.data.status) {
                    callback();
                }
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
const feedDelete = (data,callback,errorCallback) => {
    http.delete('/feed/delete/'+data.fno, header())
        .then(res => {
            if(res == null) {
                let error = {msg : '알 수 없는 오류 발생'};
                errorCallback(error);
            }
            else {
                if(res.data.status) {
                    callback();
                }
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
const createLike = (data,callback,errorCallback) => {
    http.post('/like/feed/create', data, header())
        .then(res => {
            if(res == null) {
                let error = {msg : '알 수 없는 오류 발생'};
                errorCallback(error);
            }
            else {
                if(res.data.status) {
                    callback();
                }
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
const LikeDelete = (data,callback,errorCallback) => {
    http.post('/like/feed/delete', data, header())
        .then(res => {
            if(res == null) {
                let error = {msg : '알 수 없는 오류 발생'};
                errorCallback(error);
            }
            else {
                if(res.data.status) {
                    callback();
                }
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
    , getFeedList:(data,callback, errorCallback)=>getFeedList(data, callback, errorCallback)
    , feedDetail:(data,callback, errorCallback)=>feedDetail(data, callback, errorCallback)
    , feedUpdate:(data,callback, errorCallback)=>feedUpdate(data, callback, errorCallback)
    , createReply:(data,callback, errorCallback)=>createReply(data, callback, errorCallback)
    , feedDelete:(data,callback, errorCallback)=>feedDelete(data, callback, errorCallback)
    , createLike:(data,callback, errorCallback)=>createLike(data, callback, errorCallback)
    , LikeDelete:(data,callback, errorCallback)=>LikeDelete(data, callback, errorCallback)
}

export default FeedApi
