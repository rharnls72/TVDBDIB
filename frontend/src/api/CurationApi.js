import http from "./http-common.js";
import header from "@/api/header.js"

const requestEpisode = (callback,errorCallback) => {
    http.get('/episode/following/1', header())
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
const requestEpisodeDetail = (data, callback,errorCallback) => {
    http.get('/episode', {params: {id: data.id}}, header())
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

const deleteEpisodeLike = (data,callback,errorCallback) => {
    // 피드 좋아요 삭제
    // 성공 : call back 호출
    // 실패 :  errorCallback 호출

    console.log(data)

    http.post('/like/episode/delete', data, header())
        .then(res => {
            // 서버에서 정상적으로 처리되었으면 res 가 null 이 될 수 없음
            // 서버에서 db 쿼리 하다 오류난 경우일듯

            if(res == null) {
                let error = {msg : '알 수 없는 오류 발생'};
                errorCallback(error);
            }
            // 서버에서 처리되어 데이터가 제대로 넘어왔을 때
            else {
                // 피드 좋아요 삭제 성공
                if(res.data.status) {
                    callback(res);
                }
                // 피드 좋아요 삭제 실패
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

const createEpisodeLike = (data,callback,errorCallback) => {
    // 피드 좋아요 생성
    // 성공 : call back 호출
    // 실패 :  errorCallback 호출

    console.log(data)

    http.post('/like/episode/create', data, header())
        .then(res => {
            // 서버에서 정상적으로 처리되었으면 res 가 null 이 될 수 없음
            // 서버에서 db 쿼리 하다 오류난 경우일듯
            if(res == null) {
                let error = {msg : '알 수 없는 오류 발생'};
                errorCallback(error);
            }
            // 서버에서 처리되어 데이터가 제대로 넘어왔을 때
            else {
                // 피드 좋아요 생성 성공
                if(res.data.status) {
                    callback(res);
                }
                // 피드 좋아요 생성 실패
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

const CurationApi = {
    requestEpisode:(callback,errorCallback)=>requestEpisode(callback,errorCallback)
    , requestEpisodeDetail:(data,callback,errorCallback)=>requestEpisodeDetail(data,callback,errorCallback)
    , createEpisodeLike:(data,callback,errorCallback)=>createEpisodeLike(data,callback,errorCallback)
    , deleteEpisodeLike:(data,callback,errorCallback)=>deleteEpisodeLike(data,callback,errorCallback)
}

export default CurationApi
